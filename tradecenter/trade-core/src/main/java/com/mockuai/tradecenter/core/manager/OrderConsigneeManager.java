package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * Created by zengzhangqiang on 5/23/15.
 */
public interface OrderConsigneeManager {

    /**
     *
     * @param orderConsigneeDO
     * @return
     */
    public Long addOrderConsignee(OrderConsigneeDO orderConsigneeDO) throws TradeException;

    /**
     *
     * @param orderId
     * @param userId
     * @return
     */
    public OrderConsigneeDO getOrderConsignee(Long orderId, Long userId) throws TradeException;

    /**
     * 更新地址信息
     * @param consignee
     * @param mobile
     * @param idCardNo
     * @param provinceCode
     * @param cityCode
     * @param areaCode
     * @param address
     * @return
     * @throws TradeException
     */
    public  int updateOrderConsignee(OrderConsigneeDTO orderConsigneeDTO) throws TradeException;
}
