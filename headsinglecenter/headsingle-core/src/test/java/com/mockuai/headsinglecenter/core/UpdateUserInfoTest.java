package com.mockuai.headsinglecenter.core;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.headsinglecenter.common.api.BaseRequest;
import com.mockuai.headsinglecenter.common.api.HeadSingleService;
import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.common.constant.ActionEnum;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleInfoDTO;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleUserDTO;
import com.mockuai.headsinglecenter.core.message.MessageProducer;
import com.mockuai.headsinglecenter.core.message.msg.PaySuccessMsg;
import com.mockuai.headsinglecenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateUserInfoTest {

	@SuppressWarnings("rawtypes")
	@Resource
	private HeadSingleService headSingleService;

	/**
	 * 参数正确的情况下修改密码 测试结果：修改成功
	 * */	
	@Test
	public void UpdateHeadImg() {
			BaseRequest request = new BaseRequest();
			request.setParam("id", 12L);			
			request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
			request.setCommand(ActionEnum.QUERY_HEADSINGLE_SUBBYID.getActionName());
			Response response = headSingleService.execute(request);
			System.out.println(JsonUtil.toJson(response));
	}
	
	
	@Test
	public void UpdateHddeadImg() {
		PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
		paySuccessMsg.setAppKey("e39f32712b4139ee5785f0c40a38031c");
		HeadSingleInfoDTO headSingleInfoDTO = new HeadSingleInfoDTO();
		headSingleInfoDTO.setOrderId(11111L);
		headSingleInfoDTO.setPayTime(new Date());
		headSingleInfoDTO.setTerminalType(1);
		headSingleInfoDTO.setUserId(111111L);
		
		paySuccessMsg.setHeadSingleInfoDTO(headSingleInfoDTO);
		
		BaseRequest request = new BaseRequest();
		request.setParam("paySuccessMsg", paySuccessMsg);			
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.PAY_SUCCESS_LISTENER.getActionName());
		Response response = headSingleService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	
	@Test
	public void UpdateHddeaddddImg() {
		PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
		paySuccessMsg.setAppKey("e39f32712b4139ee5785f0c40a38031c");
		HeadSingleUserDTO headSingleUserDTO = new HeadSingleUserDTO();
		headSingleUserDTO.setUserId(111111L);
		headSingleUserDTO.setOrderCount(1L);
		
		paySuccessMsg.setHeadSingleUserDTO(headSingleUserDTO);
		
		BaseRequest request = new BaseRequest();
		request.setParam("paySuccessMsg", paySuccessMsg);			
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.ORDER_UNPAID_SUCCESS_LISTENER.getActionName());
		Response response = headSingleService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	@Test
	public void UpdateHddeaddddfsddImg() {
		PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
		paySuccessMsg.setAppKey("e39f32712b4139ee5785f0c40a38031c");
		HeadSingleUserDTO headSingleUserDTO = new HeadSingleUserDTO();
		headSingleUserDTO.setUserId(111111L);
		headSingleUserDTO.setOrderCount(1L);
		
		paySuccessMsg.setHeadSingleUserDTO(headSingleUserDTO);
		
		BaseRequest request = new BaseRequest();
		request.setParam("paySuccessMsg", paySuccessMsg);			
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.ORDER_CANCEL_SUCCESS_LISTENER.getActionName());
		Response response = headSingleService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	
	@Resource
    private MessageProducer messageProducer;
    private String topic = "dev-haiyn_item_msg";

    @Test
    public void testDelete(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",124455L);
        jsonObject.put("bizCode","hanshu");
        jsonObject.put("userId",124455L);
        messageProducer.send(topic,"orderUnpaid",jsonObject);
    }
}