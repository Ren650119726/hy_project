package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.usercenter.common.dto.UserDTO;

import java.util.List;

/**
 * Created by duke on 15/11/4.
 */
public interface UserManager {
    /**
     * 通过用户ID获得用户
     * */
    UserDTO getUserByUserId(Long userId, String appKey) throws DistributionException;

    /**
     * 通过ID列表查询
     * */
    List<UserDTO> getUserByIdList(List<Long> userIdList, String appKey) throws DistributionException;

    UserDTO getUserByMobile(String mobile, String appKey) throws DistributionException;

    UserDTO addUser(UserDTO userDTO, String appKey) throws DistributionException;

    Boolean updateUser(UserDTO userDTO, String appKey) throws DistributionException;

    void updateInvitertId(Long userId, Long inviterId, String appKey) throws DistributionException;

    boolean updateRoleType(Long userId, Long roleType, String appKey) throws DistributionException;
}
