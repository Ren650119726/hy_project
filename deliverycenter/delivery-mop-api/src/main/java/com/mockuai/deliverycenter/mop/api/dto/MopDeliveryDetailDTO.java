package com.mockuai.deliverycenter.mop.api.dto;

/**
 * 具体的物流信息数据传输类
 * @author cwr
 */
public class MopDeliveryDetailDTO {
	
	public String getOpTime() {
		return opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}


	public String getDeliveryDetailUid() {
		return deliveryDetailUid;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDeliveryDetailUid(String deliveryDetailUid) {
		this.deliveryDetailUid = deliveryDetailUid;
	}

	private String deliveryDetailUid;
	
	private String  opTime;//操作时间

	private String content;//内容
	
}
