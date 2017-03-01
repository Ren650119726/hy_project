package com.mockuai.distributioncenter.core.service.action.plan;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ItemSkuDistPlanManager;
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
public class AddItemSkuDistPlanAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddItemSkuDistPlanAction.class);

    @Autowired
    private ItemSkuDistPlanManager itemSkuDistPlanManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        ItemSkuDistPlanDTO itemSkuDistPlanDTO = (ItemSkuDistPlanDTO) request.getParam("itemSkuDistPlanDTO");
        log.info("[{}]distGainsRatio:{}",itemSkuDistPlanDTO.getDistGainsRatio());

        if (itemSkuDistPlanDTO == null) {
            log.error("itemSkuDistPlanDTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        Long id = itemSkuDistPlanManager.add(itemSkuDistPlanDTO);
        itemSkuDistPlanDTO.setId(id);

        return new DistributionResponse(itemSkuDistPlanDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_ITEM_SKU_DIST_PLAN.getActionName();
    }
}
