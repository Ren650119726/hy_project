package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.UserManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.dto.UserDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/11/25.
 */
@Service
public class UserManagerImpl implements UserManager{

    @Resource
    private UserClient userClient;


    public UserDTO getUserById(Long id,String appKey) throws ItemException{

        try {

            com.mockuai.usercenter.common.api.Response<UserDTO> response = userClient.getUserById(id, appKey);

            if(response.getCode()==ResponseCode.SUCCESS.getCode()){
                return response.getModule();
            }else{
                throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,response.getMessage()+response.getCode());
            }

        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"根据ID查询用户信息时出现问题");
        }
    }
    @Override
    public Boolean isHiKe(Long id,String appKey) throws ItemException{

        try {

            com.mockuai.usercenter.common.api.Response<UserDTO> response = userClient.queryHiKeCondition(id, appKey);

            if(response.getCode()==ResponseCode.SUCCESS.getCode()){
                return response.getModule().getRoleMark() ==2;
            }else{
                throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,response.getMessage()+response.getCode());
            }

        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"根据ID查询用户信息时出现问题");
        }
    }

}
