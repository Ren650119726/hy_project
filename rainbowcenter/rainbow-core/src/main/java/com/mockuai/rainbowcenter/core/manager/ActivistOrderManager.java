package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

/**
 * Created by lizg on 2016/7/16.
 */
public interface ActivistOrderManager {

    /**
     * 获取erp退款订单状态
     * @param orderSn
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public ErpOrderDTO getRefundOrderInfo(String orderSn) throws RainbowException;


    /**
     * 获取erp退货订单状态
     * @param orderSn
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public ErpOrderDTO getReturnOrderInfo(String orderSn) throws RainbowException;
}
