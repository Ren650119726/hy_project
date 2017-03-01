package com.mockuai.headsinglecenter.core.message.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mockuai.headsinglecenter.common.api.BaseRequest;
import com.mockuai.headsinglecenter.common.api.HeadSingleService;
import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.common.constant.ActionEnum;
import com.mockuai.headsinglecenter.core.message.msg.PaySuccessMsg;
import com.mockuai.headsinglecenter.core.message.msg.parser.PaySuccessMessageParser;

/**
 * Created by csy on 15/11/4.
 */
public class HeadSingleMessageListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(HeadSingleMessageListener.class);

    @Resource
    private PaySuccessMessageParser paySuccessMessageParser;
	@SuppressWarnings("rawtypes")
	@Resource
	private HeadSingleService headSingleService;

    @SuppressWarnings("unchecked")
	@Override
    public Action consume(Message msg, ConsumeContext context) {
        log.info("receive a message, msgId: {}, key: {}", msg.getMsgID(), msg.getKey());
        
        //只接受下订单、支付成功两种消息
        if(!msg.getTag().equals("paySuccessNotify") && !msg.getTag().equals("orderUnpaid")){
        	return Action.CommitMessage;
        }
        
        //初始化数据
        PaySuccessMsg paySuccessMsg = paySuccessMessageParser.parse(msg);

        if (paySuccessMsg == null) {
            log.error("parse paySuccessMsg error");
            return Action.CommitMessage;
        }
        
        BaseRequest request = new BaseRequest();
        request.setParam("appKey", paySuccessMsg.getAppKey());
        request.setParam("paySuccessMsg", paySuccessMsg);
        
        //接受tag消息类型
        if (msg.getTag().equals("paySuccessNotify")) {//接受支付成功消息     	
            request.setCommand(ActionEnum.PAY_SUCCESS_LISTENER.getActionName());
            Response<Boolean> response = headSingleService.execute(request);

            if (!response.isSuccess()) {
                log.error("consume paySuccess message error, errMsg: {}", response.getMessage());
            } else {
                if (response.getModule()) {
                    log.info("consume paySuccess message successful");
                } else {
                    log.info("consume later");
                    return Action.ReconsumeLater;
                }
            }
        } else if(msg.getTag().equals("orderUnpaid")){//接受下订单消息
        	request.setCommand(ActionEnum.ORDER_UNPAID_SUCCESS_LISTENER.getActionName());
            Response<Boolean> response = headSingleService.execute(request);

            if (!response.isSuccess()) {
                log.error("consume orderUnpaid message error, errMsg: {}", response.getMessage());
            } else {
                if (response.getModule()) {
                    log.info("consume orderUnpaid message successful");
                } else {
                    log.info("consume later");
                    return Action.ReconsumeLater;
                }
            }
        } else {
            log.info("no tags are matched, msgId: {}, topic: {}, tags: {}", msg.getMsgID(), msg.getTopic(), msg.getTag());
        }
        
        return Action.CommitMessage;
    }
}