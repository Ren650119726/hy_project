package com.mockuai.virtualwealthcenter.common.domain.dto.boss;

import java.io.Serializable;

public class BossVirtualItemDetailDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4672388781167616331L;

	public Long getV_amount() {
		return v_amount;
	}

	public void setV_amount(Long v_amount) {
		this.v_amount = v_amount;
	}

	public Long getV_tamount() {
		return v_tamount;
	}

	public void setV_tamount(Long v_tamount) {
		this.v_tamount = v_tamount;
	}

	public Long getV_overtime() {
		return v_overtime;
	}

	public void setV_overtime(Long v_overtime) {
		this.v_overtime = v_overtime;
	}

	private Long v_amount;
	
	private Long v_tamount;
	
	private Long v_overtime;
}
