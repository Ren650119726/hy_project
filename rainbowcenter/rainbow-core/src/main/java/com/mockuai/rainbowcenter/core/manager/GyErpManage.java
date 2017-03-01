package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.rainbowcenter.common.dto.ErpStockDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;

/**
 * Created by lizg on 2016/6/2.
 * 管易erp接口
 */
public interface GyErpManage {

    /**
     * 订单查询(gy.erp.trade.get)
     * @param orderSn
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    boolean getOrder(String orderSn) throws RainbowException;

    /**
     *
     * 退款单新增(gy.erp.trade.refund.add)
     * @param orderDTO
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    String addRefund(OrderDTO orderDTO) throws RainbowException;

    /**
     * 退货单新增(gy.erp.trade.return.add)
     * @param appKey
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    String addReturn(RefundOrderItemDTO refundOrderItemDTO, ItemSkuDTO itemSkuDTO, Long storeId, String appKey) throws RainbowException;

    /**
     * 根据订单编号查询出发货状态
     * @param orderSn
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    Integer getDeliverys(String orderSn) throws RainbowException;

    /**
     * 根据订单编号查询发货成功的订单是否入库
     * @param orderSn
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    boolean getTradeReturn(String orderSn) throws RainbowException;

    /**
     * 根据商品编码和仓库id获取erp的可用库存
     * @param skuItemSn
     * @param storeId
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
     ErpStockDTO getStock(String skuItemSn, Long storeId) throws RainbowException;


}
