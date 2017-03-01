package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;

import java.util.List;

public interface ActivityCouponDAO {

	List<ActivityCouponDO> queryByCode(String code);

	Long addActivityCoupon(ActivityCouponDO paramActivityCouponDO);

	int increaseGrantedCount(long couponId, int increaseNum);

	int increaseUsedCount(long couponId, long userId, int increaseNum);

	int deleteActivityCoupon(Long couponId, Long userId);

	int updateActivityCoupon(ActivityCouponDO activityCouponDO);

	List<ActivityCouponDO> queryActivityCoupon(ActivityCouponQTO activityCouponQTO);

	ActivityCouponDO getActivityCoupon(ActivityCouponDO activityCouponDO);
}