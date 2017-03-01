package com.mockuai.marketingcenter.core.service.action.barter;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.MarketLevel;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityHistoryManager;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 店铺级别
 * <p/>
 * Created by edgar.zr on 12/2/15.
 */
@Service
public class QueryBarterAction extends TransAction {

    @Autowired
    private MarketActivityManager marketActivityManager;

    @Autowired
    private ActivityItemManager activityItemManager;

    @Autowired
    private ActivityHistoryManager activityHistoryManager;

    @Autowired
    private PropertyManager propertyManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        MarketActivityQTO marketActivityQTO = (MarketActivityQTO) context.getRequest().getParam("marketActivityQTO");
        String bizCode = (String) context.get("bizCode");

        if (marketActivityQTO == null) {
            return new MarketingResponse<List<MarketActivityDTO>>(ResponseCode.PARAMETER_NULL, "marketActivityQTO is null");
        }

        MarketPreconditions.checkNotNull(marketActivityQTO.getCreatorId(), "creatorId");

        marketActivityQTO.setBizCode(bizCode);

        // 默认查询全部
        if (marketActivityQTO.getLifecycle() == null) {
            marketActivityQTO.setLifecycle(0);
        }

        // 非全部显示则不显示已经失效的
        if (marketActivityQTO.getLifecycle().intValue() != 0) {
            marketActivityQTO.setStatus(ActivityCouponStatus.NORMAL.getValue());
        }

        // 默认为店铺级别
        if (MarketLevel.getByValue(marketActivityQTO.getLevel()) == null)
            marketActivityQTO.setLevel(MarketLevel.SHOP_LEVEL.getValue());

        if (marketActivityQTO.getLevel().intValue() == MarketLevel.BIZ_LEVEL.getValue())
            marketActivityQTO.setCreatorId(null);

        int totalCount = marketActivityManager.queryActivityCount(marketActivityQTO);

        List<MarketActivityDTO> marketActivityDTOs = ModelUtil.genMarketActivityDTOList(marketActivityManager.queryActivity(marketActivityQTO));

        long currentTime = System.currentTimeMillis();
        for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
            if (currentTime < marketActivityDTO.getStartTime().getTime()) {
                marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue());
            } else if (currentTime >= marketActivityDTO.getStartTime().getTime()
                    && currentTime <= marketActivityDTO.getEndTime().getTime()) {
                marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue());
            } else {
                marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
            }

            if (marketActivityDTO.getItemInvalidTime() != null
                    || marketActivityDTO.getStatus().intValue() == ActivityStatus.INVALID.getValue().intValue()) {
                marketActivityDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
            }
        }
        activityItemManager.fillUpActivityItems(marketActivityDTOs, bizCode);
        propertyManager.fillUpMarketWithProperty(marketActivityDTOs, bizCode);
        activityHistoryManager.fillUpActivityWithSales(marketActivityDTOs, bizCode);
        return MarketingUtils.getSuccessResponse(marketActivityDTOs, totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_BARTER.getActionName();
    }
}