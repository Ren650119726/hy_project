package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.core.manager.UserConsigneeManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by yeliming on 16/5/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserConsigneeManagerImplTest {
    @Resource
    private UserConsigneeManager userConsigneeManager;
    @Test
    public void increaseConsigneeUseCount() throws Exception {
        Boolean b = userConsigneeManager.increaseConsigneeUseCount(19307L,1841256L,"mockuai_demo");
        Assert.assertTrue(b);
    }

}