package com.mockuai.distributioncenter.mop.api.action.shop;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.CollectionShopDTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

/**
 * Created by duke on 16/5/21.
 */
public class AddCollectionShop extends BaseAction {
    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("userId");
        String shopIdStr = (String) request.getParam("shop_id");
        String appKey = (String) request.getParam("app_key");

        if (shopIdStr == null) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "shop_id is null");
        }

        CollectionShopDTO collectionShopDTO = new CollectionShopDTO();
        collectionShopDTO.setUserId(userId);
        collectionShopDTO.setShopId(Long.parseLong(shopIdStr));

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("collectionShopDTO", collectionShopDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.ADD_COLLECTION_SHOP.getActionName());

        Response<CollectionShopDTO> response = getDistributionService().execute(baseRequest);

        if (!response.isSuccess()) {
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        return new MopResponse(true);
    }

    public String getName() {
        return "/collection/shop/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
