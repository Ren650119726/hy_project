package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerRelationshipQTO;
import com.mockuai.distributioncenter.core.dao.SellerDAO;
import com.mockuai.distributioncenter.core.dao.SellerRelationshipDAO;
import com.mockuai.distributioncenter.core.domain.SellerRelationshipDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 15/10/19.
 */
@Component
public class SellerRelationshipManagerImpl implements SellerRelationshipManager {
    @Autowired
    private SellerRelationshipDAO sellerRelationshipDAO;

    @Autowired
    private SellerDAO sellerDAO;

    @Override
    public Long add(SellerRelationshipDTO sellerRelationshipDTO) throws DistributionException {
        SellerRelationshipDO sellerRelationshipDO = new SellerRelationshipDO();
        BeanUtils.copyProperties(sellerRelationshipDTO, sellerRelationshipDO);
        return sellerRelationshipDAO.add(sellerRelationshipDO);
    }

    @Override
    public Integer delete(Long id) throws DistributionException {
        return sellerRelationshipDAO.delete(id);
    }

    @Override
    public Integer update(SellerRelationshipDTO sellerRelationshipDTO) throws DistributionException {
        SellerRelationshipDO sellerRelationshipDO = new SellerRelationshipDO();
        BeanUtils.copyProperties(sellerRelationshipDTO, sellerRelationshipDO);
        return sellerRelationshipDAO.update(sellerRelationshipDO);
    }

    @Override
    public List<SellerRelationshipDTO> query(SellerRelationshipQTO sellerRelationshipQTO)
            throws DistributionException {
        List<SellerRelationshipDO> sellerRelationshipDOs =
                sellerRelationshipDAO.query(sellerRelationshipQTO);
        List<SellerRelationshipDTO> sellerRelationshipDTOs = new ArrayList<SellerRelationshipDTO>();
        for (SellerRelationshipDO sellerRelationshipDO : sellerRelationshipDOs) {
            SellerRelationshipDTO sellerRelationshipDTO = new SellerRelationshipDTO();
            BeanUtils.copyProperties(sellerRelationshipDO, sellerRelationshipDTO);
            sellerRelationshipDTOs.add(sellerRelationshipDTO);
        }
        return sellerRelationshipDTOs;
    }

    @Override
    public SellerRelationshipDTO getByUserId(Long userId) throws DistributionException {
        SellerRelationshipDO sellerRelationshipDO = sellerRelationshipDAO.getByUserId(userId);
        if (sellerRelationshipDO != null) {
            SellerRelationshipDTO sellerRelationshipDTO = new SellerRelationshipDTO();
            BeanUtils.copyProperties(sellerRelationshipDO, sellerRelationshipDTO);
            return sellerRelationshipDTO;
        }
        return null;
    }

    @Override
    public SellerRelationshipDTO get(Long id) throws DistributionException {
        SellerRelationshipDO sellerRelationshipDO =
                sellerRelationshipDAO.get(id);
        if (sellerRelationshipDO != null) {
            SellerRelationshipDTO sellerRelationshipDTO = new SellerRelationshipDTO();
            BeanUtils.copyProperties(sellerRelationshipDO, sellerRelationshipDTO);
            return sellerRelationshipDTO;
        }
        return null;
    }

    @Override
    public Long totalCount(SellerRelationshipQTO sellerRelationshipQTO) throws DistributionException {
        return sellerRelationshipDAO.totalCount(sellerRelationshipQTO);
    }

    @Override
    public List<SellerRelationshipDTO> queryByUserIds(List<Long> userIds) throws DistributionException {
        List<SellerRelationshipDTO> relationshipDTOs = new ArrayList<SellerRelationshipDTO>();
        List<SellerRelationshipDO> relationshipDOs = sellerRelationshipDAO.queryByUserIds(userIds);
        for (SellerRelationshipDO relationshipDO : relationshipDOs) {
            SellerRelationshipDTO relationshipDTO = new SellerRelationshipDTO();
            BeanUtils.copyProperties(relationshipDO, relationshipDTO);
            relationshipDTOs.add(relationshipDTO);
        }
        return relationshipDTOs;
    }

    @Override
    public List<Long> queryPosterityUserIds(SellerRelationshipQTO sellerRelationshipQTO) throws DistributionException {
        return sellerRelationshipDAO.queryPosterityUserIds(sellerRelationshipQTO);
    }

    @Override
    public List<Map<String, Long>> queryTotalCountByUserIds(List<Long> userIds) throws DistributionException {
        return sellerRelationshipDAO.queryTotalCountByUserIds(userIds);
    }
}
