package com.mockuai.appcenter.core.dao.impl;

import com.mockuai.appcenter.core.dao.AppPropertyDAO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.AppPropertyDO;
import com.mockuai.appcenter.core.exception.DAOException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class AppPropertyDAOImpl extends SqlMapClientDaoSupport implements AppPropertyDAO{
    @Override
    public long addAppProperty(AppPropertyDO appPropertyDO) throws DAOException {
        long id = (Long)this.getSqlMapClientTemplate().insert("app_property.addAppProperty",appPropertyDO);
        return id;
    }

    @Override
    public List<AppPropertyDO> getAppPropertyList(Long appId) throws DAOException {
        return (List<AppPropertyDO>)this.getSqlMapClientTemplate().queryForList("app_property.getAppPropertyList", appId);
    }

    @Override
    public AppPropertyDO getAppProperty(Long appId, String pKey) throws DAOException {
        AppPropertyDO appPropertyDO = new AppPropertyDO();
        appPropertyDO.setAppId(appId);
        appPropertyDO.setpKey(pKey);
        return (AppPropertyDO)this.getSqlMapClientTemplate().queryForObject("app_property.getAppProperty", appPropertyDO);
    }

    @Override
    public int deleteAppProperty(Long appId, String pKey) throws DAOException {
        AppPropertyDO appPropertyDO = new AppPropertyDO();
        appPropertyDO.setAppId(appId);
        appPropertyDO.setpKey(pKey);
        appPropertyDO.setDeleteVersion(System.currentTimeMillis());
        return this.getSqlMapClientTemplate().update("app_property.deleteAppProperty", appPropertyDO);
    }

    @Override
    public int deleteAppPropertyByAppId(Long appId) throws DAOException {
        AppPropertyDO appPropertyDO = new AppPropertyDO();
        appPropertyDO.setAppId(appId);
        appPropertyDO.setDeleteVersion(System.currentTimeMillis());
        return this.getSqlMapClientTemplate().update("app_property.deleteAppPropertyByAppId", appPropertyDO);
    }

    @Override
    public int deleteAppPropertyByBizCode(String bizCode) throws DAOException {
        AppPropertyDO appPropertyDO = new AppPropertyDO();
        appPropertyDO.setBizCode(bizCode);
        appPropertyDO.setDeleteVersion(System.currentTimeMillis());
        return this.getSqlMapClientTemplate().update("app_property.deleteAppPropertyByBizCode", appPropertyDO);
    }
}
