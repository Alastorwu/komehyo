package com.komehyo.dao.entity;

import java.util.Date;

public class GoodBrand {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column good_brand.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column good_brand.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column good_brand.cre_user
     *
     * @mbg.generated
     */
    private String creUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column good_brand.cre_date
     *
     * @mbg.generated
     */
    private Date creDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column good_brand.upd_user
     *
     * @mbg.generated
     */
    private String updUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column good_brand.upd_date
     *
     * @mbg.generated
     */
    private Date updDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column good_brand.remarks
     *
     * @mbg.generated
     */
    private String remarks;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column good_brand.sort
     *
     * @mbg.generated
     */
    private Integer sort;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column good_brand.id
     *
     * @return the value of good_brand.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column good_brand.id
     *
     * @param id the value for good_brand.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column good_brand.name
     *
     * @return the value of good_brand.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column good_brand.name
     *
     * @param name the value for good_brand.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column good_brand.cre_user
     *
     * @return the value of good_brand.cre_user
     *
     * @mbg.generated
     */
    public String getCreUser() {
        return creUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column good_brand.cre_user
     *
     * @param creUser the value for good_brand.cre_user
     *
     * @mbg.generated
     */
    public void setCreUser(String creUser) {
        this.creUser = creUser == null ? null : creUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column good_brand.cre_date
     *
     * @return the value of good_brand.cre_date
     *
     * @mbg.generated
     */
    public Date getCreDate() {
        return creDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column good_brand.cre_date
     *
     * @param creDate the value for good_brand.cre_date
     *
     * @mbg.generated
     */
    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column good_brand.upd_user
     *
     * @return the value of good_brand.upd_user
     *
     * @mbg.generated
     */
    public String getUpdUser() {
        return updUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column good_brand.upd_user
     *
     * @param updUser the value for good_brand.upd_user
     *
     * @mbg.generated
     */
    public void setUpdUser(String updUser) {
        this.updUser = updUser == null ? null : updUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column good_brand.upd_date
     *
     * @return the value of good_brand.upd_date
     *
     * @mbg.generated
     */
    public Date getUpdDate() {
        return updDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column good_brand.upd_date
     *
     * @param updDate the value for good_brand.upd_date
     *
     * @mbg.generated
     */
    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column good_brand.remarks
     *
     * @return the value of good_brand.remarks
     *
     * @mbg.generated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column good_brand.remarks
     *
     * @param remarks the value for good_brand.remarks
     *
     * @mbg.generated
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column good_brand.sort
     *
     * @return the value of good_brand.sort
     *
     * @mbg.generated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column good_brand.sort
     *
     * @param sort the value for good_brand.sort
     *
     * @mbg.generated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}