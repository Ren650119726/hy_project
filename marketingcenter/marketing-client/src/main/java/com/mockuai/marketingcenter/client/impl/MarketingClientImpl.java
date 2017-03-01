package com.mockuai.marketingcenter.client.impl;

import com.mockuai.marketingcenter.client.MarketingClient;
import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.MarketingService;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.*;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.CouponCodeQTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.common.domain.qto.OrderRecordQTO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.*;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class MarketingClientImpl implements MarketingClient {

	@Resource
	private MarketingService marketingService;

	public Response<SettlementInfo> getSettlementInfo(Long userId, List<MarketItemDTO> marketItemDTOs, Long consigneeId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(GET_SETTLEMENT_INFO.getActionName());
		baseRequest.setParam("userId", userId);
		baseRequest.setParam("consigneeId", consigneeId);
		baseRequest.setParam("itemList", marketItemDTOs);
		baseRequest.setParam("appKey", appKey);
		Response<SettlementInfo> response = (Response<SettlementInfo>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<List<DiscountInfo>> getCartDiscountInfo(List<MarketItemDTO> marketItemDTOs, String appKey, Long userId) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(CART_DISCOUNT_INFO_LIST.getActionName());
		baseRequest.setParam("cartItemList", marketItemDTOs);
		baseRequest.setParam("appKey", appKey);
		baseRequest.setParam("userId", userId);
		Response<List<DiscountInfo>> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<Void> preUseUserCoupon(List<Long> userCouponIdList, Long userId, Long orderId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(PRE_USE_USER_COUPON.getActionName());
		baseRequest.setParam("userId", userId);
		baseRequest.setParam("orderId", orderId);
		baseRequest.setParam("appKey", appKey);
		baseRequest.setParam("userCouponIdList", userCouponIdList);

		Response<Void> response = (Response<Void>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Void> preUseMultiUserCoupon(Map<Long, List<Long>> orderIdKeyUserCouponIdList, Long userId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(PRE_MULTI_USE_USER_COUPON.getActionName());
		baseRequest.setParam("orderIdKeyUserCouponIdList", orderIdKeyUserCouponIdList);
		baseRequest.setParam("userId", userId);
		baseRequest.setParam("appKey", appKey);

		Response<Void> response = (Response<Void>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Void> useUserCoupon(Long userId, Long orderId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(USE_USER_COUPON.getActionName());
		baseRequest.setParam("userId", userId);
		baseRequest.setParam("orderId", orderId);
		baseRequest.setParam("appKey", appKey);

		Response<Void> response = (Response<Void>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Void> useMultiUserCoupon(List<Long> orderIds, Long userId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(USE_MULTI_USER_COUPON.getActionName());
		baseRequest.setParam("orderIds", orderIds);
		baseRequest.setParam("userId", userId);
		baseRequest.setParam("appKey", appKey);

		Response<Void> response = (Response<Void>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Void> releaseUsedCoupon(Long userId, Long orderId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(RELEASE_USED_COUPON.getActionName());
		baseRequest.setParam("userId", userId);
		baseRequest.setParam("orderId", orderId);
		baseRequest.setParam("appKey", appKey);

		Response<Void> response = (Response<Void>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Void> releaseMultiUsedCoupon(List<Long> orderIds, Long userId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(RELEASE_MULTI_USED_COUPON.getActionName());
		baseRequest.setParam("orderIds", orderIds);
		baseRequest.setParam("userId", userId);
		baseRequest.setParam("appKey", appKey);

		Response<Void> response = (Response<Void>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Long> addCompositeActivity(MarketActivityDTO marketActivityDTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.ADD_COMPOSITE_ACTIVITY.getActionName());
		baseRequest.setParam("marketActivityDTO", marketActivityDTO);
		baseRequest.setParam("appKey", appKey);

		Response<Long> response = (Response<Long>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Long> addActivityCoupon(ActivityCouponDTO activityCouponDTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.ADD_ACTIVITY_COUPON.getActionName());
		baseRequest.setParam("activityCouponDTO", activityCouponDTO);
		baseRequest.setParam("appKey", appKey);

		Response<Long> response = (Response<Long>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<List<MarketActivityDTO>> queryActivity(MarketActivityQTO marketActivityQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.QUERY_ACTIVITY.getActionName());
		baseRequest.setParam("marketActivityQTO", marketActivityQTO);
		baseRequest.setParam("appKey", appKey);
		Response<List<MarketActivityDTO>> response =
				(Response<List<MarketActivityDTO>>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<List<CouponCodeDTO>> queryCouponCode(CouponCodeQTO couponCodeQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.QUERY_COUPON_CODE.getActionName());
		baseRequest.setParam("couponCodeQTO", couponCodeQTO);
		baseRequest.setParam("appKey", appKey);
		Response<List<CouponCodeDTO>> response = (Response<List<CouponCodeDTO>>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<List<DiscountInfo>> queryItemDiscountInfo(MarketItemDTO marketItemDTO, Long userId, String appKey) {
		BaseRequest request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM_DISCOUNTINFO.getActionName());
		request.setParam("marketItemDTO", marketItemDTO);
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		Response<List<DiscountInfo>> response = marketingService.execute(request);
		return response;
	}

	public Response<MopCombineDiscountDTO> queryItemDiscountInfoForMop(MarketItemDTO marketItemDTO, Long userId, String appKey) {
		BaseRequest request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM_DISCOUNTINFO_MOP.getActionName());
		request.setParam("marketItemDTO", marketItemDTO);
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		Response<MopCombineDiscountDTO> response = marketingService.execute(request);
		return response;
	}

	public Response<MarketActivityDTO> getActivity(Long activityId, Long creatorId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.GET_ACTIVITY.getActionName());
		baseRequest.setParam("activityId", activityId);
		baseRequest.setParam("creatorId", creatorId);
		baseRequest.setParam("appKey", appKey);
		Response<MarketActivityDTO> response =
				(Response<MarketActivityDTO>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<List<ActivityCouponDTO>> queryActivityCoupon(ActivityCouponQTO activityCouponQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.QUERY_ACTIVITY_COUPON.getActionName());
		baseRequest.setParam("activityCouponQTO", activityCouponQTO);
		baseRequest.setParam("appKey", appKey);
		Response<List<ActivityCouponDTO>> response =
				(Response<List<ActivityCouponDTO>>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Void> invalidActivity(Long activityId, Long activityCreatorId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.INVALID_ACTIVITY.getActionName());
		baseRequest.setParam("activityId", activityId);
		baseRequest.setParam("activityCreatorId", activityCreatorId);
		baseRequest.setParam("appKey", appKey);
		Response<Void> response = (Response<Void>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Void> invalidActivityCoupon(Long couponId, Long activityCreatorId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.INVALID_ACTIVITY_COUPON.getActionName());
		baseRequest.setParam("couponId", couponId);
		baseRequest.setParam("activityCreatorId", activityCreatorId);
		baseRequest.setParam("appKey", appKey);
		Response<Void> response = (Response<Void>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Void> updateActivityCoupon(ActivityCouponDTO activityCouponDTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.UPDATE_ACTIVITY_COUPON.getActionName());
		baseRequest.setParam("activityCouponDTO", activityCouponDTO);
		baseRequest.setParam("appKey", appKey);
		Response<Void> response = (Response<Void>) marketingService.execute(baseRequest);
		return response;
	}

	public Response<Boolean> updateMarketActivity(MarketActivityDTO marketActivityDTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.UPDATE_MARKET_ACTIVITY.getActionName());
		baseRequest.setParam("marketActivityDTO", marketActivityDTO);
		baseRequest.setParam("appKey", appKey);
		Response<Boolean> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<TransmitDTO> getTransmitByUserId(Long userId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.GET_TRANSMIT.getActionName());
		baseRequest.setParam("userId", userId);
		baseRequest.setParam("appKey", appKey);
		Response<TransmitDTO> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<Boolean> updateTransmit(TransmitDTO transmitDTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.UPDATE_TRANSMIT.getActionName());
		baseRequest.setParam("transmitDTO", transmitDTO);
		baseRequest.setParam("appKey", appKey);
		Response<Boolean> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<Boolean> grantActivityCouponWithNumber(Long activityCouponId, Long receiverId, Integer num, Integer grantSource, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.GRANT_ACTIVITY_COUPON_BATCH_BY_NUMBER.getActionName());
		baseRequest.setParam("activityCouponId", activityCouponId);
		baseRequest.setParam("receiverId", receiverId);
		baseRequest.setParam("grantSource", grantSource);
		baseRequest.setParam("num", num);
		baseRequest.setParam("appKey", appKey);
		Response<Boolean> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<Boolean> grantActivityCoupon(Long activityCouponId, Long receiverId, Long granterId, String text, Integer grantSource, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.GRANT_ACTIVITY_COUPON.getActionName());
		baseRequest.setParam("activityCouponId", activityCouponId);
		baseRequest.setParam("grantSource", grantSource);
		baseRequest.setParam("text", text);
		baseRequest.setParam("receiverId", receiverId);
		baseRequest.setParam("granterId", granterId);
		baseRequest.setParam("appKey", appKey);
		Response<Boolean> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<List<GrantedCouponDTO>> queryGrantedCoupon(GrantedCouponQTO grantedCouponQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.QUERY_GRANTED_COUPON.getActionName());
		baseRequest.setParam("grantedCouponQTO", grantedCouponQTO);
		baseRequest.setParam("appKey", appKey);
		Response<List<GrantedCouponDTO>> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<Boolean> conflictTimeOfActivityCheckAction(List<MarketItemDTO> marketItemDTOs, Date startTime, Date endTime, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.CONFLICT_TIME_OF_ACTIVITY_CHECK.getActionName());
		baseRequest.setParam("marketItemDTOs", marketItemDTOs);
		baseRequest.setParam("startTime", startTime);
		baseRequest.setParam("endTime", endTime);
		baseRequest.setParam("appKey", appKey);
		Response<Boolean> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<List<DiscountInfo>> discountInfoOfItemListAction(List<MarketItemDTO> marketItemDTOs, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.DISCOUNT_INF_OF_ITEM_LITE.getActionName());
		baseRequest.setParam("marketItemDTOs", marketItemDTOs);
		baseRequest.setParam("appKey", appKey);
		Response<List<DiscountInfo>> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<List<OrderRecordDTO>> queryOrderRecord(OrderRecordQTO orderRecordQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.QUERY_ORDER_RECORD.getActionName());
		baseRequest.setParam("orderRecordQTO", orderRecordQTO);
		baseRequest.setParam("appKey", appKey);
		Response<List<OrderRecordDTO>> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<OrderGeneralDTO> getOrderGeneral(OrderGeneralDTO orderGeneralDTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.GET_ORDER_GENERAL.getActionName());
		baseRequest.setParam("orderGeneralDTO", orderGeneralDTO);
		baseRequest.setParam("appKey", appKey);
		Response<OrderGeneralDTO> response = marketingService.execute(baseRequest);
		return response;
	}

	public Response<UserCouponDataDTO> getUserCouponData(GrantedCouponQTO grantedCouponQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.GET_GRANTED_COUPON_FOR_USER.getActionName());
		baseRequest.setParam("grantedCouponQTO", grantedCouponQTO);
		baseRequest.setParam("appKey", appKey);
		Response<UserCouponDataDTO> response = marketingService.execute(baseRequest);
		return response;
	}
}