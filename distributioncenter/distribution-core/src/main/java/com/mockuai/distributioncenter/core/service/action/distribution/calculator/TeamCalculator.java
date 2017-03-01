package com.mockuai.distributioncenter.core.service.action.distribution.calculator;

import com.mockuai.distributioncenter.common.constant.DistributionStatus;
import com.mockuai.distributioncenter.common.constant.DistributionType;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.constant.SellerStatus;
import com.mockuai.distributioncenter.common.domain.dto.*;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.ItemDistPlanManager;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 16/5/16.
 *
 * 正常分拥计算，处理团队分拥的计算。
 */
@Service
public class TeamCalculator implements Calculator {
    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    @Autowired
    private ItemDistPlanManager itemDistPlanManager;

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
            ItemDistPlanDTO planDTO = getPlan(itemDTO.getItemId(), orderDTO.getDistLevel());

            // 实际金额分拥
            DistRecordDTO recordDTO = genDistRecord(
                    seller,
                    planDTO.getRealDistRatio(),
                    itemDTO,
                    orderDTO,
                    DistributionType.REAL_AMOUNT.getType(),
                    orderDTO.getSource()
            );
            distRecordManager.add(recordDTO);

            // 嗨币分拥
            recordDTO = genDistRecord(
                    seller,
                    planDTO.getVirtualDistRatio(),
                    itemDTO,
                    orderDTO,
                    DistributionType.HI_COIN_AMOUNT.getType(),
                    orderDTO.getSource()
            );
            distRecordManager.add(recordDTO);
        }

        return true;
    }

    private ItemDistPlanDTO getPlan(Long itemId, int level) throws DistributionException {
        ItemDistPlanDTO planDTO = itemDistPlanManager.getByItemAndLevel(itemId, level);
        if (planDTO == null) {
            log.error("no item calculate plan exists, itemId: {}, level: {}", itemId, level);
            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "item dist plan not exists");
        }
        return planDTO;
    }

    private DistRecordDTO genDistRecord(final SellerDTO sellerDTO,
                               final Double distRatio,
                               final DistributionItemDTO itemDTO,
                               final DistributionOrderDTO orderDTO,
                               int type,
                               int source) throws DistributionException {
        DistRecordDTO recordDTO = new DistRecordDTO();
        recordDTO.setOrderId(orderDTO.getOrderId());
        recordDTO.setOrderSn(orderDTO.getOrderSn());
        recordDTO.setSellerId(sellerDTO.getId());
        recordDTO.setItemId(itemDTO.getItemId());
        recordDTO.setItemSkuId(itemDTO.getItemSkuId());
        recordDTO.setBuyerId(orderDTO.getUserId());
        recordDTO.setStatus(DistributionStatus.UNDER_DISTRIBUTION.getStatus());
        recordDTO.setDistRatio(distRatio);
        recordDTO.setType(type);
        recordDTO.setShopId(orderDTO.getShopId());
        recordDTO.setSource(source);
        recordDTO.setUnitPrice(itemDTO.getUnitPrice());
        recordDTO.setNumber(itemDTO.getNumber());
        // 团队分拥 = 销售分拥 * 卖家级别对应的分拥比率
        Long totalAmount = Math.round(recordDTO.getUnitPrice() * recordDTO.getNumber() * itemDTO.getSaleRatio());
        if (type == DistributionType.HI_COIN_AMOUNT.getType()) {
            recordDTO.setDistAmount(Math.round(distRatio * totalAmount / 10)); /* 嗨币和实际金额的比值是1:10 */
        } else {
            recordDTO.setDistAmount(Math.round(distRatio * totalAmount));
        }
        return recordDTO;
    }
}
