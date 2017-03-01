package com.mockuai.marketingcenter.client;

import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.*;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.CouponCodeQTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.common.domain.qto.OrderRecordQTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public interface MarketingClient {
	/**
	 * 获取商品结算信息
	 *
	 * @param userId
	 * @param marketItemDTOs
	 * @param consigneeId
	 * @param appKey
	 * @return
	 */
	Response<SettlementInfo> getSettlementInfo(Long userId, List<MarketItemDTO> marketItemDTOs, Long consigneeId, String appKey);

	/**
	 * 查询购物车关联的优惠信息
	 *
	 * @param marketItemDTOs
	 * @param appKey
	 * @return
	 */
	Response<List<DiscountInfo>> getCartDiscountInfo(List<MarketItemDTO> marketItemDTOs, String appKey, Long userId);

	/**
	 * 预使用用户优惠券
	 *
	 * @param userCouponIdList
	 * @param userId
	 * @param orderId
	 * @param appKey
	 * @return
	 */
	Response<Void> preUseUserCoupon(List<Long> userCouponIdList, Long userId, Long orderId, String appKey);

	/**
	 * 批量预使用优惠券
	 *
	 * @param orderIdKeyUserCouponIdList
	 * @param userId
	 * @param appKey
	 * @return
	 */
	Response<Void> preUseMultiUserCoupon(Map<Long, List<Long>> orderIdKeyUserCouponIdList, Long userId, String appKey);

	/**
	 * 使用用户优惠券
	 *
	 * @param userId
	 * @param orderId
	 * @param appKey
	 * @return
	 */
	Response<Void> useUserCoupon(Long userId, Long orderId, String appKey);

	/**
	 * 批量使用优惠券
	 *
	 * @param orderIds
	 * @param userId
	 * @param appKey
	 * @return
	 */
	Response<Void> useMultiUserCoupon(List<Long> orderIds, Long userId, String appKey);

	/**
	 * 释放预使用的优惠券
	 *
	 * @param userId
	 * @param orderId
	 * @param appKey
	 * @return
	 */
	Response<Void> releaseUsedCoupon(Long userId, Long orderId, String appKey);

	/**
	 * 批量释放优惠券
	 *
	 * @param orderIds
	 * @param userId
	 * @param appKey
	 * @return
	 */
	Response<Void> releaseMultiUsedCoupon(List<Long> orderIds, Long userId, String appKey);

	/**
	 * 创建复合营销活动
	 *
	 * @param marketActivityDTO
	 * @return
	 */
	Response<Long> addCompositeActivity(MarketActivityDTO marketActivityDTO, String appKey);

	/**
	 * 创建优惠券
	 *
	 * @param activityCouponDTO
	 * @param appKey
	 * @return
	 */
	Response<Long> addActivityCoupon(ActivityCouponDTO activityCouponDTO, String appKey);

	/**
	 * 查询营销活动列表
	 *
	 * @param marketActivityQTO
	 * @param appKey
	 * @return
	 */
	Response<List<MarketActivityDTO>> queryActivity(MarketActivityQTO marketActivityQTO, String appKey);

	/**
	 * 查询优惠券值列表
	 *
	 * @param couponCodeQTO
	 * @param appKey
	 * @return
	 */
	Response<List<CouponCodeDTO>> queryCouponCode(CouponCodeQTO couponCodeQTO, String appKey);

	/**
	 * 查询指定商品的营销活动列表
	 *
	 * @param marketItemDTO
	 * @param appKey
	 * @return
	 */
	Response<List<DiscountInfo>> queryItemDiscountInfo(MarketItemDTO marketItemDTO, Long userId, String appKey);

	/**
	 * 查询指定商品的营销活动列表, Mop 格式返回
	 *
	 * @param marketItemDTO
	 * @param userId
	 * @param appKey
	 * @return
	 */
	Response<MopCombineDiscountDTO> queryItemDiscountInfoForMop(MarketItemDTO marketItemDTO, Long userId, String appKey);

	/**
	 * 获取指定营销活动
	 *
	 * @param activityId
	 * @param creatorId
	 * @param appKey
	 * @return
	 */
	Response<MarketActivityDTO> getActivity(Long activityId, Long creatorId, String appKey);

	/**
	 * 查询营销活动券
	 *
	 * @param activityCouponQTO
	 * @param appKey
	 * @return
	 */
	Response<List<ActivityCouponDTO>> queryActivityCoupon(ActivityCouponQTO activityCouponQTO, String appKey);

	/**
	 * 使指定活动失效
	 *
	 * @param activityId
	 * @param activityCreatorId
	 * @param appKey
	 * @return
	 */
	Response<Void> invalidActivity(Long activityId, Long activityCreatorId, String appKey);

	/**
	 * 使指定优惠券失效
	 *
	 * @param couponId
	 * @param activityCreatorId
	 * @param appKey
	 * @return
	 */
	Response<Void> invalidActivityCoupon(Long couponId, Long activityCreatorId, String appKey);

	/**
	 * 更新活动优惠券
	 *
	 * @param activityCouponDTO
	 * @param appKey
	 * @return
	 */
	Response<Void> updateActivityCoupon(ActivityCouponDTO activityCouponDTO, String appKey);

	/**
	 * 更新满减送
	 *
	 * @param marketActivityDTO
	 * @param appKey
	 * @return
	 */
	Response<Boolean> updateMarketActivity(MarketActivityDTO marketActivityDTO, String appKey);

	/**
	 * 获取转发有礼信息
	 *
	 * @param userId
	 * @param appKey
	 * @return
	 */
	Response<TransmitDTO> getTransmitByUserId(Long userId, String appKey);

	/**
	 * 更新转发有礼信息
	 *
	 * @param transmitDTO
	 * @param appKey
	 */
	Response<Boolean> updateTransmit(TransmitDTO transmitDTO, String appKey);

	/**
	 * 单张优惠券发放多张
	 *
	 * @param activityCouponId
	 * @param receiverId
	 * @param num
	 * @param appKey
	 * @return
	 */
	Response<Boolean> grantActivityCouponWithNumber(Long activityCouponId, Long receiverId, Integer num, Integer grantSource, String appKey);

	/**
	 * 发放单张优惠券
	 *
	 * @param activityCouponId
	 * @param receiverId
	 * @param appKey
	 * @return
	 */
	Response<Boolean> grantActivityCoupon(Long activityCouponId, Long receiverId, Long granterId, String text, Integer grantSource, String appKey);

	/**
	 * 查询发放优惠券
	 *
	 * @param grantedCouponQTO
	 * @param appKey
	 * @return
	 */
	Response<List<GrantedCouponDTO>> queryGrantedCoupon(GrantedCouponQTO grantedCouponQTO, String appKey);

	/**
	 * 查询与满减送的冲突情况
	 *
	 * @param marketItemDTOs
	 * @param appKey
	 * @return
	 */
	Response<Boolean> conflictTimeOfActivityCheckAction(List<MarketItemDTO> marketItemDTOs, Date startTime, Date endTime, String appKey);

	/**
	 * marketItemDTO 中必须属性 itemId/categoryId/brandId/itemType
	 *
	 * @param marketItemDTOs
	 * @param appKey
	 * @return
	 */
	Response<List<DiscountInfo>> discountInfoOfItemListAction(List<MarketItemDTO> marketItemDTOs, String appKey);

	Response<List<OrderRecordDTO>> queryOrderRecord(OrderRecordQTO orderRecordQTO, String appKey);

	Response<OrderGeneralDTO> getOrderGeneral(OrderGeneralDTO orderGeneralDTO, String appKey);

	Response<UserCouponDataDTO> getUserCouponData(GrantedCouponQTO grantedCouponQTO, String appKey);
}