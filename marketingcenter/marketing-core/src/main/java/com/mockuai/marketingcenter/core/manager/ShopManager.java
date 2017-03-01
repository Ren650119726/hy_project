package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.ShopDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by edgar.zr on 1/13/16.
 */
public interface ShopManager {

    /**
     * 获取店铺信息
     *
     * @param sellerId
     * @param appKey
     * @return
     */
    ShopDTO getShop(Long sellerId, String appKey) throws MarketingException;
}