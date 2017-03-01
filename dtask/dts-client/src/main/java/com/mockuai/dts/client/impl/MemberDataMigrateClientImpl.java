package com.mockuai.dts.client.impl;

import com.mockuai.dts.client.MemberDataMigrateClient;
import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.MemberDataMigrateDTO;

import javax.annotation.Resource;

/**
 * Created by duke on 15/12/30.
 */
public class MemberDataMigrateClientImpl implements MemberDataMigrateClient {
    @Resource
    private DtsService dtsService;

    public Response<Boolean> dataMigrate(MemberDataMigrateDTO memberDataMigrateDTO, String appKey) {
        Request request = new DtsRequest();
        request.setParam("memberDataMigrateDTO", memberDataMigrateDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.MEMBER_DATA_MIGRATE_TASK.getActionName());
        Response<Boolean> response = dtsService.execute(request);
        return response;
    }
}
