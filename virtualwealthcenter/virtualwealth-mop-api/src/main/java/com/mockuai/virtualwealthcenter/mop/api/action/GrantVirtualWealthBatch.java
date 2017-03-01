package com.mockuai.virtualwealthcenter.mop.api.action;

import com.google.gson.reflect.TypeToken;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.GrantInfoDTO;
import com.mockuai.virtualwealthcenter.mop.api.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.GRANT_VIRTUAL_WEALTH_BATCH;

/**
 * Created by edgar.zr on 12/21/15.
 */
public class GrantVirtualWealthBatch extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrantVirtualWealth.class.getName());

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

        String wealthTypeStr = (String) request.getParam("wealth_type");
        String sourceTypeStr = (String) request.getParam("source_type");
        String grantInfoListStr = (String) request.getParam("grant_info_list");
        String appKey = (String) request.getParam("app_key");

        Integer wealthType;
        Integer sourceType;
        List<String> mobiles = new ArrayList<String>();
        List<Long> grantAmounts = new ArrayList<Long>();

        try {
            java.lang.reflect.Type type = new TypeToken<List<GrantInfoDTO>>() {
            }.getType();
            List<GrantInfoDTO> grantInfoDTOs = JsonUtil.parseJson(grantInfoListStr, type);
            if (grantInfoDTOs != null) {
                for (GrantInfoDTO grantInfoDTO : grantInfoDTOs) {
                    mobiles.add(grantInfoDTO.getMobile());
                    grantAmounts.add(grantInfoDTO.getGrantAmount());
                }
            }
            wealthType = Integer.valueOf(wealthTypeStr);
            sourceType = Integer.valueOf(sourceTypeStr);
        } catch (Exception e) {
            LOGGER.error("error of parsing, grantInfoListStr: {}, wealthTypeStr : {}, sourceTypeStr : {}",
                    grantInfoListStr, wealthTypeStr, sourceTypeStr, e);
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
        }

        Request marketReq = new BaseRequest();
        marketReq.setCommand(GRANT_VIRTUAL_WEALTH_BATCH.getActionName());
        marketReq.setParam("wealthType", wealthType);
        marketReq.setParam("sourceType", sourceType);
        marketReq.setParam("mobiles", mobiles);
        marketReq.setParam("grantAmounts", grantAmounts);
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
        return "/marketing/virtual_wealth/batch_grant";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}