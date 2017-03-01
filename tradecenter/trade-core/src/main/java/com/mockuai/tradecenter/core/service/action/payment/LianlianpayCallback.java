package com.mockuai.tradecenter.core.service.action.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.BizCodeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO.OrderSku;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.LianlianpaymentDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumLianLianRefundStatus;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.common.vo.RetBean;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import com.mockuai.tradecenter.core.manager.MsgQueueManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.manager.SupplierManager;
import com.mockuai.tradecenter.core.manager.TradeNotifyLogManager;
import com.mockuai.tradecenter.core.manager.VirtualWealthManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.LLPayUtil;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogStatus;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogType;


public class LianlianpayCallback implements Action {
    private static final Logger log = LoggerFactory.getLogger(LianlianpayCallback.class);


    @Resource
    private OrderPaymentManager orderPaymentManager;

    @Resource
    private OrderManager orderManager;

    @Resource
    private OrderItemManager orderItemManager;
    
    @Resource
    private RefundOrderManager refundOrderManager;
    
    @Resource
    private RefundOrderItemManager refundOrderItemManager;

    @Resource
    private MarketingManager marketingManager;
    
    @Resource
    private SupplierManager supplierManager;

    @Resource
    private VirtualWealthManager virtualWealthManager;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    private MsgQueueManager msgQueueManager;

    @Autowired
	private AppManager appManager;

    @Autowired
    TradeNotifyLogManager tradeNotifyLogMng;
    
    private TradeResponse<RetBean> getResponse(String code ,String msg){
    	RetBean retBean = new RetBean();
    	retBean.setRet_code(code);
        retBean.setRet_msg(msg);
        return new TradeResponse<RetBean>(retBean);
    }

    public TradeResponse<RetBean> execute(RequestContext context) {
    	
//        RetBean retBean = new RetBean();
        Request request = context.getRequest();
        // 支付回调合法性校验，参数判断，以及返回值组装

        final String reqStr = (String) request.getParam("reqStr");
    	
    	log.info(" LianlianpayCallback start , reqStr:"+reqStr);

        if (LLPayUtil.isnull(reqStr)) {
            log.error("reqStr is null");
            return this.getResponse("9999", "reqStr is null");
        }
        
        //解析异步通知对象
        final LianlianpaymentDTO lianlianpaymentDTO = JSON.parseObject(reqStr, LianlianpaymentDTO.class);

        log.info(" ----- lianlianpaymentDTO :"+JSONObject.toJSONString(lianlianpaymentDTO));
        
        try
        {
        	log.info(" LianlianpayCallback 验签 ");
        	String lianlianpayPublicKey = orderManager.getLianlianpayPubKey(BizCodeEnum.HAN_SHU.getCode());
            if (!LLPayUtil.checkSign(reqStr, lianlianpayPublicKey))
            {
                log.info("支付异步通知失败");
                return getResponse("9999","支付异步通知失败");
            }
        } catch (Exception e)
        {
            log.info("异步通知报文解析异常：" + e);
            return getResponse("9999","异步通知报文解析异常");
        }

        //appInfo判断
        AppInfoDTO appInfo = null;
        try {
			appInfo = appManager.getAppInfoByBizCode(BizCodeEnum.HAN_SHU.getCode(),AppTypeEnum.APP_WAP.getValue());
		} catch (TradeException e1) {
            return getResponse("9999","error to get appInfo, bizCode:{"+BizCodeEnum.HAN_SHU.getCode()+"}");
		}
        final AppInfoDTO finalAppInfo = appInfo;
        
        /*
         *  退款的回调通知
         */
        if(StringUtils.isNotBlank(lianlianpaymentDTO.getNo_refund())){
        	String noRefund = lianlianpaymentDTO.getNo_refund();
        	
        	log.info(" ----- lianlian refund callback ");
        	
        	Long orderId = 0l ;
            Long userId = 0l ;
            Long orderItemId = 0l ;
            OrderItemDO orderItemDO = null;
            RefundOrderItemDTO refundOrderItemDTO = null;
            OrderDO orderDOInner  = null;
            try
            {
            	String refundOrderId[] = noRefund.split("\\_");
        		orderId = Long.parseLong(refundOrderId[0]);
        		userId = Long.parseLong(refundOrderId[1]);
        		orderItemId = Long.parseLong(refundOrderId[2]);
        		
        		OrderItemQTO query = new OrderItemQTO();
    			query.setUserId(userId);
    			query.setOrderItemId(orderItemId);
    			
    			orderItemDO = orderItemManager.getOrderItem(query);
    			
    			log.info(" ----- orderItemDO:"+JSONObject.toJSONString(orderItemDO));
    			
    			//重复退款通知校验
    			if(EnumRefundStatus.REFUND_FINISHED.getCode().equals(orderItemDO.getRefundStatus()+"")){
    				log.info("已经退款成功");
                    return this.getResponse("0000", "已经退款成功");
    			}
        		
    			refundOrderItemDTO = refundOrderManager.convert2RefundOrderItemDTO(orderItemDO);
        		
        		orderDOInner = orderManager.getOrder(orderId, userId);
            	
            	/*log.info(" LianlianRefundCallback 验签 ");
            	String lianlianpayPublicKey = orderManager.getLianlianpayPubKey(orderDOInner.getBizCode());
                if (!LLPayUtil.checkSign(reqStr, lianlianpayPublicKey))
                {
                    log.info("退款异步通知失败");
                    return getResponse("9999", "退款异步通知失败");
                }*/
            	
            	String resultCode="";
            	
            	String resultMsg="";
            	
            	if(EnumLianLianRefundStatus.REFUND_SUCCESS.getCode().equals(lianlianpaymentDTO.getSta_refund())){//退款成功
                	
                	refundOrderItemManager.notifyRefundSuccess(refundOrderItemDTO,orderItemDO); 
                	resultCode = "0000";
                	resultMsg = "order refund success";                 
                   
                	try {
						
						OrderStockDTO orderStockDTO = new OrderStockDTO();
			            orderStockDTO.setOrderSn(orderDOInner.getOrderSn());
			            orderStockDTO.setSellerId(orderDOInner.getSellerId());
			            
			            List<OrderSku> orderSkus = new ArrayList<OrderSku>();			    		
		    			OrderSku orderSku = new OrderSku();						
		    			orderSku.setSkuId(orderItemDO.getItemSkuId());
		    			orderSku.setNumber(orderItemDO.getNumber());
		    			orderSku.setStoreId(orderDOInner.getStoreId());
		    			orderSku.setSupplierId(orderDOInner.getSupplierId());
		    			orderSkus.add(orderSku);
			    		
			    		orderStockDTO.setOrderSkuList(orderSkus);
			            supplierManager.backReduceItemSkuSup(orderStockDTO, finalAppInfo.getAppKey());
			            
					} catch (TradeException e) {
						log.info("  "+e);
					}
                	
                }else{//退款失败
                	
                	refundOrderItemManager.notifyRefundFailed(refundOrderItemDTO);
                	resultCode = "9999";
                	resultMsg = "order refund failed";
                }
            	
				// 记录退款日志
            	Long refundItemLogId = refundOrderManager.addRefundItemLog(refundOrderItemDTO, true);
            	if (refundItemLogId == 0) {
            		log.error(" lianlianpay refund add log error");
            	}
            	
            	return getResponse(resultCode,resultMsg); 
            	
    		} catch (Exception e) {
    			
    			log.info("" + e);
                return getResponse("9999","order refund failed"); 
    		}
            
        }
        
//      final String outTradeNo = lianlianpaymentDTO.getNoOrder();
      final String tradeNo = lianlianpaymentDTO.getOid_paybill();
//      final String tradeStatus = lianlianpaymentDTO.getResultPay();
      Long moneyOrder = 0l;

      moneyOrder = (long) (Double.parseDouble(lianlianpaymentDTO.getMoney_order()) * 100);        

      final Long totalFee = moneyOrder;

      OrderDO orderDO = null;
        //订单合法性判断
        final String orderSn = lianlianpaymentDTO.getNo_order();

        try{
            orderDO = orderManager.getOrderByOrderSn(orderSn);
            
            log.info(JSONObject.toJSONString(orderDO));
            
            if(orderDO == null){
                log.error("order is not exist, orderSn:{}",
                		orderSn);
                return getResponse("9999","order is not exist"); 
            }

            //将取到的订单信息存放到上下文中，方便filter中进行处理
            context.put("orderDO", orderDO);
           
        }catch(TradeException e){
            log.error("error to get order, orderSn:{}",
                    orderSn, e);
            return getResponse("9999","error to get order");
        }
        
        //如果订单状态不是待支付状态，则直接返回成功（这种情况大部分是支付回调重复了）
//      if(orderDO.getOrderStatus().intValue() != TradeConstants.Order_Status.UNPAID){
        if(orderDO.getOrderStatus().intValue() >= TradeConstants.Order_Status.PAID){
        	log.error("order status is not unpaid, orderStatus:{}, orderId:{}, userId:{}",orderDO.getOrderStatus(), orderDO.getId(), orderDO.getUserId());
	          
	        //以连连支付约定的格式返回数据
	        return getResponse("0000","order status is paid");
        }

        //增加支付回调的日志记录
        try {
        	
        	if(null==tradeNotifyLogMng.getTradeNotifyLogByOutBillNo(tradeNo, TradeNotifyLogType.PAYMENT)){
        		TradeNotifyLogDO tradeNotifyLogDO = new TradeNotifyLogDO();
    	        tradeNotifyLogDO.setTradeAmount(totalFee);
    	        tradeNotifyLogDO.setOrderId(orderDO.getId());
    	        tradeNotifyLogDO.setInnerBillNo(orderSn);
    	        tradeNotifyLogDO.setOutBillNo(tradeNo);//支付宝交易号
    	        if("SUCCESS".equals(lianlianpaymentDTO.getResult_pay()) ){//支付成功
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
        if("SUCCESS".equals(lianlianpaymentDTO.getResult_pay())){//支付成功

            TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
                public TradeResponse doInTransaction(TransactionStatus transactionStatus) {
                    try{
                    	log.info(" LianlianpayCallback start ");
                        //将订单状态置为已支付
                        orderManager.orderPaySuccess(order.getId(),null, order.getUserId());

                        //更新支付单信息
                        OrderPaymentDO orderPaymentDO = orderPaymentManager.getOrderPayment(order.getId(), order.getUserId());
                        if(orderPaymentDO == null){
                            //TODO error handle
                        }

                        long payAmount = 0;
                        if(StringUtils.isNotBlank(lianlianpaymentDTO.getMoney_order())){
                            try{
                                payAmount = (long)((Double.valueOf(lianlianpaymentDTO.getMoney_order()))*100L);
                            }catch(Exception e){
                                //TODO error handle
                                log.error("alipay callback error, tradeNo="+tradeNo+", totalFee="+totalFee, e);
                            }
                        }else{
                            //TODO error handle
                        }

                        List<OrderDO> subOrderDOs = querySubOrders(order);
//                        List<OrderDO> subOrderDOs = orderManager.querySubOrders(order.getId());
                        paySubOrderSuccess(subOrderDOs,lianlianpaymentDTO.getOid_paybill(),finalAppInfo.getAppKey());

                        //兼容父订单没有 orderPaymentDO 的情况
                        if(orderPaymentDO!=null){
                        	 orderPaymentManager.paySuccess(orderPaymentDO.getId(), orderPaymentDO.getUserId(),
                                     payAmount, lianlianpaymentDTO.getOid_paybill());
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
//	                            log.info(" orderItemQTO: "+JSONObject.toJSONString(orderItemQTO));
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
                        
    					
                        // 更新交易的支付方式，必要性 TODO
//                        orderManager.updateOrderPayType(orderDO, Integer.parseInt(EnumPaymentMethod.ALI_PAY_FOR_APP.getCode()));
                        
                        //发送支付成功内部mq消息
                        msgQueueManager.sendPaySuccessMsg(order);

                        //以连连支付约定的格式返回数据
                        return getResponse("0000","order pay success");
                    }catch(TradeException e){
                        //回滚事务
                        transactionStatus.setRollbackOnly();
                        log.error("", e);
                        return getResponse("9999",e.getMessage());
                    }
                }
            });

            context.put("appKey", finalAppInfo.getAppKey());
            
            log.info(" LianlianpayCallback end ");

            return transResult;
        }else{//支付失败
        	return getResponse("9999","pay failed");
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

    public String getName() {
        return ActionEnum.LIANLIAN_PAY_CALLBACK.getActionName();
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

                    //发送支付成功内部mq消息
                    msgQueueManager.sendPaySuccessMsg(order);

    			}

            }

    	}catch(Exception e){
    		log.error("",e);
    	}
    }


}