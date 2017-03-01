package com.mockuai.distributioncenter.mop.api.action.shop;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mockuai.distributioncenter.common.constant.ActionEnum.GET_SHOP_QRCODE;

/**
 * Created by 冠生 on 2016/5/23.
 * 查看店铺二维码
 */
public class GetShopQrCode  extends BaseAction{
    private static final Logger log = LoggerFactory.getLogger(GetShopQrCode.class);


    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        if (userId == null) {
            log.error("userId is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "userId is null");
        }
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(GET_SHOP_QRCODE.getActionName());
        baseRequest.setParam("appKey",appKey);
        baseRequest.setParam("userId",userId);
        Response response = getDistributionService().execute(baseRequest);
        if(response.isSuccess()){
            return new MopResponse(response.getModule());
        }
        return new MopResponse(response.getCode(),response.getMessage());
    }

    public String getName() {
        return "/dist/shop/code/get";

    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
