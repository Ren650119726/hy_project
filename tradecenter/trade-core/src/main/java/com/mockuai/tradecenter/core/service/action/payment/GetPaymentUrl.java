package com.mockuai.tradecenter.core.service.action.payment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.util.ModelUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.base.ClientExecutorFactory;
import com.mockuai.tradecenter.core.config.MockuaiConfig;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.SignUtils;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.upmp.sdk.conf.UpmpConfig;
import com.unionpay.upmp.sdk.service.UpmpService;

import org.springframework.beans.factory.annotation.Autowired;

import payment.unionpay.QuickPayConf;
import payment.unionpay.QuickPaySampleServLet;

public class GetPaymentUrl implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetPaymentUrl.class);

    @Resource
    private OrderManager orderManager;

    @Resource
    private OrderItemManager orderItemManager;

    @Resource
    private ItemManager itemManager;
    
    @Resource
    private ClientExecutorFactory clientExecutorFactory;
    
    @Autowired
    private MsgQueueManager msgQueueManager;

    public GetPaymentUrl() {
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }

    public TradeResponse<PaymentUrlDTO> execute(RequestContext context)
            throws TradeException {
        Request request = context.getRequest();
        String appKey = (String)context.get("appKey");
//        log.info(" GetPaymentUrl appkey : "+appKey);
        String bizCode = (String)context.get("bizCode");
//        log.info(" GetPaymentUrl bizCode : "+bizCode);
        Map<String,BizPropertyDTO> bizPropertyMap =   (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
//        log.info(" bizPropertyMap: "+JSONObject.toJSONString(bizPropertyMap));
        if(null==bizPropertyMap){
        	throw new TradeException(bizCode+" bizPropertyMap is null");
        }
        
        String bizName = (String) context.get("bizName");
        
        TradeResponse response = null;

        if (request.getParam("orderId") == null) {
            log.error("orderId is null");
            response = ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
        }

        if (request.getParam("userId") == null) {
            log.error("userId is null");
            response = ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
        }

        Long orderId = (Long) request.getParam("orderId");
        Long userId = (Long) request.getParam("userId");

        try{
            OrderDO orderDO = this.orderManager.getOrder(orderId.longValue(), userId);
            //订单状态检查，只有未支付订单才能获取支付链接
            if(orderDO.getOrderStatus().intValue() != TradeConstants.Order_Status.UNPAID){
                return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_E_ORDER_STATUS_ERROR,
                        "订单已经支付过或已取消！");
            }

            //TODO 待支付价格无效时，直接返回错误提示。否则调用微信支付也会失败
            if(orderDO.getTotalAmount()==null || orderDO.getTotalAmount()<= 0){
//                return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_E_ORDER_STATUS_ERROR,
//                        "订单状态错误");

                //更新支付方式
                orderManager.updateOrderPayType(orderDO, 0);

            	orderManager.orderPaySuccess(orderDO.getId(),null, orderDO.getUserId());
            	
            	try{
                    //发送支付成功内部mq消息
                    msgQueueManager.sendPaySuccessMsg(orderDO);
        		}catch(Exception e){
        			log.error("pay success notify error");
        		}
            	
            	PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();
            	paymentUrlDTO.setRequestMethod(2);
        		paymentUrlDTO.setPayType(0);
        		paymentUrlDTO.setPayAmount(0L);
    		    response = new TradeResponse(paymentUrlDTO);
                return response;
            }

            //查询下单商品，并判断所关联的原商品的状态以及sku的有效性
            OrderItemQTO orderItemQTO = new OrderItemQTO();
            orderItemQTO.setOrderId(orderId);
            orderItemQTO.setUserId(userId);
            List<OrderItemDO> orderItemDOs = orderItemManager.queryOrderItem(orderItemQTO);

            Map<Long, OrderItemDO> orderItemMap = new HashMap<Long, OrderItemDO>();
            Set<Long> itemIds = new HashSet<Long>();
            for(OrderItemDO orderItemDO: orderItemDOs){
                orderItemMap.put(orderItemDO.getItemSkuId(), orderItemDO);
                itemIds.add(orderItemDO.getItemId());
            }
            orderItemManager.checkItemForGetPaymentUrl(orderItemDOs,appKey);
            
            
            int paymentId = 1;

            if (orderDO.getPaymentId() != null) {
                paymentId = orderDO.getPaymentId().intValue();
            }
            ClientExecutor clientExecutor = clientExecutorFactory.getExecutor(paymentId+"");
            if(null==clientExecutor){
            	return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_NOT_EXIST_PAYMENT); 
            }
            context.put("orderDO", orderDO);
            context.put("bizPropertyMap", bizPropertyMap);
            PaymentUrlDTO paymentUrlDTO = clientExecutor.getPaymentUrl(context);
            response = new TradeResponse(paymentUrlDTO);
            return response;
        }catch (TradeException e){
        	log.error("get paymentUrl error",e);
        	log.error("GetPaymentUrl error",e);
            return new TradeResponse<PaymentUrlDTO>(e.getResponseCode());
        }catch (Exception e){
        	log.error("GetPaymentUrl error",e);
            return new TradeResponse<PaymentUrlDTO>(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
    }

    public String getName() {
        return ActionEnum.GET_PAYMENT_URL.getActionName();
    }

    private PaymentUrlDTO genPayInfo(OrderDO orderDO,Map<String,BizPropertyDTO> appPropertyMap,String bizName) throws TradeException{
        PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

        BizPropertyDTO returnUrlAppProperty = null;
        BizPropertyDTO notifyUrlAppProperty = null;
        
        if (orderDO.getPaymentId() == null) {
            //TODO error handle
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        if (orderDO.getOrderSn() == null) {
            //TODO error handle
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        if (orderDO.getTotalAmount() == null) {
            //TODO error handle
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        String tradeNo = orderDO.getOrderSn();
        long totalFee = orderDO.getTotalAmount().longValue();
        int paymentId = orderDO.getPaymentId().intValue();

        String payUrl = null;

        try {
            if (paymentId == 1) {
            	returnUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_RETURN_URL);
            	notifyUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_NOTIFY_URL);
            	if(null==returnUrlAppProperty||null==notifyUrlAppProperty){
            		throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
            	}
                payUrl = "https://www.alipay.com/cooperate/gateway.do";
                Map params = null;
                if(orderDO.getBizCode().equals("yangdongxi")){
                	   String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALezOUhup02v08jKupa9LKmVsoD9G04P2YNBpT5vyb2MYRIa4pYwWeHcLqeGXtde54HlvT1uE9E29jWYR12ITrxpcGZ8EWJ18mAaqAdZ0osVgxsX1dBYWxpRmSmMAvbi/f7kJouzIzsigCKlCjAd7enF5/+Vu/g66P2nWoZR3kY9AgMBAAECgYAsNjFaMm+HrgKdt9UShHMkWYf9rW0N65ihE4KOtV7rhMa0Ec5o8TgguNptrVRUJ142kDFsgHq6hqzZF05Nv4mbPE0kYMbyAUJSJmFMhtAJOcL2hB+U0yO57uwPUlaD4kB8TsazcjA9uIXiKKYAutqTtLW/lnOX4qWp9IF5qzrZTQJBAOwvd2IuGIZo8fwK1P53/FRXx5+EBRRCWKULmdi4iThfVthTXUowMuBAPWaBxylcyClfmJwJ51HYQ1HGjqgcfq8CQQDHHIjiW93z0PuWYtm0j/iHzvSBW7IAAHVp6V869lHNipIMyEmELC41f8AiHGyDGJzLZgFnMsAYlCJy3dxq0uTTAkEA1uXBbDWg3vsx4jBA6GBn2J4d5ggLTwmm+lT54HTXddFZhW8knNIKGHya4WAHxJzFCtAOXTutm4x4hDlzp4Z1xQJAN5eV+G1h6QM+W1y6IBnacECuL7fkWO/H2IxaFGJVsKex43PAYvDa7gD/Kgb5nRiwHnIaji+zRqmFfMDJG+JKFQJAeUoW12gEVXIaG7qt6C9gevHLBl+h9+QXz2DlquahhYzhlLYqcaTHTnLT6TqLRbkfaJop018RFocsXa7onuiTTw==";
                	   //
                	params = getMockuaiAlipayParam(orderDO,returnUrlAppProperty,notifyUrlAppProperty,
                			"2088311997503550","zhifu@yangdongxi.com",privateKey,bizName);
                }else{
                	params = getMockuaiAlipayParam(orderDO,returnUrlAppProperty,notifyUrlAppProperty,MockuaiConfig.alipay_partner,MockuaiConfig.alipay_seller_id,
                			MockuaiConfig.mockuai_private_key,bizName);
                }
                 
                paymentUrlDTO.setRequestMethod(1);
                paymentUrlDTO.setParams(params);
                paymentUrlDTO.setPayType(1);
            } else if (paymentId == 2) {
            	notifyUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.WECHAT_NOTIFY_URL);
            	if(null==notifyUrlAppProperty){
            		throw new TradeException("notifyUrlAppProperty is null");
            	}
            	
                String appId = "wx78ebcaf0d747991e";
                String partnerId = "1236287801";
                String partnerKey = "48d15a39462fbe06f6391328ff685954";
                String appSecret = "a7bd405156459a78433afe60e523721a";
                String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
                String outTradeNo = orderDO.getOrderSn();
                String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
                String attachData = orderUid;
                if(!orderDO.getBizCode().equals("yangdongxi")){
                	  appId = "wx1798992a7488963c";
                      partnerId = "1247585201";
                      partnerKey = "e0cf8509911342e029ab77fa1a513aeb";
                      appSecret = "9290c6da2d77844711101fab8dc455a8";
                      nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
                }
                Map paramMap = getWxPayParam(appId, nonceStr, partnerId, partnerKey, outTradeNo, attachData, orderDO,
                        notifyUrlAppProperty,bizName);
                paymentUrlDTO.setRequestMethod(2);
                paymentUrlDTO.setPayType(2);
                paymentUrlDTO.setParams(paramMap);
            } else if (paymentId == 3) {
            	returnUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_RETURN_URL);
            	notifyUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_NOTIFY_URL);
            	if(null==returnUrlAppProperty||null==notifyUrlAppProperty){
            		throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
            	}
                payUrl = "http://payment-test.chinapay.com/pay/TransGet";
                Map paramMap = getUnionPayParamByOldVersion(orderDO,returnUrlAppProperty,notifyUrlAppProperty,bizName);
                paymentUrlDTO.setRequestMethod(2);
                paymentUrlDTO.setPayType(3);
                paymentUrlDTO.setParams(paramMap);
            }
        } catch (Exception e) {
            log.error("orderId="+orderDO.getId()+",userId="+orderDO.getUserId(), e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        paymentUrlDTO.setPayUrl(payUrl);
        paymentUrlDTO.setPayAmount(orderDO.getTotalAmount());

        return paymentUrlDTO;
    }
    
    private Map<String, String> getMockuaiAlipayParam(OrderDO orderDO,BizPropertyDTO returnUrl,BizPropertyDTO notifyUrl,
    		String alipay_partner,
    		String alipaySellerId,
    		String privateKey,
    		String bizName
    		) {
        Map params = new HashMap();
        try {
            Map<String, String> paramMap = new TreeMap<String, String>();
            paramMap.put("service", "mobile.securitypay.pay");
            paramMap.put("partner", alipay_partner);
            paramMap.put("notify_url", notifyUrl.getValue());
            // 设置参数
            String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
          
            
            paramMap.put("return_url", getReturnUrl(returnUrl.getValue(),orderDO));
            paramMap.put("_input_charset", "utf-8");
            paramMap.put("subject", bizName+"商品");//TODO 待重构，组成规则为（应用名称＋‘商品’）
            paramMap.put("body", bizName+"商品");
            //TODO 支付金额的格式需要充分测试
            paramMap.put("total_fee", String.format("%.2f", ((double)(orderDO.getTotalAmount())/100)));
            paramMap.put("out_trade_no", orderUid);
            paramMap.put("payment_type", "1");
            paramMap.put("seller_id", alipaySellerId);
            paramMap.put("it_b_pay", "30m");

            StringBuilder paramSb = new StringBuilder();
            for (Entry<String, String> entry : paramMap.entrySet()) {
                paramSb.append((String) entry.getKey());
                paramSb.append("=");
                paramSb.append("\"");
                paramSb.append((String) entry.getValue());
                paramSb.append("\"");
                paramSb.append("&");
            }

            paramSb.deleteCharAt(paramSb.length() - 1);
            String signStr = SignUtils.sign(paramSb.toString(), privateKey);
            signStr = URLEncoder.encode(signStr, "utf-8");
            String paramStr = new StringBuilder().append(paramSb.toString()).append("&sign_type=\"RSA\"&sign=\"").append(signStr).append("\"").toString();
            params.put("param", paramStr);
        } catch (Exception e) {
            log.error("", e);
        }
        return params;
    }
    
    private String getReturnUrl(String returnUrl,OrderDO orderDO)throws TradeException{
    	
    	String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
    	
    	Map<String,String> params = new HashMap<String,String>();
        params.put("order_sn", orderDO.getOrderSn());
        params.put("order_uid", orderUid);
        params.put("pay_amount", orderDO.getTotalAmount()+"");
        params.put("pay_type", orderDO.getPaymentId()+"");
        
        
    	 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
         for (Map.Entry<String, String> entry : params.entrySet()) {
             NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
             pairs.add(nameValuePair);
         }
    	  
         try {
        	 String str = EntityUtils.toString(new UrlEncodedFormEntity(pairs, "utf-8"));
        	 return returnUrl+"?"+str;
         }catch (UnsupportedEncodingException e) {
        	 log.error("alipay getReturnUrl error",e);
        	 throw new TradeException("alipay getReturnUrl error",e);
         } catch (IOException e) {
        	 log.error("alipay getReturnUrl error",e);
        	 throw new TradeException("alipay getReturnUrl error",e);
         }
        
          
    	}

//    private Map<String, String> getAlipayParam(OrderDO orderDO,BizPropertyDTO returnUrl,BizPropertyDTO notifyUrl) {
//        Map params = new HashMap();
//        try {
//            Map<String, String> paramMap = new TreeMap<String, String>();
//            paramMap.put("service", "mobile.securitypay.pay");
//            paramMap.put("partner", "2088311997503550");
//            paramMap.put("notify_url", notifyUrl.getValue());
//            String alipayReturnUrl = getReturnUrl(returnUrl.getValue(),orderDO);
//            paramMap.put("return_url", alipayReturnUrl);
//            paramMap.put("_input_charset", "utf-8");
//            paramMap.put("subject", "洋东西商品");//TODO 待重构，组成规则为（应用名称＋‘商品’）
//            paramMap.put("body", "洋东西商品");
//            //TODO 支付金额的格式需要充分测试
//            paramMap.put("total_fee", String.format("%.2f", ((double)(orderDO.getTotalAmount())/100)));
//            String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
//            paramMap.put("out_trade_no", orderUid);
//            paramMap.put("payment_type", "1");
//            paramMap.put("seller_id", "zhifu@yangdongxi.com");
//            paramMap.put("it_b_pay", "30m");
//
//            StringBuilder paramSb = new StringBuilder();
//            for (Entry<String, String> entry : paramMap.entrySet()) {
//                paramSb.append((String) entry.getKey());
//                paramSb.append("=");
//                paramSb.append("\"");
//                paramSb.append((String) entry.getValue());
//                paramSb.append("\"");
//                paramSb.append("&");
//            }
//
//
//            String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALezOUhup02v08jKupa9LKmVsoD9G04P2YNBpT5vyb2MYRIa4pYwWeHcLqeGXtde54HlvT1uE9E29jWYR12ITrxpcGZ8EWJ18mAaqAdZ0osVgxsX1dBYWxpRmSmMAvbi/f7kJouzIzsigCKlCjAd7enF5/+Vu/g66P2nWoZR3kY9AgMBAAECgYAsNjFaMm+HrgKdt9UShHMkWYf9rW0N65ihE4KOtV7rhMa0Ec5o8TgguNptrVRUJ142kDFsgHq6hqzZF05Nv4mbPE0kYMbyAUJSJmFMhtAJOcL2hB+U0yO57uwPUlaD4kB8TsazcjA9uIXiKKYAutqTtLW/lnOX4qWp9IF5qzrZTQJBAOwvd2IuGIZo8fwK1P53/FRXx5+EBRRCWKULmdi4iThfVthTXUowMuBAPWaBxylcyClfmJwJ51HYQ1HGjqgcfq8CQQDHHIjiW93z0PuWYtm0j/iHzvSBW7IAAHVp6V869lHNipIMyEmELC41f8AiHGyDGJzLZgFnMsAYlCJy3dxq0uTTAkEA1uXBbDWg3vsx4jBA6GBn2J4d5ggLTwmm+lT54HTXddFZhW8knNIKGHya4WAHxJzFCtAOXTutm4x4hDlzp4Z1xQJAN5eV+G1h6QM+W1y6IBnacECuL7fkWO/H2IxaFGJVsKex43PAYvDa7gD/Kgb5nRiwHnIaji+zRqmFfMDJG+JKFQJAeUoW12gEVXIaG7qt6C9gevHLBl+h9+QXz2DlquahhYzhlLYqcaTHTnLT6TqLRbkfaJop018RFocsXa7onuiTTw==";
//
//            paramSb.deleteCharAt(paramSb.length() - 1);
//            System.out.println(new StringBuilder().append("paramSb:").append(paramSb.toString()).toString());
//            String signStr = SignUtils.sign(paramSb.toString(), privateKey);
//            signStr = URLEncoder.encode(signStr, "utf-8");
//            String paramStr = new StringBuilder().append(paramSb.toString()).append("&sign_type=\"RSA\"&sign=\"").append(signStr).append("\"").toString();
//            params.put("param", paramStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return params;
//    }

    private Map<String, String> getUnionPayParamByOldVersion(OrderDO orderDO,BizPropertyDTO returnUrl,BizPropertyDTO notifyUrl,String bizName) {
        // 请求要素
        Map<String, String> req = new HashMap<String, String>();
        req.put("version", UpmpConfig.VERSION);// 版本号
        req.put("charset", UpmpConfig.CHARSET);// 字符编码
        req.put("transType", "01");// 交易类型
        req.put("merId", UpmpConfig.MER_ID);// 商户代码
        req.put("backEndUrl", notifyUrl.getValue());// 通知URL
        try {
			String unipayReturnUrl = getReturnUrl(returnUrl.getValue(),orderDO);
			req.put("frontEndUrl",unipayReturnUrl);// 前台通知URL(可选)
		} catch (TradeException e) {
			log.error("get frontUrl error",e);
		}
        
      
//        req.put("orderDescription", "洋东西商品");// 订单描述(可选)
        req.put("orderDescription",bizName );
        req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));// 交易开始日期时间yyyyMMddHHmmss
        req.put("orderTimeout", "");// 订单超时时间yyyyMMddHHmmss(可选)
        String orderUidForUnion = ""+orderDO.getSellerId()+"x"+orderDO.getUserId()+"x"+orderDO.getId();
        req.put("orderNumber", ""+orderUidForUnion);//订单号(商户根据自己需要生成订单号)
        req.put("orderAmount", ""+orderDO.getTotalAmount());// 订单金额
        req.put("orderCurrency", "156");// 交易币种(可选)
        String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
        req.put("reqReserved", ""+orderUid);// 请求方保留域(可选，用于透传商户信息)

        Map<String, String> resp = new HashMap<String, String>();
        boolean validResp = UpmpService.trade(req, resp);

        Map paramMap = new HashMap();
        if(validResp == true){
            paramMap.put("tn", resp.get("tn"));
        }else{
            //TODO error handle
        }
        return paramMap;
    }

    private Map<String, String> getUnionPayParam(OrderDO orderDO) {
//        String signType = "MD5";
//
//        if (!"SHA1withRSA".equalsIgnoreCase(signType)) {
//            signType = "MD5";
//        }
//
//        SDKConfig.getConfig().loadPropertiesFromSrc();
//        Map data = new HashMap();
//        data.put("version", "5.0.0");
//        data.put("encoding", "UTF-8");
//        data.put("signMethod", "MD5");
//        data.put("txnType", "01");
//        data.put("txnSubType", "01");
//        data.put("bizType", "00");
//        data.put("bizSubType", "01");
//        data.put("channelType", "08");
//        data.put("frontUrl", "");
//        data.put("backUrl", "http://api.mockuai.com/trade/order/payment/callback/unionpay_notify");
//        data.put("accessType", "0");
//        data.put("merId", "104330154990009");
//        String orderUidForUnion = ""+orderDO.getSellerId()+"x"+orderDO.getUserId()+"x"+orderDO.getId();
//        data.put("orderId", orderUidForUnion);
//        data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//        data.put("txnAmt", ""+orderDO.getTotalAmount());
//        data.put("currencyCode", "156");
//        String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
//        data.put("reqReserved", orderUid);
//        data.put("orderDesc", "洋东西商品");//TODO 待重构，组成规则为（应用名称＋‘商品’）
//        data.put("signature", getUnionParamSign(data, "3nJ0bFaytXawLsbngP9CT4ZhRKDN8DSi"));

        Map<String,String> data = QuickPaySampleServLet.getReqParamMap();
        data.put("version",QuickPayConf.version);
        data.put("charset",QuickPayConf.charset);
        data.put("transType","01");
//        data.put("origQid","");
        data.put("merId","105550149170027");
        data.put("merAbbr","test");
//        data.put("acqCode","");
//        data.put("merCode","");
//        data.put("commodityUrl","");
        data.put("commodityName","test");
        data.put("commodityUnitPrice","1");
        data.put("commodityQuantity","1");
        data.put("commodityDiscount","0");
        data.put("transferFee","1");
        data.put("orderNumber",""+System.currentTimeMillis());
        data.put("orderAmount","1");
        data.put("orderCurrency","156");
        data.put("orderTime",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        data.put("customerIp","127.0.0.1");
        data.put("customerName","test");
//        data.put("defaultPayType","");
//        data.put("defaultBankNumber","");
        data.put("transTimeout","300000");
        data.put("frontEndUrl","http://127.0.0.1/");
        data.put("backEndUrl","http://127.0.0.1/");
//        data.put("merReserved","");
        data.put("signature", getUnionParamSign(data, "88888888"));
        data.put("signMethod", "MD5");

        String requestAppUrl = "http://58.246.226.99/UpopWeb/api/";

        Map resmap = submitUrl(data, requestAppUrl);
        Map paramMap = new HashMap();
        paramMap.put("tn", resmap.get("tn"));
        return paramMap;
    }

//    private Map<String, String> signData(Map<String, ?> contentData) {
//        Entry obj = null;
//        Map submitFromData = new HashMap();
//        for (Iterator it = contentData.entrySet().iterator(); it.hasNext(); ) {
//            obj = (Entry) it.next();
//            String value = (String) obj.getValue();
//            if (StringUtils.isNotBlank(value)) {
//                submitFromData.put(obj.getKey(), value.trim());
//                System.out.println(new StringBuilder().append((String) obj.getKey()).append("-->").append(String.valueOf(value)).toString());
//            }
//
//        }
//
//        SDKUtil.sign(submitFromData, "UTF-8");
//
//        return submitFromData;
//    }

    private String getUnionParamSign(Map<String, String> paramMap, String partnerKey) {
        Map<String,String> signMap = new TreeMap<String, String>();
        for(Entry<String,String> entry: paramMap.entrySet()){
            if("signMethod".equals(entry.getKey())==false && "signature".equals(entry.getKey())==false){
                signMap.put(entry.getKey(), entry.getValue());
            }
        }

        StringBuilder signSb = new StringBuilder();
        for (Entry entry : signMap.entrySet()) {
            signSb.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("&");
        }

        String toSignStr = new StringBuilder().append(signSb.toString()).append("&").append(DigestUtils.md5Hex(partnerKey)).toString();
        System.out.println(new StringBuilder().append("signSb:").append(toSignStr).toString());

        String sign = DigestUtils.md5Hex(toSignStr);
        return sign;
    }

    public Map<String, String> submitUrl(Map<String, String> submitFromData, String requestUrl) {
        String resultString = "";

        HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
        Map resData = Collections.emptyMap();
        try {
            int status = hc.send(submitFromData, "UTF-8");
            if (200 == status){
                resultString = hc.getResult();
                System.out.println("resultStr:"+resultString);
            }
            resData = JsonUtil.parseJson(resultString, Map.class);

        } catch (Exception e) {
            log.error("error to submit", e);
        }

        return resData;
    }

    private Map<String, String> getWxPayParam(String appId, String noncestr, String partnerid, String partnerKey,
                                              String outTradeNo, String attach, OrderDO orderDO,BizPropertyDTO notifyDTO,String bizName) throws TradeException{
        String timestamp = new StringBuilder().append("").append(System.currentTimeMillis() / 1000L).toString();

        Map resMap = getWxPrepayId(appId, partnerid, partnerKey, noncestr, outTradeNo, attach, orderDO,notifyDTO,bizName);
        if(resMap.containsKey("prepay_id") == false){//获取prepayId失败
            log.error("wechat resp:{}", JsonUtil.toJson(resMap));
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        Map paramMap = new TreeMap();
        paramMap.put("appid", appId);
        paramMap.put("prepayid", resMap.get("prepay_id"));
        paramMap.put("partnerid", partnerid);
        paramMap.put("package", "Sign=WXPay");
        paramMap.put("noncestr", resMap.get("nonce_str"));
        paramMap.put("timestamp", timestamp);

        paramMap.put("sign", getWxParamSign(paramMap, partnerKey));

        return paramMap;
    }

    private String getWxParamSign(Map<String, String> paramMap, String partnerKey) {
        StringBuilder signSb = new StringBuilder();
        for (Entry entry : paramMap.entrySet()) {
            signSb.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("&");
        }

        String toSignStr = new StringBuilder().append(signSb.toString()).append("key=").append(partnerKey).toString();

        String sign = DigestUtils.md5Hex(toSignStr).toUpperCase();
        return sign;
    }

    private Map<String, String> getWxPrepayId(String appId, String partnerId, String partnerKey,
                                              String noncestr, String outTradeNo, String attach, OrderDO orderDO,
                                              BizPropertyDTO notifyDTO,
                                              String bizName
    		) {
        String reqUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        try {
            Map paramMap = new TreeMap();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", partnerId);
            paramMap.put("body", bizName+"商品");
            paramMap.put("nonce_str", noncestr);
            paramMap.put("out_trade_no", outTradeNo);

            paramMap.put("total_fee", ""+orderDO.getTotalAmount());
            paramMap.put("spbill_create_ip", "183.157.67.92");//TODO ip确认
            paramMap.put("notify_url",notifyDTO.getValue());
            paramMap.put("trade_type", "APP");
            paramMap.put("attach",attach);
            paramMap.put("sign", getWxParamSign(paramMap, partnerKey));

//            System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

            String xmlData = XmlUtil.map2XmlStr(paramMap);

            //FIXME 注意，这里必须要对转出来的xmlData进行ISO8859-1编码，否则会报签名错误
            String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
            return XmlUtil.xmlStr2Map(response);
        } catch (Exception e) {
            log.error("error to getWxPrepayId", e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        GetPaymentUrl getPaymentUrl = new GetPaymentUrl();

        OrderDO orderDO = new OrderDO();
        orderDO.setOrderSn(""+System.currentTimeMillis());
        orderDO.setBizCode("yangdongxi");
        orderDO.setTotalAmount(1L);
        orderDO.setSellerId(1L);
        orderDO.setUserId(1L);
        orderDO.setId(System.currentTimeMillis());

        String appId = "wx78ebcaf0d747991e";
        String partnerId = "1236287801";
        String partnerKey = "48d15a39462fbe06f6391328ff685954";
        String appSecret = "a7bd405156459a78433afe60e523721a";
        String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
        String outTradeNo = orderDO.getOrderSn();
        String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
        String attachData = orderUid;
        if(orderDO.getBizCode().equals("yangdongxi")){
            appId = "wx337fa5329c15abf5";
            partnerId = "1327003501";
            partnerKey = "Jiangcai456ZCZ33032719890911443X";
            appSecret = "9290c6da2d77844711101fab8dc455a8";
            nonceStr = "5262981c662b15a1c7eaee1adfbd3685";
        }
        BizPropertyDTO bizPropertyDTO = new BizPropertyDTO();
        bizPropertyDTO.setValue("http://api.mockuai.com/trade/order/payment/callback/wechat_notify");
        Map paramMap = getPaymentUrl.getWxPayParam(appId, nonceStr, partnerId, partnerKey, outTradeNo, attachData, orderDO,
                bizPropertyDTO,"测试");
        System.out.println(JsonUtil.toJson("response:"+JsonUtil.toJson(paramMap)));
    }
}

/* Location:           /work/tmp/tradecenter/WEB-INF/classes/
 * Qualified Name:     com.mockuai.tradecenter.core.service.action.payment.GetPaymentUrl
 * JD-Core Version:    0.6.2
 */