package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.SellerUpgradeDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerUpgradeQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
public interface SellerUpgradeManager {
    
    List<SellerUpgradeDTO> querySellerUpgrade(SellerUpgradeQTO sellerUpgradeQTO);

    void agreeSellerUpgrade(Long id, String reason) throws DistributionException;

    void rejectSellerUpgrade(Long id, String reason) throws DistributionException;

    Long addSellerUpgrade(SellerUpgradeDTO sellerUpgradeDTO);
}
