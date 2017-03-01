package com.hanshu.employee.core.dao;

import com.hanshu.employee.common.qto.EmployeeLogQTO;
import com.hanshu.employee.core.domain.EmployeeLogDO;

import java.util.List;

/**
 * Created by yeliming on 16/5/19.
 */
public interface EmployeeLogDAO {
    Long genEmployeeLog(EmployeeLogDO employeeLogDO);

    List<EmployeeLogDO> queryEmployeeLog(EmployeeLogQTO employeeLogQTO);

    Long getTotalCount(EmployeeLogQTO employeeLogQTO);
}
