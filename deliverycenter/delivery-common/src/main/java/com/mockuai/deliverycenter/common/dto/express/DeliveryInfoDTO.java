package com.mockuai.deliverycenter.common.dto.express;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderItemDTO;

/**
 * 物流单信息
 * @author cwr
 */
public class DeliveryInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8262488355562525803L;

	private Long id;	
	
	private String deliveryInfoUid;
	
	private String expressNo;

    private Date deliveryTime;

    private Date arrivalTime;

    private Long userId;
    
    private Long orderId;

	private String memo;

    private Integer deliveryId;

    private String express;
    
    private Date gmtCreated;
    
    private Date gmtModified;
    
    List<ThirdpartyExpressDetailDTO> thirdpartyList;
    
    private String bizCode;
	
	private String expressCode;
	
	private String expressUrl;

	private List<Long> skuIdList;
	
	private List<OrderItemDTO> orderItemList;
	
	private Integer editable;//是否可修改
	
	private Long orderItemId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeliveryInfoUid() {
		return deliveryInfoUid;
	}

	public void setDeliveryInfoUid(String deliveryInfoUid) {
		this.deliveryInfoUid = deliveryInfoUid;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
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
	
	

	



	public List<ThirdpartyExpressDetailDTO> getThirdpartyList() {
		return thirdpartyList;
	}

	public void setThirdpartyList(List<ThirdpartyExpressDetailDTO> thirdpartyList) {
		this.thirdpartyList = thirdpartyList;
	}







	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}







	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}







	public List<Long> getSkuIdList() {
		return skuIdList;
	}

	public void setSkuIdList(List<Long> skuIdList) {
		this.skuIdList = skuIdList;
	}







	public List<OrderItemDTO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDTO> orderItemList) {
		this.orderItemList = orderItemList;
	}







	public Integer getEditable() {
		return editable;
	}

	public void setEditable(Integer editable) {
		this.editable = editable;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	
	public String getExpressUrl() {
		return expressUrl;
	}

	public void setExpressUrl(String expressUrl) {
		this.expressUrl = expressUrl;
	}
	
}
