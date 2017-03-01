//package com.mockuai.tradecenter.core.manager.impl;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.domain.OrderQTO;
//import com.mockuai.tradecenter.common.constant.TradeConstants;
//import com.mockuai.tradecenter.core.dao.SellerOrderDao;
//import com.mockuai.tradecenter.core.domain.OrderDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.SellerOrderManager;
//
//public class SellerOrderManagerImpl implements SellerOrderManager {
//	private static final Logger log = LoggerFactory.getLogger(SellerOrderManagerImpl.class);
//
//	@Resource
//	private SellerOrderDao sellerOrderDao;
//
//	@Override
//	public long addOrder(OrderDO orderDO) throws TradeException {
//		log.info("enter addSellerOrder,id: " + orderDO.getId());
//		long id=0;
//		try {
//			id = this.sellerOrderDao.addOrder(orderDO);
//		} catch (Exception e) {
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit addSellerOrder: "  + id);
//		return id;
//	}
//
//	@Override
//	public int cancelOrder(Long orderId, Long sellerId)
//			throws TradeException {
//		log.info("enter cancelSellerOrder: " + orderId +  "," + sellerId );
//		OrderDO order = new OrderDO();
//		order.setId(orderId);
//		order.setSellerId(sellerId);
//		int result;
//		try {
//			result = this.sellerOrderDao.cancelOrder(order);
//		} catch (Exception e) {
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit cancelSellerOrder: " + result);
//		return result;
//	}
//
//	@Override
//	public int deleteOrder(Long orderId, Long sellerId)
//			throws TradeException {
//		log.info("enter deleteSellerOrder: " + orderId  + "," + sellerId);
//		OrderDO order = new OrderDO();
//		order.setId(orderId);
//		order.setSellerId(sellerId);
//		int result;
//		try {
//			result = this.sellerOrderDao.deleteOrder(order);
//		} catch (Exception e) {
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit deleteSellerOrder: " + result);
//		return result;
//	}
//
//	@Override
//	public OrderDO getOrder(Long orderId, Long sellerId) throws TradeException {
//		log.info("enter getSellerOrder : " +  orderId + " ," + sellerId);
//		OrderDO orderDO = new OrderDO();
//		orderDO.setId(orderId);
//		orderDO.setSellerId(sellerId);
//		orderDO = this.sellerOrderDao.getOrder(orderDO);
//		return orderDO;
//	}
//
//	@Override
//	public int getTotalCount(OrderQTO orderQTO){
//		log.info("enter getSellerTotalCount:  " + orderQTO);
//		int result = this.sellerOrderDao.getTotalCount(orderQTO);
//		log.info("exit getTotalCount: " + result);
//		return result;
//	}
//
//	@Override
//	public List<OrderDO> querySellerOrder(OrderQTO orderQTO)throws TradeException{
//		log.info("enter querySellerOrder: " + orderQTO);
//		List<OrderDO> result = this.sellerOrderDao.querySellerOrders(orderQTO);
//		log.info("exit querySellerOrder,result: " + result.size());
//		return result;
//	}
//
//	@Override
//	public int closeOrder(Long orderId,Long sellerId,int orderStatus)throws TradeException{
//		log.info("enter closeSellerOrder: " + orderId + "," + sellerId);
//
//		OrderDO orderDO = new OrderDO();
//		orderDO.setId(orderId);
//		orderDO.setSellerId(sellerId);
//		orderDO.setOrderStatus(orderStatus);
//
//		int result;
//		try {
//			result = this.sellerOrderDao.closeOrder(orderDO);
//		} catch (Exception e) {
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit closeSellerOrder updated : " + result);
//		return result;
//	}
//
////	@Override
////	public int refund(Long orderId, Long sellerId,int afterSale) throws TradeException {
////		log.info("enter refund: " + orderId + "," + sellerId);
////		OrderDO orderDO = new OrderDO();
////
////		orderDO.setId(orderId);
////		orderDO.setSellerId(sellerId);
////		orderDO.setAfterSale(afterSale);
////
////		int result = 0;
////		try {
////			result = this.sellerOrderDao.refund(orderDO);
////		} catch (Exception e) {
////			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
////		}
////		log.info("enter refund, updated : " + result);
////		return result;
////	}
//
//	@Override
//	public int updateOrderMemo(Long orderId,Long sellerId,String newMemo,Integer memoType)throws TradeException{
//		log.info("enter updateOrderMemo :" + orderId + " , " + sellerId + " , " + newMemo + " , " + memoType);
//		OrderDO orderDO = new OrderDO();
//		orderDO.setId(orderId);
//		orderDO.setSellerId(sellerId);
//
//		if(newMemo == null || memoType == null){
//			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING,"memoType or memoType is null");
//		}
//		if(memoType == 1){
//			orderDO.setUserMemo(newMemo);
//		}else if(memoType == 2){
//			orderDO.setSellerMemo(newMemo);
//		}else if(memoType == 3){
//			orderDO.setAdminMemo(newMemo);
//		}else{
//			throw new TradeException(ResponseCode.PARAM_E_PARAM_INVALID,"memoType is invalid");
//		}
//		int result = 0;
//		try{
//			result = this.sellerOrderDao.updateOrderMemo(orderDO);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit updateOrderMemo updated: " + result);
//		return result;
//	}
//
////	@Override
////	public int applyReturn(long orderId ,long sellerId,int afterSale)throws TradeException{
////		int result = 0;
////		log.info("enter  applyReturn " + orderId + "," + sellerId);
////		OrderDO order =new OrderDO();
////
////		order.setId(orderId);
////		order.setSellerId(sellerId);
////		order.setAfterSale(afterSale);
////
////		result = this.sellerOrderDao.applyReturn(order);
////		log.info("exit applyReturn,updated:" + result);
////		return result;
////	}
//
////	@Override
////	public int auditReturnApply(long orderId,long sellerId,int auditResult)throws TradeException{
////		int result =0;
////		log.info("enter auditReturnApply: " + orderId + "," + sellerId + "," + auditResult);
////
////		OrderDO order = new OrderDO();
////		order.setAfterSale(auditResult); //审核退货申请
////		order.setSellerId(sellerId);
////		order.setId(orderId);
////
////		result = this.sellerOrderDao.auditReturnApply(order);
////		log.info("exit auditReturnApply,updated :" + result);
////		return result;
////	}
//
//	@Override
//	public int deliveryGoods(long orderId, long sellerId) throws TradeException{
//		log.info("enter sellerDeliveryGoods: " + orderId + "," + sellerId );
//		int result = 0;
//		OrderDO order = new OrderDO();
//
//		order.setId(orderId);
//		order.setSellerId(sellerId);
//		order.setOrderStatus(TradeConstants.Order_Status.DELIVERIED);
//
//		try{
//			result = this.sellerOrderDao.deliveryGoods(order);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit deliveryGoods , updated  " + result);
//		return result ;
//	}
//
//	@Override
//	public int orderPaySuccess(long orderId, long sellerId)throws TradeException{
//		log.info("enter orderPaySuccess :" + orderId + " , " + sellerId);
//		int result = 0;
//		OrderDO order= new OrderDO();
//		order.setId(orderId);
//		order.setSellerId(sellerId);
//		order.setOrderStatus(TradeConstants.Order_Status.PAID);
//		try{
//			result = this.sellerOrderDao.orderPaySuccess(order);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit orderPaySuccess ,updated: " + result);
//		return result ;
//	}
//
//}
