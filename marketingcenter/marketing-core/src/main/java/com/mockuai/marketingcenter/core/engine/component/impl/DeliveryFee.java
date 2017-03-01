package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ComponentType.DELIVERY_FEE;

/**
 * Created by edgar.zr on 1/12/16.
 */
@Service
public class DeliveryFee implements Component {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryFee.class);

    @Autowired
    private ItemManager itemManager;

    public static Context wrapParams(List<MarketItemDTO> itemList, List<MarketItemDTO> excludeItemList, Long userId, Long consigneeId, String appKey) {
        Context context = new Context();
        context.setParam("itemList", itemList);
        context.setParam("excludeItemList", excludeItemList);
        context.setParam("userId", userId);
        context.setParam("consigneeId", consigneeId);
        context.setParam("appKey", appKey);
        context.setParam("component", DELIVERY_FEE);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public Long execute(Context context) throws MarketingException {

        List<MarketItemDTO> itemList = (List<MarketItemDTO>) context.getParam("itemList");
        List<MarketItemDTO> excludeItemList = (List<MarketItemDTO>) context.getParam("excludeItemList");
        Long userId = (Long) context.getParam("userId");
        Long consigneeId = (Long) context.getParam("consigneeId");
        String appKey = (String) context.getParam("appKey");

        if (consigneeId == null || itemList == null || itemList.isEmpty()) return 0L;

        Map<Long, Integer> itemNumber = new HashMap<Long, Integer>();
        Map<MarketItemDTO, MarketItemDTO> excludeMarketItemDTOMap = new HashMap<MarketItemDTO, MarketItemDTO>();
        for (MarketItemDTO marketItemDTO : excludeItemList) {
            excludeMarketItemDTOMap.put(marketItemDTO, marketItemDTO);
        }

        Integer number;
        for (MarketItemDTO marketItemDTO : itemList) {
            if (excludeMarketItemDTOMap.containsKey(marketItemDTO)) {
                continue;
            }
            if (!itemNumber.containsKey(marketItemDTO.getItemId())) {
                itemNumber.put(marketItemDTO.getItemId(), 0);
            }
            number = itemNumber.get(marketItemDTO.getItemId());
            itemNumber.put(marketItemDTO.getItemId(), marketItemDTO.getNumber() + number);
        }

        try {
            Long deliveryFee = itemManager.getPostageFee(itemNumber, userId, consigneeId, appKey);
            LOGGER.info("userId : {}, consigneeId : {}, deliveryFee : {}, itemList : {}, appKey : {}",
                    userId, consigneeId, deliveryFee, JsonUtil.toJson(itemList), appKey);
            return deliveryFee;
        } catch (MarketingException e) {
            LOGGER.debug("error to get postage fee", e);
            return 0L;
        }
    }

    @Override
    public String getComponentCode() {
        return DELIVERY_FEE.getCode();
    }
}