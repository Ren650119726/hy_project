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
// * 订单取消更新用户换购记录
// * Created by edgar.zr on 11/13/15.
// */
//@Component
//public class OrderCancelListener extends BaseListener {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCancelListener.class);
//
//
//    @Override
//    public void init() {
//    }
//
//    public String getName() {
//        return RMQMessageType.TRADE_ORDER_CANCEL.combine();
//    }
//
//    @Override
//    public void consumeMessage(JSONObject msg, String appKey) throws VirtualWealthException {
//
//        LOGGER.info("{}, appKey : {}", getName(), appKey);
//
//    }
//
//    @Override
//    public Logger getLogger() {
//        return this.LOGGER;
//    }
//}