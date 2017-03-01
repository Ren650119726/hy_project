package com.hanshu.employee.core.manager;

import com.hanshu.employee.common.dto.UserRoleDTO;
import com.hanshu.employee.common.qto.UserRoleQTO;
import com.hanshu.employee.core.exception.EmployeeException;

import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
public interface UserRoleManager {
    Long addUserRole(UserRoleDTO menuDTO) throws EmployeeException;

    UserRoleDTO getUserRoleById(Long id);

    Boolean deleteUserRole(Long menuId) throws EmployeeException;

    List<UserRoleDTO> queryUserRole(UserRoleQTO userRoleQTO);

    Long getTotalCount(UserRoleQTO userRoleQTO);

    Boolean updateUserRole(UserRoleDTO userRoleDTO) throws EmployeeException;


}
