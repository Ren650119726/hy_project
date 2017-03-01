package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemCommentDTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghailong on 15-7-29.
 */
public class ItemCommentGrade extends BaseAction {
    public MopResponse execute(Request request) {
        String itemUid = (String)request.getParam("item_uid");
        String offsetStr = (String)request.getParam("offset");
        String countStr = (String)request.getParam("count");
        String scoreStr = (String)request.getParam("score");
        String appKey = (String)request.getParam("app_key");

        ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUid);

        ItemCommentQTO itemCommentQTO = new ItemCommentQTO();
        itemCommentQTO.setSellerId(itemUidDTO.getSellerId());
        itemCommentQTO.setItemId(itemUidDTO.getItemId());
        if(scoreStr != null)
            itemCommentQTO.setScore(Long.valueOf(scoreStr));
        if(StringUtils.isBlank(offsetStr) == false){
            itemCommentQTO.setOffset(Integer.valueOf(offsetStr));
        }
        if(StringUtils.isBlank(countStr) == false){
            itemCommentQTO.setPageSize(Integer.valueOf(countStr));
        }

        com.mockuai.itemcenter.common.api.Request itemCommentReq = new BaseRequest();
        itemCommentReq.setCommand(ActionEnum.QUERY_ITEMCOMMENTGRADE.getActionName());
        itemCommentReq.setParam("itemCommentQTO", itemCommentQTO);
        itemCommentReq.setParam("appKey", appKey);
        Response<ItemCommentDTO> itemResp = this.getItemService().execute(itemCommentReq);

        if(itemResp.getCode() != ResponseCode.SUCCESS.getCode()){
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }else{
            List<ItemCommentDTO> itemCommentDTOs = (List<ItemCommentDTO>)itemResp.getModule();
            List<MopItemCommentDTO> mopItemCommentDTOs = MopApiUtil.genMopItemCommentList(itemCommentDTOs);
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("comment_grade", mopItemCommentDTOs);
            data.put("total_count", itemResp.getTotalCount());
            return new MopResponse(data);
        }
    }

    public String getName() {
        return "/item/comment/grade";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}