package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.RechargeRecordQTO;
import com.mockuai.virtualwealthcenter.core.dao.RechargeRecordDAO;
import com.mockuai.virtualwealthcenter.core.domain.RechargeRecordDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 16/4/18.
 */
@Service
public class RechargeRecordDAOImpl extends SqlMapClientDaoSupport implements RechargeRecordDAO {
    @Override
    public Long add(RechargeRecordDO rechargeRecordDO) {
        return (Long) getSqlMapClientTemplate().insert("recharge_record.add", rechargeRecordDO);
    }

    @Override
    public List<RechargeRecordDO> query(RechargeRecordQTO rechargeRecordQTO) {
        return (List<RechargeRecordDO>) getSqlMapClientTemplate().queryForList("recharge_record.query", rechargeRecordQTO);
    }

    @Override
    public int update(RechargeRecordDO rechargeRecordDO) {
        return getSqlMapClientTemplate().update("recharge_record.update", rechargeRecordDO);
    }

    @Override
    public Long totalCount(RechargeRecordQTO rechargeRecordQTO) {
        return (Long) getSqlMapClientTemplate().queryForObject("recharge_record.totalCount", rechargeRecordQTO);
    }
}