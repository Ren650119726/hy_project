package com.mockuai.distributioncenter.core.service.action.plan;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.ItemDistPlanDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ItemDistPlanManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 15/10/27.
 */
@Service
public class AddItemDistPlanAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddItemDistPlanAction.class);

    @Autowired
    private ItemDistPlanManager itemDistPlanManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        ItemDistPlanDTO itemDistPlanDTO = (ItemDistPlanDTO) request.getParam("itemDistPlanDTO");

        if (itemDistPlanDTO == null) {
            log.error("itemDistPlanDTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        Long id = itemDistPlanManager.add(itemDistPlanDTO);
        itemDistPlanDTO.setId(id);

        return new DistributionResponse(itemDistPlanDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_ITEM_DIST_PLAN.getActionName();
    }
}
