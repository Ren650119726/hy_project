package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发放优惠券
 * <p/>
 * 用户通过链接领取,只有无码券可以通过此方式,其余的是输入优惠码兑换
 */
@Service
public class GrantActivityCouponAction implements Action {

	private static final Logger LOGGER = LoggerFactory.getLogger(GrantActivityCouponAction.class);

	@Autowired
	private MarketActivityManager marketActivityManager;
	@Autowired
	private ActivityCouponManager activityCouponManager;
	@Autowired
	private GrantedCouponManager grantedCouponManager;

	public MarketingResponse execute(RequestContext context) throws MarketingException {

		// activityId / activityCouponId 中二者必有一个
		Long activityCouponId = (Long) context.getRequest().getParam("activityCouponId");
		Long activityId = (Long) context.getRequest().getParam("activityId");
		String text = (String) context.getRequest().getParam("text");
		Integer grantSource = (Integer) context.getRequest().getParam("grantSource");
		Long receiverId = (Long) context.getRequest().getParam("receiverId");
		Long granterId = (Long) context.getRequest().getParam("granterId");
		String bizCode = (String) context.get("bizCode");

		MarketPreconditions.checkNotNull(receiverId, "receiverId");
		MarketPreconditions.checkNotNull(grantSource, "grantSource");

		if (activityCouponId == null && activityId == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "activityId/activityCouponId");
		}
		ActivityCouponDO activityCouponDO = null;
		if (activityCouponId != null) {
			activityCouponDO = activityCouponManager.getActivityCoupon(activityCouponId, null, bizCode);
		} else if (activityId != null) {
			activityCouponDO = activityCouponManager.getActivityCouponByActivityId(activityId, null, bizCode);
		}

		if (activityCouponDO.getCouponType().intValue() != CouponType.TYPE_NO_CODE.getValue())
			return new MarketingResponse(ResponseCode.BIZ_E_NOT_THE_SAME_COUPON_TYPE);

		// 失效的优惠券
		if (activityCouponDO.getStatus().intValue() == ActivityCouponStatus.INVALID.getValue().intValue()) {
			LOGGER.error("can not grant the activity coupon because of valid status of the coupon, activityCouponId : {}, activityId : {}",
					activityCouponDO.getId(), activityId);
			return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_COUPON_STATUS_ILLEGAL);
		}

		MarketActivityDO marketActivityDO = marketActivityManager.getActivity(activityCouponDO.getActivityId().longValue(), bizCode);

		//TODO 这里临时先用批量接口，后续分表之后无法批量原子执行之后，要重构下
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(receiverId);

		// TODO 判断当前优惠券数量以及发放优惠券需要放到一个事务中处理，避免多个用户争取同一个优惠券的时候出现问题，
		// 发放量不能超过总量
		if (activityCouponDO.getTotalCount().longValue() != -1
				    && activityCouponDO.getGrantedCount().longValue() + userIdList.size() > activityCouponDO.getTotalCount().longValue()) {
			return new MarketingResponse(ResponseCode.NO_ENOUGH_COUPON);
		}

		// 每个用户的领取不能超过单人限量
		GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
		grantedCouponQTO.setCouponId(activityCouponDO.getId());
		grantedCouponQTO.setReceiverId(receiverId);
		if (activityCouponDO.getUserReceiveLimit().intValue() == -1) {
			grantedCouponQTO.setStatus(UserCouponStatus.UN_USE.getValue());
		}

		// 查询所有的该用户在指定优惠券下领的数量,不区分优惠券的状态
		Integer countOfReceived = grantedCouponManager.queryGrantedCouponCount(grantedCouponQTO);

//		LOGGER.info("grant coupon, activityCouponDO : {}, userId : {}"
//				, JsonUtil.toJson(activityCouponDO), Arrays.deepToString(userIdList.toArray()));
		if (activityCouponDO.getUserReceiveLimit().intValue() > 0
				    && countOfReceived.intValue() >= activityCouponDO.getUserReceiveLimit().intValue()) {
			return new MarketingResponse(ResponseCode.ACTIVITY_COUPON_RECEIVED_OUT_OF_LIMIT);
		} else if (activityCouponDO.getUserReceiveLimit().intValue() == -1 && countOfReceived.intValue() != 0) {
			return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_COUPON_NEED_BE_USED);
		}

		grantCoupon(userIdList, activityCouponDO, bizCode, grantSource, granterId, text, marketActivityDO);

		int opNum = activityCouponManager.increaseGrantedCount(
				activityCouponDO.getId().longValue(), userIdList.size());

		if (opNum < 1) {
			LOGGER.error("error of increaseGrantedCount, activityCountId : {}, userIdList : {}",
					activityCouponDO.getId(), JsonUtil.toJson(userIdList));
			return new MarketingResponse(ResponseCode.SERVICE_EXCEPTION);
		}

		activityCouponManager.increaseUserCountOfGranted(activityCouponDO.getId(), receiverId, bizCode);

		return ResponseUtil.getResponse(Boolean.valueOf(true));
	}

	private void grantCoupon(List<Long> userIdList, ActivityCouponDO activityCouponDO,
	                         String bizCode, Integer grantSource, Long granterId, String text
			                        , MarketActivityDO marketActivityDO)
			throws MarketingException {

		List grantedCouponList = new ArrayList();
		Date invalidTime = activityCouponDO.getValidDuration() != null
				                   ? DateUtils.addDays(new Date(), activityCouponDO.getValidDuration()) : null;
		for (int userIndex = 0; userIndex < userIdList.size(); userIndex++) {
			GrantedCouponDO grantedCoupon = new GrantedCouponDO();
			grantedCoupon.setCouponId(activityCouponDO.getId());
			grantedCoupon.setCouponCreatorId(marketActivityDO.getCreatorId());
			grantedCoupon.setGranterId(granterId != null ? granterId : marketActivityDO.getCreatorId());
			grantedCoupon.setReceiverId(userIdList.get(userIndex));
			grantedCoupon.setStatus(UserCouponStatus.UN_USE.getValue());
			grantedCoupon.setActivityId(activityCouponDO.getActivityId());
			grantedCoupon.setActivityCreatorId(activityCouponDO.getActivityCreatorId());
			grantedCoupon.setEndTime(marketActivityDO.getEndTime());
			grantedCoupon.setStartTime(marketActivityDO.getStartTime());
			grantedCoupon.setBizCode(bizCode);
			grantedCoupon.setToolCode(marketActivityDO.getToolCode());
			grantedCoupon.setInvalidTime(invalidTime);
			grantedCoupon.setGrantSource(grantSource);
			grantedCoupon.setText(text);
			grantedCouponList.add(grantedCoupon);
		}

		grantedCouponManager.batchAddGrantedCoupon(grantedCouponList);
	}

	public String getName() {

		return ActionEnum.GRANT_ACTIVITY_COUPON.getActionName();
	}
}