package com.mockuai.marketingcenter.core.manager;


import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthQTO;

import java.util.List;

public interface VirtualWealthManager {

    /**
     * 发放虚拟财富
     *
     * @param creatorId
     * @param wealthType
     * @param sourceType
     * @param grantAmount
     * @param receiverId
     * @param orderId
     * @param appKey
     * @return
     * @throws MarketingException
     */
    Boolean grantVirtualWealth(Long creatorId, Integer wealthType, Integer sourceType,
                               Long grantAmount, Long receiverId, Long orderId, String appKey) throws MarketingException;

    /**
     * 查询虚拟财富
     *
     * @param virtualWealthQTO
     * @param appKey
     * @return
     */
    List<VirtualWealthDTO> queryVirtualWealth(VirtualWealthQTO virtualWealthQTO, String appKey) throws MarketingException;
}