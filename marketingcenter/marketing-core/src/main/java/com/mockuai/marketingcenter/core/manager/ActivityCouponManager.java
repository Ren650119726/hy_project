package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.CouponCodeDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityCouponManager {
	/**
	 * 添加优惠活动券信息
	 *
	 * @param activityCouponDO
	 * @return
	 * @throws MarketingException
	 */
	long addActivityCoupon(ActivityCouponDO activityCouponDO) throws MarketingException;

	/**
	 * 统一后的创建优惠券(有码／无码)
	 * 后续会替换掉 addActivityCoupon
	 *
	 * @param activityCouponDTO
	 * @return
	 * @throws MarketingException
	 */
	void addActivityCoupon(ActivityCouponDTO activityCouponDTO) throws MarketingException;

	/**
	 * 通过优惠码查询 通用码
	 *
	 * @param code
	 * @return
	 * @throws MarketingException
	 */
	ActivityCouponDO getByCode(String code) throws MarketingException;

	/**
	 * 单独为 通用码 生成 code，同时处理重复问题
	 *
	 * @return
	 * @throws MarketingException
	 */
	String genCodeForActivityCoupon() throws MarketingException;

	/**
	 * 领取通用码
	 *
	 * @param couponId
	 * @param sellerId
	 * @param userId
	 * @param bizCode
	 * @return
	 * @throws MarketingException
	 */
	Long exchangeByCommonCode(Long couponId, Long sellerId, Long userId, String bizCode) throws MarketingException;

	/**
	 * 领取一卡一码
	 *
	 * @param couponId
	 * @param sellerId
	 * @param userId
	 * @param couponCodeDO
	 * @param bizCode
	 * @return
	 * @throws MarketingException
	 */
	Long exchangeByPerCode(Long couponId, Long sellerId, Long userId, CouponCodeDO couponCodeDO, String bizCode) throws MarketingException;

	/**
	 * 累加优惠券已发放数量
	 *
	 * @param couponId
	 * @param incNum
	 * @return
	 * @throws MarketingException
	 */
	int increaseGrantedCount(long couponId, int incNum) throws MarketingException;

	/**
	 * 累加优惠券已激活数量
	 *
	 * @param couponId
	 * @param userId
	 * @param incNum
	 * @return
	 * @throws MarketingException
	 */
	int increaseUsedCount(Long couponId, Long userId, int incNum) throws MarketingException;

	/**
	 * 增加领取的用户人数
	 * 在首次领完以后增加人数
	 *
	 * @param couponId
	 * @param userId
	 * @param bizCode
	 * @return
	 * @throws MarketingException
	 */
	void increaseUserCountOfGranted(Long couponId, Long userId, String bizCode) throws MarketingException;

	/**
	 * 是优惠券失效
	 *
	 * @param couponId
	 * @param creatorId
	 * @param activityId
	 * @param bizCode
	 * @throws MarketingException
	 */
	void invalidActivityCoupon(Long couponId, Long creatorId, Long activityId, String bizCode) throws MarketingException;

	/**
	 * 更新优惠券
	 *
	 * @param activityCouponDTO
	 * @return
	 * @throws MarketingException
	 */
	int updateActivityCoupon(ActivityCouponDTO activityCouponDTO) throws MarketingException;

	/**
	 * 更新优惠码值
	 *
	 * @param couponId
	 * @param activityCreatorId
	 * @param name
	 * @param bizCode
	 * @return
	 * @throws MarketingException
	 */
	int updateCouponCodeName(Long couponId, Long activityCreatorId, String name, String bizCode) throws MarketingException;

	/**
	 * 查询优惠活动券
	 *
	 * @param activityCouponQTO
	 * @return
	 * @throws MarketingException
	 */
	List<ActivityCouponDO> queryActivityCoupon(ActivityCouponQTO activityCouponQTO)
			throws MarketingException;

	/**
	 * 获取指定优惠券
	 *
	 * @param couponId
	 * @param sellerId
	 * @param bizCode
	 * @return
	 * @throws MarketingException
	 */
	ActivityCouponDO getActivityCoupon(Long couponId, Long sellerId, String bizCode) throws MarketingException;

	/**
	 * 获取指定优惠券
	 *
	 * @param activityId
	 * @param sellerId
	 * @param bizCode
	 * @return
	 * @throws MarketingException
	 */
	ActivityCouponDO getActivityCouponByActivityId(Long activityId, Long sellerId, String bizCode) throws MarketingException;
}