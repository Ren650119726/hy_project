package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

public interface WealthAccountManager {

    Long addWealthAccount(WealthAccountDO wealthAccountDO) throws VirtualWealthException;

    int increaseAccountBalance(long wealthAccountId, long userId, long amount) throws VirtualWealthException;

    /**
     * 增加累计的虚拟财富
     *
     * @param wealthAccountId
     * @param amount
     * @return
     * @throws VirtualWealthException
     */
    int increaseTotalBalance(Long wealthAccountId, Long amount) throws VirtualWealthException;

    /**
     * 增加冻结财富
     *
     * @param wealthAccountId
     * @param amount
     * @return
     * @throws VirtualWealthException
     */
    int increaseFrozenBalance(Long wealthAccountId, Long amount) throws VirtualWealthException;

    /**
     * 批量增加虚拟帐号
     *
     * @param wealthAccountDOs
     * @return
     * @throws VirtualWealthException
     */
    int increaseAccountBalanceBatch(List<WealthAccountDO> wealthAccountDOs) throws VirtualWealthException;

    int decreaseAccountBalance(long wealthAccountId, long userId, long amount) throws VirtualWealthException;

    /**
     * 直接扣除用户的虚拟财富
     *
     * @param userId
     * @param wealthType
     * @param amount
     * @param bizCode
     * @throws VirtualWealthException
     */
    void deductWealthAccount(Long userId, Integer wealthType, Long amount, String bizCode) throws VirtualWealthException;

    WealthAccountDO getWealthAccount(long userId, int wealthType, String bizCode) throws VirtualWealthException;

    /**
     * 查询平台用户下各种财富列表
     *
     * @param wealthAccountQTO
     * @return
     * @throws VirtualWealthException
     */
    List<WealthAccountDO> queryWealthAccount(WealthAccountQTO wealthAccountQTO) throws VirtualWealthException;
    
    /**
     * 客户管理 余额流水 统计
     * @param userId
     * @return
     * @throws VirtualWealthException
     */
    WealthAccountDO findCustomerBalanceDetail(Long userId) throws VirtualWealthException;
    
    /**
     * 客户管理 嗨币 详情  overTime = 当前时间-10个月
     * @param userId
     * @param overTime
     * @return
     */
    WealthAccountDO findCustomerVirtualDetail(Long userId,
			String overTime)  throws VirtualWealthException;
}