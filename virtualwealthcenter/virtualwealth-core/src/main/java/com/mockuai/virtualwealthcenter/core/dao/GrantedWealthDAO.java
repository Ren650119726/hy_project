package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public interface GrantedWealthDAO {

    Long addGrantedWealth(GrantedWealthDO grantedWealthDO);

    GrantedWealthDO getGrantedWealth(GrantedWealthDO grantedWealthDO);

    int updateGrantedWealth(GrantedWealthDO grantedWealthDO);

    int batchUpdateStatus(List<GrantedWealthDO> grantedWealthDOs);

    int decreaseAmount(List<GrantedWealthDO> grantedWealthDOs);

    Long addGrantedWealths(List<GrantedWealthDO> grantedWealthDOs);

    List<GrantedWealthDO> queryGrantedWealth(GrantedWealthQTO grantedWealthQTO);
    
    /**
     * 客户管理 余额和嗨币的收入（2接口）
     */
    List<GrantedWealthDO> findCustomerGrantedPageList(GrantedWealthQTO grantedWealthQTO);
    
}