package com.mockuai.marketingcenter.core.message.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.GrantSourceEnum;
import com.mockuai.marketingcenter.common.constant.RMQMessageType;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.message.consumer.BaseListener;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 满减送活动且有发放优惠券的,向对应用户发放优惠券
 * <p/>
 * Created by edgar.zr on 11/13/15.
 */
@Component
public class CompositeActivityWithOrderSuccessListener extends BaseListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompositeActivityWithOrderSuccessListener.class);

	@Override
	public void init() {
	}

	public String getName() {
		return RMQMessageType.TRADE_PAY_SUCCESS_NOTIFY.combine();
	}

	@Override
	public void consumeMessage(JSONObject msg, String appKey) throws MarketingException {

		LOGGER.info("{}, appKey : {}", getName(), appKey);

		Long userId = msg.getLong("userId");
		Long orderId = msg.getLong("id");
		Long activityId = null;

		try {
			OrderDTO orderDTO = tradeManager.getOrder(orderId, userId, appKey);
			if (orderDTO == null) {
				LOGGER.error("the order is null, orderId : {}, userId : {}", orderId, userId);
			}
			for (OrderDiscountInfoDTO orderDiscountInfoDTO : orderDTO.getOrderDiscountInfoDTOs()) {
				if (orderDiscountInfoDTO.getDiscountCode().equals(ToolType.COMPOSITE_TOOL.getCode())) {
					// 子节点 id
					activityId = orderDiscountInfoDTO.getSubMarketActivityId();
					grantCoupon(activityId, userId, appKey);
					break;
				}
			}

		} catch (MarketingException e) {
			LOGGER.error("error to get order, {}", orderId, userId);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}

	/**
	 * 子节点如果有发放优惠券优惠,需要发放优惠券
	 *
	 * @param subActivityId
	 * @throws MarketingException
	 */
	private void grantCoupon(Long subActivityId, Long userId, String appKey) throws MarketingException {
		MarketActivityDO marketActivityDO = marketActivityManager.getActivity(subActivityId, null);
		if (marketActivityDO == null) {
			LOGGER.error("the sub market doesn't exist, subActivityId : {}", subActivityId);
			return;
		}

		PropertyQTO propertyQTO = new PropertyQTO();
		propertyQTO.setOwnerId(subActivityId);
		List<PropertyDO> propertyDOs = propertyManager.queryProperty(propertyQTO);
		Map<String, PropertyDTO> propertyDOMap = propertyManager.wrapPropertyDTO(ModelUtil.genPropertyDTOList(propertyDOs));
		List<ActivityCouponDTO> activityCouponDTOs = propertyManager.extractPropertyCouponList(propertyDOMap, null);

		if (activityCouponDTOs != null) {
			LOGGER.error("property : {}", JsonUtil.toJson(propertyDOMap));
			LOGGER.error("activityDTOs : {}", JsonUtil.toJson(activityCouponDTOs));
			// 发放优惠券
			for (ActivityCouponDTO activityCouponDTO : activityCouponDTOs) {

				try {
					BaseRequest baseRequest = new BaseRequest();
					baseRequest.setCommand(ActionEnum.GRANT_ACTIVITY_COUPON.getActionName());
					baseRequest.setParam("activityId", activityCouponDTO.getActivityId());
					baseRequest.setParam("grantSource", GrantSourceEnum.MARKET_ACTIVITY.getValue());
					baseRequest.setParam("receiverId", userId);
					baseRequest.setParam("appKey", appKey);
					Response<Boolean> response = marketingService.execute(baseRequest);
					if (response.isSuccess()) {
						LOGGER.info("grant activity coupon successfully, receiverId : {}, activityId : {}"
								, userId, activityCouponDTO.getActivityId());
					} else {
						LOGGER.error("code : {}, message : {}", response.getResCode(), response.getMessage());
					}
				} catch (Exception e) {
					LOGGER.error("", e);
				}
			}
		}
	}

	@Override
	public Logger getLogger() {
		return this.LOGGER;
	}
}