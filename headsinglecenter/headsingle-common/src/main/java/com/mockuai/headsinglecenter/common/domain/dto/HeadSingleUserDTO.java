package com.mockuai.headsinglecenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录首单用户
 * 
 * Created by csy on 2016-07-13
 */
public class HeadSingleUserDTO extends BaseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 订单统计(支付成功后累加统计,非真实订单统计数据)
	 */
	private Long orderCount;	

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
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
}
