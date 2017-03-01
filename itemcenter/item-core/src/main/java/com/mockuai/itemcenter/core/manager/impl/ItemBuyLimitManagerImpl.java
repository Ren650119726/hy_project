package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemBuyLimitDTO;
import com.mockuai.itemcenter.core.dao.ItemBuyLimitDAO;
import com.mockuai.itemcenter.core.domain.ItemBuyLimitDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemBuyLimitManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luliang on 15/7/17.
 */
@Service
public class ItemBuyLimitManagerImpl implements ItemBuyLimitManager {

    private static Logger logger = LoggerFactory.getLogger(ItemBuyLimitManagerImpl.class);

    @Resource
    private ItemBuyLimitDAO itemBuyLimitDAO;

    @Override
    public Integer queryItemBuyLimit(Long sellerId, Long itemId) throws ItemException {
        List<ItemBuyLimitDO> itemBuyLimitDOs = itemBuyLimitDAO.queryItemBuyLimit(itemId, sellerId);
        // 在当前时间范围内的限购值;
        if(itemBuyLimitDOs == null || itemBuyLimitDOs.size() == 0) {
            return 0;
        }

        long currentMillis = System.currentTimeMillis();

        //如果没设置限购时间，则认为所有时间都限购
        for(ItemBuyLimitDO itemBuyLimitDO: itemBuyLimitDOs) {
            long beginTime = 0;
            if(itemBuyLimitDO.getBeginTime() != null){
                beginTime = itemBuyLimitDO.getBeginTime().getTime();
            }

            long endTime = Long.MAX_VALUE;
            if(itemBuyLimitDO.getEndTime() != null){
                endTime = itemBuyLimitDO.getEndTime().getTime();
            }

            if(currentMillis >= beginTime && currentMillis <= endTime) {
                return itemBuyLimitDO.getBuyCount();
            }


        }
        return 0;
    }

    @Override
    public List<ItemBuyLimitDTO> queryItemBuyLimitRecord(Long sellerId, Long itemId) throws ItemException {
        List<ItemBuyLimitDO> itemBuyLimitDOs = itemBuyLimitDAO.queryItemBuyLimit(itemId, sellerId);
        if(itemBuyLimitDOs == null || itemBuyLimitDOs.size() == 0) {
            return null;
        }
        List<ItemBuyLimitDTO> itemBuyLimitDTOs = new ArrayList<ItemBuyLimitDTO>();
        for(ItemBuyLimitDO itemBuyLimitDO: itemBuyLimitDOs) {
            ItemBuyLimitDTO itemBuyLimitDTO = new ItemBuyLimitDTO();
            BeanUtils.copyProperties(itemBuyLimitDO, itemBuyLimitDTO);
            itemBuyLimitDTOs.add(itemBuyLimitDTO);
        }
        return itemBuyLimitDTOs;
    }

    @Override
    public List<ItemBuyLimitDTO> queryItemBuyLimitRecord(Long sellerId, List<Long> itemIdList) throws ItemException {
        List<ItemBuyLimitDO> itemBuyLimitDOs = itemBuyLimitDAO.queryItemBuyLimit(itemIdList, sellerId);
        if(itemBuyLimitDOs == null || itemBuyLimitDOs.size() == 0) {
            return null;
        }
        List<ItemBuyLimitDTO> itemBuyLimitDTOs = new ArrayList<ItemBuyLimitDTO>();
        for(ItemBuyLimitDO itemBuyLimitDO: itemBuyLimitDOs) {
            ItemBuyLimitDTO itemBuyLimitDTO = new ItemBuyLimitDTO();
            BeanUtils.copyProperties(itemBuyLimitDO, itemBuyLimitDTO);
            itemBuyLimitDTOs.add(itemBuyLimitDTO);
        }
        return itemBuyLimitDTOs;
    }

    @Override
    public Long addItemBuyLimit(ItemBuyLimitDTO itemBuyLimitDTO) throws ItemException {
        ItemBuyLimitDO itemBuyLimitDO = new ItemBuyLimitDO();
        BeanUtils.copyProperties(itemBuyLimitDTO, itemBuyLimitDO);
        return itemBuyLimitDAO.addItemBuyLimit(itemBuyLimitDO);
    }

    @Override
    public Boolean deleteItemBuyLimit(Long sellerId, Long itemId) throws ItemException {
        ItemBuyLimitDO itemBuyLimitDO = new ItemBuyLimitDO();
        itemBuyLimitDO.setSellerId(sellerId);
        itemBuyLimitDO.setItemId(itemId);
        itemBuyLimitDAO.deleteItemBuyLimit(itemBuyLimitDO);
        return true;
    }

    @Override
    public Long trashItemBuyLimit(Long sellerId, Long itemId) throws ItemException {
        ItemBuyLimitDO query = new ItemBuyLimitDO();
        query.setSellerId(sellerId);
        query.setItemId(itemId);
        return itemBuyLimitDAO.trashItemBuyLimit(query);
    }

    @Override
    public Long recoveryItemBuyLimit(Long sellerId, Long itemId) throws ItemException {
        ItemBuyLimitDO query = new ItemBuyLimitDO();
        query.setSellerId(sellerId);
        query.setItemId(itemId);
        return itemBuyLimitDAO.recoveryItemBuyLimit(query);
    }

    @Override
    public Long emptyRecycleBin(Long sellerId, String bizCode) throws ItemException {
        ItemBuyLimitDO query = new ItemBuyLimitDO();
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        return itemBuyLimitDAO.emptyRecycleBin(query);
    }
}
