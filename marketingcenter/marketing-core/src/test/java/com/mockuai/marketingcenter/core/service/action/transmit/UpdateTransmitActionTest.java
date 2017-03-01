//package com.mockuai.marketingcenter.core.service.action.transmit;
//
//import com.mockuai.marketingcenter.common.api.BaseRequest;
//import com.mockuai.marketingcenter.common.api.MarketingService;
//import com.mockuai.marketingcenter.common.api.Response;
//import com.mockuai.marketingcenter.common.constant.ActionEnum;
//import com.mockuai.marketingcenter.common.domain.dto.TransmitDTO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//
///**
// * Created by duke on 15/9/23.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class UpdateTransmitActionTest {
//    @Resource
//    private MarketingService marketingService;
//
//    @Test
//    public void test_getTransmitAction() {
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setCommand(ActionEnum.UPDATE_TRANSMIT.getActionName());
//        TransmitDTO transmitDTO = new TransmitDTO();
//        transmitDTO.setId(0L);
//        transmitDTO.setPercent(0.9);
//        transmitDTO.setStatus(1);
//        baseRequest.setParam("transmitDTO", transmitDTO);
//        baseRequest.setParam("appKey", "eb1b83c003bb6f2a938a5815e47e77f7");
//        Response<Boolean> response = marketingService.execute(baseRequest);
//        System.out.println(response.getModule());
//    }
//}
