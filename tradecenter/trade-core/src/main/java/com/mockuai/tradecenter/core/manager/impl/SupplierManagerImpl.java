package com.mockuai.tradecenter.core.manager.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.suppliercenter.client.StockItemSkuClient;
import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.SupplierManager;

public class SupplierManagerImpl implements SupplierManager{
	
	private static final Logger log = LoggerFactory.getLogger(SupplierManagerImpl.class);
	
	@Resource
	private StoreItemSkuClient storeItemSkuClient;
	
	@Resource
	private StockItemSkuClient stockItemSkuClient;

	@Override
	public List<StoreItemSkuDTO> queryStoreItemSku(
			StoreItemSkuQTO storeItemSkuQTO, String appKey)
			throws TradeException {
		com.mockuai.suppliercenter.common.api.Response<List<StoreItemSkuDTO>> result = null;
		try {
			result = storeItemSkuClient.queryStoreItemSku( storeItemSkuQTO,  appKey);
		} catch (Exception e) {
			log.error(" storeItemSkuClient.queryStoreItemSku error ", e);
			throw new TradeException(e);
		}
		if(result!=null){
			if(result.getCode() != com.mockuai.suppliercenter.common.constant.ResponseCode.REQUEST_SUCCESS.getValue()){
				log.error("response:"+JSONObject.toJSONString(result));
				throw new TradeException(result.getMessage());
			}else{
				if(!CollectionUtils.isEmpty(result.getModule())){
					return result.getModule();
				}else{
					log.error(" storeItemSkuClient.queryStoreItemSku no result module ,Response:"+JSONObject.toJSONString(result));
					throw new TradeException(ResponseCode.BIZ_E_ITEM_OUT_OF_STOCK);
				}
			}
		}else{
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION," storeItemSkuClient.queryStoreItemSku result is null ");
		}
		
	}

	@Override
	public OrderStockDTO freezeOrderSkuStock(OrderStockDTO orderStockDTO,
			String appKey) throws TradeException {
		try {
			com.mockuai.suppliercenter.common.api.Response<OrderStockDTO> result = stockItemSkuClient.freezeOrderSkuStock( orderStockDTO,  appKey);
			if(result.getCode() != com.mockuai.suppliercenter.common.constant.ResponseCode.REQUEST_SUCCESS.getValue()){
				log.error("response:"+JSONObject.toJSONString(result));
				throw new TradeException(result.getMessage());
			}else{
				if(result.getModule()!=null){
					return result.getModule();
				}else{
					log.error(" stockItemSkuClient.freezeOrderSkuStock no result ,Response:"+JSONObject.toJSONString(result));
					throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"stockItemSkuClient.freezeOrderSkuStock no result");
				}
			}
		} catch (Exception e) {
			log.error(" stockItemSkuClient.freezeOrderSkuStock error ", e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,e);
		}
	}

	@Override
	public Boolean thawOrderSkuStock(OrderStockDTO orderStockDTO, String appKey)
			throws TradeException {
		try {
			log.info(" orderStockDTO: "+orderStockDTO);
			com.mockuai.suppliercenter.common.api.Response<Boolean> result = stockItemSkuClient.thawOrderSkuStock( orderStockDTO,  appKey);
			if(result != null && result.getCode() != com.mockuai.suppliercenter.common.constant.ResponseCode.REQUEST_SUCCESS.getValue()){
				log.error("response:"+result);
				throw new TradeException(result.getMessage());
			}else{
				/*if(result.getModule() == null ){
					log.error(" stockItemSkuClient.thawOrderSkuStock error ,Response:"+JSONObject.toJSONString(result));
					throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION," stockItemSkuClient.thawOrderSkuStock no result ");
				}*/
				if( !result.getModule()){
					log.error(" stockItemSkuClient.thawOrderSkuStock error ,Response:"+JSONObject.toJSONString(result));
//					throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION," stockItemSkuClient.thawOrderSkuStock no result ");
				}
				return result.getModule();
			}
		} catch (TradeException e) {
			log.error(" stockItemSkuClient.thawOrderSkuStock error ", e);
			throw new TradeException(e);
		}
	}

	@Override
	public OrderStockDTO reReduceItemSkuSup(OrderStockDTO orderStockDTO,
			String appKey) throws TradeException {
		try {
			com.mockuai.suppliercenter.common.api.Response<OrderStockDTO> result = stockItemSkuClient.reReduceItemSkuSup(orderStockDTO,  appKey);
			if(result.getCode() != com.mockuai.suppliercenter.common.constant.ResponseCode.REQUEST_SUCCESS.getValue()){
				log.error("response:"+result);
				throw new TradeException(result.getMessage());
			}else{
				if(result.getModule() != null){
					log.info(" stockItemSkuClient.reReduceItemSkuSup result.getModule() : "+JSONObject.toJSONString(result.getModule()));
					return result.getModule();
				}else{
					log.error(" stockItemSkuClient.reReduceItemSkuSup no result ,Response:"+JSONObject.toJSONString(result));
					throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION," stockItemSkuClient.reReduceItemSkuSup no result ");
				}
			}
		} catch (Exception e) {
			log.error(" stockItemSkuClient.reReduceItemSkuSup error ", e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,e);
		}
	}

	@Override
	public OrderStockDTO backReduceItemSkuSup(OrderStockDTO orderStockDTO,
			String appKey) throws TradeException {
		try {
			com.mockuai.suppliercenter.common.api.Response<OrderStockDTO> result = stockItemSkuClient.backReduceItemSkuSup(orderStockDTO,  appKey);
			if(result.getCode() != com.mockuai.suppliercenter.common.constant.ResponseCode.REQUEST_SUCCESS.getValue()){
				log.error("response:"+result);
				throw new TradeException(result.getMessage());
			}else{
				if(result.getModule()!=null){
					return result.getModule();
				}else{
					log.error(" stockItemSkuClient.backReduceItemSkuSup no result ,Response:"+JSONObject.toJSONString(result));
					throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"stockItemSkuClient.backReduceItemSkuSup no result");
				}
			}
		} catch (Exception e) {
			log.error(" stockItemSkuClient.backReduceItemSkuSup error ", e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,e);
		}
	}

}
