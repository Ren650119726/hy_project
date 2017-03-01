package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.distributioncenter.common.domain.qto.ItemSkuDistPlanQTO;
import com.mockuai.distributioncenter.core.dao.ItemSkuDistPlanDAO;
import com.mockuai.distributioncenter.core.domain.ItemSkuDistPlanDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ItemSkuDistPlanManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 16/5/12.
 */
@Component
public class ItemSkuDistPlanManagerImpl implements ItemSkuDistPlanManager {
    @Autowired
    private ItemSkuDistPlanDAO itemSkuDistPlanDAO;

    @Override
    public Long add(ItemSkuDistPlanDTO ItemSkuDistPlanDTO) throws DistributionException {
        ItemSkuDistPlanDO itemSkuDistPlanDO = new ItemSkuDistPlanDO();
        BeanUtils.copyProperties(ItemSkuDistPlanDTO, itemSkuDistPlanDO);
        return itemSkuDistPlanDAO.add(itemSkuDistPlanDO);
    }

    @Override
    public Integer deleteByItemSkuId(Long itemSkuId) throws DistributionException {
        return itemSkuDistPlanDAO.deleteByItemSkuId(itemSkuId);
    }

    @Override
    public Integer update(ItemSkuDistPlanDTO ItemSkuDistPlanDTO) throws DistributionException {
        ItemSkuDistPlanDO itemSkuDistPlanDO = new ItemSkuDistPlanDO();
        BeanUtils.copyProperties(ItemSkuDistPlanDTO, itemSkuDistPlanDO);
        return itemSkuDistPlanDAO.update(itemSkuDistPlanDO);
    }

    @Override
    public ItemSkuDistPlanDTO getByItemSkuId(Long itemSkuId) throws DistributionException {
        ItemSkuDistPlanDO itemSkuDistPlanDO = itemSkuDistPlanDAO.getByItemSkuId(itemSkuId);
        if (itemSkuDistPlanDO != null) {
            ItemSkuDistPlanDTO ItemSkuDistPlanDTO = new ItemSkuDistPlanDTO();
            BeanUtils.copyProperties(itemSkuDistPlanDO, ItemSkuDistPlanDTO);
            return ItemSkuDistPlanDTO;
        }
        return null;
    }

    @Override
    public List<ItemSkuDistPlanDTO> query(ItemSkuDistPlanQTO itemSkuDistPlanQTO) throws DistributionException {
        List<ItemSkuDistPlanDO> itemSkuDistPlanDOs = itemSkuDistPlanDAO.query(itemSkuDistPlanQTO);
        List<ItemSkuDistPlanDTO> ItemSkuDistPlanDTOs = new ArrayList<ItemSkuDistPlanDTO>();
        for (ItemSkuDistPlanDO itemSkuDistPlanDO : itemSkuDistPlanDOs) {
            ItemSkuDistPlanDTO ItemSkuDistPlanDTO = new ItemSkuDistPlanDTO();
            BeanUtils.copyProperties(itemSkuDistPlanDO, ItemSkuDistPlanDTO);
            ItemSkuDistPlanDTOs.add(ItemSkuDistPlanDTO);
        }
        return ItemSkuDistPlanDTOs;
    }

    @Override
    public Long totalCount(ItemSkuDistPlanQTO itemSkuDistPlanQTO) throws DistributionException {
        return itemSkuDistPlanDAO.totalCount(itemSkuDistPlanQTO);
    }

    @Override
    public List<ItemSkuDistPlanDTO> getByItemId(Long itemId) throws DistributionException {
        List<ItemSkuDistPlanDO> itemSkuDistPlanDOs = itemSkuDistPlanDAO.getByItemId(itemId);
        List<ItemSkuDistPlanDTO> ItemSkuDistPlanDTOs = new ArrayList<ItemSkuDistPlanDTO>();
        for (ItemSkuDistPlanDO itemSkuDistPlanDO : itemSkuDistPlanDOs) {
            ItemSkuDistPlanDTO ItemSkuDistPlanDTO = new ItemSkuDistPlanDTO();
            BeanUtils.copyProperties(itemSkuDistPlanDO, ItemSkuDistPlanDTO);
            ItemSkuDistPlanDTOs.add(ItemSkuDistPlanDTO);
        }
        return ItemSkuDistPlanDTOs;
    }

    @Override
    public List<ItemSkuDistPlanDTO> getDistByItemSkuId(ItemSkuDistPlanQTO ItemSkuDistPlanQTO) throws DistributionException {

        List<ItemSkuDistPlanDO> itemSkuDistPlanDOs = itemSkuDistPlanDAO.getDistByItemSkuId(ItemSkuDistPlanQTO);

        List<ItemSkuDistPlanDTO> itemSkuDistPlanDTOArrayList = new ArrayList<ItemSkuDistPlanDTO>();

        for (ItemSkuDistPlanDO itemSkuDistPlanDO : itemSkuDistPlanDOs) {

            ItemSkuDistPlanDTO itemSkuDistPlanDTO = new ItemSkuDistPlanDTO();

            BeanUtils.copyProperties(itemSkuDistPlanDO, itemSkuDistPlanDTO);
            itemSkuDistPlanDTOArrayList.add(itemSkuDistPlanDTO);

        }
        return itemSkuDistPlanDTOArrayList;
    }
}
