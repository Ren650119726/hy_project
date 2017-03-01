package com.hanshu.employee.client;

import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.EmployeeLogDTO;
import com.hanshu.employee.common.qto.EmployeeLogQTO;

import java.util.List;

/**
 * Created by yeliming on 16/5/19.
 */
public interface EmployeeLogClient {
    /**
     * 产生日志
     */
    Response<Long> genEmployeeOpLog(EmployeeLogDTO employeeLogDTO, String appKey);

    /**
     * 检索日志
     */
    Response<List<EmployeeLogDTO>> queryEmployeeOpLog(EmployeeLogQTO employeeLogQTO, String appKey);
}
