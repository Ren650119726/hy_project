package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.VirtualWealthManager;
import com.mockuai.virtualwealthcenter.client.VirtualWealthClient;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.TotalWealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 16/5/19.
 */
@Service
public class VirtualWealthManagerImpl implements VirtualWealthManager {
    private static final Logger log = LoggerFactory.getLogger(VirtualWealthManagerImpl.class);

    @Autowired
    private VirtualWealthClient virtualWealthClient;

    @Override
    public TotalWealthAccountDTO queryVirtualWealthCombine(Long userId, String appKey) throws DistributionException {
        Response<TotalWealthAccountDTO> response = virtualWealthClient.queryTotalVirtualWealthCombine(userId, appKey);
        if (!response.isSuccess()) {
            log.error("query virtual wealth combine error, msg: {}", response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        }
        return response.getModule();
    }

    @Override
    public List<WealthAccountDTO> queryTotalVirtualWealth(List<Long> userIds, Integer wealthType, String appKey)
            throws DistributionException {
        Response<List<WealthAccountDTO>> response = virtualWealthClient.listTotalVirtualWealth(userIds, wealthType, appKey);
        if (!response.isSuccess()) {
            log.error("query total virtual wealth error, msg: {}", response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        }
        return response.getModule();
    }

    @Override
    public Boolean distributorGrant(GrantedWealthDTO grantedWealthDTO, String appKey) throws DistributionException {
        Response<Boolean> response = virtualWealthClient.distributorGrant(grantedWealthDTO, appKey);
        if (!response.isSuccess()) {
            log.error("distributor grant error, msg: {}", response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION);
        }
        return response.getModule();
    }

    @Override
    public Boolean updateStatusOfVirtualWealthDistributorGranted(Long orderId, Long itemSkuId, Integer sourceType, Integer status, String appKey) throws DistributionException {
        Response<Boolean> response = virtualWealthClient.updateStatusOfVirtualWealthDistributorGranted(orderId, itemSkuId, sourceType, status, appKey);
        if (!response.isSuccess()) {
            log.error("updateStatusOfVirtualWealthDistributorGranted error, msg: {}", response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION);
        }
        return response.getModule();
    }
}
