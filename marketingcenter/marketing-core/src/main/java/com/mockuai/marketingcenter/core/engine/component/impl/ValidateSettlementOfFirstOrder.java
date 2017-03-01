package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.FirstOrderManager;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mockuai.marketingcenter.common.constant.ComponentType.VALIDATE_SETTLEMENT_OF_FIRST_ORDER;

/**
 * Created by edgar.zr on 7/18/2016.
 */
@Service
public class ValidateSettlementOfFirstOrder implements Component {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateSettlementOfFirstOrder.class);

	@Autowired
	private FirstOrderManager firstOrderManager;

	public static Context wrapParams(SettlementInfo settlementInfo, List<MarketItemDTO> marketItemDTOs,
	                                 Long userId, String appKey) {
		Context context = new Context();
		context.setParam("settlementInfo", settlementInfo);
		context.setParam("marketItemDTOs", marketItemDTOs);
		context.setParam("userId", userId);
		context.setParam("appKey", appKey);
		context.setParam("component", VALIDATE_SETTLEMENT_OF_FIRST_ORDER);
		return context;
	}

	@Override
	public void init() {

	}

	@Override
	public <T> T execute(Context context) throws MarketingException {
		LOGGER.info("首单立减开始");
		
		SettlementInfo settlementInfo = (SettlementInfo) context.getParam("settlementInfo");
		List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getParam("marketItemDTOs");
		Long userId = (Long) context.getParam("userId");
		String appKey = (String) context.getParam("appKey");

		HeadSingleSubDTO headSingleSubDTO = firstOrderManager.getSettlementForFirstOrder(marketItemDTOs, userId, appKey);
		// TODO switch to debug
		LOGGER.info("headSingleSubDTO : {}, marketItemDTOs : {}, userId : {}, appKey : {}",
				JsonUtil.toJson(headSingleSubDTO), JsonUtil.toJson(marketItemDTOs), userId, appKey);
		if (headSingleSubDTO == null) {
			LOGGER.info("headSingleSubDTO is not avaliable");
			return null;
		}

		DiscountInfo discountInfo = new DiscountInfo();
		MarketActivityDTO marketActivityDTO = new MarketActivityDTO();
		marketActivityDTO.setToolCode(ToolType.FIRST_ORDER_DISCOUNT.getCode());
		marketActivityDTO.setDistributable(headSingleSubDTO.getDiscomStatus());
		marketActivityDTO.setDiscountAmount(headSingleSubDTO.getPrivilegeAmt());
		marketActivityDTO.setStatus(headSingleSubDTO.getOpenStatus());
		marketActivityDTO.setId(headSingleSubDTO.getId());
		discountInfo.setConsume(headSingleSubDTO.getLimitFullAmt());
		discountInfo.setDiscountAmount(headSingleSubDTO.getPrivilegeAmt());
		discountInfo.setActivity(marketActivityDTO);
		settlementInfo.getDirectDiscountList().add(discountInfo);
		LOGGER.info("首单立减结束");
		return null;
	}

	@Override
	public String getComponentCode() {
		return VALIDATE_SETTLEMENT_OF_FIRST_ORDER.getCode();
	}
}