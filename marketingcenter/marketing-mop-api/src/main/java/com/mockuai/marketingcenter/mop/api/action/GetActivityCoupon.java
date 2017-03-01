package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.mop.ActivityCouponUidDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.GET_ACTIVITY_COUPON;

/**
 * Created by edgar.zr on 8/19/15.
 * 获取指定优惠券
 */
public class GetActivityCoupon extends BaseAction {

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

        String activityCouponUid = (String) request.getParam("activity_coupon_uid");
        String appKey = (String) request.getParam("app_key");

        Request marketReq = new BaseRequest();

        marketReq.setCommand(GET_ACTIVITY_COUPON.getActionName());

        ActivityCouponUidDTO activityCouponUidDTO = MopApiUtil.parseActivityCouponUid(activityCouponUid);
        if (activityCouponUidDTO == null) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL.getCode(), "activity_coupon_uid is null");
        }
        Long activityCouponId = activityCouponUidDTO.getActivityCouponId();
        Long creatorId = activityCouponUidDTO.getCreatorId();

        marketReq.setParam("activityCouponId", activityCouponId);
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
        return "/marketing/activity_coupon/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}