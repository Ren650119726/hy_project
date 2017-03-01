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
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索店铺,简单做根据名字搜索结果不排序;
 *
 * Created by luliang on 15/7/27.
 */
public class QueryShop extends BaseAction {

    public MopResponse execute(Request request) {
        String keyword = (String)request.getParam("keyword");
        String offset = (String)request.getParam("offset");
        String count = (String)request.getParam("count");

        ShopQTO shopQTO = new ShopQTO();
        shopQTO.setShopName(keyword);
        if(StringUtils.isNotEmpty(offset) && StringUtils.isNumeric(offset)){
            shopQTO.setOffset(Integer.parseInt(offset));
        }

        if(StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)){
            shopQTO.setNeedPaging(true);
            shopQTO.setPageSize(Integer.parseInt(offset));
        }

        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();
        shopRequest.setParam("shopQTO", shopQTO);
        shopRequest.setCommand(ActionEnum.QUERY_SHOP.getActionName());
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
        return "/shop/query";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
