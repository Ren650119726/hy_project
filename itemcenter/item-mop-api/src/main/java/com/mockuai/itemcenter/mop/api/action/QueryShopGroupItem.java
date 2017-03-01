package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.GroupItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询店铺店铺商品
 * Created by luliang on 15/7/28.
 */
public class QueryShopGroupItem extends BaseAction {

    public MopResponse execute(Request request) {
        ItemQTO itemQTO = new ItemQTO();
        Long sellerId = (Long)request.getParam("seller_id");
        Long groupId = (Long)request.getParam("group_id");
        String offset = (String)request.getParam("offset");
        String count = (String)request.getParam("count");
        String appKey = (String)request.getParam("app_key");

        if(StringUtils.isNotEmpty(offset) && StringUtils.isNumeric(offset)){
            itemQTO.setOffset(Integer.parseInt(offset));
        }

        if(StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)){
            itemQTO.setNeedPaging(true);
            itemQTO.setPageSize(Integer.parseInt(offset));
        }

        itemQTO.setSellerId(sellerId);
        itemQTO.setGroupId(groupId);

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.QUERY_ITEM_GROUP_ACTION.getActionName());
        itemReq.setParam("itemQTO", itemQTO);
        itemReq.setParam("appKey", appKey);
        Response<List<GroupItemDTO>> response = this.getItemService().execute(itemReq);

        if(response.getCode() == ResponseCode.SUCCESS.getCode()) {
            List<GroupItemDTO> groupItemDTOs = response.getModule();
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("item_list", MopApiUtil.genMopItemListForGroupDTO(groupItemDTOs));
            data.put("total_count", response.getTotalCount());
            return new MopResponse(data);
        } else {
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/item/group/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
