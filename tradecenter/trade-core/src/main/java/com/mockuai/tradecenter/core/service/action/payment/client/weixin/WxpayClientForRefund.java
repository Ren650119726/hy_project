package com.mockuai.tradecenter.core.service.action.payment.client.weixin;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.CacheManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.TradeNotifyLogManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.mockuai.tradecenter.core.util.wechat.ClientCustomSSL;
import com.mockuai.tradecenter.core.util.wechat.RequestHandler;

@Service
public class WxpayClientForRefund  extends ClientExecutorAbstract {
	private static final Logger log = LoggerFactory.getLogger(WxpayClientForRefund.class);
	@Resource
	private CacheManager cacheManager;
	
	@Resource
	private TradeCoreConfig tradeCoreConfig;
	
	@Autowired
	private AppManager appManager;
	
	@Resource
	private OrderManager orderManager;
	
	@Resource
	TradeNotifyLogManager tradeNotifyLogManager;

	private void doRefund(OrderDO order, Map<String, BizPropertyDTO> bizPropertyMap, String outTradeNo,
			Long refundAmount, String refundItemSn,String certDir) throws Exception {
		String out_refund_no = refundItemSn;// 退款单号
		String total_fee = order.getTotalAmount() + "";// 总金额
		String transaction_id = outTradeNo;
		String refund_fee = refundAmount + "";// 退款金额
		String nonce_str = "4232343765";// 随机字符串
		String appid = null; // 微信公众号apid
		String partnerId = null;
		String appsecret = null;// 微信公众号appsecret
		
		String partnerkey = null;// 商户平台上的那个KEY
		
		if(order.getPaymentId().intValue()==2){
			appid = TradeUtil.getWxAppPayAppId(bizPropertyMap);
			partnerId = TradeUtil.getWxAppPayPartnerId(bizPropertyMap);
			appsecret = TradeUtil.getWxAppPayAppsecret(bizPropertyMap);
			partnerkey = TradeUtil.getWxAppPayPartnerKey(bizPropertyMap);
		}else if(order.getPaymentId().intValue()==5){
			appid = TradeUtil.getWxWapPayAppId(bizPropertyMap); // 微信公众号apid
			partnerId = TradeUtil.getWxWapPayPartnerId(bizPropertyMap);
			appsecret = TradeUtil.getWxWapPayAppsecret(bizPropertyMap);// 微信公众号appsecret
			partnerkey = TradeUtil.getWxWapPayPartnerKey(bizPropertyMap);// 商户平台上的那个KEY
		}
		String mch_id = partnerId; // 微信商户id
		String op_user_id = partnerId;// 就是MCHID
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		// packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
//		packageParams.put("total_fee", refund_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);
		packageParams.put("transaction_id", transaction_id);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				// + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<transaction_id>" + transaction_id + "</transaction_id>" + "<out_refund_no>" + out_refund_no
				+ "</out_refund_no>" + "<total_fee>" + total_fee + "</total_fee>" + "<refund_fee>" + refund_fee
				+ "</refund_fee>" + "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String s = ClientCustomSSL.doRefund(createOrderURL, xml, mch_id,certDir);
		log.info("wxpay apply refund request:"+xml);
		log.info("wxpay apply refund response:"+s);
		
		Map<String, String> respMap = XmlUtil.xmlStr2Map(s);
		String return_code = respMap.get("return_code");
		String result_code = respMap.get("result_code");
		if(!(return_code.equals("SUCCESS")&&result_code.equals("SUCCESS"))){
			String return_msg = respMap.get("return_msg");
			String err_code_des = respMap.get("err_code_des");
			StringBuffer refundErrorReason = new StringBuffer();
//			if(StringUtils.isNotBlank(return_msg)){
//				refundErrorReason.append(return_msg);
//			}
			if(StringUtils.isNotBlank(err_code_des)){
				refundErrorReason.append(err_code_des);
			}
			throw new TradeException("退款申请失败:"+refundErrorReason.toString());
		}
			
		
		

	}

	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {

		String bizCode = (String) context.get("bizCode");
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();
		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}
		OrderDO orderDO = (OrderDO) context.get("orderDO");
		if (null == orderDO) {
			throw new TradeException("orderDO is null");
		}
		
		// 分仓分单业务存在下单同时支付和下单后单个订单去支付
		if(orderDO.getOriginalOrder()!=null){
			// 分仓分单同时下单支付的话子订单的第三方交易记录为空，下单后单个订单去支付的子订单对应有第三方交易记录
			TradeNotifyLogDO tradeNotifyLogDO = tradeNotifyLogManager.getTradeNotifyLogByOrderId(orderDO.getId(), 1);
//			log.info(" 111 orderDO: "+JSONObject.toJSONString(orderDO));
			// 分仓分单订单
			if(tradeNotifyLogDO == null){
				orderDO = orderManager.getActiveOrder(orderDO.getOriginalOrder(), orderDO.getUserId());
//				log.info(" 222 orderDO: "+JSONObject.toJSONString(orderDO));
				// 组合根订单
				if(orderDO.getOriginalOrder()!=null){
					orderDO = orderManager.getActiveOrder(orderDO.getOriginalOrder(), orderDO.getUserId());
//					log.info(" 333 orderDO: "+JSONObject.toJSONString(orderDO));
				}
			}
//			orderDO = orderManager.getActiveOrder(orderDO.getOriginalOrder(), orderDO.getUserId());
		}
		
		
		OrderPaymentDO orderPaymentDO = (OrderPaymentDO) context.get("orderPaymentDO");

		Long refundAmount = (Long) context.get("refundAmount");
		String refundItemSn = (String) context.get("refundItemSn");
		String paymentType = "wap";
		if(orderDO.getPaymentId()==2){
			paymentType= "app";
		}
		
		BizInfoDTO bizInfo = appManager.getBizInfo(bizCode);
		BizPropertyDTO isPayByMockuai = bizInfo.getBizPropertyMap()
				.get(BizPropertyKey.IS_PAY_BY_MOCKUAI);
		if(null!=isPayByMockuai&&isPayByMockuai.getValue().equals("1")){
			bizCode = "mockuai_demo";
		}
		
		String certDir = tradeCoreConfig.getWechatpayCertDir()+"/"+bizCode+"/"+paymentType+"/apiclient_cert.p12";
		log.info("certDir:"+certDir);
		try {
			doRefund(orderDO, bizPropertyMap, orderPaymentDO.getOutTradeNo(), refundAmount, refundItemSn,certDir);
		} catch (Exception e) {
			log.error("wechat refund error", e);
			throw new TradeException(" refund error ");
		}

		return paymentUrlDTO;

	}

}
