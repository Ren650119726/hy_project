package com.mockuai.distributioncenter.core.service.action.gains;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.HkProtocolRecordManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lizg on 2016/8/29.
 */

@Service
public class AddProtocolAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(AddProtocolAction.class);

    @Autowired
    private HkProtocolRecordManager hkProtocolRecordManager;


    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        HkProtocolRecordDTO hkProtocolRecordDTO = (HkProtocolRecordDTO) request.getParam("hkProtocolRecordDTO");
        Integer appType = (Integer) context.get("appType");
        if (hkProtocolRecordDTO == null || hkProtocolRecordDTO.getUserId() == null) {
            log.error("hkProtocolRecordDTO are null");
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "hkProtocolRecordDTO is null");
        }

        hkProtocolRecordDTO.setAppType(appType);
        hkProtocolRecordDTO.setProtocolId(0L);
        hkProtocolRecordDTO.setAgreeDesc("同意协议");
        HkProtocolRecordDTO Protocol = new HkProtocolRecordDTO();
        Protocol.setUserId(hkProtocolRecordDTO.getUserId());
        HkProtocolRecordDTO hkProtocolRecord = hkProtocolRecordManager.get(Protocol);
        if (hkProtocolRecord != null) {
            return new DistributionResponse(ResponseCode.SUCCESS);
        }
        Long site = hkProtocolRecordManager.add(hkProtocolRecordDTO);
        if (site < 1) {
            return new DistributionResponse(ResponseCode.SERVICE_EXCEPTION);
        }


        return new DistributionResponse(ResponseCode.SUCCESS);


    }

    @Override
    public String getName() {
        return ActionEnum.ADD_PROTOCOL.getActionName();
    }
}
