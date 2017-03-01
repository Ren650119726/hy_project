package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;

import java.util.List;

public interface WealthAccountManager {

    /**
     * 查询平台用户下各种财富列表
     *
     * @param userId
     * @param wealthType
     * @param appKey
     * @return
     * @throws MarketingException
     */
    List<WealthAccountDTO> queryWealthAccount(Long userId, Integer wealthType, String appKey) throws MarketingException;

    /**
     * 获取用户在当前结算中能够使用的虚拟财富
     *
     * @param userId
     * @param settlementInfo
     * @param appKey
     * @throws MarketingException
     */
    void fillUpWealthAccountsForSettlement(Long userId, SettlementInfo settlementInfo, String appKey) throws MarketingException;
}