package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/9/10.
 */
public class GetHierachyCategorys extends BaseAction{

    public MopResponse execute(Request request) {

        String appKey = (String)request.getParam("app_key");

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.QUERY_HIERARCHY_CATOGARY.getActionName());
        itemReq.setParam("appKey", appKey);

        Response<List<ItemCategoryDTO>> itemResp = this.getItemService().execute(itemReq);
        if(itemResp.getCode() == ResponseCode.SUCCESS.getCode()){

            Map<String,Object> data = new HashMap<String, Object>();

            data.put("category_list", MopApiUtil.genMopCategoryDTOList(itemResp.getModule()));

            return new MopResponse(data);
        }else{
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }
    }

    public String getName() {
        return "/category/hierachy/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
