package com.mockuai.distributioncenter.mop.api.action.sellerupgrade;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.SellerUpgradeApplyStatus;
import com.mockuai.distributioncenter.common.domain.dto.SellerUpgradeDTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

/**
 * Created by yeliming on 16/5/18.
 */

public class SellerUpgradeApplyAction extends BaseAction {

    public MopResponse execute(Request request) {
        Long applicant_id = (Long) request.getParam("user_id");
        String appKey = (String) request.getParam("app_key");

        SellerUpgradeDTO sellerUpgradeDTO = new SellerUpgradeDTO();
        sellerUpgradeDTO.setApplicantId(applicant_id);
        sellerUpgradeDTO.setStatus(SellerUpgradeApplyStatus.Pending.getValue());

        com.mockuai.distributioncenter.common.api.Request req = new BaseRequest();
        req.setParam("sellerUpgradeDTO", sellerUpgradeDTO);
        req.setParam("appKey", appKey);
        Response<Long> response = this.getDistributionService().execute(req);
        if (response.isSuccess()) {
            return new MopResponse(response.getModule());
        } else {
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return ActionEnum.ADD_SELLER_UPGRADE.getActionName();
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
