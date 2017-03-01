package com.mockuai.distributioncenter.client.impl;

import javax.annotation.Resource;

import com.mockuai.distributioncenter.client.HkProtocolRecordClient;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;

/**
 * Created by lizg on 2016/8/29.
 */
public class HkProtocolRecordClientImpl implements HkProtocolRecordClient{

    @Resource
    private DistributionService distributionService;
    
    public Response<Boolean> addHkProtocolRecord(HkProtocolRecordDTO hkProtocolRecordDTO, String appKey){
        Request request = new BaseRequest();
        request.setParam("HkProtocolRecordDTO",hkProtocolRecordDTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.ADD_PROTOCOL.getActionName());
        return this.distributionService.execute(request);
    }

    public Response<HkProtocolRecordDTO> getHkProtocolRecord(HkProtocolRecordDTO hkProtocolRecordDTO,String appKey) {
        Request request = new BaseRequest();
        request.setParam("HkProtocolRecordDTO",hkProtocolRecordDTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GET_PROTOCOL.getActionName());
        return this.distributionService.execute(request);
    }
}
