package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.core.domain.UserDO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;

/**
 * Created by zengzhangqiang on 6/16/15.
 */
public interface DataMigrateManager {

    /**
     * 插入指定用户信息
     * @param userDO
     * @return
     * @throws UserException
     */
    public long insertUser(UserDO userDO) throws UserException;

    /**
     * 根据应用原始用户ID查询用户信息
     * @param originUserId
     * @return
     * @throws UserException
     */
    public UserDO getUserByOrigin(long originUserId) throws UserException;

    /**
     * 插入指定用户开放信息
     * @param userOpenInfoDO
     * @return
     * @throws UserException
     */
    public long insertUserOpenInfo(UserOpenInfoDO userOpenInfoDO) throws UserException;

    /**
     * 查询用户开放信息
     * @param openType
     * @param openUid
     * @return
     * @throws UserException
     */
    public UserOpenInfoDO getUserOpenInfo(int openType, String openUid) throws UserException;
}
