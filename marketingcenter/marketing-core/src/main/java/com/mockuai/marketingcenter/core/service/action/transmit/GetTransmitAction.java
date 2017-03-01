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
public class GetTransmitAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(GetTransmitAction.class);

    @Resource
    private TransmitManager transmitManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        log.info("Enter Action [{}]", getName());

        Request request = context.getRequest();
        Long userId = (Long) request.getParam("userId");
        String bizCode = (String) context.get("bizCode");

        if (userId == null) {
            throw new MarketingException(ResponseCode.PARAMETER_NULL, "userId is null");
        }

        TransmitDTO transmitDTO = transmitManager.getTransmit(bizCode);

        if (transmitDTO == null) {
            // 如果当前用户还没有转发有礼的记录，新建一个转发有礼的记录
            transmitDTO = new TransmitDTO();
            transmitDTO.setPercent(0.0);
            // 状态 0 - 关闭， 1 - 开启
            transmitDTO.setStatus(0);
            transmitDTO.setBizCode(bizCode);
            transmitDTO.setUserId(userId);
            Long id = transmitManager.addTransmit(transmitDTO);
            transmitDTO.setId(id);
        }
        log.info("Exit Action [{}]", getName());
        return new MarketingResponse(transmitDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_TRANSMIT.getActionName();
    }
}
