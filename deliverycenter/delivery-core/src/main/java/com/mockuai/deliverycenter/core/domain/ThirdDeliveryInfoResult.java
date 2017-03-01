package com.mockuai.deliverycenter.core.domain;

import java.util.List;

import com.mockuai.deliverycenter.common.dto.express.ThirdpartyExpressDetailDTO;

public class ThirdDeliveryInfoResult {
	
	private String expressNo;//快递单号
	
	private String deliveryCompanyCode;//物流公司编号
	
	 List<ThirdpartyExpressDetailDTO> expressDetailDTOs;

	public List<ThirdpartyExpressDetailDTO> getExpressDetailDTOs() {
		return expressDetailDTOs;
	}

	public void setExpressDetailDTOs(List<ThirdpartyExpressDetailDTO> expressDetailDTOs) {
		this.expressDetailDTOs = expressDetailDTOs;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getDeliveryCompanyCode() {
		return deliveryCompanyCode;
	}

	public void setDeliveryCompanyCode(String deliveryCompanyCode) {
		this.deliveryCompanyCode = deliveryCompanyCode;
	}
	
	
	 
	 

}
