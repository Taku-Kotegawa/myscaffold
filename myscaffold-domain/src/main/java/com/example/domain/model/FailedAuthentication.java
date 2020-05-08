package com.example.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Database Table Remarks:
 * 認証失敗履歴
 * <p>
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table public.failed_authentication
 */
@Data
public class FailedAuthentication implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.failed_authentication
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
    /**
     * Database Column Remarks:
     * ユーザID
     * <p>
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.failed_authentication.username
     *
     * @mbg.generated
     */
    private String username;
    /**
     * Database Column Remarks:
     * 失敗日時
     * <p>
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.failed_authentication.authentication_timestamp
     *
     * @mbg.generated
     */
    private LocalDateTime authenticationTimestamp;
}