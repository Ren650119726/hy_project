package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ImageManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lotmac on 16/3/10.
 */
@Service
public class UpdateShopAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(UpdateShopAction.class);

    private static final String shopUrl = "http://m.haiyn.com/index.html?distributor_id=%s";
    private static final String inviterUrl = "http://m.haiyn.com/merchant-login.html?inviter_seller_id=%s&real_name=%s";

    @Autowired
    private ShopManager shopManager;

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private ImageManager imageManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        DistShopDTO distShopDTO = (DistShopDTO) request.getParam("shopDTO");

        String appKey = (String) context.get("appKey");

        if (distShopDTO == null) {
            log.error("shopDTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        shopManager.update(distShopDTO);

        if (distShopDTO.getSellerDTO() != null) {
            DistShopDTO dto = shopManager.get(distShopDTO.getId());
            SellerDTO sellerDTO = distShopDTO.getSellerDTO();
            sellerDTO.setId(dto.getSellerId());
            sellerManager.update(sellerDTO);

            // 更新二维码
            sellerDTO = sellerManager.get(dto.getSellerId());
            imageManager.addShopImage(sellerDTO.getUserId(), String.format(shopUrl, sellerDTO.getId()), appKey);
            // 生成卖家推荐二维码
            try {
                imageManager.addRecommendImage(sellerDTO.getUserId(), String.format(inviterUrl, sellerDTO.getId(), URLEncoder.encode(sellerDTO.getRealName(), "UTF-8")), appKey);
            } catch (UnsupportedEncodingException e) {
                log.error("unsupported encode, err: {}", e.getMessage());
            }

        }
        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SHOP.getActionName();
    }
}
