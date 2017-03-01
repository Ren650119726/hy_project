package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.mop.ActivityCouponUidDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.GRANT_ACTIVITY_COUPON;

/**
 * 领取优惠券
 * 用户为登陆状态
 * Created by edgar.zr on 8/19/15.
 */
public class ReceiveActivityCoupon extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveActivityCoupon.class.getName());

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

        String activityCouponUid = (String) request.getParam("activity_coupon_uid");
        // 透传, 在接口文档中没有写出
        Long receiverId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        ActivityCouponUidDTO activityCouponUidDTO = MopApiUtil.parseActivityCouponUid(activityCouponUid);
        if (activityCouponUidDTO == null) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL.getCode(), "activity_coupon_uid is null");
        }
        Long activityCouponId = activityCouponUidDTO.getActivityCouponId();
        Long creatorId = activityCouponUidDTO.getCreatorId();

        Request marketReq = new BaseRequest();
        marketReq.setCommand(GRANT_ACTIVITY_COUPON.getActionName());
        marketReq.setParam("activityCouponId", activityCouponId);
        marketReq.setParam("creatorId", creatorId);
        marketReq.setParam("receiverId", receiverId);
        marketReq.setParam("appKey", appKey);

        Response marketResp = getMarketingService().execute(marketReq);
        MopResponse response = null;

        if (marketResp.isSuccess()) {
            response = new MopResponse(MopRespCode.REQUEST_SUCESS);
        } else {
            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/activity_coupon/receive";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}