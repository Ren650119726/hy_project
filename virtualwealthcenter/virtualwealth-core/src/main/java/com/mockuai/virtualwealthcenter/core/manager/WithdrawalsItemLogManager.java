package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemLogQTO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemLogDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

public interface WithdrawalsItemLogManager {

    void insert(WithdrawalsItemLogDO record) throws VirtualWealthException;

    List<WithdrawalsItemLogDO> queryWithdrawalsItemLog(WithdrawalsItemLogQTO withdrawalsItemLogQTO)
            throws VirtualWealthException;
}