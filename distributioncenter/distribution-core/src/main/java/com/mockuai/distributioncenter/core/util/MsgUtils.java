//package com.mockuai.distributioncenter.core.util;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.rocketmq.common.message.MessageExt;
//import com.mockuai.distributioncenter.core.exception.DistributionException;
//import com.mockuai.distributioncenter.core.manager.AppManager;
//import com.mockuai.distributioncenter.core.manager.TradeManager;
//import com.mockuai.distributioncenter.core.message.msg.PaySuccessMsg;
//import com.mockuai.distributioncenter.core.message.msg.RefundSuccessMsg;
//import com.mockuai.tradecenter.common.domain.OrderDTO;
//import com.mockuai.tradecenter.common.domain.OrderItemDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by duke on 16/3/11.
// */
//public class MsgUtils {
//    private static final Logger log = LoggerFactory.getLogger(MsgUtils.class);
//
//    public static PaySuccessMsg parsePaySuccessMsg(MessageExt msg, TradeManager tradeManager, AppManager appManager) throws DistributionException {
//        JSONObject jsonObject = JSONObject.parseObject(new String(msg.getBody(), Charset.forName("UTF-8")));
//        String bizCode = jsonObject.getString("bizCode");
//        String code = jsonObject.getString("attachInfo");
//        Long buyerId = jsonObject.getLong("userId");
//        Long orderId = jsonObject.getLong("id");
//
//        String appKey = appManager.getAppKeyByBizCode(bizCode);
//        OrderDTO orderDTO = tradeManager.getOrder(orderId, buyerId, appKey);
//        PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
//        paySuccessMsg.setAppKey(appKey);
//        paySuccessMsg.setBizCode(bizCode);
//        paySuccessMsg.setBuyerId(orderDTO.getUserId());
//        paySuccessMsg.setMsg(msg);
//        paySuccessMsg.setOrderId(orderId);
//        paySuccessMsg.setOrderNo(orderDTO.getOrderSn());
//        paySuccessMsg.setPayTime(orderDTO.getPayTime() == null ? new Date() : orderDTO.getPayTime());
//        paySuccessMsg.setSellerId(orderDTO.getSellerId());
//        paySuccessMsg.setType(orderDTO.getType());
//        paySuccessMsg.setPayType(orderDTO.getPaymentId());
//        paySuccessMsg.setOriginInvitationCode(code);
//        Long totalAmount = 0L;
//
//        List<PaySuccessMsg.Item> items = new ArrayList<PaySuccessMsg.Item>();
//        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
//            PaySuccessMsg.Item item = new PaySuccessMsg.Item();
//            item.setItemId(orderItemDTO.getItemId());
//            item.setItemImgUrl(orderItemDTO.getItemImageUrl());
//            item.setItemName(orderItemDTO.getItemName());
//            item.setItemSkuDesc(orderItemDTO.getItemSkuDesc());
//            item.setItemSkuId(orderItemDTO.getItemSkuId());
//            item.setNumber(orderItemDTO.getNumber());
//            item.setUnitPrice(orderItemDTO.getUnitPrice());
//            item.setCategoryId(orderItemDTO.getCategoryId());
//            item.setItemType(orderItemDTO.getItemType());
//            totalAmount += item.getUnitPrice() * item.getNumber();
//
//            if (item.getItemType() != null && item.getItemType() == 11) {
//                List<PaySuccessMsg.Item> subItemList = new ArrayList<PaySuccessMsg.Item>();
//                for (OrderItemDTO dto : orderItemDTO.getItemList()) {
//                    PaySuccessMsg.Item subItem = new PaySuccessMsg.Item();
//                    subItem.setItemId(dto.getItemId());
//                    subItem.setItemImgUrl(dto.getItemImageUrl());
//                    subItem.setItemName(dto.getItemName());
//                    subItem.setItemSkuDesc(dto.getItemSkuDesc());
//                    subItem.setItemSkuId(dto.getItemSkuId());
//                    subItem.setNumber(dto.getNumber());
//                    subItem.setUnitPrice(dto.getUnitPrice());
//                    subItem.setCategoryId(dto.getCategoryId());
//                    totalAmount += subItem.getUnitPrice() * subItem.getNumber();
//                    subItemList.add(subItem);
//                }
//                item.setSubItems(subItemList);
//            }
//            items.add(item);
//        }
//        paySuccessMsg.setItems(items);
//        paySuccessMsg.setTotalAmount(totalAmount);
//        return paySuccessMsg;
//    }
//
//    public static Boolean validatePaySuccessMsg(PaySuccessMsg msg) {
//        if (msg.getType() == null) {
//            log.error("validate pay success msg error, type is null");
//            return false;
//        }
//
//        if (msg.getOrderNo() == null) {
//            log.error("validate pay success msg error, orderNo is null");
//            return false;
//        }
//
//        if (msg.getTotalAmount() == null) {
//            log.error("validate pay success msg error, total amount is null");
//            return false;
//        }
//
//        if (msg.getOrderId() == null) {
//            log.error("validate pay success msg error, orderId is null");
//            return false;
//        }
//
//        if (msg.getBizCode() == null) {
//            log.error("validate pay success msg error, bizCode is null");
//            return false;
//        }
//
//        if (msg.getSellerId() == null) {
//            log.error("validate pay success msg error, sellerId is null");
//            return false;
//        }
//
//        if (msg.getBuyerId() == null) {
//            log.error("validate pay success msg error, buyerId is null");
//            return false;
//        }
//        return true;
//    }
//
//    public static RefundSuccessMsg parseRefundSuccessMsg(MessageExt msg) {
//        RefundSuccessMsg refundSuccessMsg = new RefundSuccessMsg();
//        JSONObject jsonObject = JSONObject.parseObject(new String(msg.getBody(), Charset.forName("UTF-8")));
//        String bizCode = jsonObject.getString("bizCode");
//        Long itemId = jsonObject.getLong("itemId");
//        Long itemSkuId = jsonObject.getLong("itemSkuId");
//        Long refundOrderId = jsonObject.getLong("id");
//        Long orderId = jsonObject.getLong("orderId");
//        Integer number = jsonObject.getInteger("number");
//        Long unitPrice = jsonObject.getLong("unitPrice");
//
//        refundSuccessMsg.setMsg(msg);
//        refundSuccessMsg.setBizCode(bizCode);
//        refundSuccessMsg.setItemId(itemId);
//        refundSuccessMsg.setItemSkuId(itemSkuId);
//        refundSuccessMsg.setNumber(number);
//        refundSuccessMsg.setOrderId(orderId);
//        refundSuccessMsg.setUnitPrice(unitPrice);
//        refundSuccessMsg.setRefundOrderId(refundOrderId);
//
//        // TODO validate
//
//        return refundSuccessMsg;
//    }
//}
