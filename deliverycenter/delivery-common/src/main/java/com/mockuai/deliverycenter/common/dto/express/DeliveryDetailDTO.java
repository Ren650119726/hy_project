package com.mockuai.deliverycenter.common.dto.express;

import java.io.Serializable;
import java.util.Date;

import com.mockuai.deliverycenter.common.dto.BaseDTO;

public class DeliveryDetailDTO extends BaseDTO implements Serializable {
    private Long id;

    private String deliveryDetailUid;
    
    private Long orderId;

    private Long userId;

    private String deliveryCode;

    private String content;

    private Date opTime;
    
    private String bizCode;
    
    


    public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setDeliveryDetailUid(String deliveryDetailUid){
    	this.deliveryDetailUid = deliveryDetailUid;
    }
    
    public String getDeliveryDetailUid(){
    	return this.deliveryDetailUid;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode == null ? null : deliveryCode.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

}