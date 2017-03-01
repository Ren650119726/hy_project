package com.mockuai.distributioncenter.core.service.action.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.manager.UserManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;

/**
 * Created by duke on 16/5/26.
 */
@Service
public class GetShopBySellerIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetShopBySellerIdAction.class);

    @Autowired
    private ShopManager shopManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private SellerManager sellerManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
//        Request request = context.getRequest();
//        Long sellerId = (Long) request.getParam("sellerId");
//        String appKey = (String) context.get("appKey");

//        if (sellerId == null) {
//            log.error("sellerId is null");
//            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "sellerId is null");
//        }
//
//        DistShopDTO distShopDTO = shopManager.getBySellerId(sellerId);
//
//        if (distShopDTO == null) {
//            log.error("shop not exists, sellerId: {}", sellerId);
//            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "shop not exists");
//        }
//
//        SellerDTO sellerDTO = sellerManager.get(distShopDTO.getSellerId());
//
//        if (sellerDTO == null) {
//            log.error("seller not exists, sellerId: {}", sellerId);
//            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "seller not exists");
//        }
//
//        UserDTO user = userManager.getUserByUserId(sellerDTO.getUserId(), appKey);
//        if (user != null) {
//            sellerDTO.setHeadImgUrl(user.getImgUrl());
//        } else {
//            log.error("user not exists, userId: {}", sellerDTO.getUserId());
//        }
//        distShopDTO.setSellerDTO(sellerDTO);
        DistShopDTO distShopDTO = new DistShopDTO();
        return new DistributionResponse(distShopDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SHOP_BY_SELLER_ID.getActionName();
    }
}
