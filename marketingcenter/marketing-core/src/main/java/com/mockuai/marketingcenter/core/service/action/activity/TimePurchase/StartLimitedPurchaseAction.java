package com.mockuai.marketingcenter.core.service.action.activity.TimePurchase;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**发布限时购活动
 * Created by huangsiqian on 2016/10/10.
 */
@Service
public class StartLimitedPurchaseAction extends TransAction {
    @Autowired
    LimitedPurchaseManager limitedPurchaseManager;
    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        TimePurchaseQTO timePurchaseQTO = (TimePurchaseQTO)context.getRequest().getParam("timePurchaseQTO");
        //入参校验
        if (timePurchaseQTO == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "timePurchaseQTO is null");
        }
        if(timePurchaseQTO.getId()==null){
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请传入id");
        }
        Long id= timePurchaseQTO.getId();
        LimitedPurchaseDO activityDO = new LimitedPurchaseDO();
        activityDO.setId(id);
        LimitedPurchaseDTO limited = limitedPurchaseManager.activityById(id);
        if(limited==null){
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "该活动不存在");
        }
        Boolean flag = limitedPurchaseManager.startLimtedPurchase(activityDO);
        return MarketingUtils.getSuccessResponse(flag);
    }

    @Override
    public String getName() {
        return ActionEnum.START_LIMITED_PURCHASE_ACTION.getActionName();
    }
}
