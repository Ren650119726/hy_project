package com.mockuai.virtualwealthcenter.mop.api.action;

import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.QUERY_VIRTUAL_WEALTH_WITH_GRANT_RULE;

/**
 * Created by edgar.zr on 11/11/15.
 */
public class GetVirtualWealthWithGrantRule extends BaseAction {

    public MopResponse execute(Request request) {

        String sourceType = (String) request.getParam("source_type");
        String wealthType = (String) request.getParam("wealth_type");
        String appKey = (String) request.getParam("app_key");

        com.mockuai.virtualwealthcenter.common.api.Request marketReq = new BaseRequest();
        marketReq.setCommand(QUERY_VIRTUAL_WEALTH_WITH_GRANT_RULE.getActionName());
        marketReq.setParam("sourceType", Integer.parseInt(sourceType));
        marketReq.setParam("wealthType", Integer.parseInt(wealthType));
        marketReq.setParam("appKey", appKey);
        Response marketResp = getVirtualWealthService().execute(marketReq);
        MopResponse response;
        if (marketResp.isSuccess()) {
            Map<String, Object> result = (Map<String, Object>) marketResp.getModule();
            Map data = new HashMap();
            data.put("virtual_wealth", MopApiUtil.genMopVirtualWealthDTO((VirtualWealthDTO) result.get("virtualWealth")));
            data.put("grant_rule", MopApiUtil.genMopGrantRuleDTO((GrantRuleDTO) result.get("grantRule")));
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }
        return response;
    }

    public String getName() {
        return "/marketing/virtual_wealth/grant_rule/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}