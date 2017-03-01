package com.mockuai.usercenter.core.message.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.mockuai.usercenter.common.constant.RoleType;
import com.mockuai.usercenter.common.dto.MessageRecordDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.AppManager;
import com.mockuai.usercenter.core.manager.MessageRecordManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by csy on 16/5/19.
 */
public class UpdateUserPayedMessageListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserPayedMessageListener.class);

    @Resource
    private UserDispatchService userDispatchService;
    @Resource
    private AppManager appManager;
    @Resource
    private MessageRecordManager messageRecordManager;
    @Resource
    private TradeService tradeService;

    @SuppressWarnings("unchecked")
	@Override
    public Action consume(Message message, ConsumeContext context) {
        if(!message.getTag().equals("paySuccessNotify")){
        	return Action.CommitMessage;
        } 
    	
        String body = new String(message.getBody());
        try {       	
            JSONObject jsonObject = JSON.parseObject(body);
            Long orderId = jsonObject.getLong("id");
            String bizCode = jsonObject.getString("bizCode");
            Long userId = jsonObject.getLong("userId");

            if (orderId == null) {
                log.error("illegal in coming argument: orderId is null, consume this message");
                return Action.CommitMessage;
            }

            if (bizCode == null) {
                log.error("illegal in coming argument: bizCode is null, consume this message");
                return Action.CommitMessage;
            }

            if (userId == null) {
                log.error("userId is blank, bizCode: {}, orderId: {}", bizCode, orderId);
                return Action.CommitMessage;
            }

            try {
                String appKey = appManager.getAppKeyByBizCode(bizCode);
                if (appKey == null) {
                    log.error("appKey is null, bizCode: {}", bizCode);
                    return Action.CommitMessage;
                }

                MessageRecordDTO messageRecordDTO = this.messageRecordManager.getRecordByIdentifyAndBizType(String.valueOf(orderId), BizType.USER_PAY.getValue());
                if (messageRecordDTO != null) {
                    return Action.CommitMessage;
                }
                
                log.info("MsgId: [{}] [{}:{}] receive a message: {}", message.getMsgID(), message.getTopic(), message.getKey(), body);

                com.mockuai.tradecenter.common.api.Request orderRequest = new com.mockuai.tradecenter.common.api.BaseRequest();
                orderRequest.setParam("orderId", orderId);
                orderRequest.setParam("userId", userId);
                orderRequest.setParam("appKey", appKey);
                orderRequest.setCommand(com.mockuai.tradecenter.common.constant.ActionEnum.GET_ORDER.getActionName());
                com.mockuai.tradecenter.common.api.Response<OrderDTO> orderResponse = this.tradeService.execute(orderRequest);
                
				if (!orderResponse.isSuccess()) {
					log.error("get order error: errCode: {}, errMsg: {}", orderResponse.getCode(), orderResponse.getMessage());
					return Action.ReconsumeLater;
				}              

                Request request = new BaseRequest();
                
                request.setParam("userId", userId);
                request.setParam("roleType", RoleType.HIKECONDITION.getValue());
                request.setParam("appKey", appKey);
                request.setCommand(ActionEnum.UPDATE_ROLE_TYPE.getActionName());
                Response<Boolean> response = this.userDispatchService.execute(request);
                                
                if (!response.isSuccess()) {
                    return Action.ReconsumeLater;
                }

                messageRecordDTO = new MessageRecordDTO();
                messageRecordDTO.setMsgId(message.getMsgID());
                messageRecordDTO.setIdentify(String.valueOf(orderId));
                messageRecordDTO.setBizType(BizType.USER_PAY.getValue());
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
