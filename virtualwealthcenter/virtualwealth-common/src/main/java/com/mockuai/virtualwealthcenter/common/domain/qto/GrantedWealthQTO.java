package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public class GrantedWealthQTO extends PageQTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5359260201794051860L;
	private String id;
    private String bizCode;
    private Long wealthId;
    private Integer wealthType;
    private Integer sourceType;
    private Long receiverId;
    private Long granterId;
    private Integer grantType;
    private Long orderId;
    /**
     * 将要过期的
     */
    private Integer willExpire;
    /**
     * 已经过期的
     */
    private Integer expire;
    private Integer status;
    /**
     * 非此状态
     */
    private Integer nonStatus;
    /**
     * 查询累计虚拟财富, status=0 and 1
     */
    private Integer totalCombine;
    /**
     * 当天时间范围
     */
    private Date currentDay;
    /**
     * 非零的发放总量
     */
    private Integer nonZero;
	/**
     * 未使用完
     */
    private Integer nonEmpty;
    private Long skuId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getWealthId() {
        return wealthId;
    }

    public void setWealthId(Long wealthId) {
        this.wealthId = wealthId;
    }

    public Integer getWealthType() {
        return wealthType;
    }

    public void setWealthType(Integer wealthType) {
        this.wealthType = wealthType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getGranterId() {
        return granterId;
    }

    public void setGranterId(Long granterId) {
        this.granterId = granterId;
    }

    public Integer getGrantType() {
        return grantType;
    }

    public void setGrantType(Integer grantType) {
        this.grantType = grantType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getWillExpire() {
        return willExpire;
    }

    public void setWillExpire(Integer willExpire) {
        this.willExpire = willExpire;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalCombine() {
        return totalCombine;
    }

    public void setTotalCombine(Integer totalCombine) {
        this.totalCombine = totalCombine;
    }

    public Date getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Date currentDay) {
        this.currentDay = currentDay;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getNonZero() {
        return nonZero;
    }

    public void setNonZero(Integer nonZero) {
        this.nonZero = nonZero;
    }

    public Integer getNonStatus() {
        return nonStatus;
    }

    public void setNonStatus(Integer nonStatus) {
        this.nonStatus = nonStatus;
    }

    public Integer getNonEmpty() {
        return nonEmpty;
    }

    public void setNonEmpty(Integer nonEmpty) {
        this.nonEmpty = nonEmpty;
    }
}