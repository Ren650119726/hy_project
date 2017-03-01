package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.domain.dto.StockFrozenRecordDTO;
import com.mockuai.itemcenter.common.domain.qto.StockFrozenRecordQTO;
import com.mockuai.itemcenter.core.dao.StockFrozenRecordDAO;
import com.mockuai.itemcenter.core.domain.StockFrozenRecordDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.StockFrozenRecordManager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by yindingyu on 16/5/25.
 */
@Service
public class StockFrozenRecordManagerImpl implements StockFrozenRecordManager {

    @Resource
    private StockFrozenRecordDAO stockFrozenRecordDAO;


    @Override
    public Long addStockFrozenRecord(StockFrozenRecordDTO stockFrozenRecordDTO)throws ItemException {

        StockFrozenRecordDO stockFrozenRecordDO = new StockFrozenRecordDO();

        BeanUtils.copyProperties(stockFrozenRecordDTO,stockFrozenRecordDO);

        return stockFrozenRecordDAO.addStockFrozenRecord(stockFrozenRecordDO);
    }

    @Override
    public List<StockFrozenRecordDTO> queryStockFrozenRecord(StockFrozenRecordQTO stockFrozenRecordQTO)throws ItemException {

        List<StockFrozenRecordDO> stockFrozenRecordDOList = stockFrozenRecordDAO.queryStockFrozenRecord(stockFrozenRecordQTO);

        if(CollectionUtils.isEmpty(stockFrozenRecordDOList)){
            return Collections.EMPTY_LIST;
        }

        List<StockFrozenRecordDTO> stockFrozenRecordDTOList = Lists.newArrayListWithExpectedSize(stockFrozenRecordDOList.size());

        for(StockFrozenRecordDO stockFrozenRecordDO : stockFrozenRecordDOList){

            StockFrozenRecordDTO stockFrozenRecordDTO = new StockFrozenRecordDTO();
            BeanUtils.copyProperties(stockFrozenRecordDO,stockFrozenRecordDTO);

            stockFrozenRecordDTOList.add(stockFrozenRecordDTO);
        }

        return stockFrozenRecordDTOList;
    }

    @Override
    public Long updateStockFrozenRecordStatus(String orderSn, Long sellerId, Integer status, String bizCode)throws ItemException{
        StockFrozenRecordQTO query = new StockFrozenRecordQTO();
        query.setOrderSn(orderSn);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        query.setStatus(status);

        return  stockFrozenRecordDAO.updateStockFrozenRecordStatus(query);
    }

    @Override
    public Long updateStockFrozenRecordStatus(String orderSn, List<Long> skuIdList, Long sellerId, int status, String bizCode) {
        StockFrozenRecordQTO query = new StockFrozenRecordQTO();
        query.setOrderSn(orderSn);
        query.setSkuIdList(skuIdList);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        query.setStatus(status);

        return  stockFrozenRecordDAO.updateStockFrozenRecordStatus(query);
    }
}
