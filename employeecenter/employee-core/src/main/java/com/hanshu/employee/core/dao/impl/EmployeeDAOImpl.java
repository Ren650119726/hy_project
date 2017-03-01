package com.hanshu.employee.core.dao.impl;

import com.hanshu.employee.common.qto.EmployeeQTO;
import com.hanshu.employee.core.dao.EmployeeDAO;
import com.hanshu.employee.core.domain.EmployeeDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class EmployeeDAOImpl extends SqlMapClientDaoSupport implements EmployeeDAO {

    @Override
    public Long addEmployee(EmployeeDO employeeDO) {
        return (Long) this.getSqlMapClientTemplate().insert("employee.addEmployee", employeeDO);
    }

    @Override
    public EmployeeDO getEmployeeById(Long id) {
        return (EmployeeDO) this.getSqlMapClientTemplate().queryForObject("employee.getEmployeeById", id);
    }

    @Override
    public int updateEmployee(EmployeeDO employeeDO) {
        return this.getSqlMapClientTemplate().update("employee.updateEmployee", employeeDO);
    }

    @Override
    public EmployeeDO getEmployeeByUserName(String userName) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        return (EmployeeDO) this.getSqlMapClientTemplate().queryForObject("employee.getEmployeeByUserName", map);
    }

    @Override
    public List<EmployeeDO> queryEmployee(EmployeeQTO employeeQTO) {
        return this.getSqlMapClientTemplate().queryForList("employee.queryEmployee", employeeQTO);
    }

    @Override
    public Long getTotal(EmployeeQTO employeeQTO) {
        return (Long) this.getSqlMapClientTemplate().queryForObject("employee.getTotalCount", employeeQTO);
    }

    @Override
    public Integer deleteEmployee(Long managerId) {
        return this.getSqlMapClientTemplate().update("employee.deleteEmployee", managerId);
    }

    @Override
    public EmployeeDO employeeLogin(EmployeeQTO employeeQTO) {
        return (EmployeeDO) this.getSqlMapClientTemplate().queryForObject("employee.employeeLogin", employeeQTO);
    }

    @Override
    public void updateLastLogin(Long id) {
        this.getSqlMapClientTemplate().update("employee.updateLastLogin", id);
    }

    @Override
    public Map<Long, Long> totalRoleGroupCount(EmployeeQTO employeeQTO) {
        return this.getSqlMapClientTemplate().queryForMap("employee.totalRoleGroupCount", employeeQTO, "roleId", "roleCount");
    }

    @Override
    public Long getRoleGroupCount(EmployeeQTO employeeQTO) {
        return (Long) this.getSqlMapClientTemplate().queryForObject("employee.getRoleGroupCount", employeeQTO);
    }

    @Override
    public int updatePassword(Long employeeId, String password) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("employeeId", employeeId + "");
        map.put("password", password);
        return this.getSqlMapClientTemplate().update("employee.updatePassword", map);
    }
}
