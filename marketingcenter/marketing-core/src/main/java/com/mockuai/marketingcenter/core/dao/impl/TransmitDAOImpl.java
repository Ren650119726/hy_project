package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.core.dao.TransmitDAO;
import com.mockuai.marketingcenter.core.domain.TransmitDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 15/9/23.
 */
@Service
public class TransmitDAOImpl extends SqlMapClientDaoSupport implements TransmitDAO {
    @Override
    public Long addTransmit(TransmitDO transmitDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("transmit.insert", transmitDO);
        return id;
    }

    @Override
    public int updateTransmit(TransmitDO transmitDO) {
        int affectRow = getSqlMapClientTemplate().update("transmit.update", transmitDO);
        return affectRow;
    }

    @Override
    public TransmitDO getTransmitByBizCode(String bizCode) {
        TransmitDO transmitDO = (TransmitDO) getSqlMapClientTemplate().queryForObject("transmit.getTransmit", bizCode);
        return transmitDO;
    }
}