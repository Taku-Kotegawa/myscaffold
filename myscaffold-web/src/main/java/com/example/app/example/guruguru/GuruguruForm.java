package com.example.app.example.guruguru;

import com.example.app.common.validation.NotContainControlChars;
import com.example.app.common.validation.UploadFileMaxSize;
import com.example.app.common.validation.UploadFileNotEmpty;
import com.example.app.common.validation.UploadFileRequired;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class GuruguruForm {

    /**
     * テキストフィールド001
     */
    @NotNull
    @NotContainControlChars
    private String textfield001;

    /**
     * テキストフィールド002(数値・整数)
     */
    private Integer textfield002;

    /**
     * テキストフィールド002(数値・小数あり)
     */
    private Float textfield003;

    /**
     * テキストフィールド(真偽値)
     */
    private Boolean textfield004;

    /**
     * テキストフィールド(文字列・複数の値)
     */
    private List<String> textfield005;

    /**
     * ラジオボタン
     */
    private Boolean radio001;

    /**
     * ラジオボタン2
     */
    private String radio002;

    /**
     * チェックボックス1
     */
    private String checkbox001;

    /**
     * チェックボックス2
     */
    private List<String> checkbox002;

    /**
     * テキストエリア1
     */
    private String textarea001;

    /**
     * 日付
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate localdate001;

    /**
     * 日付時刻
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime localdatetime001;

    /**
     * セレクト(単一の値)
     */
    @NotNull
    private String select001;

    /**
     * セレクト(複数の値)
     */
    @NotNull
    @NotEmpty
    private List<String> select002;

    /**
     * セレクト(Select2 単一の値)
     */
    @NotNull
    private String select003;

    /**
     * セレクト(Select2 複数の値)
     */
    @NotNull
    @NotEmpty
    private List<String> select004;

    /**
     * コンボボックス1
     */
    @NotNull
    private String combobox001;

    /**
     * コンボボックス2(複数の値)
     */
    @NotNull
    private List<String> combobox002;

    /**
     * コンボボックス3
     */
    @NotNull
    private String combobox003;

    /**
     * コンボボックス4
     */
    @NotNull
    private String combobox004;

    /**
     * ファイル
     */
    @UploadFileRequired
    @UploadFileNotEmpty
    @UploadFileMaxSize(value = 2024 * 1024)
    private MultipartFile file001;

}
