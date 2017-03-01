package com.mockuai.distributioncenter.mop.api.action.shop;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.CollectionShopDTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.distributioncenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 16/5/21.
 */
public class QueryCollectionShopByUserId extends BaseAction {
    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.QUERY_COLLECTION_SHOP_BY_USER_ID.getActionName());
        Response<List<CollectionShopDTO>> response = getDistributionService().execute(baseRequest);

        if (!response.isSuccess()) {
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }

        List<CollectionShopDTO> collectionShopDTOs = response.getModule();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("collection_shop_list", MopApiUtil.getMopCollectionShopDTOs(collectionShopDTOs));
        return new MopResponse(data);
    }

    public String getName() {
        return "/collection/shop/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
