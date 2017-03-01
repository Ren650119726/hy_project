package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.qto.OrderRecordQTO;
import com.mockuai.marketingcenter.core.dao.OrderRecordDAO;
import com.mockuai.marketingcenter.core.domain.OrderRecordDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public class OrderRecordDAOImpl extends SqlMapClientDaoSupport implements OrderRecordDAO {

	@Override
	public Long addOrderRecord(OrderRecordDO orderRecordDO) {
		Long id = (Long) getSqlMapClientTemplate().insert("order_record.addOrderRecord", orderRecordDO);
		return id;
	}

	@Override
	public List<OrderRecordDO> queryOrderRecord(OrderRecordQTO orderRecordQTO) {
		orderRecordQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("order_record.countOfQueryOrderRecord", orderRecordQTO));
		List<OrderRecordDO> orderRecordDOs = getSqlMapClientTemplate().queryForList("order_record.queryOrderRecord", orderRecordQTO);
		return orderRecordDOs;
	}
}