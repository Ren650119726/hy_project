package com.hanshu.employee.core.dao;

import com.hanshu.employee.common.qto.UserRoleQTO;
import com.hanshu.employee.core.domain.UserRoleDO;
import com.hanshu.employee.core.exception.EmployeeException;

import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
public interface UserRoleDAO {
    Long addUserRole(UserRoleDO userRoleDO);

    UserRoleDO getUserRoleById(Long id);

    int deleteUserRole(Long menuId);

    List<UserRoleDO> queryUserRole(UserRoleQTO userRoleQTO);

    Long getTotalCount(UserRoleQTO userRoleQTO);

    int updateUserRole(UserRoleDO userRoleDO);

    UserRoleDO getUserRoleByRoleName(String roleName) throws EmployeeException;
}
