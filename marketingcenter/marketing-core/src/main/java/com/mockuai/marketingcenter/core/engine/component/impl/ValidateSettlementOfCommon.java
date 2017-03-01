package com.mockuai.marketingcenter.core.engine.component.impl;

import static com.mockuai.marketingcenter.common.constant.ComponentType.VALIDATE_SETTLEMENT_OF_COMMON;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ItemType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.engine.MarketingEngine;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;

/**
 * Created by edgar.zr on 5/21/2016.
 */
@Service
public class ValidateSettlementOfCommon implements Component {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateSettlementOfCommon.class);

	@Autowired
	private MarketingEngine marketingEngine;
	@Autowired
	private ComponentHelper componentHelper;

	public static Context wrapParams(SettlementInfo settlementInfo, List<MarketItemDTO> marketItemDTOs,
	                                 Long consigneeId, Long userId, String bizCode, String appKey,List<DiscountInfo> limitDiscountList) {
		Context context = new Context();
		context.setParam("settlementInfo", settlementInfo);
		context.setParam("marketItemDTOs", marketItemDTOs);
		context.setParam("consigneeId", consigneeId);
		context.setParam("userId", userId);
		context.setParam("bizCode", bizCode);
		context.setParam("appKey", appKey);
		context.setParam("limitDiscountList", limitDiscountList);
		context.setParam("component", VALIDATE_SETTLEMENT_OF_COMMON);
		return context;
	}

	@Override
	public void init() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T execute(Context context) throws MarketingException {
		LOGGER.info("普通商品结算开始");

		SettlementInfo settlementInfo = (SettlementInfo) context.getParam("settlementInfo");
		List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getParam("marketItemDTOs");
		Long userId = (Long) context.getParam("userId");
		String bizCode = (String) context.getParam("bizCode");
		String appKey = (String) context.getParam("appKey");
		Long consigneeId = (Long) context.getParam("consigneeId");
		List<DiscountInfo> limitDiscountList = (List<DiscountInfo>)context.getParam("limitDiscountList");

		LOGGER.debug("settlement : {}, marketItemDTOs : {}", JsonUtil.toJson(settlementInfo), JsonUtil.toJson(marketItemDTOs));

		try {		
			marketingEngine.execute(settlementInfo, marketItemDTOs, userId, bizCode, appKey,limitDiscountList);
			List<MarketItemDTO> excludeMarkItemDTO = new ArrayList<MarketItemDTO>();
			// 免邮
			if (settlementInfo.isFreePostage() && consigneeId != null) {
				Boolean wholeShop = false;
				for (DiscountInfo discountInfo : settlementInfo.getDirectDiscountList()) {
					if (!discountInfo.isFreePostage()) {
						continue;
					}
					// 全店范围的优惠(目前是单店铺，默认就是全部订单免邮)
					if (discountInfo.getActivity().getScope() == ActivityScope.SCOPE_SHOP.getValue()) {
						settlementInfo.setSavedPostage(settlementInfo.getDeliveryFee());
						settlementInfo.setDeliveryFee(0L);
						wholeShop = true;
						break;
					}
					// 指定商品
					if (discountInfo.getActivity().getScope().intValue() == ActivityScope.SCOPE_ITEM.getValue()) {
						excludeMarkItemDTO.addAll(discountInfo.getItemList());
					}
				}
				if (!wholeShop && excludeMarkItemDTO.size() > 0) {
					Long deliveryFee = componentHelper.execute(
							DeliveryFee.wrapParams(marketItemDTOs, excludeMarkItemDTO, userId, consigneeId, appKey));
					settlementInfo.setSavedPostage(settlementInfo.getDeliveryFee() - deliveryFee.longValue());
					settlementInfo.setDeliveryFee(deliveryFee);
				}
			}
			LOGGER.info("普通商品结算结束");
		} catch (MarketingException e) {
			LOGGER.error("error of deal with settlementInfo in marketingEngine, settlementInfo : {}, itemList : {}, userId : {}, bizCode : {}",
					settlementInfo, marketItemDTOs, userId, bizCode, e);
			// 降级错误处理,将购物结算历程走完
		}
		return null;
	}

	@Override
	public String getComponentCode() {
		return VALIDATE_SETTLEMENT_OF_COMMON.getCode();
	}
}