package com.mockuai.shopcenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.shopcenter.core.exception.ShopException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     * @throws ShopException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws ShopException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     * @throws ShopException
     */
    public AppInfoDTO getAppInfo(String appKey) throws ShopException;
}
