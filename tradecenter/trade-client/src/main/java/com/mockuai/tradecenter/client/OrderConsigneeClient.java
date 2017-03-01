package com.mockuai.tradecenter.client;

import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;

/**
 * Created by lizg on 2016/9/20.
 */
public interface OrderConsigneeClient {

    Response<Boolean> updateOrderConsignee (OrderConsigneeDTO orderConsigneeDTO,String appKey);
}
