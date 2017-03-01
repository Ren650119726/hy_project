package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class ItemSalesVolumeDO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_sales_volume.id
     *
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_sales_volume.item_id
     *
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    private Long itemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_sales_volume.seller_id
     *
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    private Long sellerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_sales_volume.sales_volume
     *
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    private Long salesVolume;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_sales_volume.biz_code
     *
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    private String bizCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_sales_volume.delete_mark
     *
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    private Byte deleteMark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_sales_volume.gmt_created
     *
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    private Date gmtCreated;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_sales_volume.gmt_modified
     *
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    private Date gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_sales_volume.id
     *
     * @return the value of item_sales_volume.id
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_sales_volume.id
     *
     * @param id the value for item_sales_volume.id
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_sales_volume.item_id
     *
     * @return the value of item_sales_volume.item_id
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_sales_volume.item_id
     *
     * @param itemId the value for item_sales_volume.item_id
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_sales_volume.seller_id
     *
     * @return the value of item_sales_volume.seller_id
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public Long getSellerId() {
        return sellerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_sales_volume.seller_id
     *
     * @param sellerId the value for item_sales_volume.seller_id
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_sales_volume.sales_volume
     *
     * @return the value of item_sales_volume.sales_volume
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public Long getSalesVolume() {
        return salesVolume;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_sales_volume.sales_volume
     *
     * @param salesVolume the value for item_sales_volume.sales_volume
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_sales_volume.biz_code
     *
     * @return the value of item_sales_volume.biz_code
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public String getBizCode() {
        return bizCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_sales_volume.biz_code
     *
     * @param bizCode the value for item_sales_volume.biz_code
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_sales_volume.delete_mark
     *
     * @return the value of item_sales_volume.delete_mark
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public Byte getDeleteMark() {
        return deleteMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_sales_volume.delete_mark
     *
     * @param deleteMark the value for item_sales_volume.delete_mark
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_sales_volume.gmt_created
     *
     * @return the value of item_sales_volume.gmt_created
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_sales_volume.gmt_created
     *
     * @param gmtCreated the value for item_sales_volume.gmt_created
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_sales_volume.gmt_modified
     *
     * @return the value of item_sales_volume.gmt_modified
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_sales_volume.gmt_modified
     *
     * @param gmtModified the value for item_sales_volume.gmt_modified
     * @mbggenerated Fri Jan 29 20:44:44 CST 2016
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}