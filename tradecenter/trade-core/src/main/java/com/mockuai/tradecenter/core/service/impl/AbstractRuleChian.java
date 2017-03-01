package com.mockuai.tradecenter.core.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.datacenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.RuleChainService;
import com.mockuai.tradecenter.core.util.JexlUtil;
/**
 * 
 * @author hzmk
 *
 */
public abstract class AbstractRuleChian implements RuleChainService {

	protected final Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * 给源对象某些属性赋常量值的属性与值集合
	 * @return
	 */
	public abstract Map<String,String> getConstantForBeanPropertys();
	/**
	 * 取得将要把源对象属性拷贝到目标对象属性值的集合
	 * @return
	 */
	public abstract Map<String,String> getAnalysisParams();
	
	/**
	 * 交易服务规则过滤连
	 */
	public abstract Map<String,String> getRuleChainExpressions();


	public abstract Object getTargetObject();

	protected JexlUtil jexlUtil = new JexlUtil();
	
	public abstract TradeResponse<?> processService(RequestContext sourceRequest);
	
	
	public TradeResponse<?> executeRuleChain(RequestContext sourceRequest) {
		TradeResponse<?> tradeResponse = new TradeResponse(ResponseCode.SUCCESS);
		try {
			this.jexlUtil.addContextParam("target", getTargetObject());
			this.jexlUtil.addContextParam("source", sourceRequest);
			this.buildTargetRequestParam(sourceRequest, getTargetObject());
			tradeResponse = processService(sourceRequest);
			if(tradeResponse.getCode()!=10000)
					return tradeResponse;
			if (this.getNextTradeService() != null) {
				tradeResponse = this.getNextTradeService().executeRuleChain(sourceRequest);
			} else{
				return tradeResponse;
			}
				
			
		} catch (Exception ex) {
			log.error("abstractRuleChian error",ex);
			tradeResponse.setCode(ResponseCode.SERVICE_EXCEPTION.getCode());
			tradeResponse.setMessage(ex.getMessage());
		}
		return tradeResponse;
	}
	
	
	
	
	

    public void buildTargetRequestParam(Object sourceRequest,Object targetRequest) {
		if(this.getAnalysisParams()!=null && this.getAnalysisParams().size()>0){
			for (String sourceKey : this.getAnalysisParams().keySet()) {
				Object obj = this.jexlUtil.executeEngine(sourceKey);
				if(obj!=null){
					dynamicInvokeBeanSetMethod(obj, targetRequest,this.getAnalysisParams().get(sourceKey));
				}
			}
		}
		if(this.getConstantForBeanPropertys()!=null && this.getConstantForBeanPropertys().size()>0){
			for (String property : this.getConstantForBeanPropertys().keySet()) {
				dynamicInvokeBeanSetMethod(this.getConstantForBeanPropertys().get(property),targetRequest, property);
			}
		}
	}

	/**
	 * 使用Apache Converter工具处理种注册日期转换格式
	 *
	 * @param value
	 *            表示将要转换成目标类型的字符串值
	 * @param target
	 *            表示目标对象的Class类型
	 * @return 返回目标类型的一个值实例对象
	 */
	public Object converter(Object value, Class target) {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss","yyyy/MM/dd" });
		ConvertUtils.register(dc, Date.class);
		return ConvertUtils.convert(value, target);
	}
	/**
	 * 动态给Bean的属性赋值操作
	 */
	//TODO 此方法是bug的,存在于当给一个对象不存在property赋值时不会报错(所以要求开发者务必仔细核对需要对赋值属性的名称)后期有空在修正
	private void dynamicInvokeBeanSetMethod(Object sourceValue, Object target,String property) {
		try {
			BeanInfo targetBeanInfo = Introspector.getBeanInfo(target.getClass());
			PropertyDescriptor[] targetPropertys = targetBeanInfo.getPropertyDescriptors();
			for (int i = 0; i < targetPropertys.length; i++) {
				PropertyDescriptor pd = targetPropertys[i];
				if (property.startsWith(pd.getName())) {
					//System.out.println("pd.getName="+pd.getName()+",property="+property);
					if (pd.getName().equals(property)) {
						if (pd.getPropertyType().equals(sourceValue.getClass())) {
							pd.getWriteMethod().invoke(target,new Object[] { sourceValue });
						} else {
							Object temp = converter(sourceValue,pd.getPropertyType());
							pd.getWriteMethod().invoke(target,new Object[] { temp });
							// log.error("由于目标属性的类型与将要设置的值类型不一致因此不能调用目标对象的Set方法");
						}
					} else {
						String temp = property.substring(pd.getName().length() + 1);
						if(property.substring(pd.getName().length(),pd.getName().length()+1).equals(".")){
							Object tempObj = pd.getReadMethod().invoke(target,new Object[] {});
							if (tempObj != null) {
								dynamicInvokeBeanSetMethod(sourceValue, tempObj,temp);
							} else {
								log.error("待设置目标对象的引用为空,默认实例化一个对象:"+pd.getName());
								tempObj = pd.getPropertyType().newInstance();
								pd.getWriteMethod().invoke(target,new Object[] { tempObj });
								dynamicInvokeBeanSetMethod(sourceValue, tempObj,temp);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			log.error("Execute Method Name=dynamicInvokeBeanSetMethod is Error"+ex.getCause());
		}
	}
}
