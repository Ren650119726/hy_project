package com.mockuai.deliverycenter.common.qto.express;

import java.io.Serializable;

import com.mockuai.deliverycenter.common.qto.BaseQTO;
/**
 *  物流信息查询条件类
 * @author cwr
 */
public class DeliveryInfoQTO extends BaseQTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4242512267417355815L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	private Long id;	
	
	private Long orderId;
	
	private Long userId;
	
	private String express;
	
	private String expressNo;
	
}
