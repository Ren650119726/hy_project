package com.mockuai.shopcenter.core.service.action.shop;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.*;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.*;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.*;
import com.mockuai.shopcenter.domain.qto.BannerQTO;
import com.mockuai.shopcenter.domain.qto.ShopCouponQTO;
import com.mockuai.shopcenter.domain.qto.ShopItemQTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ziqi.
 */
@Service
public class SetShopDetailsAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(UpdateShopAction.class);

    @Resource
    private ShopImageManager shopImageManager;

    @Resource
    private ShopManager shopManager;

    @Resource
    private ShopItemManager shopItemManager;

    @Resource
    private BannerManager bannerManager;

    @Resource
    private ShopCouponManager shopCouponManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        ShopResponse response = null;

        String bizCode = (String) context.get("bizCode");
        ShopRequest request = context.getRequest();
        ShopDTO shopDTO = (ShopDTO) request.getParam("shopDTO");

        if (shopDTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopDTO is null");
        }

        ShopDTO dbShopDTO = shopManager.getShop(shopDTO.getSellerId());

        String shopIconUrl = shopDTO.getShopIconUrl();
        String shopBannerUrl = shopDTO.getShopBannerImageUrl();

        // 判断图片是否需要更新;
        if (StringUtils.isNotEmpty(shopIconUrl)) {
            ShopImageDTO iconShopImageDTO = new ShopImageDTO();
            iconShopImageDTO.setImageUrl(shopIconUrl);
            iconShopImageDTO.setSellerId(shopDTO.getSellerId());
            iconShopImageDTO.setShopId(dbShopDTO.getId());
            iconShopImageDTO.setImageType(ShopImageEnum.SHOP_ICON_IMG.getType());
            Long iconId = shopImageManager.addShopImage(iconShopImageDTO);
            shopDTO.setShopIconId(iconId);
        }

        if (StringUtils.isNotEmpty(shopBannerUrl)) {
            ShopImageDTO bannerShopImageDTO = new ShopImageDTO();
            bannerShopImageDTO.setImageUrl(shopBannerUrl);
            bannerShopImageDTO.setSellerId(shopDTO.getSellerId());
            bannerShopImageDTO.setShopId(dbShopDTO.getId());
            bannerShopImageDTO.setImageType(ShopImageEnum.SHOP_BANNER_IMG.getType());
            Long bannerId = shopImageManager.addShopImage(bannerShopImageDTO);
            shopDTO.setShopBannerImageId(bannerId);
        }

//
//        //设置商品
//        ShopItemQTO shopItemQTO = new ShopItemQTO();
//        shopItemQTO.setSellerId(shopDTO.getSellerId());
//        shopItemQTO.setShopId(shopDTO.getId());
//        shopItemQTO.setBizCode(bizCode);
//
//        List<ShopItemDTO> shopItemDTOList = shopItemManager.queryShopItem(shopItemQTO);
//
//        if (shopItemDTOList != null && shopItemDTOList.size() > 0) {
//
//            List<Long> shopItemIdList = Lists.newArrayListWithCapacity(shopItemDTOList.size());
//            for (ShopItemDTO shopItemDTO : shopItemDTOList) {
//                shopItemIdList.add(shopItemDTO.getId());
//            }
//
//            shopItemManager.batchDeleteShopItem(shopItemIdList, shopDTO.getId(), bizCode);
//        }
//
//        if (shopDTO.getItemIdList() != null) {
//            for (Long itemId : shopDTO.getItemIdList()) {
//                ShopItemDTO shopItemDTO = new ShopItemDTO();
//                shopItemDTO.setSellerId(shopDTO.getSellerId());
//                shopItemDTO.setBizCode(bizCode);
//                shopItemDTO.setShopId(shopDTO.getId());
//                shopItemDTO.setItemId(itemId);
//
//                shopItemManager.addShopItem(shopItemDTO);
//            }
//        }


        //店铺首页轮播图片
        BannerQTO bannerQTO = new BannerQTO();
        bannerQTO.setParentId(shopDTO.getId());
        bannerQTO.setParentType(ElementEnum.MAIN_PAGE.getType());
        bannerQTO.setBannerType(BannerEnum.CAROUSEL_BANNER.getType());
        bannerQTO.setBizCode(bizCode);

        List<BannerDTO> bannerDTOList = bannerManager.queryBanner(bannerQTO);


        if (bannerDTOList != null && bannerDTOList.size() > 0) {

            List<Long> bannerIdList = Lists.newArrayListWithCapacity(bannerDTOList.size());

            for (BannerDTO bannerDTO : bannerDTOList) {
                bannerIdList.add(bannerDTO.getId());
            }

            Long result = bannerManager.batchDeleteBanner(bannerIdList, shopDTO.getId(), bizCode);

        }


        if (shopDTO.getBannerDTOList() != null) {

            for (BannerDTO bannerDTO : shopDTO.getBannerDTOList()) {

                bannerDTO.setSellerId(shopDTO.getSellerId());
                bannerDTO.setParentId(shopDTO.getId());
                bannerDTO.setParentType(ElementEnum.MAIN_PAGE.getType());
                bannerDTO.setBizCode(bizCode);
                bannerDTO.setShopId(shopDTO.getId());
                bannerManager.addBanner(bannerDTO);
            }
        }


        ShopCouponQTO shopCouponQTO = new ShopCouponQTO();
        shopCouponQTO.setSellerId(shopDTO.getSellerId());
        shopCouponQTO.setBizCode(bizCode);

        List<ShopCouponDTO> shopCouponDTOList = shopCouponManager.queryShopCoupon(shopCouponQTO);


        if (shopCouponDTOList != null && shopCouponDTOList.size() > 0) {

            List<Long> shopCouponIdList = Lists.newArrayListWithCapacity(shopCouponDTOList.size());
            for (ShopCouponDTO shopCouponDTO : shopCouponDTOList) {
                shopCouponIdList.add(shopCouponDTO.getId());
            }

            shopCouponManager.batchDeleteShopCoupon(shopCouponIdList, shopDTO.getId(), bizCode);
        }


        if (shopDTO.getCouponIdList() != null) {
            for (Long couponId : shopDTO.getCouponIdList()) {

                ShopCouponDTO shopCouponDTO = new ShopCouponDTO();
                shopCouponDTO.setSellerId(shopDTO.getSellerId());
                shopCouponDTO.setShopId(shopDTO.getId());
                shopCouponDTO.setParentType(ElementEnum.MAIN_PAGE.getType());
                shopCouponDTO.setParentId(shopDTO.getId());
                shopCouponDTO.setCouponLocation(0);
                shopCouponDTO.setBizCode(bizCode);
                shopCouponDTO.setCouponId(couponId);

                shopCouponManager.addShopCoupon(shopCouponDTO);
            }
        }


        Boolean result = shopManager.updateShop(shopDTO);

        response = ResponseUtil.getSuccessResponse(result);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.SET_SHOP_DETAILS.getActionName();
    }
}
