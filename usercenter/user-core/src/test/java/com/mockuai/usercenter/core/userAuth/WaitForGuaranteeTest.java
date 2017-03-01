package com.mockuai.usercenter.core.userAuth;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.constant.UserAuthStatus;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/1/23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class WaitForGuaranteeTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void test(){
        Request request = new BaseRequest();
        request.setParam("userAuthId",95L);
        request.setParam("userId",38697L);
        request.setParam("guaranteeAmount",100L);
        request.setParam("appKey","3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.WAIT_FOR_GUARANTEE.getActionName());
        Response<Boolean> response = this.userDispatchService.execute(request);
        Assert.assertNotNull(response.getModule());
    }
}
