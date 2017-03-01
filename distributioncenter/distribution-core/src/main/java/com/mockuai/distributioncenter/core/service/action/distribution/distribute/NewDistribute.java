package com.mockuai.distributioncenter.core.service.action.distribution.distribute;

import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * Created by duke on 16/5/16.
 */
public interface NewDistribute {
    /**
     * 分拥
     *
     * @param orderDTO 分销的订单
     * */
    void distribution(final DistributionItemDTO itemDTO) throws DistributionException;
}
