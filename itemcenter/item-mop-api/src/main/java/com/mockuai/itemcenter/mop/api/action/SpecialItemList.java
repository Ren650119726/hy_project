package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
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
 * Created by zengzhangqiang on 5/4/15.
 */
public class SpecialItemList extends BaseAction {

    public MopResponse execute(Request request) {

        String itemTypeStr = (String) request.getParam("item_type");
        String lifecycleStr = (String) request.getParam("lifecycle");
        String offset = (String) request.getParam("offset");
        String count = (String) request.getParam("count");
        String appKey = (String) request.getParam("app_key");

        ItemQTO itemQTO = new ItemQTO();

        if (StringUtils.isNotEmpty(lifecycleStr) && StringUtils.isNumeric(lifecycleStr)) {
            itemQTO.setLifecycle(Integer.parseInt(lifecycleStr));
        } else {
            return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "lifecycle的值不合法");
        }

        if (StringUtils.isNotEmpty(itemTypeStr) && StringUtils.isNumeric(itemTypeStr)) {
            itemQTO.setItemType(Integer.parseInt(itemTypeStr));
        } else {
            return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "itemType的值不合法");
        }

        if (StringUtils.isNotEmpty(offset) && StringUtils.isNumeric(offset)) {
            itemQTO.setOffset(Integer.parseInt(offset));
        }

        if (StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)) {
            itemQTO.setPageSize(Integer.parseInt(count));
        }

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.QUERY_SPECIAL_ITEM.getActionName());
        itemReq.setParam("itemQTO", itemQTO);
        itemReq.setParam("appKey", appKey);
        Response<List<ItemDTO>> itemResp = this.getItemService().execute(itemReq);
        if (itemResp.getCode() == ResponseCode.SUCCESS.getCode()) {
            List<ItemDTO> itemDTOs = itemResp.getModule();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("item_list", MopApiUtil.genMopItemList(itemDTOs));
            data.put("total_count", itemResp.getTotalCount());
            data.put("time",System.currentTimeMillis());
            return new MopResponse(data);
        } else {
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }


    }

    public String getName() {
        return "/item/special/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
