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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/27.
 */
@Service
public class AddShopAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(AddShopAction.class);

    @Resource
    private ShopManager shopManager;

    @Resource
    private ShopImageManager shopImageManager;

    @Override
    public ShopResponse doTransaction(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        ShopDTO shopDTO = (ShopDTO) shopRequest.getParam("shopDTO");
        String bizCode = (String) context.get("bizCode");
        if(shopDTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shop param is null");
        }
        shopDTO.setBizCode(bizCode);//填充bizCode信息

        // 判断店铺名是否已经存在,seller是否开过店;
        boolean isExists = shopManager.isExistsShop(shopDTO);
        if(isExists) {
            // 已经存在;
            return ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "shop is already is exists.");
        }
        // 先存图片;
        String shopIconUrl = shopDTO.getShopIconUrl();
        String shopBannerUrl = shopDTO.getShopBannerImageUrl();

        ShopImageDTO iconShopImageDTO = new ShopImageDTO();
        iconShopImageDTO.setImageUrl(shopIconUrl);
        iconShopImageDTO.setSellerId(shopDTO.getSellerId());
        iconShopImageDTO.setShopId(0L);
        iconShopImageDTO.setBizCode(bizCode);
        iconShopImageDTO.setImageType(ShopImageEnum.SHOP_ICON_IMG.getType());
        Long iconId = shopImageManager.addShopImage(iconShopImageDTO);
        iconShopImageDTO.setId(iconId);

        ShopImageDTO bannerShopImageDTO = new ShopImageDTO();
        bannerShopImageDTO.setImageUrl(shopBannerUrl);
        bannerShopImageDTO.setSellerId(shopDTO.getSellerId());
        bannerShopImageDTO.setShopId(0L);
        bannerShopImageDTO.setBizCode(bizCode);
        bannerShopImageDTO.setImageType(ShopImageEnum.SHOP_BANNER_IMG.getType());
        Long bannerId = shopImageManager.addShopImage(bannerShopImageDTO);
        bannerShopImageDTO.setId(bannerId);

        shopDTO.setShopIconId(iconId);
        shopDTO.setShopBannerImageId(bannerId);
        // 存店铺信息;
        shopDTO = shopManager.addShop(shopDTO);

        iconShopImageDTO.setShopId(shopDTO.getId());
        shopImageManager.updateShopImage(iconShopImageDTO);
        bannerShopImageDTO.setShopId(shopDTO.getId());
        shopImageManager.updateShopImage(bannerShopImageDTO);

        shopResponse = ResponseUtil.getSuccessResponse(shopDTO);
        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_SHOP.getActionName();
    }
}
