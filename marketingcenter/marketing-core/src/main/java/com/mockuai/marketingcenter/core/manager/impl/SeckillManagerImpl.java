package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.client.SeckillClient;
import com.mockuai.seckillcenter.common.api.Response;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 12/15/15.
 */
public class SeckillManagerImpl implements SeckillManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeckillManagerImpl.class);

	@Autowired
	private SeckillClient seckillClient;

	@Override
	public SeckillDTO validateForSettlement(Long skuId, Long userId, Long sellerId, String appKey) throws MarketingException {

		try {
			Response<SeckillDTO> response = seckillClient.validateForSettlement(skuId, userId, sellerId, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error of validateForSettlement, skuId : {}, userId : {}, selleId : {}, errCode : {}, errMsg : {}, appKey :{}",
					skuId, userId, sellerId, response.getResCode(), response.getMessage(), appKey);
			throw new MarketingException(response.getResCode(), response.getMessage());
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error of validateForSettlement, skuId : {}, userId : {}, selleId : {}, appKey :{}",
					skuId, userId, sellerId, appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}