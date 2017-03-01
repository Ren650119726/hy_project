package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopCollectionManager;
import com.mockuai.shopcenter.core.manager.ShopImageManager;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopCollectionDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopImageDTO;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ziqi.
 */
@Service
public class GetShopAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetShopAction.class);

    @Resource
    private ShopManager shopManager;
    @Resource
    private ShopImageManager shopImageManager;
    @Resource
    private ShopCollectionManager shopCollectionManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        Long sellerId = (Long)shopRequest.getParam("sellerId");
        Long userId = (Long)shopRequest.getParam("userId");

        String bizCode = (String)context.get("bizCode");

        if(sellerId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is null");
        }

        try {

            ShopDTO shopDTO = shopManager.getShop(sellerId);
            ShopImageDTO shopImageDTO = new ShopImageDTO();
            // img
            if(shopDTO.getShopIconId() != null){
                shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopIconId(), shopDTO.getSellerId());
                shopDTO.setShopIconUrl(shopImageDTO.getImageUrl());
            }

            if(shopDTO.getShopBannerImageId() != null){
                shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopBannerImageId(), shopDTO.getSellerId());
                shopDTO.setShopBannerImageUrl(shopImageDTO.getImageUrl());
            }


            // 判断收藏;
            if(userId != null) {
                ShopCollectionDTO shopCollectionDTO = shopCollectionManager.getShopCollection(shopDTO.getSellerId(), userId);
                if(shopCollectionDTO != null) {
                    shopDTO.setIsCollected(true);
                } else {
                    shopDTO.setIsCollected(false);
                }
            }

            ShopCollectionQTO query = new ShopCollectionQTO();
            query.setSellerId(sellerId);
            query.setBizCode(bizCode);
            Integer count = shopCollectionManager.countShopCollection(query);

            shopDTO.setCollectionNum(count);

           return ResponseUtil.getSuccessResponse(shopDTO);

            // group item;D
//            ShopItemGroupQTO shopItemGroupQTO = new ShopItemGroupQTO();
//            shopItemGroupQTO.setSellerId(shopDTO.getSellerId());
//            shopItemGroupQTO.setShopId(shopDTO.getId());
//            List<ShopItemGroupDTO> shopItemGroupDTOList = shopItemGroupManager.queryShopItemGroup(shopItemGroupQTO);
//            shopDTO.setShopItemGroupDTOList(shopItemGroupDTOList);
        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SHOP.getActionName();
    }
}
