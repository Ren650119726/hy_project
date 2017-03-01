package com.mockuai.usercenter.core.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.constant.UserAuthStatus;
import com.mockuai.usercenter.common.constant.UserAuthType;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by duke on 15/10/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AddUserAuthInfoActionTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void addUserAuthInfoAction_success() {
        Request request = new BaseRequest();
        UserAuthInfoDTO userAuthInfoDTO = new UserAuthInfoDTO();

        userAuthInfoDTO.setContactName("测试");
        userAuthInfoDTO.setMobile("12121212123");
        userAuthInfoDTO.setType(UserAuthType.SELLER_AUTH.getValue());
        userAuthInfoDTO.setStatus(UserAuthStatus.AUTH_WAIT.getValue());
        userAuthInfoDTO.setUserId(30002L);
        userAuthInfoDTO.setContactAddress("测试");
        userAuthInfoDTO.setContactMobile("1212121212123");
        userAuthInfoDTO.setContactPosition("测试");
        userAuthInfoDTO.setIdcardFrontImg("img");
        userAuthInfoDTO.setIdcardHoldImg("img");
        userAuthInfoDTO.setIdcardNo("141130199011213877");
        userAuthInfoDTO.setIdcardReverseImg("img");
        userAuthInfoDTO.setRealName("测试");

        request.setParam("userAuthInfoDTO", userAuthInfoDTO);
        request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.ADD_USER_AUTH_INFO.getActionName());
        Response<UserAuthInfoDTO> response = userDispatchService.execute(request);
        Assert.assertTrue(response.getCode() == ResponseCode.REQUEST_SUCCESS.getValue());
        System.out.println(response.getModule());
    }
}
