package com.mockuai.usercenter.core;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.dto.SupplierCompanyDTO;
import com.mockuai.usercenter.common.qto.SupplierCompanyQTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CompanyTest {
	@Resource
	private UserDispatchService userDispatchService;

	@Test
	public void test() {

		Request request = new BaseRequest();

		SupplierCompanyQTO companyQto = new SupplierCompanyQTO();

		companyQto.setPageSize(5);
		request.setParam("companyQto", companyQto);

		request.setCommand(ActionEnum.QUERY_SUPPLIER_COMPANY.getActionName());

		/*
		 * request.setParam("name", "绍兴公司"); request.setParam("sort", 6);
		 * request.setCommand(ActionEnum.ADD_SUPPLIER_COMPANY.getActionName());
		 */
		/*
		 * request.setParam("id", 4); request.setParam("name", "南昌公司");
		 * request.setParam("sort", 10);
		 * 
		 * request.setCommand(ActionEnum.UPDATE_SUPPLIER_COMPANY.getActionName())
		 * ;
		 */
		/*
		 * request.setParam("id", 3);
		 * request.setCommand(ActionEnum.DELETE_SUPPLIER_COMPANY
		 * .getActionName());
		 */

		Response response = userDispatchService.execute(request);

		System.out.println(2);

	}

	@Test
	public void addCompany() {
		Request request = new BaseRequest();
		SupplierCompanyDTO supplierCompanyDto = new SupplierCompanyDTO();
		supplierCompanyDto.setName("大飛機公司");
		supplierCompanyDto.setSort(50);
		request.setParam("supplierCompanyDTO", supplierCompanyDto);
		request.setCommand(ActionEnum.ADD_SUPPLIER_COMPANY.getActionName());
		Response response = userDispatchService.execute(request);
	}

	@Test
	public void updateCompany() {
		Request request = new BaseRequest();
		SupplierCompanyDTO supplierCompanyDto = new SupplierCompanyDTO();
		supplierCompanyDto.setId(4);
		supplierCompanyDto.setName("達菲雞");
		supplierCompanyDto.setSort(12);

		request.setParam("supplierCompanyDTO", supplierCompanyDto);

		request.setCommand(ActionEnum.UPDATE_SUPPLIER_COMPANY.getActionName());
		Response response = userDispatchService.execute(request);
	}

	@Test
	public void delCompany() {
		Request request = new BaseRequest();
		request.setParam("id", 4);
		request.setCommand(ActionEnum.DELETE_SUPPLIER_COMPANY.getActionName());
		Response response = userDispatchService.execute(request);
	}

	@Test
	public void queryCompany() {
		Request request = new BaseRequest();
		SupplierCompanyQTO companyQto = new SupplierCompanyQTO();

		companyQto.setPageSize(5);
		request.setParam("companyQTO", companyQto);

		request.setCommand(ActionEnum.QUERY_SUPPLIER_COMPANY.getActionName());
		Response response = userDispatchService.execute(request);
	}

}
