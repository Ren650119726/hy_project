package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.mop.ActivityUidDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.GET_ACTIVITY_COUPON_BY_ACTIVITY;

/**
 * Created by edgar.zr on 1/21/16.
 */
public class GetActivityCouponByActivity extends BaseAction {

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

        String activityUid = (String) request.getParam("activity_uid");
        String appKey = (String) request.getParam("app_key");

        Request marketReq = new BaseRequest();

        marketReq.setCommand(GET_ACTIVITY_COUPON_BY_ACTIVITY.getActionName());

        ActivityUidDTO activityUidDTO = MopApiUtil.parseActivityUid(activityUid);
        if (activityUidDTO == null) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL.getCode(), "activity_uid is null");
        }
        Long activityId = activityUidDTO.getActivityId();
        Long creatorId = activityUidDTO.getCreatorId();

        marketReq.setParam("activityId", activityId);
        marketReq.setParam("creatorId", creatorId);
        marketReq.setParam("appKey", appKey);

        Response marketResp = getMarketingService().execute(marketReq);

        MopResponse response;
        if (marketResp.isSuccess()) {
            ActivityCouponDTO activityCouponDTO = (ActivityCouponDTO) marketResp.getModule();
            Map data = new HashMap();
            data.put("activity_coupon", MopApiUtil.genMopActivityCouponDTO(activityCouponDTO));
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/activity_coupon/get_by_activity";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}