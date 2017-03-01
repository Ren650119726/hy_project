package com.mockuai.deliverycenter.common.dto.express;

import java.util.Date;
import java.util.List;

import com.mockuai.deliverycenter.common.dto.BaseDTO;

public class ExpressDTO extends BaseDTO implements java.io.Serializable {
	private Integer id;
	private String code;
	private String name;
	private String className;
	private String printTemplate;
	private Date gmtCreated;
	private Date gmtModified;
	private List<ExpressPropertyDTO> expressPropertyDTOList;

	public List<ExpressPropertyDTO> getExpressPropertyDTOList() {
		return expressPropertyDTOList;
	}

	public void setExpressPropertyDTOList(
			List<ExpressPropertyDTO> expressPropertyDTOList) {
		this.expressPropertyDTOList = expressPropertyDTOList;
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
