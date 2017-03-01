package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

/**
 * Created by duke on 15/12/11.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws RainbowException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public AppInfoDTO getAppInfo(String appKey) throws RainbowException;

    /**
     * 通过bizCode获得appKey
     * */
    String getAppKeyByBizCode(String bizCode) throws RainbowException;
}
