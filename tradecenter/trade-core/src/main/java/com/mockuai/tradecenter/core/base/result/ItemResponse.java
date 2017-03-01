package com.mockuai.tradecenter.core.base.result;

import java.util.List;
import java.util.Map;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;

public class ItemResponse extends BaseDTO {
	
	Map<Long, ItemSkuDTO> itemSkuMap;
	
	Map<Long, ItemDTO> itemMap;
	
	List<OrderItemDO> orderItemDOList;

	public Map<Long, ItemSkuDTO> getItemSkuMap() {
		return itemSkuMap;
	}

	public void setItemSkuMap(Map<Long, ItemSkuDTO> itemSkuMap) {
		this.itemSkuMap = itemSkuMap;
	}

	public Map<Long, ItemDTO> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<Long, ItemDTO> itemMap) {
		this.itemMap = itemMap;
	}

	public List<OrderItemDO> getOrderItemDOList() {
		return orderItemDOList;
	}

	public void setOrderItemDOList(List<OrderItemDO> orderItemDOList) {
		this.orderItemDOList = orderItemDOList;
	}
	
	
	

}
