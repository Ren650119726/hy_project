package com.mockuai.marketingcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.GENERATE_ACTIVITY_COUPON;

/**
 * Created by zengzhangqiang on 5/31/15.
 * <p/>
 * 创建优惠券
 */
public class GenerateActivityCoupon extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateActivityCoupon.class.getName());

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String activityUid = (String) request.getParam("activity_uid");
        String couponTypeStr = (String) request.getParam("coupon_type");
        String totalCountStr = (String) request.getParam("total_count");
        String appKey = (String) request.getParam("app_key");

        Long activityId;
        Long creatorId;
        Integer couponType = null;
        Long totalCount = null;
        try {
            ActivityUidDTO activityUidDTO = MopApiUtil.parseActivityUid(activityUid);
            if (activityUidDTO == null) {
                return new MopResponse(MopRespCode.P_E_PARAM_ISNULL.getCode(), "activity_uid is null");
            }
            activityId = activityUidDTO.getActivityId();
            creatorId = activityUidDTO.getCreatorId();

            if (StringUtils.isNotEmpty(couponTypeStr)) {
                couponType = Integer.valueOf(couponTypeStr);
            }

            if (StringUtils.isNotEmpty(totalCountStr)) {
                totalCount = Long.valueOf(totalCountStr);
            }
        } catch (Exception e) {
            LOGGER.error("errors of parse the parameters, activityUid : {}, couponType : {}, totalCount : {}",
                    activityUid, couponTypeStr, totalCountStr);
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
        }

        ActivityCouponDTO activityCouponDTO = new ActivityCouponDTO();
        activityCouponDTO.setActivityId(activityId);
        activityCouponDTO.setActivityCreatorId(creatorId);
        activityCouponDTO.setCouponType(couponType);
        activityCouponDTO.setTotalCount(totalCount);

        Request marketReq = new BaseRequest();
        marketReq.setCommand(GENERATE_ACTIVITY_COUPON.getActionName());
        marketReq.setParam("activityCouponDTO", activityCouponDTO);
        marketReq.setParam("appKey", appKey);
        Response<Long> marketResp = getMarketingService().execute(marketReq);
        MopResponse response = null;
        if (marketResp.isSuccess()) {
            Long couponId = marketResp.getModule();
            activityCouponDTO.setId(couponId);
            String activityCouponUid = MopApiUtil.genActivityCouponUid(activityCouponDTO);
            Map<String, String> data = new HashMap<String, String>();
            data.put("activity_coupon_uid", activityCouponUid);
            response = new MopResponse(data);
        } else {
            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/activity_coupon/generate";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}