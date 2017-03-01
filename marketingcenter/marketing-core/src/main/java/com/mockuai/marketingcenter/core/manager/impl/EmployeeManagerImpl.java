package com.mockuai.marketingcenter.core.manager.impl;

import com.hanshu.employee.client.EmployeeClient;
import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.common.qto.EmployeeQTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.EmployeeManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by edgar.zr on 8/02/2016.
 */
public class EmployeeManagerImpl implements EmployeeManager {

	public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeManagerImpl.class);

	@Autowired
	private EmployeeClient employeeClient;

	@Override
	public EmployeeDTO getEmployeeByUserName(String name, String appKey) throws MarketingException {
		try {

			EmployeeQTO employeeQTO = new EmployeeQTO();
			employeeQTO.setUserName(name);
			Response<List<EmployeeDTO>> response = employeeClient.queryEmployee(employeeQTO, appKey);
			if (response.isSuccess()) {
				if (response.getModule() != null && !response.getModule().isEmpty()) {
					return response.getModule().get(0);
				}
				return null;
			}
			LOGGER.error("error to getEmployeeByUserName, name : {}, appKey : {}, errCode : {}, errMsg : {}",
					name, appKey, response.getCode(), response.getMessage());
			throw new MarketingException(response.getCode(), response.getMessage());
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getEmployeeByUserName, name : {}, appKey : {}", name, appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<EmployeeDTO> queryEmployee(EmployeeQTO employeeQTO, String appKey) throws MarketingException {
		try {
			Response<List<EmployeeDTO>> response = employeeClient.queryEmployee(employeeQTO, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to queryEmployee, employeeQTO : {}, appKey : {}, errCode : {}, errMsg : {}",
					JsonUtil.toJson(employeeQTO), appKey, response.getCode(), response.getMessage());
			throw new MarketingException(response.getCode(), response.getMessage());
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to queryEmployee, employeeQTO : {}, appKey : {}", JsonUtil.toJson(employeeQTO), appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}