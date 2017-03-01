package com.mockuai.distributioncenter.core.service.action.record;

import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerOrderDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.*;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.common.domain.dto.ShopDTO;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderTrackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by duke on 16/5/23.
 */
@Service
public class GetOrderDetailAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetOrderDetailAction.class);

    @Autowired
    private DistRecordManager distRecordManager;

    @Autowired
    private DeliveryInfoManager deliveryInfoManager;

    @Autowired
    private TradeManager tradeManager;

    @Autowired
    private ShopManager shopManager;

    @Autowired
    private SellerManager sellerManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Long orderId = (Long) request.getParam("orderId");
        Long userId = (Long) request.getParam("userId");
        String appKey = (String) context.get("appKey");

        // 获得卖家信息
        SellerDTO sellerDTO = sellerManager.getByUserId(userId);

        DistRecordQTO distRecordQTO = new DistRecordQTO();
        distRecordQTO.setOrderId(orderId);
        distRecordQTO.setSellerId(sellerDTO.getId());
        List<DistRecordDTO> distRecordDTOs = distRecordManager.query(distRecordQTO);

        SellerOrderDTO sellerOrderDTO = null;
        if (distRecordDTOs.isEmpty()) {
            return new DistributionResponse(sellerOrderDTO);
        }

        sellerOrderDTO = new SellerOrderDTO();
        sellerOrderDTO.setStatus(distRecordDTOs.get(0).getStatus());

        OrderDTO orderDTO = tradeManager.getOrder(orderId, distRecordDTOs.get(0).getBuyerId(), appKey);
        if (orderDTO == null) {
            log.error("order not exists, orderId: {}", orderId);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "order not exists");
        }

        Set<Long> sellerIds = new HashSet<Long>();
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            sellerIds.add(itemDTO.getDistributorId());
        }

        DistShopQTO shopQTO = new DistShopQTO();
        shopQTO.setSellerIds(new ArrayList<Long>(sellerIds));
        List<DistShopDTO> shopDTOs = shopManager.query(shopQTO);

        Map<Long, DistShopDTO> shopMap = new HashMap<Long, DistShopDTO>();
        for (DistShopDTO shopDTO : shopDTOs) {
            shopMap.put(shopDTO.getSellerId(), shopDTO);
        }


        sellerOrderDTO.setOrderId(orderDTO.getId());
        sellerOrderDTO.setOrderSn(orderDTO.getOrderSn());
        List<SellerOrderDTO.OrderItem> orderItems = new ArrayList<SellerOrderDTO.OrderItem>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            SellerOrderDTO.OrderItem orderItem = new SellerOrderDTO.OrderItem();
            orderItem.setItemName(orderItemDTO.getItemName());
            orderItem.setItemSkuId(orderItemDTO.getItemSkuId());
            orderItem.setItemId(orderItemDTO.getItemId());
            orderItem.setItemSkuDesc(orderItemDTO.getItemSkuDesc());
            orderItem.setItemImgUrl(orderItemDTO.getItemImageUrl());
            orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
            orderItem.setNumber(orderItemDTO.getNumber());
            // 获得这个订单的原始卖家
            DistShopDTO shopDTO = shopMap.get(orderItemDTO.getDistributorId());
            orderItem.setShopName(shopDTO.getShopName());
            orderItem.setShopId(shopDTO.getId());
            orderItem.setSellerId(shopDTO.getSellerId());
            orderItem.setHigoMark(orderItemDTO.getHigoMark());
            orderItem.setItemUid(orderItemDTO.getSellerId() + "_" + orderItemDTO.getItemId());
            orderItems.add(orderItem);
        }
        sellerOrderDTO.setOrderItemList(orderItems);

        // 获得物流信息
        DeliveryInfoQTO deliveryInfoQTO = new DeliveryInfoQTO();
        deliveryInfoQTO.setOrderId(orderId);
        deliveryInfoQTO.setUserId(distRecordDTOs.get(0).getBuyerId());
        long startTimePoint = System.currentTimeMillis();
        List<DeliveryInfoDTO> deliveryInfoDTOs = deliveryInfoManager.queryDeliveryInfo(deliveryInfoQTO, appKey);
        long endTimePoint = System.currentTimeMillis();
        log.info("query delivery info cost :{} ms", endTimePoint - startTimePoint);
        if (deliveryInfoDTOs != null && !deliveryInfoDTOs.isEmpty()) {
            SellerOrderDTO.DeliveryInfo deliveryInfo = new SellerOrderDTO.DeliveryInfo();
            deliveryInfo.setDeliveryCompany(deliveryInfoDTOs.get(0).getExpress());
            sellerOrderDTO.setDeliveryInfo(deliveryInfo);
        }

        // 获得收货人信息
        OrderConsigneeDTO consigneeDTO = orderDTO.getOrderConsigneeDTO();
        SellerOrderDTO.ConsigneeInfo consigneeInfo = new SellerOrderDTO.ConsigneeInfo();
        consigneeInfo.setAddress(consigneeDTO.getAddress());
        consigneeInfo.setConsignee(consigneeDTO.getConsignee());
        consigneeInfo.setAreaName(consigneeDTO.getArea());
        consigneeInfo.setCityName(consigneeDTO.getCity());
        consigneeInfo.setMobile(consigneeDTO.getMobile());
        consigneeInfo.setProvinceName(consigneeDTO.getProvince());
        consigneeInfo.setIdCardId(consigneeDTO.getIdCardNo());
        sellerOrderDTO.setConsigneeInfo(consigneeInfo);
        log.info("get order id: {} consignee: {}", orderDTO.getId(), JsonUtil.toJson(consigneeDTO));

        // 获得跟踪信息
        sellerOrderDTO.setOrderTime(orderDTO.getOrderTime());
        sellerOrderDTO.setPayTime(orderDTO.getPayTime());
        sellerOrderDTO.setDeliveryTime(orderDTO.getConsignTime());
        sellerOrderDTO.setConfirmTime(orderDTO.getReceiptTime());
        log.info("sellerOrderDTO: {}", JsonUtil.toJson(sellerOrderDTO));
        // sellerOrderDTO.setShutdownTime();
        /*
        List<OrderTrackDTO> orderTrackDTOs = tradeManager.queryOrderTrack(orderId, distRecordDTOs.get(0).getBuyerId(), appKey);
        if (orderTrackDTOs != null) {
            log.info("query order trace: {}", JsonUtil.toJson(orderTrackDTOs));
            for (OrderTrackDTO orderTrackDTO : orderTrackDTOs) {
                if (orderTrackDTO.getOrderStatus().equals(TradeConstants.Order_Status.UNPAID)) {
                    sellerOrderDTO.setOrderTime(orderTrackDTO.getOperateTime());
                } else if (orderTrackDTO.getOrderStatus().equals(TradeConstants.Order_Status.PAID)) {
                    sellerOrderDTO.setPayTime(orderTrackDTO.getOperateTime());
                } else if (orderTrackDTO.getOrderStatus().equals(TradeConstants.Order_Status.DELIVERIED)) {
                    sellerOrderDTO.setDeliveryTime(orderTrackDTO.getOperateTime());
                } else if (orderTrackDTO.getOrderStatus().equals(TradeConstants.Order_Status.SIGN_OFF)) {
                    sellerOrderDTO.setConfirmTime(orderTrackDTO.getOperateTime());
                } else if (orderTrackDTO.getOrderStatus().equals(TradeConstants.Order_Status.FINISHED)) {
                    sellerOrderDTO.setShutdownTime(orderTrackDTO.getOperateTime());
                }
            }
        } else {
            log.info("no order trace, order: {}, buyerId: {}", orderId, distRecordDTOs.get(0).getBuyerId());
        }
        */
        return new DistributionResponse(sellerOrderDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ORDER_DETAIL.getActionName();
    }
}
