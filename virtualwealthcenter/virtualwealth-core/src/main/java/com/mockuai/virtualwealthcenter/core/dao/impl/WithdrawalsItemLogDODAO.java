package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemLogQTO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemLogDO;

import java.util.List;

public interface WithdrawalsItemLogDODAO {

    int deleteByPrimaryKey(Long id);

    void insert(WithdrawalsItemLogDO record);

    void insertSelective(WithdrawalsItemLogDO record);

    WithdrawalsItemLogDO selectByPrimaryKey(Long id);

    List<WithdrawalsItemLogDO> queryWithdrawalsItemLog(WithdrawalsItemLogQTO withdrawalsItemLogQTO);

    int updateByPrimaryKeySelective(WithdrawalsItemLogDO record);

    int updateByPrimaryKey(WithdrawalsItemLogDO record);
}