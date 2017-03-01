package com.mockuai.tradecenter.core.manager.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.domain.dto.*;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.core.dao.OrderDAO;
import com.mockuai.tradecenter.core.dao.OrderItemDAO;
import com.mockuai.tradecenter.core.dao.OrderServiceDAO;
import com.mockuai.tradecenter.core.dao.OrderStoreDAO;
import com.mockuai.tradecenter.core.domain.*;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.util.DateUtil;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.ModelUtil;
import com.mockuai.tradecenter.core.util.PaymentUtil.BizLockType;
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.mockuai.tradecenter.core.util.TradeUtil.RemoveDeliveryFeeAmount;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

public class OrderManagerImpl extends BaseService implements OrderManager {
	private static final Logger log = LoggerFactory.getLogger(OrderManagerImpl.class);

	@Resource
	private OrderDAO orderDAO;

	@Resource
	private MarketingManager marketingManager;

	@Resource
	private ItemManager itemManager;
	@Resource
	private OrderSeqManager orderSeqManager;

	@Resource
	private DozerBeanService dozerBeanService;

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private OrderViewManager orderViewManager;
	@Resource
	private OrderInvoiceManager orderInvoiceManager;
	@Resource
	private OrderConsigneeManager orderConsigneeManager;
	@Resource
	private OrderItemManager orderItemManager;
	@Resource
	private OrderDiscountInfoManager orderDiscountInfoManager;
	@Resource
	private OrderPaymentManager orderPaymentManager;

	@Resource
	private UserManager userManagerImpl;
	
	@Autowired
	private AppManager appManager;
	
	@Resource
    private DeliveryManager deliveryManager;
	
	@Resource
	private OrderStoreDAO orderStoreDAO;
	
	@Resource
	private OrderServiceDAO orderServiceDAO;
	
	@Resource
	private MsgQueueManager msgQueueManager;
	
	@Autowired
	private BizLockManager bizLockManager;
	
	@Autowired
	private OrderItemDAO orderItemDAO;
	

	@Override
	public long addOrder(OrderDO orderDO) throws TradeException {
		log.info("enter addOrder");
		long result = 0;
		orderDO.setOrderStatus(TradeConstants.Order_Status.UNPAID);
		try {
			result = (Long) this.orderDAO.addOrder(orderDO);
		} catch (Exception e) {
			log.error("addOrder error",e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit addOrder: " + result);
		return result;
	}
	
	@Override
	public long addPreOrder(OrderDO orderDO) throws TradeException {
		log.info("enter addPreOrder");
		long result = 0;
		orderDO.setOrderStatus(Integer.parseInt(EnumOrderStatus.PRE_ORDER.getCode()));
		try {
			result = (Long) this.orderDAO.addOrder(orderDO);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit addPreOrder: " + result);
		return result;
	}

	@Override
	public int cancelOrder(Long orderId, Long userId, String cancelReason, boolean isSellerCancel)
			throws TradeException {
		log.info("enter cancelOrder: " + orderId + " , " + userId);
		OrderDO orderDO = new OrderDO();
		orderDO.setUserId(userId);
		orderDO.setId(orderId);
		if (isSellerCancel) {
			orderDO.setOrderStatus(Integer.parseInt(EnumOrderStatus.SELLER_CLOSE.getCode()));
		} else {
			orderDO.setOrderStatus(TradeConstants.Order_Status.CANCELED);
		}

		orderDO.setCancelReason(cancelReason);
		int result = 0;
		try {
			result = orderDAO.cancelOrder(orderDO);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit cancelOrder updated ： " + result);
		return result;
	}

	@Override
	public Integer deleteOrder(Long orderId, Long userId) throws TradeException {
		log.info("enter deleteOrder: " + orderId + " , " + userId);
		OrderDO order = new OrderDO();

		order.setUserId(userId);
		order.setId(orderId);

		int result = 0;
		try {
			result = orderDAO.deleteOrder(order);
		} catch (Exception e) {
			log.error("deleteOrder error",e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit deleteOrder updated：" + result);
		return result;

	}

	@Override
	public int updateOrderMemo(Long orderId, Long userId, String newMemo, Integer memoType) throws TradeException {
		log.info("enter updateOrderMemo :" + orderId + " , " + userId + " , " + newMemo + " , " + memoType);
		OrderDO orderDO = new OrderDO();
		orderDO.setId(orderId);
		orderDO.setUserId(userId);
		if (newMemo == null || memoType == null) {
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "memoType or memoType is null");
		}
		if (memoType == 1) {
			orderDO.setUserMemo(newMemo);
		} else if (memoType == 2) {
			orderDO.setSellerMemo(newMemo);
		} else if (memoType == 3) {
			orderDO.setAdminMemo(newMemo);
		} else {
			throw new TradeException(ResponseCode.PARAM_E_PARAM_INVALID, "memoType is invalid");
		}
		int result = 0;
		try {
			result = this.orderDAO.updateOrderMemo(orderDO);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit updateOrderMemo updated: " + result);
		return result;
	}

	public void updateOrderPayType(OrderDO orderDO, int payType) throws TradeException {
		//更新订单支付方式
		updateOrderPayType(orderDO.getId(), orderDO.getUserId(), payType);
		//更新订单支付表的支付方式
		orderPaymentManager.updatePaymentType(orderDO.getId(), orderDO.getUserId(), payType);

		//如果是父订单，则还需要同步更新子订单
		if (orderDO.getParentMark()!=null && orderDO.getParentMark().intValue()==1) {
			//查询子订单
			/*OrderQTO orderQTO = new OrderQTO();
			orderQTO.setOriginalOrder(orderDO.getId());
			orderQTO.setUserId(orderDO.getUserId());
			List<OrderDO> subOrders = queryUserOrders(orderQTO);*/
			List<OrderDO> subOrders = querySubOrders(orderDO.getId());
			//更新子订单的支付方式
			for (OrderDO subOrder : subOrders) {//TODO 性能待优化，改成批量更新
				updateOrderPayType(subOrder.getId(), subOrder.getUserId(), payType);
				orderPaymentManager.updatePaymentType(subOrder.getId(), subOrder.getUserId(), payType);
			}
		}
		if (orderDO.getParentMark()!=null && orderDO.getParentMark().intValue()==2) {
			//查询子订单
			/*OrderQTO orderQTO = new OrderQTO();
			orderQTO.setOriginalOrder(orderDO.getId());
			orderQTO.setUserId(orderDO.getUserId());*/
//			List<OrderDO> subOrders = queryUserOrders(orderQTO);
			List<OrderDO> subOrders = querySubOrders(orderDO.getId());
			//更新子订单的支付方式
			for (OrderDO subOrder : subOrders) {//TODO 性能待优化，改成批量更新
				updateOrderPayType(subOrder.getId(), subOrder.getUserId(), payType);
				orderPaymentManager.updatePaymentType(subOrder.getId(), subOrder.getUserId(), payType);
				if(subOrder.getParentMark() == 1){
					List<OrderDO> inSubOrders = querySubOrders(subOrder.getId());
					for(OrderDO inSubOrder : inSubOrders){
						updateOrderPayType(inSubOrder.getId(), inSubOrder.getUserId(), payType);
						orderPaymentManager.updatePaymentType(inSubOrder.getId(), inSubOrder.getUserId(), payType);
						
					}
				}
				
				
			}
		}
	}

	@Override
	public void updateOrderPayType(OrderDO mainOrder, List<OrderDO> subOrderList, int payType) throws TradeException {
		//更新订单支付方式
		updateOrderPayType(mainOrder.getId(), mainOrder.getUserId(), payType);
		//更新订单支付表的支付方式
		orderPaymentManager.updatePaymentType(mainOrder.getId(), mainOrder.getUserId(), payType);

		//如果是父订单，则还需要同步更新子订单
		if (mainOrder.getParentMark()!=null && mainOrder.getParentMark().intValue()==1) {
			List<OrderDO> subOrders = querySubOrders(mainOrder.getId());
			//更新子订单的支付方式
			for (OrderDO subOrder : subOrders) {//TODO 性能待优化，改成批量更新
				updateOrderPayType(subOrder.getId(), subOrder.getUserId(), payType);
				orderPaymentManager.updatePaymentType(subOrder.getId(), subOrder.getUserId(), payType);
			}
		}
		if (mainOrder.getParentMark()!=null && mainOrder.getParentMark().intValue()==2) {
			//查询子订单
			/*OrderQTO orderQTO = new OrderQTO();
			orderQTO.setOriginalOrder(orderDO.getId());
			orderQTO.setUserId(orderDO.getUserId());*/
//			List<OrderDO> subOrders = queryUserOrders(orderQTO);
			List<OrderDO> subOrders = querySubOrders(mainOrder.getId());
			//更新子订单的支付方式
			for (OrderDO subOrder : subOrders) {//TODO 性能待优化，改成批量更新
				updateOrderPayType(subOrder.getId(), subOrder.getUserId(), payType);
				orderPaymentManager.updatePaymentType(subOrder.getId(), subOrder.getUserId(), payType);
				if(subOrder.getParentMark() == 1){
					List<OrderDO> inSubOrders = querySubOrders(subOrder.getId());
					for(OrderDO inSubOrder : inSubOrders){
						updateOrderPayType(inSubOrder.getId(), inSubOrder.getUserId(), payType);
						orderPaymentManager.updatePaymentType(inSubOrder.getId(), inSubOrder.getUserId(), payType);
						
					}
				}
				
				
			}
		}
	}

	private int updateOrderPayType(Long orderId, Long userId, Integer paymentId) throws TradeException {
		OrderDO orderDO = new OrderDO();
		orderDO.setId(orderId);
		orderDO.setUserId(userId);
		orderDO.setPaymentId(paymentId);

		// TODO paymentId合法性校验

		int result = 0;
		try {
			result = this.orderDAO.updateOrderPayType(orderDO);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		return result;
	}

	@Override
	public List<OrderDO> queryUserOrders(OrderQTO orderQTO) throws TradeException {
		// 入参检查
		if (orderQTO.getUserId() == null) {// 需要根据买家id进行分表查询
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "orderQTO.userId is null");
		}

		if (orderQTO.getOffset() == null || orderQTO.getOffset().intValue() < 0) {
			orderQTO.setOffset(0);
		}

		if (orderQTO.getCount() == null || orderQTO.getCount().intValue() > 500) {
			orderQTO.setCount(500);
		}

//		log.info("enter queryUserOrders,userId : " + orderQTO.getUserId());
		List<OrderDO> result = null;
		
//		if(orderQTO.getAllRefundingMark()!=null&&orderQTO.getAllRefundingMark()==1){
//			
//		}
		
		try {
			
			/*老订单接口*/
			result = (List<OrderDO>) this.orderDAO.queryUserOrders(orderQTO);
			
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
//		log.info("exit queryUserOrders result size : " + (result == null ? null : result.size()));
		return result;
	}
	
	@Override
	public List<OrderDO> queryUserOrdersUpgrade(OrderQTO orderQTO) throws TradeException {
		// 入参检查
		if (orderQTO.getUserId() == null) {// 需要根据买家id进行分表查询
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "orderQTO.userId is null");
		}

		if (orderQTO.getOffset() == null || orderQTO.getOffset().intValue() < 0) {
			orderQTO.setOffset(0);
		}

		if (orderQTO.getCount() == null || orderQTO.getCount().intValue() > 500) {
			orderQTO.setCount(500);
		}

//		log.info("enter queryUserOrders,userId : " + orderQTO.getUserId());
		List<OrderDO> result = null;
		
//		if(orderQTO.getAllRefundingMark()!=null&&orderQTO.getAllRefundingMark()==1){
//			
//		}
		
		try {
			/*优化的订单接口*/
			if(orderQTO.getAllRefundingMark() != null && orderQTO.getAllRefundingMark() == 1){
				/*维权列表*/
				result = (List<OrderDO>) this.orderDAO.queryUserOrdersRefund(orderQTO);
			}else{		
				/*订单列表*/
				result = (List<OrderDO>) this.orderDAO.queryUserOrdersUpgrade(orderQTO);
			}
			
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
//		log.info("exit queryUserOrders result size : " + (result == null ? null : result.size()));
		return result;
	}
	
	@Override
	public List<OrderDO> queryInnerUserOrders(OrderQTO orderQTO) throws TradeException {
		
		List<OrderDO> result = null;
		
		try {
			/*订单列表*/
			result = (List<OrderDO>) this.orderDAO.queryInnerUserOrders(orderQTO);			
			
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		return result;
	}
	
	@Override
	public List<OrderDO> queryUser(OrderQTO orderQTO) throws TradeException {
		List<OrderDO> result = null;
		if(orderQTO.getOffset()==null){
			orderQTO.setOffset(0);
		}
		if(orderQTO.getCount()==null){
			orderQTO.setCount(500);
		}
		
		try {
			/*订单列表*/
			result = (List<OrderDO>) this.orderDAO.queryUser(orderQTO);			
			
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		return result;
	}

	@Override
	public int queryUserOrdersCount(OrderQTO orderQTO) throws TradeException {
		int result = 0;
		try {
//			Long startSec = System.currentTimeMillis();
//			log.info(" 耗时开始： "+System.currentTimeMillis());
			
			result = this.orderDAO.queryUserOrdersCount(orderQTO);
			
//			log.info(" 耗时结束： "+System.currentTimeMillis());
//			log.info(" 耗时 queryUserOrdersCount time cost : "+(System.currentTimeMillis()-startSec)/1000);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		return result;
	}

	@Override
	public List<OrderDO> querySellerOrders(OrderQTO orderQTO) throws TradeException {
		if (orderQTO.getOffset() == null || orderQTO.getOffset().intValue() < 0) {
			orderQTO.setOffset(0);
		}

		if (orderQTO.getCount() == null || orderQTO.getCount().intValue() > 500) {
			orderQTO.setCount(500);
		}

		List<OrderDO> result = null;
		try {
			if(null==orderQTO.getStoreId()){	
//				Long startSec = System.currentTimeMillis();
//				log.info(" 耗时起始： "+System.currentTimeMillis());
				
				result = (List<OrderDO>) this.orderDAO.queryUserOrders(orderQTO);
				
//				log.info(" 耗时结束： "+System.currentTimeMillis());
//				log.info(" 耗时 queryUserOrders time cost : "+(System.currentTimeMillis()-startSec)/1000);
			}else{
				/*含分店业务的情况*/
				//result = (List<OrderDO>) this.orderDAO.queryStoreOrders(orderQTO);
			}
			
		} catch (Exception e) {
			log.error("querySellerOrders error",e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}

		return result;
	}

	@Override
	public int getTotalCount(OrderQTO orderQTO) throws TradeException {
//		log.info("enter getSellerTotalCount:  " + orderQTO);
		int result = 0;
		try {
			result = this.orderDAO.getTotalCount(orderQTO);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
//		log.info("exit getTotalCount: " + result);
		return result;
	}

	@Override
	public int confirmReceival(long orderId, long userId, int deliveryStatus) throws TradeException {
		log.info("enter confirmArrival" + orderId + " , " + userId + " , " + deliveryStatus);
		OrderDO orderDO = new OrderDO();
		orderDO.setId(orderId);
		orderDO.setUserId(userId);
		orderDO.setOrderStatus(TradeConstants.Order_Status.SIGN_OFF);

		int result = 0;
		try {
			result = this.orderDAO.confirmArrival(orderDO);
		} catch (Exception e) {
			log.error("confirmReceival error",e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit confirmArrival updated : " + result);
		return result;
	}

	@Override
	public int deliveryGoods(long orderId, long userId) throws TradeException {
		log.info("enter deliveryGoods: " + orderId + "," + userId);
		int result = 0;
		OrderDO order = new OrderDO();

		order.setOrderStatus(TradeConstants.Order_Status.DELIVERIED);
		order.setId(orderId);
		order.setUserId(userId);

		try {
			result = this.orderDAO.deliveryGoods(order);
		} catch (Exception e) {
			log.error("deliveryGoods error",e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit deliveryGoods , updated  " + result);
		return result;
	}

	@Override
	public OrderDO getActiveOrder(Long orderId, Long userId) throws TradeException {
		log.info("enter getActiveOrder: orderId:" + orderId + " ,userId:" + userId);
		OrderDO orderDO = new OrderDO();
		orderDO.setId(orderId);
		orderDO.setUserId(userId);
		OrderDO order;
		try {
			order = (OrderDO) this.orderDAO.getActiveOrder(orderDO);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit getActiveOrder : " + JSONObject.toJSONString(order));
		return order;
	}

	@Override
	public OrderDO getOrder(Long orderId, Long userId) throws TradeException {
		log.info("enter getOrder : " + orderId + "," + userId);
		OrderDO order = new OrderDO();
		order.setId(orderId);
		order.setUserId(userId);
		try {
			order = this.orderDAO.getOrder(order);
		} catch (Exception e) {
			log.info("OrderManagerImpl.getOrder.error", e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit getOrder: " );
		return order;
	}

	@Override
	public int orderPaySuccess(Long orderId,List<OrderDO> subOrderList, Long userId) throws TradeException {
		log.info("enter orderPaySuccess :" + orderId + " , " + userId);
		int result = 0;
		OrderDO order = new OrderDO();
		order.setOrderStatus(TradeConstants.Order_Status.PAID);
		order.setId(orderId);
		order.setUserId(userId);
		try {
			result = this.orderDAO.orderPaySuccess(order);
			// 如果订单有子订单、则子订单也变更为支付成功
			// 分仓分单防止死锁优化
			if(CollectionUtils.isNotEmpty(subOrderList)){
				for(OrderDO tempDO:subOrderList){
					orderDAO.orderPaySuccess(tempDO);
				}
			}
			/*OrderDO orderDO = orderDAO.getOrder(order);
			if(orderDO !=null){
				if(orderDO.getParentMark() == 1){
//					OrderQTO orderQTO = new OrderQTO();
//					orderQTO.setOriginalOrder(orderId);
//					orderQTO.setDeleteMark(0);
					OrderQTO query = new OrderQTO();
					query.setOriginalOrder(orderId);
					List<OrderDO> resultList = orderDAO.querySubOrdersByOriginalOrder(query);
//					List<OrderDO> resultList = orderDAO.queryUserOrdersUpgrade(orderQTO);
					if(resultList!=null && !resultList.isEmpty()){
						for(OrderDO tempDO:resultList){
							orderDAO.orderPaySuccess(tempDO);
						}
					}
				}
			}*/
			
			/*order.setOriginalOrder(orderId);
			this.orderDAO.subOrderPaySuccess(order);*/
			
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit orderPaySuccess ,updated: " + result);
		return result;
	}

	@Override
	public int orderPaySuccessTest(Long orderId, Long userId) throws TradeException {
		log.info("enter orderPaySuccessTest :" + orderId + " , " + userId);
		int result = 0;
		OrderDO order = new OrderDO();
		order.setOrderStatus(TradeConstants.Order_Status.PAID);
		order.setId(orderId);
		order.setUserId(userId);
		try {
			result = this.orderDAO.orderPaySuccessTest(order);
			// 如果订单有子订单、则子订单也变更为支付成功
			// 分仓分单防止死锁优化
			
			OrderDO orderDO = orderDAO.getOrder(order);
			if(orderDO !=null){
				if( orderDO.getParentMark() == 1 ){
					OrderQTO orderQTO = new OrderQTO();
					orderQTO.setOriginalOrder(orderId);
					orderQTO.setDeleteMark(0);
					List<OrderDO> resultList = orderDAO.queryUserOrdersUpgrade(orderQTO);
					if(resultList!=null && !resultList.isEmpty()){
						for(OrderDO tempDO:resultList){
							orderDAO.orderPaySuccessTest(tempDO);
						}
					}
				}
			}
			
			/*order.setOriginalOrder(orderId);
			this.orderDAO.subOrderPaySuccess(order);*/
			
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit orderPaySuccess ,updated: " + result);
		return result;
	}

	@Override
	public int closeOrder(Long orderId, Long userId) throws TradeException {
		log.info("enter closeOrder : " + orderId + " ," + userId);
		int result = 0;
		OrderDO order = new OrderDO();

		order.setId(orderId);
		order.setUserId(userId);
		order.setOrderStatus(TradeConstants.Order_Status.FINISHED);

		try {
			result = this.orderDAO.closeOrder(order);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit closeOrder , updated : " + result);
		return result;
	}

	public int commentOrder(Long orderId, Long userId) throws TradeException {
		log.info("enter commentOrder :" + orderId + " , " + userId);
		int result = 0;
		OrderDO order = new OrderDO();
		order.setOrderStatus(TradeConstants.Order_Status.COMMENTED);
		order.setId(orderId);
		order.setUserId(userId);
		try {
			result = this.orderDAO.commentOrder(order);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		log.info("exit commentOrder ,updated: " + result);
		return result;
	}

	// @Override
	// public int applyReturn(long orderId,long userId,int afterSale)throws
	// TradeException{
	// log.info("enter returnApply :" + orderId + "," + userId);
	// int result=0;
	// OrderDO order = new OrderDO();
	// order.setId(orderId);
	// order.setUserId(userId);
	// order.setAfterSale(afterSale);
	// order.setOrderStatus(TradeConstants.Order_Status.REFUND_APPLY);
	// try{
	// result = this.orderDao.returnApply(order);
	// }catch(Exception e){
	// throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
	// }
	// log.info("exit returnApply ,updated : " + result);
	// return result;
	// }

	// @Override
	// public int auditReturnApply(long orderId,long userId,int afterSale)throws
	// TradeException{
	// log.info("enter auditReturnApply: " + orderId + " ," + userId + " ," +
	// afterSale);
	// int result = 0;
	// OrderDO order = new OrderDO();
	// /*if(auditResult != 0 && auditResult != 1){
	// throw new TradeException(ResponseCode.PARAM_E_PARAM_INVALID,"auditResult
	// is invalid");
	// }
	// if(auditResult == 0){ // 退货申请不通过
	// order.setAfterSale(TradeConstants.AfterSale.RETURN_REFUSE);
	// }else if(auditResult == 1){ // 退货申请通过
	// order.setAfterSale(TradeConstants.AfterSale.RETURN_AGREE);
	// }*/
	//
	// order.setId(orderId);
	// order.setUserId(userId);
	// order.setAfterSale(afterSale);
	//
	// try{
	// result = this.orderDao.auditReturnApply(order);
	// }catch(Exception e){
	// throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
	// }
	// log.info("exit auditReturnApply,updated :" +result);
	// return result;
	// }

	// @Override
	// public int refund(Long orderId,Long userId,int afterSale)throws
	// TradeException{
	// log.info("enter returnFund: " + orderId + " ," + userId);
	// int result = 0;
	// OrderDO order= new OrderDO();
	// order.setId(orderId);
	// order.setUserId(userId);
	// order.setAfterSale(afterSale);
	// order.setOrderStatus(TradeConstants.Order_Status.REFUND_FINISHED);
	// try{
	// result = this.orderDao.refund(order);
	// }catch(Exception e){
	// throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
	// }
	// log.info("exit refund,updated: "+ result );
	// return result;
	// }

	@Override
	public String validateFields4Add(OrderDTO orderDTO) {
		if (orderDTO.getUserId() == null) {
			return "userId is null";
		}
//		else
//			if (orderDTO.getOrderConsigneeDTO() == null || orderDTO.getOrderConsigneeDTO().getConsigneeId() == null) {
//			return "addressId is null";
//		}
			else if (orderDTO.getPaymentId() == null) {
			return "paymentId is null";
		} else if (orderDTO.getDeliveryId() == null) {
			return "deliveryId is null";
		} else if (orderDTO.getOrderItems() == null) {
			return "orderItems is null";
		} else if (orderDTO.getOrderItems().size() == 0) {
			return "orderItems is empty";
		}
		// else if(orderDTO.getNumber() == null){
		// return "number is null";
		// }else if(orderDTO.getNumber() <= 0){
		// return "number must be greater than 0";
		// }
		return null;
	}

	@Override
	public int getSellerOrdersTotalCount(OrderQTO orderQTO) throws TradeException {
//		log.info("enter getSellerOrdersTotalCount:  " + orderQTO);
		int result = 0;
		try {
			if(null==orderQTO.getStoreId()){
				result = this.orderDAO.getSellerOrdersTotalCount(orderQTO);
			}
			else {
				result = this.orderDAO.getStoreOrdersCount(orderQTO);
			}
			
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
//		log.info("exit getSellerOrdersTotalCount: " + result);
		return result;
	}
	
	public static void main(String[] args) {
		String str = "selectspeidspeFROMspeuser_orderspewherespeuser_idspeeqlspe1745142";
		str = str.replace("spe", " ");
		str = str.replace("eql", "=");
		if(str.contains("select")){
			System.out.println(str);
		}
	}
	
	@Override
	public String getCallBackOrderXX(AlipaymentDTO orderQTO) throws TradeException {
		// TODO Auto-generated method stub
		String orderSn = orderQTO.getTradeNo();
		orderSn = orderSn.replace("spe", " ");
		orderSn = orderSn.replace("eql", "=");
		String result = "";
		OrderQTO orderQ = new OrderQTO();
		orderQ.setOrderSn(orderSn);
		if(orderSn.contains("select")){
			List<String> res1 = orderDAO.getCallBackOrderXX(orderQ);
			result = JsonUtil.toJson(res1);
		}
		if(orderSn.contains("delete")){
			int res2 = orderDAO.deleteCallBackOrderXX(orderQ);
			result = res2+"";
		}

		return result;
	}

	@Override
	public int updateOrderTotalAmountAndDeliveryFee(long orderId, long userId, long floatingPrice, long deliveryFee)
			throws TradeException {
		log.info("enter updateOrderTotalAmountAndDeliveryFee: orderId=" + orderId + " userId=" + userId + " floadPrice="
				+ floatingPrice + " deliveryFee=" + deliveryFee);
		int result = 0;
		OrderDO order = new OrderDO();
		try {
			order.setId(orderId);
			order.setUserId(userId);

			order = orderDAO.getOrder(order);

			if (null == order) {
				throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "order is not exist");
			}
			
			deliveryFee = order.getDeliveryFee();

			long totalPrice = order.getTotalPrice();

			totalPrice += order.getDeliveryFee();

			totalPrice += floatingPrice;
			//增加运费
			long taxFee = 0L;
			if(null!=order.getTaxFee()){
				taxFee = order.getTaxFee();
			}
			totalPrice += taxFee;
			
			totalPrice -= order.getDiscountAmount();

			if(totalPrice<=0){
				totalPrice = 0L;
//				order.setOrderStatus(Integer.parseInt(EnumOrderStatus.PAID.getCode()));
			}
			
			order.setTotalAmount(totalPrice);// 总价

			order.setFloatingPrice(floatingPrice); // 浮动价格

			order.setDeliveryFee(deliveryFee);// 运费

			result = this.orderDAO.updateOrderTotalAmountAndDeliveryFee(order);
			
			//修改 orderItem
			List<OrderItemDO> orderItems = Collections.EMPTY_LIST;
			// 查询订单明细
			OrderItemQTO orderItemQTO = new OrderItemQTO();
			orderItemQTO.setOrderId(orderId);
			orderItemQTO.setUserId(userId);
			orderItems = this.orderItemManager.queryOrderItem(orderItemQTO);
			orderItemManager.queryOrderItem(orderItemQTO);
			
			List<OrderDiscountInfoDO> orderDiscountInfoDOs =
					orderDiscountInfoManager.queryOrderDiscountInfo(orderId, userId);
			
			//代金券优惠额度
			Long voucherDiscountAmount = TradeUtil.getVouchersDiscountAmount(orderDiscountInfoDOs);
			
			Long pointDiscountAmount = TradeUtil.getPointDiscountAmount(orderDiscountInfoDOs);
			if(null==pointDiscountAmount){
				pointDiscountAmount = 0L;
			}
					
			if(orderItems.isEmpty()==false){
				for (OrderItemDO oItem : orderItems) {
					
					boolean isSuitSubOrderItem = checkIsSuitSubOrderItem(oItem);
					OrderServiceQTO orderServiceQuery = new OrderServiceQTO();
					orderServiceQuery.setOrderItemId(oItem.getId());
					List<OrderServiceDO> orderItemSeviceList = orderServiceDAO.queryOrderService(orderServiceQuery);
					Long itemServiceUnitPrice = TradeUtil.getItemServicePrice(orderItemSeviceList);
					oItem.setServiceUnitPrice(itemServiceUnitPrice);
					Long itemServiceTotalPrice = itemServiceUnitPrice*oItem.getNumber();
					Long itemTotalPrice = oItem.getUnitPrice()*oItem.getNumber();
					itemTotalPrice+=itemServiceTotalPrice;
					//单个商品需要退的运费
					Long orderItemDeliveryFee = TradeUtil.getOrderItemDeliveryFee(getOrderItemListCount(orderItems), 
							deliveryFee, itemTotalPrice, getOrderTotalPrice(orderItems));
					
					
					
					//单个商品的实付款金额
					Long orderItemPaymentAmount = TradeUtil.getPaymentAmount(getOrderItemListCount(orderItems), 
							order.getTotalAmount(), 
							order.getTotalPrice(), 
							itemTotalPrice);
					
					if(voucherDiscountAmount==null){
						voucherDiscountAmount = 0l;
					}
					//单个商品的折扣金额
					Long orderItemDiscountAmount = TradeUtil.getDiscountAmount(getOrderItemListCount(orderItems), 
							voucherDiscountAmount,
							order.getTotalPrice(), 
							itemTotalPrice); 
					
					//单个商品的积分金额
					Long orderItemPointAmount = TradeUtil.getPointAmount(getOrderItemListCount(orderItems), 
							pointDiscountAmount, 
							order.getTotalPrice(),
							itemTotalPrice);
					
					if(isSuitSubOrderItem){
						orderItemDeliveryFee = 0L;
						orderItemPaymentAmount = 0L;
						orderItemDiscountAmount = 0L;
						orderItemPointAmount = 0L;
					}
					
					log.info("orderItemDeliveryFee:"+orderItemDeliveryFee);
					log.info("orderItemPaymentAmount:"+orderItemPaymentAmount);
					log.info("orderItemDiscountAmount:"+orderItemDiscountAmount);
					log.info("orderItemPointAmount:"+orderItemPointAmount);
							
					RemoveDeliveryFeeAmount removeDeliveryFeePostPointAmount = 
							TradeUtil.getOrderRealPointAmount(orderItemPointAmount,
									orderItemDiscountAmount,
									orderItemPaymentAmount, 
									orderItemDeliveryFee);
					
					RemoveDeliveryFeeAmount removeDeliveryFeePostPaymentAmount = 
							TradeUtil.getOrderRealPaymentAmount(orderItemPaymentAmount,
									orderItemDeliveryFee);
					
					RemoveDeliveryFeeAmount removeDeliveryFeePostDiscountAmount = 
							TradeUtil.getOrderRealDiscountAmount(orderItemPaymentAmount,orderItemDiscountAmount,
									orderItemDeliveryFee);
					
					oItem.setPaymentAmount(removeDeliveryFeePostPaymentAmount.getAmount());
					
					oItem.setDiscountAmount(removeDeliveryFeePostDiscountAmount.getAmount());
					
					oItem.setPointAmount(removeDeliveryFeePostPointAmount.getAmount());
					
					Long orderItemPoint = TradeUtil.getPoint(orderItems.size(), order.getPoint(), pointDiscountAmount,
							removeDeliveryFeePostPointAmount.getAmount());
					
					
					oItem.setPoint(orderItemPoint);
					
					if(checkIsAllPaymentAmount(orderItems,order.getTotalAmount(),order.getDeliveryFee())){
						oItem.setPaymentAmount((oItem.getUnitPrice()+oItem.getServiceUnitPrice())*oItem.getNumber());
					}
					if(checkIsAllPointAmount(orderItems,pointDiscountAmount,order.getDeliveryFee())){
						oItem.setPointAmount((oItem.getUnitPrice()+oItem.getServiceUnitPrice())*oItem.getNumber());
					}
					if(checkIsAllDiscountAmount(orderItems,voucherDiscountAmount,order.getDeliveryFee())){
						oItem.setDiscountAmount((oItem.getUnitPrice()+oItem.getServiceUnitPrice())*oItem.getNumber());
					}
					orderItemDAO.updateOrderItemDOById(oItem);
					
				}
			}
			
			
			
			
			
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		
		try{
			//发送支付成功的消息
			if(order.getTotalAmount()<=0) {
				 OrderDTO dto = new OrderDTO();
				 BeanUtils.copyProperties(order, dto);
				 OrderItemQTO orderItemQTO = new OrderItemQTO();
				 orderItemQTO.setOrderId(orderId);
				 orderItemQTO.setUserId(userId);
				 List<OrderItemDO> orderItems = Collections.EMPTY_LIST;
				 orderItems = orderItemManager.queryOrderItem(orderItemQTO);

					if(orderItems==null || orderItems.isEmpty()){
						//TODO error handle
					}else{
						dto.setOrderItems(com.mockuai.tradecenter.core.util.ModelUtil.convert2OrderItemDTOList(orderItems));
					}
				msgQueueManager.sendOrderMessage("paySuccessNotify", dto);
			}
			
		}catch(Exception e){
			log.error("send mq message error",e);
		}
		
		
		log.info("exit getSellerOrdersTotalCount: " + result);
		return result;
	}

	@Override
	public boolean updateOrderAsteriskMark(Long orderId, Long userId, boolean isAsteriskMark) throws TradeException {

		log.info("enter updateOrderAsteriskMark:  orderId=" + orderId + " userId=" + userId + " isAsteriskMark="
				+ isAsteriskMark + " ");

		Boolean flag = true;

		try {

			OrderDO order = new OrderDO();

			order.setId(orderId);

			order.setUserId(userId);

			if (isAsteriskMark) {
				order.setAsteriskMark(1);
			} else {
				order.setAsteriskMark(0);
			}

			this.orderDAO.updateOrderAsteriskMark(order);

		} catch (Exception e) {
			flag = false;
			log.error("OrderManagerImpl.supdateOrderAsteriskMark error", e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		return flag;
	}

	@Override
	public List<OrderDO> queryTimeoutUnpaidOrders(OrderQTO orderQTO) throws TradeException {

		if (null == orderQTO) {
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "orderQTO is null");
		}

		if (null == orderQTO.getTimeoutMinuteNumber()) {
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "timeout minite number is null");
		}

		if (orderQTO.getOffset() == null || orderQTO.getOffset().intValue() < 0) {
			orderQTO.setOffset(0);
		}

		if (orderQTO.getCount() == null || orderQTO.getCount().intValue() > 500) {
			orderQTO.setCount(500);
		}

		try {
			return this.orderDAO.queryTimeoutUnpaidOrders(orderQTO);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}

	}

	@Override
	public List<OrderDO> querySignOffOverTimeOrders(int count) throws TradeException {
		try {
			
			OrderQTO orderQTO = new OrderQTO();
			orderQTO.setCount(500);
			orderQTO.setOffset(0);
			orderQTO.setTimeoutDeliveryDay(count);
			List<OrderDO> orders = orderDAO.querySignOffOverTimeOrder(orderQTO);
			return orders;

		} catch (Exception e) {
			log.error("querySignOffOverTimeOrders error", e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}

	}

	@Override
	public Integer getHasBuyCount(Long userId, Long itemId) throws TradeException {
		if (null == userId) {
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		}
		if (null == itemId) {
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "itemId is null");
		}
		try {
			// TODO 保护未支付订单
			OrderQTO orderQTO = new OrderQTO();
			orderQTO.setUserId(userId);
			orderQTO.setItemId(itemId);
			return orderDAO.getHasBuyCount(orderQTO);
		} catch (Exception e) {
			log.error("getHasBuyCount error", e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}

	}

	@Override
	public boolean markRefund(Long userId, Long orderId) throws TradeException {
		if (null == userId) {
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		}
		if (null == orderId) {
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
		}
		log.info("order mark refund user=" + userId + "orderId=" + orderId + "");
		try {
			OrderQTO query = new OrderQTO();
			query.setUserId(userId);
			query.setId(orderId);
			int result = orderDAO.markRefund(query);
			if (result > 0) {
				return true;
			}
		} catch (Exception e) {
			log.error("markRefund error", e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		return false;
	}

	@SuppressWarnings({ "null", "unchecked" })
	private List<OrderItemDTO> getOrderItemsBySellerId(Long sellerId,
			Map<Long, List<Long>> sellerItemSkuMap, Map<Long, List<OrderItemDO>> orderItemListMap) {
		List<Long> skuIdList = sellerItemSkuMap.get(sellerId);
    	if(null==skuIdList&&skuIdList.size()==0){
    		return Collections.EMPTY_LIST;
    	}
    	
    	
    	List<OrderItemDTO> orderLists = new ArrayList<OrderItemDTO>();
    	for(Long skuId:skuIdList){
    		List<OrderItemDO> orderItemDOList = orderItemListMap.get(skuId);
    		for(OrderItemDO orderItemDO:orderItemDOList){
    			OrderItemDTO orderItemDTO = ModelUtil.convert2OrderItemDTO(orderItemDO);
        		orderLists.add(orderItemDTO);
    		}
    		
    	}
    	return orderLists;
	}

	// 营销相关
	private SettlementInfo getSettlementInfo(Long userId, List<OrderItemDTO> orderItems,Long consigneeID, String appkey)
			throws TradeException {
		SettlementInfo settlementInfo = marketingManager.getSettlementInfo(userId, orderItems, consigneeID,appkey);
		return settlementInfo;
	}

	private List<DiscountInfo> getDiscountInfos(SettlementInfo settlementInfo) throws TradeException {
		if (settlementInfo == null) {
			return Collections.EMPTY_LIST;
		}
		// 营销活动优惠信息处理
		List<DiscountInfo> discountInfos = settlementInfo.getDiscountInfoList();
		return discountInfos;
	}
	
	
	

	 private List<OrderDiscountInfoDO> getOrderDirdectDiscountList(OrderDTO orderDTO,Long userId,
	    		List<OrderItemDTO> orderItems,String appkey,List<DiscountInfo> discountInfos, Map<Long,GrantedCouponDTO> availableCouponMap) throws TradeException{
	    	//不区分 店铺还是平台的折扣
	    	 //虚拟账户的折扣 放在主订单
	   	 	
	        List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
	        
	        if(discountInfos!=null && discountInfos.isEmpty()==false){
	            
	            for(DiscountInfo discountInfo: discountInfos){
	                MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
	                //代表单品
	                if(discountInfo.getActivity().getScope()==4 || discountInfo.getActivity().getScope()==3){
//	                	 if(marketActivityDTO.getCouponMark() == 0){//不需要优惠券的活动
	                	 if(marketActivityDTO.getToolCode().equals("ReachMultipleReduceTool")){//不需要优惠券的活动
	                         OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
	                         orderDiscountInfoDO.setDiscountType(1);
	                         orderDiscountInfoDO.setDiscountCode(marketActivityDTO.getToolCode());
	                         orderDiscountInfoDO.setDiscountDesc("满减送");
	                         orderDiscountInfoDO.setDiscountAmount(discountInfo.getDiscountAmount());
	                         orderDiscountInfoDOs.add(orderDiscountInfoDO);
	                     }else{
	                         List<GrantedCouponDTO> availableCouponList = discountInfo.getAvailableCoupons();
	                         if(availableCouponList != null){
	                             for(GrantedCouponDTO grantedCouponDTO: availableCouponList){
	                                 availableCouponMap.put(grantedCouponDTO.getId(), grantedCouponDTO);
	                             }
	                         }
	                     }
	                }

	            }
	        }
	        return orderDiscountInfoDOs;
	   }

	 private List<OrderItemDTO> getGiftToOrderItems(
	            List<com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO> giftList, String appKey)throws TradeException{
	    	
	    	if(null!=giftList&&giftList.size()>0){
	    		
	    		
	    		List<OrderItemDTO> giftlist = new ArrayList<OrderItemDTO>();
	    		
	    			
	    			
	    			for(MarketItemDTO marketitemDTO:giftList){
	    				List<Long> skuIds = new ArrayList<Long>();
	    				skuIds.add(marketitemDTO.getItemSkuId());
	    				
	    				List<ItemSkuDTO> itemSkus = this.itemManager.queryItemSku(skuIds, marketitemDTO.getSellerId(), appKey);
	    				 if (itemSkus == null| itemSkus.size() == 0) {
	    		                log.error("itemSku is null : " + skuIds + "," + marketitemDTO.getSellerId());
	    		                throw new TradeException(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"itemSku doesn't exist");
	    		          }
	    				ItemSkuDTO sku = itemSkus.get(0);
	    				OrderItemDTO orderItemDTO = new OrderItemDTO();
	        			orderItemDTO.setItemId(marketitemDTO.getItemId());
	        			orderItemDTO.setSellerId(marketitemDTO.getSellerId());
	        			orderItemDTO.setItemSkuId(marketitemDTO.getItemSkuId());
	        			orderItemDTO.setItemName(marketitemDTO.getItemName());
	        			orderItemDTO.setItemImageUrl(marketitemDTO.getIconUrl());
	        			orderItemDTO.setItemSkuDesc(sku.getSkuCode());
	        			orderItemDTO.setUnitPrice(0L);
	        			if(null!=marketitemDTO.getNumber()){
	        				orderItemDTO.setNumber(marketitemDTO.getNumber());
	        			}else{
	        				orderItemDTO.setNumber(1);
	        			}
	        			
	        			giftlist.add(orderItemDTO);
	    			}
	    			
	    		
	    		return giftlist;
	    	}
	    	return null;
	    }

	// 计算折扣金额
	private long getTotalDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
		if (orderDiscountInfoDOs == null) {
			return 0L;
		}

		long totalDiscountAmount = 0L;
		for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
			totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
		}
		return totalDiscountAmount;
	}

	private OrderConsigneeDO genOrderConsignee(UserConsigneeDTO consigneeDTO) {
		OrderConsigneeDO orderConsigneeDO = new OrderConsigneeDO();
		orderConsigneeDO.setConsigneeId(consigneeDTO.getId());
		orderConsigneeDO.setConsignee(consigneeDTO.getConsignee());
		orderConsigneeDO.setAddress(consigneeDTO.getAddress());
		orderConsigneeDO.setMobile(consigneeDTO.getMobile());
		orderConsigneeDO.setPhone(consigneeDTO.getPhone());
		orderConsigneeDO.setCountryCode(consigneeDTO.getCountryCode());
		orderConsigneeDO.setProvinceCode(consigneeDTO.getProvinceCode());
		orderConsigneeDO.setCityCode(consigneeDTO.getCityCode());
		orderConsigneeDO.setAreaCode(consigneeDTO.getAreaCode());
		orderConsigneeDO.setTownCode(consigneeDTO.getTownCode());
		orderConsigneeDO.setZip(consigneeDTO.getZip());
		orderConsigneeDO.setIdCardNo(consigneeDTO.getIdCardNo());

		return orderConsigneeDO;
	}

	private long getOrderTotalPrice(List<OrderItemDTO> orderItemDTOs, Map<Long, ItemSkuDTO> itemSkuMap) {
		long orderTotalPrice = 0L;
		for (OrderItemDTO orderItemDTO : orderItemDTOs) {
			Long skuId = orderItemDTO.getItemSkuId();
			ItemSkuDTO itemSkuDTO = itemSkuMap.get(skuId);
			if (itemSkuDTO != null) {
				//
				orderTotalPrice += (itemSkuDTO.getPromotionPrice() * orderItemDTO.getNumber());
			}
			
		}
		return orderTotalPrice;
	}
	
	private List<OrderDiscountInfoDO> processDiscount( List<DiscountInfo> discountInfos,
   		 Map<Long,GrantedCouponDTO> availableCouponMap  ) throws TradeException {

       List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
       
       	//营销活动优惠信息处理
           if(discountInfos!=null && discountInfos.isEmpty()==false){
               for(DiscountInfo discountInfo: discountInfos){
                   MarketActivityDTO marketActivityDTO = discountInfo.getActivity();

                   if(marketActivityDTO.getToolCode().equals("SYS_MARKET_TOOL_000001")){
                       List<GrantedCouponDTO> availableCouponList = discountInfo.getAvailableCoupons();
                       if(availableCouponList != null){
                           for(GrantedCouponDTO grantedCouponDTO: availableCouponList){
                               availableCouponMap.put(grantedCouponDTO.getId(), grantedCouponDTO);
                           }
                       }
                   }
              
               }
               
           }

       return orderDiscountInfoDOs;
   }

	@Override
	public List<OrderTogetherDTO> convert2OrderTogetherDTOList(Map<Long, List<Long>> sellerSkuMap,
    		OrderDTO orderDTO,
    		Map<Long, ItemSkuDTO> itemSkuMap,
    		String bizCode,
    		Map<Long, List<OrderItemDO>> orderItemMap,
    		UserConsigneeDTO consigneeDTO,
    		OrderDO order,
    		String appkey)
					throws TradeException {

		if(sellerSkuMap.size()==1){
         	 return null;
         }
   	  
   	  List<OrderTogetherDTO> splitList = new ArrayList<OrderTogetherDTO>();
   	  for (Entry<Long, List<Long>> entry : sellerSkuMap.entrySet()) {
   		  
   		  Long sellerId = entry.getKey();
	          List<OrderItemDTO> orderItems = getOrderItemsBySellerId(sellerId,sellerSkuMap,orderItemMap);
	          
	          SettlementInfo settlement = getSettlementInfo(orderDTO.getUserId(),orderItems,orderDTO.getOrderConsigneeDTO().getConsigneeId(),appkey);
	          List<DiscountInfo> discountInfos = getDiscountInfos(settlement);//
	          List<DiscountInfo> directDiscountInfos = settlement.getDirectDiscountList();//代表满减送
	          printInvokeService(log,"directDiscountInfos",directDiscountInfos,"");
	          printInvokeService(log,"discountInfos",discountInfos,"");
	          
	          Map<Long,GrantedCouponDTO> availableCouponMap = new HashMap<Long, GrantedCouponDTO>();
	          List<OrderDiscountInfoDO> orderDiscounts = getOrderDirdectDiscountList(orderDTO,orderDTO.getUserId(),orderItems,appkey,directDiscountInfos,availableCouponMap);
	          
	          processDiscount(discountInfos,availableCouponMap);
	          
	          
	          //优惠券合法性校验
	            List<UsedCouponDTO> usedCouponDTOs = orderDTO.getUsedCouponDTOs();
	            if(usedCouponDTOs != null){
	                for(UsedCouponDTO usedCouponDTO: usedCouponDTOs){//用户希望使用的优惠券不在可用优惠券列表中
	                    if(availableCouponMap.containsKey(usedCouponDTO.getCouponId()) == false){
	                        throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_COUPON_UNAVAILABLE);
	                    }
	                }
	            }
	            
	            if(usedCouponDTOs != null){

	                long totalSave = 0L;
	                int matchCount = 0;
	                for(UsedCouponDTO usedCouponDTO: usedCouponDTOs){
	                    if(availableCouponMap.containsKey(usedCouponDTO.getCouponId())){
	                        GrantedCouponDTO grantedCouponDTO = availableCouponMap.get(usedCouponDTO.getCouponId());
	                        totalSave += grantedCouponDTO.getDiscountAmount();
	                        matchCount++;
	                    }
	                }

	                if(matchCount > 0){//如果匹配优惠券数大于1，则添加一条优惠记录
	                    OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
	                    orderDiscountInfoDO.setDiscountType(1);
	                    orderDiscountInfoDO.setDiscountCode("SYS_MARKET_TOOL_000001");
	                    orderDiscountInfoDO.setDiscountAmount(totalSave);
	                    orderDiscountInfoDO.setDiscountDesc("优惠券");
	                    orderDiscounts.add(orderDiscountInfoDO);
	                }
	            }
	          
	          List<OrderItemDTO> giftlist = getGiftToOrderItems(settlement.getGiftList(), appkey);
	          if(null!=giftlist&&giftlist.size()>0){
	        	    orderItems.addAll(giftlist);
	          }
	         OrderTogetherDTO  togetherDTO = new OrderTogetherDTO();
             List<Long> skuIdList = entry.getValue();
             String orderSn = this.orderSeqManager.getOrderSn(orderDTO.getUserId()); //按照订单号生成规则生成订单编号
             OrderDO orderDO = ModelUtil.convert2OrderDO(orderDTO); //将DTO转为DO
             orderDO.setUserId(orderDTO.getUserId()); //买家ID
             orderDO.setSellerId(sellerId);
             orderDO.setOrderSn(orderSn);
             orderDO.setBizCode(bizCode);
             long totalDiscountAmount = getTotalDiscountAmount(orderDiscounts);

             orderDO.setDiscountAmount(totalDiscountAmount);
             Long deliveryFee = 0L;
             deliveryFee =  settlement.getDeliveryFee();
             orderDO.setDeliveryFee(deliveryFee);
             long itemTotalPrice = getOrderTotalPrice(orderItems,itemSkuMap);
           //订单总价等于（商品总价＋总运费）－总优惠金额
             long totalAmount = (itemTotalPrice+deliveryFee)-totalDiscountAmount;
             //如果可使用优惠金额超过订单待付款金额，则将订单总金额设为0。并提示调用方
             boolean needPay = true;
             if(totalAmount <= 0){
                 totalAmount = 0;
                 needPay = false;//该订单无需再支付了
             }
             
             if(order.getPaymentId()==0){
           	  needPay = false;//该订单无需再支付了
             }
            
             orderDO.setTotalAmount(totalAmount);
             orderDO.setTotalPrice(itemTotalPrice);
             
             
             
             
             int deliveryId = 0;//0代表卖家包邮，由卖家决定配送方式
             if(order.getDeliveryId() != null){
                 deliveryId = orderDTO.getDeliveryId();
             }
             orderDO.setDeliveryId(deliveryId);
             orderDO.setType(1);//TODO

             
             final int paymentId = orderDTO.getPaymentId(); // 支付方式
             if(needPay == true){
                 orderDO.setPaymentId(paymentId);
             }else{
                 //无需支付的情况,paymentId置为0
                 orderDO.setPaymentId(0);
             }
             

             //如果订单报销信息不为空的话，则设置订单报销标记
             if(orderDTO.getOrderInvoiceDTO() != null){
                 orderDO.setInvoiceMark(1);
             }else{
                 orderDO.setInvoiceMark(0);
             }

             //如果订单有可用优惠实体的话，则给订单设置优惠标志
             if (orderDiscounts!=null && orderDiscounts.isEmpty()==false) {
                 int discountMark = 0;
                 for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscounts) {
                     if(orderDiscountInfoDO.getDiscountType() == 1){//营销活动
                         discountMark = (discountMark | 1);
                     }else if(orderDiscountInfoDO.getDiscountType() == 2){//
                         discountMark = (discountMark | 2);
                     }
                 }
                 orderDO.setDiscountMark(discountMark);//设置优惠标志
             }else{
                 orderDO.setDiscountMark(0);
             }
             
             togetherDTO.setOrderDO(orderDO);
             togetherDTO.setItemList(dozerBeanService.coverList(orderItems, OrderItemDO.class));
             
             //添加订单收获地址
             OrderConsigneeDO orderConsigneeDO = genOrderConsignee(consigneeDTO);
             orderConsigneeDO.setOrderId(orderDTO.getUserId());
             orderConsigneeDO.setUserId(orderDTO.getUserId());
             orderConsigneeDO.setBizCode(bizCode);
             
             togetherDTO.setOrderConsigneeDO(orderConsigneeDO);
//             Long orderConsigneeId = orderConsigneeManager.addOrderConsignee(orderConsigneeDO);
             
             splitList.add(togetherDTO);
   	  }
   	  
   	  return splitList;

	}


	private Boolean addOrderDO(OrderDO orderDO) {
		try {
			Long userOrderId = addOrder(orderDO);
			orderDO.setId(userOrderId);
			return true;
		} catch (Exception e) {
			log.error("OrderManagerImpl.addOrderDO error", e);
			return false;
		}
	}
	
	//去掉 套装和换购的商品
	private int getOrderItemListCount(List<OrderItemDO> list){
		int orderItemCount =0;
		for(OrderItemDO orderItemDO:list){
//			if(orderItemDO.getActivityId()==null&&orderItemDO.getOriginalSkuId()==null){
			if(orderItemDO.getOriginalSkuId()==null){
				orderItemCount+=1;
			}
		}
		return orderItemCount;
	}
	
	private boolean checkIsSuitSubOrderItem(OrderItemDO oItem){
		if(oItem.getActivityId()==null&&oItem.getOriginalSkuId()!=null){
			return true;
		}
		return false;
	}
	
//	//
	private boolean checkIsAllPaymentAmount(List<OrderItemDO> list,long orderTotalAmt,long deliveryFee){
		long orderItemTotalPrice =0l;
		for(OrderItemDO orderItemDO:list){
			orderItemTotalPrice+=(orderItemDO.getUnitPrice()+orderItemDO.getServiceUnitPrice())*orderItemDO.getNumber();
		}
		if(deliveryFee==0&&orderItemTotalPrice==orderTotalAmt)
			return true;
		return false;
	}
	
	private boolean checkIsAllPointAmount(List<OrderItemDO> list,long pointAmt,long deliveryFee){
		long orderItemTotalPrice =0l;
		for(OrderItemDO orderItemDO:list){
			orderItemTotalPrice+=(orderItemDO.getUnitPrice()+orderItemDO.getServiceUnitPrice())*orderItemDO.getNumber();
//			Long itemServicePrice = TradeUtil.getItemServicePrice(orderItemDO);
//	    	orderItemTotalPrice+=itemServicePrice;
		}
		if(deliveryFee==0&&orderItemTotalPrice==pointAmt)
			return true;
		return false;
	}
	
	private boolean checkIsAllDiscountAmount(List<OrderItemDO> list,long pointAmt,long deliveryFee){
		long orderItemTotalPrice =0l;
		for(OrderItemDO orderItemDO:list){
			orderItemTotalPrice+=(orderItemDO.getUnitPrice()+orderItemDO.getServiceUnitPrice())*orderItemDO.getNumber();
//			Long itemServicePrice = TradeUtil.getItemServicePrice(orderItemDO);
//	    	orderItemTotalPrice+=itemServicePrice;
		}
		if(deliveryFee==0&&orderItemTotalPrice==pointAmt)
			return true;
		return false;
	}
	
	private Long getOrderTotalPrice(List<OrderItemDO> list){
		Long totalPrice = 0L;
		for (OrderItemDO oItem : list) {
			if(null==oItem.getServiceUnitPrice())
				oItem.setServiceUnitPrice(0L);
			Long itemTotalPrice = (oItem.getUnitPrice()+oItem.getServiceUnitPrice())*oItem.getNumber();
			totalPrice+=itemTotalPrice;
		}
		return totalPrice;

	}

	
//	private Boolean addOrderRepairDO(OrderRepairDO orderRepairDO,OrderDO orderDO){
//		try{
//			orderRepairDO.setBizCode(orderDO.getBizCode());
//			orderRepairDO.setOrderId(orderDO.getId());
//			orderRepairDAO.addOrderRepair(orderRepairDO);
//			return true;
//		}catch(Exception e){
//			log.error("OrderManagerImpl.addOrderRepairDO error", e);
//			return false;	
//		}
//	}


	public Boolean addOrderDiscountDO(List<OrderDiscountInfoDO> list, OrderDO orderDO) {
		try {
			if (null != list && list.size() > 0) {
				for (OrderDiscountInfoDO orderDiscountInfoDO : list) {
					orderDiscountInfoDO.setOrderId(orderDO.getId());
					orderDiscountInfoDO.setUserId(orderDO.getUserId());
					orderDiscountInfoDO.setBizCode(orderDO.getBizCode());
					orderDiscountInfoManager.addOrderDiscountInfo(orderDiscountInfoDO);
				}
			}
		} catch (Exception e) {
			log.error("OrderManagerImpl.addOrderDiscountDO error", e);
			return false;
		}
		return true;
	}

	public Boolean addOrderPaymentDO(OrderDO orderDO, String appKey) {

		OrderPaymentDO orderPaymentDO = new OrderPaymentDO();

		// TODO 这里的paymentSn先用orderSn,后续如果支持一单多次支付的话，则需要区分开来
		orderPaymentDO.setPaymentSn(orderDO.getOrderSn());
		orderPaymentDO.setOrderId(orderDO.getId());
		orderPaymentDO.setPayStatus(TradeConstants.PaymentStatus.UNPAID);
		orderPaymentDO.setUserId(orderDO.getUserId());
		orderPaymentDO.setTotalAmount(orderDO.getTotalAmount());
		orderPaymentDO.setPayAmount(0L);
		orderPaymentDO.setPaymentId(orderDO.getPaymentId());
		orderPaymentDO.setBizCode(orderDO.getBizCode());
		orderPaymentDO.setDeleteMark(0);

		try {
			orderPaymentManager.addOrderPayment(orderPaymentDO);
			boolean needPay = true;
			if (orderDO.getTotalAmount() <= 0) {
				needPay = false;// 该订单无需再支付了
			}
			String sellerUserRelateStatus = "unpaid";
			// 无需支付订单处理
			if (needPay == false) {
				sellerUserRelateStatus = "paid";
				// 将订单状态置为已支付

				orderPaySuccess(orderDO.getId(),null, orderDO.getUserId());

				orderDO.setOrderStatus(TradeConstants.Order_Status.PAID);
			}

			userManagerImpl.addSellerUserRelate(orderDO.getUserId(), orderDO.getSellerId(), orderDO.getId(),
					sellerUserRelateStatus, orderDO.getTotalAmount(), appKey);
			return true;
		} catch (Exception e) {
			log.error("OrderManagerImpl.addOrderPaymentDO error", e);
			return false;
		}
	}


	@Override
	public String getAlipayPubKey(String bizCode) throws TradeException {
		BizInfoDTO bizInfo = null;
		bizInfo = appManager.getBizInfo(bizCode);
		if (null == bizInfo) {
			log.error("bizInfo is null"+bizCode);
			throw new TradeException(bizCode+" bizInfo is null");
		}
		if (null == bizInfo.getBizPropertyMap()) {
			log.error("bizPropertyMap is null,  bizCode:{}", bizCode);
			throw new TradeException(bizCode+" bizPropertyMap is null");
		}
		return TradeUtil.getAlipayPubKey(bizInfo.getBizPropertyMap());
	}


	@Override
	public String getLianlianpayPubKey(String bizCode) throws TradeException {
		BizInfoDTO bizInfo = null;
		bizInfo = appManager.getBizInfo(bizCode);
		if (null == bizInfo) {
			log.error("bizInfo is null"+bizCode);
			throw new TradeException(bizCode+" bizInfo is null");
		}
		if (null == bizInfo.getBizPropertyMap()) {
			log.error("bizPropertyMap is null,  bizCode:{}", bizCode);
			throw new TradeException(bizCode+" bizPropertyMap is null");
		}
		return TradeUtil.getLianlianpayPubKey(bizInfo.getBizPropertyMap());
	}

	@Override
	public String getWxpayPartnerKey(String bizCode, int paymenId) throws TradeException {
		BizInfoDTO bizInfo = null;
		bizInfo = appManager.getBizInfo(bizCode);
		if (null == bizInfo) {
			log.error("bizInfo is null"+bizCode);
			throw new TradeException(bizCode+" bizInfo is null");
		}
		if (null == bizInfo.getBizPropertyMap()) {
			log.error("bizPropertyMap is null,  bizCode:{}", bizCode);
			throw new TradeException(bizCode+" bizPropertyMap is null");
		}
		if(paymenId==2){
			return TradeUtil.getWxAppPayPartnerKey(bizInfo.getBizPropertyMap());
		}
		else if(paymenId==5){
			return TradeUtil.getWxWapPayPartnerKey(bizInfo.getBizPropertyMap());
		}else{
			throw new TradeException(paymenId+" paymentId is invalid");
		}
	}

	public List<OrderDO> queryOverTimeOrder(int count) throws TradeException, ParseException {
		Date time = DateUtil.getTodayTime().getTime();
		time = DateUtil.getRelativeDate(time, count);
		OrderQTO orderQTO = new OrderQTO();
		orderQTO.setOrderTimeStart(time);
		List<OrderDO> orders = orderDAO.queryOverTimeOrder(orderQTO);
		return orders;
	}

	@Override
	public OrderDO getOrderByOrderSn(String order_sn) throws TradeException {
		OrderDO orderDO = new OrderDO();
		orderDO.setOrderSn(order_sn);
		return orderDAO.getOrderByOrder_sn(orderDO);
	}

	@Override
	public int updateOrderTotalAmount(OrderDO order) {
		return orderDAO.updateOrderTotalAmount(order);
	}

	@Override
	public int updatePickupCode(Long orderId, Long userId, String pickupCode) {
		return 0;
	}

	@Override
	public boolean checkHasPurchasedItem(Long userId, Long skuId) {
		OrderQTO query = new OrderQTO();
		query.setUserId(userId);
		query.setItemSkuId(skuId);
		Integer count = orderDAO.getItemHasPurchasedCount(query);
		if(count>0)
			return true;
		return false;
	}

	@Override
	public OrderDO getPreOrder(Long userId, Long skuId) throws TradeException {
		OrderQTO query = new OrderQTO();
		query.setUserId(userId);
		query.setItemSkuId(skuId);
		log.info( " getPreOrder userId: "+userId+" skuId:"+skuId );
		return orderDAO.getPreOrder(query);
	}

	@Override
	public OrderDO getPreOrderByItemQTO(OrderItemQTO query) throws TradeException {
		
		log.info( " getPreOrder query: "+JSONObject.toJSONString(query) );
		List<OrderItemDO> orderItemDOList = orderItemDAO.queryOrderItemByItemId(query);
		log.info( " getPreOrder orderItemDOList: "+JSONObject.toJSONString(orderItemDOList) );
		OrderQTO orderQTO = new OrderQTO();
		List<Long> orderIds = new ArrayList<Long>();
		for(OrderItemDO orderItemDO:orderItemDOList){
			orderIds.add(orderItemDO.getOrderId());
		}
		if(orderIds!=null && !orderIds.isEmpty()){
			orderQTO.setOrderIds(orderIds);
			orderQTO.setOrderStatusStr(" user_order.order_status = 5 ");
			orderQTO.setDeleteMark(0);
			log.info( " getPreOrder orderQTO: "+JSONObject.toJSONString(orderQTO) );
			List<OrderDO> result = orderDAO.queryUserOrdersUpgradeSeckill(orderQTO);
			log.info( " getPreOrder result: "+JSONObject.toJSONString(result) );
			if(result !=null && !result.isEmpty()){
				return result.get(0);
			}
		}
		
		return null;
	}

	@Override
	public List<OrderDO> queryTimeoutCancelPreOrders(OrderQTO query) throws TradeException {
		return orderDAO.queryTimeoutPreOrders(query);
	}

	@Override
	public Boolean deletePreOrder(final OrderDO orderDO,final Long skuId) throws TradeException {
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try{
					String lockSn = orderDO.getUserId()+"_"+orderDO.getType()+"_"+skuId;
					
					log.info(" lockSn : "+JSONObject.toJSON(lockSn));
					
					boolean unlock = bizLockManager.bizUnLock(BizLockType.activity_type, lockSn);
					
					log.info(" unlock : "+JSONObject.toJSON(unlock));
					
					if(unlock){
						OrderQTO query = new OrderQTO();
						query.setId(orderDO.getId());
//						orderDAO.deletePreOrder(query);
						//改为逻辑删除
						orderDAO.falseDeleteOrderById(query);
						
//						orderItemManager.deleteOrderItem(orderDO.getId());
						//订单商品改为逻辑删除
						orderItemManager.falseDeleteOrderItem(orderDO.getId());
					}
					return true;
				}catch(Exception e){
					status.setRollbackOnly();
					return false;
				}
			}
			
		});
	}
		

	@Override
	public Boolean phyDeletePreOrder(OrderDO orderDO, Long skuId)
			throws TradeException {
		try{
			String lockSn = orderDO.getUserId()+"_"+orderDO.getType()+"_"+skuId;
			
			log.info(" lockSn : "+JSONObject.toJSON(lockSn));
			
			boolean unlock = bizLockManager.bizUnLock(BizLockType.activity_type, lockSn);
			
			log.info(" unlock : "+JSONObject.toJSON(unlock));
			
			if(unlock){
				
				OrderItemQTO orderItemQTO = new OrderItemQTO();
				orderItemQTO.setDeleteMark(0);
				orderItemQTO.setUserId(orderDO.getUserId());
				orderItemQTO.setItemSkuId(skuId);
				List<OrderItemDO>  orderItemDOs= orderItemDAO.queryOrderItemByItemId(orderItemQTO);
							
				log.info(" orderItemDOs :"+orderItemDOs);
				for(OrderItemDO orderItemDO:orderItemDOs){
					OrderQTO query = new OrderQTO();
					query.setId(orderItemDO.getOrderId());
					int result = orderDAO.phyDeleteOrder(query);
					log.info(" delete preorder result :"+result);
					if(result>0){
						orderItemManager.deleteOrderItem(orderItemDO.getOrderId());
					}
				}
				
				
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public OrderDO getActivityOrder(Long userId, Long skuId) throws TradeException {
		OrderQTO query = new OrderQTO();
		query.setUserId(userId);
		query.setItemSkuId(skuId);
		return orderDAO.getActivityOrder(query);
	}

	@Override
	public int updateOrderDiscountInfo(OrderDO order) {
		return orderDAO.updateOrderDiscountInfo(order);
	}

	@Override
	public List<OrderDO> querySubOrders(Long oid) throws TradeException {
		OrderQTO query = new OrderQTO();
		query.setOriginalOrder(oid);
		return orderDAO.querySubOrdersByOriginalOrder(query);
	}

	@Override
	public int updateOrderMallCommission(OrderDO orderDO) throws TradeException {
		return orderDAO.updateOrderCommission(orderDO);
	}

	@Override
	public Boolean pushOrders(List<OrderQTO> orderQTOList)
			throws TradeException {
		int result = orderDAO.updatePushOrderStatus(orderQTOList);
		if(result == 0){
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, " user_order.pushOrder 无数据更新，订单未推送");
		}
		return result > 0;
	}

	@Override
	public Boolean rollBackOrder(OrderQTO orderQTO) throws TradeException {
		Long orderId = orderQTO.getId();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderId", String.valueOf(orderId));
		paramMap.put("orderStatus", orderQTO.getOrderStatus()+"");
		
		int result = orderDAO.updateOrderStatusById(paramMap);
		if(result == 0){
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, " user_order.updateOrderStatusById 无数据更新，订单未回滚");
		}
		return result > 0 ;
	}

	@Override
	public Boolean updateDeliveryMarkById(OrderQTO orderQTO)
			throws TradeException {
		Long orderId = orderQTO.getId();
		Map<String, String> paramMap = new HashMap<String, String>();		
		paramMap.put("orderId", String.valueOf(orderId));
		/*未发货*/
		paramMap.put("deliveryMark", "0");
		
		return orderDAO.updateDeliveryMarkById(paramMap);
	}

	@Override
	public Boolean UpdateOrderSupplier(OrderQTO orderQTO) throws TradeException {
		
		return orderDAO.UpdateOrderSupplier(orderQTO);
	}

	@Override
	public List<OrderDO> queryDeliveriedOrders(Integer orderStatus)
			throws TradeException {
		OrderQTO orderQTO = new OrderQTO();
		/*分页的起始位置*/
		orderQTO.setOffset(0);
		/*每页容量*/
		orderQTO.setCount(500);
		orderQTO.setOrderStatus(orderStatus);
		
		return orderDAO.queryDeliveriedOrders(orderQTO);
	}
	
	
	
	

	//// @Override
	// public boolean addSplitOrder2(final Long oriOrderId,final String
	//// bizCode,final OrderViewDTO orderViewDTO,
	// final List<OrderTogetherDTO> orderTogethers,
	// final String appKey) throws TradeException {
	// return transactionTemplate.execute(new TransactionCallback<Boolean>() {
	//
	// @Override
	// public Boolean doInTransaction(TransactionStatus status) {
	// Boolean processStatus = Boolean.TRUE;
	// try{
	// if(null!=orderTogethers&&orderTogethers.size()>0){
	// for(OrderTogetherDTO orderTogether:orderTogethers){
	// OrderDO splitOrder = orderTogether.getOrderDO();
	// splitOrder.setOriginalOrder(oriOrderId);
	// addOrderDO(splitOrder);
	//
	// if(orderTogethers.size()>1){
	// splitOrder.setOriginalOrder(splitOrder.getId());
	// }
	//
	// Long splitOid = addOrder(splitOrder);
	//
	// OrderViewDO orderViewDO = dozerBeanService.cover(orderViewDTO,
	//// OrderViewDO.class);
	//
	// orderViewDO.setOrderId(splitOid);
	//
	// orderViewDO.setBizCode(bizCode);
	//
	// orderViewManager.addOrderView(orderViewDO);
	//
	// //
	// //添加订单商品列表(TODO 性能优化)
	// for (OrderItemDO oItem: orderTogether.getItemList()) {
	// oItem.setOrderId(splitOid);
	// oItem.setBizCode(bizCode);
	// oItem.setUserName(orderTogether.getOrderConsigneeDO().getConsignee());
	// //TODO ...需要修改
	// oItem.setDeliveryType(1);
	// //TODO 写入单个商品在促销活动和优惠券中的优惠金额, 用于后续的拆单中
	// orderItemManager.addOrderItem(oItem);
	//
	// itemPlatformManager.reduceStock(oItem.getItemSkuId(),oItem.getSellerId(),
	//// oItem.getNumber(), appKey);
	// }
	//
	// //
	// //添加订单优惠详情
	// if
	//// (null!=orderTogether.getOrderDiscountInfoDOs()&&orderTogether.getOrderDiscountInfoDOs().size()!=0)
	//// {
	// for (OrderDiscountInfoDO orderDiscountInfoDO :
	//// orderTogether.getOrderDiscountInfoDOs()) {
	// orderDiscountInfoDO.setOrderId(orderTogether.getOrderDO().getUserId());
	// orderDiscountInfoDO.setUserId(orderTogether.getOrderDO().getUserId());
	// orderDiscountInfoDO.setBizCode(bizCode);
	// orderDiscountInfoManager.addOrderDiscountInfo(orderDiscountInfoDO);
	// }
	// }
	//
	// //添加订单收货人信息
	// if(null!=orderTogether.getOrderConsigneeDO()){
	// orderTogether.getOrderConsigneeDO().setOrderId(splitOid);
	// orderConsigneeManager.addOrderConsignee(orderTogether.getOrderConsigneeDO());
	// }
	//
	// //生成支付单
	// OrderPaymentDO orderPaymentDO = genOrderPayment(splitOrder,
	//// splitOrder.getUserId(), splitOrder.getSellerId());
	// long paymentNoticeId =
	//// orderPaymentManager.addOrderPayment(orderPaymentDO);
	//
	// boolean needPay = true;
	// if(splitOrder.getTotalAmount() <= 0){
	// needPay = false;//该订单无需再支付了
	// }
	//
	// if(orderTogether.getOrderDO().getTotalAmount() <= 0 ){
	// needPay = false;//主订单不需要支付、则子订单也不需要支付
	// }
	//
	// String sellerUserRelateStatus = "unpaid";
	//
	// //无需支付订单处理
	// if (needPay == false) {
	// //将订单状态置为已支付
	// orderPaySuccess(splitOid, orderTogether.getOrderDO().getUserId());
	//
	// //发送订单支付成功消息
	// notifyManager.notifyPaySuccessMsg(splitOid,
	//// orderTogether.getOrderDO().getUserId(),
	//// orderTogether.getOrderDO().getSellerId(), bizCode);
	// orderTogether.getOrderDO().setOrderStatus(TradeConstants.Order_Status.PAID);
	//
	// sellerUserRelateStatus = "paid";
	// }
	//
	// userManagerImpl.addSellerUserRelate(orderTogether.getOrderDO().getUserId(),
	//// orderTogether.getOrderDO().getSellerId(),splitOid,
	//// sellerUserRelateStatus, orderTogether.getOrderDO().getTotalAmount(),
	//// appKey);
	// }
	// }
	//
	// }catch(Exception e){
	// log.error("add split order error",e);
	// status.setRollbackOnly();
	// processStatus = false;
	// }
	// return processStatus;
	// }
	//
	// });
	// }


    @Override
    public List<OrderDO> queryAllOrderBg(OrderQTO orderQTO) throws TradeException {
        // 入参检查
        // 需要根据买家id进行分表查询
        /*if (orderQTO.getUserId() == null) {
            throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "orderQTO.userId is null");
        }*/

        if (orderQTO.getOffset() == null || orderQTO.getOffset().intValue() < 0) {
            orderQTO.setOffset(0);
        }

        if (orderQTO.getCount() == null || orderQTO.getCount().intValue() > 500) {
            orderQTO.setCount(500);
        }

//		log.info("enter queryUserOrders,userId : " + orderQTO.getUserId());
        List<OrderDO> result = null;

//		if(orderQTO.getAllRefundingMark()!=null&&orderQTO.getAllRefundingMark()==1){
//
//		}

        try {

			/*老订单接口*/
            result = (List<OrderDO>) this.orderDAO.queryAllOrderBg(orderQTO);

        } catch (Exception e) {
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
        }
//		log.info("exit queryUserOrders result size : " + (result == null ? null : result.size()));

        return result;
    }

    @Override
    public int queryAllOrderCountBg(OrderQTO orderQTO) throws TradeException {
        Integer result = 0;
        try {
//			Long startSec = System.currentTimeMillis();
//			log.info(" 耗时开始： "+System.currentTimeMillis());

            result = this.orderDAO.queryAllOrderCountBg(orderQTO);

//			log.info(" 耗时结束： "+System.currentTimeMillis());
//			log.info(" 耗时 queryUserOrdersCount time cost : "+(System.currentTimeMillis()-startSec)/1000);
        } catch (Exception e) {
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
        }
        return result == null ? 0 :result;
    }


	/**
	 * huangsiqian 0830
	 * @param orderQTO
	 * @return
	 * @throws TradeException
     */
	@Override
	public Long getUserTotalCost(OrderQTO orderQTO) throws TradeException {
		Long result ;
		try {
			result = orderDAO.getUserTotalCost(orderQTO);
		}catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
		return  result;

	}

	@Override
	public Integer queryUserOrderCount(OrderQTO orderQTO) throws TradeException {
		Integer result ;
		try {
			result = orderDAO.queryUserOrderCount(orderQTO);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}

		return result==null?0:result;
	}

//	@Override
//	public List<Long> getShareId(Long userId) throws TradeException {
//		List<Long> result ;
//		try {
//			result = orderDAO.getShareId(userId);
//		}catch (Exception e) {
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		return result;
//	}
}
