package com.mockuai.dts.core;

import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.domain.LabelExportQTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by duke on 15/12/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class LabelExportActionTest {
    @Resource
    private DtsService dtsService;

    @Test
    public void test() {
        Request request = new DtsRequest();
        LabelExportQTO labelExportQTO = new LabelExportQTO();
        request.setParam("labelExportQTO", labelExportQTO);
        request.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
        request.setCommand(ActionEnum.LABEL_EXPORT_TASK.getActionName());
        Response<Boolean> response = dtsService.execute(request);
        Assert.assertTrue(response.getCode() == ResponseCode.SUCCESS.getCode());
    }
}
