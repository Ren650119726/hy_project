package com.mockuai.distributioncenter.core.service.action.distribution.distribute;

import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.Finder;

/**
 * Created by duke on 16/5/16.
 */
public interface Distribute {
    /**
     * 分拥
     *
     * @param seller 分销的起点
     * @param orderDTO 分销的订单
     * */
    void distribution(final SellerDTO seller, final DistributionOrderDTO orderDTO) throws DistributionException;
}
