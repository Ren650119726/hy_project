package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.CommonItemEnum;
import com.mockuai.marketingcenter.common.constant.MarketLevel;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyTmplQTO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.domain.MarketToolDO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.domain.PropertyTmplDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.MarketToolManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.manager.PropertyTmplManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.DateUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.timelimitcenter.common.qto.TimelimitConflictCheckQTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 添加复合营销活动<br>
 * 1. market_tool 无层级结构,即没有子工具
 * 2. 一个复合活动完全由一个 market_tool 来创建,即子活动无需进行 market_tool 的验证
 */
@Service
public class AddCompositeActivityAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddCompositeActivityAction.class);

	@Autowired
	private MarketActivityManager marketActivityManager;
	@Autowired
	private MarketToolManager marketToolManager;
	@Autowired
	private PropertyTmplManager propertyTmplManager;
	@Autowired
	private PropertyManager propertyManager;
	@Autowired
	private ActivityItemManager activityItemManager;
//	@Autowired
//	private TimeLimitManager timeLimitManager;

	protected MarketingResponse<Long> doTransaction(RequestContext context) throws MarketingException {

		MarketActivityDTO marketActivityDTO = (MarketActivityDTO) context.getRequest().getParam("marketActivityDTO");
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");

		//入参校验
		if (marketActivityDTO == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "marketActivityDTO is null");
		}

		if (StringUtils.isBlank(bizCode)) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "bizCode is null");
		}

		marketActivityDTO.setBizCode(bizCode);

		//活动名称不能为空
		if (StringUtils.isBlank(marketActivityDTO.getActivityName())) {
			throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_NAME_CANNOT_BE_NULL);
		}

		//活动内容不能为空。
		if (StringUtils.isBlank(marketActivityDTO.getActivityContent())) {
			marketActivityDTO.setActivityContent("");//默认置为空字符串
		}

		//活动创建者id不能为空
		if (marketActivityDTO.getCreatorId() == null) {
			throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_CREATOR_ID_CANNOT_BE_NULL);
		}

		//活动时间校验，活动时间不能为空
		if (marketActivityDTO.getStartTime() == null || marketActivityDTO.getEndTime() == null) {
			throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_TIME_CANNOT_BE_NULL);
		}

		// 活动时间有效性验证
		if (marketActivityDTO.getStartTime().after(marketActivityDTO.getEndTime())) {
			return new MarketingResponse<Long>(ResponseCode.PARAM_E_ACTIVITY_TIME_INVALID);
		}

		//FIXME 复合活动不需要优惠券。后续如果需要再重构
		marketActivityDTO.setCouponMark(0);

		//如果互斥标志没填，则默认设置为互斥
		if (marketActivityDTO.getExclusiveMark() == null) {
			marketActivityDTO.setExclusiveMark(1);
		}

		//活动创建者类型设置默认值。TODO 后续考虑是否直接去掉活动创建者类型
		if (marketActivityDTO.getCreatorType() == null) {
			marketActivityDTO.setCreatorType(1);
		}
		// 默认为店铺级别
		marketActivityDTO.setLevel(MarketLevel.SHOP_LEVEL.getValue());
		//活动范围默认值设置
		if (marketActivityDTO.getScope() == null) {
			//默认设为全店活动
			marketActivityDTO.setScope(ActivityScope.SCOPE_WHOLE.getValue());
		} else {
			//营销活动范围有效性校验
			if (ActivityScope.getScopeByValue(marketActivityDTO.getScope()) == null) {
				throw new MarketingException(ResponseCode.BIZ_E_ACTIVITY_SCOPE_INVALID);
			}

			//如果是单品活动，则营销活动单品列表不能为空
			if (marketActivityDTO.getScope().intValue() != ActivityScope.SCOPE_WHOLE.getValue()) {
				if (marketActivityDTO.getActivityItemList() == null
						    || marketActivityDTO.getActivityItemList().isEmpty()) {
					throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_ITEM_CANNOT_BE_NULL);
				}
			}
		}

		MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
		marketActivityQTO.setBizCode(marketActivityDTO.getBizCode());
		marketActivityQTO.setCreatorId(marketActivityDTO.getCreatorId());

		marketActivityQTO.setStatus(ActivityStatus.NORMAL.getValue());
		marketActivityQTO.setToolType(marketActivityDTO.getToolType());
		marketActivityQTO.setLevel(marketActivityDTO.getLevel());
		marketActivityQTO.setParentId(0L);

		// 当前商家创建的所有优惠活动
		List<MarketActivityDO> allMarketActivityDOs = marketActivityManager.queryActivityForSeller(marketActivityQTO);

		ActivityItemQTO activityItemQTO = new ActivityItemQTO();
		activityItemQTO.setBizCode(marketActivityDTO.getBizCode());
		activityItemQTO.setActivityCreatorId(marketActivityDTO.getCreatorId());
		activityItemQTO.setActivityIdList(new ArrayList<Long>());
		Date current = new Date();
		for (MarketActivityDO marketActivityDO : allMarketActivityDOs) {
			// 一个商品同时只能参与一个发布中的满减送
			if (marketActivityDO.getCouponMark().intValue() == 0 &&
					    marketActivityDO.getStatus().intValue() == ActivityStatus.NORMAL.getValue() &&
					    marketActivityDO.getEndTime().after(current) &&
					    DateUtils.isOverlappingDates(marketActivityDTO.getStartTime(),
							    marketActivityDTO.getEndTime(),
							    marketActivityDO.getStartTime(),
							    marketActivityDO.getEndTime())) {

				activityItemQTO.getActivityIdList().add(marketActivityDO.getId());
				// 满减送无全场级别
				if (marketActivityDO.getScope().intValue() == ActivityScope.SCOPE_WHOLE.getValue()
						    || ActivityScope.SCOPE_WHOLE.getValue() == marketActivityDTO.getScope().intValue()) {
					throw new MarketingException(ResponseCode.ACTIVITY_ITEM_NOT_UNIQUE);
				}
			}
		}
		TimelimitConflictCheckQTO timelimitConflictCheckQTO = new TimelimitConflictCheckQTO();
		timelimitConflictCheckQTO.setStartTime(marketActivityDTO.getStartTime());
		timelimitConflictCheckQTO.setEndTime(marketActivityDTO.getEndTime());
		if (marketActivityDTO.getScope() == ActivityScope.SCOPE_WHOLE.getValue()) {
			timelimitConflictCheckQTO.setSymbolAll("1");
		} else {
			timelimitConflictCheckQTO.setTlItemList(new ArrayList<TimelimitConflictCheckQTO.TlItem>());
			TimelimitConflictCheckQTO.TlItem tlItem;
			for (ActivityItemDTO activityItemDTO : marketActivityDTO.getActivityItemList()) {
				tlItem = new TimelimitConflictCheckQTO().new TlItem();
				tlItem.setCategoryId(activityItemDTO.getCategoryId());
				tlItem.setItemBrandId(activityItemDTO.getBrandId());
				tlItem.setItemId(activityItemDTO.getItemId());
				timelimitConflictCheckQTO.getTlItemList().add(tlItem);
			}
		}
		//TODO uncomment for conflict check
//		Boolean status = timeLimitManager.conflictCheckImInTimelimit(timelimitConflictCheckQTO, appKey);
//		if (!status) {
//			LOGGER.error("cannot create the composite activity, conflict with time limit activity, timeLimitQTO : {}, response : {}",
//					JsonUtil.toJson(timelimitConflictCheckQTO), status);
//			throw new MarketingException(ResponseCode.ACTIVITY_ITEM_NOT_UNIQUE);
//		}

		// 时间重合活动下指定商品判重, 此处的活动一定为 scope_item
		// 在所有的无券活动中判断是否当前指定商品已经参加了相应的活动
		if (!activityItemQTO.getActivityIdList().isEmpty()) {

			List<ActivityItemDO> activityItemDOs = activityItemManager.queryActivityItems(activityItemQTO);
			Set<Long> itemIds = new HashSet<>();
			Set<Long> brandIds = new HashSet<>();
			Set<Long> categroyIds = new HashSet<>();

			for (ActivityItemDTO activityItemDTO : marketActivityDTO.getActivityItemList()) {
				itemIds.add(activityItemDTO.getItemId());
				categroyIds.add(activityItemDTO.getCategoryId());
				brandIds.add(activityItemDTO.getBrandId());
			}
			// 如果此次创建商品已在之前的优惠活动中,则不予创建
			for (ActivityItemDO activityItemDO : activityItemDOs) {
				if (itemIds.contains(activityItemDO.getItemId())
						    || categroyIds.contains(activityItemDO.getCategoryId())
						    || brandIds.contains(activityItemDO.getBrandId())) {
					throw new MarketingException(ResponseCode.ACTIVITY_ITEM_NOT_UNIQUE);
				}
			}
		}

		//工具 code 不能为空
		if (StringUtils.isBlank(marketActivityDTO.getToolCode())) {
			return new MarketingResponse<Long>(ResponseCode.PARAM_E_TOOL_CODE_CANNOT_BE_NULL);
		}

		//检查活动所属工具是否存在，不存在，则提示错误
		MarketToolDO toolDO = marketToolManager.getTool(marketActivityDTO.getToolCode());

		if (toolDO == null) {
			return new MarketingResponse<Long>(ResponseCode.BIZ_E_MARKET_TOOL_NOT_EXIST);
		}

		//检查活动工具类型
		if (toolDO.getType() == null || toolDO.getType().intValue() != ToolType.COMPOSITE_TOOL.getValue()) {
			return new MarketingResponse<Long>(ResponseCode.PARAM_E_TOOL_CODE_INVALID);
		}

		List<MarketActivityDTO> subMarketActivityList = marketActivityDTO.getSubMarketActivityList();
		if (subMarketActivityList == null || subMarketActivityList.isEmpty()) {
			return new MarketingResponse<Long>(ResponseCode.PARAMETER_MISSING, "活动结构非法");
		}

		//查询营销活动属性模板列表
		// 属性模版不做隔离
		PropertyTmplQTO propertyTmplQTO = new PropertyTmplQTO();
		propertyTmplQTO.setOwnerId(toolDO.getId());
		propertyTmplQTO.setOwnerType(Integer.valueOf(1));//营销工具属性模板
		List<PropertyTmplDO> propertyTmplDOs = propertyTmplManager.queryPropertyTmpl(propertyTmplQTO);

		Map<String, PropertyTmplDO> propertyTmplMap = new HashMap<String, PropertyTmplDO>();
		if (propertyTmplDOs != null) {
			for (PropertyTmplDO propertyTmplDO : propertyTmplDOs) {
				propertyTmplMap.put(propertyTmplDO.getPkey(), propertyTmplDO);
			}
		}

		//添加父活动
		Long parentActivityId = addParentMarketActivity(marketActivityDTO, toolDO, bizCode);
		MarketActivityDO parentActivity = marketActivityManager.getActivity(parentActivityId, bizCode);

		//添加子活动 TODO 性能待优化，重构成批量操作
		for (MarketActivityDTO subMarketActivity : subMarketActivityList) {
			addSubMarketActivity(parentActivity, subMarketActivity, propertyTmplMap, bizCode);
		}

		return new MarketingResponse(parentActivityId);
	}

	private long addParentMarketActivity(MarketActivityDTO marketActivityDTO,
	                                     MarketToolDO marketToolDO, String bizCode) throws MarketingException {

		try {
			//设置营销工具码
			marketActivityDTO.setToolCode(marketToolDO.getToolCode());

			//设置营销工具id
			marketActivityDTO.setToolId(marketToolDO.getId());

			//设置toolType
			marketActivityDTO.setToolType(ToolType.COMPOSITE_TOOL.getValue());

			//设置parentId
			marketActivityDTO.setParentId(0L);

			//持久化营销活动
			MarketActivityDO marketActivityDO = ModelUtil.genMarketActivityDO(marketActivityDTO);

			//设置活动默认状态。
			marketActivityDO.setStatus(ActivityStatus.PUBLISH.getValue());

			marketActivityDO.setCommonItem(CommonItemEnum.ALL_ITEM.getValue());

			//如果活动code为空的话，则直接为其设置工具code
			if (StringUtils.isBlank(marketActivityDO.getActivityCode())) {
				marketActivityDO.setActivityCode(marketToolDO.getToolCode());
			}

			marketActivityDO.setBizCode(bizCode);
			Long activityId = marketActivityManager.addActivity(marketActivityDO);

			//如果是单品活动，则持久化活动单品列表
			if (marketActivityDTO.getScope().intValue() != ActivityScope.SCOPE_WHOLE.getValue()) {
				List<ActivityItemDTO> activityItemDTOs = marketActivityDTO.getActivityItemList();
				//填充营销活动id以及activityCreatorId属性
				for (ActivityItemDTO activityItemDTO : activityItemDTOs) {
					activityItemDTO.setBizCode(bizCode);
					activityItemDTO.setActivityId(activityId);
					activityItemDTO.setActivityCreatorId(marketActivityDO.getCreatorId());
				}
				activityItemManager.addActivityItems(ModelUtil.genActivityItemDOList(activityItemDTOs), marketActivityDTO.getScope());
			}
			return activityId;
		} catch (MarketingException e) {
			LOGGER.error("toolId:{}, creatorId:{}",
					marketActivityDTO.getToolId(), marketActivityDTO.getCreatorId(), e);
			throw new MarketingException(e.getCode(), e.getMessage());
		} catch (Exception e) {
			LOGGER.error("toolId:{}, creatorId:{}",
					marketActivityDTO.getToolId(), marketActivityDTO.getCreatorId(), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	/**
	 * 添加子活动
	 *
	 * @param parentMarketActivity
	 * @param subMarketActivity
	 * @param propertyTmplMap
	 * @param bizCode
	 * @return
	 * @throws MarketingException
	 */
	private long addSubMarketActivity(MarketActivityDO parentMarketActivity, MarketActivityDTO subMarketActivity,
	                                  Map<String, PropertyTmplDO> propertyTmplMap, String bizCode) throws MarketingException {
		//入参校验
		if (parentMarketActivity == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "parentMarketActivity is null");
		}
		if (subMarketActivity == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "subMarketActivity is null");
		}

		//FIXME 复合活动的子活动互斥。后续如果需要做非互斥需要重构
		subMarketActivity.setExclusiveMark(1);

		//创建者id跟随父活动
		subMarketActivity.setCreatorType(parentMarketActivity.getCreatorType());
		subMarketActivity.setCreatorId(parentMarketActivity.getCreatorId());
		//活动名称跟随父活动
		subMarketActivity.setActivityName(parentMarketActivity.getActivityName());
		//活动范围跟随父活动
		subMarketActivity.setScope(parentMarketActivity.getScope());
		//活动时间跟随父活动
		subMarketActivity.setStartTime(parentMarketActivity.getStartTime());
		subMarketActivity.setEndTime(parentMarketActivity.getEndTime());
		//优惠券标志跟随父活动
		subMarketActivity.setCouponMark(parentMarketActivity.getCouponMark());
		//营销工具跟随父活动
		subMarketActivity.setToolCode(parentMarketActivity.getToolCode());
		subMarketActivity.setToolId(parentMarketActivity.getToolId());
		subMarketActivity.setToolType(parentMarketActivity.getToolType());
		//设置父id
		subMarketActivity.setParentId(parentMarketActivity.getId());
		subMarketActivity.setLevel(parentMarketActivity.getLevel());
		subMarketActivity.setCommonItem(parentMarketActivity.getCommonItem());

		try {

			List<PropertyDTO> propertyDTOs = subMarketActivity.getPropertyList();
			Map<String, PropertyDO> propertyDOMap = new HashMap<String, PropertyDO>();
			if (propertyDTOs != null) {
				for (PropertyDTO propertyDTO : propertyDTOs) {
					propertyDOMap.put(propertyDTO.getPkey(), ModelUtil.genPropertyDO(propertyDTO));
				}
			}
			for (Map.Entry<String, PropertyTmplDO> entry : propertyTmplMap.entrySet()) {
				if (propertyDOMap.containsKey(entry.getKey()) == false) {

					//如果营销活动属性列表中没有涵盖所有必选的属性模板中的属性的话，则返回错误提示
					if (entry.getValue().getRequiredMark() != null && entry.getValue().getRequiredMark() == 1) {
						throw new MarketingException(ResponseCode.PARAMETER_MISSING,
								                            "the property " + entry.getKey() + " is missing");
					}
				} else {
					// FIXME 进行赠品数量的限制,目前先定为一个赠品
					PropertyTmplDO propertyTmplDO = entry.getValue();
					if (propertyDOMap.containsKey(entry.getKey())) {
						//填充属性名称
						propertyDOMap.get(entry.getKey()).setName(propertyTmplDO.getName());
						//填充属性取值类型
						propertyDOMap.get(entry.getKey()).setValueType(propertyTmplDO.getValueType());
					}
				}
			}

			//持久化营销活动
			MarketActivityDO marketActivityDO = ModelUtil.genMarketActivityDO(subMarketActivity);
			//设置活动默认状态。
			marketActivityDO.setStatus(ActivityStatus.PUBLISH.getValue());
			//如果活动code为空的话，则直接为其设置工具code
			if (StringUtils.isBlank(marketActivityDO.getActivityCode())) {
				marketActivityDO.setActivityCode(subMarketActivity.getToolCode());
			}

			marketActivityDO.setBizCode(bizCode);
			Long activityId = marketActivityManager.addActivity(marketActivityDO);

			//添加营销活动属性列表
			for (PropertyDO propertyDO : propertyDOMap.values()) {
				propertyDO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());//活动属性
				propertyDO.setOwnerId(Long.valueOf(activityId));
				propertyDO.setCreatorId(subMarketActivity.getCreatorId());
				propertyDO.setCreatorType(subMarketActivity.getCreatorType());
				propertyDO.setBizCode(bizCode);
				propertyManager.addProperty(propertyDO);
			}

			return activityId;
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("toolId:{}, creatorId:{}",
					subMarketActivity.getToolId(), subMarketActivity.getCreatorId(), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	public String getName() {
		return ActionEnum.ADD_COMPOSITE_ACTIVITY.getActionName();
	}
}