package com.mockuai.distributioncenter.core.manager;

import com.mockuai.appcenter.common.constant.ValueTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * Created by duke on 15/10/28.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     *
     * @param bizCode
     * @return
     * @throws DistributionException
     */
     BizInfoDTO getBizInfo(String bizCode) throws DistributionException;

    /**
     * 根据appKey获取应用信息
     *
     * @param appKey
     * @return
     * @throws DistributionException
     */
     AppInfoDTO getAppInfo(String appKey) throws DistributionException;

    /**
     * 通过bizCode获得AppKey
     * @param bizCode
     * @throws DistributionException
     * */
     String getAppKeyByBizCode(String bizCode) throws DistributionException;

    /**
     * 添加BizProperty
     * */
    void addBizProperty(BizPropertyDTO bizPropertyDTO) throws DistributionException;

    /**
     * 更新BizProperty
     * */
    void updateBizProperty(String bizCode, String pKey, String value, ValueTypeEnum valueTypeEnum)
            throws DistributionException;
}
