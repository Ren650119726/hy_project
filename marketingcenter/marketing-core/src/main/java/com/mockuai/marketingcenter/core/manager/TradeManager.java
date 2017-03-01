package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;

/**
 * Created by edgar.zr on 12/16/15.
 */
public interface TradeManager {

	/**
	 * 查询销售数据
	 *
	 * @param dataQTO
	 * @param appKey
	 * @return
	 */
	Long querySalesRatio(DataQTO dataQTO, String appKey) throws MarketingException;

	OrderDTO getOrder(Long orderId, Long userId, String appKey) throws MarketingException;
}