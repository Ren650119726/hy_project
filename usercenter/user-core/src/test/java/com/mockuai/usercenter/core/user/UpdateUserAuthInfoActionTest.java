package com.mockuai.usercenter.core.user;

import com.mockuai.usercenter.common.api.UserDispatchService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by duke on 16/1/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UpdateUserAuthInfoActionTest {

    @Resource
    private UserDispatchService userDispatchService;

    @org.junit.Test
    public void test() {

    }
}
