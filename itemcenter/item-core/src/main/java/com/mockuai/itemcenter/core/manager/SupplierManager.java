package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.SupplierDTO;
import com.mockuai.itemcenter.common.domain.qto.SupplierQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by yindingyu on 16/3/21.
 */
public interface SupplierManager {

    public Long addSupplier(SupplierDTO supplierDTO) throws ItemException;

    public Long updateSupplier(SupplierDTO supplierDTO) throws ItemException;

    public Long deleteSupplier(Long id, Long sellerId, String bizCode) throws ItemException;

    public SupplierDTO getSupplier(Long id, Long sellerId, String bizCode) throws ItemException;

    public List<SupplierDTO> querySupplier(SupplierQTO supplierQTO) throws ItemException;

}
