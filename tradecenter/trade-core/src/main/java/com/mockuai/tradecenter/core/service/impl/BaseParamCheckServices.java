package com.mockuai.tradecenter.core.service.impl;

import java.util.List;
import java.util.Map;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.RuleChainService;

public class BaseParamCheckServices extends AbstractRuleChian {

	/**
	 * 取得当前服务的交易码
	 *
	 * @return
	 */
	private String transCode;
	/**
	 * 取得当前执行链服务的名称
	 *
	 * @return
	 */
	private String buinessName;
	/**
	 * 取得当前规则链执行失败之后是否继续执行下一个执行链的条件
	 *
	 * @return
	 */
	private Boolean ruleErrorflowNext;
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
	/**
	 * 取得当前链的互斥链集合
	 *
	 * @return
	 */
	private List<RuleChainService> mutualChains;
	/**
	 * 交易服务规则过滤连
	 */
	private Map<String, String> ruleChainExpressions;


	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getBuinessName() {
		return buinessName;
	}

	public void setBuinessName(String buinessName) {
		this.buinessName = buinessName;
	}

	public Boolean getRuleErrorflowNext() {
		return ruleErrorflowNext==null?false:ruleErrorflowNext;
	}

	public void setRuleErrorflowNext(Boolean ruleErrorflowNext) {
		this.ruleErrorflowNext = ruleErrorflowNext;
	}

	public Map<String, String> getConstantForBeanPropertys() {
		return constantForBeanPropertys;
	}

	public void setConstantForBeanPropertys(Map<String, String> constantForBeanPropertys) {
		this.constantForBeanPropertys = constantForBeanPropertys;
	}

	public Map<String, String> getAnalysisParams() {
		return analysisParams;
	}

	public void setAnalysisParams(Map<String, String> analysisParams) {
		this.analysisParams = analysisParams;
	}

	public RuleChainService getNextTradeService() {
		return nextTradeService;
	}

	public void setNextTradeService(RuleChainService nextTradeService) {
		this.nextTradeService = nextTradeService;
	}

	public List<RuleChainService> getMutualChains() {
		return mutualChains;
	}

	public void setMutualChains(List<RuleChainService> mutualChains) {
		this.mutualChains = mutualChains;
	}

	public Map<String, String> getRuleChainExpressions() {
		return ruleChainExpressions;
	}

	public void setRuleChainExpressions(Map<String, String> ruleChainExpressions) {
		this.ruleChainExpressions = ruleChainExpressions;
	}

	public Object getTargetObject() {
		return null;
	}


	@Override
	public TradeResponse<?> processService(RequestContext sourceRequest) {
		// TODO Auto-generated method stub
		TradeResponse tradeResponse = new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
		try {
			this.log.debug("===================执行了业务操作:"+ this.buinessName + ",业务代码:" + this.transCode + "===================");
			this.log.debug("===================执行服务:"+ this.buinessName+ "成功===================");
		} catch (Exception ex) {
			this.log.debug("===================执行服务:"+ this.buinessName + "失败===================");
			tradeResponse.setCode(ResponseCode.PARAM_E_PARAM_INVALID.getCode());
			tradeResponse.setMessage(ResponseCode.PARAM_E_PARAM_INVALID.getComment());
		}
		return tradeResponse;
	}

	
}
