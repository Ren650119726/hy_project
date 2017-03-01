package com.mockuai.suppliercenter.core.manager;

import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;

public interface AppClient {
    public BizInfoDTO getBizInfo(String bizCode) throws SupplierException;

}
