//package com.mockuai.tradecenter.core.dao.impl;
//
//import java.util.List;
//
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//
//import com.mockuai.tradecenter.common.domain.OrderQTO;
//import com.mockuai.tradecenter.core.dao.SellerOrderDao;
//import com.mockuai.tradecenter.core.domain.OrderDO;
//
//public class SellerOrderDaoImpl extends SqlMapClientDaoSupport implements SellerOrderDao {
//
//	@Override
//	public long addOrder(OrderDO orderDO) {
//		return (Long)this.getSqlMapClientTemplate().insert("seller_order.addOrder",orderDO);
//	}
//
//	@Override
//	public int cancelOrder(OrderDO orderDO) {
//		return (int)this.getSqlMapClientTemplate().update("seller_order.cancelOrder",orderDO);
//	}
//
//	@Override
//	public int deleteOrder(OrderDO orderDO) {
//		return (int)this.getSqlMapClientTemplate().update("seller_order.deleteOrder",orderDO);
//	}
//
//	@Override
//	public OrderDO getOrder(OrderDO orderDO) {
//		return (OrderDO)this.getSqlMapClientTemplate().queryForObject("seller_order.getOrder",orderDO);
//	}
//
//	@Override
//	public int getTotalCount(OrderQTO orderQTO){
//		return (Integer)this.getSqlMapClientTemplate().queryForObject("seller_order.getTotalCount",orderQTO);
//	}
//
//	@Override
//	public int closeOrder(OrderDO orderDO){
//		return (int)this.getSqlMapClientTemplate().update("seller_order.closeOrder",orderDO);
//	}
//
//	@Override
//	public List<OrderDO> querySellerOrders(OrderQTO orderQTO){
//		return this.getSqlMapClientTemplate().queryForList("seller_order.querySellerOrders",orderQTO);
//	}
//
//	@Override
//	public int updateConsigneeInfo(OrderDO orderDO){
//		return (int)this.getSqlMapClientTemplate().update("seller_order.updateConsigneeInfo",orderDO);
//	}
//
//	@Override
//	public int updateOrderMemo(OrderDO orderDO){
//		return (int)this.getSqlMapClientTemplate().update("seller_order.updateOrderMemo",orderDO);
//	}
//
//	@Override
//	public int refund(OrderDO orderDO) {
//		return (int)this.getSqlMapClientTemplate().update("seller_order.refund",orderDO);
//	}
//
//	@Override
//	public int applyReturn(OrderDO orderDO){
//		return (int)this.getSqlMapClientTemplate().update("seller_order.applyReturn",orderDO);
//	}
//
//	@Override
//	public int auditReturnApply(OrderDO orderDO){
//		return (int)this.getSqlMapClientTemplate().update("seller_order.auditReturnApply",orderDO);
//	}
//
//	@Override
//	public int deliveryGoods(OrderDO orderDO){
//		return (int)this.getSqlMapClientTemplate().update("seller_order.deliveryGoods",orderDO);
//	}
//
//	@Override
//	public int orderPaySuccess(OrderDO orderDO){
//		return this.getSqlMapClientTemplate().update("seller_order.orderPaySuccess",orderDO);
//	}
//
//	@Override
//	public int pendingDelivery(OrderDO orderDO){
//		return (Integer)this.getSqlMapClientTemplate().queryForObject("seller_order.pendingDelivery",orderDO);
//	}
//}
//
//
//
