package com.mockuai.tradecenter.mop.api.action;

import java.util.List;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.domain.MopItemCommentDTO;
import com.mockuai.tradecenter.mop.api.domain.SkuUidDTO;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

/**
 * Created by zengzhangqiang on 5/17/15.
 */
public class AddOrderComment extends BaseAction{
    @Override
    public MopResponse execute(Request request) {
        String itemCommentListStr = (String)request.getParam("item_comment_list");
        String appKey = (String)request.getParam("app_key");
//        String orderUidStr = (String)request.getParam("order_uid");
        
        
        if(StringUtils.isBlank(itemCommentListStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "item_comment_list is null");
        }

        java.lang.reflect.Type type = new TypeToken<List<MopItemCommentDTO>>() {}.getType();
        List<MopItemCommentDTO> itemCommentDTOs = JsonUtil.parseJson(itemCommentListStr, type);
        OrderUidDTO orderUidDTO = null;
//        try{
//            orderUidDTO = ModelUtil.parseOrderUid(orderUidStr);
//        }catch(Exception e){
//            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "order_uid's format is invalid");
//        }
        
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.COMMENT_ORDER.getActionName());
        tradeReq.setParam("itemCommentList", MopApiUtil.genItemCommentList(itemCommentDTOs));
        tradeReq.setParam("appKey", appKey);
//        tradeReq.setParam("userId", orderUidDTO.getUserId());
//        tradeReq.setParam("orderId", orderUidDTO.getOrderId());
        
        //
        Response<List<ItemCommentDTO>> tradeResp = this.getTradeService().execute(tradeReq);

        if(tradeResp.getCode() != ResponseCode.RESPONSE_SUCCESS.getCode()){
            return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
        }else{
            return new MopResponse(MopRespCode.REQUEST_SUCESS);
        }

    }
    
    
  

    @Override
    public String getName() {
        return "/trade/order/item/comment/add";
    }

    @Override
    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    @Override
    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
