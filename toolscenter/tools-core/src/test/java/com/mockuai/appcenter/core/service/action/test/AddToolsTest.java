package com.mockuai.appcenter.core.service.action.test;

import com.mockuai.toolscenter.common.api.BaseRequest;
import com.mockuai.toolscenter.common.api.Request;
import com.mockuai.toolscenter.common.api.Response;
import com.mockuai.toolscenter.common.api.ToolsService;
import com.mockuai.toolscenter.common.constant.ActionEnum;
import com.mockuai.toolscenter.common.domain.UserBehaviourDTO;
import com.mockuai.toolscenter.core.dao.UserBehaviourDAO;
import com.mockuai.toolscenter.core.domain.UserBehaviourDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * Created by zengzhangqiang on 6/8/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddToolsTest {
    @Resource
    private UserBehaviourDAO userBehaviourDAO;
    @Resource
    private ToolsService toolsService;

    private String appKey = "6562b5ddf0aed2aad8fe471ce2a2c8a0";

    @Test
    public void test1(){
        UserBehaviourDTO userBehaviourDO = new UserBehaviourDTO();
        userBehaviourDO.setAppKey("123");
        userBehaviourDO.setParams("fwowo");
        userBehaviourDO.setClientType(1);
        userBehaviourDO.setUserId(1253L);
        userBehaviourDO.setDeviceId("1oafwe");
        userBehaviourDO.setIp("192.168.8.111");
        userBehaviourDO.setSessionId("1234fwefwefqqq");
        userBehaviourDO.setSessionToken("wlwefivfsqaaa");
        userBehaviourDO.setUrl("/user/login.do");
        userBehaviourDO.setParams("username=124&pwd=2");
        userBehaviourDO.setVersion("1.2.3b");
        Request baseRequest = new BaseRequest();
        baseRequest.setParam("userBehaviourDTO",userBehaviourDO);
        baseRequest.setParam("appKey",appKey);
        baseRequest.setCommand(ActionEnum.ADD_USER_BEHAVIOUR.getActionName());
        Response response =  toolsService.execute(baseRequest);
        System.out.println(" 塞入一个 test response:"+response.isSuccess());
    }

    @Test
    public void test() throws SQLException {
        UserBehaviourDO userBehaviourDO = new UserBehaviourDO();
        userBehaviourDO.setAppKey("123");
        userBehaviourDO.setParams("fwowo");
        userBehaviourDO.setClientType(1);
        userBehaviourDO.setUserId(1253L);
        userBehaviourDO.setDeviceId("1oafwe");
        userBehaviourDO.setIp("192.168.8.111");
        userBehaviourDO.setSessionId("1234fwefwefqqq");
        userBehaviourDO.setSessionToken("wlwefivfsqaaa");
        userBehaviourDO.setUrl("/user/login.do");
        userBehaviourDO.setParams("username=124&pwd=2");
        userBehaviourDO.setVersion("1.2.3b");
        userBehaviourDAO.addBeHaviour(userBehaviourDO);
    }
}
