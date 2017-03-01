package com.mockuai.deliverycenter.core.domain.express;

import java.util.Date;

import com.mockuai.deliverycenter.core.domain.BaseDo;

public class Express extends BaseDo {
	private Integer id;
	private String code;
	private String name;
	private String className;
	private String printTemplate;
	private Integer deleted;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPrintTemplate() {
		return printTemplate;
	}

	public void setPrintTemplate(String printTemplate) {
		this.printTemplate = printTemplate;
	}

}
