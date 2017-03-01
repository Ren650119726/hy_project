package com.mockuai.usercenter.core.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by duke on 15/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class QueryUserFromIdListActionTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void test() {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("idList", Arrays.asList());
        baseRequest.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
        baseRequest.setCommand(ActionEnum.QUERY_USER_FROM_ID_LIST.getActionName());

        Response<List<UserDTO>> response = userDispatchService.execute(baseRequest);
        Assert.assertTrue(response.getCode() == ResponseCode.REQUEST_SUCCESS.getValue());
        System.out.println(JsonUtil.toJson(response.getModule()));
    }
}
