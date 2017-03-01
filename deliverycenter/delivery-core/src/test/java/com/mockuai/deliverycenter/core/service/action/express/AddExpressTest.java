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
import com.mockuai.deliverycenter.common.dto.express.ExpressDTO;
import com.mockuai.deliverycenter.common.dto.express.ExpressPropertyDTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class AddExpressTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试新增成功分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建一个DTO并赋值
		ExpressDTO expressDTO = new ExpressDTO();
		expressDTO.setClassName("Sf");
		expressDTO.setCode("shunfeng");
		expressDTO.setName("顺风快递");
		expressDTO.setPrintTemplate("顺风模板");
		// 创建属性
		List propertyList = new ArrayList();
		ExpressPropertyDTO expressPropertyDTO = new ExpressPropertyDTO();
		expressPropertyDTO.setPropertyKey("send_name");
		expressPropertyDTO.setPropertyValue("王五");
		expressPropertyDTO.setPropertyName("寄件人姓名");
		ExpressPropertyDTO expressPropertyDTO1 = new ExpressPropertyDTO();
		expressPropertyDTO1.setPropertyKey("send_address");
		expressPropertyDTO1.setPropertyValue("西湖区");
		expressPropertyDTO1.setPropertyName("寄件人地址");
		propertyList.add(expressPropertyDTO);
		propertyList.add(expressPropertyDTO1);
		expressDTO.setExpressPropertyDTOList(propertyList);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_EXPRESS.getActionName());
		request.setParam("expressDTO", expressDTO);
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
		request.setCommand(ActionEnum.ADD_EXPRESS.getActionName());
		request.setParam("expressDTO", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试事物回滚分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建一个DTO并赋值
		ExpressDTO expressDTO = new ExpressDTO();
		expressDTO.setClassName("Sf");
		expressDTO.setCode("shunfeng");
		expressDTO.setName("顺风快递");
		expressDTO.setPrintTemplate("顺风模板");
		// 创建属性
		List propertyList = new ArrayList();
		ExpressPropertyDTO expressPropertyDTO = new ExpressPropertyDTO();
		// expressPropertyDTO.setPropertyKey("send_name");
		expressPropertyDTO.setPropertyValue("王五");
		expressPropertyDTO.setPropertyName("寄件人姓名");
		propertyList.add(expressPropertyDTO);
		expressDTO.setExpressPropertyDTOList(propertyList);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_EXPRESS.getActionName());
		request.setParam("expressDTO", expressDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
