package com.mockuai.virtualwealthcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.util.MopApiUtil;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.GET_BALANCE;

/**
 * Created by edgar.zr on 5/13/2016.
 */
public class WealthAccountOfBalance extends BaseAction {

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        Request marketReq = new BaseRequest();
        marketReq.setCommand(GET_BALANCE.getActionName());
        marketReq.setParam("userId", userId);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getVirtualWealthService().execute(marketReq);
        MopResponse response;
        if (marketResp.isSuccess()) {
            Map data = new HashMap();
            WealthAccountDTO wealthAccountDTO = (WealthAccountDTO) marketResp.getModule();
            data.put("wealth_account", MopApiUtil.genMopWealthAccountDTO(wealthAccountDTO));
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/wealth_account/balance";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}