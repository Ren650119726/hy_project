package com.mockuai.messagecenter.mop.api.action;

import org.apache.commons.lang.StringUtils;

import com.mockuai.messagecenter.common.action.ActionEnum;
import com.mockuai.messagecenter.common.api.BaseRequest;
import com.mockuai.messagecenter.common.api.Response;
import com.mockuai.messagecenter.common.constant.ResponseCode;
import com.mockuai.messagecenter.common.qto.VerifySmsQTO;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

public class SmsMobileVerifyAction extends BaseAction {

	
    public MopResponse execute(Request request) {
        String appKey = (String)request.getParam("app_key");
        String mobile = (String)request.getParam("mobile");
        String handleType = (String)request.getParam("handle_type");

        if(StringUtils.isBlank(mobile)){
    		return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, "参数mobile["+mobile+"]为空");
    	}
        if(StringUtils.isBlank(handleType)){
    		return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, "参数handleType["+handleType+"]为空");
    	}
        if(StringUtils.isBlank(appKey)){
    		return new MopResponse(MopRespCode.S_E_SERVICE_ERROR.getCode(), "app_key["+(String)request.getParam("app_key")+"]为空");
    	}
        
        VerifySmsQTO verifySmsQTO = new VerifySmsQTO();
        verifySmsQTO.setMobile(mobile);
        verifySmsQTO.setHandleType(handleType);
		
        com.mockuai.messagecenter.common.api.Request mesReq = new BaseRequest();
        mesReq.setParam("verifySmsQTO", verifySmsQTO);
        mesReq.setParam("appKey", appKey);
        mesReq.setCommand(ActionEnum.SMS_SERVICE.getActionName());
        Response<String> userResp = this.getMessageDispatchService().execute(mesReq);

        if(userResp.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
        	return new MopResponse(userResp.getCode(), userResp.getMessage());
        }else{
            String result = userResp.getModule();
            return new MopResponse(MopRespCode.REQUEST_SUCESS.getCode(),result);
        }
    }

    public String getName() {
        return "/message/mobile_verify";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }


}
