package com.mockuai.marketingcenter.core.service.action.activity;

import com.google.gson.reflect.TypeToken;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 查询复合活动
 */
@Service
public class GetActivityAction implements Action<MarketActivityDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetActivityAction.class);

	@Resource
	private MarketActivityManager marketActivityManager;

	@Resource
	private PropertyManager propertyManager;

	@Resource
	private ActivityItemManager activityItemManager;

	public MarketingResponse<MarketActivityDTO> execute(RequestContext<MarketActivityDTO> request) {

		Long activityId = (Long) request.getRequest().getParam("activityId");
		Long creatorId = (Long) request.getRequest().getParam("creatorId");
		String bizCode = (String) request.get("bizCode");

		if (activityId == null) {
			return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "activityId is null");
		}

		if (creatorId == null) {
			return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "creatorId is null");
		}

		try {

			MarketActivityDO marketActivityDO = marketActivityManager.getActivity(activityId, bizCode);

			MarketActivityDTO marketActivityDTO = ModelUtil.genMarketActivityDTO(marketActivityDO);

			//设置活动属性列表；目前只有简单活动才带属性；复合活动的父活动是不带属性的
//			if (marketActivityDTO.getToolType() != null && marketActivityDTO.getToolType().intValue() == 1) {
//				PropertyQTO propertyQTO = new PropertyQTO();
//				propertyQTO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());
//				propertyQTO.setOwnerId(marketActivityDTO.getId());
//				List propertyDOs = propertyManager.queryProperty(propertyQTO);
//				marketActivityDTO.setPropertyList(ModelUtil.genPropertyDTOList(propertyDOs));
//			}

			//查询复合活动的子活动列表，并塞到对应的复合活动父活动中
			if (marketActivityDTO.getToolType() != null && marketActivityDTO.getToolType().intValue() == 2) {
//				MarketActivityQTO subActivityQTO = new MarketActivityQTO();
//				subActivityQTO.setParentId(marketActivityDTO.getId());
//				subActivityQTO.setBizCode(bizCode);
//				List<MarketActivityDO> subActivityDOs = marketActivityManager.queryActivity(subActivityQTO);
				/**
				 * 子活动展示时要升序
				 */
//				Collections.sort(subActivityDOs, new Comparator<MarketActivityDO>() {
//					@Override
//					public int compare(MarketActivityDO o1, MarketActivityDO o2) {
//						if (o1.getId() > o2.getId()) {
//							return -1;
//						}
//						if (o1.getId() < o2.getId()) {
//							return 1;
//						}
//						return 0;
//					}
//				});
//				List<MarketActivityDTO> subActivityDTOs = ModelUtil.genMarketActivityDTOList(subActivityDOs);

				// 关联子活动
				marketActivityManager.linkSubMarketActivity(Arrays.asList(marketActivityDTO));
				// 关联 propertyList 到 活动
				propertyManager.fillUpMarketWithProperty(Arrays.asList(marketActivityDTO), bizCode);

				// 关联赠送优惠券数据

				// property 关联 activityId, 对应包含的优惠券列表
				Map<Long, List<Long>> activityIdKey = new HashMap<>();
				Set<Long> activityIdOfCouponSet = new HashSet<>();
				List<Long> activityIds = null;
//				List<ActivityCouponDO> activityCouponDOs = null;

				for (MarketActivityDTO activityDTO : marketActivityDTO.getSubMarketActivityList()) {
					activityIds = JsonUtil.parseJson(activityDTO.getPropertyMap().get(PropertyDTO.COUPON_LIST).getValue(),
							new TypeToken<List<Long>>() {
							}.getType());
					if (activityIds == null || activityIds.isEmpty()) {
						continue;
					}
					activityDTO.setSimpleCouponList(new ArrayList<ActivityCouponDTO>());
					activityIdKey.put(activityDTO.getId(), new ArrayList<Long>());
					for (Long activityIdOfCoupon : activityIds) {
						activityIdOfCouponSet.add(activityIdOfCoupon);
						activityIdKey.get(activityDTO.getId()).add(activityIdOfCoupon);
					}
				}
				if (!activityIdOfCouponSet.isEmpty()) {
					MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
					marketActivityQTO.setIdList(new ArrayList<>(activityIdOfCouponSet));
					marketActivityQTO.setBizCode(bizCode);
					List<MarketActivityDO> couponList = marketActivityManager.queryActivity(marketActivityQTO);
					List<MarketActivityDTO> couponDTOList = ModelUtil.genMarketActivityDTOList(couponList);
					propertyManager.fillUpMarketWithProperty(couponDTOList, bizCode);

					Map<Long, ActivityCouponDTO> couponIdOfActivityKey = new HashMap<>();

					ActivityCouponDTO activityCouponDTO;
					for (MarketActivityDTO coupon : couponDTOList) {
						activityCouponDTO = new ActivityCouponDTO();
						activityCouponDTO.setActivityId(coupon.getId());
						activityCouponDTO.setName(coupon.getActivityName());
						activityCouponDTO.setDiscountAmount(propertyManager.extractPropertyQuota(coupon.getPropertyMap()));
						couponIdOfActivityKey.put(coupon.getId(), activityCouponDTO);
					}
					for (MarketActivityDTO activityDTO : marketActivityDTO.getSubMarketActivityList()) {
						if (activityIdKey.get(activityDTO.getId()) == null) {
							continue;
						}
						for (Long couponId : activityIdKey.get(activityDTO.getId())) {
							activityDTO.getSimpleCouponList().add(couponIdOfActivityKey.get(couponId));
						}
					}
				}
			}

			//TODO 这里暂时时候服务器时间，后续需要考虑服务器时间的全局统一性和准确性
			//设置活动lifecycle属性值
			long currentTime = System.currentTimeMillis();
			if (currentTime < marketActivityDO.getStartTime().getTime()) {
				marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue());
			} else if (currentTime >= marketActivityDO.getStartTime().getTime()
					           && currentTime <= marketActivityDO.getEndTime().getTime()) {
				marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue());
			} else {
				marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
			}

			//如果为单品活动，则需要查询活动单品列表。TODO 性能待优化，优化成基于活动id列表批量查询~这里已经是批量查询了把?
			if (marketActivityDTO.getScope() != null
					    && marketActivityDTO.getScope().intValue() != ActivityScope.SCOPE_WHOLE.getValue()) {
				List<ActivityItemDO> activityItemDOs = activityItemManager.queryActivityItem(
						marketActivityDTO.getId(), marketActivityDTO.getCreatorId(), bizCode);

				marketActivityDTO.setActivityItemList(ModelUtil.genActivityItemDTOList(activityItemDOs));
			}

			return MarketingUtils.getSuccessResponse(marketActivityDTO);

		} catch (MarketingException e) {
			LOGGER.error("Action:" + request.getRequest().getCommand(), e);
			return MarketingUtils.getFailResponse(e.getCode(), e.getMessage());
		}
	}

	public String getName() {
		return ActionEnum.GET_ACTIVITY.getActionName();
	}
}