package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.SkuPropertyValueQTO;
import com.mockuai.itemcenter.core.dao.SkuPropertyValueDAO;
import com.mockuai.itemcenter.core.domain.SkuPropertyValueDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/9/15.
 */
@Service
public class SkuPropertyValueDAOImpl extends SqlMapClientDaoSupport implements SkuPropertyValueDAO{
    @Override
    public void addSkuPropertyValues(List<SkuPropertyValueDO> skuPropertyValueDOs) {
        getSqlMapClientTemplate().insert("sku_property_value.addSkuPropertyValues", skuPropertyValueDOs);
    }

    @Override
    public int updateSkuPropertyValue(SkuPropertyValueDO skuPropertyValueDO) {
        return getSqlMapClientTemplate().update("sku_property_value.updateSkuPropertyValue", skuPropertyValueDO);
    }

    @Override
    public int deleteSkuPropertyValues(SkuPropertyValueDO skuPropertyValueDTO) {
        return getSqlMapClientTemplate().update("sku_property_value.deleteSkuPropertyValues", skuPropertyValueDTO);
    }

    @Override
    public List<SkuPropertyValueDO> querySkuPropertyValue(SkuPropertyValueQTO skuPropertyValueQTO) {
        return getSqlMapClientTemplate().queryForList("sku_property_value.querySkuPropertyValue",skuPropertyValueQTO);
    }

    @Override
    public Long deleteSkuPropertyValue(SkuPropertyValueDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("sku_property_value.deleteSkuPropertyValue", query));
    }
}
