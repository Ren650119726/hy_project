package com.mockuai.marketingcenter.core.service.action.discountinfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ItemType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.engine.component.impl.ClassifyByItemType;
import com.mockuai.marketingcenter.core.engine.component.impl.DeliveryFee;
import com.mockuai.marketingcenter.core.engine.component.impl.FillUpSkuInfo;
import com.mockuai.marketingcenter.core.engine.component.impl.GetHigoSettlement;
import com.mockuai.marketingcenter.core.engine.component.impl.ItemTotalPrice;
import com.mockuai.marketingcenter.core.engine.component.impl.ValidateSettlementOfCommon;
import com.mockuai.marketingcenter.core.engine.component.impl.ValidateSettlementOfFirstOrder;
import com.mockuai.marketingcenter.core.engine.component.impl.ValidateSettlementOfSeckill;
import com.mockuai.marketingcenter.core.engine.component.impl.ValidateSettlementOfTimeLimit;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;

/**
 * 优惠券(在上述优惠活动之后）
 * (优惠券需要符合以下条件：
 * 一，在有满减送／限时购的情况下，只能有未勾选且满足当前订单的优惠券，
 * 二，在没有满减送／优惠券的情况下，满足当前订单的优惠券都可以，是这样吧
 * )
 * <p/>
 * 首单立减(只要满足首单条件需要列出来，在用户选择完优惠券后，在最后实际支付的金额满足首单时，首单生效。需要前端支持该逻辑)
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 */
@Service
public class GetSettlementInfoAction implements Action<SettlementInfo> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetSettlementInfoAction.class);

	@Autowired
	private ComponentHelper componentHelper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MarketingResponse<SettlementInfo> execute(RequestContext<SettlementInfo> request) throws MarketingException {

		LOGGER.info("  /trade/order/settlement/distribute/get start ");
		
		List<MarketItemDTO> itemList = (List<MarketItemDTO>) request.getRequest().getParam("itemList");
		Long userId = (Long) request.getRequest().getParam("userId");
		Long consigneeId = (Long) request.getRequest().getParam("consigneeId");
		String bizCode = (String) request.get("bizCode");
		String appKey = (String) request.get("appKey");
		BizInfoDTO bizInfo = (BizInfoDTO) request.get("bizInfo");
		MarketPreconditions.checkNotNull(userId, "userId");

		LOGGER.info("userId : {}, consigneeId : {}, itemList : {}, bizCode : {}",
				userId, consigneeId, JsonUtil.toJson(itemList), bizCode);

		// 填充商品数据，以及用户享有的商品折扣等
		Context context = FillUpSkuInfo.wrapParams(itemList, userId, appKey);
		componentHelper.execute(context);

		//划分商品类型
		Map<Integer, List<MarketItemDTO>> itemTypeKeyMarkItemValue = componentHelper.execute(ClassifyByItemType.wrapParams(itemList));

		//填充结算信息
		SettlementInfo settlementInfo = new SettlementInfo();
		settlementInfo.setDiscountInfoList(new ArrayList<DiscountInfo>());
		settlementInfo.setDirectDiscountList(new ArrayList<DiscountInfo>());

		//普通商品(套装不放入 itemList, 换购主商品不能去除)
		List<MarketItemDTO> combine = new ArrayList<>(itemTypeKeyMarkItemValue.get(ItemType.COMMON.getValue()));
		
		//快递费用
		settlementInfo.setDeliveryFee(componentHelper.<Long>execute(DeliveryFee.wrapParams(combine, new ArrayList<MarketItemDTO>(), userId, consigneeId, appKey)));
		
		//限时购结算
		Context contextLimit = ValidateSettlementOfTimeLimit.wrapParams(settlementInfo, combine, appKey, userId);
		componentHelper.execute(contextLimit);
		
		settlementInfo.setItemList(new ArrayList<>(combine));
		
		//排除限时购类型商品
		List<MarketItemDTO> noLimitMarketItemDtoList = new ArrayList<MarketItemDTO>();//不是限时购的商品 
		
		for (MarketItemDTO marketItemDTO:combine) {
			if (!marketItemDTO.getItemType().equals(ItemType.TIME_LIMIT.getValue())) {
				noLimitMarketItemDtoList.add(marketItemDTO);
			}
		}

		LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@{}",JsonUtil.toJson(noLimitMarketItemDtoList));
		
		//计算普通商品总价(普通商品/换购商品的主商品)
		Long totalPrice = componentHelper.<Long>execute(ItemTotalPrice.wrapParams(noLimitMarketItemDtoList));

		settlementInfo.setDiscountAmount(0L);
		settlementInfo.setMemberDiscountAmount((Long) context.getParam("memberDiscountAmount"));
		settlementInfo.setTotalPrice(totalPrice + settlementInfo.getMemberDiscountAmount()+settlementInfo.getTotalPrice());
		settlementInfo.setFreePostage(false);
		settlementInfo.setGiftList(new ArrayList<MarketItemDTO>());
		settlementInfo.setSavedPostage(0L);
		
		LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@{}",JsonUtil.toJson(settlementInfo.getTotalPrice()));
		
		// 普通商品结算
		List<DiscountInfo> limitDiscountList = (List<DiscountInfo>) contextLimit.getParam("limitDiscountList");
		LOGGER.info(" ######## limitDiscountList: {} ",JsonUtil.toJson(limitDiscountList));
		componentHelper.execute(ValidateSettlementOfCommon.wrapParams(settlementInfo, combine, consigneeId, userId, bizCode, appKey,limitDiscountList));

		// 秒杀结算, 单独计算价格，邮费
		componentHelper.execute(ValidateSettlementOfSeckill.wrapParams(settlementInfo,
						itemTypeKeyMarkItemValue.get(ItemType.SECKILL.getValue()), userId, consigneeId, appKey));

		if (!itemTypeKeyMarkItemValue.get(ItemType.DEPOSIT.getValue()).isEmpty())
			settlementInfo.setTotalPrice(itemTypeKeyMarkItemValue.get(ItemType.DEPOSIT.getValue()).get(0).getUnitPrice());

		if (bizInfo.getBizPropertyMap().containsKey("higo_mark")
				    && "1".equals(bizInfo.getBizPropertyMap().get("higo_mark").getValue())) {
			settlementInfo = componentHelper.execute(GetHigoSettlement.wrapParams(settlementInfo, appKey));
		}
		
		// 首单立减
		componentHelper.execute(ValidateSettlementOfFirstOrder.wrapParams(settlementInfo, itemList, userId, appKey));

		LOGGER.info(" @@@@@@@ settlementInfo : "+JSONObject.toJSONString(settlementInfo));
		
		LOGGER.info("  /trade/order/settlement/distribute/get end ");
		
		return new MarketingResponse(settlementInfo);
	}

	public String getName() {
		return ActionEnum.GET_SETTLEMENT_INFO.getActionName();
	}
}