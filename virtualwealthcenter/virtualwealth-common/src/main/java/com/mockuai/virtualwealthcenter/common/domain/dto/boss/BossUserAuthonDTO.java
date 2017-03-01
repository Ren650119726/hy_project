package com.mockuai.virtualwealthcenter.common.domain.dto.boss;

import java.io.Serializable;
import java.util.Date;

public class BossUserAuthonDTO implements Serializable{
	private Long id;
	private Long user_id;
	private String biz_code;
	private String authon_personalid;
	private String authon_no;
	private String authon_mobile;
	private String authon_realname;
	private String authon_text;
	private Integer authon_status;
	private Date authon_time;
	private Integer delete_mark;
	private Date gmt_created;
	private Date gmt_modified;
	private String authon_bankname;
	private String picture_front;
	private String picture_back;
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
	public String getAuthon_personalid() {
		return authon_personalid;
	}
	public void setAuthon_personalid(String authon_personalid) {
		this.authon_personalid = authon_personalid;
	}
	public String getAuthon_no() {
		return authon_no;
	}
	public void setAuthon_no(String authon_no) {
		this.authon_no = authon_no;
	}
	public String getAuthon_mobile() {
		return authon_mobile;
	}
	public void setAuthon_mobile(String authon_mobile) {
		this.authon_mobile = authon_mobile;
	}
	public String getAuthon_realname() {
		return authon_realname;
	}
	public void setAuthon_realname(String authon_realname) {
		this.authon_realname = authon_realname;
	}
	public String getAuthon_text() {
		return authon_text;
	}
	public void setAuthon_text(String authon_text) {
		this.authon_text = authon_text;
	}
	public Integer getAuthon_status() {
		return authon_status;
	}
	public void setAuthon_status(Integer authon_status) {
		this.authon_status = authon_status;
	}
	public Date getAuthon_time() {
		return authon_time;
	}
	public void setAuthon_time(Date authon_time) {
		this.authon_time = authon_time;
	}
	public Integer getDelete_mark() {
		return delete_mark;
	}
	public void setDelete_mark(Integer delete_mark) {
		this.delete_mark = delete_mark;
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
	public String getAuthon_bankname() {
		return authon_bankname;
	}
	public void setAuthon_bankname(String authon_bankname) {
		this.authon_bankname = authon_bankname;
	}
	public String getPicture_front() {
		return picture_front;
	}
	public void setPicture_front(String picture_front) {
		this.picture_front = picture_front;
	}
	public String getPicture_back() {
		return picture_back;
	}
	public void setPicture_back(String picture_back) {
		this.picture_back = picture_back;
	}
	
}
