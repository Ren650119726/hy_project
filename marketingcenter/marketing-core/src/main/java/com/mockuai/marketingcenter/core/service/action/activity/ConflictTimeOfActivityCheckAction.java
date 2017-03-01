package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by edgar.zr on 7/18/2016.
 */
@Service
public class ConflictTimeOfActivityCheckAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConflictTimeOfActivityCheckAction.class);

	@Autowired
	private MarketActivityManager marketActivityManager;
	@Autowired
	private ActivityItemManager activityItemManager;

	@Override
	protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
		List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getRequest().getParam("marketItemDTOs");
		Date startTime = (Date) context.getRequest().getParam("startTime");
		Date endTime = (Date) context.getRequest().getParam("endTime");

		MarketActivityDO marketActivityDO = new MarketActivityDO();
		marketActivityDO.setStartTime(startTime);
		marketActivityDO.setEndTime(endTime);

		List<MarketActivityDO> marketActivityDOs = marketActivityManager.overlappingByTimeActivity(marketActivityDO);
		List<Long> marketActivityIds = new ArrayList<>();
		for (MarketActivityDO temp : marketActivityDOs) {
			if (marketActivityDO.getScope().intValue() == ActivityScope.SCOPE_WHOLE.getValue()) {
				LOGGER.info("conflict in scope whole, activityId : {}", temp.getId());
				return MarketingUtils.getSuccessResponse(false);
			}
			marketActivityIds.add(temp.getId());
		}
		ActivityItemQTO activityItemQTO = new ActivityItemQTO();
		activityItemQTO.setActivityIds(marketActivityIds);
		List<ActivityItemDO> activityItemDOs = activityItemManager.queryActivityItemForActivity(activityItemQTO);
		Set<Long> itemIdSet = new HashSet<>();
		Set<Long> categorySet = new HashSet<>();
		Set<Long> brandSet = new HashSet<>();
		for (MarketItemDTO marketItemDTO : marketItemDTOs) {
			itemIdSet.add(marketItemDTO.getItemId());
			categorySet.add(marketItemDTO.getCategoryId());
			brandSet.add(marketItemDTO.getBrandId());
		}
		for (ActivityItemDO activityItemDO : activityItemDOs) {
			if (itemIdSet.contains(activityItemDO.getItemId())
					    || brandSet.contains(activityItemDO.getBrandId())
					    || categorySet.contains(activityItemDO.getCategoryId())) {
				LOGGER.info("conflict in scope item/branch/category, activityItemId : {}", activityItemDO.getId());
				return MarketingUtils.getSuccessResponse(false);
			}
		}

		return MarketingUtils.getSuccessResponse(true);
	}

	@Override
	public String getName() {
		return ActionEnum.CONFLICT_TIME_OF_ACTIVITY_CHECK.getActionName();
	}
}