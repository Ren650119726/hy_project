package com.mockuai.usercenter.core.service.action.userguarantee;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserGuaranteeDTO;
import com.mockuai.usercenter.common.qto.UserGuaranteeQTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/1/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class UserGuaranteeTest {

    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void addUserGuaranteeActionTest() {
        Request request = new BaseRequest();
        UserGuaranteeDTO userGuaranteeDTO = new UserGuaranteeDTO();
        userGuaranteeDTO.setGuaranteeAmount(100L);
        userGuaranteeDTO.setUserId(38842L);
        request.setParam("userGuaranteeDTO",userGuaranteeDTO);
        request.setParam("appKey","3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.ADD_USER_GUARANTEE.getActionName());
        UserResponse<UserGuaranteeDTO> response = this.userDispatchService.execute(request);
        Assert.assertNotNull(response.getModule());
    }

    @Test
    public void deleteUserGuaranteeActionTest() {
        Request request = new BaseRequest();
        request.setParam("id",1L);
        request.setParam("appKey","3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.DELETE_USER_GUARANTEE.getActionName());
        UserResponse<Boolean> response = this.userDispatchService.execute(request);
        Assert.assertNotNull(response.getModule());
    }

    @Test
    public void getUserGuaranteeByIdActionTest() {
        Request request = new BaseRequest();
        request.setParam("id",1L);
        request.setParam("appKey","3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.GET_USER_GUARANTEE_BY_ID.getActionName());
        UserResponse<UserGuaranteeDTO> response = this.userDispatchService.execute(request);
        Assert.assertNotNull(response.getModule());

    }

    @Test
    public void queryUserGuaranteeActionTest() {
        Request request = new BaseRequest();
        UserGuaranteeQTO userGuaranteeQTO = new UserGuaranteeQTO();

        request.setParam("userGuaranteeQTO",userGuaranteeQTO);
        request.setParam("appKey","3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.QUERY_USER_GUARANTEE.getActionName());
        UserResponse<List<UserGuaranteeDTO>> response = this.userDispatchService.execute(request);
        Assert.assertNotNull(response.getModule());
    }

    @Test
    public void UpdateUserGuaranteeActionTest() {
        Request request = new BaseRequest();
        UserGuaranteeDTO userGuaranteeDTO = new UserGuaranteeDTO();
        userGuaranteeDTO.setId(1l);
        userGuaranteeDTO.setUserId(38842L);
        userGuaranteeDTO.setGuaranteeAmount(200L);
        request.setParam("userGuaranteeDTO",userGuaranteeDTO);
        request.setParam("appKey","3bc25302234640259fadea047cb7c7d3");
        request.setCommand(ActionEnum.UPDATE_USER_GUARANTEE.getActionName());
        UserResponse<Boolean> response = this.userDispatchService.execute(request);
        Assert.assertNotNull(response.getModule());

    }
}
