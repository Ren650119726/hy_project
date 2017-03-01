package com.mockuai.virtualwealthcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.VirtualWealthUidDTO;
import com.mockuai.virtualwealthcenter.common.util.MopApiUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.GRANT_VIRTUAL_WEALTH;

/**
 * Created by zengzhangqiang on  5/31/15.
 */
public class GrantVirtualWealth extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrantVirtualWealth.class.getName());

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String virtualWealthUid = (String) request.getParam("virtual_wealth_uid");
        String wealthTypeStr = (String) request.getParam("wealth_type");
        String sourceTypeStr = (String) request.getParam("source_type");
        String grantAmountStr = (String) request.getParam("grant_amount");
        String receiverIdStr = (String) request.getParam("receiver_id");
        String appKey = (String) request.getParam("app_key");

        Long virtualWealthId = null;
        Long creatorId = null;
        Long receiverId;
        Long grantAmount;
        Integer wealthType;
        Integer sourceType;
        try {
            VirtualWealthUidDTO virtualWealthUidDTO = MopApiUtil.parseVirtualWealthUid(virtualWealthUid);
            if (virtualWealthUidDTO != null) {
                virtualWealthId = virtualWealthUidDTO.getVirtualWealthId();
                creatorId = virtualWealthUidDTO.getCreatorId();
            }
            receiverId = Long.valueOf(receiverIdStr);
            grantAmount = Long.valueOf(grantAmountStr);
            if (StringUtils.isBlank(wealthTypeStr)) {
                wealthType = WealthType.VIRTUAL_WEALTH.getValue();
            } else {
                wealthType = Integer.valueOf(wealthTypeStr);
            }
            if (StringUtils.isBlank(sourceTypeStr)) {
                sourceType = SourceType.OTHER.getValue();
            } else {
                sourceType = Integer.valueOf(sourceTypeStr);
            }
        } catch (Exception e) {
            LOGGER.error("error of parsing, virtualWealthUid : {}, wealthTypeStr : {}, sourceTypeStr : {}, grantAmount : {}, receiverId : {}",
                    virtualWealthUid, wealthTypeStr, sourceTypeStr, grantAmountStr, receiverIdStr, e);
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
        }

        Request marketReq = new BaseRequest();
        marketReq.setCommand(GRANT_VIRTUAL_WEALTH.getActionName());
        marketReq.setParam("virtualWealthId", virtualWealthId);
        marketReq.setParam("wealthCreatorId", creatorId);
        marketReq.setParam("wealthType", wealthType);
        marketReq.setParam("sourceType", sourceType);
        marketReq.setParam("grantAmount", grantAmount);
        marketReq.setParam("receiverId", receiverId);
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
        return "/marketing/virtual_wealth/grant";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}