package com.mockuai.deliverycenter.core.service.action.express;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.ExpressRegionDTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class CheckRegionSupportedTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试镇不为空不支持配送分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建一个QTO并赋值
		ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
		expressRegionDTO.setProvince("福建省");
		expressRegionDTO.setCity("福州市");
		expressRegionDTO.setArea("鼓楼区");
		expressRegionDTO.setTown("abc");
		expressRegionDTO.setDetailAddress("温泉");
		expressRegionDTO.setExpressCode("shunfeng");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(false, response.getModule());
	}

	/**
	 * 测试镇不为空支持配送分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		// 创建一个QTO并赋值
		ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
		expressRegionDTO.setProvince("福建省");
		expressRegionDTO.setCity("福州市");
		expressRegionDTO.setArea("鼓楼区");
		expressRegionDTO.setTown("温泉街道");
		expressRegionDTO.setDetailAddress("温泉");
		expressRegionDTO.setExpressCode("shunfeng");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(true, response.getModule());
	}

	/**
	 * 测试镇为空区支持全境配送分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建一个QTO并赋值
		ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
		expressRegionDTO.setProvince("福建省");
		expressRegionDTO.setCity("福州市");
		expressRegionDTO.setArea("鼓楼区");
		// expressRegionDTO.setTown("温泉街道");
		expressRegionDTO.setDetailAddress("温泉");
		expressRegionDTO.setExpressCode("shunfeng");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(true, response.getModule());
	}

	/**
	 * 测试镇为空区不支持全境配送详细地址支持配送分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {
		// 创建一个QTO并赋值
		ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
		expressRegionDTO.setProvince("福建省");
		expressRegionDTO.setCity("福州市");
		expressRegionDTO.setArea("闽清县");
		// expressRegionDTO.setTown("温泉街道");
		expressRegionDTO.setDetailAddress("asdf梅溪dsfa");
		expressRegionDTO.setExpressCode("shunfeng");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(true, response.getModule());
	}

	/**
	 * 测试镇为空区不支持全境配送详细地址也不支持配送分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test5() throws Exception {
		// 创建一个QTO并赋值
		ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
		expressRegionDTO.setProvince("福建省");
		expressRegionDTO.setCity("福州市");
		expressRegionDTO.setArea("闽清县");
		// expressRegionDTO.setTown("温泉街道");
		expressRegionDTO.setDetailAddress("asdf梅冬dsfa");
		expressRegionDTO.setExpressCode("shunfeng");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(false, response.getModule());
	}

	/**
	 * 测试参数为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test6() throws Exception {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试省为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test7() throws Exception {
		// 创建一个QTO并赋值
		ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
		// expressRegionDTO.setProvince("福建省");
		expressRegionDTO.setCity("福州市");
		expressRegionDTO.setArea("闽清县");
		expressRegionDTO.setTown("温泉街道");
		expressRegionDTO.setDetailAddress("asdf梅冬dsfa");
		expressRegionDTO.setExpressCode("shunfeng");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试市为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test8() throws Exception {
		// 创建一个QTO并赋值
		ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
		expressRegionDTO.setProvince("福建省");
		// expressRegionDTO.setCity("福州市");
		expressRegionDTO.setArea("闽清县");
		expressRegionDTO.setTown("温泉街道");
		expressRegionDTO.setDetailAddress("asdf梅冬dsfa");
		expressRegionDTO.setExpressCode("shunfeng");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试区县为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test9() throws Exception {
		// 创建一个QTO并赋值
		ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
		expressRegionDTO.setProvince("福建省");
		expressRegionDTO.setCity("福州市");
		// expressRegionDTO.setArea("闽清县");
		expressRegionDTO.setTown("温泉街道");
		expressRegionDTO.setDetailAddress("asdf梅冬dsfa");
		expressRegionDTO.setExpressCode("shunfeng");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试镇为空详细地址为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test10() throws Exception {
		// 创建一个QTO并赋值
		ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
		expressRegionDTO.setProvince("福建省");
		expressRegionDTO.setCity("福州市");
		expressRegionDTO.setArea("闽清县");
		// expressRegionDTO.setTown("温泉街道");
		// expressRegionDTO.setDetailAddress("asdf梅冬dsfa");
		expressRegionDTO.setExpressCode("shunfeng");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
