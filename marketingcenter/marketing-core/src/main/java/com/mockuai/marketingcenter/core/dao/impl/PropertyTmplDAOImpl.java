package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.qto.PropertyTmplQTO;
import com.mockuai.marketingcenter.core.dao.PropertyTmplDAO;
import com.mockuai.marketingcenter.core.domain.PropertyTmplDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class PropertyTmplDAOImpl extends SqlMapClientDaoSupport implements PropertyTmplDAO {

    public long addPropertyTmpl(PropertyTmplDO propertyTmplDO) {

        return ((Long) getSqlMapClientTemplate().insert("property_tmpl.addPropertyTmpl", propertyTmplDO)).longValue();
    }

    public int deletePropertyTmpl(Long propertyTmplId, Long userId) {

        PropertyTmplDO propertyTmplDO = new PropertyTmplDO();
        propertyTmplDO.setId(propertyTmplId);
        propertyTmplDO.setCreatorId(userId);
        return getSqlMapClientTemplate().update("property_tmpl.deletePropertyTmpl", propertyTmplDO);
    }

    public int updatePropertyTmpl(PropertyTmplDO propertyTmplDO) {

        return getSqlMapClientTemplate().update("property_tmpl.updatePropertyTmpl", propertyTmplDO);
    }

    public List<PropertyTmplDO> queryPropertyTmpl(PropertyTmplQTO propertyTmplQTO) {

        return getSqlMapClientTemplate().queryForList("property_tmpl.queryPropertyTmpl", propertyTmplQTO);
    }

    public long queryPropertyTmplCount(PropertyTmplQTO propertyTmplQTO) {

        return ((Long) getSqlMapClientTemplate().queryForObject("property_tmpl.queryCount", propertyTmplQTO)).longValue();
    }
}