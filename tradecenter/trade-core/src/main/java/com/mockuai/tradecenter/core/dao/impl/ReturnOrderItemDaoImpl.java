//package com.mockuai.tradecenter.core.dao.impl;
//
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//
//import com.mockuai.tradecenter.core.dao.ReturnOrderItemDao;
//import com.mockuai.tradecenter.core.domain.ReturnOrderItemDO;
//
//public class ReturnOrderItemDaoImpl extends SqlMapClientDaoSupport  implements ReturnOrderItemDao{
//
//	@Override
//	public long addReturnOrderItemDao(ReturnOrderItemDO returnOrderItemDO) {
//		return (Long)this.getSqlMapClientTemplate().insert("return_order_item.addReturnOrderItem",returnOrderItemDO);
//	}
//
//	@Override
//	public int updateReturnItemStatus(ReturnOrderItemDO returnOrderItem){
//		return (int)this.getSqlMapClientTemplate().update("return_order_item.updateReturnItemStatus",returnOrderItem);
//	}
//
//}
