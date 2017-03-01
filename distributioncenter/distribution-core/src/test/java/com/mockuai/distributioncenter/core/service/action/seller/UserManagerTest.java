package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.core.manager.UserManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by duke on 16/6/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserManagerTest {
    private static final String appKey = "27c7bc87733c6d253458fa8908001eef";

    @Autowired
    private UserManager userManager;

    @Test
    public void test() throws Exception {
        userManager.updateInvitertId(1842001L, 1841256L, appKey);
    }
}
