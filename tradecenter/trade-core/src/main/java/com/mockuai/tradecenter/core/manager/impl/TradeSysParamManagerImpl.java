//package com.mockuai.tradecenter.core.manager.impl;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.core.dao.TradeSysParamDao;
//import com.mockuai.tradecenter.core.domain.TradeSysParamDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.TradeSysParamManager;
//
//public class TradeSysParamManagerImpl implements TradeSysParamManager {
//
//	@Resource
//	private TradeSysParamDao tradeSysParamDao;
//	private static final Logger log = LoggerFactory.getLogger(OrderManagerImpl.class);
//
//	@Override
//	public List<TradeSysParamDO> getTradeSysParamsByKey(List<String> keyList)
//			throws TradeException {
//		log.info("enter getTradeSysParamsByKey : " + keyList);
//		List<TradeSysParamDO> result = null;
//		try {
//			result = this.tradeSysParamDao.getTradeSysParamsByKey(keyList);
//		} catch (Exception e) {
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//
//		log.info("exit getTradeSysParamsByKey , size:" + result.size());
//		return result;
//	}
//
//	@Override
//	public TradeSysParamDO getTradeSysParamByKey(String key)
//			throws TradeException {
//		log.info("enter getTradeSysParamByKey : " + key);
//
//		TradeSysParamDO result;
//		try{
//			result = this.tradeSysParamDao.getTradeSysParamByKey(key);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit getTradeSysParamsByKey ,key value:" + result.getKey() + " ,"+ result.getValue());
//		return result;
//	}
//
//
//
//}
