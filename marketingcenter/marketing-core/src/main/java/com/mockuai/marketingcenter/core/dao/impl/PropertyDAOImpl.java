package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.dao.PropertyDAO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class PropertyDAOImpl extends SqlMapClientDaoSupport implements PropertyDAO {

    public long addProperty(PropertyDO propertyDO) {

        return ((Long) getSqlMapClientTemplate().insert("property.addProperty", propertyDO)).longValue();
    }

    public List<PropertyDO> queryProperty(PropertyQTO propertyQTO) {

        return getSqlMapClientTemplate().queryForList("property.queryProperty", propertyQTO);
    }
}