package com.mockuai.marketingcenter.core.service.action.transmit;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.TransmitDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.TransmitManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 15/9/23.
 */
@Service
public class UpdateTransmitAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateTransmitAction.class);

    @Resource
    private TransmitManager transmitManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        log.info("Enter Action [{}]", getName());
        Request request = context.getRequest();
        TransmitDTO transmitDTO = (TransmitDTO) request.getParam("transmitDTO");
        String bizCode = (String) context.get("bizCode");

        if (transmitDTO == null) {
            throw new MarketingException(ResponseCode.PARAMETER_NULL, "transmitDTO is null");
        }
        transmitDTO.setBizCode(bizCode);
        transmitManager.updateTransmit(transmitDTO);
        log.info("Exit Action [{}]", getName());
        return new MarketingResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_TRANSMIT.getActionName();
    }
}