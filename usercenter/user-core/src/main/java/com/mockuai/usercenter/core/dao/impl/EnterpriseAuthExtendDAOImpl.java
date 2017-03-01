package com.mockuai.usercenter.core.dao.impl;

import com.mockuai.usercenter.core.dao.EnterpriseAuthExtendDAO;
import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/6/15.
 */

@Service
public class EnterpriseAuthExtendDAOImpl extends SqlMapClientDaoSupport implements EnterpriseAuthExtendDAO{
    public Long addEnterpriseAuthExtend(EnterpriseAuthExtendDO enterpriseAuthExtendDO) {
        return (Long)getSqlMapClientTemplate().insert(
                "enterprise_auth_extend.addEnterpriseAuthExtend", enterpriseAuthExtendDO);
    }

    public EnterpriseAuthExtendDO getEnterpriseAuthExtend(long userId) {
        return (EnterpriseAuthExtendDO)getSqlMapClientTemplate().queryForObject(
                "enterprise_auth_extend.getEnterpriseAuthExtend", userId);
    }

    public List<EnterpriseAuthExtendDO> queryEnterpriseAuthExtend(List<Long> userIdList) {
        return (List<EnterpriseAuthExtendDO>)getSqlMapClientTemplate().queryForList(
                "enterprise_auth_extend.queryEnterpriseAuthExtend", userIdList);
    }

    @Override
    public int updateEnterpriseAuthExtend(EnterpriseAuthExtendDO enterpriseAuthExtendDO) {
        return getSqlMapClientTemplate().update(
                "enterprise_auth_extend.updateEnterpriseAuthExtend", enterpriseAuthExtendDO);
    }
}
