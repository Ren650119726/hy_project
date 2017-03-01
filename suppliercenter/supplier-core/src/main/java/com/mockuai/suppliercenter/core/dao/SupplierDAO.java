package com.mockuai.suppliercenter.core.dao;

import com.mockuai.suppliercenter.common.qto.SupplierQTO;
import com.mockuai.suppliercenter.core.domain.SupplierDO;

import java.util.List;

public interface SupplierDAO {

    Long addSuppler(SupplierDO supplierDO);

    Long countSupplierByName(SupplierDO supplierDO);

    SupplierDO getSupplierById(Long supplierId);


    /**
     * 查询符合查询条件的供应商
     */
    List<SupplierDO> querySupplier(SupplierQTO supplierQto);

    Long getTotalCount(SupplierQTO supplierQto);

    /**
     * 修改供应商信息
     */
    int updateSupplier(SupplierDO supplierDO);


}
