package com.hanshu.employee.core.manager;

import com.hanshu.employee.common.dto.EmployeeLogDTO;
import com.hanshu.employee.common.qto.EmployeeLogQTO;

import java.util.List;

/**
 * Created by yeliming on 16/5/19.
 */
public interface EmployeeLogManager {
    Long genEmployeeLog(EmployeeLogDTO employeeLogDTO);

    List<EmployeeLogDTO> queryEmployeeLog(EmployeeLogQTO employeeLogQTO);

    Long getTotalCount(EmployeeLogQTO employeeLogQTO);
}
