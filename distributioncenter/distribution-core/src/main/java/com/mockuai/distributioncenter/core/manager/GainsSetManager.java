package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * Created by lizg on 2016/8/29.
 */
public interface GainsSetManager {

    /**
     * 添加收益设置
     * @param gainsSetDTO
     * @return
     * @throws DistributionException
     */
    Long add(GainsSetDTO gainsSetDTO) throws DistributionException;

    GainsSetDTO get() throws DistributionException;

    Integer update(GainsSetDTO gainsSetDTO) throws DistributionException;
}
