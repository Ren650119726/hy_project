package com.mockuai.distributioncenter.core.service.action.gains;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.core.manager.GainsSetManager;
import com.mockuai.distributioncenter.core.service.action.UnitTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lizg on 2016/8/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GainsSetTest {

    @Autowired
    private DistributionService distributionService;


    @Autowired
    private GainsSetManager gainsSetManager;

    private static final String appKey = "6562b5ddf0aed2aad8fe471ce2a2c8a0";

    @Test
    public void testAddGainsSet() throws Exception {
        GainsSetDTO gainsSetDTO = new GainsSetDTO();
        gainsSetDTO.setSelfGains(40);
        gainsSetDTO.setOneGains(10);
        gainsSetDTO.setTwoGains(15);
        gainsSetDTO.setSelfHib(25);
        gainsSetDTO.setOneHib(20);
        gainsSetDTO.setTwoHib(80);
      //  gainsSetDTO.setConsumerEnjoy(1);
        gainsSetDTO.setOnOff(1);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("gainsSetDTO", gainsSetDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.ADD_GAINS_SET.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
     public void testAdd ()throws Exception {
        GainsSetDTO gainsSetDTO = new GainsSetDTO();
        gainsSetDTO.setSelfGains(20);
        gainsSetDTO.setOneGains(10);
        gainsSetDTO.setTwoGains(15);
        gainsSetDTO.setSelfHib(25);
        gainsSetDTO.setOneHib(1);
        gainsSetDTO.setTwoHib(5);
        gainsSetDTO.setOnOff(0);
       Long id=  gainsSetManager.add(gainsSetDTO);
        System.out.print(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+id);
    }

    @Test
    public void testGet()throws Exception {
        GainsSetDTO id=  gainsSetManager.get();
        System.out.print(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+id);
    }

    @Test
    public void testUpdate ()throws Exception {
        GainsSetDTO gainsSetDTO = new GainsSetDTO();
        gainsSetDTO.setSelfGains(20);
        gainsSetDTO.setOneGains(10);
        gainsSetDTO.setTwoGains(15);
        gainsSetDTO.setSelfHib(25);
        gainsSetDTO.setOneHib(1);
        gainsSetDTO.setTwoHib(5);
        gainsSetDTO.setOnOff(1);
        Integer id=  gainsSetManager.update(gainsSetDTO);
        System.out.print(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+id);
    }

}


