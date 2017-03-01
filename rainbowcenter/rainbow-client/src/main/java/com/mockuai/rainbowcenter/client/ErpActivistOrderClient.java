package com.mockuai.rainbowcenter.client;


import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;

/**
 * Created by lizg on 2016/7/16.
 */
public interface ErpActivistOrderClient {

    /**
     * 获取erp退款订单信息
     * @param orderSn
     * @param appKey
     * @return
     */
    Response<ErpOrderDTO> getRefundOrderInfo(String orderSn, String appKey);


    /**
     * 获取erp退货订单信息
     * @param orderSn
     * @param appKey
     * @return
     */
    Response<ErpOrderDTO> getReturnOrderInfo(String orderSn, String appKey);
}
