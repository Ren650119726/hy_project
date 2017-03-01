package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by qqyu
*/
public class CollectionList extends BaseAction{
    public MopResponse execute(Request request) {
        String offsetStr = (String)request.getParam("offset");
        String countStr = (String)request.getParam("count");
        String appKey = (String)request.getParam("app_key");
        Long userId = (Long)request.getAttribute("user_id");

        Integer offset = null;
        Integer count = null;
        if(StringUtils.isNotEmpty(offsetStr)){
            try{
                offset = Integer.valueOf(offsetStr);
            }catch(Exception e){
                //TODO error handle
            }
        }

        if(StringUtils.isNotEmpty(countStr)){
            try{
                count = Integer.valueOf(countStr);
            }catch(Exception e){
                //TODO error handle
            }
        }

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.QUERY_ITEM_COLLECTION.getActionName());
        ItemCollectionQTO itemCollectionQTO = new ItemCollectionQTO();
        itemCollectionQTO.setUserId(userId);
        itemCollectionQTO.setOffset(offset);
        itemCollectionQTO.setPageSize(count);
        itemCollectionQTO.setNeedPaging(true);
        itemReq.setParam("itemCollectionQTO", itemCollectionQTO);
        itemReq.setParam("appKey", appKey);
        Response<List<ItemDTO>> itemResp = this.getItemService().execute(itemReq);
        if(itemResp.getCode() == ResponseCode.SUCCESS.getCode()){
            List<ItemDTO> itemDTOs = itemResp.getModule();
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("item_list", MopApiUtil.genMopItemList(itemDTOs));
            data.put("total_count", itemResp.getTotalCount());
            return new MopResponse(data);
        }else{
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }
    }

    public String getName() {
        return "/item/collection/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
