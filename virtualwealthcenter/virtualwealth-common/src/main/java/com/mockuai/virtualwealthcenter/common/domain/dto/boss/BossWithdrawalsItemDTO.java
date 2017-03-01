package com.mockuai.virtualwealthcenter.common.domain.dto.boss;

import java.io.Serializable;


/**
 * 客户管理 -余额流水DTO
 * @author Administrator
 *
 */
public class BossWithdrawalsItemDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1524714035735473922L;

	private String w_id;
	
	private Long w_amount;
	
	private String w_source_type;
	
	private String w_status;
	
	private String w_gmt_created;
	
	private String w_remark;
	
	private String w_type;

	public String getW_type() {
		return w_type;
	}

	public void setW_type(String w_type) {
		this.w_type = w_type;
	}

	public String getW_id() {
		return w_id;
	}

	public void setW_id(String w_id) {
		this.w_id = w_id;
	}

	public Long getW_amount() {
		return w_amount;
	}

	public void setW_amount(Long w_amount) {
		this.w_amount = w_amount;
	}

	public String getW_source_type() {
		return w_source_type;
	}

	public void setW_source_type(String w_source_type) {
		this.w_source_type = w_source_type;
	}

	public String getW_status() {
		return w_status;
	}

	public void setW_status(String w_status) {
		this.w_status = w_status;
	}

	public String getW_gmt_created() {
		return w_gmt_created;
	}

	public void setW_gmt_created(String w_gmt_created) {
		this.w_gmt_created = w_gmt_created;
	}

	public String getW_remark() {
		return w_remark;
	}

	public void setW_remark(String w_remark) {
		this.w_remark = w_remark;
	}
	
	
}
