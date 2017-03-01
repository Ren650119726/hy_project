package com.mockuai.distributioncenter.core.service.action.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.BizType;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.MessageRecordDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.MessageRecordManager;
import com.mockuai.distributioncenter.core.message.msg.OrderUnpaidMsg;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.distributioncenter.core.util.JsonUtil;

/**
 * Created by duke on 16/6/2.未支付
 */
@Service
public class OrderUnpaidListenerAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(OrderUnpaidListenerAction.class);

    @Autowired
    private MessageRecordManager messageRecordManager;

    @Autowired
    private DistributionService distributionService;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        RequestAdapter request = new RequestAdapter(context.getRequest());
        String appKey = (String) context.get("appKey");
        OrderUnpaidMsg orderUnpaidMsg = (OrderUnpaidMsg) request.getObject("orderUnpaidMsg");

        log.info("start do order unpaid listener action transaction, orderUnpaidMsg: {}", JsonUtil.toJson(orderUnpaidMsg.getDistributionOrderDTO()));

        Message msg = orderUnpaidMsg.getMsg();
        // 检查消息是否别消费过，消息去重
        MessageRecordDTO messageRecordDTO = messageRecordManager.getRecordByIdentifyAndBizType(
                String.valueOf(orderUnpaidMsg.getDistributionOrderDTO().getOrderId()), BizType.TRADE_ORDER_UNPAID.getValue()
        );

        if (messageRecordDTO != null) {
            // 消息已经被消费过
            log.warn("reduplicate message msgId: {}", msg.getMsgID());
            return new DistributionResponse(true);
        }

        DistributionOrderDTO distributionOrderDTO = orderUnpaidMsg.getDistributionOrderDTO();
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("distributionOrderDTO", distributionOrderDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.DO_DISTRIBUTION.getActionName());
        Response<Boolean> response = distributionService.execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("do distribution error, errMsg: {}", response.getMessage());
            throw new DistributionException(response.getCode(), response.getMessage());
        }
        // 记录已经成功消费的消息
        messageRecordDTO = new MessageRecordDTO();
        messageRecordDTO.setMsgId(msg.getMsgID());
        messageRecordDTO.setIdentify(String.valueOf(distributionOrderDTO.getOrderId()));
        messageRecordDTO.setBizType(BizType.TRADE_ORDER_UNPAID.getValue());
        messageRecordManager.addRecord(messageRecordDTO);
        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.ORDER_UNPAID_LISTENER.getActionName();
    }
}
