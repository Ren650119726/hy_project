package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public interface GrantedWealthManager {

    Long addGrantedWealth(GrantedWealthDO grantedWealthDO) throws VirtualWealthException;

    Long addGrantedWealths(List<GrantedWealthDO> grantedWealthDOs) throws VirtualWealthException;

    GrantedWealthDTO getGrantedWealth(GrantedWealthDO grantedWealthDO) throws VirtualWealthException;

    /**
     * 根据财富发放规则发放财富
     *
     * @param grantedWealthDTO
     * @throws VirtualWealthException
     */
    Long grantWealthByGrantRule(GrantedWealthDTO grantedWealthDTO) throws VirtualWealthException;

    List<GrantedWealthDO> queryGrantedWealth(GrantedWealthQTO grantedWealthQTO) throws VirtualWealthException;

    void decreaseAmount(List<GrantedWealthDO> grantedWealthDOs) throws VirtualWealthException;

    int updateGrantedWealth(GrantedWealthDO grantedWealthDO) throws VirtualWealthException;

    int batchUpdateStatus(List<GrantedWealthDO> grantedWealthDOs) throws VirtualWealthException;
    
    
    List<GrantedWealthDO> findCustomerGrantedPageList(GrantedWealthQTO grantedWealthQTO) throws VirtualWealthException;
    
}