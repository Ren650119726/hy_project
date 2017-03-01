package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.distributioncenter.core.dao.SellerConfigDAO;
import com.mockuai.distributioncenter.core.dao.SellerDAO;
import com.mockuai.distributioncenter.core.domain.SellerConfigDO;
import com.mockuai.distributioncenter.core.domain.SellerDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 15/10/19.
 */
@Component
public class SellerManagerImpl implements SellerManager {
    private static final Logger log = LoggerFactory.getLogger(SellerManagerImpl.class);

    @Autowired
    private SellerDAO sellerDAO;

    @Autowired
    private SellerConfigDAO sellerConfigDAO;

    @Override
    public Long add(SellerDTO sellerDTO) throws DistributionException {
        SellerDO sellerDO = new SellerDO();
        BeanUtils.copyProperties(sellerDTO, sellerDO);
        return sellerDAO.add(sellerDO);
    }

    @Override
    public List<SellerDTO> query(SellerQTO sellerQTO) throws DistributionException {
        List<SellerDO> sellerDOs = sellerDAO.query(sellerQTO);
        List<SellerDTO> sellerDTOs = new ArrayList<SellerDTO>();
        for (SellerDO sellerDO : sellerDOs) {
            SellerDTO sellerDTO = new SellerDTO();
            BeanUtils.copyProperties(sellerDO, sellerDTO);
            sellerDTOs.add(sellerDTO);
        }
        return sellerDTOs;
    }

    @Override
    public Long totalCount(SellerQTO sellerQTO) throws DistributionException {
        return sellerDAO.totalCount(sellerQTO);
    }

    @Override
    public SellerDTO get(Long id) throws DistributionException {
        SellerDO sellerDO = sellerDAO.get(id);
        if (sellerDO != null) {
            SellerConfigDO sellerConfigDO = sellerConfigDAO.getSellerConfig(sellerDO.getLevelId());
            SellerDTO sellerDTO = new SellerDTO();
            BeanUtils.copyProperties(sellerDO, sellerDTO);
            sellerDTO.setLevel(sellerConfigDO.getLevel());
            sellerDTO.setLevelName(sellerConfigDO.getLevelName());
            return sellerDTO;
        } else {
            log.error("seller not exists, sellerId: {}", id);
            return null;
        }
    }

    @Override
    public SellerDTO getByUserId(Long userId) throws DistributionException {
        SellerDO sellerDO = sellerDAO.getByUserId(userId);
        if (sellerDO != null) {
            SellerConfigDO sellerConfigDO = sellerConfigDAO.getSellerConfig(sellerDO.getLevelId());
            SellerDTO sellerDTO = new SellerDTO();
            BeanUtils.copyProperties(sellerDO, sellerDTO);
            sellerDTO.setLevel(sellerConfigDO.getLevel());
            sellerDTO.setLevelName(sellerConfigDO.getLevelName());
            return sellerDTO;
        } else {
            log.info("seller not exists, userId: {}", userId);
            return null;
        }
    }

    @Override
    public Integer update(SellerDTO sellerDTO) throws DistributionException {
        SellerDO sellerDO = new SellerDO();
        BeanUtils.copyProperties(sellerDTO, sellerDO);
        return sellerDAO.update(sellerDO);
    }

    @Override
    public List<SellerDTO> queryByUserIds(List<Long> userIds) throws DistributionException {
        List<SellerDTO> sellerDTOs = new ArrayList<SellerDTO>();
        if (!userIds.isEmpty()) {
            List<SellerDO> sellerDOs = sellerDAO.queryByUserIds(userIds);
            for (SellerDO sellerDO : sellerDOs) {
                SellerDTO sellerDTO = new SellerDTO();
                BeanUtils.copyProperties(sellerDO, sellerDTO);
                sellerDTOs.add(sellerDTO);
            }
        }
        return sellerDTOs;
    }

    @Override
    public SellerDTO getByInviterCode(String inviterCode) throws DistributionException {
        SellerDO sellerDO = sellerDAO.getByInviterCode(inviterCode);
        if (sellerDO != null) {
            SellerDTO sellerDTO = new SellerDTO();
            BeanUtils.copyProperties(sellerDO, sellerDTO);
            return sellerDTO;
        }
        return null;
    }

    @Override
    public Integer updateByUserId(Long userId, SellerDTO sellerDTO) throws DistributionException {
        SellerDO sellerDO = new SellerDO();
        BeanUtils.copyProperties(sellerDTO, sellerDO);
        return sellerDAO.updateByUserId(userId, sellerDO);
    }
}
