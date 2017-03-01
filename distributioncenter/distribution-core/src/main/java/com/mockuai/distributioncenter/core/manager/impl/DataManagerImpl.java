package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.datacenter.client.DataClient;
import com.mockuai.datacenter.common.api.Response;
import com.mockuai.datacenter.common.domain.dto.DataDTO;
import com.mockuai.datacenter.common.domain.dto.DataResultDTO;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 16/6/6.
 */
@Service
public class DataManagerImpl implements DataManager {
    private static final Logger log = LoggerFactory.getLogger(DataManagerImpl.class);

    @Autowired
    private DataClient dataClient;

    @Override
    public Map<DataResultDTO, List<DataDTO>> getDataByTimeRange(Long sellerId, Integer deviceType, String key, Integer timeType, Date startTime, Date endTime, String appKey) throws DistributionException {
        Response<Map<DataResultDTO, List<DataDTO>>> response = dataClient.getDataByTimeRange(sellerId, deviceType, key, timeType, startTime, endTime, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            log.error("get Data by Time range error, errMsg: {}", response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public Map<DataResultDTO, DataDTO> getDataBySum(Long sellerId, Integer deviceType, String key, Integer timeType, Date startTime, Date endTime, Integer topRange, String appKey) throws DistributionException {
        Response<Map<DataResultDTO, DataDTO>> response = dataClient.getDataBySum(sellerId, deviceType, key, timeType, startTime, endTime, topRange, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            log.error("get data by sum error, errMsg: {}", response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        }
    }
}
