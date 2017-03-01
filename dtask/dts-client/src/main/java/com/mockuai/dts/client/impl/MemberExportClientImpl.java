package com.mockuai.dts.client.impl;

import com.mockuai.dts.client.MemberExportClient;
import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.MemberExportQTO;

import javax.annotation.Resource;

/**
 * Created by duke on 15/12/7.
 */
public class MemberExportClientImpl implements MemberExportClient {
    @Resource
    private DtsService dtsService;

    public Response<Boolean> exportMembers(MemberExportQTO memberExportQTO, String appKey) {
        Request request = new DtsRequest();
        request.setParam("memberExportQTO", memberExportQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.MEMBER_EXPORT_TASK.getActionName());
        Response<Boolean> response = dtsService.execute(request);
        return response;
    }
}
