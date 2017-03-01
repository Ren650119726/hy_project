package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.core.exception.DistributionException;
import org.springframework.stereotype.Repository;

import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.HkProtocolRecordDAO;
import com.mockuai.distributioncenter.core.domain.HkProtocolRecordDO;

import java.util.List;

/**
 * Created by lizg on 2016/8/29.
 */

@Repository
public class HkProtocolRecordDAOImpl extends BaseDAO implements HkProtocolRecordDAO{

    @Override
    public Long add(HkProtocolRecordDO hkProtocolRecordDO) {
        return (Long) getSqlMapClientTemplate().insert("hk_protocol_record.add", hkProtocolRecordDO);
    }

    @Override
    public HkProtocolRecordDO get(HkProtocolRecordDO hkProtocolRecordDO) {
        return (HkProtocolRecordDO)getSqlMapClientTemplate().queryForObject("hk_protocol_record.getProByUserId",hkProtocolRecordDO);
    }

    @Override
    public List<HkProtocolRecordDO> getByUserId(HkProtocolRecordDO hkProtocolRecordDO) throws DistributionException {
        return getSqlMapClientTemplate().queryForList("hk_protocol_record.getByUserId",hkProtocolRecordDO);
    }

}
