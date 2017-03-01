package com.mockuai.distributioncenter.core.service.action.plan;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.ItemDistPlanDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ItemDistPlanManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.distributioncenter.core.util.JsonUtil;

/**
 * Created by duke on 16/3/22.
 */
@Service
public class UpdateItemDistPlanAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(UpdateItemDistPlanAction.class);

    @Autowired
    private ItemDistPlanManager itemDistPlanManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        ItemDistPlanDTO itemDistPlanDTO = (ItemDistPlanDTO) request.getParam("itemDistPlanDTO");
         log.info("update itemDistPlan: {}", JsonUtil.toJson(itemDistPlanDTO));
        if (itemDistPlanDTO == null) {
            log.error("itemDistPlanDTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "itemDistPlanDTO is null");
        }

        itemDistPlanManager.update(itemDistPlanDTO);

        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_ITEM_DIST_PLAN.getActionName();
    }
}
