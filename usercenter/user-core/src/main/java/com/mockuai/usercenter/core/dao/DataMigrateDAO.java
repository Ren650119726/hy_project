package com.mockuai.usercenter.core.dao;

import com.mockuai.usercenter.core.domain.UserDO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;

/**
 * Created by zengzhangqiang on 6/16/15.
 */
public interface DataMigrateDAO {
    public long insertUser(UserDO userDO);

    public UserDO getUserByOrigin(long originUserId);

    public long insertUserOpenInfo(UserOpenInfoDO userOpenInfoDO);

    public UserOpenInfoDO getUserOpenInfo(int openType, String openUid);
}
