package com.mockuai.distributioncenter.core.service.action.distribution.calculator;

import com.mockuai.distributioncenter.common.constant.*;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.ItemSkuDistPlanManager;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 16/5/16.
 *
 * 销售分拥计算
 */
@Service
public class SaleCalculator implements Calculator {
    private static final Logger log = LoggerFactory.getLogger(SaleCalculator.class);

    @Autowired
    private DistRecordManager distRecordManager;

    @Override
    public boolean calculate(final SellerDTO seller, final DistributionOrderDTO orderDTO) throws DistributionException {
        // 入参检查
        if (seller == null) {
            log.error("seller is null, when do calculate");
            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR.getCode(), "seller is null");
        }

        if (orderDTO == null) {
            log.error("order is null, when do calculate");
            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR.getCode(), "order is null");
        }

        if (orderDTO.getItemDTOs() == null || orderDTO.getItemDTOs().isEmpty()) {
            log.error("order has no items to calculate, order: {}", orderDTO.getOrderSn());
            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "order has no items to calculate");
        }

        // 卖家合法性检查，确认该卖家是否是可以进行分拥的
        if (!seller.getStatus().equals(SellerStatus.ACTIVE.getStatus())) {
            log.warn("seller are not in active, seller: {}", JsonUtil.toJson(seller));
            return true;
        }

        // 开始分拥计算
        for (DistributionItemDTO itemDTO : orderDTO.getItemDTOs()) {
            // 实际金额分拥
            DistRecordDTO recordDTO = genDistRecord(seller, itemDTO.getSaleRatio(), itemDTO, orderDTO);
            distRecordManager.add(recordDTO);
        }

        return true;
    }

    private DistRecordDTO genDistRecord(final SellerDTO sellerDTO,
                                        Double distRatio,
                                        final DistributionItemDTO itemDTO,
                                        final DistributionOrderDTO orderDTO) throws DistributionException {
        DistRecordDTO recordDTO = new DistRecordDTO();
        recordDTO.setOrderId(orderDTO.getOrderId());
        recordDTO.setOrderSn(orderDTO.getOrderSn());
        recordDTO.setSellerId(sellerDTO.getId());
        recordDTO.setItemId(itemDTO.getItemId());
        recordDTO.setItemSkuId(itemDTO.getItemSkuId());
        recordDTO.setBuyerId(orderDTO.getUserId());
        recordDTO.setStatus(DistributionStatus.UNDER_DISTRIBUTION.getStatus());
        recordDTO.setDistRatio(distRatio);
        recordDTO.setType(DistributionType.REAL_AMOUNT.getType());
        recordDTO.setSource(DistributeSource.SALE_DIST.getSource());
        recordDTO.setShopId(orderDTO.getShopId());
        recordDTO.setUnitPrice(itemDTO.getUnitPrice());
        recordDTO.setNumber(itemDTO.getNumber());
        Long totalAmount = recordDTO.getUnitPrice() * recordDTO.getNumber();
        recordDTO.setDistAmount(Math.round(distRatio * totalAmount));
        return recordDTO;
    }
}
