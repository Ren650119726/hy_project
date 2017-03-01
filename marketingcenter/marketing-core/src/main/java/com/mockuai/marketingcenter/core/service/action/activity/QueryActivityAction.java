package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class QueryActivityAction implements Action<List<MarketActivityDTO>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryActivityAction.class);

	@Resource
	private MarketActivityManager marketActivityManager;
	@Resource
	private PropertyManager propertyManager;
	@Resource
	private ActivityItemManager activityItemManager;

	public MarketingResponse<List<MarketActivityDTO>> execute(RequestContext<List<MarketActivityDTO>> request) {

		MarketActivityQTO marketActivityQTO = (MarketActivityQTO) request.getRequest().getParam("marketActivityQTO");
		String bizCode = (String) request.get("bizCode");

		if (marketActivityQTO == null) {
			return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "marketActivityQTO is null");
		}

		marketActivityQTO.setBizCode(bizCode);
		marketActivityQTO.setLevel(null);
		marketActivityQTO.setToolType(ToolType.COMPOSITE_TOOL.getValue());

		try {

			// 默认查询全部
			if (marketActivityQTO.getLifecycle() == null) {
				marketActivityQTO.setLifecycle(0);
			}

			// 非全部显示则不显示已经失效的
			if (marketActivityQTO.getLifecycle().intValue() != 0) {
				marketActivityQTO.setStatus(ActivityCouponStatus.NORMAL.getValue());
			}

			//只查询第一级的活动，子活动直接塞到所属的父活动中即可
			marketActivityQTO.setParentId(0L);
			int totalCount = marketActivityManager.queryActivityCount(marketActivityQTO);
			List<MarketActivityDO> result = marketActivityManager.queryActivity(marketActivityQTO);
			//转换活动对象，并填充每个活动的属性列表
			List<MarketActivityDTO> marketActivityDTOs = ModelUtil.genMarketActivityDTOList(result);
			Map<Long, MarketActivityDTO> parentActivityMap = new HashMap<Long, MarketActivityDTO>();
			for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
				//目前只有简单活动才带属性；复合活动的父活动是不带属性的
				if (marketActivityDTO.getToolType() == null
						    || marketActivityDTO.getToolType().intValue() == ToolType.SIMPLE_TOOL.getValue()) {
					PropertyQTO propertyQTO = new PropertyQTO();
					propertyQTO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());
					propertyQTO.setOwnerId(marketActivityDTO.getId());
					List propertyDOs = propertyManager.queryProperty(propertyQTO);
					marketActivityDTO.setPropertyList(ModelUtil.genPropertyDTOList(propertyDOs));
				} else if (marketActivityDTO.getToolType().intValue() == ToolType.COMPOSITE_TOOL.getValue()) {
					parentActivityMap.put(marketActivityDTO.getId(), marketActivityDTO);
				}

				//设置优惠活动的生命周期状态
				//TODO 这里暂时时候服务器时间，后续需要考虑服务器时间的全局统一性和准确性
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

				if (marketActivityDTO.getStatus().intValue() == ActivityStatus.INVALID.getValue().intValue()) {
					marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
				}

				//如果为单品活动，则需要查询活动单品列表。
				if (marketActivityDTO.getScope() != null
						    && marketActivityDTO.getScope().intValue() != ActivityScope.SCOPE_WHOLE.getValue()) {
					List<ActivityItemDO> activityItemDOs = activityItemManager.queryActivityItem(
							marketActivityDTO.getId(), marketActivityDTO.getCreatorId(), bizCode);

					marketActivityDTO.setActivityItemList(ModelUtil.genActivityItemDTOList(activityItemDOs));
				}
			}

			//查询复合活动的子活动列表，并塞到对应的复合活动父活动中
			if (parentActivityMap.isEmpty() == false) {
				MarketActivityQTO subActivityQTO = new MarketActivityQTO();
				subActivityQTO.setParentIdList(new CopyOnWriteArrayList<>(parentActivityMap.keySet()));
				subActivityQTO.setCreatorId(marketActivityQTO.getCreatorId());
				subActivityQTO.setBizCode(bizCode);
				List<MarketActivityDO> subActivityDOs = marketActivityManager.queryActivity(subActivityQTO);
				List<MarketActivityDTO> subActivityDTOs = ModelUtil.genMarketActivityDTOList(subActivityDOs);

				//TODO 性能待重构，重构成批量查询, 放入 manager 层
				for (MarketActivityDTO marketActivityDTO : subActivityDTOs) {
					PropertyQTO propertyQTO = new PropertyQTO();
					propertyQTO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());
					propertyQTO.setOwnerId(marketActivityDTO.getId());
					List propertyDOs = propertyManager.queryProperty(propertyQTO);
					marketActivityDTO.setPropertyList(ModelUtil.genPropertyDTOList(propertyDOs));
				}

				//将子活动塞到所属父活动中去
				for (MarketActivityDTO marketActivityDTO : subActivityDTOs) {
					MarketActivityDTO parentActivity = parentActivityMap.get(marketActivityDTO.getParentId());
					if (parentActivity.getSubMarketActivityList() == null) {
						parentActivity.setSubMarketActivityList(new CopyOnWriteArrayList<MarketActivityDTO>());
					}
					parentActivity.getSubMarketActivityList().add(marketActivityDTO);
				}
			}

			return MarketingUtils.getSuccessResponse(marketActivityDTOs, totalCount);
		} catch (MarketingException e) {
			LOGGER.error("Action:" + request.getRequest().getCommand(), e);
			return MarketingUtils.getFailResponse(e.getCode(), e.getMessage());
		}
	}

	public String getName() {
		return ActionEnum.QUERY_ACTIVITY.getActionName();
	}
}