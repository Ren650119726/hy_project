package com.mockuai.distributioncenter.core.manager.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.RegionManager;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.usercenter.common.constant.ResponseCode;

/**
 * Created by duke on 15/12/17.
 */
@Component
public class RegionManagerImpl implements RegionManager {
    private static final Logger log = LoggerFactory.getLogger(RegionManagerImpl.class);

    @Autowired
    private RegionClient regionClient;

    @Override
    public List<RegionDTO> queryRegion(RegionQTO regionQTO, String appKey) throws DistributionException {
        Response<List<RegionDTO>> response = regionClient.queryRegion(regionQTO, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            log.error("query region error, condition: {}", JsonUtil.toJson(regionQTO));
            throw new DistributionException(ResponseCode.SYS_E_SERVICE_EXCEPTION.getValue(), "区域查询异常");
        }
    }
}
