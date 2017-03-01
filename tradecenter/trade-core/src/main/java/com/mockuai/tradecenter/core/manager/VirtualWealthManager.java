package com.mockuai.tradecenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MultiSettlementInfo;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public interface VirtualWealthManager {
    public boolean preUseUserWealth(
            long userId, int wealthType, long amount, long orderId, String appKey) throws TradeException;

    public boolean useUserWealth(long userId, long orderId, String appKey) throws TradeException;

    public boolean releaseUsedWealth(long userId, long orderId, String appKey) throws TradeException;
    
    public boolean returnUserWealth(Long userId, Long orderId, Long orderItemId, Map<Integer, Long> map, String appKey)throws TradeException;
    
    //批量预使用虚拟财富
    public boolean preUseUserWealthBatch(long userId, List<UsedWealthDTO> usedWealthDTOs, String appKey)throws TradeException;
    //批量使用虚拟财富
    public boolean useUserWealthBatch(Long userId, List<Long> orderIds, String appKey)throws TradeException;
    
    //批量释放虚拟财富
    public Boolean releaseMultiUsedWealth(Long userId, List<UsedWealthDTO> usedWealthDTOs, String appKey)throws TradeException;

    public WealthAccountDTO getWealthAccount(long userId, int wealthType, String appKey) throws TradeException;
}
