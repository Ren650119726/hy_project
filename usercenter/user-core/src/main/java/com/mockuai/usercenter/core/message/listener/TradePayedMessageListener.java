package com.mockuai.usercenter.core.message.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.constant.BizType;
import com.mockuai.usercenter.common.dto.MessageRecordDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.AppManager;
import com.mockuai.usercenter.core.manager.MessageRecordManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/19.
 */
public class TradePayedMessageListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(TradePayedMessageListener.class);

    @Resource
    private UserDispatchService userDispatchService;
    @Resource
    private AppManager appManager;
    @Resource
    private MessageRecordManager messageRecordManager;
    @Resource
    private TradeService tradeService;

    @Override
    public Action consume(Message message, ConsumeContext context) {
        String body = new String(message.getBody());
        try {
            JSONObject jsonObject = JSON.parseObject(body);
            Long orderId = jsonObject.getLong("id");
            String bizCode = jsonObject.getString("bizCode");
            Long userId = jsonObject.getLong("userId");
            Integer type = jsonObject.getInteger("type");
            
            log.info("测试messageTradePayedMessageListener orderId"+orderId);
            log.info("测试messageTradePayedMessageListener bizCode"+bizCode);
            log.info("测试messageTradePayedMessageListener userId"+userId);

            if (orderId == null) {
                log.error("illegal in coming argument: orderId is null, consume this message");
                return Action.CommitMessage;
            }

            if (bizCode == null) {
                log.error("illegal in coming argument: bizCode is null, consume this message");
                return Action.CommitMessage;
            }

            if (userId == null) {
                log.error("userId is blank, not a legal userId no, bizCode: {}, orderId: {}, consume this message",
                        bizCode, orderId);
                return Action.CommitMessage;
            }

            try {

                String appKey = appManager.getAppKeyByBizCode(bizCode);
                if (appKey == null) {
                    log.error("appKey is null, bizCode: {}", bizCode);
                    return Action.CommitMessage;
                }

                MessageRecordDTO messageRecordDTO = this.messageRecordManager.getRecordByIdentifyAndBizType(String.valueOf(orderId), BizType.PAY.getValue());
                if (messageRecordDTO != null) {
                    return Action.CommitMessage;
                }

                log.info("MsgId: [{}] [{}:{}] receive a message: {}", message.getMsgID(), message.getTopic(), message.getKey(), body);

                com.mockuai.tradecenter.common.api.Request orderRequest = new com.mockuai.tradecenter.common.api.BaseRequest();
                orderRequest.setParam("orderId", orderId);
                orderRequest.setParam("userId", userId);
                orderRequest.setParam("appKey", appKey);
                orderRequest.setCommand(com.mockuai.tradecenter.common.constant.ActionEnum.GET_ORDER.getActionName());

                int count = 0;
                com.mockuai.tradecenter.common.api.Response<OrderDTO> orderResponse;
                do {
                    orderResponse = this.tradeService.execute(orderRequest);
                    if (!orderResponse.isSuccess()) {
                        log.error("get order error: errCode: {}, errMsg: {}", orderResponse.getCode(), orderResponse.getMessage());
                        return Action.ReconsumeLater;
                    }

                    if (orderResponse.getModule().getUserConsigneeDTO() == null) {
                        log.warn("orderDTO's userConsigneeDTO is null, count = {}", count++);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                } while (count < 3);

                if (orderResponse != null && orderResponse.getModule().getUserConsigneeDTO() == null) {
                    log.error("orderDTO's userConsigneeDTO is null, consume this message");
                    return Action.CommitMessage;
                }



                String uid = orderResponse.getModule().getUserConsigneeDTO().getConsigneeUid();
                Long consigneeId = Long.parseLong(uid.split("_")[1]);

                Request request = new BaseRequest();
                request.setParam("consigneeId", consigneeId);
                request.setParam("userId", userId);
                request.setParam("appKey", appKey);
                request.setCommand(ActionEnum.INCREASE_CONSIGNEE_USE_COUNT.getActionName());
                Response<Boolean> response = this.userDispatchService.execute(request);
                if (!response.isSuccess()) {
                    log.error("");
                    return Action.ReconsumeLater;
                }

                messageRecordDTO = new MessageRecordDTO();
                messageRecordDTO.setMsgId(message.getMsgID());
                messageRecordDTO.setIdentify(String.valueOf(orderId));
                messageRecordDTO.setBizType(BizType.PAY.getValue());
                messageRecordManager.addRecord(messageRecordDTO);

                return Action.CommitMessage;

            } catch (UserException e) {
                log.error("errCode:{}, errMsg:{}", e.getResponseCode(), e.getMessage());
                return Action.CommitMessage;
            }

        } catch (Exception e) {
            log.error("TradePayedMessage consum error, errMsg:{}", e.getMessage());
            return Action.CommitMessage;
        }
    }
}
