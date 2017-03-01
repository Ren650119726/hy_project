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
import com.mockuai.deliverycenter.core.service.BaseTest;

public class DeleteExpressPropertyTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试删除成功分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建属性
		List expressPropertyIdList = new ArrayList();
		expressPropertyIdList.add(31);
		expressPropertyIdList.add(32);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyIdList", expressPropertyIdList);
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
		request.setCommand(ActionEnum.DELETE_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyIdList", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试ID不存在删除失败分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建属性
		List expressPropertyIdList = new ArrayList();
		expressPropertyIdList.add(30);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyIdList", expressPropertyIdList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试有一个失败事物回滚分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {
		// 创建属性
		List expressPropertyIdList = new ArrayList();
		expressPropertyIdList.add(34);
		expressPropertyIdList.add(39);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyIdList", expressPropertyIdList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
