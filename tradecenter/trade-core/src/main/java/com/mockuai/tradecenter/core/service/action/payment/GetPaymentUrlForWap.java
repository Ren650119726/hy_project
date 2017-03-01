package com.mockuai.tradecenter.core.service.action.payment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.util.ModelUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.util.AlipaySubmit;
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
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.upmp.sdk.conf.UpmpConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class GetPaymentUrlForWap implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetPaymentUrlForWap.class);


    @Resource
    private OrderManager orderManager;

    @Resource
    private OrderItemManager orderItemManager;

    @Resource
    private ItemManager itemManager;

    @Resource
    private CacheManager cacheManager;
    
    @Resource
    private ClientExecutorFactory clientExecutorFactory;

    @Autowired
    private MsgQueueManager msgQueueManager;

    public GetPaymentUrlForWap() {
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }
    
    
    @SuppressWarnings("unchecked")
	public TradeResponse<PaymentUrlDTO> execute(RequestContext context)
            throws TradeException {
        Request request = context.getRequest();
        String appKey = (String)context.get("appKey");
        String bizCode = (String)context.get("bizCode");
        Map<String,BizPropertyDTO> bizPropertyMap =   (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
        if(null==bizPropertyMap){
        	throw new TradeException(bizCode+" bizPropertyMap is null");
        }
        
        String bizName = (String) context.get("bizName");
        
        TradeResponse response = null;
        String wxAuthCode = (String)request.getParam("wxAuthCode");
        boolean isYangdongxiRequest = TradeUtil.isYangdongxiRequest(bizCode);
        if (request.getParam("orderId") == null) {
            log.error("orderId is null");
            response = ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
        }

        if (request.getParam("userId") == null) {
            log.error("userId is null");
            response = ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
        }

        Integer payType = null;
        if (request.getParam("payType") != null) {
            payType = Integer.parseInt((String) request.getParam("payType"));
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

            //待支付价格无效时，直接返回错误提示。否则调用微信支付也会失败
            if(orderDO.getTotalAmount()==null || orderDO.getTotalAmount()<= 0){
//                return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_E_ORDER_STATUS_ERROR,
//                        "订单状态错误");
            	orderManager.updateOrderPayType(orderDO, 0);
            	
            	orderManager.orderPaySuccess(orderDO.getId(),null, orderDO.getUserId());
            	
            	try{
                    //发送支付成功内部mq消息
                    msgQueueManager.sendPaySuccessMsg(orderDO);
                } catch (Exception e){
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
            
            
            //判断下单商品的有效性
            /**
            List<ItemDTO> itemDTOs = itemPlatformManager.queryItem(
                    new CopyOnWriteArrayList<Long>(itemIds), orderItemDOs.get(0).getSellerId(), appKey);

            if(itemDTOs.size() < itemIds.size()){
                return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_E_ORDER_ITEM_INVALID,
                        "部分订单商品无效或已被删除");
            }
            for(ItemDTO itemDTO: itemDTOs){
                //TODO 这里的商品状态需要找商品平台将商品状态枚举常量暴露出来，然后依赖枚举常量。不能直接依赖数字常量
                if(itemDTO.getItemStatus().intValue() != 4){
                    return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_E_ORDER_ITEM_STATUS_INVALID,
                            "部分订单商品已下架或未上架");
                }
            }  
            

            //判断下单商品sku的有效性及库存是否足够
            
            
            List<ItemSkuDTO> itemSkuDTOs = itemPlatformManager.queryItemSku(
                    new CopyOnWriteArrayList<Long>(orderItemMap.keySet()), orderItemDOs.get(0).getSellerId(), appKey);
            if(itemSkuDTOs.size() < orderItemDOs.size()){
                return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_E_ORDER_ITEM_SKU_INVALID,
                        "部分订单商品sku无效或已被删除");
            }

            for(ItemSkuDTO itemSkuDTO: itemSkuDTOs){
                if(orderItemMap.containsKey(itemSkuDTO.getId()) == false){
                    return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_E_ORDER_ITEM_SKU_INVALID,
                            "部分订单商品sku无效或已被删除");
                }

            }

            **/
            //判断下单商品sku的有效性及库存是否足够
            
            long startTime = System.currentTimeMillis();
            int paymentId = 1;

            if (orderDO.getPaymentId() != null) {
                paymentId = orderDO.getPaymentId().intValue();
            }
            ClientExecutor clientExecutor =  clientExecutorFactory.getExecutor(paymentId+"");
            if(null==clientExecutor){
            	return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_NOT_EXIST_PAYMENT); 
            }
            context.put("orderDO", orderDO);
            context.put("wxAuthCode", wxAuthCode);
            context.put("bizPropertyMap", bizPropertyMap);
            PaymentUrlDTO paymentUrlDTO = clientExecutor.getPaymentUrl(context);
            
            log.info("execute, cost:{}, wxAuthCode:{}, paymentUrl:{}",
                    (System.currentTimeMillis()-startTime), wxAuthCode, JsonUtil.toJson(paymentUrlDTO));
            response = new TradeResponse(paymentUrlDTO);
            return response;
        }catch (TradeException e){
        	log.error("get paymentUrlForWap error",e);
            return new TradeResponse<PaymentUrlDTO>(e.getResponseCode());
        }catch (Exception e){
        	log.error("get paymentUrlForWap error",e);
            return new TradeResponse<PaymentUrlDTO>(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
    }

    public String getName() {
        return ActionEnum.GET_PAYMENT_URL_FOR_WAP.getActionName();
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
    
    private Map<String, String> getMockAlipayParams(OrderDO orderDO,BizPropertyDTO returnUrl,BizPropertyDTO notifyUrl) 
    		throws TradeException{
    	
    	 Map<String, String> paramMap = new HashMap<String, String>();
         paramMap.put("service", "alipay.wap.create.direct.pay.by.user");
         paramMap.put("partner", MockuaiConfig.alipay_partner);
         paramMap.put("seller_id", MockuaiConfig.alipay_seller_id);
         paramMap.put("_input_charset", com.alipay.config.AlipayConfig.input_charset);
         paramMap.put("payment_type", "1");
//         paramMap.put("notify_url", "http://api.mockuai.com/trade/order/payment/callback/alipay_notify");
         paramMap.put("notify_url", notifyUrl.getValue());
         // 设置参数
         String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
       
         String alipayReturnUrl = getReturnUrl(returnUrl.getValue(),orderDO);
         
       
//         paramMap.put("return_url", "http://m.mockuai.com/html/pay-success.html?order_sn="+orderDO.getOrderSn()+"&order_uid="+orderUid+"&pay_amount="+orderDO.getTotalAmount()+"&pay_type=4");
         paramMap.put("return_url", alipayReturnUrl);
         
         paramMap.put("out_trade_no", orderUid);
         paramMap.put("subject", "魔筷商品");
         paramMap.put("total_fee", String.format("%.2f", ((double)(orderDO.getTotalAmount())/100)));
         paramMap.put("body", "魔筷商品");
         paramMap.put("it_b_pay", "30m");
        
         return paramMap;
    }

    private PaymentUrlDTO genPayInfo(OrderDO orderDO, Integer payType, String wxAuthCode,boolean isYangdongxiRequest,
    		Map<String,BizPropertyDTO> appPropertyMap,String bizName)  throws TradeException{
        PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

        //TODO 以下写死参数上线前需要改掉
        String tradeNo = null;
        long totalFee = 1L;

        int paymentId = 1;

        if (orderDO.getPaymentId() != null) {
            paymentId = orderDO.getPaymentId().intValue();
        }

        if (orderDO.getOrderSn() != null) {
            tradeNo = orderDO.getOrderSn();
        }

        if (orderDO.getTotalAmount() != null) {
            totalFee = orderDO.getTotalAmount().longValue();
        }

        String payUrl = null;

        BizPropertyDTO returnUrlAppProperty = null;
        BizPropertyDTO notifyUrlAppProperty = null;

        //TODO 这里为了调试方便，让外部控制payType
        if(payType != null){
            paymentId = payType;
        }
        try {
            if (paymentId == 1) {

            } else if (paymentId == 2) {

            } else if (paymentId == 3) {

            }else if(paymentId == 4){//支付宝H5快捷支付
            	returnUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_RETURN_URL);
            	notifyUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_NOTIFY_URL);
            	 
            	if(null==returnUrlAppProperty||null==notifyUrlAppProperty){
            		throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
            	}
            	
            	Map<String, String> paramMap = new HashMap<String, String>();
            	 String privateKey = com.alipay.config.AlipayConfig.private_key;
            	 if(!isYangdongxiRequest){
            		 paramMap = getMockAlipayParams(orderDO,returnUrlAppProperty,notifyUrlAppProperty);
            		 privateKey = MockuaiConfig.mockuai_private_key;
            	 }
            	 else{
            		 paramMap.put("service", "alipay.wap.create.direct.pay.by.user");
                     paramMap.put("partner", com.alipay.config.AlipayConfig.partner);
                     paramMap.put("seller_id", com.alipay.config.AlipayConfig.seller_id);
                     paramMap.put("_input_charset", com.alipay.config.AlipayConfig.input_charset);
                     paramMap.put("payment_type", "1");
                     paramMap.put("notify_url", notifyUrlAppProperty.getValue());
                     paramMap.put("return_url", getReturnUrl(returnUrlAppProperty.getValue(),orderDO));
                     String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
                     paramMap.put("out_trade_no", orderUid);
                     paramMap.put("subject", bizName+"商品");
                     paramMap.put("total_fee", String.format("%.2f", ((double)(orderDO.getTotalAmount())/100)));
                     paramMap.put("body", bizName+"商品");
                     paramMap.put("it_b_pay", "30m");
            	 }
               
                //建立请求
                String sHtmlText = AlipaySubmit.buildRequest(paramMap, "post", "确认",privateKey);
                paymentUrlDTO.setRequestMethod(2);
                paymentUrlDTO.setPayType(4);
                paramMap = new HashMap<String, String>();
                paramMap.put("request_form", sHtmlText);
                paymentUrlDTO.setParams(paramMap);
            } else if(paymentId == 5){//微信H5快捷支付
                if(StringUtils.isBlank(wxAuthCode)){
                    //TODO error handle
                	 log.error("wxAuthCode is null");
                }
            	notifyUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.WECHAT_NOTIFY_URL);
                
            	if(null==notifyUrlAppProperty){
            		throw new TradeException("notifyUrlAppProperty is null");
            	}
            	
            	if(!isYangdongxiRequest){
                	 String appId = "wx1798992a7488963c";
                     String partnerId = "1247585201";
                     String partnerKey = "e0cf8509911342e029ab77fa1a513aeb";//秘钥
                     String appSecret = "9290c6da2d77844711101fab8dc455a8";//appSecret
                     String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";//随机字符串
                     String outTradeNo = orderDO.getOrderSn();
                     String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
                     String attachData = orderUid;
                     Map paramMap = getWxPayParamForH5(appId, nonceStr,
                             partnerId, partnerKey, outTradeNo, attachData, appSecret, wxAuthCode, orderDO, 
                             notifyUrlAppProperty,bizName);
                     paymentUrlDTO.setRequestMethod(2);
                     paymentUrlDTO.setPayType(5);
                     paymentUrlDTO.setParams(paramMap);
                }else{
                	 String appId = "wx791233ecd4e71c3f";
                     String appKey = "a3icaMHFXZ8HfIWVk28UD8kkkQQk3ssbGvYZQjJkDJIKkVNlNurGNScyJ0hjPjrtuNTcpJKL4qBVHpXT1qcpzRacQ2stts5Fcjv4Yn9gIshyxLcaxsxEhnMQJJI2j0sX";
                     String partnerId = "1220845901";
                     String partnerKey = "48d15a39462fbe06f6391328ff685954";
                     String appSecret = "bde7a203a8082025ed97b12821a52d20";
                     String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
                     String outTradeNo = orderDO.getOrderSn();
                     String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
                     String attachData = orderUid;
                     Map paramMap = getWxPayParamForH5(appId, nonceStr,
                             partnerId, partnerKey, outTradeNo, attachData, appSecret, wxAuthCode, orderDO,
                             notifyUrlAppProperty,bizName);
                     paymentUrlDTO.setRequestMethod(2);
                     paymentUrlDTO.setPayType(5);
                     paymentUrlDTO.setParams(paramMap);
                }
               
            } else if(paymentId == 6){//银联H5快捷支付
                payUrl = "http://payment-test.chinapay.com/pay/TransGet";
                returnUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_RETURN_URL);
            	notifyUrlAppProperty = appPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_NOTIFY_URL);
               
            	if(null==returnUrlAppProperty||null==notifyUrlAppProperty){
            		throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
            	}
            	
            	Map paramMap = getUnionPayParam(orderDO,returnUrlAppProperty,notifyUrlAppProperty);
                paymentUrlDTO.setRequestMethod(2);
                paymentUrlDTO.setPayType(6);
                paymentUrlDTO.setParams(paramMap);
            }
        } catch (Exception e) {
            log.error("orderId=" + orderDO.getId() + ",userId=" + orderDO.getUserId(), e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        paymentUrlDTO.setPayUrl(payUrl);
        //待支付金额
        paymentUrlDTO.setPayAmount(orderDO.getTotalAmount());

        return paymentUrlDTO;
    }


    private Map<String, String> getWxPayParamForH5(String appId, String noncestr, String partnerid, String partnerKey,
                                                   String outTradeNo, String attach, String appSecret, String wxAuthCode, OrderDO orderDO,
                                                   BizPropertyDTO notifyDTO,String bizName) {
        String timestamp = new StringBuilder().append("").append(System.currentTimeMillis() / 1000L).toString();

        long startTime = System.currentTimeMillis();
        String openId = getWxOpenId(appId, appSecret, wxAuthCode);
        long prepayIdStartTime = System.currentTimeMillis();
        Map resMap = getWxPrepayIdForH5(appId, partnerid, partnerKey, noncestr, outTradeNo, attach, openId, orderDO,notifyDTO,bizName);
        log.info("getWxOpenId_cost:{}, getWxPrepayIdForH5_cost:{}",
                (prepayIdStartTime-startTime), (System.currentTimeMillis()-prepayIdStartTime));
        Map paramMap = new TreeMap();
        paramMap.put("appId", appId);
        paramMap.put("package", "prepay_id="+resMap.get("prepay_id"));
        paramMap.put("nonceStr", resMap.get("nonce_str"));
        paramMap.put("timeStamp", timestamp);
        paramMap.put("signType", "MD5");
        paramMap.put("paySign", getWxParamSign(paramMap, partnerKey));
        long wxTicketStartTime = System.currentTimeMillis();
        String wxJsTicket = getWxJsTicket(appId, appSecret);
        log.info("getWxJsTicket, cost:{}", (System.currentTimeMillis()-wxTicketStartTime));
        paramMap.put("jsapi_ticket", wxJsTicket);

        return paramMap;
    }


    private String getWxParamSign(Map<String, String> paramMap, String partnerKey) {
        StringBuilder signSb = new StringBuilder();
        for (Entry entry : paramMap.entrySet()) {
            signSb.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("&");
        }

        String toSignStr = new StringBuilder().append(signSb.toString()).append("key=").append(partnerKey).toString();
        System.out.println(new StringBuilder().append("signSb:").append(toSignStr).toString());

        String sign = DigestUtils.md5Hex(toSignStr).toUpperCase();
        return sign;
    }

    private Map<String, String> getWxPrepayIdForH5(String appId, String partnerId, String partnerKey,
                                                   String noncestr, String outTradeNo, String attach, String openId, OrderDO orderDO,
                                                    BizPropertyDTO notifyDTO,String bizName) {
        String reqUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        try {
            long totalFee = orderDO.getTotalAmount();
            Map paramMap = new TreeMap();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", partnerId);
//            paramMap.put("body", "yangdongxi");
            paramMap.put("body",bizName+"商品");
            paramMap.put("nonce_str", noncestr);
            paramMap.put("out_trade_no", outTradeNo);

            paramMap.put("total_fee", ""+totalFee);
            //TODO 参数配置
            paramMap.put("spbill_create_ip", "183.157.67.92");
//            paramMap.put("notify_url", "http://218.244.147.209:8080/trade/order/payment/callback/wechat_notify");
            paramMap.put("notify_url", notifyDTO.getValue());
            paramMap.put("trade_type", "JSAPI");
            paramMap.put("attach",attach);

            paramMap.put("openid",openId);
            paramMap.put("sign", getWxParamSign(paramMap, partnerKey));

            System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

            String xmlData = XmlUtil.map2XmlStr(paramMap);

            //FIXME 注意，这里必须要对转出来的xmlData进行ISO8859-1编码，否则会报签名错误
            String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
            log.info("getWxPrepayIdForH5>>>>>>>>>>>>>>>>>>>>>>>>>paramMap:"+JsonUtil.toJson(paramMap));
            log.info("getWxPrepayIdForH5>>>>>>>>>>>>>>>>>>>>>>>>>response:"+response);
            return XmlUtil.xmlStr2Map(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private Map<String, String> getUnionPayParam(OrderDO orderDO,BizPropertyDTO returnUrl,BizPropertyDTO notifyUrl) throws TradeException{

		Map params = new HashMap();
		String unipayReturnUrl = getReturnUrl(returnUrl.getValue(),orderDO);
		String signType = "MD5";

		if (!"SHA1withRSA".equalsIgnoreCase(signType)) {
			signType = "MD5";
		}

		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

		Map<String, String> data = new HashMap<String, String>();
		String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", UpmpConfig.CHARSET);
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
//		data.put("frontUrl", "http://m.mockuai.com/html/pay-success.html?order_sn="+orderDO.getOrderSn()+"&order_uid="+orderUid+"&pay_amount="+orderDO.getTotalAmount()+"&pay_type=6");
//		data.put("frontUrl","http://192.168.31.181:8090/trade/order/payment/callback/unionpay_notify");
		data.put("frontUrl",unipayReturnUrl);
		// 后台通知地址
//		data.put("backUrl", UpmpConfig.MER_BACK_END_URL);
		data.put("backUrl",notifyUrl.getValue());
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UpmpConfig.MER_ID);
		// 商户订单号，8-40位数字字母
		
		String orderUidForUnion = ""+orderDO.getSellerId()+"x"+orderDO.getUserId()+"x"+orderDO.getId();
	    data.put("orderId", orderUidForUnion);
		
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		data.put("txnAmt", orderDO.getTotalAmount()+"");
		// 交易币种
		data.put("currencyCode", "156");
		
		data.put("reqReserved",orderUid);
		data = signData(data);

		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();

		/**
		 * 创建表单
		 */
		String html = createHtml(requestFrontUrl, data);

		
        Map<String,String> paramMap = new HashMap<String, String>();
        paramMap.put("request_form", html);
		return paramMap;
	}


    /**
     * 构造HTTP POST交易表单的方法示例
     *
     * @param action
     *            表单提交地址
     * @param hiddens
     *            以MAP形式存储的表单键值
     * @return 构造好的HTTP POST交易表单
     */
    public static String createHtml(String action, Map<String, String> hiddens) {
        StringBuffer sf = new StringBuffer();
        sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
        sf.append("<form id = \"pay_form\" action=\"" + action
                + "\" method=\"post\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Entry<String, String>> set = hiddens.entrySet();
            Iterator<Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
                        + key + "\" value=\"" + value + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.pay_form.submit();");
        sf.append("</script>");
        sf.append("</html>");
        return sf.toString();
    }

    private Map<String, String> signData(Map<String, ?> contentData) {
        Entry obj = null;
        Map submitFromData = new HashMap();
        for (Iterator it = contentData.entrySet().iterator(); it.hasNext(); ) {
            obj = (Entry) it.next();
            String value = (String) obj.getValue();
            if (StringUtils.isNotBlank(value)) {
                submitFromData.put(obj.getKey(), value.trim());
                System.out.println(new StringBuilder().append((String) obj.getKey()).append("-->").append(String.valueOf(value)).toString());
            }

        }

        SDKUtil.sign(submitFromData, "UTF-8");

        return submitFromData;
    }

    public Map<String, String> submitUrl(Map<String, String> submitFromData, String requestUrl) {
        String resultString = "";
        System.out.println(new StringBuilder().append("requestUrl====").append(requestUrl).toString());
        System.out.println(new StringBuilder().append("submitFromData====").append(submitFromData.toString()).toString());

        HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
        try {
            int status = hc.send(submitFromData, "UTF-8");
            if (200 == status)
                resultString = hc.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resData = new HashMap();

        if ((null != resultString) && (!"".equals(resultString))) {
            resData = SDKUtil.convertResultStringToMap(resultString);
            if (SDKUtil.validate(resData, "UTF-8"))
                System.out.println("验证签名成功");
            else {
                System.out.println("验证签名失败");
            }

            System.out.println(new StringBuilder().append("打印返回报文：").append(resultString).toString());
        }
        return resData;
    }

    private String getWxJsTicket(String appId, String secret){
        //先查询缓存，如果缓存命中，则直接返回
        String wxJsTicket = (String)cacheManager.get("wxJsTicket_"+appId);
        if(wxJsTicket != null){
            log.info("getWxJsTicket catch the cache, wxJsTicket:{}",wxJsTicket);
            return wxJsTicket;
        }
        long startTime = System.currentTimeMillis();
        String accessToken = getWxAccessToken(appId, secret);// 先拿个accessToken
        log.info("getWxAccessToken, cost:{}", (System.currentTimeMillis()-startTime));
        Map<String,String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        params.put("type", "jsapi");
        String response = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket", params);
        Map<String,String> respMap = JsonUtil.parseJson(response, Map.class);
        wxJsTicket = respMap.get("ticket");
        if(StringUtils.isNotBlank(accessToken)){
            //缓存下accessToken
            cacheManager.set("wxJsTicket_"+appId, 7200, wxJsTicket);
        }
        return wxJsTicket;
    }

    private String getWxAccessToken(String appId, String secret){
        //TODO access_token缓存逻辑实现
        //先查询缓存，如果缓存命中，则直接返回
        String accessToken = (String)cacheManager.get("accessToken_"+appId);
        if(accessToken != null){
            log.info("getWxAccessToken catch the cache, accessToken:{}",accessToken);
            return accessToken;
        }
        Map<String,String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
//        params.put("appid", "wx78ebcaf0d747991e");
//        params.put("secret", "40d07edae4a13340539aec8f1f072342");
        params.put("appid",appId);
      params.put("secret", secret);
        String response = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token", params);
        Map<String,String> respMap = JsonUtil.parseJson(response, Map.class);
//        accessToken = respMap.get("access_token"+appId);
        accessToken = respMap.get("access_token");
        if(StringUtils.isNotBlank(accessToken)){
            //缓存下accessToken
            cacheManager.set("accessToken_"+appId, 7200, accessToken);
        }
        return accessToken;
    }

    public static String getWxOpenId(String appId, String appSecret, String authCode){
        //TODO access_token缓存逻辑实现
        Map<String,String> params = new HashMap<String, String>();
        params.put("grant_type", "authorization_code");
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("code", authCode);
        String response = HttpUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token", params);
        Map<String,String> respMap = JsonUtil.parseJson(response, Map.class);

        log.info("getWxOpenId>>>>>>>>>>>>>>>>response:" + JsonUtil.toJson(respMap));

        return respMap.get("openid");
    }

    public static void main(String[] args) throws Exception {
//        GetPaymentUrl getPaymentUrl = new GetPaymentUrl();

//        System.out.println(new StringBuilder().append("length=").append("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN+C7OBa8ONp6Cu0+c1H5R2vLX3/X8Bg4LaKHRmaK6qHuHmq2CRCyZ9i0jNislJaT9O8eh47sQDgNpuycgK9WaONiPDw/ZZ6ZqXTfnnDMF1UToE4c45y7b+Nr4WKsswHikJwb3RGyJ10iHp0nDjZU/n6+LJ57jfhyVB2WXRyYvDNAgMBAAECgYEAvvOl188Z+c/jMGP+/mgrz/53SSvB7CNYF9tLHTJfl+M3sYpJ+kCs7GSK3Ke3XTAW/vgJBIdOo0bqoH4FdV27k6vlRTDqV1nKno1O/B+jpZFXMEciGOY0NMDi3PocLnr9xosWyYCTI3e11CWG1tHoabkirwHDkSYoogifwl/TBekCQQD8lE6ePet7XiFhiR18VLeKYe/SfoGkUjiu/oq4s+/2mNF3mr9kUw1KF9u0TZpB+gOeba+Fxp+XPkTrYZULGBYfAkEA4onW+fLd4nv7t5GDrXN6Kj+qaocbDYQ9lxB1QF0/EXaT+3ex/RzMIafAvoT5BtzivU7iyAemJXaWhjIeVngjkwJAZ/Mxl/ar344R9u5TcEP9dbpw1RSzjkk4guzHctS7QaYLL0pE0qlLot7G0SHPBrA6pQ1HW6svKzO5cZYrnPFAyQJAUXrqi7RDAnc9rmT0S6rBQfjsPqyhmlb6IB+XTLM9P/a6ezQuHLAC7Af+V4hUWZeRJi39e5zksYzZzyKvrsI/aQJAbCXmK/0Qd8pHV4D7tZCFW4QLPmd6AO61LHP8e0qFmupKYc4hHkrJ2Ewk/1atoPnEzScg6tjoBChnqhlwYK4WNQ==".length()).toString());
//        String privateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3szlIbqdNr9PIyrqWvSyplbKA\n/RtOD9mDQaU+b8m9jGESGuKWMFnh3C6nhl7XXueB5b09bhPRNvY1mEddiE68aXBm\nfBFidfJgGqgHWdKLFYMbF9XQWFsaUZkpjAL24v3+5CaLsyM7IoAipQowHe3pxef/\nlbv4Ouj9p1qGUd5GPQIDAQAB";
//
//        System.out.println(new StringBuilder().append("privateKey:").append(new String(privateKey.getBytes("us-ascii"), "utf-8")).toString());
//        System.out.println(new StringBuilder().append("data:").append(JsonUtil.toJson(getPaymentUrl.getAlipayParam())).toString());
//        getPaymentUrl.getAlipayParamForH5();

//        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx791233ecd4e71c3f&redirect_uri=http://www.yangdongxi.com:8080/&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//        System.out.println(getWxOpenId("wx791233ecd4e71c3f","bde7a203a8082025ed97b12821a52d20","041cdf8f50f0aaabc681194fa30d635-"));


//        GetPaymentUrlForWap gp = new GetPaymentUrlForWap();
//        String appId = "wx791233ecd4e71c3f";
//        String appKey = "a3icaMHFXZ8HfIWVk28UD8kkkQQk3ssbGvYZQjJkDJIKkVNlNurGNScyJ0hjPjrtuNTcpJKL4qBVHpXT1qcpzRacQ2stts5Fcjv4Yn9gIshyxLcaxsxEhnMQJJI2j0sX";
//        String partnerId = "1220845901";
//        String partnerKey = "e0cf8509911342e029ab77fa1a513aeb";
//        String appSecret = "bde7a203a8082025ed97b12821a52d20";
//        String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
//        //TODO 这里为了调试方便，以下tradeNo与orderUid都先用时间戳代替
//        String outTradeNo = ""+System.currentTimeMillis();
//        String orderUid = "1_85_10";
//        String attachData = orderUid;
//        OrderDO orderDO = new OrderDO();
//        orderDO.setTotalAmount(100L);
//        Map paramMap = gp.getWxPayParamForH5(appId, nonceStr,
//                partnerId, partnerKey, outTradeNo, attachData, appSecret, "01173a17e2dc4a5f9ddbcc14d73d642B", orderDO);
//        String openId = getWxOpenId(appId, appSecret, "04102503de220bd9a61ebc5233dee11B");
//        System.out.println("openId="+openId);

//        System.out.println("params:"+JsonUtil.toJson(paramMap));

        GetPaymentUrlForWap getPaymentUrl = new GetPaymentUrlForWap();

        OrderDO orderDO = new OrderDO();
        orderDO.setOrderSn(""+System.currentTimeMillis());
        orderDO.setTotalAmount(1L);
        String outTradeNo = orderDO.getOrderSn();
        String orderUid = ""+System.currentTimeMillis();
        String attachData = orderUid;
        orderDO.setUserId(85L);
        orderDO.setSellerId(91L);
        orderDO.setId(100L);
//        Map paramMap = getPaymentUrl.getUnionPayParam(orderDO);
//        System.out.println(JsonUtil.toJson("response:"+JsonUtil.toJson(paramMap)));
    }
}