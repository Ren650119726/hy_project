package com.mockuai.distributioncenter.core.service.action.plan;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ItemSkuDistPlanManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.distributioncenter.core.util.JsonUtil;

/**
 * Created by duke on 15/10/27.
 */
@Service
public class UpdateItemSkuDistPlanAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateItemSkuDistPlanAction.class);

    @Autowired
    private ItemSkuDistPlanManager itemSkuDistPlanManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        ItemSkuDistPlanDTO itemSkuDistPlanDTO = (ItemSkuDistPlanDTO) request.getParam("itemSkuDistPlanDTO");
        log.info("update itemSkuDistPlan: {}", JsonUtil.toJson(itemSkuDistPlanDTO));
        if (itemSkuDistPlanDTO == null) {
            log.error("itemSkuDistPlanDTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }
        itemSkuDistPlanManager.update(itemSkuDistPlanDTO);
        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_ITEM_SKU_DIST_PLAN.getActionName();
    }
}
