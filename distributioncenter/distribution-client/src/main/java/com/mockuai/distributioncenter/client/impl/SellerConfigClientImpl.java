package com.mockuai.distributioncenter.client.impl;

import com.mockuai.distributioncenter.client.SellerConfigClient;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
public class SellerConfigClientImpl implements SellerConfigClient {
    @Resource
    private DistributionService distributionService;

    public Response<Long> addSellerConfig(SellerConfigDTO sellerConfigDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerConfigDTO", sellerConfigDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_SELLER_CONFIG.getActionName());
        return this.distributionService.execute(request);
    }

    public Response<Boolean> updateSellerConfig(List<SellerConfigDTO> sellerConfigDTOs, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerConfigDTOs", sellerConfigDTOs);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_SELLER_CONFIG.getActionName());
        return this.distributionService.execute(request);
    }

    public Response<List<SellerConfigDTO>> querySellerConfig(SellerConfigQTO sellerConfigQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerConfigQTO", sellerConfigQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SELLER_CONFIG.getActionName());
        return this.distributionService.execute(request);
    }

    public Response<SellerConfigDTO> getSellerConfig(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SELLER_CONFIG.getActionName());
        return this.distributionService.execute(request);
    }
}
