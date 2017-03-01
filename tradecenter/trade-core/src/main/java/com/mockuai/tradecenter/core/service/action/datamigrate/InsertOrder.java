package com.mockuai.tradecenter.core.service.action.datamigrate;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderPaymentDTO;
import com.mockuai.tradecenter.common.domain.datamigrate.MigrateOrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.datamigrate.MigrateOrderDTO;
import com.mockuai.tradecenter.common.domain.datamigrate.MigrateOrderItemDTO;
import com.mockuai.tradecenter.common.domain.datamigrate.MigrateOrderPaymentDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DataMigrateManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zengzhangqiang on 7/20/15.
 */
public class InsertOrder extends MigrateAction {
    @Resource
    private DataMigrateManager dataMigrateManager;

    @Override
    protected TradeResponse doTransaction(RequestContext context) throws TradeException {
        MigrateOrderDTO migrateOrderDTO = (MigrateOrderDTO)context.getRequest().getParam("migrateOrderDTO");

        MigrateOrderPaymentDTO migrateOrderPaymentDTO = migrateOrderDTO.getMigrateOrderPaymentDTO();
        MigrateOrderConsigneeDTO migrateOrderConsigneeDTO = migrateOrderDTO.getMigrateOrderConsigneeDTO();
        List<MigrateOrderItemDTO> migrateOrderItemDTOs = migrateOrderDTO.getMigrateOrderItemDTOs();

        Long orderId = dataMigrateManager.insertOrder(genOrderDO(migrateOrderDTO));
        List<OrderItemDO> orderItemDOs = genOrderItems(migrateOrderItemDTOs, orderId);
        for(OrderItemDO orderItemDO: orderItemDOs){
            dataMigrateManager.insertOrderItem(orderItemDO);
        }
        dataMigrateManager.insertOrderPayment(genOrderPayment(migrateOrderPaymentDTO, orderId));
        dataMigrateManager.insertOrderConsignee(genOrderConsignee(migrateOrderConsigneeDTO, orderId));
        return new TradeResponse(orderId);
    }

    public String getName() {
        return null;
    }

    private OrderDO genOrderDO(MigrateOrderDTO migrateOrderDTO){
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(migrateOrderDTO, orderDO);
        return orderDO;
    }

    private OrderConsigneeDO genOrderConsignee(MigrateOrderConsigneeDTO migrateOrderConsigneeDTO, Long orderId){
        OrderConsigneeDO orderConsigneeDO = new OrderConsigneeDO();
        BeanUtils.copyProperties(migrateOrderConsigneeDTO, orderConsigneeDO);
        orderConsigneeDO.setOrderId(orderId);
        return orderConsigneeDO;
    }

    private OrderPaymentDO genOrderPayment(MigrateOrderPaymentDTO migrateOrderPaymentDTO, Long orderId){
        OrderPaymentDO orderPaymentDO = new OrderPaymentDO();
        BeanUtils.copyProperties(migrateOrderPaymentDTO, orderPaymentDO);
        orderPaymentDO.setOrderId(orderId);
        return orderPaymentDO;
    }

    private List<OrderItemDO> genOrderItems(List<MigrateOrderItemDTO> migrateOrderItemDTOs, Long orderId){
        List<OrderItemDO> orderItemDOs = new CopyOnWriteArrayList<OrderItemDO>();
        for(MigrateOrderItemDTO migrateOrderItemDTO: migrateOrderItemDTOs){
            orderItemDOs.add(genOrderItem(migrateOrderItemDTO, orderId));
        }
        return orderItemDOs;
    }

    private OrderItemDO genOrderItem(MigrateOrderItemDTO migrateOrderItemDTO, Long orderId){
        OrderItemDO orderItemDO = new OrderItemDO();
        BeanUtils.copyProperties(migrateOrderItemDTO, orderItemDO);
        orderItemDO.setOrderId(orderId);
        return orderItemDO;
    }
}
