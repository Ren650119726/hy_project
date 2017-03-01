package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CountCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemCommentDTO;
import com.mockuai.itemcenter.mop.api.domain.OrderUidDTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/17/15.
 */
public class ItemCommentList extends BaseAction{
    public MopResponse execute(Request request) {
        String itemUid = (String)request.getParam("item_uid");
        String offsetStr = (String)request.getParam("offset");
        String countStr = (String)request.getParam("count");
        String appKey = (String)request.getParam("app_key");

        ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUid);

        ItemCommentQTO itemCommentQTO = new ItemCommentQTO();
        itemCommentQTO.setSellerId(itemUidDTO.getSellerId());
        itemCommentQTO.setItemId(itemUidDTO.getItemId());
        if(StringUtils.isBlank(offsetStr) == false){
            itemCommentQTO.setOffset(Integer.valueOf(offsetStr));
        }
        if(StringUtils.isBlank(countStr) == false){
            itemCommentQTO.setPageSize(Integer.valueOf(countStr));
        }



        com.mockuai.itemcenter.common.api.Request itemCommentReq = new BaseRequest();
        itemCommentReq.setCommand(ActionEnum.COUNT_ITEMCOMMENTGRADE.getActionName());
        itemCommentReq.setParam("itemCommentQTO", itemCommentQTO);
        itemCommentReq.setParam("appKey", appKey);

        CountCommentDTO countCommentDTO = null;
        // 分两次查询;
        Response<CountCommentDTO> response = this.getItemService().execute(itemCommentReq);
        if(response.getCode() != ResponseCode.SUCCESS.getCode()) {
            return new MopResponse(response.getCode(), response.getMessage());
        } else {
            countCommentDTO = response.getModule();
        }

        itemCommentReq.setCommand(ActionEnum.QUERY_ITEMCOMMENT.getActionName());
        itemCommentReq.setParam("itemCommentQTO", itemCommentQTO);
        itemCommentReq.setParam("appKey", appKey);

        Response<ItemCommentDTO> itemResp = this.getItemService().execute(itemCommentReq);

        if(itemResp.getCode() != ResponseCode.SUCCESS.getCode()){
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }else{
            List<ItemCommentDTO> itemCommentDTOs = (List<ItemCommentDTO>)itemResp.getModule();
            List<MopItemCommentDTO> mopItemCommentDTOs = MopApiUtil.genMopItemCommentList(itemCommentDTOs);
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("comment_list", mopItemCommentDTOs);
            data.put("good_comment_number", countCommentDTO.getGood());
            data.put("mid_comment_number", countCommentDTO.getMid());
            data.put("bad_comment_number", countCommentDTO.getBad());
            data.put("total_count", itemResp.getTotalCount());
            return new MopResponse(data);
        }
    }

    public String getName() {
        return "/item/comment/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
