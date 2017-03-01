package com.mockuai.deliverycenter.common.qto.express;

import com.mockuai.deliverycenter.common.qto.BaseQTO;

public class ExpressRegionQTO extends BaseQTO implements java.io.Serializable {
	private String expressCode;
	private String province;
	private String city;
	private String area;
	private String town;
	private String openstatus;

	public String getOpenstatus() {
		return openstatus;
	}

	public void setOpenstatus(String openstatus) {
		this.openstatus = openstatus;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

}
