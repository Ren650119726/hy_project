package com.mockuai.headsinglecenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 首单立减功能
 * 
 * Created by csy on 2016-07-13
 */
public class HeadSingleSubDTO extends BaseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 立减金额类型(0不限金额1限满)
	 */
	private int subType;
	
	/**
	 * 限满金额(限满类型必填)
	 */
	private long limitFullAmt;
	
	/**
	 * 优惠金额
	 */
	private long privilegeAmt;
	
	/**
	 * 分佣类型(1分佣0不分佣金)
	 */
	private int discomStatus;
	
	/**
	 * 活动终端类型(android、ios、web)
	 */
	private String activityTerminalType;
	
	/**
	 * 开启状态(0开启1关闭)
	 */
	private int openStatus;
	
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

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public long getLimitFullAmt() {
		return limitFullAmt;
	}

	public void setLimitFullAmt(long limitFullAmt) {
		this.limitFullAmt = limitFullAmt;
	}

	public long getPrivilegeAmt() {
		return privilegeAmt;
	}

	public void setPrivilegeAmt(long privilegeAmt) {
		this.privilegeAmt = privilegeAmt;
	}

	public int getDiscomStatus() {
		return discomStatus;
	}

	public void setDiscomStatus(int discomStatus) {
		this.discomStatus = discomStatus;
	}

	public int getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(int openStatus) {
		this.openStatus = openStatus;
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

	public String getActivityTerminalType() {
		return activityTerminalType;
	}

	public void setActivityTerminalType(String activityTerminalType) {
		this.activityTerminalType = activityTerminalType;
	}	
}
