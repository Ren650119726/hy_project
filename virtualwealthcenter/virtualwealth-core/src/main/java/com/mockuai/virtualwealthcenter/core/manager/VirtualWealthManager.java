package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

public interface VirtualWealthManager {

    long addVirtualWealth(VirtualWealthDO paramVirtualWealthDO) throws VirtualWealthException;

    int deleteVirtualWealth(long id, long userId) throws VirtualWealthException;

    /**
     * 查询平台虚拟财富类型对应帐号
     * 如果没有帐号，不创建默认的账户
     *
     * @param bizCode
     * @param creatorId
     * @param wealthType
     * @return
     * @throws VirtualWealthException
     */
    VirtualWealthDTO getVirtualWealth(String bizCode, Long creatorId, Integer wealthType) throws VirtualWealthException;

    List<VirtualWealthDO> queryVirtualWealth(VirtualWealthQTO virtualWealthQTO) throws VirtualWealthException;

    int increaseVirtualWealth(long id, long amount) throws VirtualWealthException;

    int increaseGrantedVirtualWealth(long id, long amount) throws VirtualWealthException;

    /**
     * 更新积分的使用数据
     *
     * @param bizCode
     * @param virtualWealthId
     * @param exchangeRate
     * @param upperLimit
     * @throws VirtualWealthException
     */
    void updateVirtualWealth(String bizCode, Long virtualWealthId, Double exchangeRate, Integer upperLimit) throws VirtualWealthException;

    /**
     * 构造虚拟财富
     *
     * @param bizCode
     * @param creatorId
     * @param wealthType
     * @return
     * @throws VirtualWealthException
     */
    VirtualWealthDO fakeVirtualWealth(String bizCode, Long creatorId, Integer wealthType) throws VirtualWealthException;
}