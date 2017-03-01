package com.mockuai.virtualwealthcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.DEDUCT_VIRTUAL_WEALTH;

/**
 * Created by edgar.zr 11/20/15
 * 扣减用户虚拟财富
 */
public class DeductUserVirtualWealth extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeductUserVirtualWealth.class.getName());

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String amountStr = (String) request.getParam("deduct_amount");
        String wealthTypeStr = (String) request.getParam("wealth_type");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        Long amount;
        Integer wealthType;
        try {
            wealthType = Integer.valueOf(wealthTypeStr);
            amount = Long.valueOf(amountStr);
        } catch (Exception e) {
            LOGGER.error("error of parsing, wealthTypeStr : {}, amountStr : {}", wealthTypeStr, amountStr, e);
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
        }

        Request marketReq = new BaseRequest();
        marketReq.setCommand(DEDUCT_VIRTUAL_WEALTH.getActionName());
        marketReq.setParam("userId", userId);
        marketReq.setParam("wealthType", wealthType);
        marketReq.setParam("amount", amount);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getVirtualWealthService().execute(marketReq);
        MopResponse response;
        if (marketResp.isSuccess()) {
            response = new MopResponse(MopRespCode.REQUEST_SUCESS);
        } else {
            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }
        return response;
    }

    public String getName() {
        return "/marketing/virtual_wealth/deduct";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}