package com.mockuai.marketingcenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     *
     * @param bizCode
     * @return
     * @throws MarketingException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws MarketingException;

    /**
     * 根据appKey获取应用信息
     *
     * @param appKey
     * @return
     * @throws MarketingException
     */
    public AppInfoDTO getAppInfo(String appKey) throws MarketingException;

    /**
     * 通过bizCode获得AppKey
     * @param bizCode
     * @throws MarketingException
     * */
    String getAppKeyByBizCode(String bizCode) throws MarketingException;
}