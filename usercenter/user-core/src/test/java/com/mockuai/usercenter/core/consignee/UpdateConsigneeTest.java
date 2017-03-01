//package com.mockuai.usercenter.core.consignee;
//
//import javax.annotation.Resource;
//
//import com.mockuai.usercenter.common.api.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.dto.ConsigneeDTO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class UpdateConsigneeTest {
//
//	@Resource
//	private UserDispatchService userDispatchService;
//
//	/**
//	 * 修改收货地址 测试结果：正确修改
//	 * */
//	@Test
//	public void test1() {
//		Request request = new BaseRequest();
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(9L);
//		consigneeDto.setUserId(12L);
//		consigneeDto.setCountry(1);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(4);
//		consigneeDto.setArea(4);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老三");
//		consigneeDto.setAddress("文三路华星时代广场1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 手机格式错误条件修改手机地址 测试结果：mobile format error
//	 * */
//	@Test
//	public void test2() {
//		Request request = new BaseRequest();
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(9L);
//		consigneeDto.setUserId(12L);
//		consigneeDto.setCountry(1);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(4);
//		consigneeDto.setArea(4);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老三");
//		consigneeDto.setAddress("文三路华星时代广场1206");
//		consigneeDto.setMobileNo("1356987452");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * consigneeDto为空的情况下修改 测试结果：consigneeDTO is null
//	 */
//	@Test
//	public void test3() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = null;
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址为空的的情况下修改 测试结果：address is null
//	 */
//	@Test
//	public void test4() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(8);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上10个瓜");
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的国家为空 测试结果：user consignee country is null
//	 */
//	@Test
//	public void test6() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(8);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址国家等级错误 测试结果：user consignee country level error
//	 */
//	@Test
//	public void test7() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(3);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(8);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的省份为空 测试结果：user consignee province is null
//	 */
//	@Test
//	public void test8() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(8);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的省份不存在 测试结果：user consignee province is not exist
//	 */
//	@Test
//	public void test9() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setUserId(11L);
//		consigneeDto.setId(29L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(1000);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(8);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址省份等级错误 测试结果：user consignee province level error
//	 */
//	@Test
//	public void test10() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(7);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(8);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的城市为空 测试结果：user consignee city is null
//	 */
//	@Test
//	public void test11() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setArea(8);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的城市不存在 测试结果：user consignee city is not exist
//	 */
//	@Test
//	public void test13() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(100);
//		consigneeDto.setArea(8);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址城市等级错误 测试结果：user consignee city level error
//	 */
//	@Test
//	public void test14() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(8);
//		consigneeDto.setArea(8);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的区县为空 测试结果：user consignee area is null
//	 */
//	@Test
//	public void test15() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(7);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的区县不存在 测试结果：user consignee area is not exist
//	 */
//	@Test
//	public void test16() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(100);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址区县等级错误 测试结果：user consignee area level error
//	 */
//	@Test
//	public void test17() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(7);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的乡镇不存在 测试结果：user consignee town is not exist
//	 */
//	@Test
//	public void test18() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(8);
//		consigneeDto.setTown(100);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址乡镇等级错误 测试结果：user consignee town level error
//	 */
//	@Test
//	public void test19() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setId(29L);
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(8);
//		consigneeDto.setTown(7);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//}
