package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.dao.GainsSetDAO;
import com.mockuai.distributioncenter.core.domain.GainsSetDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.GainsSetManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lizg on 2016/8/29.
 */

@Component
public class GainsSetManagerImpl implements GainsSetManager {

    private static final Logger log = LoggerFactory.getLogger(GainsSetManagerImpl.class);

    @Autowired
    private GainsSetDAO gainsSetDAO;

    @Override
    public Long add(GainsSetDTO gainsSetDTO) throws DistributionException {
        GainsSetDO gainsSetDO = new GainsSetDO();
        BeanUtils.copyProperties(gainsSetDTO,gainsSetDO);
        return gainsSetDAO.add(gainsSetDO);
    }

    @Override
    public GainsSetDTO get() throws DistributionException {
        GainsSetDO gainsSetDO = gainsSetDAO.get();
        GainsSetDTO gainsSetDTO = new GainsSetDTO();
        BeanUtils.copyProperties(gainsSetDO, gainsSetDTO);
        return gainsSetDTO;
    }

    @Override
    public Integer update(GainsSetDTO gainsSetDTO) throws DistributionException {
        GainsSetDO gainsSetDO = new GainsSetDO();
        BeanUtils.copyProperties(gainsSetDTO,gainsSetDO);
        return gainsSetDAO.update(gainsSetDO);
    }
}
