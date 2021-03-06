package com.example.domain.example;

import com.example.domain.common.hasWhoColumnInterface;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Database Table Remarks:
 *   スタッフ
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table staff_view
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StaffView extends AbstractLombokEntity implements hasWhoColumnInterface, Serializable {
    /**
     * Database Column Remarks:
     *   ID:データの主キー
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.id_str
     *
     * @mbg.generated
     */
    private String idStr;

    /**
     * Database Column Remarks:
     *   バージョン(楽観的排他用)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.version
     *
     * @mbg.generated
     */
    private Long version;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.version_str
     *
     * @mbg.generated
     */
    private String versionStr;

    /**
     * Database Column Remarks:
     *   公開区分:0=編集中,1=公開,2=非公開(論理削除)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.status_str
     *
     * @mbg.generated
     */
    private String statusStr;

    /**
     * Database Column Remarks:
     *   登録日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.created_at
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.created_at_str
     *
     * @mbg.generated
     */
    private String createdAtStr;

    /**
     * Database Column Remarks:
     *   登録者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.created_by
     *
     * @mbg.generated
     */
    private String createdBy;

    /**
     * Database Column Remarks:
     *   更新日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.updated_at
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.updated_at_str
     *
     * @mbg.generated
     */
    private String updatedAtStr;

    /**
     * Database Column Remarks:
     *   更新者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.updated_by
     *
     * @mbg.generated
     */
    private String updatedBy;

    /**
     * Database Column Remarks:
     *   スタッフ番号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.staff_no
     *
     * @mbg.generated
     */
    private String staffNo;

    /**
     * Database Column Remarks:
     *   氏名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   生年月日
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.birthday
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate birthday;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_view.birthday_str
     *
     * @mbg.generated
     */
    private String birthdayStr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table staff_view
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}