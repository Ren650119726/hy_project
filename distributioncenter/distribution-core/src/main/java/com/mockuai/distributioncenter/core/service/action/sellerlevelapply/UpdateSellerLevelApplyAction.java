package com.mockuai.distributioncenter.core.service.action.sellerlevelapply;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerLevelApplyDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerLevelApplyManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;

/**
 * Created by yeliming on 16/5/18.
 */
@Controller
public class UpdateSellerLevelApplyAction extends TransAction {

	@Resource
    private SellerLevelApplyManager sellerLevelApplyManager; 

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerLevelApplyDTO sellerLevelApplyDTO = (SellerLevelApplyDTO) request.getParam("sellerLevelApplyDTO");
        Boolean b = sellerLevelApplyManager.updateSellerConfig(sellerLevelApplyDTO);
        return new DistributionResponse(b);

    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SELLER_LEVEL_APPLY.getActionName();
    }
}
