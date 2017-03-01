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
// * 分佣方法余额和嗨币
// * <p/>
// * Created by edgar.zr on 5/13/2016.
// */
//@Component
//public class DistributorGrantListener extends BaseListener {
//    private static final Logger LOGGER = LoggerFactory.getLogger(DistributorGrantListener.class);
//
//    @Override
//    public void consumeMessage(JSONObject msg, String appKey) throws VirtualWealthException {
//
//        LOGGER.debug("{}, appKey : {}", getName(), appKey);
//
//    }
//
//    @Override
//    public Logger getLogger() {
//        return LOGGER;
//    }
//
//    @Override
//    public void init() {
//
//    }
//
//    @Override
//    public String getName() {
//        return RMQMessageType.DISTRIBUTOR_GRANT.combine();
//    }
//}