package com.mockuai.appcenter.core.dao.impl;

import com.mockuai.appcenter.core.dao.BizPropertyDAO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.exception.DAOException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class BizPropertyDAOImpl extends SqlMapClientDaoSupport implements BizPropertyDAO {
    @Override
    public long addBizProperty(BizPropertyDO bizPropertyDO) throws DAOException {
        long id = (Long)this.getSqlMapClientTemplate().insert("biz_property.addBizProperty",bizPropertyDO);
        return id;
    }

    @Override
    public List<BizPropertyDO> getBizPropertyList(String bizCode) throws DAOException {
        return (List<BizPropertyDO>)this.getSqlMapClientTemplate().queryForList(
                "biz_property.getBizPropertyList", bizCode);

    }

    @Override
    public BizPropertyDO getBizProperty(String bizCode, String pKey) throws DAOException {
        BizPropertyDO bizPropertyDO = new BizPropertyDO();
        bizPropertyDO.setBizCode(bizCode);
        bizPropertyDO.setpKey(pKey);
        return (BizPropertyDO)this.getSqlMapClientTemplate().queryForObject(
                "biz_property.getBizProperty", bizPropertyDO);
    }

    @Override
    public int deleteBizProperty(String bizCode, String pKey) throws DAOException {
        BizPropertyDO bizPropertyDO = new BizPropertyDO();
        bizPropertyDO.setBizCode(bizCode);
        bizPropertyDO.setpKey(pKey);
        bizPropertyDO.setDeleteVersion(System.currentTimeMillis());
        return this.getSqlMapClientTemplate().update(
                "biz_property.deleteBizProperty", bizPropertyDO);
    }

    @Override
    public int updateBizProperty(BizPropertyDO bizPropertyDO) throws DAOException {
        return this.getSqlMapClientTemplate().update(
                "biz_property.updateBizProperty", bizPropertyDO);
    }

    @Override
    public int deleteBizProperties(String bizCode) throws DAOException {
        BizPropertyDO bizPropertyDO = new BizPropertyDO();
        bizPropertyDO.setBizCode(bizCode);
        bizPropertyDO.setDeleteVersion(System.currentTimeMillis());
        return this.getSqlMapClientTemplate().update(
                "biz_property.deleteBizProperties", bizPropertyDO);
    }
}
