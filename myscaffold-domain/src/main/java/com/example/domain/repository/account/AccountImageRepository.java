package com.example.domain.repository.account;

import com.example.domain.model.AccountImage;
import com.example.domain.model.AccountImageExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface AccountImageRepository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    long countByExample(AccountImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int deleteByExample(AccountImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int insert(AccountImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int insertSelective(AccountImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    List<AccountImage> selectByExampleWithBLOBsWithRowbounds(AccountImageExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    List<AccountImage> selectByExampleWithBLOBs(AccountImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    List<AccountImage> selectByExampleWithRowbounds(AccountImageExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    List<AccountImage> selectByExample(AccountImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    AccountImage selectByPrimaryKey(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AccountImage record, @Param("example") AccountImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") AccountImage record, @Param("example") AccountImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AccountImage record, @Param("example") AccountImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AccountImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(AccountImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.account_image
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AccountImage record);
}