package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemLogQTO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemLogDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class WithdrawalsItemLogDODAOImpl extends SqlMapClientDaoSupport implements WithdrawalsItemLogDODAO {

    public WithdrawalsItemLogDODAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        WithdrawalsItemLogDO _key = new WithdrawalsItemLogDO();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("withdrawals_item_log.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(WithdrawalsItemLogDO record) {
        getSqlMapClientTemplate().insert("withdrawals_item_log.insert", record);
    }

    public void insertSelective(WithdrawalsItemLogDO record) {
        getSqlMapClientTemplate().insert("withdrawals_item_log.insertSelective", record);
    }

    public WithdrawalsItemLogDO selectByPrimaryKey(Long id) {
        WithdrawalsItemLogDO _key = new WithdrawalsItemLogDO();
        _key.setId(id);
        WithdrawalsItemLogDO record = (WithdrawalsItemLogDO) getSqlMapClientTemplate().queryForObject("withdrawals_item_log.selectByPrimaryKey", _key);
        return record;
    }

    @Override
    public List<WithdrawalsItemLogDO> queryWithdrawalsItemLog(WithdrawalsItemLogQTO withdrawalsItemLogQTO) {
        return getSqlMapClientTemplate().queryForList("withdrawals_item_log.queryWithdrawalsItemLog", withdrawalsItemLogQTO);
    }

    public int updateByPrimaryKeySelective(WithdrawalsItemLogDO record) {
        int rows = getSqlMapClientTemplate().update("withdrawals_item_log.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(WithdrawalsItemLogDO record) {
        int rows = getSqlMapClientTemplate().update("withdrawals_item_log.updateByPrimaryKey", record);
        return rows;
    }
}