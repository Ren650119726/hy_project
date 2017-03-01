package com.mockuai.virtualwealthcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.LIST_RECORD_OF_WEALTH;

/**
 * Created by edgar.zr on 5/16/2016.
 */
public class ListRecordOfWealth extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListRecordOfWealth.class);

    public MopResponse execute(Request request) {
        String tradeTypeStr = (String) request.getParam("trade_type");
        String wealthTypeStr = (String) request.getParam("wealth_type");
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        Integer tradeType;
        Integer wealthType;
        Integer offset;
        Integer count;
        try {
            if (StringUtils.isNotBlank(tradeTypeStr)) {
                tradeType = Integer.valueOf(tradeTypeStr);
            } else {
                tradeType = 0;
            }
            if (StringUtils.isNotBlank(wealthTypeStr)) {
                wealthType = Integer.valueOf(wealthTypeStr);
            } else {
                wealthType = 1;
            }
            offset = Integer.valueOf(offsetStr);
            count = Integer.valueOf(countStr);

        } catch (Exception e) {
            LOGGER.error("error of parsing, tradeTypeStr : {}, wealthTypeStr : {}, offsetStr : {}, countStr : {}",
                    tradeTypeStr, wealthTypeStr, offsetStr, countStr, e);
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
        }

        com.mockuai.virtualwealthcenter.common.api.Request marketReq = new BaseRequest();
        marketReq.setCommand(LIST_RECORD_OF_WEALTH.getActionName());
        marketReq.setParam("wealthType", wealthType);
        marketReq.setParam("tradeType", tradeType);
        marketReq.setParam("offset", offset);
        marketReq.setParam("count", count);
        marketReq.setParam("userId", userId);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getVirtualWealthService().execute(marketReq);

        MopResponse response;
        if (marketResp.isSuccess()) {
            Map data = new HashMap();
            Map<String, Object> map = (Map<String, Object>) marketResp.getModule();
            data.put("wealth_log_list", map.get("data"));
            data.put("total_count", map.get("totalCount"));
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/wealth_account/record/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}