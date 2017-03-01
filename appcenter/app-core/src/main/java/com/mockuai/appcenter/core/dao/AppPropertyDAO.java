package com.mockuai.appcenter.core.dao;

import com.mockuai.appcenter.core.domain.AppPropertyDO;
import com.mockuai.appcenter.core.exception.DAOException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public interface AppPropertyDAO {

    /**
     *新增app属性
     * @param appPropertyDO
     * @return
     * @throws com.mockuai.appcenter.core.exception.DAOException
     */
    public long addAppProperty(AppPropertyDO appPropertyDO) throws DAOException;

    /**
     * 查询指定app的属性列表
     * @param appId
     * @return
     * @throws DAOException
     */
    public List<AppPropertyDO> getAppPropertyList(Long appId) throws DAOException;

    /**
     * 查询指定app的指定属性
     * @param appId
     * @param pKey
     * @return
     * @throws DAOException
     */
    public AppPropertyDO getAppProperty(Long appId, String pKey) throws DAOException;

    /**
     * 删除指定app的指定属性
     * @param appId
     * @param pKey
     * @return
     * @throws DAOException
     */
    public int deleteAppProperty(Long appId, String pKey) throws DAOException;

    public int deleteAppPropertyByAppId(Long appId) throws DAOException;

    /**
     * 删除指定bizCode下的所有appProperty
     * @param bizCode
     * @return
     * @throws DAOException
     */
    public int deleteAppPropertyByBizCode(String bizCode) throws DAOException;
}
