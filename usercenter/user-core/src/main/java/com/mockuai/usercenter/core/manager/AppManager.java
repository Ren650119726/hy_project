package com.mockuai.usercenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.usercenter.core.exception.UserException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     * @throws UserException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws UserException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     * @throws UserException
     */
    public AppInfoDTO getAppInfo(String appKey) throws UserException;

    /**
     * 通过bizCode查询appKey
     * */
    String getAppKeyByBizCode(String bizCode) throws UserException;
    
    /**
     * 根据appKey获取业务信息
     * @param appKey
     * @return
     * @throws MopException
     */
    public BizInfoDTO getBizInfoByAppKey(String appKey) throws UserException;
}
