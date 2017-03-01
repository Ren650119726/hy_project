package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemCommentDTO;
import com.mockuai.itemcenter.mop.api.domain.OrderUidDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/17/15.
 */
public class AddItemComment extends BaseAction{
    public MopResponse execute(Request request) {
        String itemCommentListStr = (String)request.getParam("item_comment_list");
        String appKey = (String)request.getParam("app_key");
        java.lang.reflect.Type type = new TypeToken<List<MopItemCommentDTO>>() {}.getType();
        List<MopItemCommentDTO> itemCommentDTOs = JsonUtil.parseJson(itemCommentListStr, type);

        String userName = (String)request.getAttribute("user_name");
        if(!CollectionUtils.isEmpty(itemCommentDTOs)) {
            for(MopItemCommentDTO mopItemCommentDTO: itemCommentDTOs) {
                mopItemCommentDTO.setUserName(userName);
            }
        }

        com.mockuai.itemcenter.common.api.Request itemCommentReq = new BaseRequest();
        itemCommentReq.setCommand(ActionEnum.ADD_ITEMCOMMENT.getActionName());
        itemCommentReq.setParam("itemCommentList", MopApiUtil.genItemCommentList(itemCommentDTOs));
        itemCommentReq.setParam("appKey", appKey);
        Response<List<ItemCommentDTO>> itemResp = this.getItemService().execute(itemCommentReq);

        if(itemResp.getCode() != ResponseCode.SUCCESS.getCode()){
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }else{
            List<ItemCommentDTO>  itemCommentDTOList = (List<ItemCommentDTO>)itemResp.getModule();
            Map<String,Object> data = new HashMap<String, Object>();
            List<String> commentUidList = new ArrayList<String>();
            for(ItemCommentDTO itemComment: itemCommentDTOList){
                commentUidList.add(MopApiUtil.genCommentUid(itemComment.getId(),
                        itemComment.getSellerId(), itemComment.getUserId() ));
            }
            data.put("comment_uid_list", JsonUtil.toJson(commentUidList));

            return new MopResponse(data);
        }

    }

    public String getName() {
        return "/item/comment/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
