package com.mockuai.distributioncenter.core.service.action.distribution.calculator;

import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * Created by duke on 16/5/16.
 */
public interface Calculator {
    /**
     * 分拥计算逻辑
     *
     * @param seller 参与分拥的人
     * @param orderDTO 参与分拥的订单
     * */
     boolean calculate(final SellerDTO seller, final DistributionOrderDTO orderDTO) throws DistributionException;
}
