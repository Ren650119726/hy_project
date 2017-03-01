package com.mockuai.virtualwealthcenter.common.domain.dto.boss;

import java.io.Serializable;

public class BossVirtualItemDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8281711132316393694L;


	private String v_id;	
	
	 
	private Long v_amount;
	
	private String v_type;
	
	private String v_source_type;
	
	private String v_status;
	
	private String v_gmt_created;
	
	private String v_remark;

	public String getV_id() {
		return v_id;
	}

	public void setV_id(String v_id) {
		this.v_id = v_id;
	}

	public Long getV_amount() {
		return v_amount;
	}

	public void setV_amount(Long v_amount) {
		this.v_amount = v_amount;
	}

	public String getV_type() {
		return v_type;
	}

	public void setV_type(String v_type) {
		this.v_type = v_type;
	}

	public String getV_source_type() {
		return v_source_type;
	}

	public void setV_source_type(String v_source_type) {
		this.v_source_type = v_source_type;
	}

	public String getV_status() {
		return v_status;
	}

	public void setV_status(String v_status) {
		this.v_status = v_status;
	}

	public String getV_gmt_created() {
		return v_gmt_created;
	}

	public void setV_gmt_created(String v_gmt_created) {
		this.v_gmt_created = v_gmt_created;
	}

	public String getV_remark() {
		return v_remark;
	}

	public void setV_remark(String v_remark) {
		this.v_remark = v_remark;
	}
	 
	
	
	
}
