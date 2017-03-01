package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 16/5/18.
 */
@Service
public class GetSellerByUserIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetSellerByUserIdAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        Long userId = request.getLong("userId");

        if (userId == null) {
            log.error("userId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        SellerDTO sellerDTO = sellerManager.getByUserId(userId);
        if (sellerDTO == null) {
            log.error("user is not a seller, userId: {}", userId);
            sellerDTO = new SellerDTO();
           // throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "user id is not a seller");
        }

        return new DistributionResponse(sellerDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SELLER_BY_USER_ID.getActionName();
    }
}
