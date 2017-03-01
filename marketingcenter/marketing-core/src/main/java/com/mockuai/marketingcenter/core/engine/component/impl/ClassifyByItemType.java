package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.constant.ItemType;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ComponentType.CLASSIFY_BY_ITEM_TYPE;

/**
 * 根据商品类型划分商品
 * <p/>
 * Created by edgar.zr on 1/19/16.
 */
@Service
public class ClassifyByItemType implements Component {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassifyByItemType.class);

    public static Context wrapParams(List<MarketItemDTO> itemList) {
        Context context = new Context();
        context.setParam("itemList", itemList);
        context.setParam("component", CLASSIFY_BY_ITEM_TYPE);
        return context;
    }

    @Override
    public void init() {

    }

    @SuppressWarnings("unchecked")
	@Override
    public Map<Integer, List<MarketItemDTO>> execute(Context context) throws MarketingException {
    	LOGGER.info("划分商品类型开始");

        Map<Integer, List<MarketItemDTO>> itemTypeKeyMarketItemValue = new HashMap<Integer, List<MarketItemDTO>>();

        List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getParam("itemList");
        for (ItemType itemType : ItemType.values()) {
            itemTypeKeyMarketItemValue.put(itemType.getValue(), new ArrayList<MarketItemDTO>());
        }
        for (MarketItemDTO marketItemDTO : marketItemDTOs) {
            // 非法的商品类型, 未指定类型并且不是换购到的商品
            if (ItemType.getByValue(marketItemDTO.getItemType()) == null && marketItemDTO.getActivityInfo() == null) {
                LOGGER.error("invalid itemType : {}", JsonUtil.toJson(marketItemDTO));
                return new HashMap<>();
            }
            if (marketItemDTO.getNumber() == null) marketItemDTO.setNumber(1);

            // 团购、竞拍、秒杀、套装
            if (marketItemDTO.getItemType() != null && marketItemDTO.getItemType().intValue() != 1 && marketItemDTO.getItemType().intValue() != 22) {
                itemTypeKeyMarketItemValue.get(marketItemDTO.getItemType().intValue()).add(marketItemDTO);
                continue;
            }

            marketItemDTO.setItemType(1);
            
            // 普通商品
            if (marketItemDTO.getItemType().intValue() == 1  )
                itemTypeKeyMarketItemValue.get(marketItemDTO.getItemType().intValue()).add(marketItemDTO);
        }
        
        LOGGER.info("划分商品类型结束");
        return itemTypeKeyMarketItemValue;
    }

    @Override
    public String getComponentCode() {
        return CLASSIFY_BY_ITEM_TYPE.getCode();
    }
}
