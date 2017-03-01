package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthDO;

import java.util.List;

public interface VirtualWealthDAO {

    long addVirtualWealth(VirtualWealthDO paramVirtualWealthDO);

    int deleteVirtualWealth(long id, long userId);

    VirtualWealthDO getVirtualWealth(long id, long userId);

    VirtualWealthDO getVirtualWealth(VirtualWealthDO virtualWealthDO);

    List<VirtualWealthDO> queryVirtualWealth(VirtualWealthQTO virtualWealthQTO);

    int increaseVirtualWealth(long id, long amount);

    int increaseGrantedVirtualWealth(long id, long amount);

    int updateVirtualWealth(VirtualWealthDO virtualWealthDO);
}