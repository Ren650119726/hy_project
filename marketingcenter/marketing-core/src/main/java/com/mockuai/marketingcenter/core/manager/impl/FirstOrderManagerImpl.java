package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.headsinglecenter.client.HeadSingleUserClient;
import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.FirstOrderManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by edgar.zr on 7/18/2016.
 */
public class FirstOrderManagerImpl implements FirstOrderManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirstOrderManagerImpl.class);

	@Autowired
	private HeadSingleUserClient headSingleUserClient;

	@Override
	public HeadSingleSubDTO getSettlementForFirstOrder(List<MarketItemDTO> marketItemDTOs, Long userId, String appKey) throws MarketingException {

		try {
			Response<HeadSingleSubDTO> response = headSingleUserClient.queryJudgeHeadSingleUser(marketItemDTOs, userId, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to getSettlementForFirstOrder, marketItemDTOs : {}, appKey : {}, errCode : {}, errMsg : {}",
					JsonUtil.toJson(marketItemDTOs), appKey, response.getResCode(), response.getMessage());
			throw new MarketingException(response.getResCode(), response.getMessage());
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getSettlementForFirstOrder, marketItemDTOs : {}, appKey : {}",
					JsonUtil.toJson(marketItemDTOs), appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}