package com.mockuai.headsinglecenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录享受首单的订单
 * 
 * Created by csy on 2016-07-13
 */
public class HeadSingleInfoDTO extends BaseDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 订单id
	 */
	private Long orderId;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 订单类型
	 */
	private int orderType;
	
	/**
	 * 支付时间
	 */
	private Date payTime;
	
	/**
	 * 终端类型
	 */
	private int terminalType;	

	/**
	 * 逻辑删除标识 未删除:0;已删除:1
	 */
	private int deleteMark;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreated;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * 备用字段
	 */
	private String res1;
	
	/**
	 * 备用字段
	 */
	private String res2;
	
	/**
	 * 备用字段
	 */
	private String res3;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(int deleteMark) {
		this.deleteMark = deleteMark;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getRes1() {
		return res1;
	}

	public void setRes1(String res1) {
		this.res1 = res1;
	}

	public String getRes2() {
		return res2;
	}

	public void setRes2(String res2) {
		this.res2 = res2;
	}

	public String getRes3() {
		return res3;
	}

	public void setRes3(String res3) {
		this.res3 = res3;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public int getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
