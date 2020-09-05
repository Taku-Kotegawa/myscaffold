package com.example.app.joho;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JohoForm implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 連番
     */
    private BigDecimal cdSeq;

    /**
     * 閲覧可能範囲
     */
    private String cdBrowse;

    /**
     * カテゴリ
     */
    private String cdCategory;

    /**
     * 公開日付
     */
    private LocalDateTime tmRelease;

    /**
     * タイトル
     */
    private String kjTitle;

    /**
     * お知らせメッセージ
     */
    private String dtOshirase;

    /**
     * 公開期限
     */
    private LocalDateTime tmLimit;

    /**
     * 添付ファイル
     */
    private String dtFile;

    /**
     * 最終更新者ユーザID
     */
    private String cdLuUser;

    /**
     * 最終更新日時
     */
    private LocalDateTime tmLu;

}
