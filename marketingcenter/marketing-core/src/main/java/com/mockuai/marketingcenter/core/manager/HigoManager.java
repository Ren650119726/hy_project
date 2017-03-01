package com.mockuai.marketingcenter.core.manager;

import com.mockuai.higocenter.common.domain.HigoSettlementDTO;
import com.mockuai.higocenter.common.domain.SettleItemSkuDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

/**
 * Created by zengzhangqiang on 1/5/16.
 */
public interface HigoManager {
    HigoSettlementDTO getHigoSettlement(List<SettleItemSkuDTO> settleItemSkuDTOs, Long freight, String appKey)
            throws MarketingException;
}
