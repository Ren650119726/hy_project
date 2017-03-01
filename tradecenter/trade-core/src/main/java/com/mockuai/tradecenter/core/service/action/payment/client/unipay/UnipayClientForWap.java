package com.mockuai.tradecenter.core.service.action.payment.client.unipay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.PaymentUtil;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.unionpay.acp.sdk.CertUtil;
import com.mockuai.tradecenter.core.util.UnipayUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.upmp.sdk.conf.UpmpConfig;

/**
 * 银联wap支付
 * 
 * @author hzmk
 *
 */
@Service
public class UnipayClientForWap  extends ClientExecutorAbstract {
	
	private static final Logger log = LoggerFactory.getLogger(UnipayClientForWap.class);
	@Resource
	TradeCoreConfig tradeCoreConfig;
	
	@Autowired
	OrderItemManager orderItemManager;
	
	@Autowired
	AppManager appManager;
	
	private Map<String, String> getUnionPayParam(String bizCode,OrderDO orderDO, Map<String, BizPropertyDTO> bizPropertyMap,String appKey)
			throws Exception {

		BizPropertyDTO returnUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_NOTIFY_URL);

		if (null == returnUrlAppProperty || null == notifyUrlAppProperty) {
			throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
		}
		OrderItemQTO query = new OrderItemQTO();
		query.setOrderId(orderDO.getId());
		query.setUserId(orderDO.getUserId());
	
		String unipayReturnUrl = TradeUtil.getReturnUrl(returnUrlAppProperty.getValue(),orderDO);
		String signType = "MD5";

		if (!"SHA1withRSA".equalsIgnoreCase(signType)) {
			signType = "MD5";
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
		
		// SDKConfig.getConfig().loadPropertiesFromPath();
		Map<String, String> data = new HashMap<String, String>();
		String orderUid = "" + orderDO.getSellerId() + "_" + orderDO.getUserId() + "_" + orderDO.getId();
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
		// data.put("frontUrl",
		// "http://m.mockuai.com/html/pay-success.html?order_sn="+orderDO.getOrderSn()+"&order_uid="+orderUid+"&pay_amount="+orderDO.getTotalAmount()+"&pay_type=6");
//		 data.put("frontUrl","http://192.168.31.182:8090/trade/order/payment/callback/unionpay_notify");
		

		data.put("frontUrl", unipayReturnUrl);
		// 后台通知地址
		// data.put("backUrl", UpmpConfig.MER_BACK_END_URL);
		data.put("backUrl", notifyUrlAppProperty.getValue());
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", TradeUtil.getUnipayMchId(bizPropertyMap));
//		data.put("merId", "898111448161343");
		// 商户订单号，8-40位数字字母

//		String orderUidForUnion = "" + orderDO.getSellerId() + "x" + orderDO.getUserId() + "x" + orderDO.getId();
		data.put("orderId", orderDO.getOrderSn());

		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		data.put("txnAmt", orderDO.getTotalAmount() + "");
		// 交易币种
		data.put("currencyCode", "156");

		data.put("reqReserved", orderUid);
//		data = TradeUtil.signData(data,bizCodeSignCertPath,SDKConfig.getConfig().getSignCertPwd(),SDKConfig.getConfig().getSignCertType());
		data = UnipayUtil.signData(data,bizCodeSignCertPath,
				SDKConfig.getConfig().getSignCertPwd(),
				SDKConfig.getConfig().getSignCertType());
		
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();

		/**
		 * 创建表单
		 */
		String html = TradeUtil.createHtml(requestFrontUrl, data);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("request_form", html);
		return paramMap;
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
		String appKey = (String)context.get("appKey");
		try {
			Map paramMap = getUnionPayParam(bizCode,orderDO, bizPropertyMap,appKey);
			paymentUrlDTO.setRequestMethod(2);
			paymentUrlDTO.setPayType(6);
			paymentUrlDTO.setParams(paramMap);
			paymentUrlDTO.setPayAmount(orderDO.getTotalAmount());
			return paymentUrlDTO;
		} catch (Exception e) {
			log.error("UnipayClientForWap getUnionPayParam error", e);
			throw new TradeException("unipay for wap getPaymentUrl error");
		}

	}

}
