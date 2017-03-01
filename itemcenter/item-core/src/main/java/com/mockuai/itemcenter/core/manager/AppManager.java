package com.mockuai.itemcenter.core.manager;

import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.itemcenter.core.exception.ItemException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     * @throws ItemException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws ItemException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     * @throws ItemException
     */
    public AppInfoDTO getAppInfo(String appKey) throws ItemException;


    public AppInfoDTO getAppInfoByType(String bizCode, AppTypeEnum appTypeEnum) throws ItemException;


}
