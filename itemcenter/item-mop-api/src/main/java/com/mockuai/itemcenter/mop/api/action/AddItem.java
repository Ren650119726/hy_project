package com.mockuai.itemcenter.mop.api.action;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

/**
* Created by zengzhangqiang on 5/4/15.
*/
public class AddItem extends BaseAction{
    public MopResponse execute(Request request) {
        String itemStr = (String)request.getParam("item");
        String appKey = (String)request.getParam("app_key");
        ItemDTO itemDTO = JsonUtil.parseJson(itemStr, ItemDTO.class);
        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.ADD_ITEM.getActionName());
        itemReq.setParam("itemDTO", itemDTO);
        itemReq.setParam("appKey", appKey);
        Response<ItemDTO> itemResp = this.getItemService().execute(itemReq);

        return new MopResponse(itemResp.getCode(), itemResp.getMessage());
    }

    public String getName() {
        return "/item/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
