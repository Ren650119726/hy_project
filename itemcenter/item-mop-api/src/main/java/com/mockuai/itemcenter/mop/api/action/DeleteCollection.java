package com.mockuai.itemcenter.mop.api.action;

import com.google.gson.reflect.TypeToken;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemCollectionDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.ArrayList;
import java.util.List;

public class DeleteCollection extends BaseAction{

    public MopResponse execute(Request request) {
        String itemListStr = (String)request.getParam("item_list");
        String appKey = (String)request.getParam("app_key");
        Long userId = (Long)request.getAttribute("user_id");
        List<MopItemCollectionDTO> itemList = JsonUtil.parseJson(itemListStr, new TypeToken<List<MopItemCollectionDTO>>(){}.getType());
        List<ItemCollectionDTO> itemCollectionDTOList = new ArrayList<ItemCollectionDTO>();
        for(MopItemCollectionDTO mopItemCollectionDTO : itemList){
            try{
                ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(mopItemCollectionDTO.getItemUid());
                ItemCollectionDTO itemCollectionDTO = new ItemCollectionDTO();
                itemCollectionDTO.setDistributorId(mopItemCollectionDTO.getDistributorId());
                itemCollectionDTO.setUserId(userId);
                itemCollectionDTO.setItemId(itemUidDTO.getItemId());
                itemCollectionDTOList.add(itemCollectionDTO);
            }catch(Exception e){
                //TODO error handle
            }
        }

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setParam("itemCollectionList", itemCollectionDTOList);
        itemReq.setCommand(ActionEnum.DELETE_ITEM_COLLECTION.getActionName());
        itemReq.setParam("appKey", appKey);
        Response<ItemCollectionDTO> collectionResp = this.getItemService().execute(itemReq);
        return MopApiUtil.transferResp(collectionResp);
    }

    public String getName() {
        // TODO Auto-generated method stub
        return "/item/collection/delete";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }

}
