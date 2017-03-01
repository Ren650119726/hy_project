package com.mockuai.distributioncenter.core.service.action.plan;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ItemSkuDistPlanManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 16/5/18.
 */
@Service
public class GetItemSkuDistPlanByItemAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetItemSkuDistPlanByItemAction.class);
    @Autowired
    private ItemSkuDistPlanManager itemSkuDistPlanManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Long itemId = (Long) request.getParam("itemId");

        if (itemId == null) {
            log.error("itemId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "itemId is null");
        }

        List<ItemSkuDistPlanDTO> itemSkuDistPlanDTOs = itemSkuDistPlanManager.getByItemId(itemId);

        return new DistributionResponse(itemSkuDistPlanDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ITEM_SKU_DIST_PLAN_BY_ITEM.getActionName();
    }
}
