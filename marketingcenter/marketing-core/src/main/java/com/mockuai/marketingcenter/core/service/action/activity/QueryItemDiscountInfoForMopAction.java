package com.mockuai.marketingcenter.core.service.action.activity;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.util.NumberFormatUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.CouponOpenEnum;
import com.mockuai.marketingcenter.common.constant.LimitTimeActivityStatus;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MopCombineDiscountDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.dto.mop.MopDiscountInfo;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.engine.component.impl.LinkActivityWithItemList;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.DistributorManager;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.manager.TimeLimitManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;

/**
 * Created by edgar.zr on 1/21/16.
 */
@Service
public class QueryItemDiscountInfoForMopAction implements Action<MarketActivityDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryItemDiscountInfoForMopAction.class);

	@Autowired
	private PropertyManager propertyManager;
	@Autowired
	private ComponentHelper componentHelper;
	@Autowired
	private TimeLimitManager timeLimitManager;
	@Autowired
	private ActivityCouponManager activityCouponManager;
	@Resource
	private LimitedPurchaseManager limitedPurchaseManager;	
	
	@Resource
	private DistributorManager distributorManager;
	
	@Resource
	private ItemManager itemManager;

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
	public MarketingResponse<MopCombineDiscountDTO> execute(RequestContext context) throws MarketingException {

		MarketItemDTO marketItemDTO = (MarketItemDTO) context.getRequest().getParam("marketItemDTO");
		LOGGER.info("marketItemDTO : {}", JsonUtil.toJson(marketItemDTO));

		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.getRequest().getParam("appKey");

		MarketPreconditions.checkNotNull(marketItemDTO, "marketItemDTO");
		MarketPreconditions.checkNotNull(marketItemDTO.getItemId(), "itemId");
		MarketPreconditions.checkNotNull(marketItemDTO.getSellerId(), "sellerId");
		MarketPreconditions.checkNotNull(marketItemDTO.getItemType(), "itemType");

		MopCombineDiscountDTO combineDiscountDTO = new MopCombineDiscountDTO();
		combineDiscountDTO.setDiscountInfoList(new ArrayList<MopDiscountInfo>());
		combineDiscountDTO.setBarterList(new ArrayList<MopDiscountInfo>());
		combineDiscountDTO.setCompositeActivityList(new ArrayList<MopDiscountInfo>());
		combineDiscountDTO.setCouponList(new ArrayList<MopDiscountInfo>());
		combineDiscountDTO.setSuitList(new ArrayList<MopDiscountInfo>());
		combineDiscountDTO.setTimeLimitList(new ArrayList<MopDiscountInfo>());

		List<MarketActivityDTO> marketActivityDTOList =
				componentHelper.execute(LinkActivityWithItemList.wrapParams(
						Arrays.asList(marketItemDTO), bizCode, null, new ArrayList<DiscountInfo>(), appKey));

		List<MarketActivityDTO> couponList = new ArrayList<>();

		// 满减送只有一个
		for (MarketActivityDTO marketActivityDTO : marketActivityDTOList) {
			// 通用版老版本为支持 toolCode 的匹配
			if (marketActivityDTO.getCouponMark().intValue() == 1) {
				couponList.add(marketActivityDTO);
				continue;
			}
			// 满减送
			if (marketActivityDTO.getToolCode().equals(ToolType.COMPOSITE_TOOL.getCode())) {
				combineDiscountDTO.getCompositeActivityList().addAll(
						MopApiUtil.genMopDiscountInfoList(wrapperForMarketActivityDTO(marketActivityDTO, bizCode, appKey)));
			}
		}
		//限时购活动处理
		combineDiscountDTO = getLimitActivitys(combineDiscountDTO, marketItemDTO, appKey,bizCode);

//		LOGGER.info("@@@@@@@@@@@@@MopCombineDiscountDTO : {}", JsonUtil.toJson(combineDiscountDTO));

		combineDiscountDTO.getDiscountInfoList().addAll(combineDiscountDTO.getTimeLimitList());
		combineDiscountDTO.getDiscountInfoList().addAll(combineDiscountDTO.getCompositeActivityList());
		combineDiscountDTO.getDiscountInfoList().addAll(combineDiscountDTO.getBarterList());
		combineDiscountDTO.getDiscountInfoList().addAll(combineDiscountDTO.getSuitList());
		
		//去除掉无效优惠券		 
		if(null != couponList && !couponList.isEmpty()){
			filterActivityCoupon(combineDiscountDTO, couponList);
			combineDiscountDTO.getDiscountInfoList().addAll(combineDiscountDTO.getCouponList());
		}		

		return new MarketingResponse<>(combineDiscountDTO);
	}

	private void filterActivityCoupon(MopCombineDiscountDTO combineDiscountDTO, List<MarketActivityDTO> couponList) {
		List<Long> activityIdList = new ArrayList<>();
		Map<Long, MarketActivityDTO> activityIdKey = new HashMap<>();

		for (MarketActivityDTO marketActivityDTO : couponList) {
			activityIdList.add(marketActivityDTO.getId());
			activityIdKey.put(marketActivityDTO.getId(), marketActivityDTO);
		}
		ActivityCouponQTO activityCouponQTO = new ActivityCouponQTO();
		activityCouponQTO.setActivityIdList(activityIdList);
		MarketActivityDTO marketActivityDTO;
		try {
			List<ActivityCouponDO> activityCouponDOs = activityCouponManager.queryActivityCoupon(activityCouponQTO);
			for (ActivityCouponDO activityCouponDO : activityCouponDOs) {
				if (activityCouponDO.getOpen().intValue() == CouponOpenEnum.OPEN.getValue()
						    && activityCouponDO.getTotalCount().longValue() != activityCouponDO.getGrantedCount().longValue()) {
					if (activityCouponDO.getValidDuration() != null) {
						marketActivityDTO = activityIdKey.get(activityCouponDO.getActivityId());
						marketActivityDTO.setStartTime(new Date());
						marketActivityDTO.setEndTime(DateUtils.addDays(marketActivityDTO.getStartTime(), activityCouponDO.getValidDuration()));
					}
					combineDiscountDTO.getCouponList().add(
							MopApiUtil.genMopDiscountInfo(wrapperForActivityCoupon(activityIdKey.get(activityCouponDO.getActivityId()))));
				}
			}
			Collections.sort(combineDiscountDTO.getCouponList(), new CouponComparator());
		} catch (MarketingException e) {
			LOGGER.error("error to query activityCouponDO, ", e);
		}
	}
	
	/**
	 * 得到限时购活动数据
	 * 
	 * @author csy
	 * @Date 2016-10-13
	 * @return
	 * @throws MarketingException 
	 */
	private MopCombineDiscountDTO getLimitActivitys(
			MopCombineDiscountDTO combineDiscountDTO,MarketItemDTO marketItemDTO,String appKey,String bizCode)
			throws MarketingException {
		
		if(null == marketItemDTO){
			return combineDiscountDTO;
		}
		
		//满减送和限时购不能同时存在如果已经有满减活动就直接返回
		if(null != combineDiscountDTO.getCompositeActivityList() && !combineDiscountDTO.getCompositeActivityList().isEmpty()){
			return combineDiscountDTO;
		}
		
		List<MarketItemDTO> marketItemDTOs = new ArrayList<MarketItemDTO>();
		marketItemDTOs.add(marketItemDTO);
		
		//判断商品是否存在限时购活动中(限时返回数据：1.itemtype为21 2.符合活动条件 3.List<MarketItemDTO>对应活动)
		Map<LimitedPurchaseDTO, List<MarketItemDTO>> limitActivityMap = limitedPurchaseManager.getTimeLimitOfItemSkuMsg(marketItemDTOs);
		
		if(null == limitActivityMap){
			return combineDiscountDTO;
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
			
			discountInfo.setItemList(entry.getValue());
			discountInfos.add(discountInfo);			
		}
		
		// 限时购活动进行中收益处理		       
		// 收益比例描述
        String gainPercentDesc = null;
        String gainSharingDesc = null;
        String gains = null;

		for (Map.Entry<LimitedPurchaseDTO, List<MarketItemDTO>> entry : limitActivityMap.entrySet()) {
//			LOGGER.info("limitActivityStatus:{}",entry.getKey().getRunStatus());
			if(LimitTimeActivityStatus.PROCESS.getValue().equals(entry.getKey().getRunStatus())){
				// 一级收益比例（直接上级）
		        BigDecimal oneGains =  new BigDecimal(distributorManager.getGainsSet(appKey).getOneGains()).divide(new BigDecimal(100));
		        
		        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
	            itemSkuQTO.setItemId(marketItemDTO.getItemId());
	            itemSkuQTO.setSellerId(marketItemDTO.getSellerId());
	            // 获取ItemSku列表
	            List<ItemSkuDTO> itemSkuDTOList = itemManager.queryItemDynamic(itemSkuQTO,appKey);

//				LOGGER.info(" @@@@@ itemSkuDTOList-before:{} ",JsonUtil.toJson(itemSkuDTOList));
	            for(ItemSkuDTO itemSkuDTO:itemSkuDTOList){
	            	for(MarketItemDTO marketItemSkuDTO:entry.getValue()){
//	            		LOGGER.info(" @@@@@ marketItemSkuDTO:{} ",JsonUtil.toJson(marketItemSkuDTO));
	            		if(marketItemSkuDTO.getItemSkuId().equals(itemSkuDTO.getId())){
	            			itemSkuDTO.setPromotionPrice(marketItemSkuDTO.getUnitPrice());
	            		}
	            	}
	            }
//				LOGGER.info(" @@@@@ itemSkuDTOList-affter:{} ",JsonUtil.toJson(itemSkuDTOList));
	            gainPercentDesc = this.getNormalItemIntervalGains(marketItemDTO.getItemId(), itemSkuDTOList, oneGains, appKey);
	            Map map = new HashMap();
	            map = this.getNormalIntervalGains(marketItemDTO.getItemId(), itemSkuDTOList, oneGains, appKey);
	            if(null!=map) {
					gainSharingDesc = (String) map.get("gainPercentDesc");
					gains = (String) map.get("gains");
				}
			}
		}		
		combineDiscountDTO.setGainPercentDesc(gainPercentDesc);
		combineDiscountDTO.setGainSharingDesc(gainSharingDesc);
		combineDiscountDTO.setSharingGains(gains);
		
		combineDiscountDTO.setTimeLimitList(MopApiUtil.genMopDiscountInfoList(discountInfos));
		
		return combineDiscountDTO;
	}
	
	public String getNormalItemIntervalGains(Long itemId,
			List<ItemSkuDTO> itemSkuDTOList, BigDecimal oneGains, String appKey)
			throws MarketingException {
		String gainPercentDesc ="";
		List<ItemSkuDistPlanDTO>  skuDistPlanList  = distributorManager.getItemSkuDistPlanList(itemId,appKey);
//		LOGGER.info(" ############ skuDistPlanList:{}  ",JsonUtil.toJson(skuDistPlanList));
		int listSize = skuDistPlanList.size();
		
		Collections.sort(skuDistPlanList,new Comparator<ItemSkuDistPlanDTO>(){
            public int compare(ItemSkuDistPlanDTO arg0, ItemSkuDistPlanDTO arg1) {
                return arg0.getDistGainsRatio().compareTo(arg1.getDistGainsRatio());
            }
        });
		
		// 收益为0不返回
		if(skuDistPlanList.get(0).getDistGainsRatio().equals(skuDistPlanList.get(listSize-1).getDistGainsRatio()) && skuDistPlanList.get(0).getDistGainsRatio().equals(new Double(0))){
			return null;
		}
		
		if(skuDistPlanList.get(0).getDistGainsRatio().equals(skuDistPlanList.get(listSize-1).getDistGainsRatio()) ){
			gainPercentDesc = new BigDecimal(skuDistPlanList.get(0).getDistGainsRatio()).multiply(oneGains).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP)+"%";
		}else{
			gainPercentDesc = new BigDecimal(skuDistPlanList.get(0).getDistGainsRatio()).multiply(oneGains).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP)+"% ~ "+ new BigDecimal(skuDistPlanList.get(listSize-1).getDistGainsRatio()).multiply(oneGains).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) +"%";
		}
        
        return gainPercentDesc;
	}

	public Map getNormalIntervalGains(Long itemId,
			List<ItemSkuDTO> itemSkuDTOList, BigDecimal oneGains, String appKey)
			throws MarketingException {
		String gainPercentDesc ="";
		String gains = "";
		List<ItemSkuDistPlanDTO>  skuDistPlanList  = distributorManager.getItemSkuDistPlanList(itemId,appKey);
//		LOGGER.info(" ############ skuDistPlanList:{}  ",JsonUtil.toJson(skuDistPlanList));
		int listSize = skuDistPlanList.size();

		Collections.sort(skuDistPlanList,new Comparator<ItemSkuDistPlanDTO>(){
            public int compare(ItemSkuDistPlanDTO arg0, ItemSkuDistPlanDTO arg1) {
                return arg0.getDistGainsRatio().compareTo(arg1.getDistGainsRatio());
            }
        });

		// 收益为0不返回
		if(skuDistPlanList.get(0).getDistGainsRatio().equals(skuDistPlanList.get(listSize-1).getDistGainsRatio()) && skuDistPlanList.get(0).getDistGainsRatio().equals(new Double(0))){
			return null;
		}

		if(skuDistPlanList.get(0).getDistGainsRatio().equals(skuDistPlanList.get(listSize-1).getDistGainsRatio()) ){
			gainPercentDesc = new BigDecimal(skuDistPlanList.get(0).getDistGainsRatio()).multiply(new BigDecimal(100)).intValue()+"%";
		}else{
			gainPercentDesc = new BigDecimal(skuDistPlanList.get(0).getDistGainsRatio()).multiply(new BigDecimal(100)).intValue()+"% ~ "
					+ new BigDecimal(skuDistPlanList.get(listSize-1).getDistGainsRatio()).multiply(new BigDecimal(100)).intValue() +"%";
		}

		//商品分享收益区间处理

		Map<Long,ItemSkuDistPlanDTO> itemSkuDistPlanMap = Maps.uniqueIndex(skuDistPlanList,
				new Function<ItemSkuDistPlanDTO, Long>() {//查询所有的收益信息
					@Override
					public Long apply(ItemSkuDistPlanDTO itemSkuDistPlanDTO) {
						return itemSkuDistPlanDTO.getItemSkuId();
					}
				});
		Set<BigDecimal> gainsSet = new TreeSet<BigDecimal>();
		for(ItemSkuDTO itemSkuDTO: itemSkuDTOList){
			BigDecimal sharingGains =  new BigDecimal(itemSkuDistPlanMap.get(itemSkuDTO.getId()).getDistGainsRatio()).
					multiply(new BigDecimal(itemSkuDTO.getPromotionPrice()).multiply(oneGains)).
					divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
			gainsSet.add(sharingGains);

		}

		if(gainsSet.isEmpty()){
			return null;
		}
		Object[] d = gainsSet.toArray();

		if(gainsSet.size()==1){
			gains = NumberFormatUtil.formatTwoDecimalNoZero((BigDecimal)d[0])+"元";
		}else{
			gains = NumberFormatUtil.formatTwoDecimalNoZero((BigDecimal)d[0])+"元~"
					+NumberFormatUtil.formatTwoDecimalNoZero((BigDecimal)d[gainsSet.size()-1])+"元";
		}

		Map map = new HashMap();
		map.put("gainPercentDesc",gainPercentDesc);
		map.put("gains",gains);
        return map;
	}
	

	private List<DiscountInfo> wrapperForMarketActivityDTO(MarketActivityDTO marketActivityDTO, String bizCode, String appKey) throws MarketingException {

		List<DiscountInfo> discountInfos = new ArrayList<DiscountInfo>();
		DiscountInfo discountInfo;
		List<MarketActivityDTO> marketActivityDTOs = new ArrayList<MarketActivityDTO>();

		marketActivityDTOs.addAll(marketActivityDTO.getSubMarketActivityList());
		marketActivityDTO.setSubMarketActivityList(new ArrayList<MarketActivityDTO>());

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
//			LOGGER.info("market : {}", JsonUtil.toJson(tempMarketActivityDTO));
			discountInfo.setCouponList(propertyManager.extractPropertyCouponList(tempMarketActivityDTO.getPropertyMap(), bizCode));
			discountInfos.add(discountInfo);
		}
		Collections.sort(discountInfos, new DiscountInfoComparator());
		// TODO reomve the log
		LOGGER.info("discountInfos : {}", JsonUtil.toJson(discountInfos));
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
		Map<String, PropertyDTO> propertyDTOMap = marketActivityDTO.getPropertyMap();
		discountInfo.setSavedPostage(0L);
		discountInfo.setActivity(marketActivityDTO);
		discountInfo.setConsume(propertyManager.extractPropertyConsume(propertyDTOMap));
		discountInfo.setDiscountAmount(propertyManager.extractPropertyQuota(propertyDTOMap));
		if (discountInfo.getConsume().longValue() == 0) {
			discountInfo.setDesc("无门槛");
		} else {
			discountInfo.setDesc("满" + discountInfo.getConsume() / 100 + "元可用");
		}

		LOGGER.debug("discountInfo of activityCoupon : {}", JsonUtil.toJson(discountInfo));
		return discountInfo;
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_DISCOUNTINFO_MOP.getActionName();
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

	private class CouponComparator implements Comparator<MopDiscountInfo> {

		@Override
		public int compare(MopDiscountInfo o1, MopDiscountInfo o2) {
			if (o1.getDiscountAmount().longValue() > o2.getDiscountAmount().longValue()) {
				return -1;
			}
			if (o1.getDiscountAmount().longValue() < o2.getDiscountAmount().longValue()) {
				return 1;
			}
			return o2.getMarketActivity().getEndTime().compareTo(o2.getMarketActivity().getEndTime());
		}
	}
}