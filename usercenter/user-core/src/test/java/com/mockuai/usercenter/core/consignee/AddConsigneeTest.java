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
//public class AddConsigneeTest {
//
//	@Resource
//	private UserDispatchService userDispatchService;
//
//	/**
//	 * 正确的添加用户收货地址 测试结果:正常添加
//	 * */
//	@Test
//	public void test1() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(5);
//		consigneeDto.setProvince(3);
//		consigneeDto.setCity(7);
//		consigneeDto.setArea(8);
//		consigneeDto.setTown(9);
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setZip("334513");
//		consigneeDto.setPhoneNo("0793-5888888");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * consigneeDto为空的情况下添加 测试结果：consigneeDto is null
//	 */
//	@Test
//	public void test2() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = null;
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址为空的的情况下添加 测试结果：address is null
//	 */
//	@Test
//	public void test3() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setUserId(12L);
//		consigneeDto.setCountryCode("1"); 		consigneeDto.setProvinceCode("3"); 		consigneeDto.setCityCode("4"); 		consigneeDto.setAreaCode("4");
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 手机号码错误的情况下添加 测试结果：mobile format error
//	 * */
//	@Test
//	public void test4() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setUserId(12L);
//		consigneeDto.setCountryCode("1"); 		consigneeDto.setProvinceCode("3"); 		consigneeDto.setCityCode("4"); 		consigneeDto.setAreaCode("4");
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("1356874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 用户拥有的收货地址大于最大值 测试结果：user consignee can't greater than 20
//	 * */
//	@Test
//	public void test5() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setUserId(12L);
//		consigneeDto.setCountryCode("1"); 		consigneeDto.setProvinceCode("3"); 		consigneeDto.setCityCode("4"); 		consigneeDto.setAreaCode("4");
//		consigneeDto.setIsDefault(true);
//		consigneeDto.setConsignee("老五");
//		consigneeDto.setAddress("文三路华星时代广场1205-1206");
//		consigneeDto.setMobileNo("13569874562");
//		consigneeDto.setRemark("一个藤上7个瓜");
//		consigneeDto.setIsDefault(true);
//		request.setParam("consigneeDTO", consigneeDto);
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的国家不存在 测试结果：user consignee country is not exist
//	 */
//	@Test
//	public void test7() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setUserId(11L);
//		consigneeDto.setCountry(100);
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址国家等级错误 测试结果：user consignee country level error
//	 */
//	@Test
//	public void test8() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/***************************************/
//	/**
//	 * 收货地址的省份为空 测试结果：user consignee province is null
//	 */
//	@Test
//	public void test9() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的省份不存在 测试结果：user consignee province is not exist
//	 */
//	@Test
//	public void test10() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
//		consigneeDto.setUserId(11L);
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址省份等级错误 测试结果：user consignee province level error
//	 */
//	@Test
//	public void test11() {
//		Request request = new BaseRequest();
//
//		ConsigneeDTO consigneeDto = new ConsigneeDTO();
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 收货地址的城市为空 测试结果：user consignee city is null
//	 */
//	@Test
//	public void test12() {
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
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
//		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//}
