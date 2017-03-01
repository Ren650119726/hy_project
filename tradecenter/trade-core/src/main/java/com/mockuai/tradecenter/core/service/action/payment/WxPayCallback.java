package com.mockuai.tradecenter.core.service.action.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO.OrderSku;
import com.mockuai.tradecenter.core.manager.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.domain.WxPaymentDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.OrderService;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogStatus;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogType;

/**
* 微信支付回调
* @author
*/
public class WxPayCallback implements Action{
	private static final Logger log = LoggerFactory.getLogger(WxPayCallback.class);
	private static final Logger notifyLogger = LoggerFactory.getLogger("notifyLogger");

	@Resource
	private OrderPaymentManager orderPaymentManager;

	@Resource
	private OrderManager orderManager;

	@Resource
	private MarketingManager marketingManager;

	@Resource
	private VirtualWealthManager virtualWealthManager;

	@Autowired
	private MsgQueueManager msgQueueManager;

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
    private UserManager userManager;

	@Autowired
	private AppManager appManager;

	@Resource
	ItemManager itemManager;

	@Resource
	OrderItemManager orderItemManager;
	
	@Resource
	SupplierManager supplierManager;

	@Autowired
	OrderService orderService;

	 @Autowired
	TradeNotifyLogManager tradeNotifyLogMng;

	public TradeResponse<String> execute(RequestContext context)
			throws TradeException {
		Request request = context.getRequest();
		
		log.info(" WxPayCallback begin ");
		
		if(request.getParam("wxPaymentDTO") == null){
			log.error("wxPaymentDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"wxPaymentDTO is null");
		}


		//TODO 支付回调合法性校验，参数判断，以及返回值组装

		// 字段验证
		final WxPaymentDTO wxPaymentDTO = (WxPaymentDTO)request.getParam("wxPaymentDTO");

		final String orderUid = wxPaymentDTO.getAttach();
		final OrderUidDTO orderUidDTO = ModelUtil.parseOrderUid(orderUid);

		String errorMsg = validateWxPaymentFields(wxPaymentDTO);
		if(!StringUtils.isEmpty(errorMsg)){
			log.error(errorMsg);
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,errorMsg);
		}

		 //增加支付回调的日志记录
        try {
        	if(null==tradeNotifyLogMng.getTradeNotifyLogByOutBillNo(wxPaymentDTO.getTransactionId(), TradeNotifyLogType.PAYMENT)){
        		TradeNotifyLogDO tradeNotifyLogDO = new TradeNotifyLogDO();

    	        long payAmount = 0;
    			if (StringUtils.isNotBlank(wxPaymentDTO.getTotalFeeStr())) {
    				payAmount = Long.valueOf(wxPaymentDTO.getTotalFeeStr());
    			} else {
    				//TODO error handle
    			}

    	        tradeNotifyLogDO.setTradeAmount(payAmount);
    	        tradeNotifyLogDO.setOrderId(orderUidDTO.getOrderId());
    	        tradeNotifyLogDO.setInnerBillNo(orderUid);
    	        tradeNotifyLogDO.setOutBillNo(wxPaymentDTO.getTransactionId());//微信交易号
    	        if("SUCCESS".equals(wxPaymentDTO.getReturnCode())){//支付成功
    	        	tradeNotifyLogDO.setStatus(TradeNotifyLogStatus.SUCCESS);
    	        }else{
    	        	tradeNotifyLogDO.setStatus(TradeNotifyLogStatus.FAILED);
    	        	 tradeNotifyLogDO.setOutErrorMsg(wxPaymentDTO.getReturnMsg());
    	        }

    	        tradeNotifyLogDO.setType(TradeNotifyLogType.PAYMENT);
    				tradeNotifyLogMng.addTradeNotifyLog(tradeNotifyLogDO);
        	}

		} catch (Exception e2) {
			log.error("wxpay call back record tradeNotifyLog error",e2);
		}

		OrderDO orderDO = null;
		try{
			orderDO = orderManager.getOrder(orderUidDTO.getOrderId(), orderUidDTO.getUserId());
			if(orderDO == null){
				log.error("order is not exist, orderId:{}, userId:{}",
						orderUidDTO.getOrderId(), orderUidDTO.getUserId());
				return new TradeResponse<String>("system error");
			}

			//如果订单状态不是待支付状态，则直接返回成功（这种情况大部分是支付回调重复了）
//			if(orderDO.getOrderStatus().intValue() != TradeConstants.Order_Status.UNPAID){
			if(orderDO.getOrderStatus().intValue() >= TradeConstants.Order_Status.PAID){
				log.warn("order status is paid, orderStatus:{}, orderId:{}, userId:{}",
						orderDO.getOrderStatus(), orderUidDTO.getOrderId(), orderUidDTO.getUserId());

				//以微信约定格式返回回调结果
				Map<String, String> respMap = new HashMap<String, String>();
				respMap.put("return_code", "SUCCESS");
				respMap.put("return_msg", "OK");
				String xmlData = XmlUtil.map2XmlStr(respMap);
				TradeResponse<String> response = ResponseUtils.getSuccessResponse(xmlData);
				return response;
			}
		}catch(TradeException e){
			log.error("error to get order, orderId:{}, userId:{}",
					orderUidDTO.getOrderId(), orderUidDTO.getUserId(), e);
			return new TradeResponse<String>("system error");
		}
		if(null==orderDO.getAppType())
    		orderDO.setAppType(3);
		final AppInfoDTO appInfo = appManager.getAppInfoByBizCode(orderDO.getBizCode(),orderDO.getAppType());
		if(null==appInfo){
			throw new TradeException("error to get appInfo, bizCode:{"+orderDO.getBizCode()+"}");
		}

		 String bizCode = orderDO.getBizCode();
		 boolean isYangdongxiRequest = TradeUtil.isYangdongxiRequest(bizCode);

		//TODO 签名验证
		String sign = wxPaymentDTO.getSign();
		Map<String,String> paramMap = wxPaymentDTO.getOriginParamMap();
		if(paramMap != null){
			Map<String,String> signMap = new TreeMap<String, String>();
			for(Map.Entry<String,String> entry: paramMap.entrySet()){
				if("sign".equals(entry.getKey()) == false){
					signMap.put(entry.getKey(), entry.getValue());
				}
			}

//			String partnerKey = "48d15a39462fbe06f6391328ff685954";//TODO 这里先写死洋东西的partnerKey,急需重构
//			 if(!isYangdongxiRequest){
//				 partnerKey = MockuaiConfig.mockuai_wx_partner_key;
//	            }
			try{
				String partnerKey = orderManager.getWxpayPartnerKey(orderDO.getBizCode(), orderDO.getPaymentId());
				String actualSign = getWxParamSign(signMap, partnerKey);
				log.info("wechatpay sign check>>>>>>>>>>sign="+sign+", actualSign="+actualSign);
			}catch (TradeException e) {
				log.error("wechatpay sign check error",e);
			}

		}






		final OrderDO order = orderDO;
		if("SUCCESS".equals(wxPaymentDTO.getReturnCode())){//支付成功
			TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
				public TradeResponse doInTransaction(TransactionStatus transactionStatus) {
					try {
						//将订单状态置为已支付
						orderManager.orderPaySuccess(orderUidDTO.getOrderId(),null, orderUidDTO.getUserId());
						//更新支付单信息
						OrderPaymentDO orderPaymentDO = orderPaymentManager.getOrderPayment(orderUidDTO.getOrderId(),
								orderUidDTO.getUserId());
						if (orderPaymentDO == null) {
							//TODO error handle
						}

						long payAmount = 0;
						if (StringUtils.isNotBlank(wxPaymentDTO.getTotalFeeStr())) {
							payAmount = Long.valueOf(wxPaymentDTO.getTotalFeeStr());
						} else {
							//TODO error handle
						}

						if(orderPaymentDO!=null){
							orderPaymentManager.paySuccess(orderPaymentDO.getId(), orderPaymentDO.getUserId(),
									payAmount, wxPaymentDTO.getTransactionId());
						}


						//add by liuchao 支付通知成功后、增加客户关系
						 userManager.addSellerUserRelate(order.getUserId(), order.getSellerId(), order.getId(),"paid", order.getTotalAmount(), appInfo.getAppKey());


						//判断订单是否有使用优惠券和虚拟财富，如果有使用，则需要调用营销接口最终确认使用优惠券和虚拟财富
						Integer discountMark = order.getDiscountMark();
						if((discountMark.intValue() & 1) == 1){//从左往右，第一个二进制位代表优惠活动标志
							boolean releaseResult = marketingManager.useUserCoupon(order.getUserId(), order.getId(), appInfo.getAppKey());
							if(releaseResult == false){
								throw new TradeException(ResponseCode.BIZ_E_USE_COUPON_ERROR);
							}
						}

						if((discountMark.intValue() & 2) == 2){//从左往右，第二个二进制位代表虚拟财富标志
							boolean releaseResult = virtualWealthManager.useUserWealth(order.getUserId(), order.getId(), appInfo.getAppKey());
							if(releaseResult == false){
								throw new TradeException(ResponseCode.BIZ_E_USE_WEALTH_ERROR);
							}
						}

//						 if(order.getType().intValue()==3||order.getType().intValue()==4){
//	                        	OrderItemQTO orderItemQuery = new OrderItemQTO();
//	        					orderItemQuery.setOrderId(order.getId());
//	        					orderItemQuery.setUserId(order.getUserId());
//	        					List<OrderItemDO> orderItemDOList = orderItemManager.queryOrderItem(orderItemQuery);
//	        					if(null!=orderItemDOList){
//	        						 itemManager.crushItemSkuStock(orderItemDOList.get(0).getItemSkuId(),
//	        								 order.getSellerId(), orderItemDOList.get(0).getNumber(), appInfo.getAppKey());
//	        					}
//	                        }


						 List<OrderDO> subOrderDOs = querySubOrders(order);
//						 List<OrderDO> subOrderDOs = orderManager.querySubOrders(orderUidDTO.getOrderId());
	                     orderService.paySubOrderSuccess(subOrderDOs,wxPaymentDTO.getTransactionId(),appInfo.getAppKey());

	                     if( order.getParentMark() == 0){	
	    						
    						OrderItemQTO orderItemQTO = new OrderItemQTO();
                            orderItemQTO.setOrderId(order.getId());
                            orderItemQTO.setUserId(order.getUserId());
//	                            log.info(" orderItemQTO: "+JSONObject.toJSONString(orderItemQTO));
                            List<OrderItemDO> orderItemDOs = orderItemManager.queryOrderItem(orderItemQTO);
                            
    						OrderDO originalOrder = orderManager.getActiveOrder(order.getOriginalOrder(), order.getUserId());
    						if(originalOrder!=null){
    							if(originalOrder.getParentMark() == 1){
    								reduceItemSkuSup(originalOrder, order, orderItemDOs, appInfo.getAppKey());
    							}
    							if(originalOrder.getParentMark() == 2){
    								reduceItemSkuSup( order, order, orderItemDOs, appInfo.getAppKey());
    							}
    						}else{
    							reduceItemSkuSup( order, order, orderItemDOs, appInfo.getAppKey());
    						}
    												
    					}
    					
                        if( order.getParentMark() == 1){	

    						List<OrderDO> subOrderList = orderManager.querySubOrders(order.getId());
    						for(OrderDO orderDO:subOrderList){
    							OrderItemQTO orderItemQTO1 = new OrderItemQTO();
    					        orderItemQTO1.setOrderId(orderDO.getId());
    					        orderItemQTO1.setUserId(orderDO.getUserId());
    					        List<OrderItemDO> reOrderItemDOs = orderItemManager.queryOrderItem(orderItemQTO1);
    					        //预扣库存
    					        reduceItemSkuSup(order, orderDO, reOrderItemDOs, appInfo.getAppKey());
    						}
    						
    					}
    					
    					if(order.getParentMark() == 2){
    						
    						List<OrderDO> subOrderList = orderManager.querySubOrders(order.getId());
    						for(OrderDO orderDOO:subOrderList){
    							if(orderDOO.getParentMark()==0){
    								OrderItemQTO orderItemQTO2 = new OrderItemQTO();
    						        orderItemQTO2.setOrderId(orderDOO.getId());
    						        orderItemQTO2.setUserId(orderDOO.getUserId());
    						        List<OrderItemDO> reOrderItemDOs = orderItemManager.queryOrderItem(orderItemQTO2);
    						        //预扣库存
    						        reduceItemSkuSup(orderDOO, orderDOO, reOrderItemDOs, appInfo.getAppKey());
    							}
    							if(orderDOO.getParentMark()==1){
    								List<OrderDO> subOrderListO = orderManager.querySubOrders(orderDOO.getId());
    								for(OrderDO orderDO:subOrderListO){
    									OrderItemQTO orderItemQTO3 = new OrderItemQTO();
    							        orderItemQTO3.setOrderId(orderDO.getId());
    							        orderItemQTO3.setUserId(orderDO.getUserId());
    							        List<OrderItemDO> reOrderItemDOs1 = orderItemManager.queryOrderItem(orderItemQTO3);
    							        //预扣库存
    							        reduceItemSkuSup(orderDOO, orderDO, reOrderItemDOs1, appInfo.getAppKey());
    								}
    							}
    						}
    						
    					}
	                    
	                        
	                        
						//以微信约定的格式返回数据
						Map<String, String> respMap = new HashMap<String, String>();
						respMap.put("return_code", "SUCCESS");
						respMap.put("return_msg", "OK");
						String xmlData = XmlUtil.map2XmlStr(respMap);
						TradeResponse<String> response = ResponseUtils.getSuccessResponse(xmlData);

						//发送支付成功内部mq消息
						msgQueueManager.sendPaySuccessMsg(order);

						return response;
					} catch (TradeException e) {
						transactionStatus.setRollbackOnly();
						log.error("WxPayCallback error", e);
						return ResponseUtils.getFailResponse(e.getResponseCode());
					}
				}
			});

			context.put("orderDO", order);
			  context.put("appKey", appInfo.getAppKey());

			return transResult;
        }else{
			return new TradeResponse<String>(ResponseCode.BIZ_E_PAY_FAILED);
		}
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
		return ActionEnum.WECHAT_PAY_CALLBACK.getActionName();
	}

	private String getWxParamSign(Map<String, String> paramMap, String partnerKey) {
		StringBuilder signSb = new StringBuilder();
		for (Map.Entry entry : paramMap.entrySet()) {
			signSb.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("&");
		}

		String toSignStr = new StringBuilder().append(signSb.toString()).append("key=").append(partnerKey).toString();
		log.info("signSb="+toSignStr);
		String sign = DigestUtils.md5Hex(toSignStr).toUpperCase();
		return sign;
	}


	/**
	 * 验证支付宝回调的参数
	 * @return
	 */
	public String validateWxPaymentFields(WxPaymentDTO wxPaymentDTO)throws TradeException{
//		if(wxPaymentDTO.getUserId() == null){
//			return "userId is null";
//		}else if(wxPaymentDTO.getTradeNo() == null){
//			return "tradeNo is null";
//		}else if(wxPaymentDTO.getTotalFee() == null){
//			return ("outTradeNo is null");
//		}else if(wxPaymentDTO.getSign() == null){
//			return ("sign is null");
//		}else if(wxPaymentDTO.getToSign() == null){
//			return ("toSign is null");
//		}else if(wxPaymentDTO.getTotalFee() == null){
//			return ("totalFee is null");
//		}else if(wxPaymentDTO.getTradeState() == null){
//			return ("tradeStatus is null");
//		}
		return null;
	}
}
