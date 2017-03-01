package com.mockuai.distributioncenter.core.manager;

import com.mockuai.datacenter.common.domain.dto.DataDTO;
import com.mockuai.datacenter.common.domain.dto.DataResultDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 16/6/3.
 */
public interface DataManager {
    Map<DataResultDTO, List<DataDTO>> getDataByTimeRange(Long sellerId, Integer deviceType, String key, Integer timeType, Date startTime, Date endTime, String appKey) throws DistributionException;
    Map<DataResultDTO, DataDTO> getDataBySum(Long sellerId, Integer deviceType, String key, Integer timeType, Date startTime, Date endTime, Integer topRange, String appKey) throws DistributionException;
}
