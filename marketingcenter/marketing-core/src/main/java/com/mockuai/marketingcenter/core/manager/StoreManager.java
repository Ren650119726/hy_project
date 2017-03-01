package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.StoreDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by edgar.zr on 1/14/16.
 */
public interface StoreManager {

    /**
     * @param consigneeId
     * @param sellerId
     * @param appKey
     * @return
     * @throws MarketingException
     */
    StoreDTO getStore(Long sellerId, Long userId, Long consigneeId, String appKey) throws MarketingException;
}