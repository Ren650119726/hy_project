package com.mockuai.itemcenter.core.dao.impl;

import com.google.common.base.Strings;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceTypeQTO;
import com.mockuai.itemcenter.core.dao.ValueAddedServiceTypeDAO;
import com.mockuai.itemcenter.core.domain.ValueAddedServiceTypeDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ValueAddedServiceTypeDAOImpl extends SqlMapClientDaoSupport implements ValueAddedServiceTypeDAO {


    @Override
    public ValueAddedServiceTypeDO getValueAddedServiceType(ValueAddedServiceTypeDO query) throws ItemException {
        return (ValueAddedServiceTypeDO) getSqlMapClientTemplate().queryForObject("ValueAddedServiceTypeDAO.getValueAddedServiceType",query);
    }

    @Override
    public Long addValueAddedServiceType(ValueAddedServiceTypeDO serviceTypeDO) {
        return (Long) getSqlMapClientTemplate().insert("ValueAddedServiceTypeDAO.addValueAddedServiceType",serviceTypeDO);
    }

    @Override
    public List<ValueAddedServiceTypeDO> queryValueAddedServiceType(ValueAddedServiceTypeQTO serviceTypeQTO) {

        serviceTypeQTO.setTypeName(Strings.emptyToNull(serviceTypeQTO.getTypeName()));

        if (null != serviceTypeQTO.getNeedPaging()) {
            Integer totalCount = (Integer) getSqlMapClientTemplate()
                    .queryForObject("ValueAddedServiceTypeDAO.countValueAddedServiceType", serviceTypeQTO);// 总记录数
            serviceTypeQTO.setTotalCount(totalCount);
            if (totalCount == 0) {
                return Collections.emptyList();
            } else {
                serviceTypeQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
            totalCount = null;
        }

        return getSqlMapClientTemplate().queryForList("ValueAddedServiceTypeDAO.queryValueAddedServiceType",serviceTypeQTO);
    }

    @Override
    public Long updateValueAddedServiceType(ValueAddedServiceTypeDO serviceTypeDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("ValueAddedServiceTypeDAO.updateValueAddedServiceType",serviceTypeDO));
    }

    @Override
    public Long deleteValueAddedServiceType(ValueAddedServiceTypeDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ValueAddedServiceTypeDAO.deleteValueAddedServiceType",query));
    }
}