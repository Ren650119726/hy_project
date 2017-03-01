package com.mockuai.tradecenter.core.service.action.order;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO.OrderSku;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.ModelUtil;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

import java.util.*;
import java.util.concurrent.ExecutorService;

/**
* 支付订单处理类
*/
public class PayOrder implements Action {
	private static final Logger log = LoggerFactory.getLogger(PayOrder.class);

	@Resource
	private OrderManager orderManager;

	@Resource
	private OrderPaymentManager orderPaymentManager;

	@Resource
	private OrderItemManager orderItemManager;

	@Resource
	private VirtualWealthManager virtualWealthManager;

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private DataManager dataManager;

	@Resource
	private UserManager userManager;

	@Resource
    private ExecutorService executor;
	
	@Resource
	private SupplierManager supplierManager;

	@Resource
	private MsgQueueManager msgQueueManager;

	@Autowired
    private TradeNotifyLogManager tradeNotifyLogManager;

	public TradeResponse<Boolean> execute(RequestContext context)
			throws TradeException {
		Request request = context.getRequest();
		final String appKey = (String) context.get("appKey");

		if (request.getParam("userId") == null) {
			log.error("userId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		}

		if (request.getParam("orderId") == null) {
			log.error("orderId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
		}

		if (request.getParam("payType") == null) {
			log.error("payType is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "payType is null");
		}

		if (request.getParam("payPassword") == null) {
			log.error("payPassword is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "payPassword is null");
		}

		final long orderId = (Long) request.getParam("orderId");
		log.info("  >>>>>>>>>>>>orderId :"+orderId);
		final long userId = (Long) request.getParam("userId");
		final int payType = (Integer) request.getParam("payType");
		final String payPassword = (String) request.getParam("payPassword");

		final OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
		if (order == null) {
			log.error("order doesn't exist orderId:" + orderId + " userId:" + userId);
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
		}

		// 订单状态校验，只有未支付的订单才可以支付
		if (order.getOrderStatus() != TradeConstants.Order_Status.UNPAID) {
			log.error("the order has been paid, forbid to pay again");
			return ResponseUtils.getFailResponse(ResponseCode.ORDER_PAID,
					"订单已经支付过或已取消！");
		}

		//支付类型校验
		final int wealthType = getWealthTypeByPayType(payType);
		if (wealthType == -1) {
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_NOT_EXIST_PAYMENT, "支付方式无效");
		}

		//查询指定虚拟财富的信息，获取兑换率
		final WealthAccountDTO wealthAccountDTO = virtualWealthManager.getWealthAccount(userId, wealthType, appKey);

		//支付密码校验
		boolean checkResult = userManager.checkUserPayPwd(userId, payPassword, appKey);
		if (checkResult == false) {
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_PAY_PASSWORD_ERROR);
		}

		//查询指定订单的子订单
		final List<OrderDO> subOrderList = querySubOrders(order);
		log.info(" subOrderList : "+JSONObject.toJSONString(subOrderList));
		// TODO 查询下单商品，并判断所关联的原商品的状态以及sku的有效性
        OrderItemQTO orderItemQTO = new OrderItemQTO();
        orderItemQTO.setOrderId(orderId);
        orderItemQTO.setUserId(userId);
//        log.info(" orderItemQTO: "+JSONObject.toJSONString(orderItemQTO));
        final List<OrderItemDO> orderItemDOs = orderItemManager.queryOrderItem(orderItemQTO);
//        log.info(" orderItemDOs: "+JSONObject.toJSONString(orderItemDOs));
        orderItemManager.checkItemForGetPaymentUrl(orderItemDOs,appKey);

		//TODO 支付密码错误次数限制（限制一段时间内同一笔订单的支付密码错误次数）

		//余额/嗨币支付逻辑
		TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
			public TradeResponse doInTransaction(TransactionStatus transactionStatus) {
				try {
					//订单待支付总金额
					long orderTotalAmount = order.getTotalAmount();

					//计算所需虚拟财富数量（如果算出来有小数，则采用进一法）
					Double exchangeRate = wealthAccountDTO.getExchangeRate();
					Double wealthAmountDoubleValue = orderTotalAmount / exchangeRate;
					long wealthTotalAmount = (wealthAmountDoubleValue==wealthAmountDoubleValue.longValue())?
							wealthAmountDoubleValue.longValue():wealthAmountDoubleValue.longValue()+1;
							
					//检查目前虚拟财富数量是否够用
					if (wealthAccountDTO.getAmount() < wealthTotalAmount) {
						throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_WEALTH_BALANCE_NOT_ENOUGH);
					}

					//获取子订单列表的虚拟财富使用分摊集
					Map<Long, Long> orderWealthAmountMap = getOrderWealthAmountMap(subOrderList,
							wealthTotalAmount, orderTotalAmount);

					//预使用虚拟财富，如果需要拆单，则分别针对子订单进行虚拟财富的使用
					if (subOrderList!=null && subOrderList.isEmpty()==false) {
						for (OrderDO subOrder : subOrderList) {
							//获取该订单所分摊使用的虚拟财富数
							long wealthAmount = orderWealthAmountMap.get(subOrder.getId());
							boolean preUseResult = virtualWealthManager.preUseUserWealth(userId, wealthType,
									wealthAmount, subOrder.getId(), appKey);

							//更新订单每个商品所使用的虚拟财富数量
							updateOrderItemUsedWealth(subOrder, wealthAmount);
						}
					}else{
						boolean preUseResult = virtualWealthManager.preUseUserWealth(userId, wealthType,
								wealthTotalAmount, orderId, appKey);

						//更新订单每个商品所使用的虚拟财富数量
						updateOrderItemUsedWealth(order, wealthTotalAmount);
					}

					//TODO 预使用结果校验

					//更新订单支付方式和状态
					orderManager.updateOrderPayType(order, subOrderList, payType);
					orderManager.orderPaySuccess(orderId,subOrderList, userId);


					//使用虚拟财富
					if (subOrderList!=null && subOrderList.isEmpty()==false) {
						for (OrderDO subOrder : subOrderList) {
							boolean useResult = virtualWealthManager.useUserWealth(userId, subOrder.getId(), appKey);
						}
					}else{
						boolean useResult = virtualWealthManager.useUserWealth(userId, orderId, appKey);
					}


					//TODO 虚拟财富使用结果校验
					executor.submit(new Thread() {
						public void run() {
							try {
								//支付成功数据埋点
//								dataManager.doPaySuccessBuriedPoint(order);
							} catch (Throwable throwable) {
								log.error("error to add paySuccess data");
							}
						}
					});
									

					
					if( order.getParentMark() == 0){						
						OrderDO originalOrder = orderManager.getActiveOrder(order.getOriginalOrder(), userId);
						if(originalOrder!=null){
							if(originalOrder.getParentMark() == 1){
								reduceItemSkuSup(originalOrder, order, orderItemDOs, appKey);
							}
							if(originalOrder.getParentMark() == 2){
								reduceItemSkuSup( order, order, orderItemDOs, appKey);
							}
						}else{
							reduceItemSkuSup( order, order, orderItemDOs, appKey);
						}
												
					}
					
					if( order.getParentMark() == 1){	

						List<OrderDO> subOrderList = orderManager.querySubOrders(orderId);
						for(OrderDO orderDO:subOrderList){
							OrderItemQTO orderItemQTO = new OrderItemQTO();
					        orderItemQTO.setOrderId(orderDO.getId());
					        orderItemQTO.setUserId(orderDO.getUserId());
					        List<OrderItemDO> reOrderItemDOs = orderItemManager.queryOrderItem(orderItemQTO);
					        //预扣库存
					        reduceItemSkuSup(order, orderDO, reOrderItemDOs, appKey);
						}
						
					}
					
					if(order.getParentMark() == 2){
						
						List<OrderDO> subOrderList = orderManager.querySubOrders(orderId);
						for(OrderDO orderDOO:subOrderList){
							if(orderDOO.getParentMark()==0){
								OrderItemQTO orderItemQTO = new OrderItemQTO();
						        orderItemQTO.setOrderId(orderDOO.getId());
						        orderItemQTO.setUserId(orderDOO.getUserId());
						        List<OrderItemDO> reOrderItemDOs = orderItemManager.queryOrderItem(orderItemQTO);
						        //预扣库存
						        reduceItemSkuSup(orderDOO, orderDOO, reOrderItemDOs, appKey);
							}
							if(orderDOO.getParentMark()==1){
								List<OrderDO> subOrderListO = orderManager.querySubOrders(orderDOO.getId());
								for(OrderDO orderDO:subOrderListO){
									OrderItemQTO orderItemQTO = new OrderItemQTO();
							        orderItemQTO.setOrderId(orderDO.getId());
							        orderItemQTO.setUserId(orderDO.getUserId());
							        List<OrderItemDO> reOrderItemDOs1 = orderItemManager.queryOrderItem(orderItemQTO);
							        //预扣库存
							        reduceItemSkuSup(orderDOO, orderDO, reOrderItemDOs1, appKey);
								}
							}
						}
						
					}
					
					
					log.info(" >>>>>>>>>>>>>>> subOrderList : "+JSONObject.toJSONString(subOrderList));
					//发送支付成功消息
					if (subOrderList!=null && subOrderList.isEmpty()==false) {
						msgQueueManager.sendPaySuccessMsg(subOrderList);
					}else{
						msgQueueManager.sendPaySuccessMsg(order);
					}
					return ResponseUtils.getSuccessResponse(true);
				} catch (TradeException e) {
					// 回滚事务
					transactionStatus.setRollbackOnly();

					//回滚使用的虚拟财富
					try{
						virtualWealthManager.releaseUsedWealth(userId, orderId, appKey);
					}catch(TradeException e1) {
						log.error("error to releaseUsedWealth, userId:{}, orderId:{}, appKey:{}",
								userId, orderId, appKey);
					}

					log.error("order_id" + orderId + ",payOrder.execute error", e);
					return ResponseUtils.getFailResponse(e.getResponseCode());
				} catch (Exception e) {
					// 回滚事务
					transactionStatus.setRollbackOnly();

					//TODO 回滚使用的虚拟财富
					log.error("order_id" + orderId + ",payOrder.execute error", e);
					return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
				}
			}
		});

		return transResult;
	}

	private void reduceItemSkuSup(OrderDO originalOrder,OrderDO order,List<OrderItemDO> orderItemDOs,String appKey){
		//预扣库存
		try {
			OrderStockDTO orderStockDTO = new OrderStockDTO();
            orderStockDTO.setOrderSn(originalOrder.getOrderSn());
            orderStockDTO.setSellerId(originalOrder.getSellerId());
            
            
            Map<Long,OrderSku> orderSkuMap = new HashMap<Long, OrderStockDTO.OrderSku>();
			for(OrderItemDO orderItemDO :orderItemDOs){
				if(orderSkuMap.get(orderItemDO.getItemSkuId())==null){
					OrderSku orderSku = new OrderSku();	
					orderSku.setSkuId(orderItemDO.getItemSkuId());
    				orderSku.setNumber(orderItemDO.getNumber());
					orderSku.setStoreId(order.getStoreId());
					orderSku.setSupplierId(order.getSupplierId());
					orderSkuMap.put(orderItemDO.getItemSkuId(), orderSku);
				}else{
					orderSkuMap.get(orderItemDO.getItemSkuId()).setNumber(orderSkuMap.get(orderItemDO.getItemSkuId()).getNumber()+orderItemDO.getNumber());
				}
				
			}		    			
			List<OrderSku> orderSkus = new ArrayList<OrderSku>();	
			for(Map.Entry<Long,OrderSku> entry : orderSkuMap.entrySet()){
				orderSkus.add(entry.getValue());
			}

    		/*for(OrderItemDO orderItemDO :orderItemDOs){
    			OrderSku orderSku = new OrderSku();						
    			orderSku.setSkuId(orderItemDO.getItemSkuId());
    			orderSku.setNumber(orderItemDO.getNumber());
    			orderSku.setStoreId(order.getStoreId());
    			orderSku.setSupplierId(order.getSupplierId());
    			orderSkus.add(orderSku);
    		}*/
    		log.info(" orderSkus "+JSONObject.toJSONString(orderSkus));
    		orderStockDTO.setOrderSkuList(orderSkus);
    		log.info(" orderStockDTO : "+JSONObject.toJSONString(orderStockDTO));
            supplierManager.reReduceItemSkuSup(orderStockDTO, appKey);
		} catch (TradeException e) {
			log.info("  "+e);
		}
	
	}
	
	/**
	 * 根据支付类型获取对应的虚拟财富类型
	 * @param payType
	 * @return
	 */
	private int getWealthTypeByPayType(int payType) {
		//获取余额和嗨币的虚拟财富类型
		if(payType == Integer.valueOf(EnumPaymentMethod.ACCOUNT_BALANCE_PAY.getCode())) {
			return WealthType.VIRTUAL_WEALTH.getValue();
		}else if (payType == Integer.valueOf(EnumPaymentMethod.HI_COIN_PAY.getCode())) {
			return WealthType.HI_COIN.getValue();
		}
		return -1;
	}

	/**
	 * 更新订单商品所分摊的虚拟财富数量
	 * @param orderDO
	 * @param orderUsedWealthAmount
	 * @throws TradeException
	 */
	private void updateOrderItemUsedWealth(OrderDO orderDO, long orderUsedWealthAmount) throws TradeException {

		OrderItemQTO orderItemQTO = new OrderItemQTO();
		orderItemQTO.setUserId(orderDO.getUserId());

		//如果该订单做过拆单则需要查询所有子单下的商品列表
		/*if (orderDO.getParentMark() != null && orderDO.getParentMark().intValue() == 1) {
			OrderQTO orderQTO = new OrderQTO();
			orderQTO.setOriginalOrder(orderDO.getId());
			orderQTO.setUserId(orderDO.getUserId());
			List<OrderDO> subOrderList = orderManager.queryUserOrders(orderQTO);
			List<Long> orderIdList = new ArrayList<Long>();
			for (OrderDO subOrder : subOrderList) {
				orderIdList.add(subOrder.getId());
			}
			orderItemQTO.setOrderIdList(orderIdList);
		}else{
			orderItemQTO.setOrderId(orderDO.getId());
		}*/
		orderItemQTO.setOrderId(orderDO.getId());
		List<OrderItemDO> orderItemDOs = orderItemManager.queryOrderItem(orderItemQTO);

		if (orderItemDOs == null || orderItemDOs.isEmpty()) {
			log.error("orderItemList is null, orderDO:{}", JsonUtil.toJson(orderDO));
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}

		long totalOrderItemPrice = orderDO.getTotalPrice();
		long totalItemUsedWealthAmount = 0;

		//为每个订单商品设置分摊的虚拟财富金额
		for (OrderItemDO orderItemDO : orderItemDOs) {
			long itemUsedWealthAmount = (orderUsedWealthAmount * orderItemDO.getUnitPrice() * orderItemDO.getNumber())
					/ totalOrderItemPrice;
			orderItemDO.setVirtualWealthAmount(itemUsedWealthAmount);
			totalItemUsedWealthAmount += itemUsedWealthAmount;
		}

		//将零头部分补到最后一个订单商品上面。这里不用管大小，直接加上(totalItemUsedWealthAmount-orderUsedWealthAmount)即可
		OrderItemDO lastOrderItem = orderItemDOs.get(orderItemDOs.size() - 1);
		lastOrderItem.setVirtualWealthAmount(lastOrderItem.getVirtualWealthAmount() +
				(totalItemUsedWealthAmount - orderUsedWealthAmount));

		//更新订单商品所使用的虚拟财富金额。TODO 性能待优化，需要改成批量更新
		for (OrderItemDO orderItemDO : orderItemDOs) {
			int updateNum = orderItemManager.updateOrderItemVirtualWealthAmount(orderItemDO.getId(),
					orderItemDO.getUserId(), orderItemDO.getVirtualWealthAmount());
		}
	}

	/**
	 * 获取指定子订单列表的虚拟财富分配集合
	 * @param subOrderList
	 * @param wealthTotalAmount
	 * @param orderTotalAmount
	 * @return
	 */
	private Map<Long, Long> getOrderWealthAmountMap(List<OrderDO> subOrderList,
													   long wealthTotalAmount, long orderTotalAmount) {
		Map<Long, Long> orderWealthAmountMap = new HashMap<Long, Long>();
		long usedWealthAmount = 0L;
		for (OrderDO subOrder : subOrderList) {
			long wealthAmount = (subOrder.getTotalAmount() * wealthTotalAmount) / orderTotalAmount;
			//最后一个订单的待支付金额＝总共需要支付的金额－累计已支付金额
			if (subOrder.getId().longValue() == subOrderList.get(subOrderList.size() - 1).getId()) {
				orderWealthAmountMap.put(subOrder.getId(), (wealthTotalAmount - usedWealthAmount));
			} else {
				orderWealthAmountMap.put(subOrder.getId(), wealthAmount);
				usedWealthAmount += wealthAmount;
			}
		}

		return orderWealthAmountMap;

	}

	/**
	 *  查询指定订单的子订单，如果没有子订单则返回空列表
	 * @param parentOrder
	 * @return
	 * @throws TradeException
	 */
	private List<OrderDO> querySubOrders(OrderDO parentOrder) throws TradeException{
		if (parentOrder == null || parentOrder.getParentMark() == 0) {
			return Collections.emptyList();
		}
		List<OrderDO> result = new ArrayList<OrderDO>();
		// 根订单		
		if(parentOrder.getParentMark().intValue() == 2){
			List<OrderDO> subOrderList = orderManager.querySubOrders(parentOrder.getId());
			for(OrderDO orderDO:subOrderList){
				result.addAll(querySubsOrders(orderDO));
			}
			
			return result;
		}
		//主订单
		if ( parentOrder.getParentMark().intValue() == 1) {
			List<OrderDO> subOrderList = orderManager.querySubOrders(parentOrder.getId());
			return subOrderList;
		}
		
		return Collections.emptyList();

	}

	/**
	 *  查询指定订单的子订单，如果没有子订单则返回空列表
	 * @param parentOrder
	 * @return
	 * @throws TradeException
	 */
	private List<OrderDO> querySubsOrders(OrderDO parentOrder) throws TradeException{
		List<OrderDO> result = new ArrayList<OrderDO>();
		if (parentOrder == null || parentOrder.getParentMark() == 0) {
			result.add(parentOrder);
			return result;
		}
		//主订单
		if ( parentOrder.getParentMark().intValue() == 1) {
			List<OrderDO> subOrderList = orderManager.querySubOrders(parentOrder.getId());
			return subOrderList;
		}
		
		return Collections.emptyList();

	}



	@Override
	public String getName() {
		return ActionEnum.PAY_ORDER.getActionName();
	}

	public static void main(String[] args) {
		Double a = 1.7111111;
		int b=1;
		double c = 1.00000000000;
		int d = 2;
		System.out.println("a>b:" + (a > b));
		System.out.println("b==c:" + (b==c));
		System.out.println("a<d:" + (a<d));
		System.out.println("a=:" + (a.longValue()));
	}
}
