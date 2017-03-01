package com.mockuai.distributioncenter.core.service.action.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.DistributeSource;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.AppManager;
import com.mockuai.distributioncenter.core.manager.MessageRecordManager;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.manager.TradeManager;
import com.mockuai.distributioncenter.core.message.msg.OrderUnpaidMsg;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderType;

/**
 * Created by duke on 16/6/2.未支付
 */
@Service
public class FenyongOrderUnpaidAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(FenyongOrderUnpaidAction.class);

    @Autowired
    private MessageRecordManager messageRecordManager;

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private TradeManager tradeManager;

    @Autowired
    private AppManager appManager;

    @Autowired
    private ShopManager shopManager;
    
    
    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
    	 RequestAdapter request = context.getRequestAdapter();
         Long orderId = request.getLong("orderId");
         String appKey = request.getString("appKey");
         Long userId = request.getLong("userId");
        OrderUnpaidMsg orderUnpaidMsg = this.parse(orderId, userId, appKey);

        log.info("start do order unpaid listener action transaction, orderUnpaidMsg: {}", JsonUtil.toJson(orderUnpaidMsg.getDistributionOrderDTOs()));


        List<DistributionOrderDTO> distributionOrderDTOs = orderUnpaidMsg.getDistributionOrderDTOs();
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("distributionOrderDTOs", distributionOrderDTOs);
        baseRequest.setParam("appKey", orderUnpaidMsg.getAppKey());
        baseRequest.setCommand(ActionEnum.DO_DISTRIBUTION.getActionName());
        Response<Boolean> response = distributionService.execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("do distribution error, errMsg: {}", response.getMessage());
            throw new DistributionException(response.getCode(), response.getMessage());
        }
        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.FENYONG_ORDER_UNPAID.getActionName();
    }
    
    
    
    private  OrderUnpaidMsg parse(Long orderId,Long buyerId,String appKey) {

        
        OrderDTO orderDTO;
        try {
            orderDTO = tradeManager.getOrder(orderId, buyerId, appKey);
        } catch (DistributionException e) {
            log.error("get order by orderId: {}, buyerId: {} error", orderId, buyerId);
            return null;
        }

        OrderUnpaidMsg orderUnpaidMsg = new OrderUnpaidMsg();
        orderUnpaidMsg.setAppKey(appKey);
        // 通过分销商ID进行拆单
        // 获得订单中的商品
        Map<Long, DistributionOrderDTO> map = new HashMap<Long, DistributionOrderDTO>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            DistributionOrderDTO distributionOrderDTO = map.get(orderItemDTO.getDistributorId());
            if (distributionOrderDTO == null) {
                distributionOrderDTO = new DistributionOrderDTO();
                distributionOrderDTO.setOrderId(orderId);
                distributionOrderDTO.setOrderSn(orderDTO.getOrderSn());
                distributionOrderDTO.setSellerId(orderItemDTO.getDistributorId());
                try {
                    DistShopDTO shopDTO = shopManager.getBySellerId(distributionOrderDTO.getSellerId());
                    distributionOrderDTO.setShopId(shopDTO.getId());
                } catch (DistributionException e) {
                    log.error("parse unpaid msg - get shop by sellerId: {}, error: {}", distributionOrderDTO.getSellerId(), e.getMessage());
                    return null;
                }
                distributionOrderDTO.setAppKey(appKey);
                distributionOrderDTO.setUserId(buyerId);
                distributionOrderDTO.setItemDTOs(new ArrayList<DistributionItemDTO>());
                map.put(orderItemDTO.getDistributorId(), distributionOrderDTO);
                // 设置分拥来源：正常购买{包括：销售、团队}、开店
                if (orderDTO.getType().equals(EnumOrderType.GIFT_PACK.getCode())) {
                    distributionOrderDTO.setSource(DistributeSource.OPEN_SHOP_DIST.getSource());
                } else {
                    distributionOrderDTO.setSource(DistributeSource.SALE_DIST.getSource());
                }
            }

            DistributionItemDTO distributionItemDTO = new DistributionItemDTO();
            distributionItemDTO.setItemId(orderItemDTO.getItemId());
            distributionItemDTO.setItemSkuId(orderItemDTO.getItemSkuId());
            distributionItemDTO.setNumber(orderItemDTO.getNumber());
            distributionItemDTO.setUnitPrice(orderItemDTO.getUnitPrice());
            distributionOrderDTO.getItemDTOs().add(distributionItemDTO);
        }

        orderUnpaidMsg.setDistributionOrderDTOs(new ArrayList<DistributionOrderDTO>(map.values()));
        log.info("parse unpaid msg: {}", JsonUtil.toJson(orderUnpaidMsg.getDistributionOrderDTOs()));
        return orderUnpaidMsg;
    }
}
