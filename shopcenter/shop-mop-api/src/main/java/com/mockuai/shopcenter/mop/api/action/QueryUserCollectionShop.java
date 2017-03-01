package com.mockuai.shopcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luliang on 15/8/9.
 */
public class QueryUserCollectionShop extends BaseAction {

    public MopResponse execute(Request request) {

        String offsetStr = (String)request.getParam("offset");
        String countStr = (String)request.getParam("count");
        String appKey = (String) request.getParam("app_key");
        ShopCollectionQTO shopCollectionQTO = new ShopCollectionQTO();

        if(StringUtils.isBlank(offsetStr) == false){
            shopCollectionQTO.setOffset(Integer.valueOf(offsetStr));
        }
        if(StringUtils.isBlank(countStr) == false){
            shopCollectionQTO.setNeedPaging(true);
            shopCollectionQTO.setPageSize(Integer.valueOf(countStr));
        }

        Long userId = (Long)request.getAttribute("user_id");
        if(userId == null) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "userId is invalid");
        }

        shopCollectionQTO.setUserId(userId);

        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();
        shopRequest.setParam("shopCollectionQTO", shopCollectionQTO);
        shopRequest.setParam("appKey", appKey);
        shopRequest.setCommand(ActionEnum.QUERY_USER_COLLECTION_SHOP.getActionName());
        Response<List<ShopDTO>> response = this.getShopService().execute(shopRequest);
        if(response.getCode() == ResponseCode.SUCCESS.getCode()) {
            List<ShopDTO> shopDTOs = response.getModule();
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("shop_list", MopApiUtil.genMopShopDTOList(shopDTOs));
            data.put("total_count", response.getTotalCount());
            return new MopResponse(data);
        } else {
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/shop/collection/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
