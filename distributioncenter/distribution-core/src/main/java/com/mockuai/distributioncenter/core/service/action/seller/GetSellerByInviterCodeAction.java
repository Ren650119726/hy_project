package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
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
public class GetSellerByInviterCodeAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetSellerByInviterCodeAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        String inviterCode = (String) request.getParam("inviterCode");

        if (inviterCode == null) {
            log.error("inviterCode is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "inviterCode is null");
        }

        SellerDTO sellerDTO = sellerManager.getByInviterCode(inviterCode);

        if (sellerDTO == null) {
            log.error("seller not exists with inviterCode: {}", inviterCode);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "inviter_code illegal");
        }

        return new DistributionResponse(sellerDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SELLER_BY_INVITER_CODE.getActionName();
    }
}
