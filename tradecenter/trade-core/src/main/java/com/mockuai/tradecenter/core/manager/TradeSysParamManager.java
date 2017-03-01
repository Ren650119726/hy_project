package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.core.domain.TradeSysParamDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface TradeSysParamManager {
	/**
	 * 根据key列表来获取系统参数的一个列表 
	 * @param keyList
	 * @return
	 */
	public List<TradeSysParamDO> getTradeSysParamsByKey(List<String> keyList)throws TradeException;
	
	/**
	 * 根据key值来获取单个系统参数的
	 * @param keyList
	 * @return
	 */
	public TradeSysParamDO getTradeSysParamByKey(String key) throws TradeException;	
}
