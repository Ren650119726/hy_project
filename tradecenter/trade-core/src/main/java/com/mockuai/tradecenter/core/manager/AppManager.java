package com.mockuai.tradecenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     * @throws TradeException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws TradeException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     * @throws TradeException
     */
    public AppInfoDTO getAppInfo(String appKey) throws TradeException;
    
    /**
     * 根据bizcode查找appkey
     * @param bizCode
     * @return
     * @throws TradeException
     */
    public AppInfoDTO getAppInfoByBizCode(String bizCode,int appType)throws TradeException;
    
    public Boolean getPayByMockuai(String bizCode) throws TradeException;
    
    
    
}
