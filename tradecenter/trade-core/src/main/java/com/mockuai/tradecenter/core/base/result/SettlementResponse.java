package com.mockuai.tradecenter.core.base.result;

import java.util.List;
import java.util.Map;

import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;

public class SettlementResponse extends BaseDTO {
	
	List<OrderDiscountInfoDO> orderDiscountInfoDOList;
	
	Long deliveryFee;
	
	Map<Long,ActivityItemDTO> activityItemMap;
	
	List<OrderItemDTO> giftOrderItemDTOList;
	

	public List<OrderItemDTO> getGiftOrderItemDTOList() {
		return giftOrderItemDTOList;
	}

	public void setGiftOrderItemDTOList(List<OrderItemDTO> giftOrderItemDTOList) {
		this.giftOrderItemDTOList = giftOrderItemDTOList;
	}

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

	public Map<Long, ActivityItemDTO> getActivityItemMap() {
		return activityItemMap;
	}

	public void setActivityItemMap(Map<Long, ActivityItemDTO> activityItemMap) {
		this.activityItemMap = activityItemMap;
	}
	
	
	
	

}
