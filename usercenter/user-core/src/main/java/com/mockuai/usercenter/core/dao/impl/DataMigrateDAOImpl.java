package com.mockuai.usercenter.core.dao.impl;

import com.mockuai.usercenter.core.dao.DataMigrateDAO;
import com.mockuai.usercenter.core.domain.UserDO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/17/15.
 */
@Repository
public class DataMigrateDAOImpl extends SqlMapClientDaoSupport implements DataMigrateDAO{
    @Override
    public long insertUser(UserDO userDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("data_migrate.insertUser",
                userDO);
        return id;
    }

    @Override
    public UserDO getUserByOrigin(long originUserId) {
        UserDO userDO = (UserDO)getSqlMapClientTemplate().queryForObject("data_migrate.getUserByOrigin", originUserId);
        return userDO;
    }

    @Override
    public long insertUserOpenInfo(UserOpenInfoDO userOpenInfoDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("data_migrate.insertUserOpenInfo",
                userOpenInfoDO);
        return id;
    }

    @Override
    public UserOpenInfoDO getUserOpenInfo(int openType, String openUid) {
        UserOpenInfoDO userOpenInfoDO = new UserOpenInfoDO();
        userOpenInfoDO.setOpenType(openType);
        userOpenInfoDO.setOpenUid(openUid);
        userOpenInfoDO = (UserOpenInfoDO) getSqlMapClientTemplate().queryForObject(
                "data_migrate.getUserOpenInfo", userOpenInfoDO);
        return userOpenInfoDO;
    }
}
