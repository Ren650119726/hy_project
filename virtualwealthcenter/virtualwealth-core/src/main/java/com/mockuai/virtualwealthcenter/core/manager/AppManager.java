package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     *
     * @param bizCode
     * @return
     * @throws VirtualWealthException
     */
    BizInfoDTO getBizInfo(String bizCode) throws VirtualWealthException;

    /**
     * 根据appKey获取应用信息
     *
     * @param appKey
     * @return
     * @throws VirtualWealthException
     */
    AppInfoDTO getAppInfo(String appKey) throws VirtualWealthException;

    /**
     * 通过bizCode获得AppKey
     *
     * @param bizCode
     * @throws VirtualWealthException
     */
    String getAppKeyByBizCode(String bizCode) throws VirtualWealthException;
}