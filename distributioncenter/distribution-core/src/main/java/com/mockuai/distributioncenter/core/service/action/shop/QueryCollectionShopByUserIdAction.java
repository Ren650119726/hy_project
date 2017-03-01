package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.CollectionShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.CollectionShopManager;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 16/5/21.
 */
@Service
public class QueryCollectionShopByUserIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryCollectionShopByUserIdAction.class);

    @Autowired
    private CollectionShopManager collectionShopManager;

    @Autowired
    private ShopManager shopManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Long userId = (Long) request.getParam("userId");

        if (userId == null) {
            log.error("userId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "userId is null");
        }

        List<CollectionShopDTO> collectionShopDTOs = collectionShopManager.queryByUserId(userId);
        DistShopQTO shopQTO = new DistShopQTO();
        List<Long> ids = new ArrayList<Long>();
        for (CollectionShopDTO shopDTO : collectionShopDTOs) {
            ids.add(shopDTO.getId());
        }
        shopQTO.setIds(ids);
        List<DistShopDTO> distShopDTOs = shopManager.query(shopQTO);
        Map<Long, DistShopDTO> map = new HashMap<Long, DistShopDTO>();
        for (DistShopDTO distShopDTO : distShopDTOs) {
            map.put(distShopDTO.getId(), distShopDTO);
        }
        for (CollectionShopDTO collectionShopDTO : collectionShopDTOs) {
            DistShopDTO distShopDTO = map.get(collectionShopDTO.getId());
            if (distShopDTO != null) {
                collectionShopDTO.setShopName(distShopDTO.getShopName());
                collectionShopDTO.setShopDesc(distShopDTO.getShopDesc());
            }
        }
        return new DistributionResponse(collectionShopDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_COLLECTION_SHOP_BY_USER_ID.getActionName();
    }
}
