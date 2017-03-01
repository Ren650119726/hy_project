package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.SupplierDTO;
import com.mockuai.itemcenter.common.domain.qto.SupplierQTO;

import java.util.List;

/**
 * Created by yindingyu on 16/3/21.
 */
public interface SupplierClient {

    public Response<Long> addSupplier(SupplierDTO supplierDTO, String appKey);

    public Response<Long> updateSupplier(SupplierDTO supplierDTO, String appKey);

    public Response<Long> deleteSupplier(Long id, Long sellerId, String appKey);

    public Response<SupplierDTO> getSupplier(Long id, Long sellerId, String appKey);

    public Response<List<SupplierDTO>> querySupplier(SupplierQTO supplierQTO, String appKey);
}
