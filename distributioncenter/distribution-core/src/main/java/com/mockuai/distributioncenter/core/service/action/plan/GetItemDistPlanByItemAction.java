package com.mockuai.distributioncenter.core.service.action.plan;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.ItemDistPlanDTO;
import com.mockuai.distributioncenter.common.domain.qto.ItemDistPlanQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ItemDistPlanManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 15/10/27.
 */
@Service
public class GetItemDistPlanByItemAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetItemDistPlanByItemAction.class);

    @Autowired
    private ItemDistPlanManager itemDistPlanManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Long itemId = (Long) request.getParam("itemId");

        if (itemId == null) {
            log.error("itemId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        ItemDistPlanQTO planQTO = new ItemDistPlanQTO();
        planQTO.setItemId(itemId);
        List<ItemDistPlanDTO> itemDistPlanDTOs = itemDistPlanManager.query(planQTO);

        return new DistributionResponse(itemDistPlanDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ITEM_DIST_PLAN_BY_ITEM.getActionName();
    }
}
