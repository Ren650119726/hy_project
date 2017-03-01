package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
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
public class DeleteCollectionShopAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(DeleteCollectionShopAction.class);

    @Autowired
    private CollectionShopManager collectionShopManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Long id = (Long) request.getParam("id");

        if (id == null) {
            log.error("id is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "userId is null");
        }

        collectionShopManager.delete(id);
        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_COLLECTION_SHOP.getActionName();
    }
}
