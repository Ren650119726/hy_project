package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.engine.component.impl.FillUpSkuInfo;
import com.mockuai.marketingcenter.core.engine.component.impl.ItemTotalPrice;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 11/9/15.
 * <p/>
 * 给出优惠券，判断是否能在订单中使用
 * 多店的判断和单店铺相同，一张优惠券的使用范围始终在一个店铺
 */
@Service
public class ActivityCouponAvailableAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityCouponAvailableAction.class);

	@Autowired
	private GrantedCouponManager grantedCouponManager;

	@Autowired
	private MarketActivityManager marketActivityManager;

	@Autowired
	private ActivityItemManager activityItemManager;

	@Autowired
	private PropertyManager propertyManager;

	@Autowired
	private ComponentHelper componentHelper;

	@Override
	protected MarketingResponse doTransaction(RequestContext request) throws MarketingException {

		List<MarketItemDTO> itemList = (List<MarketItemDTO>) request.getRequest().getParam("itemList");
		// 此处的是 grantedCouponId，默认是拥有此优惠券
		Long couponId = (Long) request.getRequest().getParam("couponId");
		Long userId = (Long) request.getRequest().getParam("userId");
		String appKey = (String) request.get("appKey");
		String bizCode = (String) request.get("bizCode");

		MarketPreconditions.checkNotNull(couponId, "couponId");
		MarketPreconditions.checkNotEmpty(itemList, "itemList");

		// 获取 MarketActivity, activityItem, property. 通过engine确认是否为可用优惠券
		GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
		grantedCouponQTO.setId(couponId);
		grantedCouponQTO.setStatus(UserCouponStatus.UN_USE.getValue());

		List<GrantedCouponDO> grantedCouponDOs = grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);
		if (grantedCouponDOs.isEmpty()) {
			return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_COUPON_NOT_EXIST);
		}

		GrantedCouponDO grantedCouponDO = grantedCouponDOs.get(0);
		// 领取的优惠券已经过期
		if (grantedCouponDO.getInvalidTime() != null && grantedCouponDO.getInvalidTime().before(new Date()))
			return new MarketingResponse(ResponseCode.BIZ_ACTIVITY_COUPON_OVERDUE);

		MarketActivityDO marketActivityDO = marketActivityManager.getActivity(grantedCouponDO.getActivityId(), bizCode);

		for (MarketItemDTO marketItemDTO : itemList) {
			MarketPreconditions.checkNotNull(marketItemDTO.getSellerId(), "sellerId");
			if (marketItemDTO.getItemType() == null || marketItemDTO.getItemType().intValue() != 1)
				return new MarketingResponse<SettlementInfo>(ResponseCode.PARAMETER_ERROR, "非普通商品不能使用优惠券");
			// 只要是单店铺级别的优惠券需要验证优惠券的使用范围
			if (marketActivityDO.getScope().intValue() != ActivityScope.SCOPE_WHOLE.getValue()
					    && marketItemDTO.getSellerId().longValue() != grantedCouponDO.getActivityCreatorId().longValue()) {
				return new MarketingResponse(ResponseCode.SUCCESS);
			}
		}

		// 填充商品信息
		componentHelper.execute(FillUpSkuInfo.wrapParams(itemList, userId, appKey));

		MarketActivityDTO marketActivityDTO = ModelUtil.genMarketActivityDTO(marketActivityDO);
		PropertyQTO propertyQTO = new PropertyQTO();
		propertyQTO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());
		propertyQTO.setOwnerId(marketActivityDTO.getId());
		propertyQTO.setCreatorId(marketActivityDTO.getCreatorId());
		List<PropertyDO> propertyDOs = propertyManager.queryProperty(propertyQTO);
		marketActivityDTO.setPropertyList(ModelUtil.genPropertyDTOList(propertyDOs));

		DiscountInfo discountInfo = new DiscountInfo();
		discountInfo.setActivity(ModelUtil.genMarketActivityDTO(marketActivityDO));
		discountInfo.setAvailableCoupons(
				Arrays.asList(ModelUtil.genGrantedCouponDTO(grantedCouponDO, marketActivityDTO)));

		//获取总价
		Long itemTotalPrice = componentHelper.<Long>execute(ItemTotalPrice.wrapParams(itemList));

		// 指定商品要重新计算适用的价格
		if (marketActivityDO.getScope().intValue() == ActivityScope.SCOPE_ITEM.getValue()) {
			ActivityItemQTO activityItemQTO = new ActivityItemQTO();
			activityItemQTO.setActivityCreatorId(marketActivityDO.getCreatorId());
			//查询优惠活动的指定商品
			List<ActivityItemDO> activityItemDOs = activityItemManager.queryActivityItem(marketActivityDO.getId(), marketActivityDO.getCreatorId(), bizCode);
			if (activityItemDOs == null || activityItemDOs.isEmpty()) {
				LOGGER.error("the activity coupon is invalid");
				return new MarketingResponse(ResponseCode.SUCCESS);
			}
			List<MarketItemDTO> specifiedItemList = new ArrayList<MarketItemDTO>();
			Map<Long, List<MarketItemDTO>> itemIdKeyMap = new HashMap<Long, List<MarketItemDTO>>();
			// 统计 itemId 下的 itemSkuId
			for (MarketItemDTO marketItemDTO : itemList) {
				if (!itemIdKeyMap.containsKey(marketItemDTO.getItemId())) {
					itemIdKeyMap.put(marketItemDTO.getItemId(), new ArrayList<MarketItemDTO>());
				}
				itemIdKeyMap.get(marketItemDTO.getItemId()).add(marketItemDTO);
			}

			// 订单中适合使用该优惠券的商品列表
			for (ActivityItemDO activityItemDO : activityItemDOs) {
				if (itemIdKeyMap.containsKey(activityItemDO.getItemId())) {
					specifiedItemList.addAll(itemIdKeyMap.get(activityItemDO.getItemId()));
				}
			}

			// 订单包含优惠券活动指定的商品
			if (specifiedItemList.isEmpty()) {
				return new MarketingResponse(ResponseCode.SUCCESS);
			} else {
				itemTotalPrice = componentHelper.<Long>execute(ItemTotalPrice.wrapParams(specifiedItemList));
			}
		}

		long consume = 0L;
		long quota = 0L;
		Map<String, PropertyDO> propertyKeyMap = propertyManager.wrapProperty(propertyDOs);

		if (propertyKeyMap.containsKey(PropertyDTO.CONSUME)) {
			String consumeStr = propertyKeyMap.get(PropertyDTO.CONSUME).getValue();
			consume = Long.parseLong(consumeStr);
		}

		// 没有达到此优惠活动的门槛
		if (itemTotalPrice < consume) {
			return new MarketingResponse(ResponseCode.SUCCESS);
		}

		if (propertyKeyMap.containsKey(PropertyDTO.QUOTA)) {
			String quotaStr = propertyKeyMap.get(PropertyDTO.QUOTA).getValue();
			quota = Long.parseLong(quotaStr);
		}

		StringBuilder sb;
		int quotaT;
		//FIXME 显示结算订单详情时的优惠券, content 需要显示未 满XX即可使用
		for (PropertyDTO propertyDTO : marketActivityDTO.getPropertyList()) {
			if (PropertyDTO.CONSUME.equals(propertyDTO.getPkey())) {
				if (StringUtils.isBlank(propertyDTO.getValue())) {
					quotaT = 0;
				} else {
					quotaT = Integer.valueOf(propertyDTO.getValue());
				}
				quotaT = quotaT / 100;
				sb = new StringBuilder("满");
				sb.append(String.valueOf(quotaT)).append("即可使用");
				discountInfo.getAvailableCoupons().get(0).setContent(sb.toString());
				break;
			}
		}
		// 每个订单只能使用一张优惠券
		discountInfo.setDiscountAmount(Long.valueOf(quota));
		discountInfo.setConsume(Long.valueOf(consume));
		return new MarketingResponse(discountInfo);
	}

	@Override
	public String getName() {
		return ActionEnum.ACTIVITY_COUPON_AVAILABLE.getActionName();
	}
}