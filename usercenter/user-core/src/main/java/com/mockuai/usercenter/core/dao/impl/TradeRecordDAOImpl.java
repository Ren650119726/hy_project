package com.mockuai.usercenter.core.dao.impl;

import com.mockuai.usercenter.common.qto.TradeRecordQTO;
import com.mockuai.usercenter.core.dao.TradeRecordDAO;
import com.mockuai.usercenter.core.domain.TradeRecordDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 15/9/22.
 */
@Service
public class TradeRecordDAOImpl extends SqlMapClientDaoSupport implements TradeRecordDAO {
    @Override
    public Long addRecord(TradeRecordDO tradeRecordDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("trade_record.insert", tradeRecordDO);
        return id;
    }

    @Override
    public  TradeRecordDO queryRecordByUserId(Long userId) {
        return (TradeRecordDO) getSqlMapClientTemplate().queryForObject("trade_record.queryByUserId", userId);
    }

    @Override
    public List<TradeRecordDO> queryAll() {
        return getSqlMapClientTemplate().queryForList("trade_record.queryAll");
    }

    @Override
    public List<TradeRecordDO> query(TradeRecordQTO qto) {
        return getSqlMapClientTemplate().queryForList("trade_record.query", qto);
    }

    @Override
    public Long totalCount(TradeRecordQTO qto) {
        return (Long) getSqlMapClientTemplate().queryForObject("trade_record.totalCount", qto);
    }

    @Override
    public int deleteByUserId(Long userId) {
        return getSqlMapClientTemplate().update("trade_record.deleteByUserId", userId);
    }

    @Override
    public int updateByUserId(Long userId, TradeRecordDO tradeRecordDO) {
        tradeRecordDO.setUserId(userId);
        return getSqlMapClientTemplate().update("trade_record.updateByUserId", tradeRecordDO);
    }
}
