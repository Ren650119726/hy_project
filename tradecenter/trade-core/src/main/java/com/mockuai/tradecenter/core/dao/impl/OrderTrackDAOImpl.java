package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.OrderTrackQTO;
import com.mockuai.tradecenter.core.dao.OrderTrackDAO;
import com.mockuai.tradecenter.core.domain.OrderTrackDO;

public class OrderTrackDAOImpl extends SqlMapClientDaoSupport implements OrderTrackDAO{

	@Override
	public Long addOrderTrack(OrderTrackDO record) {
		long id = (Long)this.getSqlMapClientTemplate().insert("order_track.add", record);
		return id;
	}

	@Override
	public List<OrderTrackDO> querOrderTrack(OrderTrackQTO query) {
		return this.getSqlMapClientTemplate().queryForList("order_track.query",query);
	}

}
