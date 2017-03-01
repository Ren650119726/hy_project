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
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luliang on 15/8/5.
 */
public class QueryShopItemGroup extends BaseAction{


    public MopResponse execute(Request request) {
        Long sellerId = (Long) request.getParam("seller_id");
        // 一个用户开一个店;
        if(sellerId == null) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "sellerId is invalid");
        }

        ShopItemGroupQTO shopItemGroupQTO = new ShopItemGroupQTO();
        shopItemGroupQTO.setSellerId(sellerId);
        shopItemGroupQTO.setSellerId(sellerId);
        String offset = (String)request.getParam("offset");
        String count = (String)request.getParam("count");

        if(StringUtils.isNotEmpty(offset) && StringUtils.isNumeric(offset)){
            shopItemGroupQTO.setOffset(Integer.parseInt(offset));
        }

        if(StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)){
            shopItemGroupQTO.setNeedPaging(true);
            shopItemGroupQTO.setPageSize(Integer.parseInt(offset));
        }

        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();
        shopRequest.setParam("shopItemGroupQTO", shopItemGroupQTO);
        shopRequest.setCommand(ActionEnum.QUERY_SHOP_ITEM_GROUP.getActionName());
        Response<List<ShopItemGroupDTO>> response = this.getShopService().execute(shopRequest);
        if(response.getCode() == ResponseCode.SUCCESS.getCode()) {
            List<ShopItemGroupDTO> shopItemGroupDTOs = response.getModule();
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("shop_item_group_list", MopApiUtil.genMopShopItemGroupDTOList(shopItemGroupDTOs));
            data.put("total_count", response.getTotalCount());
            return new MopResponse(data);
        } else {
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/shop/item/group/query";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
