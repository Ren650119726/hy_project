package com.mockuai.tradecenter.core.service.impl;

import java.util.List;
import java.util.Map;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.RuleChainService;
/**
 * 
 * @author hzmk
 *
 */
public class PointRuleChainService extends  AbstractRuleChian{
	
	
	OrderDTO order ;
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
		order = new OrderDTO();
		return order;
	}

	@Override
	public TradeResponse<?> processService(RequestContext sourceRequest) {
		// TODO Auto-generated method stub
		//TODO ...分发出去
		if(ruleActionName.equals("test")){
			System.out.println("user id"+this.order.getUserId());
		}
		return null;
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
