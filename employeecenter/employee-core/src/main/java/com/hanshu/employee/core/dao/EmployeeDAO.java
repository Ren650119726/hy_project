package com.hanshu.employee.core.dao;

import com.hanshu.employee.common.qto.EmployeeQTO;
import com.hanshu.employee.core.domain.EmployeeDO;

import java.util.List;
import java.util.Map;

/**
 * Created by yeliming on 16/5/9.
 */
public interface EmployeeDAO {
    Long addEmployee(EmployeeDO employeeDO);

    EmployeeDO getEmployeeById(Long id);

    int updateEmployee(EmployeeDO employeeDO);

    EmployeeDO getEmployeeByUserName(String userName);

    List<EmployeeDO> queryEmployee(EmployeeQTO employeeQTO);

    Long getTotal(EmployeeQTO employeeQTO);

    Integer deleteEmployee(Long managerId);

    EmployeeDO employeeLogin(EmployeeQTO employeeQTO);

    void updateLastLogin(Long id);

    Map<Long, Long> totalRoleGroupCount(EmployeeQTO employeeQTO);

    Long getRoleGroupCount(EmployeeQTO employeeQTO);

    int updatePassword(Long employeeId, String password);
}
