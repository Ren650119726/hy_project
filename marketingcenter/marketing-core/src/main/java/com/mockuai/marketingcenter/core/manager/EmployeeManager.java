package com.mockuai.marketingcenter.core.manager;

import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.common.qto.EmployeeQTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

/**
 * Created by edgar.zr on 8/02/2016.
 */
public interface EmployeeManager {

	EmployeeDTO getEmployeeByUserName(String name, String appKey) throws MarketingException;

	List<EmployeeDTO> queryEmployee(EmployeeQTO employeeQTO, String appKey) throws MarketingException;
}