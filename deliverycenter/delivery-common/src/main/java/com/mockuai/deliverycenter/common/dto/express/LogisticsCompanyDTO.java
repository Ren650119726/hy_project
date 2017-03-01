package com.mockuai.deliverycenter.common.dto.express;

public class LogisticsCompanyDTO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7868344056728297571L;

	/**
	 * 物流公司ID
	 */
	private String code;
	
	/**
	 * 物流公司名称
	 */
	private String name;

	

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
	
	
}
