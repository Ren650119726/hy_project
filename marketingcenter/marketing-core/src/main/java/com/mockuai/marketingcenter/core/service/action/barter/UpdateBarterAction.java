package com.mockuai.marketingcenter.core.service.action.barter;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by edgar.zr on 12/25/15.
 */
@Service
public class UpdateBarterAction extends TransAction {

    @Autowired
    private MarketActivityManager marketActivityManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        MarketActivityDTO marketActivityDTO = (MarketActivityDTO) context.getRequest().getParam("marketActivityDTO");
        marketActivityManager.updateActivity(ModelUtil.genMarketActivityDO(marketActivityDTO));

        return MarketingUtils.getSuccessResponse();
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_BARTER.getActionName();
    }
}