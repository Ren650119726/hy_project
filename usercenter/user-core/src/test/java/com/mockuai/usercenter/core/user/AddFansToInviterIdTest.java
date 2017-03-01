package com.mockuai.usercenter.core.user;

import com.mockuai.common.uils.StarterRunner;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddFansToInviterIdTest {
    private final static String APP_KEY = "27c7bc87733c6d253458fa8908001eef";
    private static final String APP_NAME = "distribution";
    private static final String LOCAL_PROPERTIES = "e:/hy_workspace_test/haiyn/haiyn_properties/user/haiyn.properties";


    static {
        try {
            StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES, new String[]{"local"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Resource
	private UserDispatchService userDispatchService;

	@Test
	public void TestAddFansToInviterId(){
	    Long userId = 1761536L;
	    Long inviterId = 1761536L;
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_FANS_TO_INVITER_ID.getActionName());
		request.setParam("appKey",APP_KEY);
		request.setParam("userId",userId);
		request.setParam("inviterId",inviterId);
		Response<Void> response = userDispatchService.execute(request);
		if (response.isSuccess()){
            System.out.println("------------------------------------------------SUCCESS--------------------------------------------");
        }
	}
}
