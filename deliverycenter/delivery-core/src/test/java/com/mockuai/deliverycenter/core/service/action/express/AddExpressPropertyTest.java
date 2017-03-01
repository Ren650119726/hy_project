package com.mockuai.deliverycenter.core.service.action.express;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.ExpressPropertyDTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class AddExpressPropertyTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试新增成功分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建属性
		List expressPropertyDTOList = new ArrayList();
		ExpressPropertyDTO expressPropertyDTO = new ExpressPropertyDTO();
		expressPropertyDTO.setExpressId(57);
		expressPropertyDTO.setPropertyKey("send_name");
		expressPropertyDTO.setPropertyValue("王五");
		expressPropertyDTO.setPropertyName("寄件人姓名");
		ExpressPropertyDTO expressPropertyDTO1 = new ExpressPropertyDTO();
		expressPropertyDTO1.setExpressId(57);
		expressPropertyDTO1.setPropertyKey("send_address");
		expressPropertyDTO1.setPropertyValue("西湖区");
		expressPropertyDTO1.setPropertyName("寄件人地址");
		expressPropertyDTOList.add(expressPropertyDTO);
		expressPropertyDTOList.add(expressPropertyDTO1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyDTOList", expressPropertyDTOList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
	}

	/**
	 * 测试参数为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyDTOList", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试快递ID不存在分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建属性
		List expressPropertyDTOList = new ArrayList();
		ExpressPropertyDTO expressPropertyDTO = new ExpressPropertyDTO();
		expressPropertyDTO.setExpressId(56);
		expressPropertyDTO.setPropertyKey("send_name");
		expressPropertyDTO.setPropertyValue("王五");
		expressPropertyDTO.setPropertyName("寄件人姓名");

		expressPropertyDTOList.add(expressPropertyDTO);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyDTOList", expressPropertyDTOList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试有一个不成功事物回滚分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {
		// 创建属性
		List expressPropertyDTOList = new ArrayList();
		ExpressPropertyDTO expressPropertyDTO = new ExpressPropertyDTO();
		expressPropertyDTO.setExpressId(57);
		expressPropertyDTO.setPropertyKey("send_name");
		expressPropertyDTO.setPropertyValue("王五");
		expressPropertyDTO.setPropertyName("寄件人姓名");
		ExpressPropertyDTO expressPropertyDTO1 = new ExpressPropertyDTO();
		expressPropertyDTO1.setExpressId(88);
		expressPropertyDTO1.setPropertyKey("send_address");
		expressPropertyDTO1.setPropertyValue("西湖区");
		expressPropertyDTO1.setPropertyName("寄件人地址");
		expressPropertyDTOList.add(expressPropertyDTO);
		expressPropertyDTOList.add(expressPropertyDTO1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyDTOList", expressPropertyDTOList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
