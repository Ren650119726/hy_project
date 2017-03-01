package com.mockuai.marketingcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.mop.MopMarketActivityDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.marketingcenter.mop.api.util.JsonUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.ADD_ACTIVITY;

/**
 * Created by zengzhangqiang on 5/31/15.
 * 创建活动主体
 */
public class AddActivity extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String marketActivityStr = (String) request.getParam("market_activity");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        if (StringUtils.isBlank(marketActivityStr)) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "market_activity is null");
        }

        MarketActivityDTO marketActivityDTO;
        try {
            MopMarketActivityDTO mopMarketActivityDTO = JsonUtil.parseJson(marketActivityStr, MopMarketActivityDTO.class);
            marketActivityDTO = MopApiUtil.genMarketActivityDTO(mopMarketActivityDTO);
            marketActivityDTO.setCreatorId(userId);//设置创建者id为当前操作用户的id
        } catch (Exception e) {
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "market_activity format is invalid");
        }

        Request marketReq = new BaseRequest();
        marketReq.setCommand(ADD_ACTIVITY.getActionName());
        marketReq.setParam("marketActivityDTO", marketActivityDTO);
        marketReq.setParam("appKey", appKey);
        Response<Long> marketResp = getMarketingService().execute(marketReq);
        MopResponse response;
        if (marketResp.isSuccess()) {
            Long activityId = marketResp.getModule();
            marketActivityDTO.setId(activityId);
            String activityUid = MopApiUtil.genActivityUid(marketActivityDTO);
            Map<String, String> data = new HashMap<String, String>();
            data.put("activity_uid", activityUid);
            response = new MopResponse(data);
        } else {
            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }


    public String getName() {
        return "/marketing/market_activity/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}