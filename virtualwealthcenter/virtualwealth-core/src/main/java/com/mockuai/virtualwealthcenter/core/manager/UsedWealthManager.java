package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.qto.UsedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/19/15.
 */
public interface UsedWealthManager {
    /**
     * 退还财富值
     *
     * @param usedWealthDOs
     * @throws VirtualWealthException
     */
    void giveBackUsedWealth(List<UsedWealthDO> usedWealthDOs) throws VirtualWealthException;

    long addUsedWealth(UsedWealthDO usedWealthDO) throws VirtualWealthException;

    int updateWealthStatus(List<Long> idList, Long userId, Integer fromStatus, Integer toStatus) throws VirtualWealthException;

    /**
     * 成功付款前的操作
     *
     * @param usedWealthQTO
     * @return
     * @throws VirtualWealthException
     */
    List<UsedWealthDO> queryUsedWealth(UsedWealthQTO usedWealthQTO) throws VirtualWealthException;

    /**
     * 查询指定账户类型下的财富
     *
     * @param wealthAccountId
     * @param orderId
     * @return
     * @throws VirtualWealthException
     */
    UsedWealthDO getUsedWealthByWealthAccountId(Long wealthAccountId, Long orderId) throws VirtualWealthException;

    /**
     * 查询支付完成订单的退款记录
     *
     * @param parentId
     * @return
     * @throws VirtualWealthException
     */
    List<UsedWealthDO> queryUsedWealthByParentId(Long parentId) throws VirtualWealthException;
    
    
    /**
     * 客户管理 余额和嗨币的支出（2接口)
     * @param usedWealthQTO
     * @return
     * @throws VirtualWealthException
     */
    List<UsedWealthDO> findCustomerUsedPageList(UsedWealthQTO usedWealthQTO) throws VirtualWealthException;
} 