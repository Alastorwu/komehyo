package com.komehyo.dao.mapper.app;

import com.komehyo.dao.entity.Goods;
import com.komehyo.dao.entity.GoodsWithBLOBs;

public interface GoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_goods
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_goods
     *
     * @mbg.generated
     */
    int insert(GoodsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_goods
     *
     * @mbg.generated
     */
    int insertSelective(GoodsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_goods
     *
     * @mbg.generated
     */
    GoodsWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(GoodsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(GoodsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Goods record);
}