package com.mockuai.marketingcenter.core.engine.component.impl;

import static com.mockuai.marketingcenter.common.constant.ComponentType.VALIDATE_SETTLEMENT_OF_TIME_LIMIT;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.marketingcenter.common.constant.CommonItemEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.manager.TimeLimitManager;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;

/**
 * 限时购结算
 * <p/>
 * Created by edgar.zr on 7/18/2016.
 */
@Service
public class ValidateSettlementOfTimeLimit implements Component {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateSettlementOfTimeLimit.class);
	
	@Resource
	private LimitedPurchaseManager limitedPurchaseManager;
	
	@Resource
	private TimeLimitManager timeLimitManager;
	
	@Autowired
	private ComponentHelper componentHelper;

	public static Context wrapParams(SettlementInfo settlementInfo, List<MarketItemDTO> marketItemDTOs, String appKey
			,Long userId) {
		Context context = new Context();
		context.setParam("settlementInfo", settlementInfo);
		context.setParam("marketItemDTOs", marketItemDTOs);
		context.setParam("appKey", appKey);
		context.setParam("userId", userId);
		context.setParam("component", VALIDATE_SETTLEMENT_OF_TIME_LIMIT);
		return context;
	}

	@Override
	public void init() {

	}

	@Override
	public <T> T execute(Context context) throws MarketingException {		
		List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getParam("marketItemDTOs");
		SettlementInfo settlementInfo = (SettlementInfo) context.getParam("settlementInfo");
		String appKey = (String) context.getParam("appKey");
		Long userId = (Long) context.getParam("userId");
		
		//复制原商品保留原商品价格用于计算原价
		List<MarketItemDTO> marketItems = new ArrayList<MarketItemDTO>();
		marketItems.addAll(marketItemDTOs);

		//判断商品是否存在限时购活动中(限时返回数据：1.itemtype为21 2.符合活动条件 3.List<MarketItemDTO>对应活动)
		Map<LimitedPurchaseDTO, List<MarketItemDTO>> limitActivityMap = limitedPurchaseManager.getIteminTimeLimit(marketItemDTOs, userId);
		LOGGER.info("marketItemDTOs : {}, map : {}",JsonUtil.toJson(marketItemDTOs), JsonUtil.toJson(limitActivityMap));
		
		if(null == limitActivityMap){
			return null;
		}		

		DiscountInfo discountInfo;
		MarketActivityDTO marketActivityDTO;
		LimitedPurchaseDTO limitedPurchaseDTO;
		List<MarketItemDTO> limitItemPriceList= new ArrayList<MarketItemDTO>();
		
		for (Map.Entry<LimitedPurchaseDTO, List<MarketItemDTO>> entry : limitActivityMap.entrySet()) {
			limitedPurchaseDTO = entry.getKey();
			discountInfo = new DiscountInfo();
			//判断限时标签		
			Map<String, Object> limitMap = timeLimitManager.limitActivityTag(entry.getKey());
			
			marketActivityDTO = new MarketActivityDTO();
			marketActivityDTO.setToolCode(ToolType.TIME_RANGE_DISCOUNT.getCode());
			marketActivityDTO.setStartTime(limitedPurchaseDTO.getStartTime());
			marketActivityDTO.setEndTime(limitedPurchaseDTO.getEndTime());
			marketActivityDTO.setDiscountAmount(0L);
			marketActivityDTO.setIcon(limitedPurchaseDTO.getActivityTag());
			marketActivityDTO.setActivityName(limitedPurchaseDTO.getActivityName());
			marketActivityDTO.setActivityTag((String) limitMap.get("tag"));
			marketActivityDTO.setId(entry.getKey().getId());//活动id
			marketActivityDTO.setLimitTagStatus(limitMap.get("tagStatus").toString());
			marketActivityDTO.setLimitTagDate((Long) limitMap.get("tagDate"));
			//是否使用优惠券(0使用1不使用)
			if("0".equals(entry.getKey().getVoucherType().toString())){
				marketActivityDTO.setCouponMark(1);//营销使用优惠券状态(0不使用1使用)
			}else{
				marketActivityDTO.setCouponMark(0);
			}			
			
			discountInfo.setActivity(marketActivityDTO);			
			
			//判断限时购商品结算限购数量是否符合
			Boolean flag=timeLimitManager.judgeBuyNumGtLimitNum(entry.getValue());
			
			if(false == flag){
				throw new MarketingException(ResponseCode.PARAMETER_ERROR,"您已超过限购数，请活动结束后再购买");
			}		
			
			discountInfo.setItemList(entry.getValue());
			discountInfo.setDiscountAmount(0L);
			limitItemPriceList.addAll(entry.getValue());
			
			settlementInfo.getDirectDiscountList().add(discountInfo);
		}
		
		//重新计算限时购价格(走特殊处理因为限时购是价格修改不是优惠金额)
		if(null == limitItemPriceList || limitItemPriceList.isEmpty()){
			return null;
		}		
		
		//限时购活动价
		Long limitTotalPrice = componentHelper.<Long>execute(ItemTotalPrice.wrapParams(limitItemPriceList));
		
		settlementInfo.setTotalPrice(limitTotalPrice);
		
		context.setParam("limitDiscountList", settlementInfo.getDirectDiscountList());
		
		return null;
	}
	
	/**
	 * 获取原商品价格
	 * 
	 * @author csy
	 * @return
	 * @throws MarketingException 
	 */
	private Long getLimitPriginalPrice(List<MarketItemDTO> marketItemDTOs, List<MarketItemDTO> limitItemPriceList) throws MarketingException{
		Long priginalPrice = 0L;
		LOGGER.error("limitTotalPrice进入");
		if(marketItemDTOs.isEmpty() || limitItemPriceList.isEmpty()){
			return priginalPrice;
		}
		
		List<Long> limitItemIdList = new ArrayList<Long>();
		
		for(MarketItemDTO marketItemDTO:limitItemPriceList){
			limitItemIdList.add(marketItemDTO.getItemSkuId());
		}
		
		if(null == limitItemIdList || limitItemIdList.isEmpty()){
			return priginalPrice;
		}
		
		List<MarketItemDTO> limitPriceList = new ArrayList<MarketItemDTO>();
		
		for(MarketItemDTO MarketItemDTO:marketItemDTOs){
			if(limitItemIdList.contains(MarketItemDTO.getItemSkuId())){
				limitPriceList.add(MarketItemDTO);
			}
		}
		
		priginalPrice = componentHelper.<Long>execute(ItemTotalPrice.wrapParams(limitPriceList));
		LOGGER.error("priginalPrice进入"+priginalPrice);
		return priginalPrice;
	}

	@Override
	public String getComponentCode() {
		return VALIDATE_SETTLEMENT_OF_TIME_LIMIT.getCode();
	}
}