package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.SeckillManager;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mockuai.marketingcenter.common.constant.ComponentType.VALIDATE_SETTLEMENT_OF_SECKILL;

/**
 * Created by edgar.zr on 2/02/2016.
 */
@Service
public class ValidateSettlementOfSeckill implements Component {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateSettlementOfSeckill.class);

	@Autowired
	private SeckillManager seckillManager;
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
		context.setParam("component", VALIDATE_SETTLEMENT_OF_SECKILL);
		return context;
	}

	@Override
	public void init() {

	}

	@Override
	public Void execute(Context context) throws MarketingException {
		LOGGER.info("秒杀结算开始");
		
		SettlementInfo settlementInfo = (SettlementInfo) context.getParam("settlementInfo");
		List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getParam("marketItemDTOs");
		Long userId = (Long) context.getParam("userId");
		Long consigneeId = (Long) context.getParam("consigneeId");
		String appKey = (String) context.getParam("appKey");

		if (marketItemDTOs == null || marketItemDTOs.isEmpty()) return null;
		MarketItemDTO marketItemDTO = marketItemDTOs.get(0);
		MarketItemDTO gbMarketItemDTO = new MarketItemDTO();

		SeckillDTO seckillDTO =
				seckillManager.validateForSettlement(marketItemDTO.getItemSkuId(), userId, marketItemDTO.getSellerId(), appKey);

		if (seckillDTO == null) {
			LOGGER.error("error to query seckill, it does not exist, marketItemDTO : {}",
					JsonUtil.toJson(marketItemDTO));
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "秒杀不存在");
		}

		settlementInfo.setItemList(new ArrayList<MarketItemDTO>());
		settlementInfo.getItemList().add(gbMarketItemDTO);
		gbMarketItemDTO.setIconUrl(marketItemDTO.getIconUrl());
		gbMarketItemDTO.setNumber(marketItemDTO.getNumber());
		gbMarketItemDTO.setUnitPrice(marketItemDTO.getUnitPrice());
		gbMarketItemDTO.setOriginTotalPrice(seckillDTO.getMarketPrice());
		gbMarketItemDTO.setItemType(marketItemDTO.getItemType());
		gbMarketItemDTO.setItemId(marketItemDTO.getItemId());
		gbMarketItemDTO.setItemName(seckillDTO.getItemName());
		gbMarketItemDTO.setItemSkuId(seckillDTO.getSkuId());
		gbMarketItemDTO.setSellerId(seckillDTO.getSellerId());
		gbMarketItemDTO.setHigoMark(marketItemDTO.getHigoMark());
		gbMarketItemDTO.setDeliveryType(marketItemDTO.getDeliveryType());
		gbMarketItemDTO.setVirtualMark(marketItemDTO.getVirtualMark());
		gbMarketItemDTO.setSkuSnapshot(marketItemDTO.getSkuSnapshot());
		gbMarketItemDTO.setDistributorId(marketItemDTO.getDistributorId());
		gbMarketItemDTO.setDistributorInfoDTO(marketItemDTO.getDistributorInfoDTO());

		settlementInfo.setTotalPrice(gbMarketItemDTO.getUnitPrice());
		settlementInfo.setDeliveryFee(
				componentHelper.<Long>execute(
						DeliveryFee.wrapParams(marketItemDTOs, new ArrayList<MarketItemDTO>(), userId, consigneeId, appKey)));
		LOGGER.info("settlement in seckill : {}", JsonUtil.toJson(settlementInfo));
		LOGGER.info("秒杀结算结束");
		return null;
	}

	@Override
	public String getComponentCode() {
		return VALIDATE_SETTLEMENT_OF_SECKILL.getCode();
	}
}