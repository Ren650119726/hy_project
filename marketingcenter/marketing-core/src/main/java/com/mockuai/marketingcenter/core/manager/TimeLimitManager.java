package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.timelimitcenter.common.dto.TimelimitDTO;
import com.mockuai.timelimitcenter.common.qto.TimelimitConflictCheckQTO;

import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 7/18/2016.
 */
public interface TimeLimitManager {

	/**
	 * 结算验证
	 *
	 * @param marketItemDTOs
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	Map<TimelimitDTO, List<MarketItemDTO>> getSettlementOfTimeLimit(List<MarketItemDTO> marketItemDTOs, String appKey) throws MarketingException;

	/**
	 * 查询商品关联的限时购活动
	 *
	 * @param marketItemDTOs
	 * @param appKey
	 * @return
	 */
	Map<TimelimitDTO, List<MarketItemDTO>> getTimeLimitOfItem(List<MarketItemDTO> marketItemDTOs, String appKey)
			throws MarketingException;

	/**
	 * 检查满减送
	 *
	 * @param timelimitConflictCheckQTO
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	Boolean conflictCheckItemInTimelimit(TimelimitConflictCheckQTO timelimitConflictCheckQTO, String appKey) throws MarketingException;
	
	/**
	 * 判断限时标签以及状态和活动时间(活动即将开始、限时购进行中)
	 * 
	 */
	Map<String, Object> limitActivityTag(LimitedPurchaseDTO timelimitDTO) throws MarketingException;
	
	/**
	 * 去除商品同时在满减送又在限时购中
	 * 
	 * @param marketItemDTOs
	 * @param discountInfos
	 * @return
	 * @throws MarketingException
	 */
	List<MarketItemDTO> removeMarketItemDTO(List<MarketItemDTO> marketItemDTOs,	List<DiscountInfo> discountInfos) throws MarketingException;
	
	/**
	 * 判断限时购商品结算限购数量是否符合(购买数量不能大于限购数量)
	 * 
	 * @param marketItemDTOs
	 * @throws MarketingException
	 */
	Boolean judgeBuyNumGtLimitNum(List<MarketItemDTO> marketItemDTOs) throws MarketingException;
}