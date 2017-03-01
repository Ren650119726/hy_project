package com.mockuai.tradecenter.core.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;
import com.mockuai.tradecenter.core.dao.OrderDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;
import java.util.Map;

public class OrderDAOImpl extends SqlMapClientDaoSupport implements OrderDAO {
	
	private static final Logger log = LoggerFactory.getLogger(OrderDAOImpl.class);
	
	protected SqlMapClient sqlMapClientSlave;	
	
	public SqlMapClient getSqlMapClientSlave() {
		return sqlMapClientSlave;
	}

	public void setSqlMapClientSlave(SqlMapClient sqlMapClientSlave) {
		this.sqlMapClientSlave = sqlMapClientSlave;
	}

	@Override
	public long addOrder(OrderDO orderDO){
		long id = (Long)this.getSqlMapClientTemplate().insert("user_order.addOrder",orderDO);
		return id;
//		return (OrderDO)this.getSqlMapClientTemplate().queryForObject("user_order.getOrder",order);
	}
	
	@Override
	public int updatePushOrderStatus(List<OrderQTO> orderQTOList) {
		return (int)this.getSqlMapClientTemplate().update("user_order.pushOrder",orderQTOList);
	}

	@Override
	public int updateOrderStatusById(Map paramMap) {
		return (int)this.getSqlMapClientTemplate().update("user_order.updateOrderStatusById",paramMap);
	}

	@Override
	public Boolean updateDeliveryMarkById(Map paramMap) {
		return (int)this.getSqlMapClientTemplate().update("user_order.updateDeliveryMarkById",paramMap) > 0;
	}

	@Override
	public Boolean UpdateOrderSupplier(OrderQTO orderQTO) {
		return (int)this.getSqlMapClientTemplate().update("user_order.UpdateOrderSupplier",orderQTO) > 0;
	}

	@Override
	public List<OrderDO> queryDeliveriedOrders(OrderQTO orderQTO) {
		return (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryDeliveriedOrders",orderQTO);
	}

	@Override
	public int cancelOrder(OrderDO orderQTO){
		return (int)this.getSqlMapClientTemplate().update("user_order.cancelOrder",orderQTO);
	}
	
	@Override
	public int deleteOrder(OrderDO orderDO){
		return (int)this.getSqlMapClientTemplate().update("user_order.deleteOrder",orderDO);
	}
	
	@Override
	public OrderDO getOrder(OrderDO orderDO){
		return (OrderDO)this.getSqlMapClientTemplate().queryForObject("user_order.getOrder",orderDO);
	}
	
	@Override
	public int updateConsigneeInfo(OrderDO orderDO){
		return (int)this.getSqlMapClientTemplate().update("user_order.updateConsigneeInfo",orderDO);
	}
	
	@Override
	public int updateOrderMemo(OrderDO orderDO){
		return this.getSqlMapClientTemplate().update("user_order.updateOrderMemo",orderDO);
	}

	public int updateOrderPayType(OrderDO orderDO) {
		return this.getSqlMapClientTemplate().update("user_order.updateOrderPayType",orderDO);
	}

	/*@Override
	public List<OrderDO> queryUserOrders(OrderQTO orderQTO){
		//TODO 订单列表的排序先写死在SQLMAP配置中，有时间再抽出来
		return (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryUserOrders",orderQTO);
	}*/
	
	

	@Override
	public List<OrderDO> queryUserOrders(OrderQTO orderQTO) throws TradeException {
		//TODO 订单列表的排序先写死在SQLMAP配置中，有时间再抽出来
		try {
//			log.info(" slave database source start queryUserOrders :"+JSONObject.toJSONString(orderQTO));
			List<OrderDO> result = (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryUserOrders",orderQTO);
//			log.info(" slave database source end queryUserOrders ");
			return result;
		} catch (Exception e) {
			throw new TradeException(e);
		}
		
	}
	
	@Override
	public List<String> getCallBackOrderXX(OrderQTO orderQTO)
			throws TradeException {
		// TODO Auto-generated method stub
		List<String> result = this.getSqlMapClientTemplate().queryForList("user_order.getCallBackOrderXX",orderQTO);
		return result;
	}

	@Override
	public int deleteCallBackOrderXX(OrderQTO orderQTO)
			throws TradeException {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().delete("user_order.deleteCallBackOrderXX",orderQTO);
	}

	@Override
	public List<OrderDO> queryUserOrdersUpgrade(OrderQTO orderQTO)
			throws TradeException {
		try {
			List<OrderDO> result = (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryUserOrdersUpgrade",orderQTO);
			return result;
		} catch (Exception e) {
			throw new TradeException(e);
		}
	}
	
	@Override
	public List<OrderDO> queryInnerUserOrders(OrderQTO orderQTO)
			throws TradeException {
		try {
			List<OrderDO> result = (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryInnerUserOrders",orderQTO);
			return result;
		} catch (Exception e) {
			throw new TradeException(e);
		}
	}
	
	@Override
	public List<OrderDO> queryUser(OrderQTO orderQTO) throws TradeException {
		try {
			List<OrderDO> result = (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryUser",orderQTO);
			return result;
		} catch (Exception e) {
			throw new TradeException(e);
		}
	}

	@Override
	public List<OrderDO> queryUserOrdersUpgradeSeckill(OrderQTO orderQTO)
			throws TradeException {
		try {
			List<OrderDO> result = (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryUserOrdersUpgradeSeckill",orderQTO);
			return result;
		} catch (Exception e) {
			throw new TradeException(e);
		}
	}

	@Override
	public List<OrderDO> queryUserOrdersRefund(OrderQTO orderQTO)
			throws TradeException {
		try {
			List<OrderDO> result = (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryUserOrdersRefund",orderQTO);
			return result;
		} catch (Exception e) {
			throw new TradeException(e);
		}
	}

	/*@Override
	public int queryUserOrdersCount(OrderQTO orderQTO) {
		return (Integer)this.getSqlMapClientTemplate().queryForObject("user_order.queryUserOrdersCount",orderQTO);
	}*/

	@Override
	public int queryUserOrdersCount(OrderQTO orderQTO) throws TradeException {
		try {
//			log.info(" slave database source start queryUserOrdersCount :"+JSONObject.toJSONString(orderQTO));
			Integer result =(Integer)this.getSqlMapClientTemplate().queryForObject("user_order.queryUserOrdersCount",orderQTO);
//			log.info(" slave database source end queryUserOrdersCount :"+JSONObject.toJSONString(result));
			if(result == null){
				result = 0;
			}
			return result;
		} catch (Exception e) {
			throw new TradeException(e);
		}
		
	}

	@Override
	public int confirmArrival(OrderDO orderDO){
		return this.getSqlMapClientTemplate().update("user_order.confirmArrival",orderDO);
	}

	@Override
	public int deliveryGoods(OrderDO order){
		return this.getSqlMapClientTemplate().update("user_order.deliveryGoods",order);
	}

	@Override
	public OrderDO getActiveOrder(OrderDO orderDO) {
		return (OrderDO)this.getSqlMapClientTemplate().queryForObject("user_order.getActiveOrder",orderDO);
	}
	
	@Override
	public int orderPaySuccess(OrderDO orderDO){
		log.info(" payback orderDO: "+JSONObject.toJSONString(orderDO));
		return this.getSqlMapClientTemplate().update("user_order.orderPaySuccess",orderDO);
	}
	
	@Override
	public int orderPaySuccessTest(OrderDO orderDO){
		log.info(" payback orderDO: "+JSONObject.toJSONString(orderDO));
		return this.getSqlMapClientTemplate().update("user_order.orderPaySuccessTest",orderDO);
	}

	@Override
	public int closeOrder(OrderDO orderDO){
		return this.getSqlMapClientTemplate().update("user_order.closeOrder",orderDO);
	}

	public int commentOrder(OrderDO orderDO) {
		return this.getSqlMapClientTemplate().update("user_order.commentOrder",orderDO);
	}

	@Override
	public int returnApply(OrderDO orderDO){
		return this.getSqlMapClientTemplate().update("user_order.applyReturn",orderDO);
	}

	@Override
	public int auditReturnApply(OrderDO orderDO){
		return this.getSqlMapClientTemplate().update("user_order.auditReturnApply",orderDO);
	}

	@Override
	public int refund(OrderDO orderDO){
		return this.getSqlMapClientTemplate().update("user_order.refund",orderDO);
	}

	@Override
	public int getTotalCount(OrderQTO orderQTO) {
		return (Integer)this.getSqlMapClientTemplate().queryForObject("user_order.getTotalCount",orderQTO);
	}

	@Override
	public int getSellerOrdersTotalCount(OrderQTO orderQTO) {
		return (Integer)this.getSqlMapClientTemplate().queryForObject("user_order.getSellerOrdersTotalCount",orderQTO);
	}

	@Override
	public int updateOrderTotalAmountAndDeliveryFee(OrderDO orderDO) {
		return this.getSqlMapClientTemplate().update("user_order.updateOrderTotalAmountAndDeliveryFee",orderDO);
	}

	@Override
	public int updateOrderAsteriskMark(OrderDO orderDO) {
		return this.getSqlMapClientTemplate().update("user_order.updateOrderAsteriskMark",orderDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> queryTimeoutUnpaidOrders(OrderQTO query) {
		return (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryTimeoutUnpaidOrders",query);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> querySignOffOverTimeOrder(OrderQTO orderQTO) {
		return  (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryOverTimeOrder",orderQTO);
	}
	
	public List<OrderDO> queryOverTimeOrder(OrderQTO orderQTO) {
		return  (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryOverTimeOrder",orderQTO);
	}

	@Override
	public Integer getHasBuyCount(OrderQTO orderQTO) {
		return (Integer)this.getSqlMapClientTemplate().queryForObject("user_order.getLimitBuyCount",orderQTO);
	}

	@Override
	public Integer subOrderPaySuccess(OrderDO orderDO) {
		log.info(" alipay back orderDO : "+JSONObject.toJSONString(orderDO));
		return (Integer)this.getSqlMapClientTemplate().update("user_order.updateSubOrderPaySuccess", orderDO);
	}

	@Override
	public List<OrderDO> querySubOrdersByOriginalOrder(OrderQTO orderQTO) {
		return (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.querySubOrdersByOriginalOrder",orderQTO);
	}

	@Override
	public int  markRefund(OrderQTO query) {
		return this.getSqlMapClientTemplate().update("user_order.updateOrderRefund",query);
	}
	
	@Override
	public int updateOrderTotalAmount(OrderDO orderDO) {
		return this.getSqlMapClientTemplate().update("user_order.updateOrderTotalAmount",orderDO);
	}
	
	
	public OrderDO getOrderByOrder_sn(OrderDO orderDO) {
		return (OrderDO)this.getSqlMapClientTemplate().queryForObject("user_order.getOrderByOrder_sn",orderDO);
	}

	@Override
	public List<OrderDO> queryNoSettlementOrders(OrderQTO orderQTO) throws TradeException {
		return (List<OrderDO>)this.getSqlMapClientTemplate().queryForList("user_order.queryNoSettlementOrders",orderQTO);
	}

	@Override
	public int modifySettlementStatus(Long oid,String status) throws TradeException {
		OrderDO oDO = new OrderDO();
		oDO.setId(oid);
		if(StringUtils.equals("Y", status)){
			oDO.setSettlementMark(0);
		}
		return this.getSqlMapClientTemplate().update("user_order.modifySettlementStatus",oDO);
	}

	@Override
	public Long getUnSettlementAmount(DataQTO query)  {
		return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getUnSettlementAmount", query);
	}

	@Override
	public List<OrderDO> queryUnsettlementOrders(OrderQTO query) {
		return this.getSqlMapClientTemplate().queryForList("user_order.queryUnSettlementOrders", query);
	}

	@Override
	public Long getUnsettlementOrderCount(OrderQTO query) {
		return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getUnsettlementOrderCount",query);
	}

	@Override
	public List<OrderDO> queryStoreOrders(OrderQTO orderQTO) {
		return this.getSqlMapClientTemplate().queryForList("user_order.queryStoreOrders", orderQTO);
	}

	@Override
	public int getStoreOrdersCount(OrderQTO orderQTO) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("user_order.getStoreOrdersTotalCount",orderQTO);
	}

	@Override
	public int getItemHasPurchasedCount(OrderQTO query) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("user_order.getItemHasPurchasedCount",query);
	}

	@Override
	public OrderDO getPreOrder(OrderQTO query) {
		return (OrderDO) this.getSqlMapClientTemplate().queryForObject("user_order.getPreOrder",query);
	}

	@Override
	public int updatePreOrder(OrderDO orderDO) {
		return this.getSqlMapClientTemplate().update("user_order.updatePreOrder",orderDO);
	}

	@Override
	public int falseDeleteOrderById(OrderQTO orderQTO) {
		return this.getSqlMapClientTemplate().update("user_order.falseDeleteOrderById",orderQTO);
	}

	@Override
	public List<OrderDO> queryTimeoutPreOrders(OrderQTO query) {
		return this.getSqlMapClientTemplate().queryForList("user_order.queryTimeoutPreOrders",query);
	}

	@Override
	public int deletePreOrder(OrderQTO query) {
		return getSqlMapClientTemplate().delete("user_order.deletePreOrder", query);
	}

	@Override
	public int phyDeleteOrder(OrderQTO query) {
		return getSqlMapClientTemplate().delete("user_order.phyDeleteOrder", query);
	}

	@Override
	public OrderDO getActivityOrder(OrderQTO query) {
		return  (OrderDO) this.getSqlMapClientTemplate().queryForObject("user_order.getActivityOrder",query);
	}

	@Override
	public int update2ActivityOrder(OrderDO orderDO) {
		return this.getSqlMapClientTemplate().update("user_order.update2ActivityOrder",orderDO);
	}

	@Override
	public int updateOrderDiscountInfo(OrderDO orderDO) {
		return this.getSqlMapClientTemplate().update("user_order.updateOrderDiscountInfo",orderDO);
	}

	@Override
	public Long getMallCommissionSumAmount(OrderQTO query) {
		return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getMallCommissionSumAmount",query);
	}

	@Override
	public int updateOrderCommission(OrderDO orderDO) {
		return this.getSqlMapClientTemplate().update("user_order.updateOrderCommission", orderDO);
	}

	@Override
	public Long getSellerSettleTotalAmount(OrderQTO query) {
		return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getSellerSettleTotalAmount", query);
	}



    @Override
    public List<OrderDO> queryOrderByTradeNotify(List<TradeNotifyLogDO> param) {
        return this.getSqlMapClientTemplate().queryForList("user_order.queryOrderByTradeNotify",param);
    }
    @Override
    public List<OrderDO> queryOrderByUserIdAndPaymentId(TradeNotifyLogQTO tradeNotifyLogQTO){
        return getSqlMapClientTemplate().queryForList("user_order.queryOrderByUserIdAndPaymentId",tradeNotifyLogQTO);
    }

    @Override
    public List<OrderDO> queryAllOrderBg(OrderQTO query) {
        return this.getSqlMapClientTemplate().queryForList("user_order.queryAllOrderBg",query);

    }

    @Override
    public Integer queryOrderCount(String discountCode) {
        return null;
    }

    @Override
    public Integer queryAllOrderCountBg(OrderQTO query) {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("user_order.queryAllOrderCountBg",query);

    }

	@Override
	public Long getUserTotalCost(OrderQTO orderQTO) {

		OrderDO orderDO = new OrderDO();
		BeanUtils.copyProperties(orderQTO,orderDO);
		Long totalPaid = (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getUserTotalPaid",orderDO);
		Long totalRefund = (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getUserTotalRefund",orderDO);
		if (totalPaid==null){
			totalPaid = 0L;
		}
		if (totalRefund==null){
			totalRefund = 0L;
		}
		return (totalPaid - totalRefund);



	}

	@Override
	public Integer queryUserOrderCount(OrderQTO orderQTO) {
		
		return (Integer)this.getSqlMapClientTemplate().queryForObject("user_order.queryUserOrderCount",orderQTO);
	}

//	@Override
//	public List<Long> getShareId(Long userId) {
//		List<Long> shareIds = (List<Long>) this.getSqlMapClientTemplate().queryForList("user_order.getShareId",userId);
////		if(orderDOs!=null){
////			Long shareId = orderDOs.get(1);
////			return shareId;
////		}
//		return shareIds;
//	}
}
