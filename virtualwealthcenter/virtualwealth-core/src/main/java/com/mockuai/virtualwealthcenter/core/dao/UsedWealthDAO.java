package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.qto.UsedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/19/15.
 */
public interface UsedWealthDAO {

    public long addUsedWealth(UsedWealthDO usedWealthDO);

    public int updateWealthStatus(List<Long> idList, Long userId, Integer fromStatus, Integer toStatus);

    public List<UsedWealthDO> queryUsedWealth(UsedWealthQTO usedWealthQTO);

    UsedWealthDO getUsedWealthByWealthAccount(Long wealthAccountId, Long orderId);

    List<UsedWealthDO> queryUsedWealthByParentId(Long parentId);
    
    List<UsedWealthDO> findCustomerUsedPageList(UsedWealthQTO usedWealthQTO);
}