package com.mockuai.distributioncenter.core.service.action.fans_gain;

import com.mockuai.common.uils.StarterRunner;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.FansDistDTO;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.common.domain.qto.FansDistQTO;
import com.mockuai.distributioncenter.core.manager.GainsSetManager;
import com.mockuai.distributioncenter.core.service.action.UnitTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lizg on 2016/8/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FansGainTest {

    private static final String APP_NAME = "distribution";
    private static final String LOCAL_PROPERTIES = "e:/hy_workspace_test/haiyn/haiyn_properties/distribution/haiyn.properties";
//    E:\hy_workspace_test\haiyn\haiyn_properties
    @Autowired
    private DistributionService distributionService;


    @Autowired
    private GainsSetManager gainsSetManager;

    private static final String appKey = "27c7bc87733c6d253458fa8908001eef";

    static {
        try {
            StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES, new String[]{"local"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void FansGainTest111(){
        FansDistQTO fansDistQTO = new FansDistQTO();
        fansDistQTO.setInviterId(1615260L);
        fansDistQTO.setOffset(0L);
        fansDistQTO.setCount(100);
//        fansDistQTO.setInviterId()

        Request request = new BaseRequest();
        request.setParam("fansDistQTO",fansDistQTO);
        request.setCommand(ActionEnum.QUERY_FANS_AND_DIST.getActionName());
        request.setParam("appKey",appKey);

        Response<List<FansDistDTO>> fansDistDTOS = (Response<List<FansDistDTO>>) distributionService.execute(request);
        if (fansDistDTOS.isSuccess()){
            System.out.println("success!!-------------------------------------");
        }
        List<FansDistDTO> fansDistDTOS1 = fansDistDTOS.getModule();


        for (FansDistDTO fansDistDTO : fansDistDTOS1){
            System.out.println(fansDistDTO.toString());
        }

    }

}


