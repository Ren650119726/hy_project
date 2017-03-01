package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.ItemDistPlanDTO;
import com.mockuai.distributioncenter.common.domain.qto.ItemDistPlanQTO;
import com.mockuai.distributioncenter.core.dao.ItemDistPlanDAO;
import com.mockuai.distributioncenter.core.domain.ItemDistPlanDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ItemDistPlanManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 15/10/27.
 */
@Component
public class ItemDistPlanManagerImpl implements ItemDistPlanManager {
    @Autowired
    private ItemDistPlanDAO itemDistPlanDAO;

    @Override
    public Long add(ItemDistPlanDTO itemDistPlanDTO) throws DistributionException {
        ItemDistPlanDO itemDistPlanDO = new ItemDistPlanDO();
        BeanUtils.copyProperties(itemDistPlanDTO, itemDistPlanDO);
        return itemDistPlanDAO.add(itemDistPlanDO);
    }

    @Override
    public Integer deleteByItemId(Long itemId) throws DistributionException {
        return itemDistPlanDAO.deleteByItemId(itemId);
    }

    @Override
    public Integer update(ItemDistPlanDTO itemDistPlanDTO) throws DistributionException {
        ItemDistPlanDO itemDistPlanDO = new ItemDistPlanDO();
        BeanUtils.copyProperties(itemDistPlanDTO, itemDistPlanDO);
        return itemDistPlanDAO.update(itemDistPlanDO);
    }

    @Override
    public ItemDistPlanDTO getByItemAndLevel(Long itemId, Integer level) throws DistributionException {
        ItemDistPlanDO itemDistPlanDO = itemDistPlanDAO.getByItemAndLevel(itemId, level);
        if (itemDistPlanDO != null) {
            ItemDistPlanDTO itemDistPlanDTO = new ItemDistPlanDTO();
            BeanUtils.copyProperties(itemDistPlanDO, itemDistPlanDTO);
            return itemDistPlanDTO;
        }
        return null;
    }

    @Override
    public List<ItemDistPlanDTO> query(ItemDistPlanQTO itemDistPlanQTO) throws DistributionException {
        List<ItemDistPlanDO> itemDistPlanDOs = itemDistPlanDAO.query(itemDistPlanQTO);
        List<ItemDistPlanDTO> itemDistPlanDTOs = new ArrayList<ItemDistPlanDTO>();
        for (ItemDistPlanDO itemDistPlanDO : itemDistPlanDOs) {
            ItemDistPlanDTO itemDistPlanDTO = new ItemDistPlanDTO();
            BeanUtils.copyProperties(itemDistPlanDO, itemDistPlanDTO);
            itemDistPlanDTOs.add(itemDistPlanDTO);
        }
        return itemDistPlanDTOs;
    }

    @Override
    public Long totalCount(ItemDistPlanQTO itemDistPlanQTO) throws DistributionException {
        return itemDistPlanDAO.totalCount(itemDistPlanQTO);
    }
}
