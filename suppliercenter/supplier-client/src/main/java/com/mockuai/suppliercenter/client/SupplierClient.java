package com.mockuai.suppliercenter.client;

import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.common.qto.SupplierQTO;

import java.util.List;

public interface SupplierClient {
    /**
     * 添加供应商
     */
    Response<SupplierDTO> addSupplier(SupplierDTO supplierDTO, String appKey);

    /**
     * 根据id获取供应商信息
     */
    Response<SupplierDTO> getSupplierInf(Long id, String appKey);

    /**
     * 查询供应商
     */
    Response<List<SupplierDTO>> querySupplier(SupplierQTO supplierQTO,
                                              String appKey);

    /**
     * 编辑供应商
     */
    Response<Boolean> updateSupplier(SupplierDTO supplierDTO, String appKey);

    /**
     * 提供订单使用,查询供应商
     */
    Response<List<SupplierDTO>> querySupplierInfForOrder(
            SupplierQTO supplierQTO, String appKey);

}
