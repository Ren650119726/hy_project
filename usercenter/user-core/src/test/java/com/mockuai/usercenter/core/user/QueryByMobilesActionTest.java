package com.mockuai.usercenter.core.user;

import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.service.UserRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 15/12/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class QueryByMobilesActionTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void test() {
        Request request = new BaseRequest();
        List<String> mobiles = new ArrayList<String>();
        mobiles.add("15869179713");
        mobiles.add("18268836320");
        request.setParam("mobiles", mobiles);
        request.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
        request.setCommand(ActionEnum.QUERY_USER_BY_MOBILES.getActionName());
        UserResponse<List<UserDTO>> response = userDispatchService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JsonUtil.toJson(response.getModule()));
    }

}
