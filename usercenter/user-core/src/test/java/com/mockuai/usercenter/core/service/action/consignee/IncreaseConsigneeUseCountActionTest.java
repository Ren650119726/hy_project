package com.mockuai.usercenter.core.service.action.consignee;

import com.alibaba.fastjson.JSON;
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

/**
 * Created by yeliming on 16/6/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class IncreaseConsigneeUseCountActionTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void test() {
        Request request = new BaseRequest();
        request.setParam("consigneeId",19313L);
        request.setParam("userId",38699L);
        request.setParam("appKey", "6562b5ddf0aed2aad8fe471ce2a2c8a0");
        request.setCommand(ActionEnum.INCREASE_CONSIGNEE_USE_COUNT.getActionName());
        Response response = this.userDispatchService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));

    }
}