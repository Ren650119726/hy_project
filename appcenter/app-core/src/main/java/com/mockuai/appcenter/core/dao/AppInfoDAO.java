package com.mockuai.appcenter.core.dao;

import com.mockuai.appcenter.common.domain.AppInfoQTO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.exception.DAOException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public interface AppInfoDAO {
    /**
     * 新增应用信息
     * @param appInfoDO
     * @return
     * @throws com.mockuai.appcenter.core.exception.DAOException
     */
    public long addAppInfo(AppInfoDO appInfoDO) throws DAOException;

    /**
     * 根据appKey查询应用信息
     * @param appKey
     * @return
     * @throws DAOException
     */
    public AppInfoDO getAppInfo(String appKey) throws DAOException;

    /**
     * 更新指定应用信息
     * @param appInfoDO
     * @return
     * @throws DAOException
     */
    public int updateAppInfo(AppInfoDO appInfoDO) throws DAOException;

    /**
     * 复合条件查询应用信息
     * @param appInfoQTO
     * @return
     * @throws DAOException
     */
    public List<AppInfoDO> queryAppInfo(AppInfoQTO appInfoQTO) throws DAOException;

    /**
     * 根据domainName查询应用信息
     * @param domainName
     * @return
     * @throws DAOException
     */
    public AppInfoDO getAppInfoByDomain(String domainName) throws DAOException;

    /**
     * 根据应用类型查询应用信息
     * @param bizCode
     * @return
     * @throws DAOException
     */
    public AppInfoDO getAppInfoByType(String bizCode, int appType) throws DAOException;

    /**
     * 删除应用信息
     * @param appId
     * @return
     * @throws DAOException
     */
    public int deleteAppInfo(long appId) throws DAOException;

    /**
     * 删除指定bizCode下的所有app
     * @param bizCode
     * @return
     * @throws DAOException
     */
    public int deleteAppInfoByBizCode(String bizCode) throws DAOException;
}
