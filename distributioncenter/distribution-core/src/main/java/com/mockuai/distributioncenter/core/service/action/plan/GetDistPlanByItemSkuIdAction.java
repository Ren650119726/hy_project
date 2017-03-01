package com.mockuai.distributioncenter.core.service.action.plan;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.distributioncenter.common.domain.qto.ItemSkuDistPlanQTO;
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
 * Created by lizg on 2016/10/27.
 */
@Service
public class GetDistPlanByItemSkuIdAction implements Action{

    private static final Logger log = LoggerFactory.getLogger(GetDistPlanByItemSkuIdAction.class);

    @Autowired
    private ItemSkuDistPlanManager itemSkuDistPlanManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {

        Request request = context.getRequest();
        ItemSkuDistPlanQTO itemSkuDistPlanQTO =  (ItemSkuDistPlanQTO)request.getParam("itemSkuDistPlanQTO");

        if (itemSkuDistPlanQTO == null) {
            log.error("itemSkuDistPlanQTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        List<ItemSkuDistPlanDTO>  itemSkuDistPlanDTOs= itemSkuDistPlanManager.getDistByItemSkuId(itemSkuDistPlanQTO);

        return new DistributionResponse(itemSkuDistPlanDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_DIST_PLAN_BY_ITEM_SKU_ID.getActionName();
    }
}
