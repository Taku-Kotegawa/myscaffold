package com.example.domain.repository.example;

import com.example.domain.example.Sample1;
import com.example.domain.example.Sample1Example;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface Sample1Repository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    long countByExample(Sample1Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int deleteByExample(Sample1Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer integerNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int insert(Sample1 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int insertSelective(Sample1 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    List<Sample1> selectByExampleWithBLOBsWithRowbounds(Sample1Example example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    List<Sample1> selectByExampleWithBLOBs(Sample1Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    List<Sample1> selectByExampleWithRowbounds(Sample1Example example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    List<Sample1> selectByExample(Sample1Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    Sample1 selectByPrimaryKey(Integer integerNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Sample1 record, @Param("example") Sample1Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") Sample1 record, @Param("example") Sample1Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Sample1 record, @Param("example") Sample1Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(Sample1 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Sample1 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionWithBLOBs(Sample1 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(Sample1 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(Sample1 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sample1
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Sample1 record);
}