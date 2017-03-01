//package com.mockuai.tradecenter.core.dao.impl;
//
//import java.util.List;
//
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//
//import com.mockuai.tradecenter.core.dao.OrderLogDao;
//import com.mockuai.tradecenter.core.domain.OrderLogDO;
//
//public class OrderLogDaoImpl extends SqlMapClientDaoSupport implements OrderLogDao{
//
//	@Override
//	public long addOrderLog(OrderLogDO orderLogDO){
//		return (Long)this.getSqlMapClientTemplate().insert("order_log.addOrderLog",orderLogDO);
//	}
//
//	@Override
//	public List<OrderLogDO> getUserOrderLog(OrderLogDO orderLogDO){
//		return (List<OrderLogDO>)this.getSqlMapClientTemplate().queryForList("order_log.getUserOrderLog",orderLogDO);
//	}
//
//}
