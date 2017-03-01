package com.mockuai.distributioncenter.client.impl;

import com.mockuai.distributioncenter.client.GainsSetClient;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/8/29.
 */
public class GainsSetClientImpl implements GainsSetClient{

    @Resource
    private DistributionService distributionService;

    public Response<GainsSetDTO> addGainsSet(GainsSetDTO gainsSetDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("gainsSetDTO",gainsSetDTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.ADD_GAINS_SET.getActionName());
        return this.distributionService.execute(request);
    }

    public Response<GainsSetDTO> getGainsSet(String appKey) {
        Request request = new BaseRequest();
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GET_GAINS_SET.getActionName());
        return this.distributionService.execute(request);
    }
}
