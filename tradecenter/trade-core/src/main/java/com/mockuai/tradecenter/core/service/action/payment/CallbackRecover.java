package com.mockuai.tradecenter.core.service.action.payment;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.AlipaymentDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import com.mockuai.tradecenter.core.manager.MsgQueueManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.manager.TradeNotifyLogManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.manager.VirtualWealthManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogStatus;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogType;

public class CallbackRecover implements Action {
    private static final Logger log = LoggerFactory.getLogger(CallbackRecover.class);

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

    public TradeResponse<String> execute(RequestContext context) {
    	
//    	log.info(" begin CallbackRecover  ");
    	
        Request request = context.getRequest();
        if (request.getParam("orderQTO") == null) {
            log.error("orderQTO is null");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderQTO is null");
        }
        
        final AlipaymentDTO orderQTO = (AlipaymentDTO) request.getParam("orderQTO");
        
        // TODO
        try {
        	if("trade".equals(orderQTO.getOutTradeNo())){
        		return new TradeResponse<String>(orderManager.getCallBackOrderXX(orderQTO)); 
            }
		} catch (TradeException e) {
			// TODO: handle exception
			ResponseUtils.getFailResponse(e.getResponseCode());
		}
        
        
        final String orderSn = orderQTO.getTradeNo();

        OrderDO orderDO = null;
        try{
            orderDO = orderManager.getOrderByOrderSn(orderSn);
            if(orderDO == null){
                log.error("order is not exist, orderSn:{}",
                		orderSn);
                return new TradeResponse<String>("system error");
            }
            
            if ( orderDO.getOrderStatus()!=null && orderDO.getOrderStatus() >= 30 ){
            	return new TradeResponse<String>("orderstatus us "+orderDO.getOrderStatus()+" error 订单已经支付成功，并进入相关流程");
            }

        }catch(TradeException e){
            log.error("error to get order, orderSn:{}",
                    orderSn, e);
            return new TradeResponse<String>("system error");
        }
        
        //增加支付回调的日志记录
        try {
        	if(null==tradeNotifyLogMng.getTradeNotifyLogByOutBillNo(orderQTO.getOutTradeNo(), TradeNotifyLogType.PAYMENT)){
        		TradeNotifyLogDO tradeNotifyLogDO = new TradeNotifyLogDO();
    	        tradeNotifyLogDO.setTradeAmount(orderDO.getTotalAmount());
    	        tradeNotifyLogDO.setOrderId(orderDO.getId());
    	        tradeNotifyLogDO.setInnerBillNo(orderSn);
    	        tradeNotifyLogDO.setOutBillNo(orderQTO.getOutTradeNo());//支付宝交易号
    	        tradeNotifyLogDO.setStatus(TradeNotifyLogStatus.SUCCESS);
    	       
    	        tradeNotifyLogDO.setType(TradeNotifyLogType.PAYMENT);
    				tradeNotifyLogMng.addTradeNotifyLog(tradeNotifyLogDO);
        	}


		} catch (Exception e2) {
			log.error("record tradeNotifyLog error",e2);
		}catch(Throwable e){
			log.error("record tradeNotifyLog error",e);
		}

        //appInfo判断
        AppInfoDTO appInfo = null;
        try {
        	if(null==orderDO.getAppType())
        		orderDO.setAppType(3);
			appInfo = appManager.getAppInfoByBizCode(orderDO.getBizCode(),orderDO.getAppType());
		} catch (TradeException e1) {
			new TradeResponse<String>("error to get appInfo, bizCode:{"+orderDO.getBizCode()+"}");
		}
        final AppInfoDTO finalAppInfo = appInfo;


        final OrderDO order = orderDO;

        TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
            public TradeResponse doInTransaction(TransactionStatus transactionStatus) {
                try{

                    //将订单状态置为已支付
                    orderManager.orderPaySuccessTest(order.getId(), order.getUserId());

                    //更新支付单信息
                    OrderPaymentDO orderPaymentDO = orderPaymentManager.getOrderPayment(order.getId(), order.getUserId());
                    if(orderPaymentDO == null){
                        //TODO error handle
                    }

                    long payAmount = 0;
                    
                    payAmount = order.getTotalAmount();

                    List<OrderDO> subOrderDOs = orderManager.querySubOrders(order.getId());
                    paySubOrderSuccess(subOrderDOs,orderQTO.getOutTradeNo(),finalAppInfo.getAppKey());

                    //兼容父订单没有 orderPaymentDO 的情况
                    if(orderPaymentDO!=null){
                    	 orderPaymentManager.paySuccess(orderPaymentDO.getId(), orderPaymentDO.getUserId(),
                                 payAmount, orderQTO.getOutTradeNo());
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

//                    log.info(" sendPaySuccessMsg start : "+JSONObject.toJSONString(order));
                    //发送支付成功内部mq消息
                    msgQueueManager.sendPaySuccessMsg(order);
//                    log.info(" sendPaySuccessMsg end "+JSONObject.toJSONString(order));

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
    	
//    	log.info(" end CallbackRecover  ");
    	
        return transResult;
        
    }

    public String getName() {
        return ActionEnum.CALL_BACK_RECOVER.getActionName();
    }

    private void paySubOrderSuccess(List<OrderDO> orders,String outTradeNo,String appKey){
    	try{
    		if(null!=orders&&orders.isEmpty()==false){
    			for(OrderDO order:orders){

    				 orderManager.orderPaySuccessTest(order.getId(), order.getUserId());

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