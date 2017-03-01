package com.mockuai.marketingcenter.core.service.action.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.engine.component.impl.FillUpSkuInfo;
import com.mockuai.marketingcenter.core.engine.component.impl.LinkActivityWithItemList;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import com.mockuai.marketingcenter.core.manager.ItemSuitManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.manager.TimeLimitManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;

/**
 * 查询单个商品的优惠活动
 * 换购/套装/满减送/优惠券/限时购
 * Created by edgar.zr on 8/5/15.
 */
@Service
public class QueryItemDiscountInfoAction implements Action<MarketActivityDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryItemDiscountInfoAction.class);

	@Autowired
	private ItemSuitManager itemSuitManager;

	@Autowired
	private ItemManager itemManager;

	@Resource
	private PropertyManager propertyManager;

	@Autowired
	private ComponentHelper componentHelper;
	
	@Resource
	private LimitedPurchaseManager limitedPurchaseManager;
	
	@Resource
	private TimeLimitManager timeLimitManager;

	/**
	 * itemDto 中需要完整参数, itemId, sellerId, unitPrice
	 * <p/>
	 * 无登陆用户也通过此接口查询商品的优惠信心
	 *
	 * @param context
	 * @return
	 * @throws MarketingException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public MarketingResponse<List<DiscountInfo>> execute(RequestContext context) throws MarketingException {

		MarketItemDTO marketItemDTO = (MarketItemDTO) context.getRequest().getParam("marketItemDTO");
		Long userId = (Long) context.getRequest().getParam("userId");
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.getRequest().getParam("appKey");
		
		MarketPreconditions.checkNotNull(marketItemDTO, "marketItemDTO");
		MarketPreconditions.checkNotNull(marketItemDTO.getItemId(), "itemId");
		MarketPreconditions.checkNotNull(marketItemDTO.getSellerId(), "sellerId");

		// 填充商品信息
		componentHelper.execute(FillUpSkuInfo.wrapParams(Arrays.asList(marketItemDTO), userId, appKey));

		List<DiscountInfo> discountInfos = new ArrayList<DiscountInfo>();

		// 包装套装
		discountInfos.addAll(wrapperForSuit(marketItemDTO, userId, appKey));

		List<MarketActivityDTO> marketActivityDTOList =
				componentHelper.execute(LinkActivityWithItemList.wrapParams(
						Arrays.asList(marketItemDTO), bizCode, null, new ArrayList<DiscountInfo>(), appKey));

		if (marketActivityDTOList.isEmpty() && discountInfos.isEmpty()) {
			return new MarketingResponse<>(discountInfos);
		}

		for (MarketActivityDTO marketActivityDTO : marketActivityDTOList) {
			// 换购
			if (marketActivityDTO.getToolCode().equals(ToolType.BARTER_TOOL.getCode())) {
				discountInfos.addAll(wrapperForBarter(marketActivityDTO, appKey));
				continue;
			}
			// 通用版未支持 toolCode 的匹配, itemcenter 需要处理
			if (marketActivityDTO.getCouponMark().intValue() == 1) {
				continue;
			}
			
			// 满减送
			discountInfos.addAll(wrapperForMarketActivityDTO(marketActivityDTO, appKey));
			
			//限时购活动(现在是以普通商品方式处理)
			for(DiscountInfo discountInfo:discountInfos){
				//排除满减送活动商品
				if(null == discountInfo || null == discountInfo.getGiftList() || discountInfo.getGiftList().isEmpty()){
					discountInfos.addAll(wrapperForLimitDTO(Arrays.asList(marketItemDTO)));
				}			
			}			
		}	
		
		return new MarketingResponse<>(discountInfos);
	}
	
	/**
	 * 包装限时购活动内容
	 * 
	 * @param marketItemDTO
	 * @return
	 * @throws MarketingException 
	 */
	private List<DiscountInfo> wrapperForLimitDTO(List<MarketItemDTO> marketItemDTOs) throws MarketingException {		
		if(null == marketItemDTOs){
			return null;
		}
		
		//判断商品是否存在限时购活动中(限时返回数据：1.itemtype为21 2.符合活动条件 3.List<MarketItemDTO>对应活动)
		Map<LimitedPurchaseDTO, List<MarketItemDTO>> limitActivityMap = limitedPurchaseManager.getTimeLimitOfItem(marketItemDTOs);
		
		if(null == limitActivityMap){
			return null;
		}
		
		List<DiscountInfo> discountInfos = new ArrayList<DiscountInfo>();

		// 符合的限时购活动		
		for (Map.Entry<LimitedPurchaseDTO, List<MarketItemDTO>> entry : limitActivityMap.entrySet()) {		
			DiscountInfo discountInfo = new DiscountInfo();
			MarketActivityDTO marketActivityDTO = new MarketActivityDTO();
			discountInfo.setActivity(marketActivityDTO);
			
			Map<String, Object> limitMap = timeLimitManager.limitActivityTag(entry.getKey());

			marketActivityDTO.setIcon(entry.getKey().getActivityTag());//角标
			marketActivityDTO.setToolCode(ToolType.TIME_RANGE_DISCOUNT.getCode());
			marketActivityDTO.setActivityTag(limitMap.get("tag").toString());
			marketActivityDTO.setLimitTagStatus(limitMap.get("tagStatus").toString());
			marketActivityDTO.setLimitTagDate((Long) limitMap.get("tagDate"));
			marketActivityDTO.setId(entry.getKey().getId());//活动id
			
			//是否使用优惠券(0使用1不使用)
			if("0".equals(entry.getKey().getVoucherType().toString())){
				marketActivityDTO.setCouponMark(1);//营销使用优惠券状态(0不使用1使用)
			}else{
				marketActivityDTO.setCouponMark(0);
			}
			
			discountInfo.setItemList(entry.getValue());
			discountInfo.setDiscountAmount(0L);
			
			discountInfos.add(discountInfo);
		}
		
		return discountInfos;
	}
	
	/**
	 * 查询商品的套装活动
	 *
	 * @param marketItemDTO
	 * @return
	 */
	private List<DiscountInfo> wrapperForSuit(MarketItemDTO marketItemDTO, Long userId, String appKey) {
		List<DiscountInfo> discountInfos = new ArrayList<DiscountInfo>();

		ItemQTO itemQTO = new ItemQTO();
		itemQTO.setId(marketItemDTO.getItemId());
		itemQTO.setSellerId(marketItemDTO.getSellerId());
		itemQTO.setItemStatus(4);
		List<ItemDTO> itemDTOs = null;
		try {
			itemDTOs = itemSuitManager.querySuitsByItem(itemQTO, userId, appKey);
		} catch (MarketingException e) {
			LOGGER.error("error to wrap for suit, marketItemDTO : {}, userId : {}, appKey: {}", JsonUtil.toJson(marketItemDTO), e);
		}
		if (itemDTOs == null || itemDTOs.isEmpty()) return discountInfos;
		DiscountInfo discountInfo;
		MarketActivityDTO activityDTO;
		for (ItemDTO itemDTO : itemDTOs) {
			discountInfo = new DiscountInfo();
			discountInfo.setDiscountAmount(itemDTO.getItemSkuDTOList().get(0).getMarketPrice().longValue()
					                               - itemDTO.getItemSkuDTOList().get(0).getPromotionPrice());
			activityDTO = new MarketActivityDTO();
			// 套装内部信息
			activityDTO.setTargetItemList(itemManager.wrapItemWithSkuInfo(itemDTO.getSubItemList()));
			// 套装外部信息
			activityDTO.setActivityItemList(itemManager.wrapItemWithSkuInfo(Arrays.asList(itemDTO)));
			activityDTO.setToolCode("SuitTool");
			discountInfo.setActivity(activityDTO);
			discountInfos.add(discountInfo);
		}
		return discountInfos;
	}

	private List<DiscountInfo> wrapperForMarketActivityDTO(MarketActivityDTO marketActivityDTO, String appKey) throws MarketingException {

		List<DiscountInfo> discountInfos = new ArrayList<DiscountInfo>();
		DiscountInfo discountInfo;
		List<MarketActivityDTO> marketActivityDTOs = new ArrayList<MarketActivityDTO>();
		// 没有子活动则用父节点 否则,只展示子节点
		if (marketActivityDTO.getSubMarketActivityList().isEmpty()) {
			marketActivityDTOs.add(marketActivityDTO);
		} else {
			marketActivityDTOs.addAll(marketActivityDTO.getSubMarketActivityList());
			marketActivityDTO.setSubMarketActivityList(new ArrayList<MarketActivityDTO>());
		}
		for (MarketActivityDTO tempMarketActivityDTO : marketActivityDTOs) {
			discountInfo = new DiscountInfo();
			discountInfo.setSavedPostage(0L);

			MarketActivityDTO dto = new MarketActivityDTO();
			BeanUtils.copyProperties(marketActivityDTO, dto);
			dto.setId(tempMarketActivityDTO.getId());
			dto.setParentId(tempMarketActivityDTO.getParentId());
			dto.setPropertyList(tempMarketActivityDTO.getPropertyList());

			discountInfo.setActivity(dto);

			discountInfo.setConsume(propertyManager.extractPropertyConsume(tempMarketActivityDTO.getPropertyMap()));
			discountInfo.setFreePostage(propertyManager.extractPropertyFreePostage(tempMarketActivityDTO.getPropertyMap()));
			discountInfo.setDiscountAmount(propertyManager.extractPropertyQuota(tempMarketActivityDTO.getPropertyMap()));
			discountInfo.setGiftList(propertyManager.extractPropertyGiftItemList(tempMarketActivityDTO.getPropertyMap(), appKey));
			discountInfos.add(discountInfo);
		}
		Collections.sort(discountInfos, new DiscountInfoComparator());
		LOGGER.debug("discountInfos : {}", JsonUtil.toJson(discountInfos));
		return discountInfos;
	}

	/**
	 * 优惠券信息
	 *
	 * @param marketActivityDTO
	 * @return
	 * @throws MarketingException
	 */
	private DiscountInfo wrapperForActivityCoupon(MarketActivityDTO marketActivityDTO) throws MarketingException {

		DiscountInfo discountInfo = new DiscountInfo();
		Map<String, PropertyDTO> propertyDTOMap = propertyManager.wrapPropertyDTO(marketActivityDTO.getPropertyList());
		discountInfo.setSavedPostage(0L);
		discountInfo.setActivity(marketActivityDTO);
		discountInfo.setConsume(propertyManager.extractPropertyConsume(propertyDTOMap));
		discountInfo.setDiscountAmount(propertyManager.extractPropertyQuota(propertyDTOMap));

		LOGGER.debug("discountInfo of activityCoupon : {}", JsonUtil.toJson(discountInfo));
		return discountInfo;
	}

	/**
	 * 将换购到的商品单独放到 targetItemList 中，不包含主商品列表
	 *
	 * @param marketActivityDTO
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	private List<DiscountInfo> wrapperForBarter(MarketActivityDTO marketActivityDTO, String appKey) throws MarketingException {
		DiscountInfo discountInfo = new DiscountInfo();

		discountInfo.setActivity(marketActivityDTO);
		discountInfo.setConsume(propertyManager.extractPropertyConsume(marketActivityDTO.getPropertyMap()));
		discountInfo.setDiscountAmount(propertyManager.extractPropertyExtra(marketActivityDTO.getPropertyMap()));
		ActivityItemDTO activityItemDTO;
		try {
			activityItemDTO = propertyManager.extractPropertySkuId(marketActivityDTO.getPropertyMap(),
					marketActivityDTO.getCreatorId(), appKey);
			if (activityItemDTO == null) {
				throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
			}
		} catch (MarketingException e) {
			return Collections.emptyList();
		}
		marketActivityDTO.setTargetItemList(Arrays.asList(activityItemDTO));
		return Arrays.asList(discountInfo);
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_DISCOUNTINFO.getActionName();
	}

	private class DiscountInfoComparator implements Comparator<DiscountInfo> {

		@Override
		public int compare(DiscountInfo o1, DiscountInfo o2) {
			if (o1.getConsume().longValue() > o2.getConsume().longValue()) {
				return 1;
			}
			if (o1.getConsume().longValue() < o2.getConsume().longValue()) {
				return -1;
			}
			
			return 0;
		}
	}
}