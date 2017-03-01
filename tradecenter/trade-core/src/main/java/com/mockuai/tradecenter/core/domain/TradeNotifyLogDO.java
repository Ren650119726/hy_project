package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class TradeNotifyLogDO {

	private Long id;

	private Long orderId;

	/**
	 * 第三方支付平台上的交易金额
	 */
	private Long tradeAmount;

	/**
	 * 目前都是1，1代表支付（目前退款没记notify记录）
	 */
	private Integer type;

	/**
	 * 1是初始值（获取支付链接的时候会生成一条）
	 * 2是支付成功
	 * 3是支付失败
	 */
	private Integer status;

	/**
	 * 魔筷平台的orderSn
	 */
	private String innerBillNo;

	/**
	 * 第三方支付平台的流水号
	 */
	private String outBillNo;

	/**
	 * 支付的错误提示信息
	 */
	private String outErrorMsg;


	private Date gmtCreated;

	private Date gmtModified;

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

	public Long getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInnerBillNo() {
		return innerBillNo;
	}

	public void setInnerBillNo(String innerBillNo) {
		this.innerBillNo = innerBillNo;
	}

	public String getOutBillNo() {
		return outBillNo;
	}

	public void setOutBillNo(String outBillNo) {
		this.outBillNo = outBillNo;
	}

	public String getOutErrorMsg() {
		return outErrorMsg;
	}

	public void setOutErrorMsg(String outErrorMsg) {
		this.outErrorMsg = outErrorMsg;
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
}
