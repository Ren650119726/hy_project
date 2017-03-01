package com.mockuai.tradecenter.core.manager;

import com.mockuai.higocenter.common.domain.HigoSettlementDTO;
import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.higocenter.common.domain.SettleItemSkuDTO;
import com.mockuai.higocenter.common.domain.qto.ItemHigoInfoQTO;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

/**
 * Created by zengzhangqiang on 1/5/16.
 */
public interface HigoManager {
    public HigoSettlementDTO getHigoSettlement(List<SettleItemSkuDTO> settleItemSkuDTOs,long deliveryFee ,String appKey)
            throws TradeException;
    
    public List<ItemHigoInfoDTO> queryItemHigoInfo(ItemHigoInfoQTO itemHigoInfoQTO, String appKey)throws TradeException;
}
