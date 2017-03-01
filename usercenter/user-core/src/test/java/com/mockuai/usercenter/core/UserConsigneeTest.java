package com.mockuai.usercenter.core;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserConsigneeTest {
	@Resource
	private UserDispatchService userDispatchService;

	@Test
	public void test() {

		Request request = new BaseRequest();

		/*
		 * ConsigneeDTO consigneeDto = new ConsigneeDTO();
		 * consigneeDto.setReceiverId(11L); consigneeDto.setCountry(1);
		 * consigneeDto.setProvince(3); consigneeDto.setCity(4);
		 * consigneeDto.setArea(4); consigneeDto.setConsignee("金刚葫芦娃");
		 * consigneeDto.setAddress("文三路华星时代广场1205");
		 * consigneeDto.setMobileNo("13569874562");
		 * consigneeDto.setRemark("一个藤上8个挂");
		 * 
		 * request.setParam("consigneeDTO", consigneeDto);
		 * request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
		 */

		request.setParam("userId", 11L);
		// request.setParam("consigneeId", 5L);
		request.setCommand(ActionEnum.QUERY_CONSIGNEE.getActionName());

		Response response = userDispatchService.execute(request);

	}

	@Test
	public void addtest() {
		Request request = new BaseRequest();

		UserConsigneeDTO userConsigneeDto = new UserConsigneeDTO();
		userConsigneeDto.setUserId(12L);
		userConsigneeDto.setCountryCode("1"); 		userConsigneeDto.setProvinceCode("3"); 		userConsigneeDto.setCityCode("4"); 		userConsigneeDto.setAreaCode("4");
		userConsigneeDto.setConsignee("老五");
		userConsigneeDto.setAddress("文三路华星时代广场1205-1206");
		userConsigneeDto.setMobile("13569874562");
		request.setParam("consigneeDTO", userConsigneeDto);
		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());

		Response response = userDispatchService.execute(request);

	}

	@Test
	public void updatetest() {

		Request request = new BaseRequest();
		UserConsigneeDTO userConsigneeDto = new UserConsigneeDTO();
		userConsigneeDto.setId(9L);
		userConsigneeDto.setUserId(12L);
		userConsigneeDto.setCountryCode("1"); 		userConsigneeDto.setProvinceCode("3"); 		userConsigneeDto.setCityCode("4"); 		userConsigneeDto.setAreaCode("4");
		userConsigneeDto.setConsignee("老三");
		userConsigneeDto.setAddress("文三路华星时代广场1206");
		userConsigneeDto.setMobile("13569874562");
		request.setParam("consigneeDTO", userConsigneeDto);
		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());

		Response response = userDispatchService.execute(request);

	}

	@Test
	public void deltest() {
		Request request = new BaseRequest();
		request.setParam("userId", 12L);
		request.setParam("consigneeId", 8L);
		request.setCommand(ActionEnum.DELETE_CONSIGNEE.getActionName());
		Response response = userDispatchService.execute(request);
	}

	@Test
	public void queryConsignee() {
		Request request = new BaseRequest();
		request.setParam("userId", 11L);
		request.setCommand(ActionEnum.QUERY_CONSIGNEE.getActionName());
		Response response = userDispatchService.execute(request);
	}

	@Test
	public void setDefConfignee() {
		Request request = new BaseRequest();
		request.setParam("consigneeId", 8L);
		request.setParam("userId", 12L);
		request.setCommand(ActionEnum.SET_DEFAULT_CONSIGNEE.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
