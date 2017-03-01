package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyTmplQTO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.domain.MarketToolDO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.domain.PropertyTmplDO;
import com.mockuai.marketingcenter.core.engine.tool.Tool;
import com.mockuai.marketingcenter.core.engine.tool.ToolHolder;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.MarketToolManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.manager.PropertyTmplManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddActivityAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddActivityAction.class);

	@Resource
	private MarketActivityManager marketActivityManager;

	@Resource
	private MarketToolManager marketToolManager;

	@Resource
	private PropertyTmplManager propertyTmplManager;

	@Resource
	private PropertyManager propertyManager;

	@Resource
	private ActivityItemManager activityItemManager;

	@Resource
	private ToolHolder toolHolder;

	/**
	 * 有券优惠券 无同时举行活动的数量限制, 无单品能参与活动数量的限制
	 * 1.满减送
	 *
	 * @param context
	 * @return
	 * @throws MarketingException
	 */
	protected MarketingResponse<Long> doTransaction(RequestContext context) throws MarketingException {
		MarketActivityDTO marketActivityDTO = (MarketActivityDTO) context.getRequest().getParam("marketActivityDTO");
		String bizCode = (String) context.get("bizCode");

		//入参校验
		if (marketActivityDTO == null) {
			return new MarketingResponse(ResponseCode.PARAMETER_NULL, "marketActivityDTO is null");
		}

		if (StringUtils.isBlank(bizCode)) {
			return new MarketingResponse<Long>(ResponseCode.PARAMETER_NULL, "bizCode is null");
		}

		marketActivityDTO.setBizCode(bizCode);

		//工具 code 不能为空
		if (StringUtils.isBlank(marketActivityDTO.getToolCode())) {
			return new MarketingResponse(ResponseCode.PARAM_E_TOOL_CODE_CANNOT_BE_NULL);
		}

		//活动名称不能为空
		if (StringUtils.isBlank(marketActivityDTO.getActivityName())) {
			return new MarketingResponse(ResponseCode.PARAM_E_ACTIVITY_NAME_CANNOT_BE_NULL);
		}

		if (marketActivityDTO.getActivityName().length() > 32)
			marketActivityDTO.setActivityName(marketActivityDTO.getActivityName().substring(0, 32));

		//活动内容不能为空。优惠券中的content取的就是活动的content
		if (StringUtils.isBlank(marketActivityDTO.getActivityContent())) {
			marketActivityDTO.setActivityContent("");//默认置为空字符串
		}

		//活动创建者id不能为空
		if (marketActivityDTO.getCreatorId() == null) {
			return new MarketingResponse(ResponseCode.PARAM_E_ACTIVITY_CREATOR_ID_CANNOT_BE_NULL);
		}

		//活动范围默认值设置
		if (marketActivityDTO.getScope() == null) {
			//默认设为全店活动
			marketActivityDTO.setScope(ActivityScope.SCOPE_WHOLE.getValue());
		} else {
			//营销活动范围有效性校验
			if (ActivityScope.getScopeByValue(marketActivityDTO.getScope()) == null) {
				return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_SCOPE_INVALID);
			}
			//如果是单品活动，则营销活动单品列表不能为空
			if (marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_ITEM.getValue()) {
				if (marketActivityDTO.getActivityItemList() == null
						    || marketActivityDTO.getActivityItemList().isEmpty()) {
					return new MarketingResponse(ResponseCode.PARAM_E_ACTIVITY_ITEM_CANNOT_BE_NULL);
				}
			}
		}

		//活动时间校验，活动时间不能为空
		if (marketActivityDTO.getStartTime() == null || marketActivityDTO.getEndTime() == null) {
			return new MarketingResponse(ResponseCode.PARAM_E_ACTIVITY_TIME_CANNOT_BE_NULL);
		}

		// 活动时间有效性验证,只要判断结束时间是否在开始时间之前
		if (marketActivityDTO.getStartTime().after(marketActivityDTO.getEndTime())) {
			return new MarketingResponse<>(ResponseCode.PARAM_E_ACTIVITY_TIME_INVALID);
		}

		List<PropertyDTO> propertyDTOs = marketActivityDTO.getPropertyList();
		Map<String, PropertyDO> propertyDOMap = new HashMap<>();
		if (propertyDTOs != null) {
			for (PropertyDTO propertyDTO : propertyDTOs) {
				propertyDOMap.put(propertyDTO.getPkey(), ModelUtil.genPropertyDO(propertyDTO));
			}
		}

		//如果活动优惠券标志为空，则默认设置为需要优惠券
		if (marketActivityDTO.getCouponMark() == null) {
			marketActivityDTO.setCouponMark(1);
		}

		//如果是优惠券，且是否有码缺少，则默认将优惠券类型设置为无码券
		if (marketActivityDTO.getCouponMark() != null &&
				    marketActivityDTO.getCouponMark() == 1 && marketActivityDTO.getCouponType() == null) {
			//TODO 重构时考虑是否删掉couponType字段，然后以activity_coupon中的coupon_type字段为准
			marketActivityDTO.setCouponType(CouponType.TYPE_NO_CODE.getValue());
		}

		//活动创建者类型设置默认值。TODO 后续考虑是否直接去掉活动创建者类型, 卖家, 运营. 有可能是商城创建的, 商城应该也是有 sellerId, 本质上不存在什么区别把?
		if (marketActivityDTO.getCreatorType() == null) {
			marketActivityDTO.setCreatorType(1);
		}

		try {
			//检查活动所属工具是否存在，不存在，则提示错误
			MarketToolDO toolDO = marketToolManager.getTool(marketActivityDTO.getToolCode());

			if (toolDO == null) {
				LOGGER.warn("can not get tool from db, toolCode : {}, bizCode : {}",
						marketActivityDTO.getToolCode(), bizCode);
				return new MarketingResponse(ResponseCode.BIZ_E_MARKET_TOOL_NOT_EXIST);
			}

			Tool tool = toolHolder.getTool(toolDO.getToolCode());
			if (tool == null) {
				LOGGER.warn("can not get tool from toolHolder, toolCode : {}", toolDO.getToolCode());
				return new MarketingResponse<Long>(ResponseCode.BIZ_E_MARKET_TOOL_NOT_EXIST);
			}

			//设置营销工具id
			marketActivityDTO.setToolId(toolDO.getId());

			//查询营销活动属性模板列表
			// 属性模版不做隔离
			PropertyTmplQTO propertyTmplQTO = new PropertyTmplQTO();
			propertyTmplQTO.setOwnerId(toolDO.getId());
			propertyTmplQTO.setOwnerType(PropertyOwnerType.TOOL.getValue());//营销工具属性模板
			List<PropertyTmplDO> propertyTmplDOs = propertyTmplManager.queryPropertyTmpl(propertyTmplQTO);

			//筛选出必选的属性模板
			Map<String, PropertyTmplDO> propertyTmplMap = new HashMap<String, PropertyTmplDO>();
			if (propertyTmplDOs != null) {
				for (PropertyTmplDO propertyTmplDO : propertyTmplDOs) {
					propertyTmplMap.put(propertyTmplDO.getPkey(), propertyTmplDO);
				}
			}

			//必选属性校验
			for (Map.Entry<String, PropertyTmplDO> entry : propertyTmplMap.entrySet()) {

				//如果营销活动属性列表中没有涵盖所有必选的属性模板中的属性的话，则返回错误提示
				if (propertyDOMap.containsKey(entry.getKey()) == false) {
					if (entry.getValue().getRequiredMark().intValue() == 1) {
						return new MarketingResponse(ResponseCode.PARAMETER_MISSING,
								                            "the property " + entry.getKey() + " is missing");
					}
				} else {
					PropertyTmplDO propertyTmplDO = entry.getValue();
					//填充属性名称
					propertyDOMap.get(entry.getKey()).setName(propertyTmplDO.getName());
					//填充属性取值类型
					propertyDOMap.get(entry.getKey()).setValueType(propertyTmplDO.getValueType());
				}
			}

			//持久化营销活动
			MarketActivityDO marketActivityDO = ModelUtil.genMarketActivityDO(marketActivityDTO);

			//设置活动默认状态
			marketActivityDO.setStatus(ActivityStatus.NORMAL.getValue());
			//如果活动code为空的话，则直接为其设置工具code
			if (StringUtils.isBlank(marketActivityDO.getActivityCode())) {
				marketActivityDO.setActivityCode(toolDO.getToolCode());
			}

			marketActivityDO.setBizCode(bizCode);
			//设置活动类型为简单活动
			marketActivityDO.setToolType(ToolType.SIMPLE_TOOL.getValue());
			marketActivityDO.setParentId(0L);
			Long activityId = marketActivityManager.addActivity(marketActivityDO);

			//添加营销活动属性列表
			for (PropertyDO propertyDO : propertyDOMap.values()) {
				propertyDO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());//活动属性
				propertyDO.setOwnerId(Long.valueOf(activityId));
				propertyDO.setCreatorId(marketActivityDO.getCreatorId());
				propertyDO.setCreatorType(marketActivityDO.getCreatorType());
				propertyDO.setBizCode(bizCode);
				propertyManager.addProperty(propertyDO);
			}

			marketActivityDTO.setId(activityId);

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

			return new MarketingResponse(activityId);
		} catch (MarketingException e) {
			LOGGER.error("toolId:{}, creatorId:{}",
					marketActivityDTO.getToolId(), marketActivityDTO.getCreatorId(), e);
			return new MarketingResponse(e.getCode(), e.getMessage());
		} catch (Exception e) {
			LOGGER.error("toolId:{}, creatorId:{}",
					marketActivityDTO.getToolId(), marketActivityDTO.getCreatorId(), e);
			return new MarketingResponse(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	public String getName() {
		return ActionEnum.ADD_ACTIVITY.getActionName();
	}
}