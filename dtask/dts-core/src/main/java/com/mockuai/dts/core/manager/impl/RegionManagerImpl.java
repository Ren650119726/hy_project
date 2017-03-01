package com.mockuai.dts.core.manager.impl;

import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.RegionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
public class RegionManagerImpl implements RegionManager {

    private static final Logger LOG = LoggerFactory.getLogger(RegionManagerImpl.class);

    @Resource
    private RegionClient regionClient;

    public RegionDTO getRegionByName(String name, String appKey) throws DtsException {
        RegionQTO regionQTO = new RegionQTO();
        regionQTO.setName(name);
        Response<List<RegionDTO>> response = regionClient.queryRegion(regionQTO,appKey);
        if(response.isSuccess() && response.getModule().size() == 1){
            return response.getModule().get(0);
        }else{
            throw new DtsException(ResponseCode.SYS_E_DB_QUERY.getCode(),"查询不到对应的地区列表");
        }
    }

}
