package com.mockuai.marketingcenter.mop.api.action;

import com.google.gson.reflect.TypeToken;
import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.common.domain.dto.mop.ParamMarketItemDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.marketingcenter.mop.api.util.JsonUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.GET_SETTLEMENT_INFO;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class GetSettlement extends BaseAction {
	private static final Logger log = LoggerFactory.getLogger(GetSettlement.class);
	
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String orderItemListStr = (String) request.getParam("market_item_list");
        String consigneeUid = (String) request.getParam("consignee_uid");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");       

        java.lang.reflect.Type type = new TypeToken<List<ParamMarketItemDTO>>() {
        }.getType();
        List<ParamMarketItemDTO> paramMarketItemDTOs = JsonUtil.parseJson(orderItemListStr, type);

        Request marketReq = new BaseRequest();
        marketReq.setCommand(GET_SETTLEMENT_INFO.getActionName());
        marketReq.setParam("consigneeId", MopApiUtil.parseConsigneeId(consigneeUid));
        marketReq.setParam("itemList", MopApiUtil.genMarketingItemDTOList(paramMarketItemDTOs));
        marketReq.setParam("userId", userId);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getMarketingService().execute(marketReq);
        MopResponse response;
        if (marketResp.isSuccess()) {
            SettlementInfo settlementInfo = (SettlementInfo) marketResp.getModule();
            Map data = new HashMap();
            data.put("settlement_info", MopApiUtil.genMopSettlementInfo(settlementInfo));
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/trade/order/settlement/distribute/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}