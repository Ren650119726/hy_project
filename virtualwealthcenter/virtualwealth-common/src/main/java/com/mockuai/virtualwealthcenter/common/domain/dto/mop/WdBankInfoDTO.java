package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

import java.io.Serializable;

public class WdBankInfoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2697919937643279324L;
	private Integer withdrawalsStatus;
	private Integer withdrawalsType;
	private String withdrawalsNo;
	private String withdrawalsLastno;
	private String withdrawalsAmount;
	private String withdrawalsNumber;
	
	public String getWithdrawalsNumber() {
		return withdrawalsNumber;
	}
	public void setWithdrawalsNumber(String withdrawalsNumber) {
		this.withdrawalsNumber = withdrawalsNumber;
	}
	public Integer getWithdrawalsStatus() {
		return withdrawalsStatus;
	}
	public void setWithdrawalsStatus(Integer withdrawalsStatus) {
		this.withdrawalsStatus = withdrawalsStatus;
	}
	public Integer getWithdrawalsType() {
		return withdrawalsType;
	}
	public void setWithdrawalsType(Integer withdrawalsType) {
		this.withdrawalsType = withdrawalsType;
	}
	public String getWithdrawalsNo() {
		return withdrawalsNo;
	}
	public void setWithdrawalsNo(String withdrawalsNo) {
		this.withdrawalsNo = withdrawalsNo;
	}
	public String getWithdrawalsLastno() {
		return withdrawalsLastno;
	}
	public void setWithdrawalsLastno(String withdrawalsLastno) {
		this.withdrawalsLastno = withdrawalsLastno;
	}
	public String getWithdrawalsAmount() {
		return withdrawalsAmount;
	}
	public void setWithdrawalsAmount(String withdrawalsAmount) {
		this.withdrawalsAmount = withdrawalsAmount;
	}
	
	
	
}
