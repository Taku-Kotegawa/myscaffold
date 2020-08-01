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
 * This class corresponds to the database table staff_rev
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StaffRev extends AbstractLombokEntity implements hasWhoColumnInterface, Serializable {
    /**
     * Database Column Remarks:
     *   ID:データの主キー
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   バージョン(楽観的排他用)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.version
     *
     * @mbg.generated
     */
    private Long version;

    /**
     * Database Column Remarks:
     *   公開区分:0=編集中,1=公開,2=非公開(論理削除)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     * Database Column Remarks:
     *   登録日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.created_at
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Database Column Remarks:
     *   登録者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.created_by
     *
     * @mbg.generated
     */
    private String createdBy;

    /**
     * Database Column Remarks:
     *   更新日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.updated_at
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * Database Column Remarks:
     *   更新者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.updated_by
     *
     * @mbg.generated
     */
    private String updatedBy;

    /**
     * Database Column Remarks:
     *   スタッフ番号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.staff_no
     *
     * @mbg.generated
     */
    private String staffNo;

    /**
     * Database Column Remarks:
     *   氏名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   生年月日
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff_rev.birthday
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate birthday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table staff_rev
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}