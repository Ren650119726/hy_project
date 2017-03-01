package com.mockuai.distributioncenter.mop.api.action.shop;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.distributioncenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by duke on 16/5/18.
 */
public class GetShopAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(GetShopAction.class);

    public MopResponse execute(Request request) {
        String sellerIdStr = (String) request.getParam("seller_id");
        String appKey = (String) request.getParam("app_key");

        if (sellerIdStr == null) {
            log.error("sellerIdStr is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "sellerIdStr is null");
        }
        Long sellerId = Long.parseLong(sellerIdStr);

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("sellerId", sellerId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SHOP_BY_SELLER_ID.getActionName());
        Response<DistShopDTO> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("get shop by userId error, sellerId: {}, msg: {}", sellerId, response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        return new MopResponse(MopApiUtil.getMopShopDTO(response.getModule()));
    }

    public String getName() {
        return "/dist/shop/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
