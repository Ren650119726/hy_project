package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.TotalWealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;

import java.util.List;

/**
 * Created by duke on 16/5/19.
 */
public interface VirtualWealthManager {
    TotalWealthAccountDTO queryVirtualWealthCombine(Long userId, String appKey) throws DistributionException;
    List<WealthAccountDTO> queryTotalVirtualWealth(List<Long> userIds, Integer wealthType, String appKey) throws DistributionException;
    Boolean distributorGrant(GrantedWealthDTO grantedWealthDTO, String appKey) throws DistributionException;
    Boolean updateStatusOfVirtualWealthDistributorGranted(Long orderId, Long itemSkuId, Integer sourceType, Integer status, String appKey) throws DistributionException;
}
