package com.mockuai.tradecenter.core.service.action.payment.client.unipay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.mockuai.tradecenter.core.util.UnipayUtil;
import com.unionpay.acp.sdk.CertUtil;
import com.unionpay.acp.sdk.SDKConfig;

@Service
public class UnipayClientForRefund  extends ClientExecutorAbstract{

	private static final Logger log = LoggerFactory.getLogger(UnipayClientForRefund.class);
	@Resource
	TradeCoreConfig tradeCoreConfig;
	
	@Autowired
	AppManager appManager;
	
	public static void main(String args[]){
		

			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

			/**
			 * 组装请求报文
			 */
			Map<String, String> data = new HashMap<String, String>();
			// 版本号
			data.put("version", "5.0.0");
			// 字符集编码 默认"UTF-8"
			data.put("encoding", "UTF-8");
			// 签名方法 01 RSA
			data.put("signMethod", "01");
			// 交易类型
			data.put("txnType", "04");
			// 交易子类型
			data.put("txnSubType", "00");
			// 业务类型
			data.put("bizType", "000201");
			// 渠道类型，07-PC，08-手机
			data.put("channelType", "08");
			// 前台通知地址 ，控件接入方式无作用
			// data.put("frontUrl",
			// "http://localhost:8080/ACPTest/acp_front_url.do");
			data.put("frontUrl", "http://localhost:8080/trade/order/payment/callback/unionpay_notify");
			// 后台通知地址
			data.put("backUrl", "http://localhost:8080/trade/order/payment/callback/unionpay_notify");
			// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
			data.put("accessType", "0");
			// 商户号码，请改成自己的商户号
			data.put("merId", "898111448161343");
			// 原消费的queryId，可以从查询接口或者通知接口中获取
			data.put("origQryId", "201510191428179492108");
			// 商户订单号，8-40位数字字母，重新产生，不同于原消费
			data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			// 订单发送时间，取系统时间
			data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			// 交易金额
			data.put("txnAmt", 1 + "");
			// 交易币种
			data.put("currencyCode", "156");
			
			data.put("reqReserved", "refund");
			// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
			// data.put("reqReserved", "透传信息");

			data = TradeUtil.signData(data);

			// 交易请求url 从配置文件读取
			String url = SDKConfig.getConfig().getBackRequestUrl();

			Map<String, String> resmap = UnipayUtil.submitUrl(data, url);

			// resmap 里返回报文中
			// 银联订单号 tn 商户推送订单后银联移动支付系统返回该流水号，商户调用支付控件时使用
			System.out.println("请求报文=[" + data.toString() + "]");
			System.out.println("应答报文=[" + resmap.toString() + "]");

		

		}

	
	
	

	private void doRefund(String bizCode,Map<String, BizPropertyDTO> bizPropertyMap, String outTradeNo, Long amount,String refundOrderId)
			throws TradeException {

		BizPropertyDTO returnUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_NOTIFY_URL);

		if (null == returnUrlAppProperty || null == notifyUrlAppProperty) {
			throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
		}

		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
//		if(TradeUtil.isYangdongxiRequest(bizCode)){
//			String signCertPath = SDKConfig.getConfig().getSignCertPath();
//			String yangdongxiSignCertPath = signCertPath.substring(0, signCertPath.lastIndexOf("/")+1)+"yangdongxi.pfx";
//			SDKConfig.getConfig().setSignCertPath(yangdongxiSignCertPath);
//			SDKConfig.getConfig().setSignCertPwd(SDKConfig.getConfig().getSignCertPwd());
//			SDKConfig.getConfig().setSignCertType(SDKConfig.getConfig().getSignCertType());
//			SDKConfig.getConfig().setValidateCertDir(SDKConfig.getConfig().getValidateCertDir());
//		}
		
		BizInfoDTO bizInfo = appManager.getBizInfo(bizCode);
		BizPropertyDTO isPayByMockuai = bizInfo.getBizPropertyMap()
				.get(BizPropertyKey.IS_PAY_BY_MOCKUAI);
		if(null!=isPayByMockuai&&isPayByMockuai.getValue().equals("1")){
			bizCode = "mockuai_demo";
		}
		
		String bizCodeSignCertPath = tradeCoreConfig.getUnipayCertDir()+"/"+bizCode+"/"+bizCode+".pfx";
		SDKConfig.getConfig().setSignCertPath(bizCodeSignCertPath);
		SDKConfig.getConfig().setSignCertPwd(SDKConfig.getConfig().getSignCertPwd());
		SDKConfig.getConfig().setSignCertType(SDKConfig.getConfig().getSignCertType());
		SDKConfig.getConfig().setValidateCertDir(tradeCoreConfig.getUnipayCertDir()+"/mockuai_demo");
		
		if(bizCode.equals("naturekiss")){
			SDKConfig.getConfig().setSignCertPwd("111111");
		}
		
		CertUtil.init();
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型
		data.put("txnType", "04");
		// 交易子类型
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		// data.put("frontUrl",
		// "http://localhost:8080/ACPTest/acp_front_url.do");
		String unipayReturnUrl = notifyUrlAppProperty.getValue();
		data.put("frontUrl",unipayReturnUrl);
		// 后台通知地址
		data.put("backUrl", notifyUrlAppProperty.getValue());
//		data.put("backUrl","http://74.207.229.184:8081/alipay/unipay_notify_url.jsp");
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", TradeUtil.getUnipayMchId(bizPropertyMap));
		// 原消费的queryId，可以从查询接口或者通知接口中获取
		data.put("origQryId", outTradeNo);
		// 商户订单号，8-40位数字字母，重新产生，不同于原消费
		String orderId = refundOrderId.replace("_", "X");
		data.put("orderId", orderId);
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额
		data.put("txnAmt", amount + "");
		// 交易币种
		data.put("currencyCode", "156");
		
		data.put("reqReserved", "Refund");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");

//		data = TradeUtil.signData(data);

		data = UnipayUtil.signData(data,bizCodeSignCertPath,
				SDKConfig.getConfig().getSignCertPwd(),
				SDKConfig.getConfig().getSignCertType());
		
		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getBackRequestUrl();

		Map<String, String> resmap = UnipayUtil.submitUrl(data, url);

		// resmap 里返回报文中
		// 银联订单号 tn 商户推送订单后银联移动支付系统返回该流水号，商户调用支付控件时使用
		System.out.println("请求报文=[" + data.toString() + "]");
		System.out.println("应答报文=[" + resmap.toString() + "]");

		// respCode
		if (!resmap.get("respCode").equals("00")) {
			throw new TradeException(resmap.get("respMsg"));
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
		OrderPaymentDO orderPaymentDO = (OrderPaymentDO) context.get("orderPaymentDO");

		Long refundAmount = (Long) context.get("refundAmount");
		
		String newOrderId = (String) context.get("order_id");

		doRefund(bizCode,bizPropertyMap, orderPaymentDO.getOutTradeNo(), refundAmount,newOrderId);
		return paymentUrlDTO;

	}

}
