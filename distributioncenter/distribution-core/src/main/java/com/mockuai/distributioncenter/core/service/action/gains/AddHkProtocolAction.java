package com.mockuai.distributioncenter.core.service.action.gains;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.util.List;

/**
 * Created by lizg on 2016/10/25.
 */

@Service
public class AddHkProtocolAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(AddProtocolAction.class);

    @Autowired
    private HkProtocolRecordManager hkProtocolRecordManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        HkProtocolRecordDTO hkProtocolRecordDTO = (HkProtocolRecordDTO) request.getParam("hkProtocolRecordDTO");
        String protocolIds = (String) request.getParam("protocolIds");
        Integer appType = (Integer) context.get("appType");
        if (hkProtocolRecordDTO == null || hkProtocolRecordDTO.getUserId() == null) {
            log.error("hkProtocolRecordDTO are null");
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "hkProtocolRecordDTO is null");
        }

        List<HkProtocolRecordDTO> protocolRecordDTOList = hkProtocolRecordManager.getByUserId(hkProtocolRecordDTO);
        if (protocolRecordDTOList.size() > 2) {
            return new DistributionResponse(ResponseCode.SUCCESS);
        }
        log.info("[{}] protocolIds:{}",protocolIds);
        JSONObject jsonObject = JSONObject.parseObject(protocolIds);
        JSONArray jsonArray = jsonObject.getJSONArray("id");
        for (int i = 0; i < jsonArray.size(); i++) {
            log.info("[{}] protocolId:{}", jsonArray.getLong(i));
            hkProtocolRecordDTO.setProtocolId(jsonArray.getLong(i));
            hkProtocolRecordDTO.setAppType(appType);
            hkProtocolRecordDTO.setAgreeDesc("注册");
            Long site = hkProtocolRecordManager.add(hkProtocolRecordDTO);
            if (site < 1) {
                return new DistributionResponse(ResponseCode.SERVICE_EXCEPTION);
            }
        }

        return new DistributionResponse(ResponseCode.SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_HK_PROTOCOL.getActionName();
    }
}
