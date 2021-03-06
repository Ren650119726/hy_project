package com.mockuai.usercenter.core.service.action.userauth;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeliming on 16/5/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserAuthInfoActionTest {
    private String appKey = "4f5508d72d9d78c9242bf1c867ac1063";

    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void queryUserAuthInfoActionTest() {
        List<Long> userIdList = new ArrayList<Long>();
        userIdList.add(38780L);
        userIdList.add(38781L);
        UserAuthInfoQTO userAuthInfoQTO = new UserAuthInfoQTO();

        userAuthInfoQTO.setUserIdList(userIdList);

        Request request = new BaseRequest();
        request.setParam("userAuthInfoQTO",userAuthInfoQTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.QUERY_USER_AUTH_INFO.getActionName());
        Response<List<UserOpenInfoDTO>> response = this.userDispatchService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));
    }
}