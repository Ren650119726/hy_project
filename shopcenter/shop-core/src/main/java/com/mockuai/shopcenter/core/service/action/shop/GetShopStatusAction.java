package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/28.
 */
@Service
public class GetShopStatusAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetShopAction.class);

    @Resource
    private ShopManager shopManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        Long sellerId = (Long) shopRequest.getParam("sellerId");
        if(sellerId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is null");
        }

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setSellerId(sellerId);

        try {

            shopDTO = shopManager.getShop(sellerId);
            shopResponse = ResponseUtil.getSuccessResponse(shopDTO.getShopStatus());
            return shopResponse;
        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SHOP_STATUS.getActionName();
    }
}
