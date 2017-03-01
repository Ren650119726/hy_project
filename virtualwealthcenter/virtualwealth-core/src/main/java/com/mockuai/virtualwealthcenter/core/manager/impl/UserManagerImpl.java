package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UserManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by duke on 15/9/28.
 */
@Service
public class UserManagerImpl implements UserManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerImpl.class.getName());

    @Resource
    private UserClient userClient;

    @Override
    public List<UserDTO> getUserByInvitationCode(String invitationCode, String appKey) throws VirtualWealthException {
        UserQTO userQTO = new UserQTO();
        userQTO.setInvitationCode(invitationCode);
        Response<List<UserDTO>> response = userClient.queryUser(userQTO, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw new VirtualWealthException(response.getCode(), response.getMessage());
        }
    }

    @Override
    public List<UserDTO> queryUserByUserName(String userName, String appKey) throws VirtualWealthException {

        try {
            UserQTO userQTO = new UserQTO();
            userQTO.setName(userName);
            Response<List<UserDTO>> response = userClient.queryUser(userQTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (Exception e) {
            LOGGER.error("failed when queryUserByUserName, userName : {}, appKey : {}", userName, appKey, e);
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public List<UserDTO> queryByUserIdList(List<Long> userIds, String appKey) throws VirtualWealthException {
        try {
            Response<List<UserDTO>> response = userClient.queryFromIdList(userIds, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (Exception e) {
            LOGGER.error("failed when queryByUserIdList, userIdList : {}, appKey : {}", Arrays.deepToString(userIds.toArray()), appKey, e);
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public List<UserDTO> queryUserByMobiles(List<String> mobiles, String appKey) throws VirtualWealthException {
        try {
            Response<List<UserDTO>> response = userClient.queryUserByMobiles(mobiles, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to queryUserByMobiles, mobiles : {}, appKey : {}, errCode : {}, errMsg : {}",
                    JsonUtil.toJson(mobiles), appKey, response.getCode(), response.getMessage());
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to queryUserByMobiles, mobiles : {}, appKey : {}",
                    Arrays.deepToString(mobiles.toArray()), appKey, e);
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public UserDTO getUserById(Long id, String appKey) throws VirtualWealthException {
        Response<UserDTO> response = userClient.getUserById(id, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            LOGGER.error("get user error, userId: {}", id);
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}