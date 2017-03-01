package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopImageManager;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopImageDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/27.
 */
@Service
public class QueryShopAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(QueryShopAction.class);

    @Resource
    private ShopManager shopManager;
    @Resource
    private ShopImageManager shopImageManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        ShopQTO shopQTO = (ShopQTO) shopRequest.getParam("shopQTO");
        if(shopQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopQTO is null");
        }
        shopQTO.setBizCode(bizCode);

        List<ShopDTO> shopDTOList;

        try {
            shopDTOList = shopManager.queryShop(shopQTO);
            for(ShopDTO shopDTO: shopDTOList) {
                ShopImageDTO shopImageDTO = new ShopImageDTO();
                // img
                if(shopDTO.getShopIconId() != null) {
                    shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopIconId(), shopDTO.getSellerId());
                    shopDTO.setShopIconUrl(shopImageDTO.getImageUrl());
                }

                if(shopDTO.getShopBannerImageId() != null) {
                    shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopBannerImageId(), shopDTO.getSellerId());
                    shopDTO.setShopBannerImageUrl(shopImageDTO.getImageUrl());
                }
            }

        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }catch (Exception e){
            shopResponse = ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
        shopResponse = ResponseUtil.getSuccessResponse(shopDTOList, shopQTO.getTotalCount());
        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SHOP.getActionName();
    }
}
