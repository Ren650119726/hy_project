package com.mockuai.dts.core;

import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.DistributionWithdrawRecordExportQTO;
import com.mockuai.tradecenter.common.util.DateUtil;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlMimeType;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lotmac on 16/3/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ExportWithdrawRecordActionTest {

    @Resource
    private DtsService dtsService;


    @Test
    public void export(){
        Request request = new DtsRequest();
        DistributionWithdrawRecordExportQTO distributionWithdrawRecordExportQTO = new DistributionWithdrawRecordExportQTO();
        distributionWithdrawRecordExportQTO.setSellerId(3444L);
        distributionWithdrawRecordExportQTO.setBizCode("mockuai_demo");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 月份是以0开始计数的
        calendar.set(Calendar.MONTH, 3 - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startTime = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        Date endTime = calendar.getTime();
        distributionWithdrawRecordExportQTO.setStartTime(startTime);
        distributionWithdrawRecordExportQTO.setEndTime(endTime);
        request.setParam("distributionWithdrawRecordExportQTO", distributionWithdrawRecordExportQTO);
        request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
        request.setCommand(ActionEnum.DISTRIBUTION_WITHDRAW_RECORD_EXPROT_TASK.getActionName());
        Response<Boolean> response = dtsService.execute(request);
        System.out.println("========response response code="+response.getCode());
    }
}
