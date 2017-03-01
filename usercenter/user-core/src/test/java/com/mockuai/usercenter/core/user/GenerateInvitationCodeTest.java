package com.mockuai.usercenter.core.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GenerateInvitationCodeTest
{
    @Resource
    private UserDispatchService userDispatchService;

    /**
     * 随机生成邀请码
     * */
    @org.junit.Test
    public void generateInvitationCodeTest()
    {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GENERATE_INVITATION_CODE.getActionName());
        Response response = userDispatchService.execute(request);

        System.out.println("123123213" + response.getModule());
    }
}
