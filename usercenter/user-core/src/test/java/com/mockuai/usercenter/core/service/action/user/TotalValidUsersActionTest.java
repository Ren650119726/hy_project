package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;


/**
 * Created by yeliming on 16/1/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class TotalValidUsersActionTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void test() {
        Request request = new BaseRequest();
        Date start = new Date(1453179072000L);

        Date end = new Date();

        request.setParam("start",start);
        request.setParam("end",end);
        request.setParam("appKey", "0dcbdaa661f317efd479273b2e46e6aa");
        request.setCommand(ActionEnum.TOTAL_VALID_USERS.getActionName());
        Response response = this.userDispatchService.execute(request);
        Assert.assertNotNull(response);

    }
}