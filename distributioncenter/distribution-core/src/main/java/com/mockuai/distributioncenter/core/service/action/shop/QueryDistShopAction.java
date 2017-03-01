package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 16/5/23.
 */
@Service
public class QueryDistShopAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryDistShopAction.class);

    @Autowired
    private ShopManager shopManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        DistShopQTO distShopQTO = (DistShopQTO) request.getParam("distShopQTO");

        if (distShopQTO == null) {
            log.error("distShopQTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "distShopQTO is null");
        }

        List<DistShopDTO> distShopDTOs = shopManager.query(distShopQTO);


        return new DistributionResponse(distShopDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SHOP.getActionName();
    }
}
