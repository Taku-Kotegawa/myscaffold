package com.example.domain.model;

import com.example.domain.common.SetUpdateInterface;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Database Table Remarks:
 *   情報提供管理
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table tt_joho
 */
@Data
public class TtJoho implements SetUpdateInterface, Serializable {
    /**
     * Database Column Remarks:
     *   連番
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.cd_seq
     *
     * @mbg.generated
     */
    private BigDecimal cdSeq;

    /**
     * Database Column Remarks:
     *   閲覧可能範囲
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.cd_browse
     *
     * @mbg.generated
     */
    private String cdBrowse;

    /**
     * Database Column Remarks:
     *   カテゴリ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.cd_category
     *
     * @mbg.generated
     */
    private String cdCategory;

    /**
     * Database Column Remarks:
     *   公開日付
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.tm_release
     *
     * @mbg.generated
     */
    private LocalDateTime tmRelease;

    /**
     * Database Column Remarks:
     *   タイトル
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.kj_title
     *
     * @mbg.generated
     */
    private String kjTitle;

    /**
     * Database Column Remarks:
     *   お知らせメッセージ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.dt_oshirase
     *
     * @mbg.generated
     */
    private String dtOshirase;

    /**
     * Database Column Remarks:
     *   公開期限
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.tm_limit
     *
     * @mbg.generated
     */
    private LocalDateTime tmLimit;

    /**
     * Database Column Remarks:
     *   添付ファイル
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.dt_file
     *
     * @mbg.generated
     */
    private String dtFile;

    /**
     * Database Column Remarks:
     *   最終更新者ユーザID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.cd_lu_user
     *
     * @mbg.generated
     */
    private String cdLuUser;

    /**
     * Database Column Remarks:
     *   最終更新日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_joho.tm_lu
     *
     * @mbg.generated
     */
    private LocalDateTime tmLu;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tt_joho
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}