package com.mockuai.tradecenter.core.service.action.payment;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.manager.*;
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
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.domain.UnionPaymentDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.OrderService;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogStatus;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogType;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

public class UnionPayCallbackForOldVersion implements Action {
    private static final Logger log = LoggerFactory.getLogger(UnionPayCallbackForOldVersion.class);
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

    @Autowired
    private MsgQueueManager msgQueueManager;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
	private AppManager appManager;

    @Autowired
	private RefundOrderManager refundOrderManager;

    @Autowired
	private RefundOrderItemManager refundOrderItemManager;

    @Resource
	TradeCoreConfig tradeCoreConfig;

    @Resource
    ItemManager itemManager;

    @Autowired
    OrderService orderService;

    @Autowired
    TradeNotifyLogManager tradeNotifyLogMng;

    public TradeResponse<String> execute(RequestContext context) throws TradeException {
        Request request = context.getRequest();
        if (request.getParam("unionPaymentDTO") == null) {
            log.error("unionPaymentDTO is null");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "unionPaymentDTO is null");
        }

        final UnionPaymentDTO unionPaymentDTO = (UnionPaymentDTO) request.getParam("unionPaymentDTO");
        SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
        SDKConfig.getConfig().setValidateCertDir(tradeCoreConfig.getUnipayCertDir()+"/mockuai_demo");
        //签名验证
        Map<String,String[]> paramMap = unionPaymentDTO.getOriginParamMap();
        Map<String, String> valideData = null;
      		if (null != paramMap && !paramMap.isEmpty()) {
      			Iterator<Entry<String, String[]>> it = paramMap.entrySet().iterator();
      			valideData = new HashMap<String, String>(paramMap.size());
      			while (it.hasNext()) {
      				Entry<String, String[]> e = it.next();
      				String key = (String) e.getKey();
      				String[] value = (String[]) e.getValue();
      				String valideValue = null;
      				try {
      					valideValue = new String(value[0].getBytes("ISO-8859-1"), "utf-8");
      				} catch (UnsupportedEncodingException e1) {
      					log.error("",e1);
      				}
      				valideData.put(key, valideValue);
      			}
      		}

              if(paramMap != null){
                  Map<String,String> signMap = new TreeMap<String, String>();
                  for(Map.Entry<String,String[]> entry: paramMap.entrySet()){
                      signMap.put(entry.getKey(), entry.getValue()[0]);

                  }
                  String encoding = signMap.get((SDKConstants.param_encoding));
                  boolean validateResult = false;
                  if (SDKUtil.validate(valideData, encoding)) {
                  	validateResult = true;
          		}
                  log.error("unionpay sign check>>>>>>>>>>>>>>>>>>>result="+validateResult);
              }else{
                  //TODO error handle
              }



        String reserved = unionPaymentDTO.getReqReserved();
        if(reserved.equals("Refund")){
        	String refundOrderId = unionPaymentDTO.getOrderId();
        	refundOrderId = refundOrderId.replace("X", "_");
    		OrderItemDO orderItemDO = refundOrderManager.getOrderItemByRefundId(refundOrderId);
    		if(null!=orderItemDO){
    			RefundOrderItemDTO refundOrderItemDTO = refundOrderManager.convert2RefundOrderItemDTO(orderItemDO);
    			String respCode = unionPaymentDTO.getRespCode();
    			Boolean result = true;
    			if(respCode.equals("00")&&orderItemDO.getRefundStatus()!=Integer.parseInt(EnumRefundStatus.REFUND_FINISHED.getCode())){
    			 result = 	refundOrderItemManager.notifyRefundSuccess(refundOrderItemDTO,orderItemDO);
    			}else if(!respCode.equals("00")&&orderItemDO.getRefundStatus()!=Integer.parseInt(EnumRefundStatus.REFUND_FAILED.getCode())){
    				result = 	refundOrderItemManager.notifyRefundFailed(refundOrderItemDTO);
    			}
    			 //以银联约定的格式返回数据
                TradeResponse<String> response = ResponseUtils.getSuccessResponse(result.toString());
                return response;

    		}

        }


        final String orderUid = unionPaymentDTO.getReqReserved();
        final OrderUidDTO orderUidDTO = ModelUtil.parseOrderUid(orderUid);
        //TODO 支付回调合法性校验，参数判断，以及返回值组装

//        Long totalFee = Long.valueOf(unionPaymentDTO.getSettleAmt());
        String respCode = unionPaymentDTO.getRespCode();
        log.info("unionPayCallback>>>>>>>>>>>>>orderUid=" + orderUid +
                ", respCode=" + respCode);

        //增加支付回调的日志记录
        try {
        	if(null==tradeNotifyLogMng.getTradeNotifyLogByOutBillNo(unionPaymentDTO.getQueryId(), TradeNotifyLogType.PAYMENT)){
        		 TradeNotifyLogDO tradeNotifyLogDO = new TradeNotifyLogDO();

     	        long payAmount = 0;
                 if (StringUtils.isNotBlank(unionPaymentDTO.getSettleAmt())) {
                     try {
                         payAmount = Long.valueOf(unionPaymentDTO.getSettleAmt());
                     } catch (Exception e) {
                         //TODO error handle
                         log.error("unionpay totalfee parse error, orderUid=" +
                                 orderUid + ", totalFee=" + unionPaymentDTO.getSettleAmt(), e);
                     }
                 } else {
                     //TODO error handle
                 }


     	        tradeNotifyLogDO.setTradeAmount(payAmount);
     	        tradeNotifyLogDO.setOrderId(orderUidDTO.getOrderId());
     	        tradeNotifyLogDO.setInnerBillNo(orderUid);
     	        tradeNotifyLogDO.setOutBillNo(unionPaymentDTO.getTraceNo());//银联交易号
     	        if("00".equals(unionPaymentDTO.getRespCode())){//支付成功
     	        	tradeNotifyLogDO.setStatus(TradeNotifyLogStatus.SUCCESS);
     	        }else{
     	        	tradeNotifyLogDO.setStatus(TradeNotifyLogStatus.FAILED);
     	        	 tradeNotifyLogDO.setOutErrorMsg(unionPaymentDTO.getRespMsg());
     	        }

     	        tradeNotifyLogDO.setType(TradeNotifyLogType.PAYMENT);
     				tradeNotifyLogMng.addTradeNotifyLog(tradeNotifyLogDO);
        	}

		} catch (Exception e2) {
			log.error("unipay call back record tradeNotifyLog error",e2);
		}catch(Throwable e){
			log.error("unipay record tradeNotifyLog error",e);
		}

        //订单状态检查
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
                //以银联约定的格式返回数据
                TradeResponse<String> response = ResponseUtils.getSuccessResponse("SUCCESS");
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


        final OrderDO order = orderDO;
        if("00".equals(unionPaymentDTO.getRespCode())){//支付成功
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
                        if (StringUtils.isNotBlank(unionPaymentDTO.getSettleAmt())) {
                            try {
                                payAmount = Long.valueOf(unionPaymentDTO.getSettleAmt());
                            } catch (Exception e) {
                                //TODO error handle
                                log.error("unionpay totalfee parse error, orderUid=" +
                                        orderUid + ", totalFee=" + unionPaymentDTO.getSettleAmt(), e);
                            }
                        } else {
                            //TODO error handle
                        }

                        //兼容父订单没有 orderPaymentDO 的情况
                        if(orderPaymentDO!=null){
                        	orderPaymentManager.paySuccess(orderPaymentDO.getId(), orderPaymentDO.getUserId(),
                                payAmount, unionPaymentDTO.getQueryId());
                        }



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

//                        if(order.getType().intValue()==3||order.getType().intValue()==4){
//                        	OrderItemQTO orderItemQuery = new OrderItemQTO();
//        					orderItemQuery.setOrderId(order.getId());
//        					orderItemQuery.setUserId(order.getUserId());
//        					List<OrderItemDO> orderItemDOList = orderItemManager.queryOrderItem(orderItemQuery);
//        					if(null!=orderItemDOList){
//        						 itemManager.crushItemSkuStock(orderItemDOList.get(0).getItemSkuId(),
//        								 order.getSellerId(), orderItemDOList.get(0).getNumber(), appInfo.getAppKey());
//        					}
//                        }

                        userManager.addSellerUserRelate(orderUidDTO.getUserId(), order.getSellerId(),order.getId(), "paid", order.getTotalAmount(), appInfo.getAppKey());


                        List<OrderDO> subOrderDOs = orderManager.querySubOrders(orderUidDTO.getOrderId());
                        orderService.paySubOrderSuccess(subOrderDOs,unionPaymentDTO.getQueryId(),appInfo.getAppKey());

                        //以银联约定的格式返回数据
                        TradeResponse<String> response = ResponseUtils.getSuccessResponse("SUCCESS");

                        //FIXME 内部mq消息跟外部客户消息要严格区分开来
                        //发送支付成功内部mq消息
                        msgQueueManager.sendPaySuccessMsg(order);

                        return response;
                    } catch (TradeException e) {
                        transactionStatus.setRollbackOnly();
                        log.error("", e);
                        return ResponseUtils.getFailResponse(e.getResponseCode());
                    }
                }
            });
            return transResult;
        }else{
            return new TradeResponse<String>(ResponseCode.BIZ_E_PAY_FAILED);
        }
    }

    public String getName() {
        return ActionEnum.UNION_PAY_CALLBACK_FOR_OLD_VERSION.getActionName();
    }


    private Long getUserId(String orderUid) {
        String[] strs = orderUid.split("_");
        if (strs.length != 3) ;
        long sellerId = Long.parseLong(strs[0]);
        long buyerId = Long.parseLong(strs[1]);
        long orderId = Long.parseLong(strs[2]);
        return Long.valueOf(buyerId);
    }
}