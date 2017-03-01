package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
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
import com.mockuai.marketingcenter.core.service.action.TransAction;
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

import static com.mockuai.marketingcenter.common.constant.ActionEnum.GRANT_ACTIVITY_COUPON_BATCH_BY_NUMBER;

/**
 * 为单用户发放多张相同优惠券
 * <p/>
 * Created by edgar.zr on 6/06/2016.
 */
@Service
public class GrantActivityCouponByMultiNumAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(GrantActivityCouponByMultiNumAction.class);

	@Autowired
	private MarketActivityManager marketActivityManager;
	@Autowired
	private ActivityCouponManager activityCouponManager;
	@Autowired
	private GrantedCouponManager grantedCouponManager;

	@Override
	protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

		Long activityCouponId = (Long) context.getRequest().getParam("activityCouponId");
		Integer num = (Integer) context.getRequest().getParam("num");
		Long receiverId = (Long) context.getRequest().getParam("receiverId");
		String bizCode = (String) context.get("bizCode");
		String text = (String) context.getRequest().getParam("text");
		Integer grantSource = (Integer) context.getRequest().getParam("grantSource");

		MarketPreconditions.checkNotNull(activityCouponId, "activityCouponId");
		MarketPreconditions.checkNotNull(receiverId, "receiverId");
		MarketPreconditions.checkNotNull(grantSource, "grantSource");
		MarketPreconditions.checkNotNull(num, "num");

		ActivityCouponDO activityCouponDO = activityCouponManager.getActivityCoupon(activityCouponId, null, bizCode);

		if (activityCouponDO.getCouponType().intValue() != CouponType.TYPE_NO_CODE.getValue())
			return new MarketingResponse(ResponseCode.BIZ_E_NOT_THE_SAME_COUPON_TYPE);

		// 失效的优惠券
		if (activityCouponDO.getStatus().intValue() == ActivityCouponStatus.INVALID.getValue().intValue()) {
			LOGGER.error("can not grant the activity coupon because of valid status of the coupon, activityCouponId : {}",
					activityCouponId);
			return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_COUPON_STATUS_ILLEGAL);
		}

		MarketActivityDO marketActivityDO =
				marketActivityManager.getActivity(activityCouponDO.getActivityId().longValue(), bizCode);

		Date now = new Date();
		if ((now.compareTo(marketActivityDO.getEndTime()) > 0)) {
			return new MarketingResponse(ResponseCode.BIZ_ACTIVITY_COUPON_OVER);
		}

		// TODO 判断当前优惠券数量以及发放优惠券需要放到一个事务中处理，避免多个用户争取同一个优惠券的时候出现问题，
		// 发放量不能超过总量
		if (activityCouponDO.getTotalCount().longValue() != -1
				    && activityCouponDO.getGrantedCount().longValue() + num > activityCouponDO.getTotalCount().longValue()) {
			return new MarketingResponse(ResponseCode.NO_ENOUGH_COUPON);
		}

		// 每个用户的领取不能超过单人限量
		GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
		grantedCouponQTO.setCouponId(activityCouponDO.getId());
		grantedCouponQTO.setReceiverId(receiverId);

		// 查询所有的该用户在指定优惠券下领的数量,不区分优惠券的状态
		Integer countOfReceived = grantedCouponManager.queryGrantedCouponCount(grantedCouponQTO);

		if (activityCouponDO.getUserReceiveLimit().intValue() != 0
				    && countOfReceived.intValue() >= activityCouponDO.getUserReceiveLimit().intValue()) {
			return new MarketingResponse(ResponseCode.ACTIVITY_COUPON_RECEIVED_OUT_OF_LIMIT);
		}

		grantCoupon(receiverId, num, activityCouponDO, bizCode, text, grantSource, marketActivityDO);

		int opNum = activityCouponManager.increaseGrantedCount(activityCouponId.longValue(), num);

		if (opNum < 1) {
			LOGGER.error("error of increaseGrantedCount, activityCountId : {}, creatorId : {}, num : {}",
					activityCouponId, 0, num);
			return new MarketingResponse(ResponseCode.SERVICE_EXCEPTION);
		}

		activityCouponManager.increaseUserCountOfGranted(activityCouponId, receiverId, bizCode);

		return ResponseUtil.getResponse(Boolean.valueOf(true));
	}

	private void grantCoupon(Long userId, Integer count, ActivityCouponDO activityCouponDO,
	                         String bizCode, String text, Integer grantSource, MarketActivityDO marketActivityDO) throws MarketingException {

		List grantedCouponList = new ArrayList();
		Date invalidTime = activityCouponDO.getValidDuration() != null
				                   ? DateUtils.addDays(new Date(), activityCouponDO.getValidDuration()) : null;
		for (int i = 0; i < count; i++) {
			GrantedCouponDO grantedCoupon = new GrantedCouponDO();
			grantedCoupon.setCouponId(activityCouponDO.getId());
			grantedCoupon.setCouponCreatorId(marketActivityDO.getCreatorId());
			grantedCoupon.setGranterId(marketActivityDO.getCreatorId());
			grantedCoupon.setReceiverId(userId);
			grantedCoupon.setStatus(UserCouponStatus.UN_USE.getValue());
			grantedCoupon.setActivityId(activityCouponDO.getActivityId());
			grantedCoupon.setActivityCreatorId(activityCouponDO.getActivityCreatorId());
			grantedCoupon.setEndTime(marketActivityDO.getEndTime());
			grantedCoupon.setStartTime(marketActivityDO.getStartTime());
			grantedCoupon.setBizCode(bizCode);
			grantedCoupon.setToolCode(marketActivityDO.getToolCode());
			grantedCoupon.setInvalidTime(invalidTime);
			grantedCoupon.setText(text);
			grantedCoupon.setGrantSource(grantSource);
			grantedCouponList.add(grantedCoupon);
		}

		grantedCouponManager.batchAddGrantedCoupon(grantedCouponList);
	}

	@Override
	public String getName() {
		return GRANT_ACTIVITY_COUPON_BATCH_BY_NUMBER.getActionName();
	}
}