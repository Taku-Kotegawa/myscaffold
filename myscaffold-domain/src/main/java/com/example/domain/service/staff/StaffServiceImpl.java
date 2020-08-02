package com.example.domain.service.staff;

import com.example.domain.common.BeanUtils;
import com.example.domain.common.exception.DuplicateKeyBusinessException;
import com.example.domain.common.exception.NoChangeBusinessException;
import com.example.domain.common.exception.OptimisticLockingFailureBusinessException;
import com.example.domain.common.message.MessageKeys;
import com.example.domain.example.Staff;
import com.example.domain.example.StaffExample;
import com.example.domain.example.StaffRev;
import com.example.domain.example.StaffRevExample;
import com.example.domain.repository.example.StaffRepository;
import com.example.domain.repository.example.StaffRevRepository;
import com.example.domain.service.userdetails.LoggedInUser;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.util.List;

import static com.example.domain.common.Constants.OPERATION;
import static com.example.domain.common.Constants.STATUS;

@Slf4j
@Transactional
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    StaffRevRepository staffRevRepository;

    @Autowired
    Mapper beanMapper;

    @Override
    @Transactional(readOnly = true)
    public Staff findOne(String staff_no) {
        StaffExample example = new StaffExample();
        example.or().andStaffNoEqualTo(staff_no);
        List<Staff> staffList = staffRepository.selectByExample(example);
        // データがなければResourceNotFoundExceptionをスロー
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

        if (asDraft == null) { asDraft = false; }

        // 入力チェック
        checkArgumentError(record, OPERATION.CREATE);

        // 状態チェック(主キーの重複)
        if (exists(record.getStaffNo())) {
            ResultMessages messages = ResultMessages.error().add(MessageKeys.W_AR_FW_2002, "従業員番号", record.getStaffNo());
            throw new DuplicateKeyBusinessException(messages);
        }

        // 初期値をセット
        if (asDraft) {
            record.setStatus(STATUS.DRAFT);
        } else {
            record.setStatus(STATUS.VALID);
        }
        record.setVersion(0L);
        BeanUtils.setWhoColumn(record);

        long count = staffRepository.insert(record);
        if (count == 0) {
            // 登録に失敗したのでIllegalStateExceptionをスロー
            throw new IllegalStateException("staff_no = " + record.getStaffNo());
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
     * @throws IllegalArgumentException 入力内容に不備がある場合
     */
    private void checkArgumentError(Staff record, String operation) {

        // 入力チェックエラーがあればIllegalArgumentExceptionをスロー

    }

    @Override
    public Staff save(Staff record, Boolean asDraft) {
        return save(record, asDraft, true);
    }

    @Override
    public Staff save(Staff record, Boolean asDraft, Boolean checkChange) {

        if (asDraft == null) { asDraft = false; }

        // 入力チェック
        checkArgumentError(record, OPERATION.UPDATE);


        // 上書き更新する前に保存しているデータを一時退避
        Staff beforeRecord = findOne(record.getId());

        // 登録済みデータが仮保存かどうか
        boolean isDraft = STATUS.DRAFT.equals(beforeRecord.getStatus());

        // 固定値設定
        record.setStatus(asDraft ? STATUS.DRAFT : STATUS.VALID);
        BeanUtils.setWhoColumn(record);

        // データ更新
        if (asDraft && isDraft) {
            // 下書き保存の上書きはVersionをカウントアップしない
            int count = staffRepository.updateByPrimaryKey(record);
            if (count == 0) {
                throw new ResourceNotFoundException("StaffNo = " + record.getStaffNo());
            }
        } else {

            // 登録内容と保存されている値を比較、変更がなければ例外をスロー
            if (checkChange) {
                Boolean withoutStatus = true;
                if (!asDraft && isDraft) {
                    withoutStatus = false;
                }
                if (hasNotChangedWithoutWhoColumn(record, beforeRecord, withoutStatus)) {
                    throw new NoChangeBusinessException(ResultMessages.warning().add(MessageKeys.W_AR_FW_2001));
                }
            }

            int count = staffRepository.updateByPrimaryKeyAndVersion(record);
            if (count == 0) {
                // 楽観的排他チェックエラー
                throw new OptimisticLockingFailureBusinessException(ResultMessages.warning().add(MessageKeys.E_AR_FW_8001));
            }
        }

        // 本保存のみ履歴テーブルにデータコピー
        if (!asDraft) {
            staffRevRepository.insert(beanMapper.map(findOne(record.getId()), StaffRev.class));
        }

        return findOne(record.getId());
    }

    @Override
    public Staff invalid(Long id) {

        // 状態チェック
        Staff record = findOne(id);
        if (!STATUS.VALID.equals(record.getStatus())) {
            throw new IllegalStateException();
        }

        Staff update = new Staff();
        update.setId(record.getId());
        update.setVersion(record.getVersion());
        update.setStatus(STATUS.INVALID);

        BeanUtils.setWhoColumn(update);

        // status のみ更新
        long count = staffRepository.updateByPrimaryKeyAndVersionSelective(update);

        if (count == 0) {
            // 楽観的排他チェックエラー
            throw new OptimisticLockingFailureBusinessException(ResultMessages.warning().add(MessageKeys.E_AR_FW_8001));
        }

        // 更新結果を取得し、履歴テーブルにデータコピー
        Staff afterRecord = findOne(record.getId());
        staffRevRepository.insert(beanMapper.map(afterRecord, StaffRev.class));

        return afterRecord;
    }

    @Override
    public Staff cancelDraft(long id) {

        // 状態チェック
        Staff currentRecord = findOne(id);
        if (!STATUS.DRAFT.equals(currentRecord.getStatus())) {
            // 下書きデータ以外はキャンセルできない
            throw new IllegalStateException("id = " + id);
        }

        // 履歴テーブルより１世代前のデータを取得し、取得できればそれを復元する。
        // 取得できなければ、今のデータを削除
        StaffRev prevRecord = staffRevRepository.selectByPrimaryKey(id, currentRecord.getVersion() - 1L);
        if (prevRecord != null) {
            Staff afterRecord = beanMapper.map(prevRecord, Staff.class);
            staffRepository.updateByPrimaryKey(afterRecord);
            return findOne(id);
        } else {
            delete(id);
            return null;
        }
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
    public void deleteWithRevision(long id) {
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

    private Boolean hasNotChangedWithoutWhoColumn(Staff record, Staff beforeRecord, Boolean withoutStatus) {
        Staff recordCopy = beanMapper.map(record, Staff.class);
        unsetWhoColumn(recordCopy);
        Staff beforeCopy = beanMapper.map(beforeRecord, Staff.class);
        unsetWhoColumn(beforeCopy);

        if (withoutStatus) {
            recordCopy.setStatus(null);
            beforeCopy.setStatus(null);
        }

        return recordCopy.equals(beforeCopy);
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
    @PostAuthorize("returnObject == true")
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {
        return true;
    }
}
