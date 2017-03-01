package com.mockuai.distributioncenter.core.service.action.sellerlevelapply;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerLevelApplyDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerLevelApplyQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerLevelApplyManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;

/**
 * Created by yeliming on 16/5/24.
 */
@Controller
public class QuerySellerLevelApplyAction implements Action {
    @Resource
    private SellerLevelApplyManager sellerLevelApplyManager; 

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerLevelApplyQTO sellerLevelApplyQTO = (SellerLevelApplyQTO) request.getParam("sellerLevelApplyQTO");
        if (sellerLevelApplyQTO == null) {
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "sellerLevelApplyQTO is null");
        }
        List<SellerLevelApplyDTO>  list =  sellerLevelApplyManager.querySellerLevelApply(sellerLevelApplyQTO);
        return new DistributionResponse(list,sellerLevelApplyQTO.getTotalCount());
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SELLER_LEVEL_APPLY.getActionName();
    }
}
