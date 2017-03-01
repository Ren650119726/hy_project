package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by edgar.zr on 1/20/16.
 */
@Service
public class ActivityItemListAction extends TransAction {

    @Autowired
    private ActivityItemManager activityItemManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        Long activityId = (Long) context.getRequest().getParam("activityId");
        Integer count = (Integer) context.getRequest().getParam("count");
        Integer offset = (Integer) context.getRequest().getParam("offset");
        String bizCode = (String) context.get("bizCode");

        MarketPreconditions.checkNotNull(activityId, "activityId");

        ActivityItemQTO activityItemQTO = new ActivityItemQTO();
        activityItemQTO.setActivityId(activityId);
        activityItemQTO.setBizCode(bizCode);

        offset = offset == null ? 0 : offset;
        activityItemQTO.setOffset(offset);

        count = count == null ? 100 : count;
        activityItemQTO.setCount(count);

        List<ActivityItemDO> activityItemDOs = activityItemManager.queryActivityItems(activityItemQTO);
        return new MarketingResponse(ModelUtil.genActivityItemDTOList(activityItemDOs), activityItemQTO.getTotalCount());
    }

    @Override
    public String getName() {
        return ActionEnum.ACTIVITY_ITEM_LIST.getActionName();
    }
}