package com.example.domain.service.staff;

import com.example.domain.common.Constants;
import com.example.domain.example.*;
import com.example.domain.repository.example.StaffRepository;
import com.example.domain.repository.example.StaffRevRepository;
import com.example.domain.repository.example.StaffViewRepository;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Transactional
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    StaffRevRepository staffRevRepository;

    @Autowired
    StaffViewRepository staffViewRepository;

    @Autowired
    Mapper beanMapper;

    @Override
    @Transactional(readOnly = true)
    public Staff findOne(String staff_no) {

        StaffExample example = new StaffExample();
        example.or().andStaffNoEqualTo(staff_no);
        List<Staff> staffList = staffRepository.selectByExample(example);

        // データがなければResouceNotFoundExceptionをスロー
        if (staffList.size() == 0) {
            throw new ResourceNotFoundException("staff_no = " + staff_no);
        }

        return staffList.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public Staff findOne(long id) {

        Staff staff = staffRepository.selectByPrimaryKey(id);
        // データがなければResourceNotFoundExceptionをスロー
        if (staff == null) {
            throw new ResourceNotFoundException("id = " + id);
        }
        return staff;
    }

    @Override
    public Staff create(Staff record, Boolean asDraft) {

        // 入力チェック
        RuntimeException runtimeException = checkArgumentError(record, Constants.OPERATION.CREATE);
        if (runtimeException != null) {
            throw runtimeException;
        }

        // 初期値をセット
        if (asDraft) {
            record.setStatus(Constants.STATUS.DRAFT);
        } else {
            record.setStatus(Constants.STATUS.PUBLISHED);
        }
        record.setVersion(0L);

        long count = staffRepository.insert(record);
        if (count == 0) {
            // 登録に失敗したのでIllegalStateExceptionをスロー
            throw new IllegalStateException("Staff_no = " + record.getStaffNo());
        }

        // 本保存のみ履歴テーブルにデータコピー
        if (!asDraft) {
            staffRevRepository.insert(beanMapper.map(findOne(record.getId()), StaffRev.class));
        }

        return findOne(record.getId());
    }

    /**
     * Staffエンティティの入力チェック
     *
     * @param record    対象エンティティ
     * @param operation 操作種別
     * @return null=エラーなし、RuntimeException=例外
     */
    private RuntimeException checkArgumentError(Staff record, String operation) {

        // 入力チェックエラーがあればIllegalArgumentException をスロー

        return null;
    }

    @Override
    public Staff save(Staff record, Boolean asDraft) {

        long count;

        // 上書き更新する前に保存しているデータを一時退避
        // Hiddenに持っていない項目などは必要に応じてrecordに値を復元
        Staff beforeRecord = findOne(record.getId());
        // 復元する値なし

        // 登録済みデータが仮保存かどうか
        boolean isDraft = Constants.STATUS.DRAFT.equals(beforeRecord.getStatus());

        // 固定値設定
        if (asDraft) {
            record.setStatus(Constants.STATUS.DRAFT);
        } else {
            record.setStatus(Constants.STATUS.PUBLISHED);
        }

        // データ更新
        if (asDraft && isDraft) {
            // 下書き保存の上書きはVersionをカウントアップしない
            count = staffRepository.updateByPrimaryKey(record);
            if (count == 0) {
                throw new ResourceNotFoundException("StaffNo = " + record.getStaffNo());
            }
        } else {
            count = staffRepository.updateByPrimaryKeyAndVersion(record);
            if (count == 0) {
                // 楽観的排他チェックエラー
                throw new OptimisticLockingFailureException("StaffNo = " + record.getStaffNo() + ", version = " + record.getVersion());
            }
        }

        // 本保存のみ履歴テーブルにデータコピー
        if (!asDraft) {
            staffRevRepository.insert(beanMapper.map(findOne(record.getId()), StaffRev.class));
        }

        return findOne(record.getId());
    }

    @Override
    public Staff invalid(Staff record) {

        // 状態チェック
        Staff target = findOne(record.getId());
        if (Constants.STATUS.INVALID.equals(record.getStatus())) {
            throw new IllegalStateException();
        }

        // 入力チェック
        RuntimeException exception = checkArgumentError(record, Constants.OPERATION.CREATE);
        if (exception != null) {
            throw exception;
        }

        Staff update = Staff.builder()
                .id(record.getId())
                .version(record.getVersion())
                .status(Constants.STATUS.INVALID)
                .build();

        // status のみ更新
        long count = staffRepository.updateByPrimaryKeyAndVersionSelective(record);

        if (count == 0) {
            // 楽観的排他チェックエラー
            throw new OptimisticLockingFailureException("StaffNo = " + record.getStaffNo() + ", version = " + record.getVersion());
        }

        // 更新結果を取得し、履歴テーブルにデータコピー
        Staff afterRecord = findOne(record.getId());
        staffRevRepository.insert(beanMapper.map(afterRecord, StaffRev.class));

        return afterRecord;
    }

    @Override
    public Staff cancelDraft(long id) {

        Staff beforeRecord = findOne(id);
        if (!Constants.STATUS.DRAFT.equals(beforeRecord.getStatus())) {
            // 下書きデータ以外はキャンセルできない
            throw new UnsupportedOperationException("id = " + id);
        }

        // 履歴テーブルより直前の正保存データを復元
        Staff afterRecord = beanMapper.map(staffRevRepository.selectByPrimaryKey(id, beforeRecord.getVersion() - 1L), Staff.class);
        staffRepository.updateByPrimaryKey(afterRecord);

        return null;
    }

    @Override
    public void delete(String staff_no) {
        Staff record = findOne(staff_no);
        delete(record.getId());
    }

    @Override
    public void delete(long id) {
        long count;
        count = staffRepository.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new ResourceNotFoundException("id = " + id);
        }
    }

    @Override
    public void deleteWithHistory(long id) {
        delete(id);
        StaffRevExample example = new StaffRevExample();
        example.or().andIdEqualTo(id);
        staffRevRepository.deleteByExample(example);
        // 履歴テーブルの削除件数は数えない。0件でも正常。
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean exists(String staff_no) {
        try {
            findOne(staff_no);
        } catch (ResourceNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean exists(long id) {
        try {
            findOne(id);
        } catch (ResourceNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean hasChangedWithoutWhoColumn(Staff record) {

        Staff copy = beanMapper.map(record, Staff.class);
        Staff original = findOne(record.getId());
        unsetWhoColumn(original);
        unsetWhoColumn(copy);

        return copy.equals(original);
    }

    /**
     * Whoカラムにnullをセットする
     *
     * @param record Whoカラムを含むEntity
     */
    private void unsetWhoColumn(Staff record) {
        record.setCreatedAt(null);
        record.setCreatedBy(null);
        record.setUpdatedAt(null);
        record.setUpdatedBy(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Staff> findByExample(StaffExample example) {
        return staffRepository.selectByExample(example);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Staff> findByExample(StaffExample example, RowBounds rowBounds) {
        return staffRepository.selectByExampleWithRowbounds(example, rowBounds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffRev> findRevByExample(StaffRevExample example) {
        return staffRevRepository.selectByExample(example);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffRev> findRevByExample(StaffRevExample example, RowBounds rowBounds) {
        return staffRevRepository.selectByExampleWithRowbounds(example, rowBounds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffView> findViewByExample(StaffViewExample example) {
        return staffViewRepository.selectByExample(example);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffView> findViewByExample(StaffViewExample example, RowBounds rowBounds) {
        return staffViewRepository.selectByExampleWithRowbounds(example, rowBounds);
    }
}
