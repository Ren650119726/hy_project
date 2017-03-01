package com.mockuai.distributioncenter.core.service.action.distribution.calculator;

import com.mockuai.distributioncenter.common.constant.DistributionStatus;
import com.mockuai.distributioncenter.common.constant.DistributionType;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.constant.SellerStatus;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.GiftsManager;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketProfitDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 16/5/18.
 */
@Service
public class OpenShopCalculator implements Calculator {
    private static final Logger log = LoggerFactory.getLogger(OpenShopCalculator.class);

    @Autowired
    private DistRecordManager distRecordManager;

    @Autowired
    private GiftsManager giftsManager;

    @Override
    public boolean calculate(SellerDTO seller, DistributionOrderDTO orderDTO) throws DistributionException {
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

        String appKey = orderDTO.getAppKey();
        // 开始分拥计算
        for (DistributionItemDTO itemDTO : orderDTO.getItemDTOs()) {
            GiftsPacketProfitDTO profitDTO = giftsManager.getGiftsPoint(itemDTO.getItemId(), (long)orderDTO.getDistLevel(), appKey);
            if (profitDTO == null) {
                log.error("gift is null, itemId: {}, level: {}", itemDTO.getItemId(), orderDTO.getDistLevel());
                throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR);
            }
            // 实际金额分拥
            DistRecordDTO recordDTO = genDistRecord(seller, profitDTO.getLevelMoney(), itemDTO, orderDTO, DistributionType.REAL_AMOUNT.getType(), orderDTO.getSource());
            distRecordManager.add(recordDTO);

            // 嗨币分拥
            recordDTO = genDistRecord(seller, profitDTO.getLevelScore(), itemDTO, orderDTO, DistributionType.HI_COIN_AMOUNT.getType(), orderDTO.getSource());
            distRecordManager.add(recordDTO);
        }


        return true;
    }

    private DistRecordDTO genDistRecord(final SellerDTO sellerDTO,
                                        final Long money,
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
        recordDTO.setDistRatio(0.0);
        recordDTO.setType(type);
        recordDTO.setShopId(orderDTO.getShopId());
        recordDTO.setSource(source);
        recordDTO.setUnitPrice(itemDTO.getUnitPrice());
        recordDTO.setNumber(itemDTO.getNumber());
        recordDTO.setDistAmount(money);
        return recordDTO;
    }
}