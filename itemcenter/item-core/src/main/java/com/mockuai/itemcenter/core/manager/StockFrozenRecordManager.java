package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.StockFrozenRecordDTO;
import com.mockuai.itemcenter.common.domain.qto.StockFrozenRecordQTO;
import com.mockuai.itemcenter.core.domain.StockFrozenRecordDO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by yindingyu on 16/5/25.
 */
public interface StockFrozenRecordManager {

    Long addStockFrozenRecord(StockFrozenRecordDTO stockFrozenRecordDTO) throws ItemException;

    List<StockFrozenRecordDTO> queryStockFrozenRecord(StockFrozenRecordQTO stockFrozenRecordQTO)throws ItemException;

    Long updateStockFrozenRecordStatus(String orderSn, Long sellerId, Integer status, String bizCode)throws ItemException;

    Long updateStockFrozenRecordStatus(String orderSn, List<Long> skuIdList, Long sellerId, int status, String bizCode);
}
