package com.mockuai.usercenter.core.feedback;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by duanyytop on 15/5/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddFeedbackTest {

    @Resource
    private UserDispatchService userDispatchService;

    /**
     * 参数正确的情况下添加反馈意见 测试结果:成功添加
     * */
    @Test
    public void addTest() {

        Request request = new BaseRequest();

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setUserId(20L);
        feedbackDTO.setContent("软件有bug");
        feedbackDTO.setMobile("18667045290");

        request.setCommand(ActionEnum.ADD_FEEDBACK.getActionName());
        request.setParam("feedbackDTO", feedbackDTO);

        Response response = userDispatchService.execute(request);
//        Assert.assertNotNull(response.getModule());
        System.out.println(JSON.toJSON(response));
    }


    /**
     * 缺少内容的情况下添加反馈意见 测试结果:成功添加
     * */
    @Test
    public void addTest1() {

        Request request = new BaseRequest();

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setUserId(20L);
        feedbackDTO.setMobile("18667045290");

        request.setCommand(ActionEnum.ADD_FEEDBACK.getActionName());
        request.setParam("feedbackDTO", feedbackDTO);

        Response response = userDispatchService.execute(request);
//        Assert.assertNotNull(response.getModule());
        System.out.println(JSON.toJSON(response));

    }

}
