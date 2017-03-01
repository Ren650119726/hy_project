package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.PageQTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.marketingcenter.mop.api.util.JsonUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.QUERY_USER_COUPON;

/**
 * Created by edgar.zr on 8/11/15.
 */
public class UserCouponList extends BaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserCouponList.class);

	public MopResponse execute(Request request) {

		Long userId = (Long) request.getAttribute("user_id");
		// 请求参数 status, 30 : 未使用(无过期\无失效) 50 : 已使用(无论过期\失效状态) 60 : 已过期(包括时间过期\失效)
		String status = (String) request.getParam("status");
		String offset = (String) request.getParam("offset");
		String count = (String) request.getParam("count");
		String appKey = (String) request.getParam("app_key");

		GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
		grantedCouponQTO.setReceiverId(userId);

		if (StringUtils.isBlank(status)) {
			grantedCouponQTO.setStatus(UserCouponStatus.UN_USE.getValue());
		} else {
			grantedCouponQTO.setStatus(Integer.valueOf(status));
		}

		if (StringUtils.isBlank(offset)) {
			grantedCouponQTO.setOffset(PageQTO.DEFAULT_OFFSET);
		} else {
			grantedCouponQTO.setOffset(Integer.valueOf(offset));
		}

		if (StringUtils.isBlank(count)) {
			grantedCouponQTO.setCount(PageQTO.DEFAULT_COUNT);
		} else {
			grantedCouponQTO.setCount(Integer.valueOf(count));
		}

		com.mockuai.marketingcenter.common.api.Request marketingRequest = new BaseRequest();
		marketingRequest.setCommand(QUERY_USER_COUPON.getActionName());

		marketingRequest.setParam("grantedCouponQTO", grantedCouponQTO);
		marketingRequest.setParam("appKey", appKey);
		LOGGER.info("enter, grantedCouponQTO : {}, appKey : {}", JsonUtil.toJson(grantedCouponQTO), appKey);
		Response<List<GrantedCouponDTO>> response = getMarketingService().execute(marketingRequest);
		LOGGER.info("response, response : {}", JsonUtil.toJson(response));
		LOGGER.info("service : {}", JsonUtil.toJson(getMarketingService()));
		MopResponse mopResponse;
		if (response.isSuccess()) {
			Map data = new HashMap();
			data.put("coupon_list", MopApiUtil.genMopGrantedCouponDTOList(response.getModule()));
			data.put("total_count", response.getTotalCount());
			mopResponse = new MopResponse(data);
		} else {
			return new MopResponse(response.getResCode(), response.getMessage());
		}

		return mopResponse;
	}

	public String getName() {
		return "/marketing/user/coupon/list";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_GET;
	}
}