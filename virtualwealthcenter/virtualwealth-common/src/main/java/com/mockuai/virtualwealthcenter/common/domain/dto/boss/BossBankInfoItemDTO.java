package com.mockuai.virtualwealthcenter.common.domain.dto.boss;

import java.io.Serializable;

public class BossBankInfoItemDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -750706157600920160L;

	private String id; 	 
	
	private String bank_type;	
	  
	private String bank_name;	 	 	 
	
	private String bank_no;
	

	private String gmt_created;	 	 	 
	private String withdrawals_num;	 	 	 
	private Long withdrawals_amount;	 	 
	private String delete_mark;	 	 	 
	private String gmt_modified;
	
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getGmt_created() {
		return gmt_created;
	}
	public void setGmt_created(String gmt_created) {
		this.gmt_created = gmt_created;
	}
	public String getWithdrawals_num() {
		return withdrawals_num;
	}
	public void setWithdrawals_num(String withdrawals_num) {
		this.withdrawals_num = withdrawals_num;
	}
	public Long getWithdrawals_amount() {
		return withdrawals_amount;
	}
	public void setWithdrawals_amount(Long withdrawals_amount) {
		this.withdrawals_amount = withdrawals_amount;
	}
	public String getDelete_mark() {
		return delete_mark;
	}
	public void setDelete_mark(String delete_mark) {
		this.delete_mark = delete_mark;
	}
	public String getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(String gmt_modified) {
		this.gmt_modified = gmt_modified;
	}	 	 	 

}
