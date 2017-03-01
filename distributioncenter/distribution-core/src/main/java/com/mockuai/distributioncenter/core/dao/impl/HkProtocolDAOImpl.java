package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.common.domain.qto.HkProtocolQTO;
import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.HkProtocolDAO;
import com.mockuai.distributioncenter.core.domain.HkProtocolDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lizg on 2016/10/24.
 */

@Repository
public class HkProtocolDAOImpl extends BaseDAO implements HkProtocolDAO{
    @Override
    public List<HkProtocolDO> getByProModel(HkProtocolQTO hkProtocolQTO) throws DistributionException {
        return getSqlMapClientTemplate().queryForList("hk_protocol.getByProModel",hkProtocolQTO);
    }

    @Override
    public List<HkProtocolDO> getProModelByUserId(HkProtocolQTO hkProtocolQTO) throws DistributionException {
        return getSqlMapClientTemplate().queryForList("hk_protocol.getProModelByUserId",hkProtocolQTO);
    }
}
