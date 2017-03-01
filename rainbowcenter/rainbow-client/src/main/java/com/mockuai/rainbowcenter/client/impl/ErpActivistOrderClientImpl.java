package com.mockuai.rainbowcenter.client.impl;

import com.mockuai.rainbowcenter.client.ErpActivistOrderClient;
import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.RainbowService;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/7/16.
 */
public class ErpActivistOrderClientImpl implements ErpActivistOrderClient{

    @Resource
    private RainbowService rainbowService;

    @Override
    public Response<ErpOrderDTO> getRefundOrderInfo(String orderSn, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderSn", orderSn);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_REFUND_ORDER_INFO.getActionName());
        return this.rainbowService.execute(request);
    }

    @Override
    public Response<ErpOrderDTO> getReturnOrderInfo(String orderSn, String appKey) {
        Request request = new BaseRequest();
        request.setParam("orderSn", orderSn);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_RETURN_ORDER_INFO.getActionName());
        return this.rainbowService.execute(request);
    }
}
