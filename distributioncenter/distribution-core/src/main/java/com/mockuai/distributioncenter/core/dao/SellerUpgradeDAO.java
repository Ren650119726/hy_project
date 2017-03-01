package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.common.domain.qto.SellerUpgradeQTO;
import com.mockuai.distributioncenter.core.domain.SellerUpgradeDO;

import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
public interface SellerUpgradeDAO {
    List<SellerUpgradeDO> querySellerUpgrade(SellerUpgradeQTO sellerUpgradeQTO);

    int agreeSellerUpgrade(Long id, String reason);

    int rejectSellerUpgrade(Long id, String reason);

    Long addSellerUpgrade(SellerUpgradeDO sellerUpgradeDO);
}
