package com.mockuai.marketingcenter.core.engine.tool.impl;

import com.mockuai.marketingcenter.common.constant.PropertyEnum;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketToolDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyTmplDTO;
import com.mockuai.marketingcenter.core.engine.component.ComponentHolder;
import com.mockuai.marketingcenter.core.engine.tool.Tool;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 计算满减送活动
 * 如果满足需要对相应商品做等比的优惠力度扣减
 * <p/>
 * Created by edgar.zr on 8/8/15.
 */
@Service
public class ReachMultipleReduceTool implements Tool {

    private static final MarketToolDTO marketTool;

    static {
        marketTool = new MarketToolDTO();
        marketTool.setBizCode("mockuai");
        marketTool.setDeleteMark(0);
        marketTool.setImplContent("");
        marketTool.setImplType(1);
        marketTool.setType(ToolType.COMPOSITE_TOOL.getValue());
        marketTool.setToolCode(ToolType.COMPOSITE_TOOL.getCode());
        marketTool.setProviderId(1L);
        marketTool.setProviderType(1);
        marketTool.setToolName("满减送工具");
        marketTool.setStatus(0);
        marketTool.setParentId(0L);

        marketTool.setPropertyTmplList(new ArrayList<PropertyTmplDTO>());
        marketTool.getPropertyTmplList().add(ModelUtil.genCommonPropertyTmplDTO(PropertyEnum.CONSUME, 1));
        marketTool.getPropertyTmplList().add(ModelUtil.genCommonPropertyTmplDTO(PropertyEnum.QUOTA, 1));
        marketTool.getPropertyTmplList().add(ModelUtil.genCommonPropertyTmplDTO(PropertyEnum.FREE_POSTAGE, 0));
        marketTool.getPropertyTmplList().add(ModelUtil.genCommonPropertyTmplDTO(PropertyEnum.GIFT_ITEM_LIST, 0));
    }

    @Autowired
    private PropertyManager propertyManager;

    @Override
    public void init() {
    }

    @Override
    public ComponentHolder getComponentHolder() {
        return null;
    }

    @Override
    public DiscountInfo execute(Context context) throws MarketingException {

        DiscountInfo discountInfo = (DiscountInfo) context.getParam("discountInfo");
        MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
//        GrantedCouponManager grantedCouponManager = (GrantedCouponManager) context.getAttribute("grantedCouponManager");
        Long userId = (Long) context.getParam("userId");
        String appKey = (String) context.getParam("appKey");
        long itemTotalPrice = (Long) context.getParam("itemTotalPrice");

        // 复合活动中最优的子活动
        MarketActivityDTO matchActivity = (MarketActivityDTO) context.getParam("matchActivity");
        Map<String, PropertyDTO> propertyMap = matchActivity.getPropertyMap();

//        Map<String, PropertyDTO> propertyMap = new HashMap<String, PropertyDTO>();
//        for (PropertyDTO propertyDTO : matchActivity.getPropertyList()) {
//            propertyMap.put(propertyDTO.getPkey(), propertyDTO);
//        }

        // 优惠活动优惠门槛无需判断, activityEngine 已经做了处理

        // 用户目前拥有的有效优惠券列表
//        List<GrantedCouponDO> grantedCouponDOs = Collections.emptyList();

//        List usableCoupons = new ArrayList();

        // 如需使用优惠券,需要单独进行判断
//        if (marketActivityDTO.getCouponMark().intValue() == 1) {
//
//            GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
//            grantedCouponQTO.setActivityId(marketActivityDTO.getId());
//            grantedCouponQTO.setReceiverId(userId);
//            grantedCouponDOs = grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);
//
//            if (grantedCouponDOs.isEmpty()) {
//                return null;
//            }
//
//            usableCoupons.addAll(grantedCouponDOs);
//        }

//        discountInfo.setAvailableCoupons(ModelUtil.genGrantedCouponDTOList(usableCoupons, marketActivityDTO.getPropertyList(), marketActivityDTO));
        discountInfo.setActivity(marketActivityDTO);
        discountInfo.setFreePostage(propertyManager.extractPropertyFreePostage(propertyMap));
        discountInfo.setDiscountAmount(propertyManager.extractPropertyQuota(propertyMap));
        discountInfo.setConsume(propertyManager.extractPropertyConsume(propertyMap));
        discountInfo.setGiftList(propertyManager.extractPropertyGiftItemList(propertyMap, appKey));
        // 填充赠送优惠券信息
//        discountInfo.setCouponList(propertyManager.extractPropertyCouponList(propertyMap));

        List<MarketItemDTO> itemDTOs = discountInfo.getItemList();
        if (itemDTOs.isEmpty()) {
            itemDTOs = (List<MarketItemDTO>) context.getParam("itemList");
        }

        cutByQuota(itemDTOs, itemTotalPrice, discountInfo.getDiscountAmount());

        return discountInfo;
    }

    @Override
    public String getToolCode() {
        return ToolType.COMPOSITE_TOOL.getCode();
    }

    @Override
    public MarketToolDTO getMarketTool() {
        return ReachMultipleReduceTool.marketTool;
    }

    /**
     * 根据满减送优惠值在相应范围下减少商品价值
     *
     * @param itemDTOs
     * @param itemTotalPrice
     * @param quota
     * @throws MarketingException
     */
    public void cutByQuota(List<MarketItemDTO> itemDTOs, Long itemTotalPrice, Long quota) throws MarketingException {

        for (MarketItemDTO marketItemDTO : itemDTOs) {
            marketItemDTO.setTotalPrice(marketItemDTO.getTotalPrice()
                    - (long) (marketItemDTO.getTotalPrice().longValue() * 1.0 / itemTotalPrice.longValue() * quota));
        }
    }
}