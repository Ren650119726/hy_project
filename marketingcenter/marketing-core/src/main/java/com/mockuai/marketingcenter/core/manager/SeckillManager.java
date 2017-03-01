package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;

/**
 * Created by edgar.zr on 12/15/15.
 */
public interface SeckillManager {

    /**
     * 由 秒杀平台 验证结算信息
     *
     * @param skuId
     * @param userId
     * @param sellerId
     * @param appKey
     * @return
     * @throws MarketingException
     */
    SeckillDTO validateForSettlement(Long skuId, Long userId, Long sellerId, String appKey) throws MarketingException;
}