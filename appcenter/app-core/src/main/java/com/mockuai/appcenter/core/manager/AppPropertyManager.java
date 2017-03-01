package com.mockuai.appcenter.core.manager;

import com.mockuai.appcenter.core.domain.AppPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public interface AppPropertyManager {
    /**
     *新增app属性
     * @param appPropertyDO
     * @return
     * @throws AppException
     */
    public long addAppProperty(AppPropertyDO appPropertyDO) throws AppException;

    /**
     * 查询指定app的属性列表
     * @param appId
     * @return
     * @throws AppException
     */
    public List<AppPropertyDO> getAppPropertyList(Long appId) throws AppException;

    /**
     * 查询指定app的指定属性
     * @param appId
     * @param pKey
     * @return
     * @throws AppException
     */
    public AppPropertyDO getAppProperty(Long appId, String pKey) throws AppException;

    /**
     * 删除指定app的指定属性
     * @param appId
     * @param pKey
     * @return
     * @throws AppException
     */
    public int deleteAppProperty(Long appId, String pKey) throws AppException;

    /**
     *
     * @param appId
     * @return
     * @throws AppException
     */
    public int deleteAppPropertyByAppId(Long appId) throws AppException;

    /**
     * 删除指定的bizCode下的所有appProperty
     * @param bizCode
     * @return
     * @throws AppException
     */
    public int deleteAppPropertyByBizCode(String bizCode) throws AppException;
}
