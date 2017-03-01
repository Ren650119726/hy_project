package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 使优惠活动失效
 */
@Service
public class InvalidActivityAction extends TransAction {

    @Resource
    private MarketActivityManager marketActivityManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        String bizCode = (String) context.get("bizCode");
        Long activityId = (Long) context.getRequest().getParam("activityId");
        Long activityCreatorId = (Long) context.getRequest().getParam("activityCreatorId");

        MarketPreconditions.checkNotNull(activityId, "activityId");
        MarketPreconditions.checkNotNull(activityCreatorId, "activityCreatorId");

        marketActivityManager.updateActivityStatus(activityId, ActivityStatus.INVALID, bizCode);

        return MarketingUtils.getSuccessResponse();
    }

    public String getName() {
        return ActionEnum.INVALID_ACTIVITY.getActionName();
    }
}