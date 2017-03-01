package com.mockuai.deliverycenter.core.manager;

import org.omg.CORBA.UserException;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     * @throws UserException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws DeliveryException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     * @throws UserException
     */
    public AppInfoDTO getAppInfo(String appKey) throws DeliveryException;
}
