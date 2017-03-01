package com.mockuai.distributioncenter.core.message.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.core.message.msg.*;
import com.mockuai.distributioncenter.core.message.msg.parser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by duke on 15/11/4.
 */
public class TradeMessageListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(TradeMessageListener.class);

    @Autowired
    private PaySuccessMessageParser paySuccessMessageParser;

    @Autowired
    private OrderUnpaidMessageParser orderUnpaidMessageParser;

    @Autowired
    private OrderFinishedMessageParser orderFinishedMessageParser;

    @Autowired
    private RefundMessageParser refundMessageParser;

    @Autowired
    private OrderDeliveryMessageParser orderDeliveryMessageParser;

    @Autowired
    private OrderCancelMessageParser orderCancelMessageParser;

    @Autowired
    private DistributionService distributionService;

    @Override
    public Action consume(Message msg, ConsumeContext context) {
        log.info("receive a message, msgId: {}, key: {},tag:{}", msg.getMsgID(), msg.getKey(),msg.getTag());

        if (msg.getTag().equals("paySuccessNotify")) {

            PaySuccessMsg paySuccessMsg = paySuccessMessageParser.parse(msg);

            if (paySuccessMsg == null) {
                log.error("parse paySuccessMsg error");
                return Action.CommitMessage;
            }
          //首单立减是否分拥
            if(paySuccessMsg.getFilter()){
	            BaseRequest request = new BaseRequest();
	            request.setParam("appKey", paySuccessMsg.getAppKey());
	            request.setParam("paySuccessMsg", paySuccessMsg);
	            request.setCommand(ActionEnum.PAY_SUCCESS_LISTENER.getActionName());
	            Response<Boolean> response = distributionService.execute(request);
	
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
            }else{
            	log.info("orrder type shoudanlijian");
                return Action.CommitMessage;
            }
        }
        else if (msg.getTag().equals("orderUnpaid")) {
            // 解析
            OrderUnpaidMsg orderUnpaidMsg = orderUnpaidMessageParser.parse(msg);

            if (orderUnpaidMsg == null) {
                log.error("parse unpaid msg error");
                return Action.ReconsumeLater;
            }

            //首单立减是否分拥
            if(orderUnpaidMsg.getFilter()){
	            BaseRequest baseRequest = new BaseRequest();
	            baseRequest.setParam("orderUnpaidMsg", orderUnpaidMsg);
	            baseRequest.setParam("appKey", orderUnpaidMsg.getAppKey());
	            baseRequest.setCommand(ActionEnum.ORDER_UNPAID_LISTENER.getActionName());
	            Response<Boolean> response = distributionService.execute(baseRequest);
	            if (!response.isSuccess()) {
	                log.error("do distribution error, errMsg: {}", response.getMessage());
	            }
	            log.info("do distribution successful");
            }else{
            	log.info("orrder type shoudanlijian");
                return Action.CommitMessage;
            }
        }
        else if (msg.getTag().equals("orderFinishedNotify")) {
            // parse message body
            OrderFinishedMsg orderFinishedMsg = orderFinishedMessageParser.parse(msg);

            if (orderFinishedMsg == null) {
                log.error("parse order finished message error");
                return Action.CommitMessage;
            }

            BaseRequest request = new BaseRequest();
            request.setParam("appKey", orderFinishedMsg.getAppKey());
            request.setParam("orderFinishedMsg", orderFinishedMsg);
            request.setCommand(ActionEnum.ORDER_FINISHED_LISTENER.getActionName());

            Response<Boolean> response = distributionService.execute(request);

            if (!response.isSuccess()) {
                log.error("consume order finished message error, errMsg: {}", response.getMessage());
            } else {
                if (response.getModule()) {
                    log.info("consume order finished message successful");
                } else {
                    log.info("consume later");
                    return Action.ReconsumeLater;
                }
            }
        }
        else if (msg.getTag().equals("refundSuccess")) {
            // parse message body
            RefundSuccessMsg refundSuccessMsg = refundMessageParser.parse(msg);
            if (refundSuccessMsg == null) {
                log.error("parse refundSuccessMsg error");
                return Action.CommitMessage;
            }

            BaseRequest request = new BaseRequest();
            request.setParam("refundSuccessMsg", refundSuccessMsg);
            request.setParam("appKey", refundSuccessMsg.getAppKey());
            request.setCommand(ActionEnum.ORDER_REFUND_SUCCESS_LISTENER.getActionName());
            Response<Boolean> response = distributionService.execute(request);
            if (!response.isSuccess()) {
                log.error("consume refund message error, errMsg: {}", response.getMessage());
            } else {
                if (response.getModule()) {
                    log.info("consume refund message successful");
                } else {
                    log.info("consume later");
                    return Action.ReconsumeLater;
                }
            }
        }
        else if (msg.getTag().equals("orderdelivered")) {
            // parse message body
            OrderDeliveryMsg orderDeliveryMsg = orderDeliveryMessageParser.parse(msg);

            BaseRequest request = new BaseRequest();
            request.setParam("orderDeliveryMsg", orderDeliveryMsg);
            request.setParam("appKey", orderDeliveryMsg.getAppKey());
            request.setCommand(ActionEnum.ORDER_DELIVERY_LISTENER.getActionName());
            Response<Boolean> response = distributionService.execute(request);
            if (!response.isSuccess()) {
                log.error("consume delivery message error, errMsg: {}", response.getMessage());
            } else {
                if (response.getModule()) {
                    log.info("consume delivery message successful");
                } else {
                    log.info("consume later");
                    return Action.ReconsumeLater;
                }
            }
        }
        else if (msg.getTag().equals("orderCancel")) {
            OrderCancelMsg orderCancelMsg = orderCancelMessageParser.parse(msg);
            BaseRequest request = new BaseRequest();
            request.setParam("orderCancelMsg", orderCancelMsg);
            request.setParam("appKey", orderCancelMsg.getAppKey());
            request.setCommand(ActionEnum.ORDER_CANCEL_LISTENER.getActionName());
            Response<Boolean> response = distributionService.execute(request);
            if (!response.isSuccess()) {
                log.error("consume order cancel message error, errMsg: {}", response.getMessage());
            } else {
                if (response.getModule()) {
                    log.info("consume order cancel message successful");
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
