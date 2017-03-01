package com.mockuai.tradecenter.core.base.factory.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.mockuai.tradecenter.common.enums.EnumSubTransCode;
import com.mockuai.tradecenter.core.base.TradeInnerOper;
import com.mockuai.tradecenter.core.base.factory.TradeInnerFactory;

public class TradeInnerFactoryImpl implements TradeInnerFactory, BeanFactoryAware {

	protected final Log log = LogFactory.getLog(TradeInnerFactoryImpl.class);
	private BeanFactory beanFactory;
	private Map<String, String> transMap;

	public void setTransMap(Map<String, String> transMap) {
		this.transMap = transMap;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public TradeInnerOper getTrans(EnumSubTransCode code) {
		if (code == null) {
			return null;
		}
		if (transMap == null) {
			log.error("transMap为空,初始化失败");
			return null;
		}
		String transName = transMap.get(code.getCode());
		return (TradeInnerOper) beanFactory.getBean(transName);
	}

}
