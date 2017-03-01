package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuRecommendationDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuRecommendationQTO;
import com.mockuai.itemcenter.core.dao.ItemSkuRecommendationDAO;
import com.mockuai.itemcenter.core.domain.ItemSkuRecommendationDO;
import com.mockuai.itemcenter.core.manager.ItemSkuRecommendationManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/11.
 */
@Service
public class ItemSkuRecommendationManagerImpl implements ItemSkuRecommendationManager {

    @Resource
    private ItemSkuRecommendationDAO itemSkuRecommendationDAO;

    @Override
    public List<ItemSkuRecommendationDTO> queryItemSkuRecommendation(ItemSkuRecommendationQTO itemSkuRecommendationQTO) {

        List<ItemSkuRecommendationDO> itemSkuRecommendationDOList = itemSkuRecommendationDAO.queryItemSkuRecommendation(itemSkuRecommendationQTO);

        List<ItemSkuRecommendationDTO> itemSkuRecommendationDTOList = Lists.newArrayListWithCapacity(itemSkuRecommendationDOList.size());

        for(ItemSkuRecommendationDO skuPropertyRecommendationDO : itemSkuRecommendationDOList){

            ItemSkuRecommendationDTO itemSkuRecommendationDTO = new ItemSkuRecommendationDTO();

            BeanUtils.copyProperties(skuPropertyRecommendationDO, itemSkuRecommendationDTO);

            itemSkuRecommendationDTOList.add(itemSkuRecommendationDTO);
        }

        return  itemSkuRecommendationDTOList;
    }

    @Override
    public Long addItemSkuRecommendation(ItemSkuRecommendationDTO itemSkuRecommendationDTO) {

        ItemSkuRecommendationDO itemSkuRecommendationDO = new ItemSkuRecommendationDO();

        BeanUtils.copyProperties(itemSkuRecommendationDTO,itemSkuRecommendationDO);

        Long result = itemSkuRecommendationDAO.addItemSkuRecommendation(itemSkuRecommendationDO);

        return result;
    }

    @Override
    public Long deleteItemSkuRecommendation(Long id, Long sellerId, String bizCode) {

        ItemSkuRecommendationDO query = new ItemSkuRecommendationDO();

        query.setId(id);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);


        Long result = itemSkuRecommendationDAO.deleteItemSkuRecommendation(query);

        return result;
    }
}
