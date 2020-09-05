package com.example.app.joho;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JohoListRow {

    private static final long serialVersionUID = 1L;

    /**
     * タイトル
     */
    private String kjTitle;

    /**
     * カテゴリ(ラベル)
     */
    private String cdCategoryLabel;

    /**
     * 公開日付
     */
    private LocalDateTime tmRelease;

    /**
     * 公開期限
     */
    private LocalDateTime tmLimit;

    /**
     * 添付ファイル
     */
    private String dtFile;

    /**
     * 閲覧可能範囲(ラベル)
     */
    private String cdBrowseLabel;

    /**
     * 編集
     */
    private String operation;

}
