package com.mockuai.tradecenter.core.base.result;

import java.util.List;
import java.util.Map;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;

public class StoreResponse extends BaseDTO {
	
	List<OrderDiscountInfoDO> orderDiscountInfoDOList;
	
	Long deliveryFee;

	public List<OrderDiscountInfoDO> getOrderDiscountInfoDOList() {
		return orderDiscountInfoDOList;
	}

	public void setOrderDiscountInfoDOList(List<OrderDiscountInfoDO> orderDiscountInfoDOList) {
		this.orderDiscountInfoDOList = orderDiscountInfoDOList;
	}

	public Long getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Long deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	
	
	
	

}
