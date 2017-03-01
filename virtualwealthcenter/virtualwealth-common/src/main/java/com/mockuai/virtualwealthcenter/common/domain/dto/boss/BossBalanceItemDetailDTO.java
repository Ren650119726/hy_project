package com.mockuai.virtualwealthcenter.common.domain.dto.boss;

import java.io.Serializable;

public class BossBalanceItemDetailDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5979960101347245510L;

	private Long w_amount;
	
	private Long w_tamount;
	
	private Long w_total_p;
	
	private Long w_total_w;

	public Long getW_amount() {
		return w_amount;
	}

	public void setW_amount(Long w_amount) {
		this.w_amount = w_amount;
	}

	public Long getW_tamount() {
		return w_tamount;
	}

	public void setW_tamount(Long w_tamount) {
		this.w_tamount = w_tamount;
	}

	public Long getW_total_p() {
		return w_total_p;
	}

	public void setW_total_p(Long w_total_p) {
		this.w_total_p = w_total_p;
	}

	public Long getW_total_w() {
		return w_total_w;
	}

	public void setW_total_w(Long w_total_w) {
		this.w_total_w = w_total_w;
	}
	
	
	
}
