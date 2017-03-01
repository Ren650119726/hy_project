package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.SupplierQTO;
import com.mockuai.itemcenter.core.domain.SupplierDO;

import java.util.List;

public interface SupplierDAO {


    public Long addSupplier(SupplierDO supplierDO);

    public Long updateSupplier(SupplierDO supplierDO);

    public Long deleteSupplier(SupplierDO supplierDO);

    public SupplierDO getSupplier(SupplierDO supplierDO);

    public List<SupplierDO> querySupplier(SupplierQTO supplierQTO);
}