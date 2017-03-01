package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.dao.ActivityCouponDAO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.CouponCodeDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.CouponCodeManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.util.CouponCodeUtil;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityCouponManagerImpl implements ActivityCouponManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityCouponManagerImpl.class);

	private static final int DEFAULT_QUERY_COUNT = 200;

	@Autowired
	private MarketActivityManager marketActivityManager;

	@Autowired
	private ActivityCouponDAO activityCouponDAO;

	@Autowired
	private GrantedCouponManager grantedCouponManager;

	@Autowired
	private CouponCodeManager couponCodeManager;

	public long addActivityCoupon(ActivityCouponDO activityCouponDO) throws MarketingException {

		try {
			return activityCouponDAO.addActivityCoupon(activityCouponDO).longValue();
		} catch (Exception e) {
			LOGGER.error("failed when adding activity coupon : {}",
					activityCouponDO, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public void addActivityCoupon(ActivityCouponDTO activityCouponDTO) throws MarketingException {

		//创建优惠券
		ActivityCouponDO activityCouponDO = ModelUtil.genActivityCouponDO(activityCouponDTO);

		//初始化优惠券相关参数
		activityCouponDO.setGrantedCount(0L);
		activityCouponDO.setActivateCount(0L);
		activityCouponDO.setUsedCount(0L);
		activityCouponDO.setUserCount(0L);
		//设置优惠券默认状态
		activityCouponDO.setStatus(ActivityCouponStatus.NORMAL.getValue());

		//优惠券类型
		if (activityCouponDO.getCouponType() == null) {
			//默认设为无码优惠券
			activityCouponDO.setCouponType(CouponType.TYPE_NO_CODE.getValue());
		}

		//如果未设置每人限领个数，则默认设置每人限领个数为0，即不限领
		if (activityCouponDO.getUserReceiveLimit() == null) {
			activityCouponDO.setUserReceiveLimit(0);
		}

		// 通用码
//		if (activityCouponDO.getCouponType().intValue() == CouponType.TYPE_COMMON_CODE.getValue().intValue()) {
//			activityCouponDO.setCode(activityCouponDO.getCouponType() + genCodeForActivityCoupon());
//		}
		Long couponId = activityCouponDAO.addActivityCoupon(activityCouponDO);

		activityCouponDTO.setId(couponId);

		// 一卡一码
//		if (activityCouponDTO.getCouponType().intValue() == CouponType.TYPE_PER_CODE.getValue().intValue()) {
//			couponCodeManager.batchAddCouponCode(activityCouponDTO, activityCouponDTO.getMarketActivity());
//		}
	}

	@Override
	public ActivityCouponDO getByCode(String code) throws MarketingException {
		try {
			List<ActivityCouponDO> couponDOs = activityCouponDAO.queryByCode(code);
			if (couponDOs.size() == 0) {
				throw new MarketingException(ResponseCode.BIZ_E_THE_COUPON_CODE_DOES_NOT_EXIST);
			}
			return couponDOs.get(0);
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("failed when queryByCode, code : {}", code, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public String genCodeForActivityCoupon() throws MarketingException {
		List<ActivityCouponDO> activityCouponDOs = null;
		String code;
		do {
			code = CouponCodeUtil.genCode();
			activityCouponDOs = activityCouponDAO.queryByCode(code);
		} while (activityCouponDOs.size() != 0);

		return code;
	}

	@Override
	public Long exchangeByCommonCode(Long couponId, Long sellerId, Long userId, String bizCode) throws MarketingException {
		try {
			ActivityCouponDO activityCouponDO = getActivityCoupon(couponId, sellerId, bizCode);
			if (activityCouponDO.getActivateCount().longValue() + 1 > activityCouponDO.getTotalCount().longValue()) {
				throw new MarketingException(ResponseCode.BIZ_E_THE_ACTIVITY_CODE_IS_NONE);
			}
			ActivityCouponDO updateActivityCouponDO = new ActivityCouponDO();
			updateActivityCouponDO.setId(couponId);
			updateActivityCouponDO.setBizCode(bizCode);
			updateActivityCouponDO.setActivateCount(activityCouponDO.getActivateCount().longValue() + 1);

			GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
			grantedCouponQTO.setCouponId(couponId);
			grantedCouponQTO.setReceiverId(userId);

			Integer count = grantedCouponManager.queryGrantedCouponCount(grantedCouponQTO);
			// 该用户还没有领过
			if (count.intValue() == 0) {
				updateActivityCouponDO.setUserCount(activityCouponDO.getUserCount() + 1);
			}
			int optNum = activityCouponDAO.updateActivityCoupon(updateActivityCouponDO);
			if (optNum != 1) {
				LOGGER.error("failed when update the value of activateCount and userCount, couponId : {}, sellerId : {}, userId : {}",
						couponId, sellerId, userId);
				throw new MarketingException(ResponseCode.DB_OP_ERROR);
			}
			GrantedCouponDO grantedCouponDO = new GrantedCouponDO();
			grantedCouponDO.setReceiverId(userId);
			grantedCouponDO.setGranterId(sellerId);
			grantedCouponDO.setStatus(UserCouponStatus.UN_USE.getValue());
			grantedCouponDO.setActivityId(activityCouponDO.getActivityId());
			grantedCouponDO.setBizCode(activityCouponDO.getBizCode());
			grantedCouponDO.setCode(activityCouponDO.getCode());
			grantedCouponDO.setCouponId(couponId);
			grantedCouponDO.setCouponType(activityCouponDO.getCouponType());
			grantedCouponDO.setCouponCreatorId(sellerId);
			grantedCouponDO.setStartTime(activityCouponDO.getStartTime());
			grantedCouponDO.setEndTime(activityCouponDO.getEndTime());
			grantedCouponDO.setReceiverId(userId);
			grantedCouponDO.setActivityCreatorId(sellerId);
			grantedCouponDO.setInvalidTime(activityCouponDO.getValidDuration() != null ? DateUtils.addDays(new Date(), activityCouponDO.getValidDuration()) : null);
			MarketActivityDO marketActivityDO = marketActivityManager.getActivity(activityCouponDO.getActivityId(), activityCouponDO.getBizCode());
			if (marketActivityDO != null) {
				grantedCouponDO.setToolCode(marketActivityDO.getToolCode());
			} else {
				grantedCouponDO.setToolCode(ToolType.SIMPLE_TOOL.getCode());
			}
			grantedCouponManager.addGrantedCoupon(grantedCouponDO);
			if (grantedCouponDO.getId() == null) {
				LOGGER.error("failed to add grantedCoupon, the id of GrantedCoupon is null, GrantedCoupon : {}", JsonUtil.toJson(grantedCouponDO));
				throw new MarketingException(ResponseCode.DB_OP_ERROR);
			}

			return grantedCouponDO.getId();
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("failed when exchangeByCommonCode, couponId : {}, sellerId : {}, userId : {}", couponId, sellerId, userId, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public Long exchangeByPerCode(Long couponId, Long sellerId, Long userId, CouponCodeDO couponCodeDO, String bizCode) throws MarketingException {
		try {
			ActivityCouponDO activityCouponDO = getActivityCoupon(couponId, sellerId, bizCode);
			ActivityCouponDO updateActivityCouponDO = new ActivityCouponDO();
			updateActivityCouponDO.setId(couponId);
			updateActivityCouponDO.setBizCode(bizCode);
			updateActivityCouponDO.setActivateCount(activityCouponDO.getActivateCount().longValue() + 1);

			GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
			grantedCouponQTO.setCouponId(couponId);
			grantedCouponQTO.setReceiverId(userId);

			Integer count = grantedCouponManager.queryGrantedCouponCount(grantedCouponQTO);
			// 该用户还没有领过
			if (count.intValue() == 0) {
				updateActivityCouponDO.setUserCount(activityCouponDO.getUserCount().longValue() + 1);
			}

			int opNum = activityCouponDAO.updateActivityCoupon(updateActivityCouponDO);
			if (opNum != 1) {
				LOGGER.error("error to update the activate count and user count, couponId : {}, sellerId : {}, userId : {}", couponId, sellerId, userId);
				throw new MarketingException(ResponseCode.DB_OP_ERROR);
			}
			CouponCodeDO updateCouponCodeDO = new CouponCodeDO();
			updateCouponCodeDO.setId(couponCodeDO.getId());
			updateCouponCodeDO.setStatus(UserCouponStatus.UN_USE.getValue());
			updateCouponCodeDO.setActivityCreatorId(activityCouponDO.getActivityCreatorId());
			updateCouponCodeDO.setUserId(userId);

			opNum = couponCodeManager.updateCouponCode(updateCouponCodeDO);
			if (opNum != 1) {
				LOGGER.error("error to update coupon code, couponCodeId : {}, creatorId : {}, status : {}, userId : {}",
						couponCodeDO.getId(), sellerId, UserCouponStatus.UN_USE.getValue(), userId);
				throw new MarketingException(ResponseCode.DB_OP_ERROR);
			}

			GrantedCouponDO grantedCouponDO = new GrantedCouponDO();
			grantedCouponDO.setReceiverId(userId);
			grantedCouponDO.setGranterId(sellerId);
			grantedCouponDO.setStatus(UserCouponStatus.UN_USE.getValue());
			grantedCouponDO.setActivityId(activityCouponDO.getActivityId());
			grantedCouponDO.setBizCode(activityCouponDO.getBizCode());
			grantedCouponDO.setCode(couponCodeDO.getCode());
			grantedCouponDO.setCouponId(couponId);
			grantedCouponDO.setCouponType(activityCouponDO.getCouponType());
			grantedCouponDO.setCouponCreatorId(sellerId);
			grantedCouponDO.setStartTime(activityCouponDO.getStartTime());
			grantedCouponDO.setEndTime(activityCouponDO.getEndTime());
			grantedCouponDO.setReceiverId(userId);
			grantedCouponDO.setActivityCreatorId(sellerId);
			grantedCouponDO.setInvalidTime(activityCouponDO.getValidDuration() != null ? DateUtils.addDays(new Date(), activityCouponDO.getValidDuration()) : null);
			MarketActivityDO marketActivityDO = marketActivityManager.getActivity(activityCouponDO.getActivityId(), activityCouponDO.getBizCode());
			if (marketActivityDO != null) {
				grantedCouponDO.setToolCode(marketActivityDO.getToolCode());
			} else {
				grantedCouponDO.setToolCode(ToolType.SIMPLE_TOOL.getCode());
			}
			grantedCouponManager.addGrantedCoupon(grantedCouponDO);
			if (grantedCouponDO.getId() == null) {
				LOGGER.error("failed to add grantedCoupon, the id of GrantedCoupon is null, GrantedCoupon : {}", JsonUtil.toJson(grantedCouponDO));
				throw new MarketingException(ResponseCode.DB_OP_ERROR);
			}
			return grantedCouponDO.getId();
		} catch (Exception e) {
			LOGGER.error("failed when exchangeByPerCode, couponId : {}, creatorId : {}, userId : {}, couponCode : {}",
					couponId, sellerId, userId, couponCodeDO.getCode(), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public int increaseGrantedCount(long couponId, int incNum) throws MarketingException {

		try {
			return activityCouponDAO.increaseGrantedCount(couponId, incNum);
		} catch (Exception e) {
			LOGGER.error("failed when increasing the number of granted coupon, couponId : {}, increasing number : {}",
					couponId, incNum, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public int increaseUsedCount(Long couponId, Long userId, int incNum) throws MarketingException {

		try {
			return activityCouponDAO.increaseUsedCount(couponId, userId, incNum);
		} catch (Exception e) {
			LOGGER.error("failed when increasing the number of used coupon, couponId : {}, userId : {}, increasing number : {}",
					couponId, userId, incNum, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public void increaseUserCountOfGranted(Long couponId, Long userId, String bizCode) throws MarketingException {

		GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
		grantedCouponQTO.setCouponId(couponId);
		grantedCouponQTO.setReceiverId(userId);

		try {
			ActivityCouponDO dbActivityCouponDO = getActivityCoupon(couponId, null, bizCode);
			Integer count = grantedCouponManager.queryGrantedCouponCount(grantedCouponQTO);
			ActivityCouponDO activityCouponDO = new ActivityCouponDO();
			activityCouponDO.setId(couponId);
			activityCouponDO.setBizCode(bizCode);

			// 该用户还没有领过
			if (count.intValue() != 1)
				return;

			activityCouponDO.setUserCount(dbActivityCouponDO.getUserCount() + 1);
			int optNum = activityCouponDAO.updateActivityCoupon(activityCouponDO);
			if (optNum != 1) {
				LOGGER.error("failed to increaseUserCountOfGranted, couponId : {}, userId : {}", couponId, userId);
				throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
			}
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("failed to increaseUserCountOfGranted, couponId : {}, userId : {}", couponId, userId);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public void invalidActivityCoupon(Long couponId, Long creatorId, Long activityId, String bizCode) throws MarketingException {

		try {
			ActivityCouponDO activityCouponDO = new ActivityCouponDO();
			activityCouponDO.setId(couponId);
			activityCouponDO.setBizCode(bizCode);
			activityCouponDO.setStatus(ActivityCouponStatus.INVALID.getValue());

			int opNum = activityCouponDAO.updateActivityCoupon(activityCouponDO);

			if (opNum != 1) {
				LOGGER.error("error of invalidActivityCoupon, couponId : {}, activityCreatorId : {}, activityCouponStatus : {}",
						couponId, creatorId, ActivityCouponStatus.INVALID.getValue());
				throw new MarketingException(ResponseCode.DB_OP_ERROR);
			}

			// 关联的活动也进行状态更改
			marketActivityManager.updateActivityStatus(activityId, ActivityStatus.INVALID, bizCode);

		} catch (MarketingException e) {
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public int updateActivityCoupon(ActivityCouponDTO activityCouponDTO) throws MarketingException {

//        ActivityCouponDO activityCouponDO = getActivityCoupon(activityCouponDTO.getId(), activityCouponDTO.getActivityCreatorId(), activityCouponDTO.getBizCode());

		try {
//            ActivityCouponDO activityCouponDOUpdate = new ActivityCouponDO();
//            activityCouponDOUpdate.setId(activityCouponDTO.getId());
//            activityCouponDOUpdate.setBizCode(activityCouponDO.getBizCode());
//            activityCouponDOUpdate.setTotalCount(activityCouponDTO.getTotalCount());
			int opNum = activityCouponDAO.updateActivityCoupon(ModelUtil.genActivityCouponDO(activityCouponDTO));
			if (opNum != 1) {
				throw new MarketingException(ResponseCode.DB_OP_ERROR);
			}
//            if (activityCouponDTO.getCouponType().intValue() == CouponType.TYPE_PER_CODE.getValue()) {
//                activityCouponDTO.setTotalCount(activityCouponDTO.getTotalCount() - activityCouponDO.getTotalCount());
//                couponCodeManager.batchAddCouponCode(activityCouponDTO, marketActivityDTO);
//            }
			return opNum;
		} catch (Exception e) {
			LOGGER.error("failed to update activity coupon, activityCouponId : {}, totalCount : {}, content : {}, name : {}, open : {} bizCode : {}",
					activityCouponDTO.getId()
					, activityCouponDTO.getTotalCount()
					, activityCouponDTO.getContent()
					, activityCouponDTO.getName()
					, activityCouponDTO.getOpen()
					, activityCouponDTO.getBizCode(), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public int updateCouponCodeName(Long couponId, Long activityCreatorId, String name, String bizCode) throws MarketingException {
		ActivityCouponDTO activityCouponDTO = new ActivityCouponDTO();
		activityCouponDTO.setId(couponId);
		activityCouponDTO.setBizCode(bizCode);
		activityCouponDTO.setName(name);
		try {
			return activityCouponDAO.updateActivityCoupon(ModelUtil.genActivityCouponDO(activityCouponDTO));
		} catch (Exception e) {
			LOGGER.error("failed when updateCouponCodeName, couponId : {}, sellerId : {}, name : {}, bizCode : {}",
					couponId, activityCreatorId, name, bizCode, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public List<ActivityCouponDO> queryActivityCoupon(ActivityCouponQTO activityCouponQTO) throws MarketingException {

		if (activityCouponQTO.getCount() == null || activityCouponQTO.getCount().intValue() > DEFAULT_QUERY_COUNT) {
			activityCouponQTO.setCount(DEFAULT_QUERY_COUNT);
		}

		if (activityCouponQTO.getOffset() == null) {
			activityCouponQTO.setOffset(0);
		}

		//根据lifecycle字段，设置时间查询条件
		if (activityCouponQTO.getLifecycle() != null) {
			Date now = new Date();
			if (activityCouponQTO.getLifecycle().intValue() == ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue()) {
				activityCouponQTO.setStartTimeLt(now);
			} else if (activityCouponQTO.getLifecycle().intValue()
					           == ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue()) {
				activityCouponQTO.setStartTimeGe(now);
				activityCouponQTO.setEndTimeLe(now);
			} else if (activityCouponQTO.getLifecycle().intValue()
					           == ActivityLifecycle.LIFECYCLE_ENDED.getValue()) {
				activityCouponQTO.setEndTimeGt(now);
			}
			// 未开始、进行中、已结束 均显示的是有效活动，无效活动只显示在全部
			if (activityCouponQTO.getLifecycle().intValue() != 0) {
				activityCouponQTO.setStatus(ActivityCouponStatus.NORMAL.getValue());
			}
		}

		try {
			return activityCouponDAO.queryActivityCoupon(activityCouponQTO);
		} catch (Exception e) {
			LOGGER.error("failed when querying the activity coupon, activityCouponQTO : {}",
					JsonUtil.toJson(activityCouponQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public ActivityCouponDO getActivityCoupon(Long couponId, Long sellerId, String bizCode) throws MarketingException {

		try {
			ActivityCouponDO activityCouponDO = new ActivityCouponDO();
			activityCouponDO.setBizCode(bizCode);
			activityCouponDO.setId(couponId);
			ActivityCouponDO dbActivityCouponDO = activityCouponDAO.getActivityCoupon(activityCouponDO);
			if (dbActivityCouponDO == null) {
				LOGGER.error("can not found the activityCoupon, activityCouponId : {}, bizCode : {}", couponId, sellerId, bizCode);
				throw new MarketingException(ResponseCode.BIZ_E_ACTIVITY_COUPON_NOT_EXIST);
			}
			return dbActivityCouponDO;
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("failed when getting the activity coupon, couponId : {}, userId : {}, bizCode : {}", couponId, sellerId, bizCode, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public ActivityCouponDO getActivityCouponByActivityId(Long activityId, Long sellerId, String bizCode) throws MarketingException {

		try {
			ActivityCouponDO activityCouponDO = new ActivityCouponDO();
			activityCouponDO.setBizCode(bizCode);
			activityCouponDO.setActivityId(activityId);
			ActivityCouponDO dbActivityCouponDO = activityCouponDAO.getActivityCoupon(activityCouponDO);
			if (dbActivityCouponDO == null) {
				LOGGER.error("can not found the activityCoupon in getActivityCouponByActivityId, activityId : {}, creatorId : {}, bizCode : {}",
						activityId, sellerId, bizCode);
				throw new MarketingException(ResponseCode.BIZ_E_ACTIVITY_COUPON_NOT_EXIST);
			}
			return dbActivityCouponDO;
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("failed when getting the activity coupon in getActivityCouponByActivityId, activityId : {}, userId : {}, bizCode : {}",
					activityId, sellerId, bizCode, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}
}