package com.mockuai.headsinglecenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     *
     * @param bizCode
     * @return
     * @throws HeadSingleException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws HeadSingleException;

    /**
     * 根据appKey获取应用信息
     *
     * @param appKey
     * @return
     * @throws HeadSingleException
     */
    public AppInfoDTO getAppInfo(String appKey) throws HeadSingleException;

    /**
	 *
     * 通过bizCode获得AppKey
     * @param bizCode
     * @throws HeadSingleException
     * */
    String getAppKeyByBizCode(String bizCode) throws HeadSingleException;
}