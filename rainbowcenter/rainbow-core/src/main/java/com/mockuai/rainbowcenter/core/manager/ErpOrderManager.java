package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

/**
 * Created by lizg on 2016/6/15.
 */
public interface ErpOrderManager {

    /**
     * 增加管易erp订单
     * @param erpOrderDTO
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public ErpOrderDTO addErpOrder(ErpOrderDTO erpOrderDTO) throws RainbowException;

    /**
     * 根据订单id查询出单据编号
     * @param orderId
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public ErpOrderDTO getGyerpCode(String orderId) throws RainbowException;
}
