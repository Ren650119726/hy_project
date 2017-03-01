package com.mockuai.deliverycenter.common.qto.express;
/**
 * 
 * @author hzmk
 *
 */
public class ThirdpartyExpressInfoQTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2680509313136661366L;
	
	private String expressNo;//快递单号
	
	private String deliveryCompanyCode;//物流公司编号
	
	private String thirdpartyCode;//第三方平台编码

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}


	public String getThirdpartyCode() {
		return thirdpartyCode;
	}

	public void setThirdpartyCode(String thirdpartyCode) {
		this.thirdpartyCode = thirdpartyCode;
	}

	public String getDeliveryCompanyCode() {
		return deliveryCompanyCode;
	}

	public void setDeliveryCompanyCode(String deliveryCompanyCode) {
		this.deliveryCompanyCode = deliveryCompanyCode;
	}
	
	

}
