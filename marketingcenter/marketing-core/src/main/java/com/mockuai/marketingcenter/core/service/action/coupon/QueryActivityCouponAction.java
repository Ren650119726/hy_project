package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.MarketLevel;
import com.mockuai.marketingcenter.common.constant.PropertyEnum;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 查询优惠券
 * <p/>
 * 1.老优惠券，只要有 seller_id, lifecycle 查询
 * 2.新的有码优惠券，需要和老优惠券区别，在于是否使用码
 */
@Service
public class QueryActivityCouponAction implements Action {

	@Resource
	private MarketActivityManager marketActivityManager;

	@Resource
	private ActivityCouponManager activityCouponManager;

	@Resource
	private PropertyManager propertyManager;

	@Resource
	private ActivityItemManager activityItemManager;

	public MarketingResponse execute(RequestContext context) throws MarketingException {

		ActivityCouponQTO activityCouponQTO = (ActivityCouponQTO) context.getRequest().getParam("activityCouponQTO");
		String bizCode = (String) context.get("bizCode");

		MarketPreconditions.checkNotNull(activityCouponQTO, "activityCouponQTO");
		activityCouponQTO.setBizCode(bizCode);

		// 默认为店铺级别
		if (MarketLevel.getByValue(activityCouponQTO.getLevel()) == null)
			activityCouponQTO.setLevel(MarketLevel.SHOP_LEVEL.getValue());

		if (activityCouponQTO.getLevel().intValue() == MarketLevel.BIZ_LEVEL.getValue())
			activityCouponQTO.setActivityCreatorId(null);

		// 默认查询全部
		if (activityCouponQTO.getLifecycle() == null) {
			activityCouponQTO.setLifecycle(0);
		}

		// 非全部显示则不显示已经失效的
		if (activityCouponQTO.getLifecycle().intValue() != 0) {
			activityCouponQTO.setStatus(ActivityCouponStatus.NORMAL.getValue());
		}

		//查询指定活动券信息
		List<ActivityCouponDO> activityCouponDOs = activityCouponManager.queryActivityCoupon(activityCouponQTO);

		List<ActivityCouponDTO> activityCouponDTOs = ModelUtil.genActivityCouponDTOList(activityCouponDOs);

		//查询优惠券的活动主体信息
		if (activityCouponDOs != null && activityCouponDOs.isEmpty() == false) {
			Set<Long> activityIdSet = new HashSet<Long>();
			Map<Long, ActivityCouponDTO> activityIdKeyMap = new HashMap<Long, ActivityCouponDTO>();
			for (ActivityCouponDTO activityCouponDTO : activityCouponDTOs) {
				activityIdSet.add(activityCouponDTO.getActivityId());
				activityIdKeyMap.put(activityCouponDTO.getActivityId(), activityCouponDTO);
			}

			MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
			marketActivityQTO.setIdList(new CopyOnWriteArrayList<Long>(activityIdSet));
			marketActivityQTO.setStatus(activityCouponQTO.getStatus());
			marketActivityQTO.setBizCode(bizCode);

			List<MarketActivityDO> marketActivityDOs = marketActivityManager.queryActivity(marketActivityQTO);
			Map<Long, MarketActivityDTO> marketActivityDTOMap = new HashMap<Long, MarketActivityDTO>();

			// 查询所有可能的指定商品
			ActivityItemQTO activityItemQTO = new ActivityItemQTO();
			activityItemQTO.setBizCode(bizCode);
			activityItemQTO.setActivityIdList(new ArrayList<Long>(activityIdSet));
			List<ActivityItemDO> activityItemDOs = activityItemManager.queryActivityItems(activityItemQTO);

			Map<Long, List<ActivityItemDO>> activityIdKeyActivityItemValue = new HashMap<Long, List<ActivityItemDO>>();
			for (ActivityItemDO activityItemDO : activityItemDOs) {
				if (!activityIdKeyActivityItemValue.containsKey(activityItemDO.getActivityId())) {
					activityIdKeyActivityItemValue.put(activityItemDO.getActivityId(), new ArrayList<ActivityItemDO>());
				}
				activityIdKeyActivityItemValue.get(activityItemDO.getActivityId()).add(activityItemDO);
			}

			ActivityCouponDTO activityCouponDTO;

			for (MarketActivityDO marketActivityDO : marketActivityDOs) {
				if (activityCouponQTO.getLevel().intValue() != marketActivityDO.getLevel().intValue())
					continue;
				MarketActivityDTO marketActivityDTO = ModelUtil.genMarketActivityDTO(marketActivityDO);

				PropertyQTO propertyQTO = new PropertyQTO();
				propertyQTO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());
				propertyQTO.setOwnerId(marketActivityDTO.getId());
				List propertyDOs = propertyManager.queryProperty(propertyQTO);
				marketActivityDTO.setPropertyList(ModelUtil.genPropertyDTOList(propertyDOs));

				Map<String, PropertyDO> propertyPKeyMap = propertyManager.wrapProperty(propertyDOs);
				activityCouponDTO = activityIdKeyMap.get(marketActivityDO.getId());
				// 提出价值和消费门槛
				if (activityCouponDTO != null) {
					activityCouponDTO.setDiscountAmount(Long.valueOf(propertyPKeyMap.get(PropertyEnum.QUOTA.getValue()).getValue()));
					activityCouponDTO.setConsume(Long.parseLong(propertyPKeyMap.get(PropertyEnum.CONSUME.getValue()).getValue()));
					activityCouponDTO.setName(marketActivityDO.getActivityName());
				}

				//TODO 这里暂时使用服务器时间，后续需要考虑服务器时间的全局统一性和准确性
				//设置活动lifecycle属性值
				long currentTime = System.currentTimeMillis();
				if (currentTime < marketActivityDTO.getStartTime().getTime()) {
					marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue());
				} else if (currentTime >= marketActivityDTO.getStartTime().getTime()
						           && currentTime <= marketActivityDTO.getEndTime().getTime()) {
					marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue());
				} else {
					marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
				}

				// 失效的优惠券活动设置为已结束
				if (marketActivityDTO.getStatus().intValue() == ActivityCouponStatus.INVALID.getValue().intValue()) {
					marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
				}

				//如果为单品活动，则需要查询活动单品列表。
				if (marketActivityDTO.getScope() != null && (marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_ITEM.getValue()
						                                             || marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_BRAND.getValue()
						                                             || marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_CATEGORY.getValue())) {

					if (activityIdKeyActivityItemValue.containsKey(marketActivityDTO.getId())) {
						marketActivityDTO.setActivityItemList(ModelUtil.genActivityItemDTOList(activityIdKeyActivityItemValue.get(marketActivityDTO.getId())));
					}
				}

				marketActivityDTOMap.put(marketActivityDO.getId(), marketActivityDTO);
			}

			MarketActivityDTO marketActivityDTO;
			//将营销活动信息填充到营销活动券对象中
			Iterator<ActivityCouponDTO> iterator = activityCouponDTOs.iterator();
			Date now = new Date();
			while (iterator.hasNext()) {
				activityCouponDTO = iterator.next();
				marketActivityDTO = marketActivityDTOMap.get(activityCouponDTO.getActivityId());
				if (marketActivityDTO == null) {
					iterator.remove();
					activityCouponQTO.setTotalCount(activityCouponQTO.getTotalCount() - 1);
					continue;
				}
				activityCouponDTO.setMarketActivity(marketActivityDTO);
				if (activityCouponDTO.getValidDuration() != null) {
					activityCouponDTO.setStartTime(now);
					activityCouponDTO.setEndTime(DateUtils.addDays(now, activityCouponDTO.getValidDuration()));
				}
			}
		}

		return ResponseUtil.getResponse(activityCouponDTOs, activityCouponQTO.getTotalCount());
	}

	public String getName() {
		return ActionEnum.QUERY_ACTIVITY_COUPON.getActionName();
	}
}