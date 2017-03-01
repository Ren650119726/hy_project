package com.hanshu.employee.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.hanshu.employee.core.exception.EmployeeException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     *
     * @param bizCode
     * @return
     * @throws EmployeeException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws EmployeeException;

    /**
     * 根据appKey获取应用信息
     *
     * @param appKey
     * @return
     * @throws EmployeeException
     */
    public AppInfoDTO getAppInfo(String appKey) throws EmployeeException;

    /**
     * 通过bizCode查询appKey
     */
    String getAppKeyByBizCode(String bizCode) throws EmployeeException;
}
