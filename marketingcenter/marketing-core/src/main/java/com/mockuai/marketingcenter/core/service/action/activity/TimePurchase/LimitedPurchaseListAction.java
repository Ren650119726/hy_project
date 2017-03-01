package com.mockuai.marketingcenter.core.service.action.activity.TimePurchase;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangsiqian on 2016/10/10.
 */
@Service
public class LimitedPurchaseListAction extends TransAction {
    @Autowired
    private LimitedPurchaseManager activityManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        TimePurchaseQTO timePurchaseQTO = (TimePurchaseQTO)context.getRequest().getParam("timePurchaseQTO");
        Integer offset = timePurchaseQTO.getOffset();
        if (offset == null) {
            offset = 0;
        }
        Integer count = timePurchaseQTO.getCount();
        if (count == null || count > 20) {
            count = 20;
        }

        Integer status = timePurchaseQTO.getRunStatus();
        if("".equals(status)){
            status = null;
        }
        timePurchaseQTO.setOffset((offset-1)*count);
        timePurchaseQTO.setCount(count);
        timePurchaseQTO.setRunStatus(status);
        Integer totalCount = activityManager.activityCount(timePurchaseQTO);
        List<LimitedPurchaseDTO> activityDTOs= activityManager.activityList(timePurchaseQTO);
        if(null==activityDTOs){

            totalCount=0;
        }

        return MarketingUtils.getSuccessResponse(activityDTOs, totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.TIME_PURCHASE_LIST.getActionName();
    }
}
