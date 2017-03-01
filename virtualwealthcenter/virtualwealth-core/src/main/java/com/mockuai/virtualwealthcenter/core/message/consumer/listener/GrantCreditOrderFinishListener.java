//package com.mockuai.virtualwealthcenter.core.message.consumer.listener;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
//import com.mockuai.virtualwealthcenter.common.api.Request;
//import com.mockuai.virtualwealthcenter.common.api.Response;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.RMQMessageType;
//import com.mockuai.virtualwealthcenter.common.constant.SourceType;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.message.consumer.BaseListener;
//import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
///**
// * 接收订单成功结束消息
// * 15 天后
// * <p/>
// * Created by edgar.zr on 11/13/15.
// */
//@Component
//public class GrantCreditOrderFinishListener extends BaseListener {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(GrantCreditOrderFinishListener.class);
//
//    @Override
//    public void init() {
//    }
//
//    public String getName() {
//        return RMQMessageType.TRADE_ORDER_FINISHED_NOTIFY.combine();
//    }
//
//    @Override
//    public void consumeMessage(JSONObject msg, String appKey) throws VirtualWealthException {
//
//        LOGGER.debug("{}, appKey : {}", getName(), appKey);
//
//        String bizCode = msg.getString("bizCode");
//        Long senderId = msg.getLong("sellerId");
//        Long userId = msg.getLong("userId");
//        // 订单中商品的市场价格
//        Long totalPrice = msg.getLong("totalPrice");
//        // 订单实际支付金额
//        Long totalAmount = msg.getLong("totalAmount");
//        Long id = msg.getLong("id");
//
//        try {
//
//            Request request = new BaseRequest();
//
//            GrantedWealthDTO grantedWealthDTO = new GrantedWealthDTO();
//            grantedWealthDTO.setBizCode(bizCode);
//            grantedWealthDTO.setReceiverIdList(new ArrayList<Long>());
//            grantedWealthDTO.getReceiverIdList().add(userId);
//            grantedWealthDTO.setOrderId(id);
//            grantedWealthDTO.setGranterId(senderId);
//            // 判定条件
//            grantedWealthDTO.setBaseAmount(totalAmount);
//            grantedWealthDTO.setSourceType(SourceType.ORDER_PAY.getValue());
//            grantedWealthDTO.setWealthType(WealthType.CREDIT.getValue());
//
//            request.setParam("grantedWealthDTO", grantedWealthDTO);
//            request.setParam("senderId", senderId);
//            request.setParam("appKey", appKey);
//            request.setCommand(ActionEnum.GRANT_VIRTUAL_WEALTH_WITH_GRANT_RULE.getActionName());
//            Response<Boolean> response = virtualWealthService.execute(request);
//            LOGGER.info("grant wealth, response : {}", JsonUtil.toJson(response));
//
//        } catch (Exception e) {
//            LOGGER.error("error to consumeByCredit, {}, {}", msg.toJSONString(), appKey);
//        }
//    }
//
//    @Override
//    public Logger getLogger() {
//        return this.LOGGER;
//    }
//}