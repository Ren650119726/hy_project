package com.mockuai.rainbowcenter.core.manager.impl;

import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.ActivistOrderManager;
import com.mockuai.rainbowcenter.core.manager.GyErpManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/7/16.
 */

@Service
public class ActivistOrderManagerImpl implements ActivistOrderManager {

    private static final Logger log = LoggerFactory.getLogger(ActivistOrderManagerImpl.class);

    @Resource
    private GyErpManage gyErpManage;

    @Override
    public ErpOrderDTO getRefundOrderInfo(String orderSn) throws RainbowException {

        ErpOrderDTO erpOrderDTO  = new ErpOrderDTO();

        //查询订单是否已审核
        boolean status = gyErpManage.getOrder(orderSn);
        log.info("[{}] status:{}",status);
        if (status) {

            //查询订单发货状态
            Integer deliveryStatus = gyErpManage.getDeliverys(orderSn);
            log.info("[{}] delivery status:{}",deliveryStatus);
            if (null != deliveryStatus) {
                erpOrderDTO.setOrderSn(orderSn);
                erpOrderDTO.setDeliveryStatus(deliveryStatus);
            }

        }
        return erpOrderDTO;
    }

    @Override
    public ErpOrderDTO getReturnOrderInfo(String orderSn) throws RainbowException {
        ErpOrderDTO erpOrderDTO  = new ErpOrderDTO();

        //查询订单是否已审核
        boolean status = gyErpManage.getOrder(orderSn);
        log.info("[{}] status:{}",status);
        if (!status) {
            erpOrderDTO.setAuditStatus(0);
            erpOrderDTO.setOrderSn(orderSn);
            return erpOrderDTO;
        }else {
           //查询发货成功的订单是否入库
            boolean storageStatus =  gyErpManage.getTradeReturn(orderSn);
            if (storageStatus) {
                erpOrderDTO.setStorageStatus(0);
                erpOrderDTO.setOrderSn(orderSn);
            }
            return erpOrderDTO;
        }

    }
}
