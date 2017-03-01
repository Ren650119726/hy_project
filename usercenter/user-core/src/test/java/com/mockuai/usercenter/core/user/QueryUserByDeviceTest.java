//package com.mockuai.usercenter.core.user;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.BaseRequest;
//import com.mockuai.usercenter.common.api.Request;
//import com.mockuai.usercenter.common.api.Response;
//import com.mockuai.usercenter.common.api.UserDispatchService;
//import com.mockuai.usercenter.common.dto.UserAccountDTO;
//import com.mockuai.usercenter.common.qto.UserExtraInfoQTO;
//import com.mockuai.usercenter.common.qto.UserQTO;
//import org.junit.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by duke on 15/8/13.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class QueryUserByDeviceTest {
//
//    @Resource
//    UserDispatchService userDispatchService;
//
//    @Test
//    public void queryUserByDeviceTest() throws Exception {
//        Request request = new BaseRequest();
//        request.setParam("appType", 3);
//
//        UserExtraInfoQTO extraInfoQTO = new UserExtraInfoQTO();
//
//        //extraInfoQTO.setStartTime((new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-13")));
//        //extraInfoQTO.setEndTime((new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-15")));
//        request.setParam("userExtraInfoQTO", extraInfoQTO);
//        request.setParam("appKey", "eb1b83c003bb6f2a938a5815e47e77f7");
//
//        request.setCommand(ActionEnum.QUERY_USER_BY_DEVICE.getActionName());
//        Response<List<UserAccountDTO>> response = userDispatchService.execute(request);
//        Assert.assertNotNull(response.getModule());
//    }
//}
