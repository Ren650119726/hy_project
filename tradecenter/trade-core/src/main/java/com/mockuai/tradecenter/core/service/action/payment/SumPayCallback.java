package com.mockuai.tradecenter.core.service.action.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.manager.*;
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
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.domain.SumPayCallBackDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.common.util.Money;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.OrderService;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.RSA;

public class SumPayCallback implements Action{
	private static final Logger log = LoggerFactory.getLogger(SumPayCallback.class);
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
	
	@Autowired
	OrderService orderService;

	public TradeResponse<String> execute(RequestContext context)
			throws TradeException {
		Request request = context.getRequest();

		if(request.getParam("sumPayCallBackDTO") == null){
			log.error("sumPayCallBackDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"sumPayCallBackDTO is null");
		}
		
		
		// 字段验证
		final SumPayCallBackDTO sumPayCallBackDTO = (SumPayCallBackDTO)request.getParam("sumPayCallBackDTO");
		
		if(sumPayCallBackDTO.getCode()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING," sumpay callback code is null");
		}
		if(sumPayCallBackDTO.getTradeno()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING," sumpay callback tradeno is null");
		}
		if(sumPayCallBackDTO.getPaystatus()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING," sumpay callback paystatus is null");
		}
//		if(sumPayCallBackDTO.getRemark()==null){
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING," sumpay callbakc remark is null");
//		}
		if(sumPayCallBackDTO.getTradeamount()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING," sumpay callbakc tradeamount is null");
		}
		final String orderUid = sumPayCallBackDTO.getTradeno();
	    final OrderUidDTO orderUidDTO = ModelUtil.parseOrderUid(orderUid);
		
		OrderDO orderDO = null;
		try{
			orderDO = orderManager.getOrder(orderUidDTO.getOrderId(), orderUidDTO.getUserId());
			if(orderDO == null){
				log.error("order is not exist, orderId:{}, userId:{}",
						orderUidDTO.getOrderId(), orderUidDTO.getUserId());
				return new TradeResponse<String>("system error");
			}

			//如果订单状态不是待支付状态，则直接返回成功（这种情况大部分是支付回调重复了）
			if(orderDO.getOrderStatus().intValue() != TradeConstants.Order_Status.UNPAID){
				log.warn("order status is not unpaid, orderStatus:{}, orderId:{}, userId:{}",
						orderDO.getOrderStatus(), orderUidDTO.getOrderId(), orderUidDTO.getUserId());
				
				//以微信约定格式返回回调结果
				Map<String, String> respMap = new HashMap<String, String>();
				respMap.put("resp_code", "SUCCESS");
				respMap.put("resp_msg", "OK");
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
		
		//签名验证
        Map<String,String> paramMap = sumPayCallBackDTO.getOriginParamMap();
        if(paramMap != null){
            Map<String,String> signMap = new TreeMap<String, String>();
            for(Map.Entry<String,String> entry: paramMap.entrySet()){
                if("notifyurl".equals(entry.getKey())==false && "sign".equals(entry.getKey())==false){
                	 signMap.put(entry.getKey(), entry.getValue());
                }
            }

            StringBuilder paramSb = new StringBuilder();
            for (Map.Entry<String, String> entry : signMap.entrySet()) {
                paramSb.append(entry.getKey());
                paramSb.append("=");
                paramSb.append(entry.getValue());
                paramSb.append("&");
            }
            paramSb.deleteCharAt(paramSb.length() - 1);

            String sumpayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkJvcPew7sWXECAwBWj1CKN6BA6dkjcd/z8mEJJHRdjfPZJmIele90u9sFOQmsfiMzD3rb8RdGg+KFAjIhIY93bEiGoYF7PlJsSRE/pLR8tnR+M1ltwt5wGwlgu01JUY7M4qmKtV4y3sYBSowgEN3xwWL+4kd92J4efR0DTvEcqQIDAQAB";
			try {
				sumpayPublicKey = orderManager.getAlipayPubKey(orderDO.getBizCode());
				 log.error("paramSb="+paramSb.toString()+", sign="+sumPayCallBackDTO.getSign());
		            boolean result = RSA.verify(paramSb.toString(), sumPayCallBackDTO.getSign(), sumpayPublicKey, "utf-8");
		            log.error("sumpay sign check>>>>>>>>>>>>>>>>>result="+result);
			} catch (TradeException e) {
				log.error("get alipay public key error",e);
			}
        }
		

		final OrderDO order = orderDO;
		if("000000".equals(sumPayCallBackDTO.getCode())&&"200".equals(sumPayCallBackDTO.getPaystatus())){//支付成功
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
						Money money = new Money(sumPayCallBackDTO.getTradeamount()+"");
						orderPaymentManager.paySuccess(orderPaymentDO.getId(), orderPaymentDO.getUserId(),
								money.getCent(), sumPayCallBackDTO.getPaytradeno());

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


						 List<OrderDO> subOrderDOs = orderManager.querySubOrders(orderUidDTO.getOrderId());
//	                        paySubOrderSuccess(subOrderDOs,sumPayCallBackDTO.getSerialNo(),appInfo.getAppKey());
						 orderService.paySubOrderSuccess(subOrderDOs,sumPayCallBackDTO.getPaytradeno(),appInfo.getAppKey());
						//以微信约定的格式返回数据
						Map<String, String> respMap = new HashMap<String, String>();
						respMap.put("resp_code", "SUCCESS");
						respMap.put("resp_msg", "OK");
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
			return transResult;
        }else{
			return new TradeResponse<String>(ResponseCode.BIZ_E_PAY_FAILED);
		}
	}

	@Override
	public String getName() {
		return ActionEnum.SUM_PAY_CALLBACK.getActionName();
	}



}
