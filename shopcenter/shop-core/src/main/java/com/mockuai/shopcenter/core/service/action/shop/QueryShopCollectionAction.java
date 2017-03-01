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
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用户查询收藏店铺
 * Created by luliang on 15/8/7.
 */
@Service
public class QueryShopCollectionAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddShopCollectionAction.class);

    @Resource
    private ShopCollectionManager shopCollectionManager;

    @Resource
    private ShopManager shopManager;

    @Resource
    private ShopImageManager shopImageManager;


    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        ShopCollectionQTO shopCollectionQTO = (ShopCollectionQTO) shopRequest.getParam("shopCollectionQTO");
        String bizCode = (String) context.get("bizCode");
        if (shopCollectionQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopCollectionQTO param is null");
        }

        try {
            shopCollectionQTO.setBizCode(bizCode);
            List<ShopCollectionDTO> shopCollectionDTOList = shopCollectionManager.queryShopCollection(shopCollectionQTO);
            List<Long> idList = new ArrayList<Long>();
            for (ShopCollectionDTO shopCollectionDTO : shopCollectionDTOList) {
                idList.add(shopCollectionDTO.getShopId());
            }
            List<ShopDTO> shopDTOs = new ArrayList<ShopDTO>();
            ShopQTO shopQTO = new ShopQTO();
            // 走查询店铺的逻辑;
            if (!CollectionUtils.isEmpty(idList)) {
                shopQTO.setIdList(idList);
                shopDTOs = shopManager.queryShop(shopQTO);
                for (ShopDTO shopDTO : shopDTOs) {
                    ShopImageDTO shopImageDTO = new ShopImageDTO();
                    // img
                    if (shopDTO.getShopIconId() != null) {
                        shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopIconId(), shopDTO.getSellerId());
                        shopDTO.setShopIconUrl(shopImageDTO.getImageUrl());
                    }

                    if (shopDTO.getShopBannerImageId() != null) {
                        shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopBannerImageId(), shopDTO.getSellerId());
                        shopDTO.setShopBannerImageUrl(shopImageDTO.getImageUrl());
                    }
                    shopDTO.setIsCollected(true);
                }
            }
            shopResponse = ResponseUtil.getSuccessResponse(shopDTOs, shopCollectionQTO.getTotalCount());
        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }catch (Exception e){
            shopResponse = ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_COLLECTION_SHOP.getActionName();
    }
}
