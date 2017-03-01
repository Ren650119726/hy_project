package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.core.dao.ItemPropertyValueDAO;
import com.mockuai.itemcenter.core.domain.ItemPropertyValueDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/9/15.
 */
@Service
public class ItemPropertyValueDAOImpl extends SqlMapClientDaoSupport implements ItemPropertyValueDAO{
    @Override
    public void addItemPropertyValues(List<ItemPropertyValueDO> itemPropertyValueDOs) {
        getSqlMapClientTemplate().insert("item_property_value.addItemPropertyValues", itemPropertyValueDOs);
    }

    @Override
    public int updateItemPropertyValue(ItemPropertyValueDO itemPropertyValueDO) {
        return getSqlMapClientTemplate().update("item_property_value.updateItemPropertyValue", itemPropertyValueDO);
    }

    @Override
    public int deleteItemPropertyValues(ItemPropertyValueDO itemPropertyValueDO) {
        return getSqlMapClientTemplate().update("item_property_value.deleteItemPropertyValues", itemPropertyValueDO);
    }

    @Override
    public List<ItemPropertyValueDO> queryItemPropertyValue(Long itemPropertyTmplId) {
        return getSqlMapClientTemplate().queryForList("item_property_value.queryItemPropertyValue", itemPropertyTmplId);
    }
}
