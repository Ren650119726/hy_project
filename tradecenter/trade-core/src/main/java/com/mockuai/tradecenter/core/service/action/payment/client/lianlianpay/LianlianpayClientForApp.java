package com.mockuai.tradecenter.core.service.action.payment.client.lianlianpay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.vo.PaymentInfo;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.LLPayUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.mockuai.usercenter.common.dto.UserDTO;
@Service
public class LianlianpayClientForApp extends  ClientExecutorAbstract {

	private static final Logger log = LoggerFactory.getLogger(LianlianpayClientForApp.class);

	@Resource
	private UserManager userManager;
	
	@Resource
	OrderConsigneeManager orderConsigneeManager;

	private Map<String, String> getLianlianpayParams(String bizName,OrderDO orderDO, Map<String, BizPropertyDTO> bizPropertyMap,String appKey)
			throws Exception {
//		BizPropertyDTO returnUrlAppProperty = bizPropertyMap
//				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.LIANLIANPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.LIANLIANPAY_NOTIFY_URL);

		if (null == notifyUrlAppProperty) {
			throw new TradeException(" notifyUrlAppProperty is null");
		}

		 // 风控参数 TODO
		 String risk_item = createRiskItem(orderDO,bizPropertyMap,appKey);
		 
		// 构造支付请求对象
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setBusi_partner(TradeUtil.getLianlianpayBusiPartner(bizPropertyMap));
        paymentInfo.setDt_order(DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss"));
        paymentInfo.setInfo_order("嗨云购物");
        paymentInfo.setMoney_order(String.format("%.2f", ((double)(orderDO.getTotalAmount())/100)));
        paymentInfo.setName_goods("嗨云购物");
        paymentInfo.setNo_order(orderDO.getOrderSn());
        paymentInfo.setNotify_url(notifyUrlAppProperty.getValue());
        paymentInfo.setOid_partner(TradeUtil.getLianlianpayOidPartner(bizPropertyMap));
        paymentInfo.setRisk_item(risk_item);
        paymentInfo.setSign_type("RSA");
        
        String privateKey = TradeUtil.getLianlianpayPrikey(bizPropertyMap);
        // 加签名
        String signStr = LLPayUtil.addSign(JSON.parseObject(JSON.toJSONString(paymentInfo)), privateKey);
        paymentInfo.setSign(signStr);
		
		Map<String, String> paramMap = new TreeMap<String, String>();
		
	     paramMap.put("busi_partner", TradeUtil.getLianlianpayBusiPartner(bizPropertyMap));
	     paramMap.put("dt_order", DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss"));
	     paramMap.put("info_order", "嗨云购物");
	     paramMap.put("money_order", String.format("%.2f", ((double)(orderDO.getTotalAmount())/100)));
	     paramMap.put("name_goods", "嗨云购物");
	     paramMap.put("no_order", orderDO.getOrderSn());
	     paramMap.put("notify_url", notifyUrlAppProperty.getValue());
	     paramMap.put("oid_partner", TradeUtil.getLianlianpayOidPartner(bizPropertyMap));
	//       paramMap.put("return_url", TradeUtil.getReturnUrl(returnUrlAppProperty.getValue(),orderDO));
	     paramMap.put("risk_item", risk_item);
	     paramMap.put("sign_type", "RSA");
	     paramMap.put("user_id", orderDO.getUserId()+"");

//         String signStr = LLPayUtil.addSign(JSON.parseObject(JSON.toJSONString(paramMap)),privateKey);

         paramMap.put("private_key", privateKey);
         paramMap.put("sign", signStr);
         

		return paramMap;
	}
	
	private String codeFormat(String code){
		String result = "000000";
		
		if(StringUtils.isNotBlank(code) ){
			if( code.length()>=6){
				result = code.substring(0, 6);
			}else{
				result=code;
				int i = 6-code.length();
				while(i>0){
					result +="0";
					i--;
				}
			}
		}
		
		return result;
	}
	
	/**
     * 根据连连支付风控部门要求的参数进行构造风控参数
     * @return
	 * @throws TradeException 
     */
    private String createRiskItem(OrderDO orderDO,Map<String, BizPropertyDTO> bizPropertyMap,String appKey) throws TradeException
    {
    	UserDTO userDTO = userManager.getUser(orderDO.getUserId(), appKey);
		OrderConsigneeDO orderConsigneeDO = orderConsigneeManager.getOrderConsignee(orderDO.getId(), orderDO.getUserId());
		log.info(" >>>>>>>>> orderConsigneeDO :"+JSONObject.toJSONString(orderConsigneeDO));
		String provinceCode = codeFormat(orderConsigneeDO.getProvinceCode());
		String cityCode = codeFormat(orderConsigneeDO.getCityCode());
		
		
        JSONObject riskItemObj = new JSONObject();
        riskItemObj.put("frms_ware_category", TradeUtil.getLianlianpayWareCategory(bizPropertyMap));
        riskItemObj.put("user_info_mercht_userno", orderDO.getUserId()+"");
        riskItemObj.put("user_info_dt_register", DateUtil.getFormatDate(userDTO.getGmtCreated(), "yyyyMMddHHmmss"));
        riskItemObj.put("delivery_addr_province", provinceCode);
        riskItemObj.put("delivery_addr_city", cityCode);
        riskItemObj.put("delivery_phone", orderConsigneeDO.getMobile());
        riskItemObj.put("logistics_mode", "2");
        riskItemObj.put("delivery_cycle", "72h");
        return riskItemObj.toString();
    }

	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		String bizCode = (String) context.get("bizCode");
		OrderDO orderDO = (OrderDO)context.get("orderDO");
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}
		String bizName = (String) context.get("bizName");
        String appKey = (String)context.get("appKey");
		Map<String, String> paramMap = new HashMap<String, String>();
		try{
			paramMap = getLianlianpayParams(bizName,orderDO, bizPropertyMap,appKey);
		}catch(Exception e){
			log.error("lianlianpayClientForApp getMockLianlianpayParams error",e);
			throw new TradeException("lianlianpay for app getPaymentUrl error");
		}
		
		paymentUrlDTO.setRequestMethod(1);
        paymentUrlDTO.setParams(paramMap);
        paymentUrlDTO.setPayType(13);
     	paymentUrlDTO.setPayAmount(orderDO.getTotalAmount());

		return paymentUrlDTO;
	}

}
