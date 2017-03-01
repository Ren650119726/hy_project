package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mockuai.marketingcenter.common.constant.ActivityScope.SCOPE_WHOLE;
import static com.mockuai.marketingcenter.common.constant.ComponentType.LINK_ACTIVITY_WITH_ITEM_LIST;

/**
 * 为订单商品关联优惠活动
 * <p/>
 * Created by edgar.zr on 1/28/2016.
 */
@Service
public class LinkActivityWithItemList implements Component {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkActivityWithItemList.class);
    private static final Integer DEFAULT_COUNT = 5000;

    @Autowired
    private MarketActivityManager marketActivityManager;
    @Autowired
    private PropertyManager propertyManager;
    @Autowired
    private ActivityItemManager activityItemManager;
    @Autowired
    private GrantedCouponManager grantedCouponManager;

    public static Context wrapParams(List<MarketItemDTO> itemDTOs,
                                     String bizCode,
                                     Long userId,
                                     List<DiscountInfo> discountInfos,
                                     String appKey) {

        Context context = new Context();
        context.setParam("itemDTOs", itemDTOs);
        context.setParam("userId", userId);
        context.setParam("discountInfos", discountInfos);
        context.setParam("bizCode", bizCode);
        context.setParam("appKey", appKey);
        context.setParam("component", LINK_ACTIVITY_WITH_ITEM_LIST);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public List<MarketActivityDTO> execute(Context context) throws MarketingException {

        List<MarketItemDTO> itemDTOs = (List<MarketItemDTO>) context.getParam("itemDTOs");
        List<DiscountInfo> discountInfos = (List<DiscountInfo>) context.getParam("discountInfos");
        String bizCode = (String) context.getParam("bizCode");
        Long userId = (Long) context.getParam("userId");

        List<MarketActivityDO> marketActivityDOs = new ArrayList<>();
        List<MarketActivityDO> tempMarketActivityDOs;
        List<MarketActivityDTO> marketActivityDTOs;
        try {
            // 查询商场下所有关联的优惠活动
            MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
            marketActivityQTO.setBizCode(bizCode);
            marketActivityQTO.setScopeList(new ArrayList<Integer>());
            marketActivityQTO.getScopeList().add(SCOPE_WHOLE.getValue()); // 全场
//            marketActivityQTO.getScopeList().add(ActivityScope.SCOPE_SPECIAL.getValue()); // 专场
            marketActivityQTO.getScopeList().add(ActivityScope.SCOPE_SHOP.getValue()); // 店铺
            marketActivityQTO.getScopeList().add(ActivityScope.SCOPE_ITEM.getValue()); // 指定商品
            marketActivityQTO.getScopeList().add(ActivityScope.SCOPE_BRAND.getValue()); // 指定品牌
            marketActivityQTO.getScopeList().add(ActivityScope.SCOPE_CATEGORY.getValue()); // 指定品类
            marketActivityQTO.setStatus(ActivityStatus.NORMAL.getValue());
            marketActivityQTO.setOffset(-DEFAULT_COUNT);
            marketActivityQTO.setCount(DEFAULT_COUNT);

            do {
                marketActivityQTO.setOffset(marketActivityQTO.getOffset() + DEFAULT_COUNT);
                tempMarketActivityDOs = marketActivityManager.queryActivityForSettlement(marketActivityQTO);
                marketActivityDOs.addAll(tempMarketActivityDOs);
            } while (!tempMarketActivityDOs.isEmpty());

            if (marketActivityDOs.isEmpty()) {
                return ModelUtil.genMarketActivityDTOList(marketActivityDOs);
            }

            Set<Long> sellerIdSet = new HashSet<>();

            Set<ItemCombine> itemSet = new HashSet<>();// 根据 scope 拆分单个商品的特征, 分别表示 itemId, brandId, categoryId
            Map<Long, List<MarketItemDTO>> itemIdKey = new HashMap<>();// 同属一个 itemId
            Map<Long, List<MarketItemDTO>> brandIdKey = new HashMap<>();// 同属一个 brandId
            Map<Long, List<MarketItemDTO>> categoryIdKey = new HashMap<>();// 同属一个 categoryId

            // 创建 hash 数组
            for (MarketItemDTO marketItemDTO : itemDTOs) {
                sellerIdSet.add(marketItemDTO.getSellerId());
                if (marketItemDTO.getItemId() != null) {
                    itemSet.add(new ItemCombine(marketItemDTO.getSellerId(), marketItemDTO.getItemId(), 0));
                    if (!itemIdKey.containsKey(marketItemDTO.getItemId())) {
                        itemIdKey.put(marketItemDTO.getItemId(), new ArrayList<MarketItemDTO>());
                    }
                    itemIdKey.get(marketItemDTO.getItemId()).add(marketItemDTO);
                }
                if (marketItemDTO.getCategoryId() != null) {
                    itemSet.add(new ItemCombine(null, marketItemDTO.getCategoryId(), 1)); // category 无 sellerId
                    if (!categoryIdKey.containsKey(marketItemDTO.getCategoryId())) {
                        categoryIdKey.put(marketItemDTO.getCategoryId(), new ArrayList<MarketItemDTO>());
                    }
                    categoryIdKey.get(marketItemDTO.getCategoryId()).add(marketItemDTO);
                }
                if (marketItemDTO.getBrandId() != null) {
                    itemSet.add(new ItemCombine(null, marketItemDTO.getBrandId(), 2)); // brand 无 sellerId
                    if (!brandIdKey.containsKey(marketItemDTO.getBrandId())) {
                        brandIdKey.put(marketItemDTO.getBrandId(), new ArrayList<MarketItemDTO>());
                    }
                    brandIdKey.get(marketItemDTO.getBrandId()).add(marketItemDTO);
                }
            }

            Date currentDate = new Date();
            Iterator<MarketActivityDO> activityIterator = marketActivityDOs.iterator();
            MarketActivityDO marketActivityDO;
            Map<Long, MarketActivityDO> unknownMarketActivityDO = new HashMap<>(); // 未过滤关联商品的活动
            Map<Long, MarketActivityDO> couponMarketActivityDO = new HashMap<>();  // 未通过用户持有优惠券情况过滤的活动
            while (activityIterator.hasNext()) {
                marketActivityDO = activityIterator.next();

                //只有在可用期范围内的营销活动方可满足需求，且商品售空时间为空(针对换购)
                if (marketActivityDO.getItemInvalidTime() != null
                        || marketActivityDO.getStartTime().after(currentDate)
                        || marketActivityDO.getEndTime().before(currentDate)) {
                    activityIterator.remove();
                    continue;
                }

                switch (ActivityScope.getScopeByValue(marketActivityDO.getScope())) {
                    case SCOPE_WHOLE:
                        if (marketActivityDO.getCouponMark().intValue() == 1) {
                            couponMarketActivityDO.put(marketActivityDO.getId(), marketActivityDO);
                            activityIterator.remove();
                        }
                        break;
                    case SCOPE_SPECIAL:
                        if (marketActivityDO.getCouponMark().intValue() == 1) {
                            couponMarketActivityDO.put(marketActivityDO.getId(), marketActivityDO);
                            activityIterator.remove();
                        }
                        break;
                    case SCOPE_SHOP:
                        if (!sellerIdSet.contains(marketActivityDO.getCreatorId())) {
                            activityIterator.remove();
                            break;
                        }
                        if (marketActivityDO.getCouponMark().intValue() == 1) {
                            couponMarketActivityDO.put(marketActivityDO.getId(), marketActivityDO);
                            activityIterator.remove();
                        }
                        break;
                    case SCOPE_ITEM:
                    case SCOPE_CATEGORY:
                    case SCOPE_BRAND:
                        unknownMarketActivityDO.put(marketActivityDO.getId(), marketActivityDO);
                        activityIterator.remove();
                        break;
                }
            }

            List<ActivityItemDO> activityItemDOs = new ArrayList<>();
            if (!unknownMarketActivityDO.isEmpty()) {

                ActivityItemQTO activityItemQTO = new ActivityItemQTO();
                activityItemQTO.setOffset(-DEFAULT_COUNT);
                activityItemQTO.setCount(DEFAULT_COUNT);
                activityItemQTO.setActivityIds(new ArrayList<>(unknownMarketActivityDO.keySet()));
                List<ActivityItemDO> tempActivityItemDOs;
                do {
                    activityItemQTO.setOffset(activityItemQTO.getOffset() + DEFAULT_COUNT);
                    tempActivityItemDOs = activityItemManager.queryActivityItemForActivity(activityItemQTO);
                    activityItemDOs.addAll(tempActivityItemDOs);
                } while (!tempActivityItemDOs.isEmpty());
            }

            Map<Long, Set<MarketItemDTO>> activityIdKey = new HashMap<>(activityItemDOs.size());
            for (ActivityItemDO activityItemDO : activityItemDOs) { // 过滤出关联 itemId, categoryId, brandId 的活动
                if (activityItemDO.getItemId() != null
                        && itemSet.contains(new ItemCombine(activityItemDO.getSellerId(), activityItemDO.getItemId(), 0))) {
                    addActivityThroughItem(activityItemDO.getActivityId(),
                            marketActivityDOs, unknownMarketActivityDO, couponMarketActivityDO);
                    if (!activityIdKey.containsKey(activityItemDO.getActivityId())) {
                        activityIdKey.put(activityItemDO.getActivityId(), new HashSet<MarketItemDTO>());
                    }
                    activityIdKey.get(activityItemDO.getActivityId()).addAll(itemIdKey.get(activityItemDO.getItemId()));
                    continue;
                }
                if (activityItemDO.getCategoryId() != null
                        && itemSet.contains(new ItemCombine(null, activityItemDO.getCategoryId(), 1))) {
                    addActivityThroughItem(activityItemDO.getActivityId(),
                            marketActivityDOs, unknownMarketActivityDO, couponMarketActivityDO);
                    if (!activityIdKey.containsKey(activityItemDO.getActivityId())) {
                        activityIdKey.put(activityItemDO.getActivityId(), new HashSet<MarketItemDTO>());
                    }
                    activityIdKey.get(activityItemDO.getActivityId()).addAll(categoryIdKey.get(activityItemDO.getCategoryId()));
                    continue;
                }
                if (activityItemDO.getBrandId() != null
                        && itemSet.contains(new ItemCombine(null, activityItemDO.getBrandId(), 2))) {
                    addActivityThroughItem(activityItemDO.getActivityId(),
                            marketActivityDOs, unknownMarketActivityDO, couponMarketActivityDO);
                    if (!activityIdKey.containsKey(activityItemDO.getActivityId())) {
                        activityIdKey.put(activityItemDO.getActivityId(), new HashSet<MarketItemDTO>());
                    }
                    activityIdKey.get(activityItemDO.getActivityId()).addAll(brandIdKey.get(activityItemDO.getBrandId()));
                }
            }

            marketActivityDTOs = ModelUtil.genMarketActivityDTOList(marketActivityDOs);
            // 提前包装 market 相关数据
            List<MarketActivityDTO> tempMarkets = new ArrayList<>(marketActivityDTOs);
            tempMarkets.addAll(ModelUtil.genMarketActivityDTOList(new ArrayList<>(couponMarketActivityDO.values())));
            Map<Long, MarketActivityDTO> activityIdKeyDTO = new HashMap<>();
            for (MarketActivityDTO marketActivityDTO : tempMarkets) {
                activityIdKeyDTO.put(marketActivityDTO.getId(), marketActivityDTO);
            }
            // 关联子活动
            marketActivityManager.linkSubMarketActivity(tempMarkets);
            // 关联 propertyList 到 活动
            propertyManager.fillUpMarketWithProperty(tempMarkets, bizCode);

            // 过滤用户未持有优惠券的活动
            if (userId != null) {
                GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
                grantedCouponQTO.setActivityIds(new ArrayList<>(couponMarketActivityDO.keySet()));
                grantedCouponQTO.setStatus(UserCouponStatus.UN_USE.getValue());
                grantedCouponQTO.setReceiverId(userId);
                grantedCouponQTO.setOffset(-DEFAULT_COUNT);
                grantedCouponQTO.setCount(DEFAULT_COUNT);
                List<GrantedCouponDO> grantedCouponDOs = new ArrayList<>();
                List<GrantedCouponDO> tempGrantedCoupon;

                do {
                    grantedCouponQTO.setOffset(grantedCouponQTO.getOffset() + DEFAULT_COUNT);
                    tempGrantedCoupon = grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);
                    grantedCouponDOs.addAll(tempGrantedCoupon);
                } while (!tempGrantedCoupon.isEmpty());

                MarketActivityDTO tempMarket;
                for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
                    if (grantedCouponDO.getInvalidTime() != null && grantedCouponDO.getInvalidTime().before(new Date())) {
                        continue;
                    }
                    if (couponMarketActivityDO.containsKey(grantedCouponDO.getActivityId())) {
                        tempMarket = activityIdKeyDTO.get(grantedCouponDO.getActivityId());
                        marketActivityDTOs.add(tempMarket);
                        tempMarket.setGrantedCouponDTO(ModelUtil.genGrantedCouponDTO(grantedCouponDO, tempMarket));
                        couponMarketActivityDO.remove(grantedCouponDO.getActivityId());
                    }
                }
            } else {
                marketActivityDTOs = tempMarkets;
            }

            if (marketActivityDTOs.isEmpty()) {
                return Collections.EMPTY_LIST;
            }

//            marketActivityDTOs = filterActivityByStockNum(marketActivityDTOs, appKey);

            // 按照 满减送/优惠券类型, level 双关键字排序, 满减送在优惠券前, level 降序, 去除满减 和 解决商城和店铺优惠的冲突
            Collections.sort(marketActivityDTOs, new Comparator<MarketActivityDTO>() {
                @Override
                public int compare(MarketActivityDTO o1, MarketActivityDTO o2) {
                    if (o1.getToolCode().equals(ToolType.COMPOSITE_TOOL.getCode()) && o2.getToolCode().equals(ToolType.SIMPLE_TOOL.getCode()))
                        return -1;
                    if (o1.getToolCode().equals(ToolType.SIMPLE_TOOL.getCode()) && o2.getToolCode().equals(ToolType.COMPOSITE_TOOL.getCode()))
                        return 1;
                    if (o1.getLevel().intValue() > o2.getLevel().intValue())
                        return -1;
                    if (o1.getLevel().intValue() < o2.getLevel().intValue())
                        return 1;
                    return 0;
                }
            });

            if (discountInfos != null) {

                // 同一个商品 itemId 可能会同时选择其下不同的 sku
                Map<Long, List<MarketItemDTO>> marketItemDTOMapWithItemIdKey = new HashMap<>();

                for (MarketItemDTO marketItemDTO : itemDTOs) {
                    if (!marketItemDTOMapWithItemIdKey.containsKey(marketItemDTO.getItemId())) {
                        marketItemDTOMapWithItemIdKey.put(marketItemDTO.getItemId(), new ArrayList<MarketItemDTO>());
                    }
                    marketItemDTOMapWithItemIdKey.get(marketItemDTO.getItemId()).add(marketItemDTO);
                }

                DiscountInfo discountInfo;
                // 关联 MarketItemDTO 到相应的优惠活动上
                for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
                    discountInfo = new DiscountInfo(marketActivityDTO);
                    discountInfo.setItemList(new ArrayList<MarketItemDTO>());
                    if (activityIdKey.containsKey(marketActivityDTO.getId())) {
                        for (MarketItemDTO marketItemDTO : activityIdKey.get(marketActivityDTO.getId())) {
                            discountInfo.getItemList().add(marketItemDTO);
                        }
                    }
                    discountInfos.add(discountInfo);
                }
            }
        } catch (MarketingException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            LOGGER.error("error of queryActivityWithItemList, itemDTOs : {}, userId : {}, bizCode : {}",
                    JsonUtil.toJson(itemDTOs), userId, bizCode, e);
            return Collections.emptyList();
        }

        return marketActivityDTOs;
    }

    /**
     * 添加商品关联活动
     *
     * @param activityId        与订单中商品关联的活动
     * @param marketActivityDOs 合法的活动
     * @param map               关联商品活动
     * @param couponMap         优惠券活动
     */
    private void addActivityThroughItem(Long activityId,
                                        List<MarketActivityDO> marketActivityDOs,
                                        Map<Long, MarketActivityDO> map,
                                        Map<Long, MarketActivityDO> couponMap) {
        if (map.containsKey(activityId)) {
            if (map.get(activityId).getCouponMark().intValue() == 1) {
                couponMap.put(activityId, map.get(activityId));
            } else {
                marketActivityDOs.add(map.get(activityId));
            }
            map.remove(activityId);// 避免重复添加
        }
    }

    @Override
    public String getComponentCode() {
        return LINK_ACTIVITY_WITH_ITEM_LIST.getCode();
    }

    private static class ItemCombine {
        private Long sellerId;
        private Long deltaId; // itemId, categoryId, brandId
        private Integer type; // 0, 1, 2

        public ItemCombine(Long sellerId, Long deltaId, Integer type) {
            this.sellerId = sellerId;
            this.deltaId = deltaId;
            this.type = type;
        }

        public Long getSellerId() {
            return sellerId;
        }

        public void setSellerId(Long sellerId) {
            this.sellerId = sellerId;
        }

        public Long getDeltaId() {
            return deltaId;
        }

        public void setDeltaId(Long deltaId) {
            this.deltaId = deltaId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ItemCombine that = (ItemCombine) o;

            if (sellerId != null ? !sellerId.equals(that.sellerId) : that.sellerId != null) return false;
            if (!deltaId.equals(that.deltaId)) return false;
            return type.equals(that.type);

        }

        @Override
        public int hashCode() {
            int result = sellerId != null ? sellerId.hashCode() : 0;
            result = 31 * result + deltaId.hashCode();
            result = 31 * result + type.hashCode();
            return result;
        }
    }
}