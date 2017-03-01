package com.mockuai.marketingcenter.core.engine.tool.impl;

import com.mockuai.marketingcenter.common.constant.PropertyEnum;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketToolDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyTmplDTO;
import com.mockuai.marketingcenter.core.engine.component.ComponentHolder;
import com.mockuai.marketingcenter.core.engine.tool.Tool;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReachReduceTool implements Tool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReachReduceTool.class);

    private static final MarketToolDTO marketTool;

    static {
        marketTool = new MarketToolDTO();
        marketTool.setBizCode("mockuai");
        marketTool.setDeleteMark(0);
        marketTool.setImplContent("");
        marketTool.setImplType(1);
        marketTool.setType(ToolType.SIMPLE_TOOL.getValue());
        marketTool.setToolCode(ToolType.SIMPLE_TOOL.getCode());
        marketTool.setProviderId(1L);
        marketTool.setProviderType(1);
        marketTool.setToolName("优惠券工具");
        marketTool.setStatus(0);
        marketTool.setParentId(0L);

        marketTool.setPropertyTmplList(new ArrayList<PropertyTmplDTO>());
        marketTool.getPropertyTmplList().add(ModelUtil.genCommonPropertyTmplDTO(PropertyEnum.CONSUME, 1));
        marketTool.getPropertyTmplList().add(ModelUtil.genCommonPropertyTmplDTO(PropertyEnum.QUOTA, 1));
    }

    public void init() {
    }

    public ComponentHolder getComponentHolder() {
        return null;
    }

    public DiscountInfo execute(Context context) throws MarketingException {

        DiscountInfo discountInfo = (DiscountInfo) context.getParam("discountInfo");
        MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
        Long userId = (Long) context.getParam("userId");
        Long itemTotalPrice = (Long) context.getParam("itemTotalPrice");

        long consume = Long.valueOf(marketActivityDTO.getPropertyMap().get(PropertyDTO.CONSUME).getValue());
        long quota = Long.valueOf(marketActivityDTO.getPropertyMap().get(PropertyDTO.QUOTA).getValue());

        LOGGER.info("userId : {}, itemTotalPrice : {}, consume : {}, quota : {}", userId, itemTotalPrice, consume, quota);

        // 没有达到此优惠活动的门槛
        if (itemTotalPrice < consume) {
            return null;
        }

        List<GrantedCouponDTO> availableCoupons = Arrays.asList(marketActivityDTO.getGrantedCouponDTO());
        StringBuilder sb;

        if (consume == 0) {
            marketActivityDTO.getGrantedCouponDTO().setContent("无门槛");
        } else {
            marketActivityDTO.getGrantedCouponDTO().setContent("满" + consume / 100 + "元可用");
        }

        discountInfo.setAvailableCoupons(availableCoupons);
        discountInfo.setActivity(marketActivityDTO);
        // 每个订单只能使用一张优惠券
        discountInfo.setDiscountAmount(Long.valueOf(quota));
        discountInfo.setConsume(Long.valueOf(consume));
        discountInfo.setFreePostage(false);
        return discountInfo;
    }

    @Override
    public String getToolCode() {
        return ToolType.SIMPLE_TOOL.getCode();
    }

    @Override
    public MarketToolDTO getMarketTool() {
        return ReachReduceTool.marketTool;
    }
}