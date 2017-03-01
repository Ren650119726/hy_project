package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.dto.SysConfigDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;

import java.util.Map;


/**
 * Created by lizg on 2016/6/2.
 */
public interface HsOrderManager {

    OrderDTO getOrder(Long orderId, Long userId, String appKey) throws RainbowException;

    /**
     * 推送已支付的订单到管易ERP
     *
     * @param orderDTO
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    boolean deliveryOrderToGyerp(OrderDTO orderDTO) throws RainbowException;

    /**
     * 在未发货之前，取消订单
     *
     * @param orderDTO
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    boolean hsCancelErpOrder(OrderDTO orderDTO, Long itemId) throws RainbowException;

    /**
     * 发货之后退单
     *
     * @param refundOrderItemDTO
     * @param appKey
     * @return
     */
    boolean hsResturnErpStore(RefundOrderItemDTO refundOrderItemDTO, String appKey) throws RainbowException;

    /**
     * 发货,要支持部分发货
     *
     * @param value
     * @param appKey       @return
     */
    boolean deliveryItems(Map<String, Object> value,String appKey) throws RainbowException;


    /**
     * 退款审核
     *
     * @param userId
     * @param itemCode
     * @param orderId
     * @param appKey
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    boolean refundAudit(Long userId, Long itemCode, Long orderId, String appKey) throws RainbowException;

}
