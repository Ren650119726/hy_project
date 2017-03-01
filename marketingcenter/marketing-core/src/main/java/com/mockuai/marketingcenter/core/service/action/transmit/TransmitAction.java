package com.mockuai.marketingcenter.core.service.action.transmit;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.TransmitDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.TransmitManager;
import com.mockuai.marketingcenter.core.manager.VirtualWealthManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 15/9/25.
 */
@Service
public class TransmitAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(TransmitAction.class);

    @Resource
    private TransmitManager transmitManager;
    @Resource
    private VirtualWealthManager virtualWealthManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        log.info("Enter Action [{}]", getName());
        Request request = context.getRequest();
        Long senderId = (Long) request.getParam("senderId");
        Long receiverId = (Long) request.getParam("receiverId");
        Long totalAccount = (Long) request.getParam("totalAccount");
        Long orderId = (Long) request.getParam("orderId");
        String bizCode = (String) context.get("bizCode");
        String appKey = (String) context.get("appKey");

        TransmitDTO transmitDTO = null;
        try {
            transmitDTO = transmitManager.getTransmit(bizCode);
        } catch (MarketingException e) {
            log.error("get transmit error with sender ID: {}", senderId);
            return new MarketingResponse(ResponseCode.SERVICE_EXCEPTION);
        }

        // 转发有礼是关闭状态，或者不存在，则不进行转发
        if (transmitDTO == null || transmitDTO.getStatus() == 0) {
            log.info("Exit Action [{}]", getName());
            return new MarketingResponse(true);
        }

        // 实际发放的量，取整
        Long grantAmount = Math.round(totalAccount * transmitDTO.getPercent());

        virtualWealthManager.grantVirtualWealth(senderId, WealthType.VIRTUAL_WEALTH.getValue(),
                SourceType.TRANSMIT.getValue(), grantAmount, receiverId, orderId, appKey);

        log.info("Exit Action [{}]", getName());
        return new MarketingResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.TRANSMIT.getActionName();
    }
}