package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerUpgradeDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerUpgradeQTO;
import com.mockuai.distributioncenter.core.dao.SellerUpgradeDAO;
import com.mockuai.distributioncenter.core.domain.SellerUpgradeDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerUpgradeManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
@Service
public class SellerUpgradeManagerImpl implements SellerUpgradeManager {
    @Resource
    private SellerUpgradeDAO sellerUpgradeDAO;

    @Override
    public List<SellerUpgradeDTO> querySellerUpgrade(SellerUpgradeQTO sellerUpgradeQTO) {
        List<SellerUpgradeDTO> sellerUpgradeDTOs = null;
        List<SellerUpgradeDO> sellerUpgradeDOs = this.sellerUpgradeDAO.querySellerUpgrade(sellerUpgradeQTO);
        if (sellerUpgradeDOs != null) {
            for (SellerUpgradeDO sellerUpgradeDO : sellerUpgradeDOs) {
                SellerUpgradeDTO sellerUpgradeDTO = new SellerUpgradeDTO();
                BeanUtils.copyProperties(sellerUpgradeDO, sellerUpgradeDTO);
                sellerUpgradeDTOs.add(sellerUpgradeDTO);
            }
        }
        return sellerUpgradeDTOs;
    }

    @Override
    public void agreeSellerUpgrade(Long id, String reason) throws DistributionException {
        int n = this.sellerUpgradeDAO.agreeSellerUpgrade(id,reason);
        if(n!=1){
            throw new DistributionException(ResponseCode.UPDATE_DB_ERROR);
        }
    }

    @Override
    public void rejectSellerUpgrade(Long id, String reason) throws DistributionException {
        int n = this.sellerUpgradeDAO.rejectSellerUpgrade(id,reason);
        if(n != 1){
            throw new DistributionException(ResponseCode.UPDATE_DB_ERROR);
        }
    }

    @Override
    public Long addSellerUpgrade(SellerUpgradeDTO sellerUpgradeDTO) {
        SellerUpgradeDO sellerUpgradeDO = new SellerUpgradeDO();
        BeanUtils.copyProperties(sellerUpgradeDTO,sellerUpgradeDO);
        return this.sellerUpgradeDAO.addSellerUpgrade(sellerUpgradeDO);
    }
}
