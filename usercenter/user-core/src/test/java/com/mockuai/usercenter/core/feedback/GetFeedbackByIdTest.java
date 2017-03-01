package com.mockuai.usercenter.core.feedback;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by duanyytop on 15/5/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetFeedbackByIdTest {

    @Resource
    private UserDispatchService userDispatchService;

    /**
     * 参数正确的情况下添加反馈意见 测试结果:成功添加
     * */
    @Test
    public void getByIdTest() {

        Request request = new BaseRequest();

        request.setParam("id", 14L);
        request.setCommand(ActionEnum.GET_FEEDBACK.getActionName());

        Response response = userDispatchService.execute(request);
        System.out.println(JSON.toJSON(response));

    }


    /**
     * 参数正确的情况下添加反馈意见 测试结果:成功添加
     * */
    @Test
    public void getByDateTest() {

        Request request = new BaseRequest();

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-2);//让日期-1

//        request.setParam("startDate", calendar.getTime());
//        request.setParam("endDate", new Date());
//        request.setParam("offset", 5L);
//        request.setParam("count", 5);
        request.setCommand(ActionEnum.QUERY_USER_FEEDBACK.getActionName());

        Response response = userDispatchService.execute(request);
        System.out.println(JSON.toJSON(response));

    }

}
