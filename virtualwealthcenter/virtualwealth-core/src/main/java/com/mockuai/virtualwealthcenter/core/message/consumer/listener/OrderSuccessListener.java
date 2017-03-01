//package com.mockuai.virtualwealthcenter.core.message.consumer.listener;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mockuai.virtualwealthcenter.common.constant.RMQMessageType;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.message.consumer.BaseListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
///**
// * 订单支付成功
// * <p/>
// * Created by edgar.zr on 11/13/15.
// */
//@Component
//public class OrderSuccessListener extends BaseListener {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSuccessListener.class);
//
////    @Autowired
////    private TradeManager tradeManager;
//
//    @Override
//    public void init() {
//    }
//
//    public String getName() {
//        return RMQMessageType.TRADE_PAY_SUCCESS_NOTIFY.combine();
//    }
//
//    @Override
//    public void consumeMessage(JSONObject msg, String appKey) throws VirtualWealthException {
//
//        LOGGER.info("{}, appKey : {}", getName(), appKey);
//        // 处理虚拟财富充值支付成功的消息
//        getLogger().info("msg: {}", msg.toJSONString());
//        Long id = msg.getLong("id");
//        Integer type = msg.getInteger("type");
//        Long userId = msg.getLong("userId");
//
//        // 只消费正常的订单，订单类型为1
//        if (type == null || type != 11) {
//            return;
//        }
//
//        // 获得订单对象
////        OrderDTO orderDTO = tradeManager.getOrder(id, userId, appKey);
////
////        if (orderDTO == null) {
////            getLogger().error("order is null, id: {}, userId: {}", id, userId);
////            return;
////        }
//
//        // 获得商品ID
////        if (orderDTO.getOrderItems().isEmpty()) {
////            getLogger().error("orderItems is empty, orderId: {}", id);
////            return;
////        }
////
////        OrderItemDTO orderItemDTO = orderDTO.getOrderItems().get(0);
////
////        if (orderItemDTO.getItemId() == null) {
////            LOGGER.error("item id is null when parse the order pay success msg, orderSn: {}", orderDTO.getOrderSn());
////            return;
////        }
////        RechargeRecordDTO rechargeRecordDTO = new RechargeRecordDTO();
////        rechargeRecordDTO.setOrderId(id);
////        rechargeRecordDTO.setOrderSn(orderDTO.getOrderSn());
////        rechargeRecordDTO.setUserId(userId);
////        rechargeRecordDTO.setPaymentId(orderDTO.getPaymentId());
////        rechargeRecordDTO.setItemId(orderItemDTO.getItemId());
////
////        BaseRequest baseRequest = new BaseRequest();
////        baseRequest.setParam("rechargeRecordDTO", rechargeRecordDTO);
////        baseRequest.setParam("appKey", appKey);
////        baseRequest.setCommand(ActionEnum.ADD_RECHARGE_RECORD.getActionName());
////        Response<RechargeRecordDTO> response = virtualWealthService.execute(baseRequest);
////        if (!response.isSuccess()) {
////            LOGGER.error("add rechargeOrder error, errMsg: {}", response.getMessage());
////            throw new VirtualWealthException(response.getResCode(), response.getMessage());
////        }
//    }
//
//    @Override
//    public Logger getLogger() {
//        return this.LOGGER;
//    }
//}