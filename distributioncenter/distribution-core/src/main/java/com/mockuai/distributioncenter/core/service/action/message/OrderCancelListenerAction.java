package com.mockuai.distributioncenter.core.service.action.message;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.BizType;
import com.mockuai.distributioncenter.common.constant.DistributionStatus;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.MessageRecordDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.MessageRecordManager;
import com.mockuai.distributioncenter.core.message.msg.OrderCancelMsg;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 16/6/24.取消
 */
@Service
public class OrderCancelListenerAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(OrderCancelListenerAction.class);

    @Autowired
    private MessageRecordManager messageRecordManager;

    @Autowired
    private DistRecordManager distRecordManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        OrderCancelMsg orderCancelMsg = (OrderCancelMsg) request.getParam("orderCancelMsg");
        // 检查消息是否已经被消费过了，保证消息处理幂等
        MessageRecordDTO messageRecordDTO = messageRecordManager.getRecordByIdentifyAndBizType(
                String.valueOf(orderCancelMsg.getDistributionOrderDTO().getOrderId()) /* 使用order ID作为唯一标识，保证消息唯一性 */,
                BizType.TRADE_CANCEL.getValue()
        );

        Message msg = orderCancelMsg.getMsg();

        if (messageRecordDTO != null) {
            // 消息已经被消费过
            log.warn("reduplicate message msgId: {}", msg.getMsgID());
            return new DistributionResponse(true);
        }

        DistributionOrderDTO distributionOrderDTO = orderCancelMsg.getDistributionOrderDTO();

        DistRecordQTO distRecordQTO = new DistRecordQTO();
        distRecordQTO.setOrderId(distributionOrderDTO.getOrderId());
        List<DistRecordDTO> distRecordDTOs =
                distRecordManager.query(distRecordQTO);

        if (distRecordDTOs != null && !distRecordDTOs.isEmpty()) {
        	//是否是嗨币支付
        	if(distributionOrderDTO.getPaymentId() != 12){
			    for (DistRecordDTO dto : distRecordDTOs) {
			        // 处理发货订单的分拥记录
			        dto.setStatus(DistributionStatus.CANCEL_DISTRIBUTION.getStatus());
			        distRecordManager.update(dto);
			    }
        	}
        } else {
            // 订单不存在
            log.warn("calculate record not exists, orderSn: {}", distributionOrderDTO.getOrderSn());
            // return new DistributionResponse(false);
        }

        // 记录已经成功消费的消息
        messageRecordDTO = new MessageRecordDTO();
        messageRecordDTO.setMsgId(msg.getMsgID());
        messageRecordDTO.setIdentify(String.valueOf(distributionOrderDTO.getOrderId()));
        messageRecordDTO.setBizType(BizType.TRADE_CANCEL.getValue());

        messageRecordManager.addRecord(messageRecordDTO);

        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.ORDER_CANCEL_LISTENER.getActionName();
    }
}
