package com.mockuai.imagecenter.mop.api.action;

import com.mockuai.imagecenter.common.api.action.BaseRequest;
import com.mockuai.imagecenter.common.api.action.Response;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;

public class GetQrcodeMopAction extends BaseAction{

		public String getName() {
			return "qrcode/get";
		}
		
		public ActionAuthLevel getAuthLevel() {
			return ActionAuthLevel.NO_AUTH;
		}

		public HttpMethodLimit getMethodLimit() {
			return HttpMethodLimit.ONLY_GET;
		}


	public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

			MopResponse mopResponse;

			String userId= (String) request.getParam("user_id");
			String appkey = (String) request.getParam("app_key");
			if(userId == null){
				return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"userId is null");
			}

			if(appkey ==null){
				return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"appkey is null");
			}
			BaseRequest baseRequest  = new BaseRequest();
			baseRequest.setParam("id",Long.parseLong(userId));
			baseRequest.setParam("appKey",appkey);
			baseRequest.setCommand(ActionEnum.GET_SHOP_RECOMMEND_QRCODE.getActionName());
			Response codeResponse = getImageService().execute(baseRequest);
			if (codeResponse.isSuccess()) {
				mopResponse = new MopResponse(codeResponse.getModule());
			} else {
				mopResponse = new MopResponse(codeResponse.getCode(), codeResponse.getMessage());
			}
	        return mopResponse;

		}


}
