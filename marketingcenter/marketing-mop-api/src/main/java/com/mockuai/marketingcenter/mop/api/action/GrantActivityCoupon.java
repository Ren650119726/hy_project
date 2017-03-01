package com.mockuai.marketingcenter.mop.api.action;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.GRANT_ACTIVITY_COUPON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.constant.GrantSourceEnum;
import com.mockuai.marketingcenter.common.domain.dto.mop.ActivityUidDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;

/**
 * Created by zengzhangqiang on 5/31/15.
 */
public class GrantActivityCoupon extends BaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(GrantActivityCoupon.class.getName());

	public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
		String activityUid = (String) request.getParam("activity_uid");
		Long userId = (Long) request.getAttribute("user_id");
		String appKey = (String) request.getParam("app_key");

		Long activityId;
		Long creatorId;
		try {
			ActivityUidDTO activityUidDTO = MopApiUtil.parseActivityUid(activityUid);
			if (activityUidDTO == null) {
				return new MopResponse(MopRespCode.P_E_PARAM_ISNULL.getCode(), "activity_uid is null");
			}
			activityId = activityUidDTO.getActivityId();
			creatorId = activityUidDTO.getCreatorId();
		} catch (Exception e) {
			LOGGER.error("errors of parsing the parameters, activityCouponUid : {}", activityUid);
			return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
		}

		Request marketReq = new BaseRequest();
		marketReq.setCommand(GRANT_ACTIVITY_COUPON.getActionName());
		marketReq.setParam("activityId", activityId);
		marketReq.setParam("creatorId", creatorId);
		marketReq.setParam("grantSource", GrantSourceEnum.RECEIVE.getValue());
		marketReq.setParam("receiverId", userId);
		marketReq.setParam("appKey", appKey);
		Response marketResp = getMarketingService().execute(marketReq);
		MopResponse response;
		if (marketResp.isSuccess()) {
			response = new MopResponse(MopRespCode.REQUEST_SUCESS);
		} else {
			response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
		}

		return response;
	}

	public String getName() {
		return "/marketing/activity_coupon/grant";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}
}