package com.mockuai.suppliercenter.core.dao.impl;

import com.mockuai.suppliercenter.core.dao.StoreItemDAO;
import com.mockuai.suppliercenter.core.domain.SupplierStoreItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

/**
 * Created by lizg on 2016/9/27.
 */

@Service
public class StoreItemDAOImpl extends SqlMapClientDaoSupport implements StoreItemDAO {

    @Override
    public Long addStoreItem(SupplierStoreItemDO supplierStoreItemDO) {
        return (Long)getSqlMapClientTemplate().insert("store_item.insert",supplierStoreItemDO);
    }

    @Override
    public SupplierStoreItemDO getStoreItem(Long itemId) {

        return (SupplierStoreItemDO) getSqlMapClientTemplate().queryForObject("store_item.getStoreItem", itemId);
    }
}
