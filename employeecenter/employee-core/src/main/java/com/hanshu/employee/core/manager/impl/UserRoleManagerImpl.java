package com.hanshu.employee.core.manager.impl;

import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.UserRoleDTO;
import com.hanshu.employee.common.qto.EmployeeQTO;
import com.hanshu.employee.common.qto.UserRoleQTO;
import com.hanshu.employee.core.dao.EmployeeDAO;
import com.hanshu.employee.core.dao.UserRoleDAO;
import com.hanshu.employee.core.domain.EmployeeDO;
import com.hanshu.employee.core.domain.UserRoleDO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.UserRoleManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
@Service
public class UserRoleManagerImpl implements UserRoleManager {
    @Resource
    private UserRoleDAO userRoleDAO;
    @Resource
    private EmployeeDAO employeeDAO;

    @Override
    public Long addUserRole(UserRoleDTO userRoleDTO) throws EmployeeException {

        String roleName = userRoleDTO.getRoleName();
        UserRoleDO userRoleDO1 = this.userRoleDAO.getUserRoleByRoleName(roleName);
        if (userRoleDO1 != null) {
            throw new EmployeeException(ResponseCode.B_RECORD_IS_EXIST);
        }

        UserRoleDO userRoleDO = new UserRoleDO();
        BeanUtils.copyProperties(userRoleDTO, userRoleDO);
        Long id = this.userRoleDAO.addUserRole(userRoleDO);
        return id;
    }

    @Override
    public UserRoleDTO getUserRoleById(Long id) {
        UserRoleDO userRoleDO = this.userRoleDAO.getUserRoleById(id);
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        BeanUtils.copyProperties(userRoleDO, userRoleDTO);
        return userRoleDTO;
    }

    @Override
    public Boolean deleteUserRole(Long userRoleId) throws EmployeeException {
        UserRoleDO userRoleDO = this.userRoleDAO.getUserRoleById(userRoleId);
        if (userRoleDO == null) {
            throw new EmployeeException(ResponseCode.B_SELECT_ERROR);
        }

        //该角色不被使用时,才可删除
        EmployeeQTO employeeQTO = new EmployeeQTO();
        employeeQTO.setRoleId(userRoleId);
        List<EmployeeDO> employeeDOs = this.employeeDAO.queryEmployee(employeeQTO);
        if (!employeeDOs.isEmpty()) {
            throw new EmployeeException(ResponseCode.B_RECORD_IS_EXIST, "不能删除使用中的角色");
        }

        int n = this.userRoleDAO.deleteUserRole(userRoleId);
        if (n == 1) {
            return true;
        } else {
            throw new EmployeeException(ResponseCode.B_DELETE_ERROR, "删除用户角色失败");
        }
    }

    @Override
    public List<UserRoleDTO> queryUserRole(UserRoleQTO userRoleQTO) {
        List<UserRoleDO> userRoleDOs = this.userRoleDAO.queryUserRole(userRoleQTO);
        if (userRoleDOs != null) {
            List<UserRoleDTO> userRoleDTOs = new ArrayList<UserRoleDTO>();
            for (UserRoleDO userRoleDO : userRoleDOs) {
                UserRoleDTO userRoleDTO = new UserRoleDTO();
                BeanUtils.copyProperties(userRoleDO, userRoleDTO);
                userRoleDTOs.add(userRoleDTO);
            }
            return userRoleDTOs;

        } else {
            return null;
        }
    }

    @Override
    public Long getTotalCount(UserRoleQTO userRoleQTO) {
        return this.userRoleDAO.getTotalCount(userRoleQTO);
    }

    @Override
    public Boolean updateUserRole(UserRoleDTO userRoleDTO) throws EmployeeException {

        UserRoleDO userRoleDO1 = this.userRoleDAO.getUserRoleByRoleName(userRoleDTO.getRoleName());
        if (userRoleDO1 != null && !userRoleDO1.getId().equals(userRoleDTO.getId())) {
            throw new EmployeeException(ResponseCode.B_RECORD_IS_EXIST);
        }

        UserRoleDO userRoleDO = new UserRoleDO();
        BeanUtils.copyProperties(userRoleDTO, userRoleDO);
        int n = this.userRoleDAO.updateUserRole(userRoleDO);
        if (n == 1) {
            return true;
        } else {
            throw new EmployeeException(ResponseCode.B_UPDATE_ERROR, "更新用户角色出错");
        }
    }
}
