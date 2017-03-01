package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.domain.dto.ItemDescTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDescTmplQTO;
import com.mockuai.itemcenter.core.dao.ItemDescTmplDAO;
import com.mockuai.itemcenter.core.domain.ItemDescTmplDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemDescTmplManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/3/8.
 */
@Service
public class ItemDescTmplManagerImpl implements ItemDescTmplManager {

    @Resource
    private ItemDescTmplDAO itemDescTmplDAO;

    @Override
    public Long addItemDescTmpl(ItemDescTmplDTO itemDescTmplDTO) throws ItemException {

        ItemDescTmplDO itemDescTmplDO = new ItemDescTmplDO();
        BeanUtils.copyProperties(itemDescTmplDTO, itemDescTmplDO);
        return (Long) itemDescTmplDAO.addItemDescTmpl(itemDescTmplDO);
    }

    @Override
    public Long updateItemDescTmpl(ItemDescTmplDTO itemDescTmplDTO) throws ItemException {

        ItemDescTmplDO itemDescTmplDO = new ItemDescTmplDO();
        BeanUtils.copyProperties(itemDescTmplDTO, itemDescTmplDO);
        return itemDescTmplDAO.updateItemDescTmpl(itemDescTmplDO);
    }

    @Override
    public ItemDescTmplDTO getItemDescTmpl(Long itemDescTmplId, Long sellerId, String bizCode) {


        ItemDescTmplDO query = new ItemDescTmplDO();
        query.setId(itemDescTmplId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);

        ItemDescTmplDO itemDescTmplDO = itemDescTmplDAO.getItemDescTmpl(query);

        ItemDescTmplDTO itemDescTmplDTO = new ItemDescTmplDTO();

        if (itemDescTmplDO == null) {
            return null;
        }
        BeanUtils.copyProperties(itemDescTmplDO, itemDescTmplDTO);
        return itemDescTmplDTO;
    }

    @Override
    public Long deleteItemDescTmpl(Long itemDescTmplId, Long sellerId, String bizCode) throws ItemException {
        ItemDescTmplDO query = new ItemDescTmplDO();
        query.setId(itemDescTmplId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);

        return itemDescTmplDAO.deleteItemDescTmpl(query);
    }

    @Override
    public List<ItemDescTmplDTO> queryItemDescTmpl(ItemDescTmplQTO itemDescTmplQTO) throws ItemException {

        List<ItemDescTmplDO> itemDescTmplDOList = itemDescTmplDAO.queryItemDescTmpl(itemDescTmplQTO);

        List<ItemDescTmplDTO> itemDescTmplDTOList = Lists.newArrayListWithCapacity(itemDescTmplDOList.size());

        for (ItemDescTmplDO itemDescTmplDO : itemDescTmplDOList) {

            ItemDescTmplDTO itemDescTmplDTO = new ItemDescTmplDTO();
            BeanUtils.copyProperties(itemDescTmplDO, itemDescTmplDTO);
            itemDescTmplDTOList.add(itemDescTmplDTO);
        }

        return itemDescTmplDTOList;

    }
}
