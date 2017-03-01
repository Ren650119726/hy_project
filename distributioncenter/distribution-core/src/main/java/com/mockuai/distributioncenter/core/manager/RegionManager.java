package com.mockuai.distributioncenter.core.manager;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by duke on 15/12/17.
 */
public interface RegionManager {
    /**
     * 条件查询区域
     * @param regionQTO
     * @param appKey
     * */
    List<RegionDTO> queryRegion(RegionQTO regionQTO, String appKey) throws DistributionException;
}
