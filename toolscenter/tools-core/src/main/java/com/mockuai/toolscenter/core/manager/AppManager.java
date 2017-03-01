package com.mockuai.toolscenter.core.manager;

import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.toolscenter.core.exception.ToolsException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     */
    public BizInfoDTO getBizInfo(String bizCode) throws ToolsException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     */
    public AppInfoDTO getAppInfo(String appKey) throws ToolsException;


    public AppInfoDTO getAppInfoByType(String bizCode, AppTypeEnum appTypeEnum) throws ToolsException;


}
