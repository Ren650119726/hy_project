package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.core.dao.SellerConfigDAO;
import com.mockuai.distributioncenter.core.domain.SellerConfigDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
@Component
public class SellerConfigManagerImpl implements SellerConfigManager {
    @Resource
    private SellerConfigDAO sellerConfigDAO;

    @Override
    public Long addSellerConfig(SellerConfigDTO sellerConfigDTO) {
        SellerConfigDO sellerConfigDO = new SellerConfigDO();
        BeanUtils.copyProperties(sellerConfigDTO, sellerConfigDO);
        Long id = this.sellerConfigDAO.addSellerConfig(sellerConfigDO);
        return id;
    }

    @Override
    public SellerConfigDTO getSellerConfig(Long id) {
        SellerConfigDO sellerConfigDO = this.sellerConfigDAO.getSellerConfig(id);
        SellerConfigDTO sellerConfigDTO = new SellerConfigDTO();
        if (sellerConfigDO != null) {
            BeanUtils.copyProperties(sellerConfigDO, sellerConfigDTO);
            return sellerConfigDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<SellerConfigDTO> querySellerConfig(SellerConfigQTO sellerConfigQTO) {
        List<SellerConfigDTO> sellerConfigDTOs = null;
        List<SellerConfigDO> sellerConfigDOs = this.sellerConfigDAO.querySellerConfig(sellerConfigQTO);
        if (sellerConfigDOs != null) {
            sellerConfigDTOs = new ArrayList<SellerConfigDTO>();
            for (SellerConfigDO sellerConfigDO : sellerConfigDOs) {
                SellerConfigDTO sellerConfigDTO = new SellerConfigDTO();
                BeanUtils.copyProperties(sellerConfigDO, sellerConfigDTO);
                sellerConfigDTOs.add(sellerConfigDTO);
            }
        }
        return sellerConfigDTOs;
    }

    @Override
    public Boolean updateSellerConfig(SellerConfigDTO sellerConfigDTO) throws DistributionException {
        SellerConfigDO sellerConfigDO = new SellerConfigDO();
        BeanUtils.copyProperties(sellerConfigDTO, sellerConfigDO);
        int n = this.sellerConfigDAO.updateSellerConfig(sellerConfigDO);
        if (n == 1) {
            return true;
        } else {
            throw new DistributionException(ResponseCode.UPDATE_DB_ERROR, "更新记录大于1条");
        }
    }
}
