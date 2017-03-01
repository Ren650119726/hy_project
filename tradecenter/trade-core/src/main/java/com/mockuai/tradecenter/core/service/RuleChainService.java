package com.mockuai.tradecenter.core.service;

import java.util.List;

import com.mockuai.tradecenter.common.api.TradeResponse;

/**
 * 
 * @author liuchao
 *
 */
public interface RuleChainService {
	/**
	 * 用来执行规则链
	 * @param context
	 * @return
	 */
	public TradeResponse<?> executeRuleChain(RequestContext context);
	
	  /**
     * 验证规则链执行结果
     * @return
     */
//    public TradeResponse<?> validateRuleChainCondition(RequestContext context);
   
	/**
     * 取得当前服务链的下一个执行链
     * @return
     */
    public RuleChainService getNextTradeService();
    
    /**
     * 取得当前服务链的互斥并行链
     * @return
     */
    public List<RuleChainService> getMutualChains();
    
    
    
    
}
