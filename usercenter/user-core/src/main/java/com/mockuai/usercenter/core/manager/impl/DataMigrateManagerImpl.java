package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.dao.DataMigrateDAO;
import com.mockuai.usercenter.core.domain.UserDO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.DataMigrateManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/16/15.
 */
@Component
public class DataMigrateManagerImpl implements DataMigrateManager{

    @Resource
    private DataMigrateDAO dataMigrateDAO;

    @Override
    public long insertUser(UserDO userDO) throws UserException {
        if(userDO == null){
            throw new UserException(ResponseCode.P_PARAM_NULL, "userDO is null");
        }

        //通过数据迁移新增的用户，type置为3，方便后续做登录兼容
        userDO.setType(3);

        //临时将deleteMark置为10，等验证导入的数据没问题之后再打开

        //对于数据迁移导入的用户数据，origin_user_id必须存在
        if(userDO.getOriginUserId() == null){
            throw new UserException(ResponseCode.P_PARAM_ERROR, "user.origin_user_id is missing!");
        }

        userDO.setDeleteMark(10);
        try{
            long id = dataMigrateDAO.insertUser(userDO);
            return id;
        }catch(Exception e){
            throw new UserException(ResponseCode.B_ADD_ERROR);
        }
    }

    @Override
    public UserDO getUserByOrigin(long originUserId) throws UserException {
        try{
            UserDO userDO = dataMigrateDAO.getUserByOrigin(originUserId);
            return userDO;
        }catch(Exception e){
            throw new UserException(ResponseCode.B_SELECT_ERROR);
        }

    }

    @Override
    public long insertUserOpenInfo(UserOpenInfoDO userOpenInfoDO) throws UserException {
        if(userOpenInfoDO == null){
            throw new UserException(ResponseCode.P_PARAM_NULL, "userOpenInfoDO is null");
        }

        if(StringUtils.isBlank(userOpenInfoDO.getOpenUid())){
            throw new UserException(ResponseCode.P_PARAM_NULL, "openUid is null");
        }

        //临时将deleteMark置为10，等验证导入的数据没问题之后再打开
        userOpenInfoDO.setDeleteMark(10);
        try{
            long id = dataMigrateDAO.insertUserOpenInfo(userOpenInfoDO);
            return id;
        }catch(Exception e){
            throw new UserException(ResponseCode.B_ADD_ERROR);
        }
    }

    @Override
    public UserOpenInfoDO getUserOpenInfo(int openType, String openUid) throws UserException {
        if(StringUtils.isBlank(openUid)){
            throw new UserException(ResponseCode.P_PARAM_NULL, "openUid is null");
        }

        try{
            UserOpenInfoDO userOpenInfoDO = dataMigrateDAO.getUserOpenInfo(openType, openUid);
            return userOpenInfoDO;
        }catch(Exception e){
            throw new UserException(ResponseCode.B_SELECT_ERROR);
        }
    }
}
