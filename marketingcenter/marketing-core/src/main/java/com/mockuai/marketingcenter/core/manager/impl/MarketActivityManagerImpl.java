package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.MarketLevel;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.dao.MarketActivityDAO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.domain.MarketToolDO;
import com.mockuai.marketingcenter.core.engine.tool.Tool;
import com.mockuai.marketingcenter.core.engine.tool.ToolHolder;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.MarketToolManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.manager.PropertyTmplManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketActivityManagerImpl implements MarketActivityManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarketActivityManagerImpl.class);

	private static final int DEFAULT_QUERY_COUNT = 200;

	@Autowired
	private MarketActivityDAO marketActivityDAO;

	@Autowired
	private MarketToolManager marketToolManager;

	@Autowired
	private PropertyTmplManager propertyTmplManager;

	@Autowired
	private PropertyManager propertyManager;

	@Autowired
	private ActivityItemManager activityItemManager;

	@Autowired
	private ToolHolder toolHolder;

	@Override
	public void addActivity(MarketActivityDTO marketActivityDTO) throws MarketingException {

		try {
			//设置活动默认状态
			marketActivityDTO.setStatus(ActivityStatus.NORMAL.getValue());
			marketActivityDTO.setParentId(0L);

			if (marketActivityDTO.getExclusiveMark() == null) {
				marketActivityDTO.setExclusiveMark(Integer.valueOf(1));
			}

			//检查活动所属工具是否存在，不存在，则提示错误
			MarketToolDO toolDO = marketToolManager.getTool(marketActivityDTO.getToolCode());

			if (toolDO == null) {
				LOGGER.warn("can not get tool from db, toolCode : {}", marketActivityDTO.getToolCode());
				throw new MarketingException(ResponseCode.BIZ_E_MARKET_TOOL_NOT_EXIST);
			}

			Tool tool = toolHolder.getTool(toolDO.getToolCode());
			if (tool == null) {
				LOGGER.warn("tool does not exist in toolHolder, toolCode : {}", toolDO.getToolCode());
				throw new MarketingException(ResponseCode.BIZ_E_MARKET_TOOL_NOT_EXIST);
			}

			// 设置营销工具id
			marketActivityDTO.setToolId(toolDO.getId());
			marketActivityDTO.setToolType(toolDO.getType());
			// activityCode 无用处~~
			marketActivityDTO.setActivityCode(toolDO.getToolCode());

			// 验证活动主体必要属性是否有包含
			List<PropertyDTO> propertyDTOs = marketActivityDTO.getPropertyList();
			propertyTmplManager.validateMustIncludeProperty(propertyDTOs, toolDO.getId());

			MarketActivityDO marketActivityDO = ModelUtil.genMarketActivityDO(marketActivityDTO);

			// 单独验证
//            if (ToolType.BARTER_TOOL.getCode().equals(marketActivityDTO.getToolCode())) {
//                validForBarter(marketActivityDTO);
//            }

			if (marketActivityDO.getActivityName().length() > 32)
				marketActivityDO.setActivityName(marketActivityDO.getActivityName().substring(0, 32));

			// 持久化活动主体
			Long activityId = Long.valueOf(marketActivityDAO.addActivity(marketActivityDO));
			marketActivityDTO.setId(activityId);

			// 添加属性
			propertyManager.addPropertyList(propertyDTOs, marketActivityDTO);

			// 持久化活动单品列表
			if (marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_ITEM.getValue() ||
					    marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_CATEGORY.getValue() ||
					    marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_BRAND.getValue()) {
				activityItemManager.addActivityItems(marketActivityDTO);
			}

		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to addActivity bizCode : {}, creatorId : {}, ",
					marketActivityDTO.getBizCode(), marketActivityDTO.getCreatorId(), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	public Long addActivity(MarketActivityDO marketActivityDO) throws MarketingException {
		try {
			if (marketActivityDO.getExclusiveMark() == null) {
				marketActivityDO.setExclusiveMark(Integer.valueOf(1));
			}
			// 默认为店铺级别
			if (MarketLevel.getByValue(marketActivityDO.getLevel()) == null)
				marketActivityDO.setLevel(MarketLevel.SHOP_LEVEL.getValue());
			return Long.valueOf(marketActivityDAO.addActivity(marketActivityDO));
		} catch (Exception e) {
			LOGGER.error("failed when adding activity, activity : {}",
					JsonUtil.toJson(marketActivityDO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

//    /**
//     * 验证创建的换购活动的合法性
//     * 在所有与当前换购时间重合且目标商品相同的活动中，数量不能超过5个，且条件商品集合要没有交集，
//     * 全店的相当于同样一个目标商品只能有一个活动同时进行
//     *
//     * @param marketActivityDTO
//     * @throws MarketingException
//     */
//    public void validForBarter(MarketActivityDTO marketActivityDTO) throws MarketingException {
//
//        ActivityItemDTO tActivityItemDTO = propertyManager.extractPropertySkuId(propertyManager.wrapPropertyDTO(marketActivityDTO.getPropertyList()), null, null);
//
//        if (tActivityItemDTO == null) throw new MarketingException(ResponseCode.PARAMETER_NULL, "换购商品不能为空");
//        Long skuId = tActivityItemDTO.getItemSkuId();
//
//        MarketActivityDO dbMarketActivityDO = new MarketActivityDO();
//        dbMarketActivityDO.setCreatorId(marketActivityDTO.getCreatorId());
//        dbMarketActivityDO.setBizCode(marketActivityDTO.getBizCode());
//        dbMarketActivityDO.setStatus(ActivityStatus.NORMAL.getValue());
//        dbMarketActivityDO.setStartTime(marketActivityDTO.getStartTime());
//        dbMarketActivityDO.setEndTime(marketActivityDTO.getEndTime());
//        dbMarketActivityDO.setToolCode("BarterTool");
//
//        // 所有与创建换购时间重合的
//        List<MarketActivityDO> marketActivityDOs = marketActivityDAO.overlappingByTimeActivity(dbMarketActivityDO);
//        if (marketActivityDOs.isEmpty()) {
//            return;
//        }
//        List<MarketActivityDTO> marketActivityDTOs = ModelUtil.genMarketActivityDTOList(marketActivityDOs);
//        propertyManager.fillUpMarketWithProperty(marketActivityDTOs, marketActivityDTO.getBizCode());
//        // 与当前 skuId 相同
//        Map<String, PropertyDTO> propertyDTOMap;
//        for (Iterator<MarketActivityDTO> iter = marketActivityDTOs.iterator(); iter.hasNext(); ) {
//            MarketActivityDTO activityDTO = iter.next();
//            propertyDTOMap = propertyManager.wrapPropertyDTO(activityDTO.getPropertyList());
//            ActivityItemDTO activityItemDTO = propertyManager.extractPropertySkuId(propertyDTOMap, null, null);
//            if (activityItemDTO != null && activityItemDTO.getItemSkuId().longValue() != skuId) {
//                iter.remove();
//            }
//        }
//        if (marketActivityDTOs.isEmpty()) return;
//        if (marketActivityDTOs.size() > 4) {
//            throw new MarketingException(ResponseCode.BIZ_BARTER_IS_OUT_OF_LIMIT);
//        }
//        if (marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_SHOP.getValue()) {
//            throw new MarketingException(ResponseCode.BIZ_BARTER_THE_SAME_ACTIVITY_ITEM);
//        }
//
//        Set<Long> itemId = new HashSet<Long>();
//        for (ActivityItemDTO activityItemDTO : marketActivityDTO.getActivityItemList()) {
//            itemId.add(activityItemDTO.getItemId());
//        }
//
//        activityItemManager.fillUpActivityItems(marketActivityDTOs, marketActivityDTO.getBizCode());
//        for (MarketActivityDTO activityDTO : marketActivityDTOs) {
//            for (ActivityItemDTO activityItemDTO : activityDTO.getActivityItemList()) {
//                if (itemId.contains(activityItemDTO.getItemId()))
//                    throw new MarketingException(ResponseCode.BIZ_BARTER_THE_SAME_ACTIVITY_ITEM);
//            }
//        }
//    }

	@Override
	public List<MarketActivityDO> queryActivityForSeller(MarketActivityQTO marketActivityQTO) throws MarketingException {
		if (marketActivityQTO == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, " marketActivityQTO is null");
		}
		if (marketActivityQTO.getCreatorId() == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "creatorId is null");
		}

		if (marketActivityQTO.getLifecycle() != null) {
			Date now = new Date();
			if (marketActivityQTO.getLifecycle().intValue() == ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue()) {
				marketActivityQTO.setStartTimeLt(now);
			} else if (marketActivityQTO.getLifecycle().intValue()
					           == ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue()) {
				marketActivityQTO.setStartTimeGe(now);
				marketActivityQTO.setEndTimeLe(now);
			} else if (marketActivityQTO.getLifecycle().intValue()
					           == ActivityLifecycle.LIFECYCLE_ENDED.getValue()) {
				marketActivityQTO.setEndTimeGt(now);
			}
		}

		try {
			return marketActivityDAO.queryActivity(marketActivityQTO);
		} catch (Exception e) {
			LOGGER.error("failed when querying activity for seller, marketActivityQTO : {}",
					JsonUtil.toJson(marketActivityQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public int updateActivity(MarketActivityDO marketActivityDO) throws MarketingException {

		if (marketActivityDO == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "marketActivityDO is null");
		}

		try {
			return marketActivityDAO.updateActivity(marketActivityDO);
		} catch (Exception e) {
			LOGGER.error("failed when updating activity, marketActivityDO : {}",
					JsonUtil.toJson(marketActivityDO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public void updateActivityStatus(Long id, ActivityStatus activityStatus, String bizCode) throws MarketingException {

		MarketActivityDO marketActivityDO = new MarketActivityDO();
		marketActivityDO.setId(id);
		marketActivityDO.setStatus(activityStatus.getValue());
		marketActivityDO.setBizCode(bizCode);

		try {
			int opNum = marketActivityDAO.updateActivity(marketActivityDO);
			if (opNum != 1) {
				LOGGER.error("cannot update activity status correctly, activityId : {}, activityStatus : {}, bizCode : {}",
						id, activityStatus.getValue(), bizCode);
				throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
			}
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to  updateActivityStatus, activityId : {}, activityStatus : {}, bizCode : {}",
					id, activityStatus, bizCode, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public List<MarketActivityDO> queryActivity(MarketActivityQTO marketActivityQTO) throws MarketingException {

		if (marketActivityQTO == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "marketActivityQTO is null");
		}

		if (marketActivityQTO.getCount() == null) {
			marketActivityQTO.setCount(DEFAULT_QUERY_COUNT);
		}

		if (marketActivityQTO.getOffset() == null) {
			marketActivityQTO.setOffset(0);
		}

		//根据lifecycle字段，设置时间查询条件
		if (marketActivityQTO.getLifecycle() != null) {
			Date now = new Date();
			if (marketActivityQTO.getLifecycle().intValue() == ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue()) {
				marketActivityQTO.setStartTimeLt(now);
			} else if (marketActivityQTO.getLifecycle().intValue()
					           == ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue()) {
				marketActivityQTO.setStartTimeGe(now);
				marketActivityQTO.setEndTimeLe(now);
			} else if (marketActivityQTO.getLifecycle().intValue()
					           == ActivityLifecycle.LIFECYCLE_ENDED.getValue()) {
				marketActivityQTO.setEndTimeGt(now);
			}
		}

		try {
			return marketActivityDAO.queryActivity(marketActivityQTO);
		} catch (Exception e) {
			LOGGER.error("failed when querying activity, marketActivityQTO : {}",
					JsonUtil.toJson(marketActivityQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public List<MarketActivityDO> queryActivityForSettlement(MarketActivityQTO marketActivityQTO) throws MarketingException {
		try {
			return marketActivityDAO.queryActivityForSettlement(marketActivityQTO);
		} catch (Exception e) {
			LOGGER.error("failed to queryActivityForSettlement, marketActivityQTO : {}",
					JsonUtil.toJson(marketActivityQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public int countOfQueryActivityForSettlement(MarketActivityQTO marketActivityQTO) throws MarketingException {
		try {
			return marketActivityDAO.countOfQueryActivityForSettlement(marketActivityQTO);
		} catch (Exception e) {
			LOGGER.error("failed to countOfQueryActivityForSettlement, marketActivityQTO : {}",
					JsonUtil.toJson(marketActivityQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public List<MarketActivityDO> overlappingByTimeActivity(MarketActivityDO marketActivityDO) throws MarketingException {
		try {
			List<MarketActivityDO> marketActivityDOs = marketActivityDAO.overlappingByTimeActivity(marketActivityDO);
			return marketActivityDOs;
		} catch (Exception e) {
			LOGGER.error("failed to overlappingByTimeActivity, marketActivityDO : {}",
					JsonUtil.toJson(marketActivityDO), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	public MarketActivityDO getActivity(Long id, String bizCode) throws MarketingException {

		try {
			MarketActivityDO marketActivityDO = new MarketActivityDO();
			marketActivityDO.setId(id);
			marketActivityDO.setBizCode(bizCode);

			MarketActivityDO dbMarketActivityDO = marketActivityDAO.getActivity(marketActivityDO);
			if (dbMarketActivityDO == null) {
				LOGGER.error("can not find marketActivityDO, id : {}, bizCode : {}", id, bizCode);
				throw new MarketingException(ResponseCode.BIZ_E_MARKET_ACTIVITY_NOT_EXIST);
			}
			return dbMarketActivityDO;
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("failed when getting activity, activityId  {}, bizCode : {}",
					id, bizCode, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public int queryActivityCount(MarketActivityQTO marketActivityQTO) throws MarketingException {

		if (marketActivityQTO == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "marketActivityQTO is null");
		}

		//根据lifecycle字段，设置时间查询条件
		if (marketActivityQTO.getLifecycle() != null) {
			Date now = new Date();
			if (marketActivityQTO.getLifecycle().intValue() == ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue()) {
				marketActivityQTO.setStartTimeLt(now);
			} else if (marketActivityQTO.getLifecycle().intValue()
					           == ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue()) {
				marketActivityQTO.setStartTimeGe(now);
				marketActivityQTO.setEndTimeLe(now);
			} else if (marketActivityQTO.getLifecycle().intValue()
					           == ActivityLifecycle.LIFECYCLE_ENDED.getValue()) {
				marketActivityQTO.setEndTimeGt(now);
			}
		}

		try {
			return marketActivityDAO.queryActivityCount(marketActivityQTO);
		} catch (Exception e) {
			LOGGER.error("failed when query count of activity, marketActivityQTO : {}",
					JsonUtil.toJson(marketActivityQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public void linkSubMarketActivity(List<MarketActivityDTO> marketActivityDTOs) throws MarketingException {

		if (marketActivityDTOs == null || marketActivityDTOs.isEmpty()) return;

		Map<Long, MarketActivityDTO> marketActivityDTOMapWithIdKey = new HashMap<Long, MarketActivityDTO>();

		MarketActivityQTO activityQTO = new MarketActivityQTO();
		activityQTO.setParentIdList(new ArrayList<Long>());
		activityQTO.setBizCode(marketActivityDTOs.get(0).getBizCode());

		for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
			marketActivityDTO.setSubMarketActivityList(new ArrayList<MarketActivityDTO>());
			// 不为复合工具或者为复合工具的子结点
			if (marketActivityDTO.getToolType().intValue() != ToolType.COMPOSITE_TOOL.getValue() ||
					    marketActivityDTO.getParentId().intValue() != 0) {
				marketActivityDTO.setSubMarketActivityList(new ArrayList<MarketActivityDTO>());
				continue;
			}
			activityQTO.getParentIdList().add(marketActivityDTO.getId());
			marketActivityDTOMapWithIdKey.put(marketActivityDTO.getId(), marketActivityDTO);
		}

		if (activityQTO.getParentIdList().isEmpty()) {
			return;
		}
		activityQTO.setCount(queryActivityCount(activityQTO));
		if (activityQTO.getCount() != 0) {
			List<MarketActivityDO> subMarketActivityDO = queryActivity(activityQTO);
			MarketActivityDTO marketActivityDTO;
			for (MarketActivityDO marketActivityDO : subMarketActivityDO) {
				marketActivityDTO = marketActivityDTOMapWithIdKey.get(marketActivityDO.getParentId());
				if (marketActivityDTO != null) {
					marketActivityDTO.getSubMarketActivityList().add(ModelUtil.genMarketActivityDTO(marketActivityDO));
				} else {
					LOGGER.error("the parent MarketActivityDTO can not find in the map, marketActivityQTO : {}, marketActivityDO : {}",
							JsonUtil.toJson(activityQTO), JsonUtil.toJson(marketActivityDO));
				}
			}
		}
	}
}