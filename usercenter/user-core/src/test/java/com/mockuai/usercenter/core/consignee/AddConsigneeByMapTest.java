package com.mockuai.usercenter.core.consignee;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.core.util.JsonUtil;

/**
 * Created by duke on 15/11/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AddConsigneeByMapTest {
    @Resource
    private UserDispatchService userDispatchService;

    @Test
    public void addConsigneeByMap_success() {
        BaseRequest request = new BaseRequest();
        UserConsigneeDTO userConsigneeDTO = new UserConsigneeDTO();
        userConsigneeDTO.setCity("北京市");
        userConsigneeDTO.setProvince("北京市");
        userConsigneeDTO.setArea("西湖区");
        userConsigneeDTO.setUserId(85L);
        userConsigneeDTO.setMobile("12345678901");
        userConsigneeDTO.setAddress("测试地址");
        userConsigneeDTO.setConsignee("收货人");
        userConsigneeDTO.setLatitude("0.01");
        userConsigneeDTO.setLongitude("0.02");

        request.setParam("consigneeDTO", userConsigneeDTO);
        request.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
        request.setCommand(ActionEnum.ADD_CONSIGNEE_BY_MAP.getActionName());
        Response<UserConsigneeDTO> response = userDispatchService.execute(request);
        Assert.assertTrue(response.getCode() == ResponseCode.REQUEST_SUCCESS.getValue());
        System.out.println(JsonUtil.toJson(response.getModule()));
    }

    @Test
    public void updateConsigneeByMap_success() {
        BaseRequest request = new BaseRequest();
        UserConsigneeDTO userConsigneeDTO = new UserConsigneeDTO();
        userConsigneeDTO.setId(174L);
        userConsigneeDTO.setCity("重庆市");
        userConsigneeDTO.setProvince("重庆市");
        userConsigneeDTO.setArea("西湖区");
        userConsigneeDTO.setUserId(85L);
        userConsigneeDTO.setMobile("12345678901");
        userConsigneeDTO.setAddress("测试地址");
        userConsigneeDTO.setConsignee("收货人");
        userConsigneeDTO.setLatitude("0.01");
        userConsigneeDTO.setLongitude("0.02");

        request.setParam("consigneeDTO", userConsigneeDTO);
        request.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
        request.setCommand(ActionEnum.UPDATE_CONSIGNEE_BY_MAP.getActionName());
        Response<UserConsigneeDTO> response = userDispatchService.execute(request);
        Assert.assertTrue(response.getCode() == ResponseCode.REQUEST_SUCCESS.getValue());
        System.out.println(JsonUtil.toJson(response.getModule()));
    }
}
