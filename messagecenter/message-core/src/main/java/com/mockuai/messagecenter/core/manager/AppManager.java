package com.mockuai.messagecenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.messagecenter.core.exception.MessageException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     * @throws MessageException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws MessageException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     * @throws MessageException
     */
    public AppInfoDTO getAppInfo(String appKey) throws MessageException;

    /**
     * 通过bizCode查询appKey
     * */
    String getAppKeyByBizCode(String bizCode) throws MessageException;
}
