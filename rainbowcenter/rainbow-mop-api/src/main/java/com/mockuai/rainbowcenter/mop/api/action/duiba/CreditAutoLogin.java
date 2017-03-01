package com.mockuai.rainbowcenter.mop.api.action.duiba;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.dto.CreditAutoLoginDTO;
import com.mockuai.rainbowcenter.mop.api.action.BaseAction;
import com.mockuai.rainbowcenter.mop.api.util.MopApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by lizg on 2016/9/19.
 */
public class CreditAutoLogin extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(CreditAutoLogin.class);


    public MopResponse execute(Request request) {
        String uid = (String) request.getParam("uid");
        String credits = (String) request.getParam("credits");
        log.info("[{}] uid:{}",uid);
        log.info("[{}] credits:{}",credits);
        if (null == uid) {
            log.error("uid is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "uid is null");
        }
        if (null == credits) {
            log.error("credits is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "credits is null");
        }
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("uid",uid);
        baseRequest.setParam("credits",credits);
        baseRequest.setCommand(ActionEnum.DUIBA_CREDIT_AUTO_LOGIN.getActionName());
        Response<CreditAutoLoginDTO> response = getRainbowService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("duiba creditAutoLogin error,uid:{}, msg: {}", uid, response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }

        return new MopResponse(MopApiUtil.getCreditAutoLogDTO(response.getModule()));
    }


    public String getName() {
        return "/duiba/build/creditAutoLog";
    }


    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }


    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
