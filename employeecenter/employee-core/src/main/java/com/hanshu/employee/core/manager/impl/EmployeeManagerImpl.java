package com.hanshu.employee.core.manager.impl;

import com.hanshu.employee.common.constant.EmployeeStatus;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.common.qto.EmployeeQTO;
import com.hanshu.employee.core.dao.EmployeeDAO;
import com.hanshu.employee.core.dao.UserRoleDAO;
import com.hanshu.employee.core.domain.EmployeeDO;
import com.hanshu.employee.core.domain.UserRoleDO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.EmployeeManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class EmployeeManagerImpl implements EmployeeManager {

    @Resource
    private EmployeeDAO employeeDAO;
    @Resource
    private UserRoleDAO userRoleDAO;

    @Override
    public EmployeeDTO getEmployeeByUserName(String userName) {
        EmployeeDTO employeeDTO = null;
        EmployeeDO employeeDO = this.employeeDAO.getEmployeeByUserName(userName);
        if (employeeDO != null) {
            employeeDTO = new EmployeeDTO();
            BeanUtils.copyProperties(employeeDO, employeeDTO);
        }
        return employeeDTO;
    }

    @Override
    public Long addEmployee(EmployeeDTO employeeDTO) throws EmployeeException {

        //角色值校验
        Long roleId = employeeDTO.getRoleId();
        UserRoleDO userRoleDO = this.userRoleDAO.getUserRoleById(roleId);
        if (userRoleDO == null) {
            throw new EmployeeException(ResponseCode.B_SELECT_ERROR, "角色不存在");
        }

        //状态值校验
        if (employeeDTO.getStatus() != EmployeeStatus.NOMARL.getCode() && employeeDTO.getStatus() != EmployeeStatus.FORBIDDEN.getCode()) {
            throw new EmployeeException(ResponseCode.B_RECORD_IS_EXIST);
        }

        EmployeeDO employeeDO = new EmployeeDO();
        BeanUtils.copyProperties(employeeDTO, employeeDO);
        return this.employeeDAO.addEmployee(employeeDO);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) throws EmployeeException {
        EmployeeDO employeeDO = this.employeeDAO.getEmployeeById(id);
        if (employeeDO != null) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            BeanUtils.copyProperties(employeeDO, employeeDTO);
            return employeeDTO;
        } else {
            throw new EmployeeException(ResponseCode.B_SELECT_ERROR, "获取管理员出错");
        }
    }

    @Override
    public Boolean updateEmployee(EmployeeDTO employeeDTO) throws EmployeeException {
        //重名判断
        EmployeeDO employeeDO1 = this.employeeDAO.getEmployeeByUserName(employeeDTO.getUserName());
        if (employeeDO1 != null && !employeeDO1.getId().equals(employeeDTO.getId())) {
            throw new EmployeeException(ResponseCode.P_PARAM_INVALID, "用户名已存在");
        }

        //角色值校验
        Long roleId = employeeDTO.getRoleId();
        if (roleId != null && roleId != 0) {
            UserRoleDO userRoleDO = this.userRoleDAO.getUserRoleById(roleId);
            if (userRoleDO == null) {
                throw new EmployeeException(ResponseCode.B_SELECT_ERROR, "角色不存在");
            }
        }

        if (employeeDTO.getStatus() != null) {
            if (employeeDTO.getStatus() != EmployeeStatus.NOMARL.getCode() && employeeDTO.getStatus() != EmployeeStatus.FORBIDDEN.getCode()) {
                throw new EmployeeException(ResponseCode.B_RECORD_IS_EXIST);
            }
        }

        EmployeeDO employeeDO = new EmployeeDO();
        BeanUtils.copyProperties(employeeDTO, employeeDO);
        Integer n = this.employeeDAO.updateEmployee(employeeDO);
        if (n == 1) {
            return true;
        } else {
            throw new EmployeeException(ResponseCode.B_UPDATE_ERROR, "更新记录不能大于2条");
        }
    }

    @Override
    public List<EmployeeDTO> queryEmployee(EmployeeQTO employeeQTO) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
        List<EmployeeDO> employeeDOs = this.employeeDAO.queryEmployee(employeeQTO);
        if (employeeDTOs != null) {
            for (EmployeeDO employeeDO : employeeDOs) {
                employeeDO.setPassword(null);
                EmployeeDTO employeeDTO = new EmployeeDTO();
                BeanUtils.copyProperties(employeeDO, employeeDTO);
                employeeDTOs.add(employeeDTO);
            }
        }

        return employeeDTOs;

    }

    @Override
    public Long getTotalCount(EmployeeQTO employeeQTO) {
        return this.employeeDAO.getTotal(employeeQTO);
    }

    @Override
    public Boolean deleteEmployee(Long employeeId) throws EmployeeException {
        EmployeeDO employeeDO = this.employeeDAO.getEmployeeById(employeeId);
        if (employeeDO == null) {
            throw new EmployeeException(ResponseCode.B_SELECT_ERROR, "管理员不存在");
        }
        Integer n = this.employeeDAO.deleteEmployee(employeeId);
        if (n == 1) {
            return true;
        } else {
            throw new EmployeeException(ResponseCode.B_DELETE_ERROR, "删除非超管出错");
        }
    }

    @Override
    public EmployeeDTO employeeLogin(EmployeeQTO employeeQTO) {
        EmployeeDO storedEmployeeDO = this.employeeDAO.employeeLogin(employeeQTO);
        if (storedEmployeeDO != null) {
            storedEmployeeDO.setPassword(null);
            EmployeeDTO employeeDTO = new EmployeeDTO();
            BeanUtils.copyProperties(storedEmployeeDO, employeeDTO);
            return employeeDTO;
        } else {
            return null;
        }
    }

    @Override
    public void updateLastLogin(Long id) {
        this.employeeDAO.updateLastLogin(id);
    }

    @Override
    public Map<Long, Long> totalRoleGroupCount(EmployeeQTO employeeQTO) {
        return this.employeeDAO.totalRoleGroupCount(employeeQTO);
    }

    @Override
    public Long getRoleGroupCount(EmployeeQTO employeeQTO) {
        return this.employeeDAO.getRoleGroupCount(employeeQTO);
    }

    @Override
    public Boolean updateemployeeStatus(EmployeeDTO employeeDTO) throws EmployeeException {
        EmployeeDO employeeDO1 = this.employeeDAO.getEmployeeById(employeeDTO.getId());
        if (employeeDO1 == null) {
            throw new EmployeeException(ResponseCode.B_SELECT_ERROR, "管理员不存在");
        }
        EmployeeDO employeeDO = new EmployeeDO();
        BeanUtils.copyProperties(employeeDTO, employeeDO);
        int n = this.employeeDAO.updateEmployee(employeeDO);
        if (n == 1) {
            return true;
        } else {
            throw new EmployeeException(ResponseCode.B_UPDATE_ERROR, "更新管理员出错");
        }
    }

    @Override
    public Boolean updatePassword(Long employeeId, String password) throws EmployeeException {
        EmployeeDO employeeDO1 = this.employeeDAO.getEmployeeById(employeeId);
        if (employeeDO1 == null) {
            throw new EmployeeException(ResponseCode.B_SELECT_ERROR, "管理员不存在");
        }
        int n = this.employeeDAO.updatePassword(employeeId, password);
        if (n == 1) {
            return true;
        } else {
            throw new EmployeeException(ResponseCode.B_UPDATE_ERROR, "修改密码出错");
        }
    }
}
