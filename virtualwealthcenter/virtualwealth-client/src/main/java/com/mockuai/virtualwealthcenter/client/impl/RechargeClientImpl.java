package com.mockuai.virtualwealthcenter.client.impl;

import com.mockuai.virtualwealthcenter.client.RechargeClient;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.dto.RechargeRecordDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.RechargeRecordQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthItemQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 16/4/19.
 */
public class RechargeClientImpl implements RechargeClient {
    @Resource
    private VirtualWealthService virtualWealthService;

    public Response<Boolean> updateVirtualItem(List<VirtualWealthItemDTO> virtualWealthItemDTOs, String appKey) {
        Request request = new BaseRequest();
        request.setParam("virtualWealthItemDTOs", virtualWealthItemDTOs);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_VIRTUAL_WEALTH_ITEM.getActionName());
        return virtualWealthService.execute(request);
    }

    public Response<List<VirtualWealthItemDTO>> queryVirtualItem(VirtualWealthItemQTO virtualWealthItemQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("virtualWealthItemQTO", virtualWealthItemQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_VIRTUAL_WEALTH_ITEM.getActionName());
        return virtualWealthService.execute(request);
    }

    public Response<Boolean> deleteVirtualItem(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_VIRTUAL_WEALTH_ITEM.getActionName());
        return virtualWealthService.execute(request);
    }

    public Response<List<RechargeRecordDTO>> queryRechargeRecord(RechargeRecordQTO rechargeRecordQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("rechargeRecordQTO", rechargeRecordQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_RECHARGE_RECORD.getActionName());
        return virtualWealthService.execute(request);
    }
}