package com.mockuai.suppliercenter.core.manager;

import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.common.qto.SupplierQTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;

import java.util.List;

public interface SupplierManager {
    /**
     * 添加供应商
     */
    SupplierDTO addSupplier(SupplierDTO supplierDTO) throws SupplierException;

    SupplierDTO getSupplierById(Long supplierId) throws SupplierException;

    /**
     * 查询符合查询条件的供应商
     */
    List<SupplierDTO> querySupplier(SupplierQTO supplierQto, String appKey) throws SupplierException;

    /**
     * 查询指定查询条件下的供应商总数
     */
    Long getTotalCount(SupplierQTO supplierQto) throws SupplierException;

    /**
     * 修改供应商信息
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    int updateSupplier(SupplierDTO supplierDTO) throws SupplierException;

}
