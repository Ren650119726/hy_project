package com.mockuai.marketingcenter.client.impl;

import com.mockuai.marketingcenter.client.BarterClient;
import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.MarketingService;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by edgar.zr on 12/2/15.
 */
public class BarterClientImpl implements BarterClient {

    @Resource
    private MarketingService marketingService;

    public Response<Long> addBarterActivity(MarketActivityDTO marketActivityDTO, String appKey) {

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.ADD_BARTER.getActionName());
        baseRequest.setParam("marketActivityDTO", marketActivityDTO);
        baseRequest.setParam("appKey", appKey);
        Response<Long> response = (Response<Long>) marketingService.execute(baseRequest);
        return response;
    }

    public Response<MarketActivityDTO> getBarterActivity(Long activityId, Long creatorId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GET_BARTER.getActionName());
        baseRequest.setParam("activityId", activityId);
        baseRequest.setParam("creatorId", creatorId);
        baseRequest.setParam("appKey", appKey);
        Response<MarketActivityDTO> response = (Response<MarketActivityDTO>) marketingService.execute(baseRequest);
        return response;
    }

    public Response<List<MarketActivityDTO>> queryBarterActivity(MarketActivityQTO marketActivityQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.QUERY_BARTER.getActionName());
        baseRequest.setParam("marketActivityQTO", marketActivityQTO);
        baseRequest.setParam("appKey", appKey);
        Response<List<MarketActivityDTO>> response = (Response<List<MarketActivityDTO>>) marketingService.execute(baseRequest);
        return response;
    }
}