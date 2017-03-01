package com.mockuai.tradecenter.client.impl;

import com.mockuai.tradecenter.client.OrderConsigneeClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;


import javax.annotation.Resource;

/**
 * Created by lizg on 2016/9/20.
 */
public class OrderConsigneeClientImpl implements OrderConsigneeClient{

    @Resource
    private TradeService tradeService;

    @Override
    public Response<Boolean> updateOrderConsignee(OrderConsigneeDTO orderConsigneeDTO, String appKey) {

        com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
        request.setCommand(ActionEnum.UPDATE_ORDER_CONSIGNEE.getActionName());
        request.setParam("orderConsigneeDTO",orderConsigneeDTO);
        request.setParam("appKey", appKey);
        Response<Boolean> response = tradeService.execute(request);
        return response;
    }

}
