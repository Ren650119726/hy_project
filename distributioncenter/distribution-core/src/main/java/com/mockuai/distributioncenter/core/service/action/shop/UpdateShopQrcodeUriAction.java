package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 冠生 on 16/3/10.
 * 更新店铺二维码
 */
@Service
public class UpdateShopQrcodeUriAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(UpdateShopQrcodeUriAction.class);

    @Autowired
    private ShopManager shopManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        DistShopQTO shop = (DistShopQTO) request.getParam("distShopDTO");

        if (shop == null) {
            log.error("shopDTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        try {
            shopManager.updateQrcodeUrl(shop);
            return new DistributionResponse(true);
        }catch (DistributionException e){
            return new DistributionResponse(e.getCode(),e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SHOP_QRCODE_URI.getActionName();
    }
}
