package com.mockuai.suppliercenter.core.dao;

import com.mockuai.suppliercenter.core.domain.SupplierStoreItemDO;

/**
 * Created by lizg on 2016/9/27.
 */
public interface StoreItemDAO {

    Long addStoreItem(SupplierStoreItemDO supplierStoreItemDO);

    SupplierStoreItemDO getStoreItem(Long itemId);
}
