package com.mockuai.imagecenter.mop.api.action;

import com.mockuai.imagecenter.common.api.action.BaseRequest;
import com.mockuai.imagecenter.common.api.action.Response;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import org.springframework.util.StringUtils;

import java.util.StringTokenizer;

public class GetItemQrcodeMopAction extends BaseAction{

    //提现申请
    public String getName() {
        return "/item/qrcode/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }


    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

        MopResponse mopResponse;

        String itemIdToken  = (String) request.getParam("item_id");
        String shareUserId = (String) request.getParam("share_user_id");
        StringTokenizer token=new StringTokenizer(itemIdToken,"_");
        if(token.countTokens() != 2){
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID,"商品id不正确");
        }
        String sellerId  = token.nextToken();
        String itemId = token.nextToken();
        //String distributorId = (String) request.getParam("distributor_id");
        //String userId = (String) request.getAttribute("user_id");
        String appkey = (String) request.getParam("app_key");
        Long lShareUserId =0L;
        if(itemId == null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"itemId is null");
        }
        if(sellerId == null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"sellerId is null");
        }
			/*if(distributorId ==null){
				return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"distributorId is null");
			}*/
        if(appkey ==null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"appkey is null");
        }
        if(StringUtils.hasText(shareUserId)){
            lShareUserId = Long.parseLong(shareUserId);
        }
           /* Long longUserId ;
            if(userId == null || "".equals(userId)){
                longUserId = null;
            }else{
                longUserId = Long.parseLong(userId);
            }*/
        BaseRequest baseRequest  = new BaseRequest();
        baseRequest.setParam("itemId",Long.parseLong(itemId));
        baseRequest.setParam("sellerId",Long.parseLong(sellerId));
        //baseRequest.setParam("distributorId",Long.parseLong(distributorId));
        baseRequest.setParam("userId",lShareUserId);
        baseRequest.setParam("appKey",appkey);
        baseRequest.setCommand(ActionEnum.GET_ITEM.getActionName());
        Response codeResponse = getImageService().execute(baseRequest);
        if (codeResponse.isSuccess()) {
            mopResponse = new MopResponse(codeResponse.getModule());
        } else {
            mopResponse = new MopResponse(codeResponse.getCode(), codeResponse.getMessage());
        }
        return mopResponse;

    }


}
