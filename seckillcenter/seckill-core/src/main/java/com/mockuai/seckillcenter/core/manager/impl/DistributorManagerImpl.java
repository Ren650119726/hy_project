package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopForMopDTO;
import com.mockuai.seckillcenter.common.constant.ResponseCode;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.DistributorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 5/23/2016.
 */
public class DistributorManagerImpl implements DistributorManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(DistributorManagerImpl.class);

	@Autowired
	private DistributionClient distributionClient;

	@Override
	public DistShopDTO getShop(Long sellerId, String appKey) throws SeckillException {
		try {
			Response<DistShopDTO> response = distributionClient.getShopBySellerId(sellerId, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}

			LOGGER.error("error to getShop, sellerId : {}, appKey : {}, code : {}, msg : {}",
					sellerId, appKey, response.getCode(), response.getMessage());
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getShop, sellerId : {}, appKey : {}", sellerId, appKey, e);
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public DistShopForMopDTO getShopForMopBySellerId(Long sellerId, String appKey) throws SeckillException {
		try {
			Response<DistShopForMopDTO> response = distributionClient.getShopForMopBySellerId(sellerId, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}

			LOGGER.error("error to getShopForMopBySellerId, sellerId : {}, appKey : {}, code : {}, msg : {}",
					sellerId, appKey, response.getCode(), response.getMessage());
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getShopForMopBySellerId, sellerId : {}, appKey : {}", sellerId, appKey, e);
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}