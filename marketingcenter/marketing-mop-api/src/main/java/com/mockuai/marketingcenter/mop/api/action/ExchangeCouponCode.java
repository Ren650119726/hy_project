package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.EXCHANGE_COUPON_CODE;

/**
 * Created by edgar.zr on 11/5/15.
 * 兑换优惠码
 */
public class ExchangeCouponCode extends BaseAction {

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

        String code = (String) request.getParam("coupon_code");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        if (StringUtils.isEmpty(code)) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "code is empty");
        }
        Request marketReq = new BaseRequest();
        marketReq.setCommand(EXCHANGE_COUPON_CODE.getActionName());
        marketReq.setParam("code", code);
        marketReq.setParam("userId", userId);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getMarketingService().execute(marketReq);
        MopResponse response;
        if (marketResp.isSuccess()) {
            Long grantedCouponId = (Long) marketResp.getModule();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("coupon_uid", userId + "_" + grantedCouponId);
            response = new MopResponse(data);
        } else {
            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/activity_coupon/exchange";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}