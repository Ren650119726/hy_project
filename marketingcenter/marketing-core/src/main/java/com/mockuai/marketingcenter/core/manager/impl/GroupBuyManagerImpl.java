package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.groupbuycenter.client.GroupBuyClient;
import com.mockuai.groupbuycenter.common.api.Response;
import com.mockuai.groupbuycenter.common.domain.dto.GroupBuyDTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.GroupBuyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 12/14/15.
 */
public class GroupBuyManagerImpl implements GroupBuyManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(GroupBuyManagerImpl.class);

	@Autowired
	GroupBuyClient groupBuyClient;

	@Override
	public GroupBuyDTO validateForSettlement(Long skuId, Long userId, Long sellerId, String appKey) throws MarketingException {
		try {
			Response<GroupBuyDTO> response = groupBuyClient.validateForSettlement(skuId, userId, sellerId, appKey);
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