package com.mockuai.marketingcenter.core.manager;

import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

/**
 * Created by edgar.zr on 7/18/2016.
 */
public interface FirstOrderManager {

	HeadSingleSubDTO getSettlementForFirstOrder(List<MarketItemDTO> marketItemDTOs, Long userId, String appKey) throws MarketingException;
}