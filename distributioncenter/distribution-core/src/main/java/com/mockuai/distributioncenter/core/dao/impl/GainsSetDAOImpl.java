package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.GainsSetDAO;
import com.mockuai.distributioncenter.core.domain.GainsSetDO;
import org.springframework.stereotype.Repository;

/**
 * Created by lizg on 2016/8/29.
 */

@Repository
public class GainsSetDAOImpl extends BaseDAO implements GainsSetDAO{

    @Override
    public Long add(GainsSetDO gainsSetDO) {
        return (Long) getSqlMapClientTemplate().insert("gainsSet.add", gainsSetDO);
    }

    @Override
    public GainsSetDO get() {
        return (GainsSetDO)getSqlMapClientTemplate().queryForObject("gainsSet.get");
    }

    @Override
    public Integer update(GainsSetDO gainsSetDO) {
        return getSqlMapClientTemplate().update("gainsSet.update",gainsSetDO);
    }
}
