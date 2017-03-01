package com.mockuai.marketingcenter.core.manager;

import com.mockuai.groupbuycenter.common.domain.dto.GroupBuyDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by edgar.zr on 12/14/15.
 */
public interface GroupBuyManager {

	/**
	 * 由 团购平台 验证结算信息
	 *
	 * @param skuId
	 * @param userId
	 * @param sellerId
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	GroupBuyDTO validateForSettlement(Long skuId, Long userId, Long sellerId, String appKey) throws MarketingException;
}