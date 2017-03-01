package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 16/6/7.
 */
@Service
public class UpdateSellerRealNameByUserId extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateSellerRealNameByUserId.class);

    @Autowired
    private SellerManager sellerManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Long userId = (Long) request.getParam("userId");
        String realName = (String) request.getParam("realName");

        if (userId == null) {
            log.error("user id is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "user id is null");
        }

        if (realName == null) {
            log.error("real name is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "real name is null");
        }

        SellerDTO sellerDTO = sellerManager.getByUserId(userId);
        if (sellerDTO == null) {
            log.error("seller not exists, userId: {}", userId);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "seller not exists");
        }
        sellerDTO.setRealName(realName);
        sellerManager.updateByUserId(userId, sellerDTO);

        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SELLER_REAL_NAME_BY_USER_ID.getActionName();
    }
}
