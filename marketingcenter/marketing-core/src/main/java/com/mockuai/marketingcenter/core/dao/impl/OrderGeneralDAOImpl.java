package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.dto.OrderGeneralDTO;
import com.mockuai.marketingcenter.core.dao.OrderGeneralDAO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public class OrderGeneralDAOImpl extends SqlMapClientDaoSupport implements OrderGeneralDAO {

	@Override
	public OrderGeneralDTO getOrderGeneral(OrderGeneralDTO orderGeneralDTO) {
		OrderGeneralDTO record = (OrderGeneralDTO) getSqlMapClientTemplate().queryForObject("order_general.getOrderGeneral", orderGeneralDTO);
		return record;
	}
}