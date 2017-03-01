package com.mockuai.usercenter.core.service.action.consignee;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.qto.UserConsigneeQTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/7/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class QueryAllConsigneeActionTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void test1() {
        UserConsigneeQTO userConsigneeQTO = new UserConsigneeQTO();
        userConsigneeQTO.setCount(20);
        userConsigneeQTO.setOffset(0L);
        Request request = new BaseRequest();
        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
        request.setParam("userConsigneeQTO", userConsigneeQTO);
        request.setCommand(ActionEnum.QUERY_ALL_CONSIGNEE.getActionName());
        Response response = this.userDispatchService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));
    }
}