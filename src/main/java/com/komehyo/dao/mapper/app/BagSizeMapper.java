package com.komehyo.dao.mapper.app;

import com.komehyo.dao.entity.BagSize;

public interface BagSizeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bag_size
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bag_size
     *
     * @mbg.generated
     */
    int insert(BagSize record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bag_size
     *
     * @mbg.generated
     */
    int insertSelective(BagSize record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bag_size
     *
     * @mbg.generated
     */
    BagSize selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bag_size
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BagSize record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bag_size
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BagSize record);
}