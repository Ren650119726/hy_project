package com.mockuai.suppliercenter.core.util;

import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.core.domain.SupplierDO;
import org.springframework.beans.BeanUtils;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
public class ModelUtil {

    public static SupplierDO convertToUserDO(SupplierDTO supplierDTO) {
        if (supplierDTO == null) {
            return null;
        }

        SupplierDO supplierDO = new SupplierDO();
        BeanUtils.copyProperties(supplierDTO, supplierDO);
        return supplierDO;
    }

    public static SupplierDTO convertToUserDTO(SupplierDO supplierDO) {
        if (supplierDO == null) {
            return null;
        }

        SupplierDTO supplierDTO = new SupplierDTO();
        BeanUtils.copyProperties(supplierDO, supplierDTO);
        return supplierDTO;
    }


}
