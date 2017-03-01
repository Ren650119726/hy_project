package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.core.dao.ActivityCouponDAO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityCouponDAOImpl extends SqlMapClientDaoSupport implements ActivityCouponDAO {

	@Override
	public List<ActivityCouponDO> queryByCode(String code) {
		return getSqlMapClientTemplate().queryForList("activity_coupon.queryByCode", code);
	}

	public Long addActivityCoupon(ActivityCouponDO activityCouponDO) {

		return (Long) getSqlMapClientTemplate().insert("activity_coupon.addActivityCoupon", activityCouponDO);
	}

	public int increaseGrantedCount(long couponId, int increaseNum) {

		Map<String, Object> params = new HashMap();
		params.put("id", Long.valueOf(couponId));
		params.put("increaseNum", Integer.valueOf(increaseNum));

		return getSqlMapClientTemplate().update("activity_coupon.increaseGrantedCount", params);
	}

	public int increaseUsedCount(long couponId, long userId, int increaseNum) {

		Map<String, Object> params = new HashMap();
		params.put("id", Long.valueOf(couponId));
		params.put("creatorId", Long.valueOf(userId));
		params.put("increaseNum", Integer.valueOf(increaseNum));

		return getSqlMapClientTemplate().update("activity_coupon.increaseUsedCount", params);
	}

	public int deleteActivityCoupon(Long couponId, Long userId) {

		Map<String, Object> params = new HashMap();
		params.put("id", couponId);
		params.put("userId", userId);

		return getSqlMapClientTemplate().update("activity_coupon.deleteActivityCoupon", params);
	}

	public int updateActivityCoupon(ActivityCouponDO activityCouponDO) {

		return getSqlMapClientTemplate().update("activity_coupon.updateActivityCoupon", activityCouponDO);
	}

	public ActivityCouponDO getActivityCoupon(ActivityCouponDO activityCouponDO) {

		return (ActivityCouponDO) getSqlMapClientTemplate().queryForObject("activity_coupon.getActivityCoupon", activityCouponDO);
	}

	public List<ActivityCouponDO> queryActivityCoupon(ActivityCouponQTO activityCouponQTO) {
		activityCouponQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("activity_coupon.queryActivityCouponCount",
				activityCouponQTO));
		return getSqlMapClientTemplate().queryForList("activity_coupon.queryActivityCoupon", activityCouponQTO);
	}
}