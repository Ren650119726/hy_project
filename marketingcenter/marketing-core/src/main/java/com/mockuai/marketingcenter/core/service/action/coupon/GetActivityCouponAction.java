package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 8/19/15.
 * <p/>
 * 查看指定优惠券
 * 提供给 mop 接口，不包含优惠主体信息
 */
@Service
public class GetActivityCouponAction extends TransAction {

	@Resource
	private MarketActivityManager marketActivityManager;

	@Resource
	private ActivityCouponManager activityCouponManager;

	@Resource
	private PropertyManager propertyManager;

	@Override
	protected MarketingResponse<ActivityCouponDTO> doTransaction(RequestContext context) throws MarketingException {

		Long activityCouponId = (Long) context.getRequest().getParam("activityCouponId");
		Long creatorId = (Long) context.getRequest().getParam("creatorId");
		String bizCode = (String) context.get("bizCode");

		MarketPreconditions.checkNotNull(activityCouponId, "activityCouponId");
		MarketPreconditions.checkNotNull(creatorId, "creatorId");

		ActivityCouponDO activityCouponDO = activityCouponManager.getActivityCoupon(activityCouponId, creatorId, bizCode);
		MarketActivityDO marketActivityDO = marketActivityManager.getActivity(activityCouponDO.getActivityId().longValue(), bizCode);

		// content --> desc
		ActivityCouponDTO activityCouponDTO = ModelUtil.genActivityCouponDTO(activityCouponDO, ModelUtil.genMarketActivityDTO(marketActivityDO));

		long currentTime = System.currentTimeMillis();
		if (currentTime < marketActivityDO.getStartTime().getTime()) {
			activityCouponDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue());
		} else if (currentTime >= marketActivityDO.getStartTime().getTime()
				           && currentTime <= marketActivityDO.getEndTime().getTime()) {
			activityCouponDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue());
		} else {
			activityCouponDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
		}

		PropertyQTO propertyQTO = new PropertyQTO();
		propertyQTO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());
		propertyQTO.setOwnerId(marketActivityDO.getId());
		List<PropertyDO> propertyDOs = propertyManager.queryProperty(propertyQTO);

		List<PropertyDTO> propertyDTOs = ModelUtil.genPropertyDTOList(propertyDOs);
		Map<String, PropertyDTO> propertyDTOMap = propertyManager.wrapPropertyDTO(propertyDTOs);

		activityCouponDTO.setConsume(propertyManager.extractPropertyConsume(propertyDTOMap));
		activityCouponDTO.setDiscountAmount(propertyManager.extractPropertyQuota(propertyDTOMap));
		StringBuilder sb = new StringBuilder("满");
		sb.append(activityCouponDTO.getConsume() / 100).append("减").append(activityCouponDTO.getDiscountAmount() / 100);
		activityCouponDTO.setContent(sb.toString());

		Date now = new Date();

		if (activityCouponDTO.getValidDuration() != null) {
			activityCouponDTO.setStartTime(now);
			activityCouponDTO.setEndTime(DateUtils.addDays(now, activityCouponDTO.getValidDuration()));
		}

		return new MarketingResponse(activityCouponDTO);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ACTIVITY_COUPON.getActionName();
	}
}