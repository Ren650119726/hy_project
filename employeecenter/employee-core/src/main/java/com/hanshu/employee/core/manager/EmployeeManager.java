package com.hanshu.employee.core.manager;


import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.common.qto.EmployeeQTO;
import com.hanshu.employee.core.exception.EmployeeException;

import java.util.List;
import java.util.Map;

/**
 * Created by yeliming on 16/5/9.
 */
public interface EmployeeManager {
    /**
     * 根据用户名,查找管理员
     */
    EmployeeDTO getEmployeeByUserName(String userName);

    /**
     * 添加管理员账号
     */
    Long addEmployee(EmployeeDTO employeeDTO) throws EmployeeException;

    /**
     * 根据id,获取管理员
     */
    EmployeeDTO getEmployeeById(Long id) throws EmployeeException;

    /**
     * 更新用户
     */
    Boolean updateEmployee(EmployeeDTO employeeDTO) throws EmployeeException;

    /**
     * 查询管理员
     */
    List<EmployeeDTO> queryEmployee(EmployeeQTO employeeQTO);

    /**
     * 查询管理员数量
     */
    Long getTotalCount(EmployeeQTO employeeQTO);

    /**
     * 删除非超管
     *
     * @param employeeId
     */
    Boolean deleteEmployee(Long employeeId) throws EmployeeException;

    /**
     * 管理员登录
     *
     * @param employeeQTO
     */
    EmployeeDTO employeeLogin(EmployeeQTO employeeQTO);

    /**
     * 更新登录日期
     */
    void updateLastLogin(Long id);

    /**
     * 统计管理员的数量,按照role_id分组
     *
     * @param employeeQTO
     */
    Map<Long, Long> totalRoleGroupCount(EmployeeQTO employeeQTO);

    /**
     * 获取role_id的总数
     *
     * @param employeeQTO
     * @return
     */
    Long getRoleGroupCount(EmployeeQTO employeeQTO);

    /**
     * 更新管理员状态
     *
     * @param employeeDTO
     * @return
     */
    Boolean updateemployeeStatus(EmployeeDTO employeeDTO) throws EmployeeException;

    /**
     * 修改密码
     *
     * @param employeeId
     * @param password
     * @return
     */
    Boolean updatePassword(Long employeeId, String password) throws EmployeeException;
}
