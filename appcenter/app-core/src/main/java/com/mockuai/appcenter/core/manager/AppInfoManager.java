package com.mockuai.appcenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoQTO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.exception.AppException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public interface AppInfoManager {
    /**
     * 新增应用信息
     * @param appInfoDO
     * @return
     * @throws AppException
     */
    public long addAppInfo(AppInfoDO appInfoDO) throws AppException;

    /**
     * 根据appKey查询应用信息
     * @param appKey
     * @return
     * @throws AppException
     */
    public AppInfoDO getAppInfo(String appKey) throws AppException;

    /**
     * 更新指定应用信息
     * @param appInfoDO
     * @return
     * @throws AppException
     */
    public int updateAppInfo(AppInfoDO appInfoDO) throws AppException;

    /**
     * 复合条件查询应用信息
     * @param appInfoQTO
     * @return
     * @throws AppException
     */
    public List<AppInfoDO> queryAppInfo(AppInfoQTO appInfoQTO) throws AppException;

    /**
     * 根据domainName查询应用信息
     * @param domainName
     * @return
     * @throws AppException
     */
    public AppInfoDO getAppInfoByDomain(String domainName) throws AppException;


    /**
     * 获取指定企业下的指定类型的应用信息
     * @param bizCode
     * @param appType
     * @return
     * @throws AppException
     */
    public AppInfoDO getAppInfoByType(String bizCode, int appType) throws AppException;

    /**
     * 删除应用信息
     * @param appId
     * @return
     * @throws AppException
     */
    public int deleteAppInfo(long appId) throws AppException;

    /**
     * 删除指定bizCode下的所有app
     * @param bizCode
     * @return
     * @throws AppException
     */
    public int deleteAppInfoByBizCode(String bizCode) throws AppException;
}
