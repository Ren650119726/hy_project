package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 冠生 on 16/3/9.
 */
@Service
public class GetShopQrCodeAction implements Action{
    private static final Logger log = LoggerFactory.getLogger(GetShopQrCodeAction.class);

    @Autowired
    private ShopManager shopManager;

    @Autowired
    private SellerManager sellerManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        Long userId = (Long) request.getParam("userId");

        if(userId == null){
            log.error("user id is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL,"user id is null");
        }

        SellerDTO sellerDTO = sellerManager.getByUserId(userId);

        if (sellerDTO == null) {
            log.error("user : {} is not a seller", userId);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "user is not a seller");
        }

        DistShopDTO shopDTO = shopManager.getBySellerId(sellerDTO.getId());
        shopDTO.setSellerDTO(sellerDTO);

        return null;
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SHOP_QRCODE.getActionName();
    }
}
