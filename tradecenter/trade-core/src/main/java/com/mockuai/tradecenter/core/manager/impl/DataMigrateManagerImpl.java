package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.dao.DataMigrateDAO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DataMigrateManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/16/15.
 */
@Component
public class DataMigrateManagerImpl implements DataMigrateManager {

    @Resource
    private DataMigrateDAO dataMigrateDAO;

    public Long insertOrder(OrderDO orderDO) throws TradeException {
        try{
            long id = dataMigrateDAO.insertOrder(orderDO);
            return id;
        }catch(Exception e){
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR);
        }
    }

    public Long insertOrderConsignee(OrderConsigneeDO orderConsigneeDO) throws TradeException {
        try{
            long id = dataMigrateDAO.insertOrderConsignee(orderConsigneeDO);
            return id;
        }catch(Exception e){
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR);
        }
    }

    public Long insertOrderPayment(OrderPaymentDO orderPaymentDO) throws TradeException {
        try{
            long id = dataMigrateDAO.insertOrderPayment(orderPaymentDO);
            return id;
        }catch(Exception e){
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR);
        }
    }

    public Long insertOrderItem(OrderItemDO orderItemDO) throws TradeException {
        try{
            long id = dataMigrateDAO.insertOrderItem(orderItemDO);
            return id;
        }catch(Exception e){
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR);
        }
    }
}
