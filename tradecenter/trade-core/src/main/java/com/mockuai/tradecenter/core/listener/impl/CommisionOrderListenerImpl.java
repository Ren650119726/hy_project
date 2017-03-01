package com.mockuai.tradecenter.core.listener.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.CommissionUnitDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.dao.OrderDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.listener.OrderListenerAbstract;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.ShopManager;
import com.mockuai.tradecenter.core.util.TradeUtil;


@Service("commisionOrderListener")
public class CommisionOrderListenerImpl extends OrderListenerAbstract {
	
	@Autowired
	ShopManager shopManager;
	
	@Autowired
	OrderItemManager orderItemManager;
	
	@Autowired
	OrderDAO orderDAO;
	
	//
	@Override
	public void afterSave(OrderTogetherDTO orderTogetherDTO,String appKey) throws TradeException {
		List<CommissionUnitDTO> commissionUnitDTOList = new ArrayList<CommissionUnitDTO>();
		List<OrderItemDO> orderItems = orderTogetherDTO.getItemList();
//		// 查询订单明细
//		OrderItemQTO orderItemQTO = new OrderItemQTO();
//		orderItemQTO.setOrderId(orderDTO.getId());
//		orderItemQTO.setUserId(orderDTO.getUserId());
//		orderItems = this.orderItemManager.queryOrderItem(orderItemQTO);
		if(orderItems==null || orderItems.isEmpty()){
			//TODO error handle
		}else{
			for(OrderItemDO orderItemDO:orderItems){
				if(null!=orderItemDO.getPaymentAmount()&&orderItemDO.getPaymentAmount()>0){
					CommissionUnitDTO commisionUnitDTO = new CommissionUnitDTO();
					commisionUnitDTO.setItemId(orderItemDO.getItemId());
					commisionUnitDTO.setSkuId(orderItemDO.getItemSkuId());
					commisionUnitDTO.setPrice(orderItemDO.getUnitPrice());
					commisionUnitDTO.setNum(orderItemDO.getNumber());
					commisionUnitDTO.setSellerId(orderTogetherDTO.getOrderDO().getSellerId());
					commissionUnitDTOList.add(commisionUnitDTO);
				}
				
			}
		}
		
		List<OrderDiscountInfoDO> orderDiscountDOs = orderTogetherDTO.getOrderDiscountInfoDOs();
		
		//修改订单价格。。。
		Long commission = 0L;
		if(commissionUnitDTOList.isEmpty()==false){
			commission  = shopManager.getOrderCommission(commissionUnitDTOList, appKey);
		}
		
		long mallAllDiscountAmt = TradeUtil.getMallAllDiscountAmt(orderDiscountDOs);
		
		commission-=mallAllDiscountAmt;
		
		if(commission<=0){
			commission = 0L;
		}
		
		OrderDO orderDO = new OrderDO();
		orderDO.setId(orderTogetherDTO.getOrderDO().getId());
		orderDO.setUserId(orderTogetherDTO.getOrderDO().getUserId());
		orderDO.setMallCommission(commission);
		orderDAO.updateOrderCommission(orderDO);
		
	}

}
