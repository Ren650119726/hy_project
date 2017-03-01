package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

import java.io.Serializable;
import java.util.Date;

public class MopWdBankInfoAppDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3171781023839776093L;
	private Long id;
	private Long user_id;
	private String biz_code;
	private String bank_name;
	private String bank_lastno;
	private String bank_no;
	private Integer bank_type;
	private String bank_remark;
	private Integer bank_isdefault;
	private String bank_realname;
	private Long bank_oneday_quota;
	private Long bank_single_quota;
	private Integer delete_mark;
	private Date bank_bindtime;
	private Date gmt_created;
	private Date gmt_modified;
	
	private String wd_config_text;
	private String wd_config_mininum;
	private String wd_config_maxnum;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getBiz_code() {
		return biz_code;
	}

	public void setBiz_code(String biz_code) {
		this.biz_code = biz_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_lastno() {
		return bank_lastno;
	}

	public void setBank_lastno(String bank_lastno) {
		this.bank_lastno = bank_lastno;
	}

	public String getBank_no() {
		return bank_no;
	}

	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}

	public Integer getBank_type() {
		return bank_type;
	}

	public void setBank_type(Integer bank_type) {
		this.bank_type = bank_type;
	}

	public String getBank_remark() {
		return bank_remark;
	}

	public void setBank_remark(String bank_remark) {
		this.bank_remark = bank_remark;
	}

	public Integer getBank_isdefault() {
		return bank_isdefault;
	}

	public void setBank_isdefault(Integer bank_isdefault) {
		this.bank_isdefault = bank_isdefault;
	}

	public String getBank_realname() {
		return bank_realname;
	}

	public void setBank_realname(String bank_realname) {
		this.bank_realname = bank_realname;
	}

	public Long getBank_oneday_quota() {
		return bank_oneday_quota;
	}

	public void setBank_oneday_quota(Long bank_oneday_quota) {
		this.bank_oneday_quota = bank_oneday_quota;
	}

	public Long getBank_single_quota() {
		return bank_single_quota;
	}

	public void setBank_single_quota(Long bank_single_quota) {
		this.bank_single_quota = bank_single_quota;
	}

	public Integer getDelete_mark() {
		return delete_mark;
	}

	public void setDelete_mark(Integer delete_mark) {
		this.delete_mark = delete_mark;
	}

	public Date getBank_bindtime() {
		return bank_bindtime;
	}

	public void setBank_bindtime(Date bank_bindtime) {
		this.bank_bindtime = bank_bindtime;
	}

	public Date getGmt_created() {
		return gmt_created;
	}

	public void setGmt_created(Date gmt_created) {
		this.gmt_created = gmt_created;
	}

	public Date getGmt_modified() {
		return gmt_modified;
	}

	public void setGmt_modified(Date gmt_modified) {
		this.gmt_modified = gmt_modified;
	}

	public String getWd_config_text() {
		return wd_config_text;
	}

	public void setWd_config_text(String wd_config_text) {
		this.wd_config_text = wd_config_text;
	}

	public String getWd_config_mininum() {
		return wd_config_mininum;
	}

	public void setWd_config_mininum(String wd_config_mininum) {
		this.wd_config_mininum = wd_config_mininum;
	}

	public String getWd_config_maxnum() {
		return wd_config_maxnum;
	}

	public void setWd_config_maxnum(String wd_config_maxnum) {
		this.wd_config_maxnum = wd_config_maxnum;
	}
	
}
