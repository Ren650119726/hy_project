package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;

import java.util.List;

public interface WealthAccountDAO {

    long addWealthAccount(WealthAccountDO paramWealthAccountDO);

    int increaseAccountBalance(long wealthAccountId, long userId, long amount);

    int increaseTotalBalance(Long wealthAccountId, Long total);

    int increaseFrozenBalance(Long wealthAccountId, Long amount);

    int increaseAccountBalanceBatch(List<WealthAccountDO> wealthAccountDOs);

    int decreaseAccountBalance(long wealthAccountId, long userId, long amount);

    WealthAccountDO getWealthAccount(long userId, int wealthType, String bizCode);

    List<WealthAccountDO> queryWealthAccount(WealthAccountQTO wealthAccountQTO);
    
    
    WealthAccountDO findCustomerBalanceDetail(Long userId);
    
    WealthAccountDO findCustomerVirtualDetail(Long userId,String overTime);
}