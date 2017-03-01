package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据 优惠券 idList 获取优惠券列表,不包含活动主体信息
 * <p/>
 * Created by edgar.zr on 3/03/2016.
 */
@Service
public class GetActivityCouponListByIdsAction extends TransAction {

    @Resource
    private MarketActivityManager marketActivityManager;
    @Resource
    private ActivityCouponManager activityCouponManager;
    @Resource
    private PropertyManager propertyManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        List<Long> activityCouponIdList = (List<Long>) context.getRequest().getParam("activityCouponIdList");
        String bizCode = (String) context.get("bizCode");

        MarketPreconditions.checkNotEmpty(activityCouponIdList, "activityCouponIdList");

        ActivityCouponQTO activityCouponQTO = new ActivityCouponQTO();
        activityCouponQTO.setIdList(activityCouponIdList);
        activityCouponQTO.setBizCode(bizCode);

        // 优惠券列表
        List<ActivityCouponDO> activityCouponDOs = activityCouponManager.queryActivityCoupon(activityCouponQTO);
        if (activityCouponDOs.isEmpty()) {
            return ResponseUtil.getResponse(new ArrayList());
        }

        List<Long> activityIds = new ArrayList<Long>();
        Map<Long, ActivityCouponDO> activityIdKey = new HashMap<Long, ActivityCouponDO>();

        for (ActivityCouponDO activityCouponDO : activityCouponDOs) {
            activityIds.add(activityCouponDO.getActivityId());
            activityIdKey.put(activityCouponDO.getActivityId(), activityCouponDO);
        }

        // 活动主体
        MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
        marketActivityQTO.setBizCode(bizCode);
        marketActivityQTO.setIdList(activityIds);

        List<ActivityCouponDTO> activityCouponDTOs = new ArrayList<ActivityCouponDTO>();
        List<MarketActivityDTO> marketActivityDTOs =
                ModelUtil.genMarketActivityDTOList(marketActivityManager.queryActivity(marketActivityQTO));
        propertyManager.fillUpMarketWithProperty(marketActivityDTOs, bizCode);

        ActivityCouponDTO activityCouponDTO;
        long currentTime = System.currentTimeMillis();
        for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
            // content --> desc
            activityCouponDTO = ModelUtil.genActivityCouponDTO(activityIdKey.get(marketActivityDTO.getId()),
                    marketActivityDTO);
            if (currentTime < marketActivityDTO.getStartTime().getTime()) {
                activityCouponDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue());
            } else if (currentTime >= marketActivityDTO.getStartTime().getTime()
                    && currentTime <= marketActivityDTO.getEndTime().getTime()) {
                activityCouponDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue());
            } else {
                activityCouponDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
            }
            Map<String, PropertyDTO> propertyDTOMap = propertyManager.wrapPropertyDTO(marketActivityDTO.getPropertyList());
            activityCouponDTO.setConsume(propertyManager.extractPropertyConsume(propertyDTOMap));
            activityCouponDTO.setDiscountAmount(propertyManager.extractPropertyQuota(propertyDTOMap));
            StringBuilder sb = new StringBuilder("满");
            sb.append(activityCouponDTO.getConsume() / 100).append("减").append(activityCouponDTO.getDiscountAmount() / 100);
            activityCouponDTO.setContent(sb.toString());
            activityCouponDTOs.add(activityCouponDTO);
        }

        return new MarketingResponse(activityCouponDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ACTIVITY_COUPON_LIST_BY_IDS.getActionName();
    }
}