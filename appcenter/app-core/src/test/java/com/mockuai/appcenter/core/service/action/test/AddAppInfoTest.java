package com.mockuai.appcenter.core.service.action.test;

import com.mockuai.appcenter.common.api.AppService;
import com.mockuai.appcenter.common.api.BaseRequest;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.util.JsonUtil;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/8/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddAppInfoTest {
    @Resource
    private AppService appService;

    @Test
    public void test(){
        BaseRequest request = new BaseRequest();
        request.setCommand(ActionEnum.ADD_APP_INFO.getActionName());
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        appInfoDTO.setAppType(AppTypeEnum.APP_IOS.getValue());
        appInfoDTO.setBizCode("yangdongxi");
        appInfoDTO.setAdministrator("test");
        appInfoDTO.setMobile("18888888888");
        appInfoDTO.setEmail("test@mockuai.com");
        appInfoDTO.setAppName("洋东西商城");
        appInfoDTO.setAppVersion("1.0.0");
        appInfoDTO.setAppKey("2c15b1a0d6dd3215ce5e18240bf12acc");
        appInfoDTO.setAppPwd("12345678");
        request.setParam("appInfoDTO", appInfoDTO);
        Response response = appService.execute(request);
        System.out.println("response:"+ JsonUtil.toJson(response));
        Assert.assertEquals(ResponseCode.RESPONSE_SUCCESS.getCode(), response.getCode());
    }
}
