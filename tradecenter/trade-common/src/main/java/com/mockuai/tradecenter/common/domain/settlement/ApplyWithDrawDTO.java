package com.mockuai.tradecenter.common.domain.settlement;

import com.mockuai.tradecenter.common.domain.BaseDTO;

/**
 * 申请提现DTO
 * @author hzmk
 *
 */
public class ApplyWithDrawDTO extends BaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2541136884889931484L;
	
	private Long sellerId;

	private String name;
	
	private String account;
	
	private String channel;
	
	private Long amount;
	
	private String bizCode;
	
	private Integer type;/** 类别  1店铺 2 商城**/
	
	private Integer operByMockuai;
	
	private Long shopId;

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getOperByMockuai() {
		return operByMockuai;
	}

	public void setOperByMockuai(Integer operByMockuai) {
		this.operByMockuai = operByMockuai;
	}
	
	

}
