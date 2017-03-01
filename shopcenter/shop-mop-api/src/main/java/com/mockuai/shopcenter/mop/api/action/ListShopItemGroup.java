package com.mockuai.shopcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListShopItemGroup extends BaseAction {

    public MopResponse execute(Request request) {

        String shopIdStr = (String)request.getParam("shop_id");

        String appKey = (String)request.getAttribute("app_key");


        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();

        if(StringUtils.isNotEmpty(shopIdStr) && StringUtils.isNumeric(shopIdStr)){
            shopRequest.setParam("shopId",Long.parseLong(shopIdStr));
            shopRequest.setParam("appKey",appKey);
        }else {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"shop_id不能为空");
        }

        shopRequest.setCommand(ActionEnum.QUERY_SHOP_ITEM_GROUP_BY_SHOP.getActionName());
        Response<List<ShopItemGroupDTO>> response = this.getShopService().execute(shopRequest);
        if(response.getCode() == ResponseCode.SUCCESS.getCode()) {
            List<ShopItemGroupDTO> shopItemGroupDTOList = response.getModule();
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("item_group_list", MopApiUtil.genMopShopItemGroupDTOList(shopItemGroupDTOList));
            data.put("total_count", response.getTotalCount());
            return new MopResponse(data);
        } else {
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/shop/item_group/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
