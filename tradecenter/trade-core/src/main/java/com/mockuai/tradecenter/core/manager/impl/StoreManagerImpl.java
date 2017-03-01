package com.mockuai.tradecenter.core.manager.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.shopcenter.StoreClient;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.dao.OrderStoreDAO;
import com.mockuai.tradecenter.core.domain.OrderStoreDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.StoreManager;

public class StoreManagerImpl extends BaseService implements StoreManager {
	private static final Logger log = LoggerFactory.getLogger(StoreManagerImpl.class);
	@Resource
	private StoreClient storeClient;

	@Autowired
	private OrderStoreDAO orderStoreDAO;

	@Override
	public StoreDTO getStore(Long storeId, Long sellerId, String appkey) throws TradeException {
		try {
			 com.mockuai.shopcenter.api.Response response = storeClient.getStore(storeId,sellerId, appkey);
			 printInvokeService(log,"getStore respose",response,"");
			 if (response.getCode() != 10000) {
				 log.error("error to get store, storeId:{},sellerId:{},errorCode:{}, errorMsg:{}", storeId, sellerId,response.getMessage());
				 throw new TradeException("当前门店已经关闭");
			 }
			 return (StoreDTO) response.getModule();
//			 StoreDTO store = (StoreDTO) response.getModule();
			
//			 return store;
//			StoreDTO store = new StoreDTO();
//			store.setAddress("xxx");
//			store.setStoreName("xxx");
//			store.setStoreNumber("xxxx");
//			store.setId(storeId);
//			store.setOwnerId(38899L);
//			store.setCountryCode("x");
//			store.setProvinceCode("xx");
//			store.setAreaCode("xxx");
//			store.setTownCode("xxxx");
//			store.setCityCode("xxxxx");
//			return store;
		} catch (Exception e) {
			log.error("storeId:{}", storeId, e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}

	}

	@Override
	public OrderStoreDO getOrderStore(Long orderId) {
		printIntoService(log, "getOrderStore", orderId, "");
		OrderStoreDO orderStore = orderStoreDAO.getOrderStore(orderId);
		printInvokeService(log, "getOrderStore response", orderStore, "");
		return orderStore;
	}

	@Override
	public Boolean updatePickupCode(Long orderId, String pickupCode) {
		try {
			int result = orderStoreDAO.updatePickupCode(orderId, pickupCode);
			if (result > 0) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
