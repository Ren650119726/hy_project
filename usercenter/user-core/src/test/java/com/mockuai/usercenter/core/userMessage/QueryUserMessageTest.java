package com.mockuai.usercenter.core.userMessage;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.qto.UserMessageQTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryUserMessageTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 获取用户消息列表 测试结果：
	 */
	@Test
	public void test1() {
		Request request = new BaseRequest();

		UserMessageQTO userMessageQto = new UserMessageQTO();
		userMessageQto.setReceiverId(85L);
		//userMessageQto.setPageNum(1L);
		//userMessageQto.setPageSize(3);

		request.setParam("userMessageQTO", userMessageQto);
		request.setCommand(ActionEnum.QUERY_USER_MESSAGE.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

//	/**
//	 * userMessageQto 为空获取用户 测试结果：userMessageQto is null
//	 */
//	@Test
//	public void test2() {
//		Request request = new BaseRequest();
//		UserMessageQTO userMessageQto = null;
//		request.setParam("userMessageQTO", userMessageQto);
//		request.setCommand(ActionEnum.QUERY_USER_MESSAGE.getActionName());
//		Response response = userDispatchService.execute(request);
//		System.out.println(JSON.toJSON(response));
//	}
//
//	/**
//	 * 查询的页数大于最大一页，这默认进入最后一页
//	 */
//	@Test
//	public void test3() {
//		Request request = new BaseRequest();
//		UserMessageQTO userMessageQto = new UserMessageQTO();
//		userMessageQto.setReceiverId(1L);
//		userMessageQto.setPageNum(100L);
//		request.setParam("userMessageQTO", userMessageQto);
//		request.setCommand(ActionEnum.QUERY_USER_MESSAGE.getActionName());
//		Response response = userDispatchService.execute(request);
//		System.out.println(JSON.toJSON(response));
//	}

}
