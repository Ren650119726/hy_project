package com.mockuai.usercenter.core.useropeninfo;

import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by duke on 15/12/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class GetOpenInfoByUserIdActionTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void test() {
        Request request = new BaseRequest();
        request.setParam("userId", 38780L);
        request.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
        request.setCommand(ActionEnum.GET_USER_OPEN_INFO_BY_USER_ID.getActionName());
        Response<UserOpenInfoDTO> response = userDispatchService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JsonUtil.toJson(response.getModule()));
    }
}
