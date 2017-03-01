package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.core.dao.ActivityItemDAO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 7/19/15.
 */
public class ActivityItemManagerImpl implements ActivityItemManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityItemManagerImpl.class);

	@Resource
	private ActivityItemDAO activityItemDAO;

	@Override
	public boolean addActivityItems(List<ActivityItemDO> activityItemDOs, Integer scope) throws MarketingException {
		//入参校验
		if (activityItemDOs == null || activityItemDOs.isEmpty()) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "activityItemDOs is null");
		}

		for (ActivityItemDO activityItemDO : activityItemDOs) {
			if (activityItemDO.getActivityId() == null) {
				throw new MarketingException(ResponseCode.PARAMETER_MISSING, "activityId is missing");
			}

			if (activityItemDO.getActivityCreatorId() == null) {
				throw new MarketingException(ResponseCode.PARAMETER_MISSING, "activityCreatorId is missing");
			}

			if (activityItemDO.getItemId() == null && scope == ActivityScope.SCOPE_ITEM.getValue()) {
				throw new MarketingException(ResponseCode.PARAMETER_MISSING, "itemId is missing");
			}

			if (activityItemDO.getSellerId() == null && scope == ActivityScope.SCOPE_ITEM.getValue()) {
				throw new MarketingException(ResponseCode.PARAMETER_MISSING, "sellerId is missing");
			}
			if (activityItemDO.getBrandId() == null && scope == ActivityScope.SCOPE_BRAND.getValue()) {
				throw new MarketingException(ResponseCode.PARAMETER_MISSING, "brandId is missing");
			}
			if (activityItemDO.getCategoryId() == null && scope == ActivityScope.SCOPE_CATEGORY.getValue()) {
				throw new MarketingException(ResponseCode.PARAMETER_MISSING, "categoryId is missing");
			}
		}

		try {
			activityItemDAO.addActivityItems(activityItemDOs);
		} catch (Exception e) {
			LOGGER.error("error to addActivityItems, activityItemDOs : {}", JsonUtil.toJson(activityItemDOs), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
		return true;
	}

	@Override
	public void addActivityItems(MarketActivityDTO marketActivityDTO) throws MarketingException {
		try {

			for (ActivityItemDTO activityItemDTO : marketActivityDTO.getActivityItemList()) {
				activityItemDTO.setBizCode(marketActivityDTO.getBizCode());
				activityItemDTO.setActivityId(marketActivityDTO.getId());
				activityItemDTO.setActivityCreatorId(marketActivityDTO.getCreatorId());
			}
			activityItemDAO.addActivityItems(ModelUtil.genActivityItemDOList(marketActivityDTO.getActivityItemList()));

		} catch (Exception e) {
			LOGGER.error("error to addActivityItems, bizCode : {}, activityId : {}, activityCreatorId : {}",
					marketActivityDTO.getBizCode(), marketActivityDTO.getId(), marketActivityDTO.getCreatorId(), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public void fillUpActivityItems(List<MarketActivityDTO> marketActivityDTOs, String bizCode) throws MarketingException {
		if (marketActivityDTOs == null || marketActivityDTOs.isEmpty()) return;

		ActivityItemQTO activityItemQTO = new ActivityItemQTO();
		activityItemQTO.setActivityIdList(new ArrayList<Long>());
		activityItemQTO.setBizCode(bizCode);

		for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
			activityItemQTO.getActivityIdList().add(marketActivityDTO.getId());
		}

		List<ActivityItemDTO> activityItemDTOs = ModelUtil.genActivityItemDTOList(activityItemDAO.queryActivityItem(activityItemQTO));

		Map<Long, List<ActivityItemDTO>> activityIdKeyActivityItemValue = new HashMap<Long, List<ActivityItemDTO>>();
		for (ActivityItemDTO activityItemDTO : activityItemDTOs) {
			if (!activityIdKeyActivityItemValue.containsKey(activityItemDTO.getActivityId())) {
				activityIdKeyActivityItemValue.put(activityItemDTO.getActivityId(), new ArrayList<ActivityItemDTO>());
			}
			activityIdKeyActivityItemValue.get(activityItemDTO.getActivityId()).add(activityItemDTO);
		}

		for (MarketActivityDTO activityDTO : marketActivityDTOs) {
			activityItemDTOs = activityIdKeyActivityItemValue.get(activityDTO.getId());
			activityDTO.setActivityItemList(activityItemDTOs == null ? Collections.EMPTY_LIST : activityItemDTOs);
		}
	}

	@Override
	public List<ActivityItemDO> queryActivityItem(Long activityId, Long activityCreatorId, String bizCode) throws MarketingException {

		//入参校验
		if (activityId == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "activityId is null");
		}

		if (activityCreatorId == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "activityCreatorId is null");
		}

		try {
			ActivityItemQTO activityItemQTO = new ActivityItemQTO();
			activityItemQTO.setActivityId(activityId);
			activityItemQTO.setBizCode(bizCode);
			return activityItemDAO.queryActivityItem(activityItemQTO);
		} catch (Exception e) {
			LOGGER.error("error to queryActivityItem, activityId : {}, activityCreatorId : {}, bizCode : {}",
					activityId, activityCreatorId, bizCode, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<ActivityItemDO> queryActivityItemForActivity(ActivityItemQTO activityItemQTO) throws MarketingException {

		try {
			return activityItemDAO.queryActivityItemForActivity(activityItemQTO);
		} catch (Exception e) {
			LOGGER.error("error to queryActivityItemForActivity, activityItemQTO : {}",
					JsonUtil.toJson(activityItemQTO), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<ActivityItemDO> queryActivityItems(ActivityItemQTO activityItemQTO) throws MarketingException {

		try {
			return activityItemDAO.queryActivityItem(activityItemQTO);
		} catch (Exception e) {
			LOGGER.error("error to queryActivityItem, activityIds : {}, activityCreatorId : {}",
					JsonUtil.toJson(activityItemQTO.getActivityIdList()), activityItemQTO.getActivityCreatorId());
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}