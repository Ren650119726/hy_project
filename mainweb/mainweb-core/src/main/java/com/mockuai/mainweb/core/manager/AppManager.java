package com.mockuai.mainweb.core.manager;

import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.mainweb.core.exception.MainWebException;

/**
 * Created by hy on 2016/9/26.
 */
public interface AppManager {

    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     */
    public BizInfoDTO getBizInfo(String bizCode) throws MainWebException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     */
    public AppInfoDTO getAppInfo(String appKey) throws MainWebException;


    public AppInfoDTO getAppInfoByType(String bizCode, AppTypeEnum appTypeEnum) throws MainWebException;


}
