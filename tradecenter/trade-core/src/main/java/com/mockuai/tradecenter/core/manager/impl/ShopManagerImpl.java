package com.mockuai.tradecenter.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.itemcenter.client.CommissionClient;
import com.mockuai.itemcenter.common.domain.dto.CommissionUnitDTO;
import com.mockuai.shopcenter.ShopClient;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.ShopManager;
import com.mockuai.usercenter.common.constant.ResponseCode;

public class ShopManagerImpl extends BaseService implements ShopManager{
	private static final Logger log = LoggerFactory.getLogger(ShopManagerImpl.class);
	@Resource
	private ShopClient shopClient;
	
	@Autowired
	private CommissionClient  shopCommissionClient;
	
	@Override
	public ShopDTO getShopDTO(Long sellerId, String appKey) throws TradeException{
        Response response = shopClient.getShop(sellerId, appKey);
        
        if(response.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
        	throw new TradeException("get shop error");
		}else{
			return (ShopDTO) response.getModule();
		}
	
	}

	@Override
	public List<ShopDTO> queryShop(ShopQTO query, String appKey) throws TradeException {
		Response response = shopClient.queryShop(query, appKey);
		 if(response.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
	        	throw new TradeException("query shop error");
			}else{
				return  (List<ShopDTO>) response.getModule();
			}
	}

	@Override
	public Long getOrderCommission(List<CommissionUnitDTO> commissionUnitDTOList, String appKey)throws TradeException{
			com.mockuai.itemcenter.common.api.Response response =  shopCommissionClient.calculateCommission(commissionUnitDTOList, appKey);
			printInvokeService(log,"invoke itemcenter calculateCommision ",response,"");
			
			if(response.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
	        	throw new TradeException("get shop commission error");
			}else{
				return  (Long) response.getModule();
			}
		
		
	}

}
