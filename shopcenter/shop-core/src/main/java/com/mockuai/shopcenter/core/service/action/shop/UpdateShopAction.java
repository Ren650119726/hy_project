package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.constant.ShopImageEnum;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopImageManager;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopImageDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 变更店铺信息;
 * Created by luliang on 15/7/28.
 */
@Service
public class UpdateShopAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(UpdateShopAction.class);

    @Resource
    private ShopImageManager shopImageManager;

    @Resource
    private ShopManager shopManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        ShopResponse response = null;
        ShopRequest request = context.getRequest();
        ShopDTO shopDTO = (ShopDTO)request.getParam("shopDTO");
        if(shopDTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopDTO is null");
        }

        ShopDTO dbShopDTO = shopManager.getShop(shopDTO.getSellerId());

        String shopIconUrl = shopDTO.getShopIconUrl();
        String shopBannerUrl = shopDTO.getShopBannerImageUrl();
        // 判断图片是否需要更新;
        if(StringUtils.isNotEmpty(shopIconUrl)) {
            ShopImageDTO iconShopImageDTO = new ShopImageDTO();
            iconShopImageDTO.setImageUrl(shopIconUrl);
            iconShopImageDTO.setSellerId(shopDTO.getSellerId());
            iconShopImageDTO.setShopId(dbShopDTO.getId());
            iconShopImageDTO.setImageType(ShopImageEnum.SHOP_ICON_IMG.getType());
            Long iconId = shopImageManager.addShopImage(iconShopImageDTO);
            shopDTO.setShopIconId(iconId);
        }

        if(StringUtils.isNotEmpty(shopBannerUrl)) {
            ShopImageDTO bannerShopImageDTO = new ShopImageDTO();
            bannerShopImageDTO.setImageUrl(shopBannerUrl);
            bannerShopImageDTO.setSellerId(shopDTO.getSellerId());
            bannerShopImageDTO.setShopId(dbShopDTO.getId());
            bannerShopImageDTO.setImageType(ShopImageEnum.SHOP_BANNER_IMG.getType());
            Long bannerId = shopImageManager.addShopImage(bannerShopImageDTO);
            shopDTO.setShopBannerImageId(bannerId);
        }
        Boolean result = shopManager.updateShop(shopDTO);
        response = ResponseUtil.getSuccessResponse(result);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SHOP.getActionName();
    }
}
