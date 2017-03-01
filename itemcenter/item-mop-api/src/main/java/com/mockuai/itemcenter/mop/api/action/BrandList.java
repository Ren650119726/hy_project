package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class BrandList extends BaseAction {
    public MopResponse execute(Request request) {


        String offset = (String) request.getParam("offset");
        String count = (String) request.getParam("count");
        String appKey = (String) request.getParam("app_key");

        SellerBrandQTO sellerBrandQTO = new SellerBrandQTO();
        sellerBrandQTO.setNeedPaging(true);

        if (StringUtils.isNotEmpty(offset) && StringUtils.isNumeric(offset)) {
            sellerBrandQTO.setOffset(Integer.parseInt(offset));
        } else {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "offset不能为空");
        }

        if (StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)) {
            sellerBrandQTO.setPageSize(Integer.parseInt(count));
        } else {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "count不能为空");
        }

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.QUERY_SELLER_BRAND.getActionName());
        itemReq.setParam("sellerBrandQTO", sellerBrandQTO);
        itemReq.setParam("appKey", appKey);
        Response<List<SellerBrandDTO>> itemResp = this.getItemService().execute(itemReq);
        if (itemResp.getCode() == ResponseCode.SUCCESS.getCode()) {

            Map<String, Object> data = Maps.newHashMapWithExpectedSize(2);

            data.put("brand_list", MopApiUtil.genMopBrandDTOList(itemResp.getModule()));
            data.put("total_count", itemResp.getTotalCount());
            return new MopResponse(data);
        } else {
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }
    }

    public String getName() {
        return "/item/brand/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
