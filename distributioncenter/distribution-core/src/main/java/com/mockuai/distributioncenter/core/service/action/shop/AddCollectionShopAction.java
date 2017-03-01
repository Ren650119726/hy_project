package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.CollectionShopDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.CollectionShopManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 16/5/21.
 */
@Service
public class AddCollectionShopAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddCollectionShopAction.class);

    @Autowired
    private CollectionShopManager collectionShopManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        CollectionShopDTO collectionShopDTO = (CollectionShopDTO) request.getParam("collectionShopDTO");

        if (collectionShopDTO == null) {
            log.error("collectionShopDTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "collectionShopDTO is null");
        }

        Long id = collectionShopManager.add(collectionShopDTO);
        collectionShopDTO.setId(id);
        return new DistributionResponse(collectionShopDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_COLLECTION_SHOP.getActionName();
    }
}
