package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.tradecenter.common.domain.OrderDTO;

/**
 * Created by lizg on 2016/7/16.
 * 对支付方式代码进行重构
 */
public interface CommonManage {


    public String paymentType(OrderDTO orderDTO);
}
