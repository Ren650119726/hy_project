package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.UserManager;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/11/4.
 */
@Component
public class UserManagerImpl implements UserManager {
    @Autowired
    private UserClient userClient;

    @Override
    public UserDTO getUserByUserId(Long userId, String appKey) throws DistributionException {
        Response<UserDTO> response = userClient.getUserById(userId, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw new DistributionException(response.getCode(), response.getMessage());
        }
    }


    @Override
    public List<UserDTO> getUserByIdList(List<Long> userIdList, String appKey) throws DistributionException {
        Response<List<UserDTO>> response = userClient.queryFromIdList(userIdList, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw new DistributionException(response.getCode(), response.getMessage());
        }
    }

    @Override
    public UserDTO getUserByMobile(String mobile, String appKey) throws DistributionException {
        Response<UserDTO> response = userClient.getUserByMobile(mobile, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public UserDTO addUser(UserDTO userDTO, String appKey) throws DistributionException {
        Response<UserDTO> response = userClient.addUser(userDTO, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public Boolean updateUser(UserDTO userDTO, String appKey) throws DistributionException {
        Response<Boolean> response = userClient.updateUser(userDTO, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public void updateInvitertId(Long userId, Long inviterId, String appKey) throws DistributionException {
        Response<Void> response = userClient.updateInviterId(userId, inviterId, appKey);
        if (!response.isSuccess()) {
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public boolean updateRoleType(Long userId, Long roleType, String appKey) throws DistributionException {
        Response<Boolean> response = userClient.updateRoleType(userId, roleType, appKey);
        if (!response.isSuccess()) {
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        }
        return response.getModule();
    }
}
