package com.mockuai.usercenter.core.userAuth;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/8/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryUserAuthTest {
    @Resource
    private UserDispatchService userDispatchService;

    /**
     * 查询用户的认证信息
     * */
    @Test
    public void queryAuthInfo() {
        UserAuthInfoQTO qto = new UserAuthInfoQTO();
        qto.setStartTime("2015-08-11");
        qto.setEndTime("2015-08-12");
        // qto.setRealName("11111");
        // qto.setStatus(1);
        // qto.setType(2);
        Request request = new BaseRequest();
        request.setParam("userAuthInfoQTO", qto);
        request.setParam("appKey", "eb1b83c003bb6f2a938a5815e47e77f7");
        request.setCommand(ActionEnum.QUERY_USER_AUTH_INFO.getActionName());
        Response<List<UserAuthInfoDTO>> response = userDispatchService.execute(request);
        System.out.println(response.getTotalCount());
    }

    @Test
    public void test1(){
        Request request = new BaseRequest();
        UserAuthInfoQTO qto = new UserAuthInfoQTO();
        qto.setStatus(4);
        request.setParam("userAuthInfoQTO",qto);
        request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.QUERY_USER_AUTH_INFO.getActionName());
        Response<List<UserAuthInfoDTO>> response = userDispatchService.execute(request);
        System.out.println(response.getModule());



    }
}
