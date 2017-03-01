package com.mockuai.suppliercenter.core.manager.impl;

import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.DeliveryManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/4/15.
 */

@Service
public class DeliveryManagerImpl implements DeliveryManager {

    @Resource
    private RegionClient regionClient;

    @Override
    public List<RegionDTO> queryRegion(List<String> regionCodes, String appKey) throws SupplierException {
        RegionQTO regionQTO = new RegionQTO();
        regionQTO.setRegionCodes(regionCodes);
        try {
            Response<List<RegionDTO>> response = regionClient.queryRegion(regionQTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            } else {
                //TODO error handle
            }
        } catch (Exception e) {
            //TODO error handle
        }
        return Collections.EMPTY_LIST;
    }
}
