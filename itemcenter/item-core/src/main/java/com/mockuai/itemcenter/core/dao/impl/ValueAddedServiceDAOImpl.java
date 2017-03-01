package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceQTO;
import com.mockuai.itemcenter.core.dao.ValueAddedServiceDAO;
import com.mockuai.itemcenter.core.domain.ValueAddedServiceDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValueAddedServiceDAOImpl extends SqlMapClientDaoSupport implements ValueAddedServiceDAO {



    @Override
    public List<ValueAddedServiceDO> queryValueAddedService(ValueAddedServiceQTO valueAddedServiceQTO) {

        return getSqlMapClientTemplate().queryForList("ValueAddedServiceDAO.queryValueAddedService",valueAddedServiceQTO);
    }

    @Override
    public Long addValueAddedService(ValueAddedServiceDO valueAddedServiceDO) {
        return (Long) getSqlMapClientTemplate().insert("ValueAddedServiceDAO.addValueAddedService",valueAddedServiceDO);
    }

    @Override
    public ValueAddedServiceDO getValueAddedService(ValueAddedServiceDO valueAddedServiceDO) {
        return (ValueAddedServiceDO) getSqlMapClientTemplate().queryForObject("ValueAddedServiceDAO.getValueAddedService",valueAddedServiceDO);
    }

    @Override
    public Long deleteValueAddedServiceByType(Long typeId, Long sellerId, String bizCode) {

        ValueAddedServiceQTO query  = new ValueAddedServiceQTO();
        query.setTypeId(typeId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        return Long.valueOf(getSqlMapClientTemplate().update("ValueAddedServiceDAO.deleteValueAddedServiceByType",query));
    }

    @Override
    public Long deleteValueAddedService(ValueAddedServiceDO query) {

        return Long.valueOf(getSqlMapClientTemplate().update("ValueAddedServiceDAO.deleteValueAddedService",query));
    }

    @Override
    public Long updateValueAddedService(ValueAddedServiceDO valueAddedServiceDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("ValueAddedServiceDAO.updateValueAddedService",valueAddedServiceDO));
    }

}