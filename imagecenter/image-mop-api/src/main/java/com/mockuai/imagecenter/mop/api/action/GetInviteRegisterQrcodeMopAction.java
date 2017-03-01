package com.mockuai.imagecenter.mop.api.action;

import com.mockuai.imagecenter.common.api.action.BaseRequest;
import com.mockuai.imagecenter.common.api.action.Response;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.mop.api.domain.MopImageDTO;
import com.mockuai.imagecenter.mop.api.utils.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lizg on 2016/12/1.
 */
public class GetInviteRegisterQrcodeMopAction extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(GetInviteRegisterQrcodeMopAction.class);

    public MopResponse execute(Request request) {
        String shareUserId =(String) request.getParam("share_user_id");
        String appkey = (String) request.getParam("app_key");
        if(shareUserId == null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"shareUserId is null");
        }

        if(appkey ==null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"appkey is null");
        }
        BaseRequest baseRequest  = new BaseRequest();
        baseRequest.setParam("shareUserId",Long.parseLong(shareUserId));
        baseRequest.setParam("appKey",appkey);
        baseRequest.setCommand(ActionEnum.INVITE_REGISTER_QRCODE.getActionName());
        Response<ImageDTO> codeResponse = getImageService().execute(baseRequest);


        if (!codeResponse.isSuccess()) {
            log.error("get inviter error, errMsg: {}", codeResponse.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, codeResponse.getMessage());
        }

        MopImageDTO mopImageDTO = MopApiUtil.getMopSellerDTO(codeResponse.getModule());

        return new MopResponse(mopImageDTO);
    }

    public String getName() {
        return "/register/qrcode/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
