package com.example.domain.repository.account;

import com.example.domain.model.Account;
import com.example.domain.model.AccountExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface AccountRepository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    long countByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    int deleteByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    int insert(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    int insertSelective(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    List<Account> selectByExampleWithRowbounds(AccountExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    List<Account> selectByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    Account selectByPrimaryKey(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Account record);
}