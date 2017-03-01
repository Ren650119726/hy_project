package com.mockuai.tradecenter.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.datacenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.enums.EnumSubTransCode;
import com.mockuai.tradecenter.core.base.TradeInnerOper;
import com.mockuai.tradecenter.core.base.factory.TradeInnerFactory;
import com.mockuai.tradecenter.core.base.request.InnerRequest;
import com.mockuai.tradecenter.core.base.result.TradeOperResult;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.RuleChainService;
/**
 * 
 *
 */
public class OrderActionService extends  AbstractRuleChian{
	
	
	InnerRequest innerRequest ;
	/**
	 * 根据ruleaction name查找需要的ACTION实现
	 */
	private String ruleActionName;
	
	/**
	 * 取得当前执行链服务的名称
	 * @return
	 */
	private String buinessName;
	
	/**
	 * 给源对象某些属性赋常量值的属性与值集合
	 *
	 * @return
	 */
	private Map<String, String> constantForBeanPropertys;

	/**
	 * 取得将要把源对象属性拷贝到目标对象属性值的集合
	 *
	 * @return
	 */
	private Map<String, String> analysisParams;
	/**
	 * 取得当前执行链的下一个 执行链
	 *
	 * @return
	 */
	private RuleChainService nextTradeService;
	
	@Autowired
	TradeInnerFactory  tradeInnerFactory;
	
	
//	@Override
//	public TradeResponse<?> validateRuleChainCondition(RequestContext context) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public RuleChainService getNextTradeService() {
		return nextTradeService;
	}

	@Override
	public List<RuleChainService> getMutualChains() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getConstantForBeanPropertys() {
		return constantForBeanPropertys;
	}

	@Override
	public Map<String, String> getAnalysisParams() {
		return analysisParams;
	}

	@Override
	public Map<String, String> getRuleChainExpressions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTargetObject() {
		innerRequest = new InnerRequest();
		return innerRequest;
	}

	@Override
	public TradeResponse<?> processService(RequestContext sourceRequest) {
		
		EnumSubTransCode enumSubTransCode = EnumSubTransCode.getByCode(ruleActionName);
		TradeResponse tradeResponse = new TradeResponse(ResponseCode.SUCCESS);
		if(enumSubTransCode!=null){
			TradeInnerOper transInnerOper = tradeInnerFactory.getTrans(enumSubTransCode);
			if(null!=transInnerOper){
				try {
					TradeOperResult<?> tradeOperResult = transInnerOper.doTransaction(innerRequest);
					
					if(null!=tradeOperResult.getModule()){
						tradeResponse = new TradeResponse(tradeOperResult.getModule());
					}
					if(!tradeOperResult.isSuccess()){
						tradeResponse.setCode(ResponseCode.SERVICE_EXCEPTION.getCode());
						if(null!=tradeOperResult.getTradeException())
							tradeResponse.setMessage(tradeOperResult.getTradeException().getMessage());
						
						System.out.println("orderActionService tradeResponse------"+JSONObject.toJSONString(tradeResponse));
						return tradeResponse;
					}
					
					TradeOperResult<?> tradeInnerOperResult = sourceRequest.getTradeInnerResult();
					if(null==tradeInnerOperResult)
						tradeInnerOperResult = new TradeOperResult();
					if(null!=tradeOperResult.getItemResponse()){
						tradeInnerOperResult.setItemResponse(tradeOperResult.getItemResponse());
					}
					if(null!=tradeOperResult.getSettlementResponse()){
						tradeInnerOperResult.setSettlementResponse(tradeOperResult.getSettlementResponse());
					}
					sourceRequest.setTradeInnerResult(tradeInnerOperResult);
					
				
				} catch (TradeException e) {
					log.error("TradeException error",e);
					tradeResponse.setCode(ResponseCode.SERVICE_EXCEPTION.getCode());
					tradeResponse.setMessage(e.getMessage());
				}
			}
		}
		
		return tradeResponse;
	}

	public String getRuleActionName() {
		return ruleActionName;
	}

	public void setRuleActionName(String ruleActionName) {
		this.ruleActionName = ruleActionName;
	}

	public String getBuinessName() {
		return buinessName;
	}

	public void setBuinessName(String buinessName) {
		this.buinessName = buinessName;
	}

	public void setConstantForBeanPropertys(Map<String, String> constantForBeanPropertys) {
		this.constantForBeanPropertys = constantForBeanPropertys;
	}

	public void setAnalysisParams(Map<String, String> analysisParams) {
		this.analysisParams = analysisParams;
	}

	public void setNextTradeService(RuleChainService nextTradeService) {
		this.nextTradeService = nextTradeService;
	}
	
	

}
