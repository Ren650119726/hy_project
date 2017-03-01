package com.mockuai.tradecenter.core.service.action.payment.client.weixin;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;
/**
 * 退款对账
 *
 */
@Service
public class WxpayClientForDaily  extends ClientExecutorAbstract{
	private static final Logger log = LoggerFactory.getLogger(WxpayClientForDaily.class);

	@Autowired
	private RefundOrderManager refundOrderManager;
	
	@Autowired
	private RefundOrderItemManager refundOrderItemManager;
	
	private void processDaily(Map<String, BizPropertyDTO> bizPropertyMap) throws Exception {
		String nonce_str = "4232343765";// 随机字符串
		String appid = TradeUtil.getWxWapPayAppId(bizPropertyMap); // 微信公众号apid
		String partnerId = TradeUtil.getWxWapPayPartnerId(bizPropertyMap);
		String mch_id = partnerId; // 微信商户id
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String partnerKey = TradeUtil.getWxWapPayPartnerKey(bizPropertyMap);
		
		Map paramMap = new TreeMap();
		paramMap.put("appid", appid);
		paramMap.put("mch_id", mch_id);
		paramMap.put("nonce_str", nonce_str);
		paramMap.put("bill_date", DateUtil.convertDateToString("yyyyMMdd",DateUtil.getRelativeDate(new Date(), -1)));
		paramMap.put("bill_type", "REFUND");

		paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerKey));

		System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

		String reqUrl = "https://api.mch.weixin.qq.com/pay/downloadbill";
		String xmlData = XmlUtil.map2XmlStr(paramMap);

		String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
		System.out.println("response:"+response);
		Map<String, String> respMap = XmlUtil.xmlStr2Map(response);
		if(null!=respMap.get("return_code")){
			String lines[] = response.split("\n");
			if(lines.length>=4){
				for(int i=1;i<=lines.length-3;i++){
					String line = lines[i];
					System.out.println("line==="+line);
					String fileds[] = line.split("\\,");
					String refundStatus = fileds[21].substring(1,fileds[21].length()-1);
					String mchRefundId = fileds[17].substring(1, fileds[17].length()-1);
					System.out.println("refundStatus:"+refundStatus);
					System.out.println("mchRefundId:"+mchRefundId);
					OrderItemDO orderItemDO = refundOrderManager.getOrderItemByRefundId(mchRefundId);
					RefundOrderItemDTO refundOrderItemDTO = refundOrderManager.convert2RefundOrderItemDTO(orderItemDO);
					if(null!=orderItemDO&&orderItemDO.getRefundStatus()==Integer.parseInt(EnumRefundStatus.REFUNDING.getCode())){
						if(refundStatus.equalsIgnoreCase("SUCCESS")){
							refundOrderItemManager.notifyRefundSuccess(refundOrderItemDTO,orderItemDO);
						}else if(refundStatus.equalsIgnoreCase("FAILED")){
							refundOrderItemManager.notifyRefundFailed(refundOrderItemDTO);
						}
					}
					
				}
			}
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
		
		try {
			
			processDaily(bizPropertyMap);
			
		} catch (Exception e) {
			log.error("wechat refund error", e);
			throw new TradeException(" refund error ");
		}

		return paymentUrlDTO;

	}

}
