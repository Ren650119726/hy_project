package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.groupbuycenter.common.domain.dto.GroupBuyDTO;
import com.mockuai.groupbuycenter.common.domain.dto.GroupBuyItemDTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.GroupBuyManager;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mockuai.marketingcenter.common.constant.ComponentType.VALIDATE_SETTLEMENT_OF_GROUP_BUY;

/**
 * Created by edgar.zr on 2/02/2016.
 */
@Service
public class ValidateSettlementOfGroupBuy implements Component {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateSettlementOfGroupBuy.class);

    @Autowired
    private GroupBuyManager groupBuyManager;
    @Autowired
    private ComponentHelper componentHelper;

    public static Context wrapParams(SettlementInfo settlementInfo, List<MarketItemDTO> marketItemDTOs,
                                     Long userId, Long consigneeId, String appKey) {
        Context context = new Context();
        context.setParam("settlementInfo", settlementInfo);
        context.setParam("marketItemDTOs", marketItemDTOs);
        context.setParam("userId", userId);
        context.setParam("consigneeId", consigneeId);
        context.setParam("appKey", appKey);
        context.setParam("component", VALIDATE_SETTLEMENT_OF_GROUP_BUY);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public Void execute(Context context) throws MarketingException {
        SettlementInfo settlementInfo = (SettlementInfo) context.getParam("settlementInfo");
        List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getParam("marketItemDTOs");
        Long userId = (Long) context.getParam("userId");
        Long consigneeId = (Long) context.getParam("consigneeId");
        String appKey = (String) context.getParam("appKey");

        if (marketItemDTOs == null || marketItemDTOs.isEmpty()) return null;
        MarketItemDTO marketItemDTO = marketItemDTOs.get(0);
        MarketItemDTO gbMarketItemDTO = new MarketItemDTO();

        GroupBuyDTO groupBuyDTO =
                groupBuyManager.validateForSettlement(marketItemDTO.getItemSkuId(),
                        userId, marketItemDTO.getSellerId(), appKey);

        if (groupBuyDTO == null) {
            LOGGER.error("error to query groupBuy, it does not exist, marketItemDTO : {}",
                    JsonUtil.toJson(marketItemDTO));
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "团购不存在");
        }

        // 填充团购商品
        GroupBuyItemDTO groupBuyItemDTO = groupBuyDTO.getGroupBuyItem();

        settlementInfo.setItemList(new ArrayList<MarketItemDTO>());
        settlementInfo.getItemList().add(gbMarketItemDTO);
        gbMarketItemDTO.setSkuSnapshot(groupBuyItemDTO.getSkuSnapshot());
        gbMarketItemDTO.setIconUrl(groupBuyItemDTO.getIconUrl());
        gbMarketItemDTO.setNumber(marketItemDTO.getNumber());
        gbMarketItemDTO.setUnitPrice(groupBuyDTO.getPrice());
        gbMarketItemDTO.setOriginTotalPrice(groupBuyDTO.getPrice());
        gbMarketItemDTO.setItemType(marketItemDTO.getItemType());
        gbMarketItemDTO.setItemId(marketItemDTO.getItemId());
        gbMarketItemDTO.setItemName(groupBuyItemDTO.getName());
        gbMarketItemDTO.setItemSkuId(groupBuyItemDTO.getSkuId());
        gbMarketItemDTO.setSellerId(groupBuyDTO.getSellerId());

        settlementInfo.setTotalPrice(gbMarketItemDTO.getUnitPrice());
        settlementInfo.setDeliveryFee(
                componentHelper.<Long>execute(
                        DeliveryFee.wrapParams(marketItemDTOs, new ArrayList<MarketItemDTO>(), userId, consigneeId, appKey)));
        LOGGER.info("settlement in groupbuy : {}", JsonUtil.toJson(settlementInfo));
        return null;
    }

    @Override
    public String getComponentCode() {
        return VALIDATE_SETTLEMENT_OF_GROUP_BUY.getCode();
    }
}