package com.hanshu.employee.client;

import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.common.qto.EmployeeQTO;

import java.util.List;
import java.util.Map;

/**
 * Created by yeliming on 16/5/9.
 */
public interface EmployeeClient {
    /**
     * 新增管理员
     *
     * @param employeeDTO
     * @param appKey
     * @return
     */
    Response<EmployeeDTO> addEmployee(EmployeeDTO employeeDTO, String appKey);

    /**
     * 编辑管理员
     *
     * @param employeeDTO
     * @param appKey
     * @return
     */
    Response<Boolean> updateEmployee(EmployeeDTO employeeDTO, String appKey);

    /**
     * 删除管理员
     *
     * @param managerId
     * @param appKey
     * @return
     */
    Response<Boolean> deleteEmployee(Long managerId, String appKey);

    /**
     * 查询管理员
     *
     * @param employeeQTO
     * @param appKey
     * @return
     */
    Response<List<EmployeeDTO>> queryEmployee(EmployeeQTO employeeQTO, String appKey);

    /**
     * 获取管理员
     *
     * @param managerId
     * @param appKey
     * @return
     */
    Response<EmployeeDTO> getEmployeeById(Long managerId, String appKey);

    /**
     * 管理员登录接口
     *
     * @param employeeQTO
     * @param appKey
     * @return
     */
    Response<EmployeeDTO> employeeLogin(EmployeeQTO employeeQTO, String appKey);

    /**
     * 统计管理员的数量,按照role_id分组
     *
     * @param employeeQTO
     * @param appKey
     * @return
     */
    Response<Map<Long, Long>> totalRoleGroupCount(EmployeeQTO employeeQTO, String appKey);

    /**
     * 更新管理员状态
     *
     * @param employeeDTO
     * @param appKey
     * @return
     */
    Response<Boolean> updateEmployeeStatus(EmployeeDTO employeeDTO, String appKey);

    /**
     * 修改密码
     *
     * @param employeeId
     * @param password
     * @param appKey
     * @return
     */
    Response<Boolean> updatePassword(Long employeeId, String password, String appKey);

}
