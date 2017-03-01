package com.mockuai.tradecenter.core.util;


import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class JexlUtil {
	/**
	 * 规则解析引擎类
	 */
	private JexlEngine jexlEngine = new JexlEngine();
	/**
	 * 规则表达式 的上下文实例
	 */
	private JexlContext jexlContext = new MapContext();
	
	public void addContextParam(String contextParam,Object target){
		this.jexlContext.set(contextParam, target);
	}
	public JexlEngine getJexlEngine() {
		return jexlEngine;
	}
	public void setJexlEngine(JexlEngine jexlEngine) {
		this.jexlEngine = jexlEngine;
	}
	public JexlContext getJexlContext() {
		return jexlContext;
	}
	public void setJexlContext(JexlContext jexlContext) {
		this.jexlContext = jexlContext;
	}
	/**
	 * 根据用户传递过来的
	 * @param expression
	 * @return
	 */
	public Object executeEngine(String expression){
		Expression exp=this.jexlEngine.createExpression(expression);
		return exp.evaluate(this.jexlContext);
	}
	

}
