package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.RItemServiceTypeQTO;
import com.mockuai.itemcenter.core.dao.RItemServiceTypeDAO;
import com.mockuai.itemcenter.core.domain.RItemServiceTypeDO;
import com.mockuai.itemcenter.core.domain.ValueAddedServiceTypeDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RItemServiceTypeDAOImpl extends SqlMapClientDaoSupport implements RItemServiceTypeDAO {


    @Override
    public List<RItemServiceTypeDO> queryRItemSuitDO(RItemServiceTypeQTO rItemServiceTypeQTO) {
        return getSqlMapClientTemplate().queryForList("RItemServiceType.queryRItemServiceType",rItemServiceTypeQTO);
    }

    @Override
    public Long addRItemServiceType(RItemServiceTypeDO rItemServiceTypeDO) {
        return (Long) getSqlMapClientTemplate().insert("RItemServiceType.addRItemServiceType",rItemServiceTypeDO);
    }

    @Override
    public Long deleteRItemServiceByType(ValueAddedServiceTypeDO serviceTypeDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("RItemServiceType.deleteRItemServiceByType",serviceTypeDO));
    }
}