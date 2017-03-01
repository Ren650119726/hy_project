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
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.mockuai.tradecenter.core.util.UnipayUtil;
import com.unionpay.acp.sdk.CertUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.upmp.sdk.conf.UpmpConfig;
import com.unionpay.upmp.sdk.service.UpmpService;

/**
 * 银联app支付
 * 
 * @author hzmk
 *
 */
@Service
public class UnipayClientForApp  extends ClientExecutorAbstract {
	
	private static final Logger log = LoggerFactory.getLogger(UnipayClientForApp.class);
	@Resource
	TradeCoreConfig tradeCoreConfig;
	
	@Autowired
	AppManager appManager;
	
	
	private Map<String, String> getUnionPayParamByOldVersion2(String bizCode,OrderDO orderDO,Map<String, BizPropertyDTO> bizPropertyMap ) 
			throws Exception{
		BizPropertyDTO returnUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_NOTIFY_URL);

		if (null == returnUrlAppProperty || null == notifyUrlAppProperty) {
			throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
		}
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		if(TradeUtil.isYangdongxiRequest(bizCode)){
			String signCertPath = SDKConfig.getConfig().getSignCertPath();
			String yangdongxiSignCertPath = signCertPath.substring(0, signCertPath.lastIndexOf("/")+1)+"yangdongxi.pfx";
			SDKConfig.getConfig().setSignCertPath(yangdongxiSignCertPath);
			SDKConfig.getConfig().setSignCertPwd(SDKConfig.getConfig().getSignCertPwd());
			SDKConfig.getConfig().setSignCertType(SDKConfig.getConfig().getSignCertType());
			SDKConfig.getConfig().setValidateCertDir(SDKConfig.getConfig().getValidateCertDir());
		}
		
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
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		 try {
				String unipayReturnUrl = TradeUtil.getReturnUrl(returnUrlAppProperty.getValue(),orderDO);
				data.put("frontUrl", unipayReturnUrl);
			} catch (TradeException e) {
				log.error("get frontUrl error",e);
			}
		
		// 后台通知地址
		data.put("backUrl", notifyUrlAppProperty.getValue());
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
//		data.put("merId", "898111448161343");
		data.put("merId", TradeUtil.getUnipayMchId(bizPropertyMap));
		
//		data.put("merId", realMch.mchId);
		
		// 商户订单号，8-40位数字字母
		String orderUidForUnion = "" + orderDO.getSellerId() + "x" + orderDO.getUserId() + "x" + orderDO.getId();
		data.put("orderId", orderDO.getOrderSn());
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		data.put("txnAmt", orderDO.getTotalAmount()+"");
		// 交易币种
		data.put("currencyCode", "156");
		String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
		data.put("reqReserved", ""+orderUid);// 请求方保留域(可选，用于透传商户信息)
//		data.put("orderDescription",bizName );
		data = UnipayUtil.signData(data,bizCodeSignCertPath,
				SDKConfig.getConfig().getSignCertPwd(),
				SDKConfig.getConfig().getSignCertType());

		// 交易请求url 从配置文件读取
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();

		Map<String, String> resmap = UnipayUtil.submitUrl(data, requestAppUrl);
        
        Map paramMap = new HashMap();
        paramMap.put("tn", resmap.get("tn"));
        return paramMap;
    }

	private Map<String, String> getUnionPayParamByOldVersion(OrderDO orderDO,Map<String, BizPropertyDTO> bizPropertyMap ) throws Exception{
        // 请求要素s
		
		 BizPropertyDTO returnUrlAppProperty = bizPropertyMap
					.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_RETURN_URL);
			BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
					.get(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_NOTIFY_URL);
			
			if (null == returnUrlAppProperty || null == notifyUrlAppProperty) {
				throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
			}
			
        Map<String, String> req = new HashMap<String, String>();
        req.put("version", UpmpConfig.VERSION);// 版本号
        req.put("charset", UpmpConfig.CHARSET);// 字符编码
        req.put("transType", "01");// 交易类型
        req.put("merId", TradeUtil.getUnipayMchId(bizPropertyMap));// 商户代码
        
		req.put("backEndUrl", notifyUrlAppProperty.getValue());// 通知URL
        
      
        req.put("orderDescription", "洋东西商品");// 订单描述(可选)
        req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));// 交易开始日期时间yyyyMMddHHmmss
        req.put("orderTimeout", "");// 订单超时时间yyyyMMddHHmmss(可选)
        String orderUidForUnion = ""+orderDO.getSellerId()+"x"+orderDO.getUserId()+"x"+orderDO.getId();
        req.put("orderNumber", ""+orderDO.getOrderSn());//订单号(商户根据自己需要生成订单号)
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
		try{
			 boolean isYangdongxiRequest = TradeUtil.isYangdongxiRequest(bizCode);
			 Map paramMap = null;
//			 if(isYangdongxiRequest){
//				 paramMap = getUnionPayParamByOldVersion(orderDO,bizPropertyMap);
//			 }else{
//				 paramMap = getUnionPayParamByOldVersion2(isYangdongxiRequest,orderDO,bizPropertyMap);
//			 }
			paramMap = getUnionPayParamByOldVersion2(bizCode,orderDO,bizPropertyMap);
			 
			paymentUrlDTO.setRequestMethod(2);
	        paymentUrlDTO.setPayType(3);
	        paymentUrlDTO.setParams(paramMap);
	        paymentUrlDTO.setPayAmount(orderDO.getTotalAmount());
			return paymentUrlDTO;
		}catch(Exception e){
			log.error("UnipayClientForApp getUnionPayParamByOldVersion error",e);
			throw new TradeException("unipay for app getPaymentUrl error");
		}
		
	}

}
