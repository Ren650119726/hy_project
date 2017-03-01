package com.mockuai.distributioncenter.core.service.action.record;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.DistributionType;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerOrderDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.TradeManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by duke on 16/5/23.
 */
@Service
public class ListOrderAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ListOrderAction.class);

    @Autowired
    private DistRecordManager distRecordManager;

    @Autowired
    private TradeManager tradeManager;

    @Autowired
    private SellerManager sellerManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        DistRecordQTO distRecordQTO = (DistRecordQTO) request.getParam("distRecordQTO");
        Long userId = (Long) request.getParam("userId");
        String appKey = (String) context.get("appKey");

        long startPoint = System.currentTimeMillis();
        SellerDTO sellerDTO = sellerManager.getByUserId(userId);
        distRecordQTO.setSellerId(sellerDTO.getId());
        List<Long> orderIds = distRecordManager.queryValuableOrderIds(distRecordQTO);
        List<DistRecordDTO> distRecordDTOs;
        if (!orderIds.isEmpty()) {
            distRecordQTO = new DistRecordQTO();
            distRecordQTO.setSellerId(sellerDTO.getId());
            distRecordQTO.setOrderIds(orderIds);
            distRecordDTOs = distRecordManager.query(distRecordQTO);
        } else {
            distRecordDTOs = new ArrayList<DistRecordDTO>();
        }
        long endPoint = System.currentTimeMillis();
        log.info("query seller record, cost: {} ms", endPoint - startPoint);

        // 计算卖家分拥到的金额
        Map<Long, Long> realMap = new HashMap<Long, Long>();
        Map<Long, Long> virtualMap = new HashMap<Long, Long>();

        Map<Long, DistRecordDTO> map = new HashMap<Long, DistRecordDTO>();

        Set<Long> orderIdSet = new HashSet<Long>();
        for (DistRecordDTO distRecordDTO : distRecordDTOs) {
            orderIdSet.add(distRecordDTO.getOrderId());
            map.put(distRecordDTO.getOrderId(), distRecordDTO);

            if (distRecordDTO.getType().equals(DistributionType.REAL_AMOUNT.getType())) {
                // 计算实际分拥金额
                Long totalAmount = realMap.get(distRecordDTO.getOrderId());
                if (totalAmount == null) {
                    totalAmount = 0L;
                }
                totalAmount += distRecordDTO.getDistAmount();
                realMap.put(distRecordDTO.getOrderId(), totalAmount);
            } else {
                // 计算Hi币分拥金额
                Long totalAmount = virtualMap.get(distRecordDTO.getOrderId());
                if (totalAmount == null) {
                    totalAmount = 0L;
                }
                totalAmount += distRecordDTO.getDistAmount();
                virtualMap.put(distRecordDTO.getOrderId(), totalAmount);
            }
        }
        startPoint = System.currentTimeMillis();
        List<OrderDTO> orderDTOs;
        if (!orderIdSet.isEmpty()) {
            OrderQTO orderQTO = new OrderQTO();
            orderQTO.setOrderIds(new ArrayList<Long>(orderIdSet));
            orderDTOs = tradeManager.queryOrder(orderQTO, appKey);
        } else {
            orderDTOs = Collections.EMPTY_LIST;
        }
        endPoint = System.currentTimeMillis();
        log.info("query order, cost: {} ms", endPoint - startPoint);

        List<SellerOrderDTO> sellerOrderDTOs = new ArrayList<SellerOrderDTO>();
        if(orderDTOs == null){
        	 return new DistributionResponse(sellerOrderDTOs);
        }
        for (OrderDTO orderDTO : orderDTOs) {
        	//嗨币支付订单不展示
        	if(orderDTO.getPaymentId() != 12){
        	
	            SellerOrderDTO sellerOrderDTO = new SellerOrderDTO();
	            sellerOrderDTO.setOrderId(orderDTO.getId());
	
	            DistRecordDTO distRecordDTO = map.get(orderDTO.getId());
	            if (distRecordDTO != null) {
	                sellerOrderDTO.setStatus(distRecordDTO.getStatus());
	            }
	            sellerOrderDTO.setOrderTime(orderDTO.getOrderTime());
	
	            Long totalAmount = realMap.get(orderDTO.getId());
	            sellerOrderDTO.setRealDistAmount(totalAmount);
	            totalAmount = virtualMap.get(orderDTO.getId());
	            sellerOrderDTO.setVirtualDistAmount(totalAmount);
	            List<SellerOrderDTO.OrderItem> orderItems = new ArrayList<SellerOrderDTO.OrderItem>();
	            for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
	                SellerOrderDTO.OrderItem orderItem = new SellerOrderDTO.OrderItem();
	                orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
	                orderItem.setItemId(orderItemDTO.getItemId());
	                orderItem.setItemName(orderItemDTO.getItemName());
	                orderItem.setItemImgUrl(orderItemDTO.getItemImageUrl());
	                orderItem.setItemSkuDesc(orderItemDTO.getItemSkuDesc());
	                orderItem.setItemSkuId(orderItemDTO.getItemSkuId());
	                orderItem.setNumber(orderItemDTO.getNumber());
	                orderItem.setHigoMark(orderItemDTO.getHigoMark());
	                orderItems.add(orderItem);
	            }
	            sellerOrderDTO.setOrderItemList(orderItems);
	            sellerOrderDTOs.add(sellerOrderDTO);
        	
        	}
        }
        return new DistributionResponse(sellerOrderDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.LIST_ORDER.getActionName();
    }
}
