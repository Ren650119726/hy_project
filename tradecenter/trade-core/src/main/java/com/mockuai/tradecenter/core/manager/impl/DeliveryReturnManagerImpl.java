//package com.mockuai.tradecenter.core.manager.impl;
//
//import javax.annotation.Resource;
//
//import com.mockuai.tradecenter.common.domain.DeliveryReturnDTO;
//import com.mockuai.tradecenter.core.dao.DeliveryReturnDao;
//import com.mockuai.tradecenter.core.domain.DeliveryReturnDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.DeliveryReturnManager;
//
//public class DeliveryReturnManagerImpl implements DeliveryReturnManager{
//
//	@Resource
//	private DeliveryReturnDao deliveryReturnDao;
//
//	@Override
//	public long addDeliveryReturn(DeliveryReturnDO deliveryReturnDO)
//			throws TradeException {
//		return this.deliveryReturnDao.addDeliveryReturn(deliveryReturnDO);
//	}
//
//	@Override
//	public String validateFields(DeliveryReturnDTO deliveryReturnDTO)throws TradeException{
//		//TODO
//		// 字段验证
//		return null ;
//	}
//
//}
