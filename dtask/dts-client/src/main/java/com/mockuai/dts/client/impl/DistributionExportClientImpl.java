package com.mockuai.dts.client.impl;

import com.mockuai.dts.client.DistributionExportClient;
import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.DistributionStatisticsExportQTO;
import com.mockuai.dts.common.domain.DistributionWithdrawRecordExportQTO;

import javax.annotation.Resource;

/**
 * Created by duke on 15/12/26.
 */
public class DistributionExportClientImpl implements DistributionExportClient {
    @Resource
    DtsService dtsService;

    public Response<Boolean> exportStatistics(DistributionStatisticsExportQTO distributionStatisticsExportQTO, String appKey) {
        Request request = new DtsRequest();
        request.setParam("distributionStatisticsExportQTO", distributionStatisticsExportQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DISTRIBUTION_STATISTICS_EXPORT_TASK.getActionName());
        Response<Boolean> response = dtsService.execute(request);
        return response;
    }

    public Response<Boolean> exportWithdrawRecord(DistributionWithdrawRecordExportQTO distributionWithdrawRecordExportQTO, String appKey) {
        Request request = new DtsRequest();
        request.setParam("distributionWithdrawRecordExportQTO", distributionWithdrawRecordExportQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DISTRIBUTION_WITHDRAW_RECORD_EXPROT_TASK.getActionName());
        Response<Boolean> response = dtsService.execute(request);
        return response;
    }
}
