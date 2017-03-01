package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.StockFrozenRecordQTO;
import com.mockuai.itemcenter.core.domain.StockFrozenRecordDO;

import java.util.List;

public interface StockFrozenRecordDAO {

    Long addStockFrozenRecord(StockFrozenRecordDO stockFrozenRecordDO);

    List<StockFrozenRecordDO> queryStockFrozenRecord(StockFrozenRecordQTO stockFrozenRecordQTO);

    Long updateStockFrozenRecordStatus(StockFrozenRecordQTO stockFrozenRecordDO);

}