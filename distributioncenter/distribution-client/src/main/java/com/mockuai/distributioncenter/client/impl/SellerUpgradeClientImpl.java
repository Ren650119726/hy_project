package com.mockuai.distributioncenter.client.impl;

import com.mockuai.distributioncenter.client.SellerUpgradeClient;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerUpgradeDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerUpgradeQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
public class SellerUpgradeClientImpl implements SellerUpgradeClient {
    @Resource
    private DistributionService distributionService;

    public Response<List<SellerUpgradeDTO>> querySellerUpgrade(SellerUpgradeQTO sellerUpgradeQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerUpgradeQTO", sellerUpgradeQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SELLER_UPGRADE_LIST.getActionName());
        Response<List<SellerUpgradeDTO>> response = this.distributionService.execute(request);
        return response;
    }

    public Response<Void> agreeSellerUpgrade(Long id, String reason, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("reason", reason);
        request.setCommand(ActionEnum.AGREE_SELLER_UPGRADE.getActionName());
        Response<Void> response = this.distributionService.execute(request);
        return response;
    }

    public Response<Void> rejectSellerUpgrade(Long id, String reason, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("reason", reason);
        request.setCommand(ActionEnum.REJECT_SELLER_UPGRADE.getActionName());
        Response<Void> response = this.distributionService.execute(request);
        return response;
    }
}
