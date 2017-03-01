package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityHistoryManager;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mockuai.marketingcenter.common.constant.ComponentType.VALIDATE_SETTLEMENT_OF_BARTER;

/**
 * Created by edgar.zr on 2/02/2016.
 */
@Service
public class ValidateSettlementOfBarter implements Component {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateSettlementOfBarter.class);

    @Autowired
    ComponentHelper componentHelper;
    @Autowired
    private ActivityItemManager activityItemManager;
    @Autowired
    private PropertyManager propertyManager;
    @Autowired
    private MarketActivityManager marketActivityManager;
    @Autowired
    private ItemManager itemManager;
    @Autowired
    private ActivityHistoryManager activityHistoryManager;

    public static Context wrapParams(SettlementInfo settlementInfo, List<MarketItemDTO> marketItemDTOs,
                                     Long userId, Long consigneeId, String bizCode, String appKey) {
        Context context = new Context();
        context.setParam("settlementInfo", settlementInfo);
        context.setParam("marketItemDTOs", marketItemDTOs);
        context.setParam("userId", userId);
        context.setParam("consigneeId", consigneeId);
        context.setParam("bizCode", bizCode);
        context.setParam("appKey", appKey);
        context.setParam("component", VALIDATE_SETTLEMENT_OF_BARTER);
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
        String bizCode = (String) context.getParam("bizCode");
        String appKey = (String) context.getParam("appKey");

        if (marketItemDTOs == null || marketItemDTOs.isEmpty()) return null;

        // 统计每个换购活动下共换购的商品数量
        Map<Long, Long> activityIdKeyNumValue = new HashMap<Long, Long>();
        List<Long> activityIds = new ArrayList<Long>();
        MarketItemDTO itemDTO;
        Long num;
        for (Iterator<MarketItemDTO> iter = marketItemDTOs.iterator(); iter.hasNext(); ) {
            itemDTO = iter.next();
            num = activityIdKeyNumValue.get(itemDTO.getActivityInfo().getActivityId());
            if (num == null) {
                num = 0L;
                activityIdKeyNumValue.put(itemDTO.getActivityInfo().getActivityId(), num);
                activityIds.add(itemDTO.getActivityInfo().getActivityId());
            }
            activityIdKeyNumValue.put(itemDTO.getActivityInfo().getActivityId(), num + itemDTO.getNumber());
        }

        // 要验证的换购活动
        if (activityIds.isEmpty()) return null;
        MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
        marketActivityQTO.setBizCode(bizCode);
        marketActivityQTO.setIdList(activityIds);
        List<MarketActivityDTO> marketActivityDTOs =
                ModelUtil.genMarketActivityDTOList(componentHelper.<List<MarketActivityDO>>execute(
                        FilterOutActivityByCurrentDate.wrapParams(marketActivityManager.queryActivity(marketActivityQTO))));
        activityItemManager.fillUpActivityItems(marketActivityDTOs, bizCode);
        propertyManager.fillUpMarketWithProperty(marketActivityDTOs, bizCode);

        Map<Long, MarketActivityDTO> activityIdKeyMarketActivityValue = new HashMap<Long, MarketActivityDTO>();
        Map<Long, Map<String, PropertyDTO>> activityIdKeyPropertyMapValue = new HashMap<Long, Map<String, PropertyDTO>>();

        for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
            activityIdKeyMarketActivityValue.put(marketActivityDTO.getId(), marketActivityDTO);
            activityIdKeyPropertyMapValue.put(marketActivityDTO.getId(), propertyManager.wrapPropertyDTO(marketActivityDTO.getPropertyList()));
        }

        MarketActivityDTO marketActivityDTO;
        Long skuId;
        ItemSkuDTO itemSkuDTO;
        // 验证换购活动的合法性
        for (Iterator<MarketItemDTO> iter = marketItemDTOs.iterator(); iter.hasNext(); ) {
            itemDTO = iter.next();
            marketActivityDTO = activityIdKeyMarketActivityValue.get(itemDTO.getActivityInfo().getActivityId());
            // 没有找到活动
            if (marketActivityDTO == null) {
                LOGGER.error("the activity of barter does not exist, marketItemDTO : {}", JsonUtil.toJson(itemDTO));
                throw new MarketingException(ResponseCode.BIZ_E_BARTER_NOT_EXISTS);
            }

            skuId = propertyManager.extractPropertySkuId(activityIdKeyPropertyMapValue.get(marketActivityDTO.getId()), null, null).getItemSkuId();
            itemSkuDTO = itemManager.getItemSku(skuId, itemDTO.getSellerId(), appKey);

            // 目标商品不合法
            if (itemDTO.getItemSkuId().longValue() != skuId || itemSkuDTO == null
                    || itemSkuDTO.getStockNum().longValue() == 0) {
                LOGGER.error("sku is illegal, marketItemDTO : {}, property of marketActivityDTO : {}, itemSkuDTO : {}",
                        JsonUtil.toJson(itemDTO), JsonUtil.toJson(activityIdKeyPropertyMapValue.get(marketActivityDTO.getId())),
                        JsonUtil.toJson(itemSkuDTO));
                throw new MarketingException(ResponseCode.BIZ_E_ITEM_OF_BARTER_NOT_EXISTS);
            }

            Boolean sameSeller = componentHelper.<Boolean>execute(SameSeller.wrapParams(
                    itemDTO.getActivityInfo().getItemDTOs(), marketActivityDTO.getCreatorId()));

            // 条件商品不合法
            if (marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_SHOP.getValue() && sameSeller
                    || marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_ITEM.getValue()
                    && containByActivityItem(marketActivityDTO.getActivityItemList(), itemDTO.getActivityInfo().getItemDTOs())) {
                LOGGER.error("the list of marketItems in MarketItemDTO are illegal, activityItems : {}, itemDTO : {}",
                        JsonUtil.toJson(marketActivityDTO.getActivityItemList()), JsonUtil.toJson(itemDTO));
                throw new MarketingException(ResponseCode.BIZ_BARTER_IS_OUT_OF_LIMIT);
            }
        }

        List<DiscountInfo> discountInfos = new ArrayList<DiscountInfo>();
        DiscountInfo discountInfo;
        Long limit;
        // 用户限购判断
        for (Iterator<MarketItemDTO> iter = marketItemDTOs.iterator(); iter.hasNext(); ) {
            itemDTO = iter.next();
            marketActivityDTO = activityIdKeyMarketActivityValue.get(itemDTO.getActivityInfo().getActivityId());
            limit = propertyManager.extractPropertyLimit(activityIdKeyPropertyMapValue.get(marketActivityDTO.getId()));
            num = activityIdKeyNumValue.get(itemDTO.getActivityInfo().getActivityId());
            // 超出了购买数量的限制
            if (limit.longValue() != 0
                    && activityHistoryManager.getNumOfSkuBuyByUser(userId, itemDTO.getActivityInfo().getActivityId(), bizCode) + num > limit)
                throw new MarketingException(ResponseCode.BIZ_E_BARTER_OUT_OF_USER_LIMIT);

            discountInfo = new DiscountInfo();
            // 填充换购价格
            discountInfo.setDiscountAmount(propertyManager.extractPropertyExtra(activityIdKeyPropertyMapValue.get(marketActivityDTO.getId())));
            itemDTO.setTotalPrice(discountInfo.getDiscountAmount());
            discountInfo.setActivity(marketActivityDTO);
            // 换购到的商品
            marketActivityDTO.setTargetItemList(genActivityItemDTOs(Arrays.asList(itemDTO)));
            // 换购主商品
            marketActivityDTO.setActivityItemList(genActivityItemDTOs(itemDTO.getActivityInfo().getItemDTOs()));

            Context ctx = new Context();
            ctx.setParam("userId", userId);
            ctx.setParam("bizCode", bizCode);
            ctx.setParam("appKey", appKey);
            ctx.setParam("discountInfo", discountInfo);
            ctx.setParam("itemTotalPrice", componentHelper.execute(ItemTotalPrice.wrapParams(itemDTO.getActivityInfo().getItemDTOs())));

            // 验证未通过
            if (componentHelper.execute(ExecuteActivityTool.wrapParams(discountInfo.getActivity(), ctx)) == null)
                throw new MarketingException(ResponseCode.BIZ_E_BARTER_UNREACHABLE);
            discountInfos.add(discountInfo);
        }
        //单独计算换购价格
        settlementInfo.setTotalPrice(settlementInfo.getTotalPrice()
                + componentHelper.<Long>execute(ItemTotalPrice.wrapParams(marketItemDTOs)));
        settlementInfo.setDeliveryFee(settlementInfo.getDeliveryFee()
                + componentHelper.<Long>execute(DeliveryFee.wrapParams(marketItemDTOs, new ArrayList<MarketItemDTO>(), userId, consigneeId, appKey)));

        settlementInfo.getDirectDiscountList().addAll(discountInfos);
        return null;
    }

    /**
     * 条件商品在活动的条件商品中
     *
     * @param activityItemDTOs
     * @param marketItemDTOs
     * @return
     */
    private boolean containByActivityItem(List<ActivityItemDTO> activityItemDTOs, List<MarketItemDTO> marketItemDTOs) {
        Set<Long> itemIdSet = new HashSet<Long>();
        for (ActivityItemDTO activityItemDTO : activityItemDTOs) {
            itemIdSet.add(activityItemDTO.getItemId());
        }
        for (MarketItemDTO marketItemDTO : marketItemDTOs) {
            if (!itemIdSet.contains(marketItemDTO.getItemId())) return true;
        }
        return false;
    }

    private List<ActivityItemDTO> genActivityItemDTOs(List<MarketItemDTO> marketItemDTOs) {
        List<ActivityItemDTO> activityItemDTOs = new ArrayList<ActivityItemDTO>();
        if (marketItemDTOs == null || marketItemDTOs.isEmpty()) return activityItemDTOs;

        for (MarketItemDTO marketItemDTO : marketItemDTOs) {
            activityItemDTOs.add(new ActivityItemDTO(marketItemDTO));
        }
        return activityItemDTOs;
    }

    @Override
    public String getComponentCode() {
        return VALIDATE_SETTLEMENT_OF_BARTER.getCode();
    }
}