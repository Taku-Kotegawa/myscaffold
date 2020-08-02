package com.example.domain.service.staff;

import com.example.domain.common.exception.DuplicateKeyBusinessException;
import com.example.domain.common.exception.InvalidArgumentBusinessException;
import com.example.domain.common.exception.NoChangeBusinessException;
import com.example.domain.common.exception.OptimisticLockingFailureBusinessException;
import com.example.domain.example.Staff;
import com.example.domain.example.StaffExample;
import com.example.domain.service.userdetails.LoggedInUser;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.access.AccessDeniedException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Staffエンティティを操作するサービス
 * @author taku.kotegawa
 */
public interface StaffService {

    /**
     * 検索条件に合致するStaffエンティティを取得する。
     * @param staff_no 検索条件(スタッフ番号)
     * @return Staffエンティティ
     * @throws ResourceNotFoundException 検索条件に合致するエンティティが存在しない場合
     */
    Staff findOne(String staff_no);

    /**
     * 検索条件に合致するStaffエンティティを取得する。
     * @param id 検索条件(ID)
     * @return Staffエンティティ
     * @throws ResourceNotFoundException 検索条件に合致するエンティティが存在しない場合
     */
    Staff findOne(long id);

    /**
     * Staffエンティティを新規に登録する。
     * @param record 登録するStaffエンティティ
     * @param asDraft true=ステータスを下書きで保存する。false|null=確定状態で保存する
     * @return 新規登録したStaffエンティティ
     * @throws DuplicateKeyBusinessException スタッフ番号が既に登録されていた場合
     * @throws IllegalArgumentException 入力に不備がある場合
     * @throws IllegalStateException 原因不明な登録失敗
     */
    Staff create(Staff record, Boolean asDraft);

    /**
     * Staffエンティティを更新する。
     * save(record, asDraft, true)を呼ぶ
     */
    Staff save(Staff record, Boolean asDraft);

    /**
     * Staffエンティティを更新する。
     * @param record 更新するStaffエンティティ
     * @param asDraft true=ステータスを下書きで保存する。false|null=確定状態で保存する
     * @param checkChange true=変更箇所の有無を確認する false|null=常に上書きで更新する
     * @return 更新後のStaffエンティティ
     * @throws NoChangeBusinessException 既に登録されている内容と変更する箇所がない場合
     * @throws OptimisticLockingFailureBusinessException 楽観的排他チェックで更新に失敗した場合
     * @throws InvalidArgumentBusinessException 入力に不備がある場合
     * @throws ResourceNotFoundException 検索条件に合致するエンティティが存在しない場合
     */
    Staff save(Staff record, Boolean asDraft, Boolean checkChange);

    /**
     * Staffエンティティの状態を無効にする
     * @param id 検索条件(ID)
     * @return 更新後のStaffエンティティ
     * @throws OptimisticLockingFailureBusinessException 楽観的排他チェックで更新に失敗した場合
     * @throws ResourceNotFoundException 検索条件に合致するエンティティが存在しない場合
     * @throws IllegalStateException 登録されているエンティティの状態が既に無効になっている場合
     */
    Staff invalid(Long id);

    /**
     * <pre>
     * 下書きを取り消す。履歴テーブルから最新の確定データを取得し上書き更新する。
     * 履歴テーブルに確定データが存在しない場合はデータを削除する。
     * </pre>
     * @param id 検索条件(ID)
     * @return 更新後のStaffエンティティ
     * @throws IllegalStateException 登録されているエンティティが下書きでない場合
     */
    Staff cancelDraft(long id);

    /**
     * Staffエンティティを削除する。履歴テーブルは削除しない。
     * @param id 検索条件(ID)
     * @throws ResourceNotFoundException 検索条件に合致するエンティティが存在しない場合
     */
    void delete(long id);

    /**
     * Staffエンティティを削除する。履歴テーブルも一緒に削除する。
     * @param id 検索条件(ID)
     * @throws ResourceNotFoundException 検索条件に合致するエンティティが存在しない場合
     */
    void deleteWithRevision(long id);

    /**
     * Staffエンティティの登録を確認する。
     * @param staff_no 検索条件(スタッフ番号)
     * @return true=登録されている false=登録されていない
     */
    Boolean exists(String staff_no);

    /**
     * Staffエンティティの登録を確認する。
     * @param id 検索条件(ID)
     * @return true=登録されている false=登録されていない
     */
    Boolean exists(long id);

    /**
     * Staffエンティティの一覧を取得する。
     * @param example 検索条件
     * @return Staffエンティティのリスト、0件の場合は空のリスト
     */
    List<Staff> findByExample(StaffExample example);

    /**
     * Staffエンティティの一覧を取得する。
     * @param example 検索条件
     * @param rowBounds 取得するデータ範囲
     * @return Staffエンティティのリスト、0件の場合は空のリスト
     */
    List<Staff> findByExample(StaffExample example, RowBounds rowBounds);

    /**
     * 権限チェックを行う。
     * @param Operation 操作の種類(Constants.OPERATIONに登録された値)
     * @param loggedInUser ログインユーザ情報
     * @return true=操作する権限を持つ, false=操作する権限なし
     * @throws AccessDeniedException @PostAuthorizeを用いてfalse時にスロー
     */
    Boolean hasAuthority(String Operation, LoggedInUser loggedInUser);

}
