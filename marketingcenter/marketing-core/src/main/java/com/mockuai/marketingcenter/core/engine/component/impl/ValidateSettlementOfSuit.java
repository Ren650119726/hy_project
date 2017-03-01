package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import com.mockuai.marketingcenter.core.manager.ItemSuitManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ComponentType.VALIDATE_SETTLEMENT_OF_SUIT;

/**
 * Created by edgar.zr on 2/02/2016.
 */
@Service
public class ValidateSettlementOfSuit implements Component {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateSettlementOfSuit.class);

    @Autowired
    private ItemSuitManager itemSuitManager;
    @Autowired
    private ItemManager itemManager;
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
        context.setParam("component", VALIDATE_SETTLEMENT_OF_SUIT);
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

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setIdList(new ArrayList<Long>());
        itemQTO.setItemStatus(4);

        Map<Long, MarketItemDTO> itemIdKeyMarketItemValue = new HashMap<Long, MarketItemDTO>();

        for (MarketItemDTO marketItemDTO : marketItemDTOs) {
            itemQTO.getIdList().add(marketItemDTO.getItemId());
            itemIdKeyMarketItemValue.put(marketItemDTO.getItemId(), marketItemDTO);
        }
        List<MarketItemDTO> targetItems = new ArrayList<MarketItemDTO>();
        MarketItemDTO marketItemDTO;
        List<ItemDTO> itemDTOs = itemSuitManager.querySuit(itemQTO, userId, appKey);
        if (itemDTOs == null || itemDTOs.isEmpty()) {
            LOGGER.error("error to query suit, it does not exist, itemQTO : {}", JsonUtil.toJson(itemQTO));
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "套装不存在");
        }

        DiscountInfo discountInfo;
        MarketActivityDTO marketActivityDTO;
        Integer number;
        for (ItemDTO itemDTO : itemDTOs) {
            number = itemIdKeyMarketItemValue.get(itemDTO.getId()).getNumber();
            // 套装价格
            settlementInfo.setTotalPrice(settlementInfo.getTotalPrice() + itemDTO.getItemSkuDTOList().get(0).getPromotionPrice() * number);

            discountInfo = new DiscountInfo();
            marketActivityDTO = new MarketActivityDTO();
            marketActivityDTO.setActivityItemList(itemManager.wrapItemWithSkuInfo(Arrays.asList(itemDTO)));
            marketActivityDTO.getActivityItemList().get(0).setNumber(number);
            marketActivityDTO.setTargetItemList(itemManager.wrapItemWithSkuInfo(itemDTO.getSubItemList()));
            for (ActivityItemDTO activityItemDTO : marketActivityDTO.getTargetItemList()) {
                marketItemDTO = new MarketItemDTO();
                marketItemDTO.setItemId(activityItemDTO.getItemId());
                marketItemDTO.setNumber(number);
                marketItemDTO.setSkuSnapshot(activityItemDTO.getSkuSnapshot());
                targetItems.add(marketItemDTO);
                activityItemDTO.setNumber(number);
            }
            marketActivityDTO.setToolCode("SuitTool");
            discountInfo.setActivity(marketActivityDTO);

            settlementInfo.getDirectDiscountList().add(discountInfo);
        }
        settlementInfo.setDeliveryFee(settlementInfo.getDeliveryFee() + componentHelper.<Long>execute(
                DeliveryFee.wrapParams(targetItems, new ArrayList<MarketItemDTO>(), userId, consigneeId, appKey)));

        return null;
    }

    @Override
    public String getComponentCode() {
        return VALIDATE_SETTLEMENT_OF_SUIT.getCode();
    }
}