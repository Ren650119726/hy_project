package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.StockFrozenRecordQTO;
import com.mockuai.itemcenter.core.dao.StockFrozenRecordDAO;
import com.mockuai.itemcenter.core.domain.StockFrozenRecordDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockFrozenRecordDAOImpl extends SqlMapClientDaoSupport implements StockFrozenRecordDAO {


    @Override
    public Long addStockFrozenRecord(StockFrozenRecordDO stockFrozenRecordDO) {
        return (Long) getSqlMapClientTemplate().insert("stock_frozen_record.addStockFrozenRecord",stockFrozenRecordDO);
    }

    @Override
    public List<StockFrozenRecordDO> queryStockFrozenRecord(StockFrozenRecordQTO stockFrozenRecordQTO) {
        return getSqlMapClientTemplate().queryForList("stock_frozen_record.queryStockFrozenRecord",stockFrozenRecordQTO);
    }

    @Override
    public Long updateStockFrozenRecordStatus(StockFrozenRecordQTO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("stock_frozen_record.updateStockFrozenRecordStatus", query));
    }
}