package com.mockuai.suppliercenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     *
     * @param bizCode
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws SupplierException;

    /**
     * 根据appKey获取应用信息
     *
     * @param appKey
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public AppInfoDTO getAppInfo(String appKey) throws SupplierException;

    /**
     * 通过bizCode查询appKey
     */
    String getAppKeyByBizCode(String bizCode) throws SupplierException;
}
