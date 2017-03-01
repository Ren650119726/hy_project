package com.hanshu.employee.client;


import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.UserRoleDTO;
import com.hanshu.employee.common.qto.UserRoleQTO;

import java.util.List;

/**
 * Created by yeliming on 16/5/12.
 */
public interface UserRoleClient {
    Response<Long> addUserRole(UserRoleDTO userRoleDTO, String appKey);

    Response<Boolean> updateUserRole(UserRoleDTO userRoleDTO, String appKey);

    Response<Boolean> deleteUserRole(Long userRoleId, String appKey);

    Response<List<UserRoleDTO>> queryUserRole(UserRoleQTO userRoleQTO, String appKey);

    Response<UserRoleDTO> getUserRole(Long userRoleId, String appKey);
}
