package com.mockuai.virtualwealthcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.util.MopApiUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.QUERY_WEALTH_ACCOUNT;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class ListWealthAccount extends BaseAction {

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String typeStr = (String) request.getParam("wealth_type");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        Integer type = null;
        if (StringUtils.isNotBlank(typeStr)) {
            int typeValue = Integer.valueOf(typeStr);
            if (typeValue > 0) {
                type = typeValue;
            }
        }

        Request marketReq = new BaseRequest();
        marketReq.setCommand(QUERY_WEALTH_ACCOUNT.getActionName());
        marketReq.setParam("userId", userId);
        marketReq.setParam("wealthType", type);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getVirtualWealthService().execute(marketReq);
        MopResponse response;
        if (marketResp.isSuccess()) {
            Map data = new HashMap();
            List<WealthAccountDTO> wealthAccountDTOs = (List<WealthAccountDTO>) marketResp.getModule();
            data.put("wealth_account_list", MopApiUtil.genMopWealthAccountDTOList(wealthAccountDTOs));
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/wealth_account/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}