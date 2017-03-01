package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.usercenter.common.dto.UserDTO;

import java.util.List;

/**
 * Created by duke on 15/9/28.
 */
public interface UserManager {
    /**
     * 通过邀请码查询用户
     */
    List<UserDTO> getUserByInvitationCode(String invitationCode, String appKey) throws VirtualWealthException;

    /**
     * 根据用户名查询用户列表
     *
     * @param userName
     * @param appKey
     * @return
     * @throws VirtualWealthException
     */
    List<UserDTO> queryUserByUserName(String userName, String appKey) throws VirtualWealthException;

    /**
     * 根据用户 id 查询用户列表
     *
     * @param userIds
     * @param appKey
     * @return
     * @throws VirtualWealthException
     */
    List<UserDTO> queryByUserIdList(List<Long> userIds, String appKey) throws VirtualWealthException;


    /**
     * @param mobiles
     * @param appKey
     * @return
     * @throws VirtualWealthException
     */
    List<UserDTO> queryUserByMobiles(List<String> mobiles, String appKey) throws VirtualWealthException;

    UserDTO getUserById(Long id, String appKey) throws VirtualWealthException;
}
