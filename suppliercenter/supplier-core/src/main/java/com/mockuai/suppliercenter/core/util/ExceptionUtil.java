package com.mockuai.suppliercenter.core.util;

import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.core.domain.SupplierDO;

public class ExceptionUtil {

    /**
     * 获得指定用户名不存在情况下的异常
     */
    public static Response userNotExistException(SupplierDO supplierDo) {
        if (supplierDo == null) {
            SupplierResponse response = new SupplierResponse(false);
            return response;
        }
        return null;
    }
}
