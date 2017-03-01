package com.mockuai.tradecenter.core.dao.impl;

import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.dao.OrderItemDAO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;
/**
 * 订单明细表Dao实现类
 * @author cwr
 */
public class OrderItemDAOImpl extends SqlMapClientDaoSupport implements OrderItemDAO {

	@Override
	public long addOrderItem(OrderItemDO orderItem) {
		return (Long)this.getSqlMapClientTemplate().insert("order_item.addOrderItem",orderItem);
	}
	
	@Override
	public List<OrderItemDO> queryOrderItem(OrderItemQTO orderItemQTO){
		return (List<OrderItemDO>)this.getSqlMapClientTemplate().queryForList(
				"order_item.queryOrderItem", orderItemQTO);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<OrderItemDO> queryOrderItemBg(OrderItemQTO orderItemQTO){
        return (List<OrderItemDO>)this.getSqlMapClientTemplate().queryForList(
                "order_item.queryOrderItemBg", orderItemQTO);
    }
	@Override
	public OrderItemDO getOrderItem(OrderItemQTO orderItemQTO) {
		return (OrderItemDO) this.getSqlMapClientTemplate().queryForObject("order_item.getOrderItem", orderItemQTO);
	}

	@Override
	public List<OrderItemDO> queryOrderItemNoRefund(OrderItemQTO orderItemQTO) {
		return (List<OrderItemDO>)this.getSqlMapClientTemplate().queryForList(
				"order_item.queryOrderItemNoRefund", orderItemQTO);
	}
	

	@Override
	public Integer getProcessingRefundOrderItemCount(OrderItemQTO query) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("order_item.getProcessingRefundOrderItemCount", query);
	}

	@Override
	public List<?> queryProcessingReturnOrderItemDOList(OrderItemQTO query) {
		// TODO Auto-generated method stub
		return (List<OrderItemDO>)this.getSqlMapClientTemplate().queryForList(
				"order_item.queryProcessingReturnOrderItemDOList", query);
	}

	@Override
	public List<?> queryWxPayProcessingRefundItemList(OrderItemQTO query) {
		// TODO Auto-generated method stub
		return (List<OrderItemDO>)this.getSqlMapClientTemplate().queryForList(
				"order_item.queryWxPayProcessingRefundItemList", query);
	}

	@Override
	public Long updateOrderItemDeliveryMark(OrderItemDO query) {
		// TODO Auto-generated method stub
		return (long)this.getSqlMapClientTemplate().update("order_item.updateOrderItemDeliveryMark", query);
	}

	@Override
	public Integer getUnDeliveryOrderItemCount(OrderItemQTO query) {
		// TODO Auto-generated method stub
		return (Integer) this.getSqlMapClientTemplate().queryForObject("order_item.getUnDeliveryOrderItemCount", query);
	}

	@Override
	public List<?> queryTimeoutUntreatedOrderItemList(OrderItemQTO query) {
		/*订单申请售后超时拒绝时间为10天*/
		query.setTimeoutAutoRefundDay(10);
		
		 return (List<OrderItemDO>)this.getSqlMapClientTemplate().queryForList(
					"order_item.queryTimeoutUntreatedOrderItemList", query);
	}

	@Override
	public Long getUnRefundOrderItemTotalPayamount(OrderItemQTO query) {
		return (Long) this.getSqlMapClientTemplate().queryForObject("order_item.getUnRefundOrderItemTotalPayamount", query);
	}

	@Override
	public Integer getItemSalesVolumes(OrderItemQTO query) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("order_item.getItemSalesVolumes",query);
	}

	@Override
	public int updateOrderItemDOById(OrderItemDO query) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().update("order_item.update", query);
	}

	@Override
	public int deleteOrderItem(OrderItemQTO query) {
		return this.getSqlMapClientTemplate().update("order_item.deleteOrderItem", query);
	}

	@Override
	public int falseDeleteOrderItem(OrderItemQTO query) {
		return this.getSqlMapClientTemplate().update("order_item.falseDeleteOrderItem", query);
	}

	@Override
	public OrderItemDO getOrderItemById(Long id) {
		return (OrderItemDO) this.getSqlMapClientTemplate().queryForObject("order_item.getOrderItemById",id);
	}

	@Override
	public List<OrderItemDO> queryOrderItemByItemId(OrderItemQTO query) {
		 return (List<OrderItemDO>)this.getSqlMapClientTemplate().queryForList(
					"order_item.queryOrderItemByItemId", query);
	}

	
}
