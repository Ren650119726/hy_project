package com.mockuai.giftscenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.giftscenter.core.exception.GiftsException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     *
     * @param bizCode
     * @return
     * @throws GiftsException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws GiftsException;

    /**
     * 根据appKey获取应用信息
     *
     * @param appKey
     * @return
     * @throws GiftsException
     */
    public AppInfoDTO getAppInfo(String appKey) throws GiftsException;

    /**

     * 通过bizCode获得AppKey
     * @param bizCode
     * @throws GiftsException
     * */
    String getAppKeyByBizCode(String bizCode) throws GiftsException;
}