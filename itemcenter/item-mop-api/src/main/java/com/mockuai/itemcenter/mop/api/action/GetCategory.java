package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/6/15.
 */
public class GetCategory extends BaseAction{
    public MopResponse execute(Request request) {
        String categoryIdStr = (String)request.getParam("category_id");
        String appKey = (String)request.getParam("app_key");
        int categoryId = 0;
        if(StringUtils.isBlank(categoryIdStr) == false){
            categoryId = Integer.valueOf(categoryIdStr);
        }
        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.GET_ITEM_CATEGORY.getActionName());
        itemReq.setParam("categoryId", categoryId);
        itemReq.setParam("appKey", appKey);

        Response<ItemCategoryDTO> itemResp = this.getItemService().execute(itemReq);
        if(itemResp.getCode() == ResponseCode.SUCCESS.getCode()){
            return new MopResponse(MopApiUtil.genMopCategoryDTO(itemResp.getModule()));
        }else{
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }
    }

    public String getName() {
        return "/category/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
