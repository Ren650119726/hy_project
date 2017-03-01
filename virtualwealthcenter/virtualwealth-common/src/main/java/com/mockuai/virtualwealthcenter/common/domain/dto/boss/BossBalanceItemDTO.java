package com.mockuai.virtualwealthcenter.common.domain.dto.boss;

import java.io.Serializable;

public class BossBalanceItemDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5890430766064697668L;

	private String b_id;
	
	private Long b_amount;
	
	private String b_type;
	
	private String b_source_type;
	
	private String b_status;
	
	private String b_gmt_created;
	
	private String b_remark;

	public String getB_id() {
		return b_id;
	}

	public void setB_id(String b_id) {
		this.b_id = b_id;
	}

	public Long getB_amount() {
		return b_amount;
	}

	public void setB_amount(Long b_amount) {
		this.b_amount = b_amount;
	}

	public String getB_type() {
		return b_type;
	}

	public void setB_type(String b_type) {
		this.b_type = b_type;
	}

	public String getB_source_type() {
		return b_source_type;
	}

	public void setB_source_type(String b_source_type) {
		this.b_source_type = b_source_type;
	}

	public String getB_status() {
		return b_status;
	}

	public void setB_status(String b_status) {
		this.b_status = b_status;
	}

	public String getB_gmt_created() {
		return b_gmt_created;
	}

	public void setB_gmt_created(String b_gmt_created) {
		this.b_gmt_created = b_gmt_created;
	}

	public String getB_remark() {
		return b_remark;
	}

	public void setB_remark(String b_remark) {
		this.b_remark = b_remark;
	}
	
	
	
}
