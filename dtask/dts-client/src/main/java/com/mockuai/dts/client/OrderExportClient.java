package com.mockuai.dts.client;

import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.domain.OrderExportQTO;

public interface OrderExportClient {

    public Response<Boolean> exportOrders(OrderExportQTO query,String appkey);

}
