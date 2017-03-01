package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.CollectionShopDTO;
import com.mockuai.distributioncenter.core.dao.CollectionShopDAO;
import com.mockuai.distributioncenter.core.domain.CollectionShopDO;
import com.mockuai.distributioncenter.core.manager.CollectionShopManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 16/5/21.
 */
@Component
public class CollectionShopManagerImpl implements CollectionShopManager {
    @Autowired
    private CollectionShopDAO collectionShopDAO;

    @Override
    public Long add(CollectionShopDTO collectionShopDTO) {
        CollectionShopDO collectionShopDO = new CollectionShopDO();
        BeanUtils.copyProperties(collectionShopDTO, collectionShopDO);
        return collectionShopDAO.add(collectionShopDO);
    }

    @Override
    public int delete(Long id) {
        return collectionShopDAO.delete(id);
    }

    @Override
    public List<CollectionShopDTO> queryByUserId(Long userId) {
        List<CollectionShopDO> collectionShopDOs = collectionShopDAO.queryByUserId(userId);
        List<CollectionShopDTO> collectionShopDTOs = new ArrayList<CollectionShopDTO>();
        for (CollectionShopDO collectionShopDO : collectionShopDOs) {
            CollectionShopDTO collectionShopDTO = new CollectionShopDTO();
            BeanUtils.copyProperties(collectionShopDO, collectionShopDTO);
            collectionShopDTOs.add(collectionShopDTO);
        }
        return collectionShopDTOs;
    }
}
