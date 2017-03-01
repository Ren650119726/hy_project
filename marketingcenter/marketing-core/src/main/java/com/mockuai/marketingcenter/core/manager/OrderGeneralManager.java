package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.OrderGeneralDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public interface OrderGeneralManager {

	OrderGeneralDTO getOrderGeneral(OrderGeneralDTO orderGeneralDTO) throws MarketingException;
}