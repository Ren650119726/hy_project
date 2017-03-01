package com.mockuai.virtualwealthcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopWithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.mop.api.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.DETAIL_OF_WITHDRAWALS;

/**
 * Created by edgar.zr on 5/21/2016.
 */
public class DetailOfWithdrawals extends BaseAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailOfWithdrawals.class);

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String withdrawalsNumberStr = (String) request.getParam("withdrawals_number");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        Request marketReq = new BaseRequest();
        marketReq.setCommand(DETAIL_OF_WITHDRAWALS.getActionName());
        marketReq.setParam("withdrawalsNumber", withdrawalsNumberStr);
        marketReq.setParam("userId", userId);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getVirtualWealthService().execute(marketReq);

        MopResponse response;
        if (marketResp.isSuccess()) {
            Map data = new HashMap();
            MopWithdrawalsItemDTO mopWithdrawalsItemDTO = (MopWithdrawalsItemDTO) marketResp.getModule();
            LOGGER.info("wi : {}", JsonUtil.toJson(mopWithdrawalsItemDTO));
            data.put("withdrawals_item", mopWithdrawalsItemDTO);
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/virtualwealth/withdrawals/detail";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}