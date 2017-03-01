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
import com.mockuai.tradecenter.core.util.ModelUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.BizCodeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.AlipaymentDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.domain.settlement.NotifyWithdrawResultDTO;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.RSA;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogStatus;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogType;

public class AlipayCallback implements Action {
    private static final Logger log = LoggerFactory.getLogger(AlipayCallback.class);
    private static final Logger notifyLogger = LoggerFactory.getLogger("notifyLogger");

    @Resource
    private OrderPaymentManager orderPaymentManager;

    @Resource
    private OrderManager orderManager;

    @Resource
    private OrderItemManager orderItemManager;

    @Resource
    private UserManager userManager;

    @Resource
    private MarketingManager marketingManager;

    @Resource
    private VirtualWealthManager virtualWealthManager;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    private MsgQueueManager msgQueueManager;

    @Autowired
	private AppManager appManager;
    
    @Resource
    SupplierManager supplierManager;

    @Autowired
    private SellerTransLogManager sellerTransLogManager;

    @Autowired
    private RefundOrderManager  refundOrderManager;

    @Autowired
    private RefundOrderItemManager refundOrderItemManager;

    @Resource
    ItemManager itemManager;

    @Autowired
    TradeNotifyLogManager tradeNotifyLogMng;

    private List<Long> getIds(String successDetails){

    	if(StringUtils.isBlank(successDetails)){
    		return null;
    	}

    	List<Long> ids = new ArrayList<Long>();
    	String lines[] = successDetails.split("\\|");
		for(String line:lines){
			String lineData[] = line.split("\\^");
			ids.add(Long.parseLong(lineData[0]));
		}
		return ids;
    }

    public TradeResponse<String> execute(RequestContext context) {
    	
        Request request = context.getRequest();
        if (request.getParam("alipaymentDTO") == null) {
            log.error("alipaymentDTO is null");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "alipaymenetDTO is null");
        }        

        final AlipaymentDTO alipaymentDTO = (AlipaymentDTO) request.getParam("alipaymentDTO");
    	
    	log.info(" alipaycallback start  alipaymentDTO"+JSONObject.toJSONString(alipaymentDTO));
    	
    	//TODO 支付回调参数判断
        try{
            String errorMsg = validateAlipaymentFields(alipaymentDTO);
            if (!StringUtils.isEmpty(errorMsg)) {
                log.error(errorMsg);
                return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, errorMsg);
            }
        }catch(TradeException e){
            return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        //支付宝代付回调
        /*if(StringUtils.isNotBlank(batchNo)){
        	NotifyWithdrawResultDTO notifyWhthdrawResultDTO  = new NotifyWithdrawResultDTO();
        	notifyWhthdrawResultDTO.setBatchNo(batchNo);
        	String successDetails = alipaymentDTO.getSuccessDetails();
        	String failDetails = alipaymentDTO.getFailDetails();
        	notifyWhthdrawResultDTO.setSuccessIds(getIds(successDetails));
        	notifyWhthdrawResultDTO.setFailIds(getIds(failDetails));
        	 //以支付宝约定的格式返回数据
            TradeResponse<String> response = null;
        	try {
				boolean processNotifyResult = sellerTransLogManager.processWithdrawNotify(notifyWhthdrawResultDTO);
				if(processNotifyResult)
					 //以支付宝约定的格式返回数据
		           response = ResponseUtils.getSuccessResponse("success");
				else
					  response = ResponseUtils.getSuccessResponse("failed");
			} catch (TradeException e) {
				 log.error("",e);
				 response = ResponseUtils.getSuccessResponse("failed");
			}
            return response;
        }*/
        
        //支付宝签名验证
        Map<String,String[]> paramMap = alipaymentDTO.getOriginParamMap();
        if(paramMap != null){
            Map<String,String> signMap = new TreeMap<String, String>();
            for(Map.Entry<String,String[]> entry: paramMap.entrySet()){
                if("sign_type".equals(entry.getKey())==false && "sign".equals(entry.getKey())==false){
                    if(entry.getValue().length == 1){
                        signMap.put(entry.getKey(), entry.getValue()[0]);
                    }
                }
            }

            StringBuilder paramSb = new StringBuilder();
            for (Map.Entry<String, String> entry : signMap.entrySet()) {
                paramSb.append(entry.getKey());
                paramSb.append("=");
//                paramSb.append("\"");
                paramSb.append(entry.getValue());
//                paramSb.append("\"");
                paramSb.append("&");
            }
            paramSb.deleteCharAt(paramSb.length() - 1);
//            String alipayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkJvcPew7sWXECAwBWj1CKN6BA6dkjcd/z8mEJJHRdjfPZJmIele90u9sFOQmsfiMzD3rb8RdGg+KFAjIhIY93bEiGoYF7PlJsSRE/pLR8tnR+M1ltwt5wGwlgu01JUY7M4qmKtV4y3sYBSowgEN3xwWL+4kd92J4efR0DTvEcqQIDAQAB";
//            boolean isYangdongxiRequest = TradeUtil.isYangdongxiRequest(orderDO.getBizCode());
//            if(!isYangdongxiRequest){
//            	alipayPublicKey = MockuaiConfig.alipay_public_key;
//            }
            String alipayPublicKey;
			try {
				alipayPublicKey = orderManager.getAlipayPubKey(BizCodeEnum.HAN_SHU.getCode());
				log.info(" ----- paramSb="+paramSb.toString()+", sign="+alipaymentDTO.getSign());
	            boolean result = RSA.verify(paramSb.toString(), alipaymentDTO.getSign(), alipayPublicKey, "utf-8");
	            log.info(" >>>>> alipay sign check result:"+result);
			} catch (TradeException e) {
				log.error("get alipay public key error",e);
			}
        }

        //appInfo判断
        AppInfoDTO appInfo = null;
        try {
			appInfo = appManager.getAppInfoByBizCode(BizCodeEnum.HAN_SHU.getCode(),AppTypeEnum.APP_WAP.getValue());
		} catch (TradeException e1) {
			new TradeResponse<String>("error to get appInfo, bizCode:{"+BizCodeEnum.HAN_SHU.getCode()+"}");
		}
        final AppInfoDTO finalAppInfo = appInfo;
        
        String resultDetails = alipaymentDTO.getResultDetails();
        if(StringUtils.isNotBlank(resultDetails)){ //代表是退款的通知
        	  TradeResponse<String> response = null;

            String batchNo = alipaymentDTO.getBatchNo();
              
        	log.info(" ----- resultDetails : "+resultDetails);
        	 
        	try{
        		String batchNoIn = alipaymentDTO.getBatchNo();
        		OrderItemDO orderItem = refundOrderManager.getOrderItemByAlipayDetailData(resultDetails,batchNoIn);
        		
        		log.info(" >>>>> orderItem: "+JSONObject.toJSONString(orderItem));
        		
        		RefundOrderItemDTO refundItemDTO = new RefundOrderItemDTO();
        		if(null!=orderItem){
        			refundItemDTO  = refundOrderManager.convert2RefundOrderItemDTO(orderItem);
        			        			
        			boolean result = refundOrderItemManager.notifyRefundSuccess(refundItemDTO,orderItem);
        			
        			
        			try {
        				OrderDO orderDOInner = orderManager.getOrder(orderItem.getOrderId(), orderItem.getUserId());
						OrderStockDTO orderStockDTO = new OrderStockDTO();
			            orderStockDTO.setOrderSn(orderDOInner.getOrderSn());
			            orderStockDTO.setSellerId(orderDOInner.getSellerId());
			            
			            List<OrderSku> orderSkus = new ArrayList<OrderSku>();			    		
		    			OrderSku orderSku = new OrderSku();						
		    			orderSku.setSkuId(orderItem.getItemSkuId());
		    			orderSku.setNumber(orderItem.getNumber());
		    			orderSku.setStoreId(orderDOInner.getStoreId());
		    			orderSku.setSupplierId(orderDOInner.getSupplierId());
		    			orderSkus.add(orderSku);
			    		
			    		orderStockDTO.setOrderSkuList(orderSkus);
			            supplierManager.backReduceItemSkuSup(orderStockDTO, finalAppInfo.getAppKey());
			            
					} catch (TradeException e) {
						log.info("  "+e);
					}
        			
        			
//        			if(result){
        			response = ResponseUtils.getSuccessResponse("success");
//        			}else{
//        				  response = ResponseUtils.getSuccessResponse("failed");
//        			}
        		}else{
        			
        			OrderItemDO orderItemNew = refundOrderManager.getOrderItemByAlipayDetailDataNew(resultDetails,batchNo);
        			refundItemDTO  = refundOrderManager.convert2RefundOrderItemDTO(orderItemNew);       			
        			
        			boolean result = refundOrderItemManager.notifyRefundFailed(refundItemDTO);
        			
//	            	if(result){
	            	response = ResponseUtils.getSuccessResponse("success");
//	        		}else{
//	        			response = ResponseUtils.getSuccessResponse("failed");
//		  			}
		        			
        		}
        		
        		// 记录退款日志
            	Long refundItemLogId = refundOrderManager.addRefundItemLog(refundItemDTO, true);
            	
            	log.info(" >>>>> refundItemLogId : "+refundItemLogId);
            	
            	if (refundItemLogId == 0) {
            		log.error(" alipay refund add log error");
            	}
        		
        	}catch (TradeException e) {
				 log.error("",e);
				 response = ResponseUtils.getSuccessResponse("failed");
			}
        	
        	return response;

        }        

        
      final String orderSn = alipaymentDTO.getOutTradeNo();
      final String tradeNo = alipaymentDTO.getTradeNo();
//      final String tradeStatus = alipaymentDTO.getTradeStatus();
      final Long totalFee = (long) (Double.parseDouble(alipaymentDTO.getTotalFee()) * 100);
//      log.info("alipayCallback>>>>>>>>>>>>>outTradeNo="+outTradeNo+", tradeStatus="+tradeStatus);

      //订单合法性判断
      OrderDO orderDO = null;
        try{
            orderDO = orderManager.getOrderByOrderSn(orderSn);
            log.info(" alipaycallback orderDO :"+JSONObject.toJSONString(orderDO));
            if(orderDO == null){
                log.error("order is not exist, orderSn:{}",
                		orderSn);
                return new TradeResponse<String>("system error");
            }

            //将取到的订单信息存放到上下文中，方便filter中进行处理
            context.put("orderDO", orderDO);

        }catch(TradeException e){
            log.error("error to get order, orderSn:{}",
                    orderSn, e);
            return new TradeResponse<String>("system error");
        }
        
        //如果订单状态不是待支付状态，则直接返回成功（这种情况大部分是支付回调重复了）
//      if(orderDO.getOrderStatus().intValue() != TradeConstants.Order_Status.UNPAID){
        if(orderDO.getOrderStatus().intValue() >= TradeConstants.Order_Status.PAID){

          log.error(" ----- order status is not unpaid, orderStatus:{}, orderId:{}, userId:{}",
                  orderDO.getOrderStatus(), orderDO.getId(), orderDO.getUserId());

          //以支付宝约定的格式返回数据
          TradeResponse<String> response = ResponseUtils.getSuccessResponse("success");
          return response;
        }
        
        //增加支付回调的日志记录
        try {
        	if(null==tradeNotifyLogMng.getTradeNotifyLogByOutBillNo(tradeNo, TradeNotifyLogType.PAYMENT)){
        		TradeNotifyLogDO tradeNotifyLogDO = new TradeNotifyLogDO();
    	        tradeNotifyLogDO.setTradeAmount(totalFee);
    	        tradeNotifyLogDO.setOrderId(orderDO.getId());
    	        tradeNotifyLogDO.setInnerBillNo(orderSn);
    	        tradeNotifyLogDO.setOutBillNo(tradeNo);//支付宝交易号
    	        if("TRADE_FINISHED".equals(alipaymentDTO.getTradeStatus())
    	                || "TRADE_SUCCESS".equals(alipaymentDTO.getTradeStatus())){//支付成功
    	        	tradeNotifyLogDO.setStatus(TradeNotifyLogStatus.SUCCESS);
    	        }else{
    	        	tradeNotifyLogDO.setStatus(TradeNotifyLogStatus.FAILED);
    	        }
    	        tradeNotifyLogDO.setType(TradeNotifyLogType.PAYMENT);
    				tradeNotifyLogMng.addTradeNotifyLog(tradeNotifyLogDO);
        	}

		} catch (Exception e2) {
			log.error("record tradeNotifyLog error",e2);
		}catch(Throwable e){
			log.error("record tradeNotifyLog error",e);
		}

        final OrderDO order = orderDO;
        //TODO 确认支付宝支付成功的trade_status有哪些情况
        if("TRADE_FINISHED".equals(alipaymentDTO.getTradeStatus())
                || "TRADE_SUCCESS".equals(alipaymentDTO.getTradeStatus())){//支付成功

            TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
                public TradeResponse doInTransaction(TransactionStatus transactionStatus) {
                    try{

                    	log.info(" alipay callback success");
                    	
                        //将订单状态置为已支付
                        orderManager.orderPaySuccess(order.getId(), null,order.getUserId());

                        //更新支付单信息
                        OrderPaymentDO orderPaymentDO = orderPaymentManager.getOrderPayment(order.getId(), order.getUserId());
                        if(orderPaymentDO == null){
                            //TODO error handle
                        }

                        long payAmount = 0;
                        if(StringUtils.isNotBlank(alipaymentDTO.getTotalFee())){
                            try{
                                payAmount = (long)((Double.valueOf(alipaymentDTO.getTotalFee()))*100L);
                            }catch(Exception e){
                                //TODO error handle
                                log.error("alipay callback error, tradeNo="+tradeNo+", totalFee="+totalFee, e);
                            }
                        }else{
                            //TODO error handle
                        }

                        List<OrderDO> subOrderDOs = querySubOrders(order);
//                        List<OrderDO> subOrderDOs = orderManager.querySubOrders(order.getId());
                        paySubOrderSuccess(subOrderDOs,alipaymentDTO.getTradeNo(),finalAppInfo.getAppKey());

                        //兼容父订单没有 orderPaymentDO 的情况
                        if(orderPaymentDO!=null){
                        	 orderPaymentManager.paySuccess(orderPaymentDO.getId(), orderPaymentDO.getUserId(),
                                     payAmount, alipaymentDTO.getTradeNo());
                        }



                        //判断订单是否有使用优惠券和虚拟财富，如果有使用，则需要调用营销接口最终确认使用优惠券和虚拟财富
                        Integer discountMark = order.getDiscountMark();
                        if((discountMark.intValue() & 1) == 1){//从左往右，第一个二进制位代表优惠活动标志
                            boolean releaseResult = marketingManager.useUserCoupon(order.getUserId(), order.getId(), finalAppInfo.getAppKey());
                            if(releaseResult == false){
                                throw new TradeException(ResponseCode.BIZ_E_USE_COUPON_ERROR);
                            }
                        }

                        if((discountMark.intValue() & 2) == 2){//从左往右，第二个二进制位代表虚拟财富标志
                            boolean releaseResult = virtualWealthManager.useUserWealth(order.getUserId(), order.getId(), finalAppInfo.getAppKey());
                            if(releaseResult == false){
                                throw new TradeException(ResponseCode.BIZ_E_USE_WEALTH_ERROR);
                            }
                        }

                        
    					
    					if( order.getParentMark() == 0){	
    						
    						OrderItemQTO orderItemQTO = new OrderItemQTO();
                            orderItemQTO.setOrderId(order.getId());
                            orderItemQTO.setUserId(order.getUserId());
//                            log.info(" orderItemQTO: "+JSONObject.toJSONString(orderItemQTO));
                            List<OrderItemDO> orderItemDOs = orderItemManager.queryOrderItem(orderItemQTO);
                            
    						OrderDO originalOrder = orderManager.getActiveOrder(order.getOriginalOrder(), order.getUserId());
    						if(originalOrder!=null){
    							if(originalOrder.getParentMark() == 1){
    								reduceItemSkuSup(originalOrder, order, orderItemDOs, finalAppInfo.getAppKey());
    							}
    							if(originalOrder.getParentMark() == 2){
    								reduceItemSkuSup( order, order, orderItemDOs, finalAppInfo.getAppKey());
    							}
    						}else{
    							reduceItemSkuSup( order, order, orderItemDOs, finalAppInfo.getAppKey());
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
    					        reduceItemSkuSup(order, orderDO, reOrderItemDOs, finalAppInfo.getAppKey());
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
    						        reduceItemSkuSup(orderDOO, orderDOO, reOrderItemDOs, finalAppInfo.getAppKey());
    							}
    							if(orderDOO.getParentMark()==1){
    								List<OrderDO> subOrderListO = orderManager.querySubOrders(orderDOO.getId());
    								for(OrderDO orderDO:subOrderListO){
    									OrderItemQTO orderItemQTO3 = new OrderItemQTO();
    							        orderItemQTO3.setOrderId(orderDO.getId());
    							        orderItemQTO3.setUserId(orderDO.getUserId());
    							        List<OrderItemDO> reOrderItemDOs1 = orderItemManager.queryOrderItem(orderItemQTO3);
    							        //预扣库存
    							        reduceItemSkuSup(orderDOO, orderDO, reOrderItemDOs1, finalAppInfo.getAppKey());
    								}
    							}
    						}
    						
    					}
                        

//                        if(order.getType().intValue()==3||order.getType().intValue()==4){
//                        	OrderItemQTO orderItemQuery = new OrderItemQTO();
//        					orderItemQuery.setOrderId(order.getId());
//        					orderItemQuery.setUserId(order.getUserId());
//        					List<OrderItemDO> orderItemDOList = orderItemManager.queryOrderItem(orderItemQuery);
//        					if(null!=orderItemDOList){
//        						 itemManager.crushItemSkuStock(orderItemDOList.get(0).getItemSkuId(),
//        								 order.getSellerId(), orderItemDOList.get(0).getNumber(), finalAppInfo.getAppKey());
//        					}
//                        }

                        // 更新交易的支付方式，必要性 TODO
//                        orderManager.updateOrderPayType(orderDO, Integer.parseInt(EnumPaymentMethod.ALI_PAY_FOR_APP.getCode()));
                        
                        //发送支付成功内部mq消息
                        msgQueueManager.sendPaySuccessMsg(order);

                        //以支付宝约定的格式返回数据
                        TradeResponse<String> response = ResponseUtils.getSuccessResponse("success");
                        return response;
                    }catch(TradeException e){
                        //回滚事务
                        transactionStatus.setRollbackOnly();
                        log.error("", e);
                        return ResponseUtils.getFailResponse(e.getResponseCode());
                    }
                }
            });

            context.put("appKey", finalAppInfo.getAppKey());

            return transResult;
        }else{//支付失败
            TradeResponse<String> response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_PAY_FAILED);
            return response;
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
    
    public String getName() {
        return ActionEnum.ALIPAY_CALLBACK.getActionName();
    }

    private String validateAlipaymentFields(AlipaymentDTO alipaymentDTO)
            throws TradeException {
        return null;
    }

    private Long getUserId(String orderUid) {
        String[] strs = orderUid.split("_");
        if (strs.length != 3) ;
        long sellerId = Long.parseLong(strs[0]);
        long buyerId = Long.parseLong(strs[1]);
        long orderId = Long.parseLong(strs[2]);
        return Long.valueOf(buyerId);
    }

    private void paySubOrderSuccess(List<OrderDO> orders,String outTradeNo,String appKey){
    	try{
    		if(null!=orders&&orders.isEmpty()==false){
    			for(OrderDO order:orders){

    				 orderManager.orderPaySuccess(order.getId(),null, order.getUserId());

    				//更新支付单信息
                    OrderPaymentDO orderPaymentDO = orderPaymentManager.getOrderPayment(order.getId(),order.getUserId());
                    if(orderPaymentDO == null){
                        //TODO error handle
                    	continue;
                    }

                    orderPaymentManager.paySuccess(orderPaymentDO.getId(), orderPaymentDO.getUserId(),
                    		orderPaymentDO.getPayAmount(), outTradeNo);


                    //判断订单是否有使用优惠券和虚拟财富，如果有使用，则需要调用营销接口最终确认使用优惠券和虚拟财富
                    Integer discountMark = order.getDiscountMark();
                    if((discountMark.intValue() & 1) == 1){//从左往右，第一个二进制位代表优惠活动标志
                        boolean releaseResult = marketingManager.useUserCoupon(order.getUserId(), order.getId(), appKey);
                        if(releaseResult == false){
                            throw new TradeException(ResponseCode.BIZ_E_USE_COUPON_ERROR);
                        }
                    }

                    if((discountMark.intValue() & 2) == 2){//从左往右，第二个二进制位代表虚拟财富标志
                        boolean releaseResult = virtualWealthManager.useUserWealth(order.getUserId(), order.getId(), appKey);
                        if(releaseResult == false){
                            throw new TradeException(ResponseCode.BIZ_E_USE_WEALTH_ERROR);
                        }
                    }


//                    if(order.getType().intValue()==3||order.getType().intValue()==4){
//                    	OrderItemQTO orderItemQuery = new OrderItemQTO();
//    					orderItemQuery.setOrderId(order.getId());
//    					orderItemQuery.setUserId(order.getUserId());
//    					List<OrderItemDO> orderItemDOList = orderItemManager.queryOrderItem(orderItemQuery);
//    					if(null!=orderItemDOList){
//    						 itemManager.crushItemSkuStock(orderItemDOList.get(0).getItemSkuId(),
//    								 order.getSellerId(), orderItemDOList.get(0).getNumber(), appKey);
//    					}
//                    }


                    //发送支付成功内部mq消息
                    msgQueueManager.sendPaySuccessMsg(order);

    			}

            }

    	}catch(Exception e){
    		log.error("",e);
    	}
    }

    public static void main(String[] args){
//        String signStr = "body=\"TEST\"&buyer_email=\"zengzhangqiang@gmail.com\"&buyer_id=\"2088102555474298\"&discount=\"0.00\"&gmt_create=\"2015-06-11 08:38:49\"&gmt_payment=\"2015-06-11 08:38:49\"&is_total_fee_adjust=\"N\"&notify_id=\"58e264bcd15b140af58c3b8384c86a3b3m\"&notify_time=\"2015-06-11 08:42:50\"&notify_type=\"trade_status_sync\"&out_trade_no=\"1_92_1311\"&payment_type=\"1\"&price=\"0.01\"&quantity=\"1\"&seller_email=\"zhifu@yangdongxi.com\"&seller_id=\"2088311997503550\"&subject=\"TEST\"&total_fee=\"0.01\"&trade_no=\"2015061100001000290055324025\"&trade_status=\"TRADE_SUCCESS\"&use_coupon=\"N\"";
//        String sign = "MG31uyeQu5/flODI/HyhNy3o2INa2GM7qhz8NvctufyAQ6+wA27n8epssIhmPj0R5JoS4PAW8rU8wvFRAsargbx5W6awzAVt3wnzXvMOThgsYcDVYG0NBNUi8/xpSTH5cBsABq7dfX4LHgaP5MZyo2fQoMoKU40pqra15Ezc8tA=";
//        String alipayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkJvcPew7sWXECAwBWj1CKN6BA6dkjcd/z8mEJJHRdjfPZJmIele90u9sFOQmsfiMzD3rb8RdGg+KFAjIhIY93bEiGoYF7PlJsSRE/pLR8tnR+M1ltwt5wGwlgu01JUY7M4qmKtV4y3sYBSowgEN3xwWL+4kd92J4efR0DTvEcqQIDAQAB";
//
//        boolean result = RSA.verify(signStr, sign, alipayPublicKey, "utf-8");
//        log.error("alipay sign check>>>>>>>>>>>>>>>>>result="+result);

        Map<String,Long> userMap = new HashMap<String, Long>();
        userMap.put("51723_84129_24178", 84129L);
        for(Map.Entry<String,Long> entry: userMap.entrySet()){
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("user_id", entry.getValue());
            data.put("order_uid", entry.getKey());
            data.put("pay_status", 1);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("topic", "trade-pay-msg"));
            params.add(new BasicNameValuePair("data", JsonUtil.toJson(data)));
            try{
//                String response = HttpUtil.post("http://121.40.98.204:8082/YDX-ERP/message/notify", params);//洋东西回调地址
                String response = HttpUtil.post("http://api.lhydx.com/message/notify", params);//世纪联华回调地址
                System.out.println("order_uid:"+entry.getKey()+", response:"+response);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}