package com.mockuai.tradecenter.core.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;
import com.mockuai.tradecenter.core.dao.TradeNotifyLogDAO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;

import java.util.List;

public class TradeNotifyLogDAOImpl extends SqlMapClientDaoSupport implements TradeNotifyLogDAO {

	@Override
	public Long addTradeNofifyLog(TradeNotifyLogDO record) {
		return  (Long) this.getSqlMapClientTemplate().insert("trade_notify_log.addTradeNofifyLog", record);
	}

	@Override
	public TradeNotifyLogDO getTradeNofityLog(TradeNotifyLogQTO query) {
		return (TradeNotifyLogDO) this.getSqlMapClientTemplate().queryForObject("trade_notify_log.getTradeNofityLog", query);
	}

    @Override
    public List<TradeNotifyLogDO> queryTradeNotifyLog(TradeNotifyLogQTO param) {
        return getSqlMapClientTemplate().queryForList("trade_notify_log.queryTradeNotifyLog",param);
    }

}
