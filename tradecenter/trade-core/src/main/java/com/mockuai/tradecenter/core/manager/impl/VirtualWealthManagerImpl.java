package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderServiceDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import com.mockuai.tradecenter.core.manager.VirtualWealthManager;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.virtualwealthcenter.client.VirtualWealthClient;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class VirtualWealthManagerImpl extends BaseService implements VirtualWealthManager {
	private static final Logger log = LoggerFactory.getLogger(VirtualWealthManagerImpl.class);


	@Resource
	private VirtualWealthClient virtualWealthClient;
	
	@Resource
	private DozerBeanService dozerBeanService;

	public boolean preUseUserWealth(long userId, int wealthType, long amount, long orderId, String appKey)
			throws TradeException {
		try {
			log.info(" userId: "+userId+", wealthType:"+wealthType+" , amount:"+amount+" , orderId:"+orderId+" , appKey:"+appKey);
			Response<Void> response = virtualWealthClient.preUseUserWealth(userId, wealthType, amount, orderId, appKey);
			if (response.isSuccess()) {
				return true;
			} else {
				log.error("userId:{}, orderId:{}, errorCode:{}, errorMsg:{}", userId, orderId, response.getResCode(),
						response.getMessage());
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}
		} catch (Exception e) {
			log.error("userId:{}, orderId:{}", userId, orderId, e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
	}

	public boolean useUserWealth(long userId, long orderId, String appKey) throws TradeException {
		try {
			Response<Void> response = virtualWealthClient.useUserWealth(userId, orderId, appKey);
			if (response.isSuccess()) {
				return true;
			} else {
				log.error("userId:{}, orderId:{}, errorCode:{}, errorMsg:{}", userId, orderId, response.getResCode(),
						response.getMessage());
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}
		} catch (Exception e) {
			log.error("userId:{}, orderId:{}", userId, orderId, e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
	}

	public boolean releaseUsedWealth(long userId, long orderId, String appKey) throws TradeException {
		try {
			Response<Void> response = virtualWealthClient.releaseUsedWealth(userId, orderId, appKey);
			if (response.isSuccess()) {
				return true;
			} else {
				log.error("userId:{}, orderId:{}, errorCode:{}, errorMsg:{}", userId, orderId, response.getResCode(),
						response.getMessage());
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}
		} catch (Exception e) {
			log.error("userId:{}, orderId:{}", userId, orderId, e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
	}

	@Override
	public boolean returnUserWealth(Long userId, Long orderId, Long orderItemId, Map<Integer, Long> map, String appKey)
			throws TradeException {
		try {
			Response<Void> response = virtualWealthClient.returnUsedWealth(userId, orderId, orderItemId, map, appKey);
			
			printInvokeService(log, "returnUsedWealth response", response, "");
			
			if (response.isSuccess()) {
				return true;
			} else {
				log.error("userId:{}, orderId:{}, errorCode:{}, errorMsg:{}", userId, orderId, response.getResCode(),
						response.getMessage());
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}
		} catch (Exception e) {
			log.error("userId:{}, orderId:{}", userId, orderId, e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}

	}

	@Override
	public boolean preUseUserWealthBatch(long userId, List<UsedWealthDTO> usedWealthDTOs, String appKey)
			throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log,userId+" preUseUserWealthBatch ",usedWealthDTOs,"");
		try{
			List<com.mockuai.virtualwealthcenter.common.domain.dto.UsedWealthDTO> marketUsedWealthDTOs = dozerBeanService.coverList(usedWealthDTOs,
					com.mockuai.virtualwealthcenter.common.domain.dto.UsedWealthDTO.class);
			Response<Void> response = virtualWealthClient.preUseMultiUserWealth(userId, marketUsedWealthDTOs, appKey);
			printInvokeService(log,"invoke marketingcenter preUseUserWealthBatch",response,"");
			if (response.isSuccess()) {
				return true;
			} else {
				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
			}
		}catch(Exception e){
			log.error("preUseUserWealthBatch error", e);
			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
		}
	}

	@Override
	public boolean useUserWealthBatch(Long userId,List<Long> orderIds, String appKey) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log,userId+" useUserWealthBatch ",orderIds,"");
		try{
			Response<Void> response = virtualWealthClient.useMultiUserWealth(orderIds,userId, appKey);
			printInvokeService(log,"invoke marketingcenter useUserWealthBatch",response,"");
			if (response.isSuccess()) {
				return true;
			} else {
				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
			}
		}catch(Exception e){
			log.error("useUserWealthBatch error", e);
			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
		}
	}


	@Override
	public Boolean releaseMultiUsedWealth(Long userId, List<UsedWealthDTO> usedWealthDTOs, String appKey) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log,userId+"releaseMultiUsedWealth",usedWealthDTOs,"");
		List<Long> orderIds = new ArrayList<Long>();
		if(usedWealthDTOs.isEmpty()){
			return true;
		}
		
		try{
			Response<?> response = virtualWealthClient.releaseMultiUsedWealth(orderIds, userId, appKey);
			if (response.isSuccess()) {
				return true;
			}else {
				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
			}
			
		}catch(Exception e){
			log.error("releaseMultiUsedCoupon error", e);
			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
		}
		
		
	}

	public WealthAccountDTO getWealthAccount(long userId, int wealthType, String appKey) throws TradeException {
		try{
			Response<List<WealthAccountDTO>> response = virtualWealthClient.queryWealthAccount(userId, wealthType, appKey);
			if (response.isSuccess() == false) {
				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
			}

			if (response.getModule() == null || response.getModule().isEmpty()) {
				log.error("the wealthAccount is not exist, userId:{}, wealthType:{}", userId, wealthType);
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}

			return response.getModule().get(0);

		}catch(Exception e){
			log.error("getWealthAccount error", e);
			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
		}

	}
}
