package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.LimitTimeActivityStatus;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.TimeLimitManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.timelimitcenter.common.dto.TimelimitDTO;
import com.mockuai.timelimitcenter.common.qto.TimelimitConflictCheckQTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 7/18/2016.
 */
public class TimeLimitManagerImpl implements TimeLimitManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeLimitManagerImpl.class);

	@Override
	public Map<TimelimitDTO, List<MarketItemDTO>> getSettlementOfTimeLimit(List<MarketItemDTO> marketItemDTOs, String appKey) throws MarketingException {
		return null;
	}

	@Override
	public Map<TimelimitDTO, List<MarketItemDTO>> getTimeLimitOfItem(List<MarketItemDTO> marketItemDTOs, String appKey) throws MarketingException {
		return null;
	}

	@Override
	public Boolean conflictCheckItemInTimelimit(TimelimitConflictCheckQTO timelimitConflictCheckQTO, String appKey) throws MarketingException {
		return null;
	}

	@Override
	public Map<String, Object> limitActivityTag(LimitedPurchaseDTO timelimitDTO) throws MarketingException {
		if(null == timelimitDTO){
			return null;
		}
		
		Map<String, Object> limitMap = new HashMap<String, Object>();
		Date activityStart = timelimitDTO.getStartTime();//开始时间
		Date activityEnd = timelimitDTO.getEndTime();//结束时间
		Date nowDate =  new Date();//现在时间
		
		if(activityStart.after(nowDate)){
			Long dateCom = computationTime(nowDate, activityStart);
			
			limitMap.put("tag", "活动即将开始");
			limitMap.put("tagStatus", LimitTimeActivityStatus.NOBEGIN.getValue());
			limitMap.put("tagDate", dateCom);
			
			return limitMap;
		}else if(activityEnd.after(nowDate) && nowDate.after(activityStart)){
			Long dateCom = computationTime(nowDate, activityEnd);
			
			limitMap.put("tag", "限时购进行中");
			limitMap.put("tagStatus", LimitTimeActivityStatus.PROCESS.getValue());
			limitMap.put("tagDate", dateCom);
			
			return limitMap;
		}else if(nowDate.after(activityEnd)){
			limitMap.put("tag", "活动已经结束");
			limitMap.put("tagStatus", LimitTimeActivityStatus.ALREADYOVER.getValue());
			limitMap.put("tagDate", 0L);
			
			return limitMap;
		}
		
		return null;
	}
	
	/**
	 * 去除商品同时在满减送又在限时购中
	 * 
	 * @author csy
	 * @param marketItemDTOs
	 * @return
	 */
	public List<MarketItemDTO> removeMarketItemDTO(List<MarketItemDTO> marketItemDTOs, 
			List<DiscountInfo> discountInfos) throws MarketingException{		
		//限时购和满减送不可同时存在(满减送优先有争议可以变更)
		List<MarketItemDTO> mjMarketItemDTOs= new ArrayList<MarketItemDTO>();
		
		if(null != discountInfos && !discountInfos.isEmpty()){
			for(DiscountInfo discountInfo:discountInfos){
				if(null == discountInfo.getItemList() || discountInfo.getItemList().isEmpty()){
					continue;
				}
				
				mjMarketItemDTOs.addAll(discountInfo.getItemList());
			}
		}	
		
		//排除满减送中的商品
		List<MarketItemDTO> marItemDtos = new ArrayList<MarketItemDTO>();
		List<Long> itemIdList = new ArrayList<Long>();
		
		if(null == mjMarketItemDTOs || mjMarketItemDTOs.isEmpty()){
			return marketItemDTOs;
		}
		
		for(MarketItemDTO mjMarketItemDTO:mjMarketItemDTOs){
			itemIdList.add(mjMarketItemDTO.getItemId());			
		}
		
		if(null == itemIdList || itemIdList.isEmpty()){
			return marketItemDTOs;
		}
		
		//如果在满减送活动中包含就把商品去掉
		for(MarketItemDTO marketItemDTO:marketItemDTOs){
			if(!itemIdList.contains(marketItemDTO.getItemId())){
				marItemDtos.add(marketItemDTO);
			}
		}
		
		if(null != marItemDtos && !marItemDtos.isEmpty()){
			marketItemDTOs = marItemDtos;
		}
		
		return marketItemDTOs;
	}
	
	/**
	 * 判断限时购商品结算限购数量是否符合(购买数量不能大于限购数量)
	 * 
	 * @author csy
	 * @Date 2016-11-07
	 * 
	 */
	public Boolean judgeBuyNumGtLimitNum(List<MarketItemDTO> marketItemDTOs) throws MarketingException{
		if(null == marketItemDTOs || marketItemDTOs.isEmpty()){
			return null;
		}
		LOGGER.info("goodsNumber :{}", JsonUtil.toJson(marketItemDTOs));
		for(MarketItemDTO marketItemDTO:marketItemDTOs){
			if("0".equals(marketItemDTO.getLimitNumber().toString())){
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * 距离开始结束时间差
	 * 
	 * @author csy
	 * @return
	 */
	private long computationTime(Date dateOne, Date dateTwo){
		if(null == dateOne || null == dateTwo || "".equals(dateOne) || "".equals(dateTwo)){
			return 0L;
		}
		
		//距离时间
		long dateComputation = Math.abs(dateOne.getTime()-dateTwo.getTime());
		
		//转换时间格式
		Date dateCom = new Date(dateComputation);
		
		return dateCom.getTime();
	}
}