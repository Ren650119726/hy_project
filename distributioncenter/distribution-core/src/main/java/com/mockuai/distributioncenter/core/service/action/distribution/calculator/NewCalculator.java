package com.mockuai.distributioncenter.core.service.action.distribution.calculator;

import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;


/**
 * Created by duke on 16/5/16.
 */
public interface NewCalculator {
    /**
     * 分拥计算逻辑
     *
     * @param seller 参与分拥的人
     * @param orderDTO 参与分拥的订单
     * */
     boolean calculate(final Long userID, final DistributionItemDTO itemDTO,GainsSetDTO gainsSetDTO) throws DistributionException;
}
