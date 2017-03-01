package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

public class AddUserBrowseLogAction extends BaseAction {

    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
    	Long userId = Long.valueOf((String) request.getAttribute("user_id"));
        String nickName = (String)request.getParam("nick_name");//用户昵称
        Long storeId = (Long)request.getParam("store_id");//店铺id
        Long goodsId = (Long)request.getParam("goods_id");//商品id
        String appKey = (String)request.getParam("app_key");//应用接入标识码
                
        if(null == userId){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "保存用户访问信息异常");
        }
        
        if(null == nickName){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "保存用户访问信息异常");
        }
        
        if(null == storeId){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "保存用户访问信息异常");
        }
        
        if(null == goodsId){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "保存用户访问信息异常");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("nickName", nickName);
        userReq.setParam("storeId", storeId);
        userReq.setParam("goodsId", goodsId);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.ADDUSERBROWSELOG.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/browseLog/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
