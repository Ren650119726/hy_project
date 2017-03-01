package com.mockuai.appcenter.core.dao.impl;

import com.mockuai.appcenter.common.domain.AppInfoQTO;
import com.mockuai.appcenter.core.dao.AppInfoDAO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.exception.DAOException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class AppInfoDAOImpl extends SqlMapClientDaoSupport implements AppInfoDAO{
    @Override
    public long addAppInfo(AppInfoDO appInfoDO) throws DAOException {
        long id = (Long)this.getSqlMapClientTemplate().insert("app_info.addAppInfo",appInfoDO);
        return id;
    }

    @Override
    public AppInfoDO getAppInfo(String appKey) throws DAOException {
        return (AppInfoDO)this.getSqlMapClientTemplate().queryForObject("app_info.getAppInfo", appKey);
    }

    @Override
    public int updateAppInfo(AppInfoDO appInfoDO) throws DAOException {
        return this.getSqlMapClientTemplate().update("app_info.updateAppInfo", appInfoDO);
    }

    @Override
    public List<AppInfoDO> queryAppInfo(AppInfoQTO appInfoQTO) throws DAOException {
        return (List<AppInfoDO>)this.getSqlMapClientTemplate().queryForList("app_info.queryAppInfo", appInfoQTO);
    }

    @Override
    public AppInfoDO getAppInfoByDomain(String domainName) throws DAOException {
        return (AppInfoDO)this.getSqlMapClientTemplate().queryForObject("app_info.getAppInfoByDomain", domainName);
    }

    @Override
    public AppInfoDO getAppInfoByType(String bizCode, int appType) throws DAOException {
        AppInfoDO appInfoDO = new AppInfoDO();
        appInfoDO.setBizCode(bizCode);
        appInfoDO.setAppType(appType);
        //TODO 这里暂时只查询默认空间下的应用信息，如果后续需要扩展再重构这里的逻辑
        appInfoDO.setNamespaceId(0);
        return (AppInfoDO)this.getSqlMapClientTemplate().queryForObject("app_info.getAppInfoByType", appInfoDO);
    }

    @Override
    public int deleteAppInfo(long appId) throws DAOException {
        return this.getSqlMapClientTemplate().update("app_info.deleteAppInfo", appId);
    }

    @Override
    public int deleteAppInfoByBizCode(String bizCode) throws DAOException {
        AppInfoDO appInfoDO = new AppInfoDO();
        appInfoDO.setBizCode(bizCode);
        appInfoDO.setDeleteVersion(System.currentTimeMillis());
        return this.getSqlMapClientTemplate().update("app_info.deleteAppInfoByBizCode", appInfoDO);
    }
}
