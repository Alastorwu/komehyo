package com.komehyo.dao.mapper.app;

import com.komehyo.dao.entity.MiddleType;

public interface MiddleTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table middle_type
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table middle_type
     *
     * @mbg.generated
     */
    int insert(MiddleType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table middle_type
     *
     * @mbg.generated
     */
    int insertSelective(MiddleType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table middle_type
     *
     * @mbg.generated
     */
    MiddleType selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table middle_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MiddleType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table middle_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MiddleType record);
}