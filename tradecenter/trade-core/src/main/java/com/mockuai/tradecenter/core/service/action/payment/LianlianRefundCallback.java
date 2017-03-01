package com.mockuai.tradecenter.core.service.action.payment;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumLianLianRefundStatus;
import com.mockuai.tradecenter.common.vo.RetBean;
import com.mockuai.tradecenter.common.vo.TradeRefundCallbackBean;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import com.mockuai.tradecenter.core.manager.MsgQueueManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.manager.TradeNotifyLogManager;
import com.mockuai.tradecenter.core.manager.VirtualWealthManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.LLPayUtil;


public class LianlianRefundCallback implements Action {
    private static final Logger log = LoggerFactory.getLogger(LianlianRefundCallback.class);


    @Resource
    private OrderPaymentManager orderPaymentManager;
    
    @Resource
    private OrderItemManager orderItemManager;
    
    @Resource
    private RefundOrderManager refundOrderManager;
    
    @Resource
    private RefundOrderItemManager refundOrderItemManager;

    @Resource
    private OrderManager orderManager;

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
    TradeNotifyLogManager tradeNotifyLogMng;

    public TradeResponse<RetBean> execute(RequestContext context) {
    	
    	log.info(" LianlianrefundCallback start ");
    	
        RetBean retBean = new RetBean();
        Request request = context.getRequest();
        // 支付回调合法性校验，参数判断，以及返回值组装

        final String reqStr = (String) request.getParam("reqStr");

        if (LLPayUtil.isnull(reqStr)) {
            log.error("reqStr is null");
            retBean.setRet_code("9999");
            retBean.setRet_msg("退款失败");
            return new TradeResponse<RetBean>(retBean);
        }
        
    	log.info(" refund reqStr : "+reqStr);
        
        //解析异步通知对象
        final TradeRefundCallbackBean tradeRefundCallbackBean = JSON.parseObject(reqStr, TradeRefundCallbackBean.class);

        String noRefund = tradeRefundCallbackBean.getNo_refund();


        Long orderId = 0l ;
        Long userId = 0l ;
        Long orderItemId = 0l ;
        OrderItemDO orderItemDO = null;
        RefundOrderItemDTO refundOrderItemDTO = null;
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
    		
			refundOrderItemDTO = refundOrderManager.convert2RefundOrderItemDTO(orderItemDO);
    		    		
    		OrderDO orderDO = orderManager.getOrder(orderId, userId);
        	
        	log.info(" LianlianRefundCallback 验签 ");
        	String lianlianpayPublicKey = orderManager.getLianlianpayPubKey(orderDO.getBizCode());
            if (!LLPayUtil.checkSign(reqStr, lianlianpayPublicKey))
            {
                log.info("退款异步通知失败");
                retBean.setRet_code("9999");
                retBean.setRet_msg("退款失败");
                return new TradeResponse<RetBean>(retBean);
            }
        } catch (Exception e)
        {
            log.info("退款异步通知报文解析异常：" + e);
            retBean.setRet_code("9999");
            retBean.setRet_msg("退款失败");
            return new TradeResponse<RetBean>(retBean);
        }
       
        final OrderItemDO orderItemDOIn = orderItemDO;
		
		final RefundOrderItemDTO refundOrderItemDTOIn = refundOrderItemDTO;
		
        try {
        	if(EnumLianLianRefundStatus.REFUND_SUCCESS.getCode().equals(tradeRefundCallbackBean.getSta_refund())){//支付成功
            	
            	refundOrderItemManager.notifyRefundSuccess(refundOrderItemDTOIn,orderItemDOIn);
            	
            	retBean.setRet_code("0000");
                retBean.setRet_msg("order refund success");
                TradeResponse<RetBean> response = ResponseUtils.getSuccessResponse(retBean);
                return response;
               
            }else{//支付失败
            	
            	refundOrderItemManager.notifyRefundFailed(refundOrderItemDTOIn);
            	retBean.setRet_code("9999");
            	retBean.setRet_msg("refund failed");
                TradeResponse<RetBean> response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_PAY_FAILED);
                return response;
            }
		} catch (Exception e) {
			
			log.info("" + e);
            retBean.setRet_code("9999");
            retBean.setRet_msg("退款失败");
            return new TradeResponse<RetBean>(retBean);
		}
        
    }

    public String getName() {
        return ActionEnum.LIANLIAN_REFUND_CALLBACK.getActionName();
    }



}