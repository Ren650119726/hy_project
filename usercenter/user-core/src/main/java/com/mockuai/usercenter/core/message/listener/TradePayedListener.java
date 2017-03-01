//package com.mockuai.usercenter.core.message.listener;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import com.alibaba.rocketmq.common.message.MessageExt;
//import com.mockuai.tradecenter.common.api.TradeService;
//import com.mockuai.tradecenter.common.domain.OrderDTO;
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.BaseRequest;
//import com.mockuai.usercenter.common.api.Request;
//import com.mockuai.usercenter.common.api.Response;
//import com.mockuai.usercenter.common.api.UserDispatchService;
//import com.mockuai.usercenter.common.constant.BizType;
//import com.mockuai.usercenter.common.dto.MessageRecordDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.manager.AppManager;
//import com.mockuai.usercenter.core.manager.MessageRecordManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * Created by yeliming on 16/5/19.
// */
//public class TradePayedListener extends ConcurrentMessageListener {
//    private static final Logger log = LoggerFactory.getLogger(TradePayedListener.class);
//
//    @Resource
//    private UserDispatchService userDispatchService;
//    @Resource
//    private AppManager appManager;
//    @Resource
//    private MessageRecordManager messageRecordManager;
//    @Resource
//    private TradeService tradeService;
//
//    private String topic;
//    private String tags;
//
//    public void setTopic(String topic) {
//        this.topic = topic;
//    }
//
//    public void setTags(String tags) {
//        this.tags = tags;
//    }
//
//    @Override
//    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//        if (!msgs.isEmpty()) {
//            for (MessageExt msg : msgs) {
//                if (msg.getTopic().equals(topic) && msg.getTags().equals(tags)) {
//                    String body = new String(msg.getBody());
//                    JSONObject jsonObject = JSONObject.parseObject(body);
//
//                    Long orderId = jsonObject.getLong("id");
//                    String bizCode = jsonObject.getString("bizCode");
//                    Long userId = jsonObject.getLong("userId");
//                    Integer type = jsonObject.getInteger("type");
//
//                    if (orderId == null) {
//                        log.error("illegal in coming argument: orderId is null, re consume later");
//                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                    }
//
//                    if (bizCode == null) {
//                        log.error("illegal in coming argument: bizCode is null, re consume later");
//                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                    }
//
//                    if (userId == null) {
//                        log.error("userId is blank, not a legal userId no, bizCode: {}, orderId: {}, re consume later",
//                                bizCode, orderId);
//                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                    }
//
//
//                    try {
//
//                        String appKey = appManager.getAppKeyByBizCode(bizCode);
//                        if (appKey == null) {
//                            log.error("appKey is null, bizCode: {}", bizCode);
//                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                        }
//
//                        MessageRecordDTO messageRecordDTO = this.messageRecordManager.getRecordByIdentifyAndBizType(String.valueOf(orderId), BizType.PAY.getValue());
//                        if (messageRecordDTO != null) {
//                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                        }
//
//                        com.mockuai.tradecenter.common.api.Request orderRequest = new com.mockuai.tradecenter.common.api.BaseRequest();
//                        orderRequest.setParam("orderId", orderId);
//                        orderRequest.setParam("userId", userId);
//                        orderRequest.setParam("appKey", appKey);
//                        orderRequest.setCommand(com.mockuai.tradecenter.common.constant.ActionEnum.GET_ORDER.getActionName());
//
//                        int count = 0;
//                        com.mockuai.tradecenter.common.api.Response<OrderDTO> orderResponse;
//                        do {
//                            orderResponse = this.tradeService.execute(orderRequest);
//                            if (!orderResponse.isSuccess()) {
//                                log.error("get order error: errCode: {}, errMsg: {}", orderResponse.getCode(), orderResponse.getMessage());
//                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//                            }
//
//                            if (orderResponse.getModule().getUserConsigneeDTO() == null) {
//                                log.warn("orderDTO's userConsigneeDTO is null, count = {}", count++);
//                                try {
//                                    Thread.sleep(500);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                break;
//                            }
//                        } while (count < 3);
//
//                        if (orderResponse != null && orderResponse.getModule().getUserConsigneeDTO() == null) {
//                            log.error("orderDTO's userConsigneeDTO is null, consum this message");
//                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                        }
//
//
//                        log.info("MsgId: [{}] [{}:{}] receive a message: {}", msg.getMsgId(), msg.getTopic(), msg.getTags(), body);
//
//                        String uid = orderResponse.getModule().getUserConsigneeDTO().getConsigneeUid();
//                        Long consigneeId = Long.parseLong(uid.split("_")[1]);
//
//                        Request request = new BaseRequest();
//                        request.setParam("consigneeId", consigneeId);
//                        request.setParam("userId", userId);
//                        request.setParam("appKey", appKey);
//                        request.setCommand(ActionEnum.INCREASE_CONSIGNEE_USE_COUNT.getActionName());
//                        Response<Boolean> response = this.userDispatchService.execute(request);
//                        if (!response.isSuccess()) {
//                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//                        }
//
//                        messageRecordDTO = new MessageRecordDTO();
//                        messageRecordDTO.setMsgId(msg.getMsgId());
//                        messageRecordDTO.setIdentify(String.valueOf(orderId));
//                        messageRecordDTO.setBizType(BizType.PAY.getValue());
//                        messageRecordManager.addRecord(messageRecordDTO);
//
//                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//
//                    } catch (UserException e) {
//                        log.error("errCode:{}, errMsg:{}", e.getResponseCode(), e.getMessage());
//                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                    }
//
//                }
//            }
//        }
//        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//    }
//}
