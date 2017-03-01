package com.mockuai.dts.client.impl;

import com.mockuai.dts.client.LabelExportClient;
import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.LabelExportQTO;

import javax.annotation.Resource;

/**
 * Created by duke on 15/12/7.
 */
public class LabelExportClientImpl implements LabelExportClient {
    @Resource
    private DtsService dtsService;

    public Response<Boolean> exportLabels(LabelExportQTO labelExportQTO, String appKey) {
        Request request = new DtsRequest();
        request.setParam("labelExportQTO", labelExportQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.LABEL_EXPORT_TASK.getActionName());
        Response<Boolean> response = dtsService.execute(request);
        return response;
    }
}
