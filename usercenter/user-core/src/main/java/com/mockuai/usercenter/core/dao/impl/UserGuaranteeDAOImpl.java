package com.mockuai.usercenter.core.dao.impl;

import com.mockuai.usercenter.common.qto.UserGuaranteeQTO;
import com.mockuai.usercenter.core.dao.UserGuaranteeDAO;
import com.mockuai.usercenter.core.domain.UserGuaranteeDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yeliming on 16/1/23.
 */
@Service
public class UserGuaranteeDAOImpl extends SqlMapClientDaoSupport implements UserGuaranteeDAO {
    @Override
    public Long addUserGuarantee(UserGuaranteeDO userGuaranteeDO) {
        return (Long) this.getSqlMapClientTemplate().insert("guarantee.addGuaranteeInfo", userGuaranteeDO);
    }

    @Override
    public UserGuaranteeDO getUserGuaranteeById(Long id) {
        return (UserGuaranteeDO) this.getSqlMapClientTemplate().queryForObject("guarantee.selectById",id);
    }

    @Override
    public Integer updateUserGuarantee(UserGuaranteeDO userGuaranteeDO) {
        return this.getSqlMapClientTemplate().update("guarantee.updateGuaranteeInfo", userGuaranteeDO);
    }

    @Override
    public List<UserGuaranteeDO> queryUserGuarantee(UserGuaranteeQTO userGuaranteeQTO) {
        return this.getSqlMapClientTemplate().queryForList("guarantee.queryGuaranteeInfo", userGuaranteeQTO);
    }

    @Override
    public Long totalUserGuarantee(UserGuaranteeQTO userGuaranteeQTO) {
        return (Long) this.getSqlMapClientTemplate().queryForObject("guarantee.totalGuaranteeInfo", userGuaranteeQTO);
    }

    @Override
    public Integer deleteUserGuarantee(Long id) {
        return this.getSqlMapClientTemplate().update("guarantee.deleteGuaranteeInfo", id);
    }
}
