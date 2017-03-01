package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuPropertyRecommendationDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuPropertyRecommendationQTO;
import com.mockuai.itemcenter.core.dao.ItemSkuPropertyRecommendationDAO;
import com.mockuai.itemcenter.core.domain.ItemSkuPropertyRecommendationDO;
import com.mockuai.itemcenter.core.manager.ItemSkuPropertyRecommendationManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/11.
 */
@Service
public class ItemSkuPropertyRecommendationManagerImpl implements ItemSkuPropertyRecommendationManager {

    @Resource
    private ItemSkuPropertyRecommendationDAO itemSkuPropertyRecommendationDAO;

    @Override
    public List<ItemSkuPropertyRecommendationDTO> queryItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationQTO itemSkuRecommendationQTO) {

        List<ItemSkuPropertyRecommendationDO> itemSkuPropertyRecommendationDOList = itemSkuPropertyRecommendationDAO.queryItemSkuPropertyRecommendation(itemSkuRecommendationQTO);

        List<ItemSkuPropertyRecommendationDTO> itemSkuPropertyRecommendationDTOList = Lists.newArrayListWithCapacity(itemSkuPropertyRecommendationDOList.size());

        for(ItemSkuPropertyRecommendationDO skuPropertyRecommendationDO : itemSkuPropertyRecommendationDOList){

            ItemSkuPropertyRecommendationDTO itemSkuPropertyRecommendationDTO = new ItemSkuPropertyRecommendationDTO();

            BeanUtils.copyProperties(skuPropertyRecommendationDO,itemSkuPropertyRecommendationDTO);

            itemSkuPropertyRecommendationDTOList.add(itemSkuPropertyRecommendationDTO);
        }

        return  itemSkuPropertyRecommendationDTOList;
    }

    @Override
    public Long addItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationDTO itemSkuPropertyRecommendationDTO) {

        ItemSkuPropertyRecommendationDO itemSkuPropertyRecommendationDO = new ItemSkuPropertyRecommendationDO();

        BeanUtils.copyProperties(itemSkuPropertyRecommendationDTO,itemSkuPropertyRecommendationDO);

        Long result = itemSkuPropertyRecommendationDAO.addItemSkuPropertyRecommendation(itemSkuPropertyRecommendationDO);

        return result;
    }

    @Override
    public Long deleteItemSkuPropertyRecommendation(Long id, Long sellerId, String bizCode) {

        ItemSkuPropertyRecommendationDO query = new ItemSkuPropertyRecommendationDO();

        query.setId(id);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);


        Long result = itemSkuPropertyRecommendationDAO.deleteItemSkuPropertyRecommendation(query);

        return result;
    }
}
