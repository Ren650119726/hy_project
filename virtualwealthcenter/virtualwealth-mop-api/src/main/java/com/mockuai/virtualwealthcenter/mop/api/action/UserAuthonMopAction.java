package com.mockuai.virtualwealthcenter.mop.api.action;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.GRANT_VIRTUAL_WEALTH_BATCH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.ActionLoader;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.USER_AUTHON_ADD;

public class UserAuthonMopAction{
	 //extends BaseAction

//
//	public MopResponse execute(
//			com.mockuai.mop.common.service.action.Request request) {
//		Long userId = (Long)request.getAttribute("user_id");
//	
//		//Long userId = Long.valueOf((String)request.getParam("user_id"));
//		
//        //发卡银行
//        String authon_bankname = (String)request.getParam("bank_name");
//        //银行卡号
//        String authon_no = (String)request.getParam("bank_no");
//
//        //银行卡认证信息
//        String authon_text = (String)request.getParam("bank_authon_text");
//        //持卡人姓名
//        String authon_realname = (String)request.getParam("bank_realname");
//        //身份证号
//        String authon_personalid = (String)request.getParam("bank_personal_id");
//        //手机号
//        String authon_mobile =(String)request.getParam("bank_mobile"); 
//        
//        String appKey = (String) request.getParam("app_key");
//        
//
//	 	Request marketReq = new BaseRequest();
//        marketReq.setCommand(USER_AUTHON_ADD.getActionName());
//	 	marketReq.setParam("userId", userId);
//        marketReq.setParam("bank_name", authon_bankname);
//        marketReq.setParam("bank_no", authon_no);
//        marketReq.setParam("bank_authon_text", authon_text);
//        marketReq.setParam("bank_realname", authon_realname);
//        marketReq.setParam("bank_personal_id", authon_personalid);
//	 	marketReq.setParam("bank_mobile", authon_mobile);
//	 	marketReq.setParam("appKey", appKey);
//	 	
//	 	//记得加验证码
//        Response marketResp = getVirtualWealthService().execute(marketReq);
//        MopResponse response;
//		if (marketResp.isSuccess()) {
//            response = new MopResponse(MopRespCode.REQUEST_SUCESS);
//        } else {
//            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
//        }
//        return response;
//
//	}
//	
//	//请求要实名认证
//	public String getName() {
//		return "/user/authon/save";
//	}
//
//	public ActionAuthLevel getAuthLevel() {
//		return ActionAuthLevel.AUTH_LOGIN;
//	}
//
//	public HttpMethodLimit getMethodLimit() {
//		return HttpMethodLimit.ONLY_POST;
//	}

}
