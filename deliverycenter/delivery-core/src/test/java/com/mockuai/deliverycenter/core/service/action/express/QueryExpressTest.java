package com.mockuai.deliverycenter.core.service.action.express;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.dto.express.ExpressDTO;
import com.mockuai.deliverycenter.common.qto.express.ExpressQTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

import junit.framework.Assert;

public class QueryExpressTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试查询有数据分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建一个QTO并赋值
		ExpressQTO expressQTO = new ExpressQTO();
		expressQTO.setPageNo(1);
		expressQTO.setPageSize(50);
//		expressQTO.setCode("shunfeng");
//		expressQTO.setName("顺风快递");
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_EXPRESS.getActionName());
		request.setParam("expressQTO", expressQTO);
		// 执行分发执行对应Action
		Response<List> response = deliveryService.execute(request);
		
		List<ExpressDTO> list = response.getModule();
		
		for(ExpressDTO dto:list){
			System.out.println("id"+dto.getId());
		}
//		Assert.assertEquals(true, response.isSuccess());
//		Assert.assertTrue(response.getModule().size() > 0);
	}

	/**
	 * 测试查询有数据分支
	 * 
	 * @throws Exception
	 */
//	@Test
	public void test2() throws Exception {
		// 创建一个QTO并赋值
		ExpressQTO expressQTO = new ExpressQTO();
		expressQTO.setPageNo(1);
		expressQTO.setPageSize(50);
		expressQTO.setCode("abc");
		expressQTO.setName("顺风快递");
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_EXPRESS.getActionName());
		request.setParam("expressQTO", expressQTO);
		// 执行分发执行对应Action
		Response<List> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule().size() == 0);
	}

	/**
	 * 测试参数为空分支
	 * 
	 * @throws Exception
	 */
//	@Test
	public void test3() throws Exception {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_EXPRESS.getActionName());
		request.setParam("expressQTO", null);
		// 执行分发执行对应Action
		Response<List> response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
