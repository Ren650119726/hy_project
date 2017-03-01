//package com.mockuai.virtualwealthcenter.core.message.consumer.listener;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
//import com.mockuai.virtualwealthcenter.common.api.Response;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.GrantedWealthStatus;
//import com.mockuai.virtualwealthcenter.common.constant.RMQMessageType;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.message.consumer.BaseListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
///**
// * 订单15天结束后,将嗨币转为入账
// * <p/>
// * Created by edgar.zr on 5/13/2016.
// */
//@Component
//public class UpdateHiCoinStatusOrderFinishedListener extends BaseListener {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateHiCoinStatusOrderFinishedListener.class);
//
//    @Override
//    public void consumeMessage(JSONObject msg, String appKey) throws VirtualWealthException {
//        // TODO 交易消息中需要提供区分是销售还是团队销售类型
//
//        Long orderId = null;
//        Integer sourceType = null;
//
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setCommand(ActionEnum.UPDATE_STATUS_OF_VIRTUAL_WEALTH_DISTRIBUTOR_GRANTED.getActionName());
//        baseRequest.setParam("orderId", orderId);
//        baseRequest.setParam("sourceType", sourceType);
//        baseRequest.setParam("status", GrantedWealthStatus.TRANSFERRED.getValue());
//        baseRequest.setParam("appKey", appKey);
//        Response<Boolean> response = virtualWealthService.execute(baseRequest);
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
//        return RMQMessageType.TRADE_ORDER_FINISHED_NOTIFY.combine();
//    }
//}