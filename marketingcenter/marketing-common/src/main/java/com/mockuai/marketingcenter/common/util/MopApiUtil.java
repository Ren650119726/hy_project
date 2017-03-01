package com.mockuai.marketingcenter.common.util;

import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.*;
import com.mockuai.marketingcenter.common.domain.dto.mop.*;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopWealthAccountDTO;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MopApiUtil {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat dateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");

	public static Long parseConsigneeId(String consigneeUid) {
		if (consigneeUid == null) {
			return null;
		}
		String[] strs = consigneeUid.split("_");
		if (strs.length != 2) {
			return null;
		}
		return Long.valueOf(strs[1]);
	}

	public static Long parseCouponId(String couponUid) {
		if (couponUid == null) {
			return null;
		}
		String[] strs = couponUid.split("_");
		if (strs.length != 2) {
			return null;
		}
		return Long.valueOf(strs[1]);
	}

	public static List<ActivityCouponUidDTO> parseActivityCouponUidList(List<String> activityCouponUidStrList) {
		if (activityCouponUidStrList == null || activityCouponUidStrList.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<ActivityCouponUidDTO> activityCouponUidList = new ArrayList<ActivityCouponUidDTO>();
		ActivityCouponUidDTO activityCouponUidDTO;
		for (String activityCouponUidStr : activityCouponUidStrList) {
			activityCouponUidDTO = parseActivityCouponUid(activityCouponUidStr);
			if (activityCouponUidDTO != null) {
				activityCouponUidList.add(activityCouponUidDTO);
			}
		}
		return activityCouponUidList;
	}

	public static ActivityCouponUidDTO parseActivityCouponUid(String activityCouponUid) {
		if (activityCouponUid == null) {
			return null;
		}

		String[] strs = activityCouponUid.split("_");
		if (strs.length != 2) {
			return null;
		}

		ActivityCouponUidDTO activityCouponUidDTO = new ActivityCouponUidDTO();

		long creatorId = Long.parseLong(strs[0]);
		long activityCouponId = Long.parseLong(strs[1]);
		activityCouponUidDTO.setCreatorId(creatorId);
		activityCouponUidDTO.setActivityCouponId(activityCouponId);
		return activityCouponUidDTO;
	}

	public static UserCouponUidDTO parseUserCouponUid(String userCouponUid) {
		if (userCouponUid == null) {
			return null;
		}

		String[] strs = userCouponUid.split("_");
		if (strs.length != 2) {
			return null;
		}

		UserCouponUidDTO userCouponUidDTO = new UserCouponUidDTO();

		long userId = Long.parseLong(strs[0]);
		long userCouponId = Long.parseLong(strs[1]);
		userCouponUidDTO.setUserId(userId);
		userCouponUidDTO.setUserCouponId(userCouponId);
		return userCouponUidDTO;
	}

	public static String genActivityCouponUid(ActivityCouponDTO activityCouponDTO) {
		if (activityCouponDTO == null) {
			return null;
		}

		return activityCouponDTO.getActivityCreatorId() + "_" + activityCouponDTO.getId();
	}

	public static String genActivityUid(MarketActivityDTO marketActivityDTO) {
		if (marketActivityDTO == null) {
			return null;
		}

		return marketActivityDTO.getCreatorId() + "_" + marketActivityDTO.getId();
	}

	public static ActivityUidDTO parseActivityUid(String activityUid) {
		if (activityUid == null) {
			return null;
		}

		String[] strs = activityUid.split("_");
		if (strs.length != 2) {
			return null;
		}

		ActivityUidDTO activityUidDTO = new ActivityUidDTO();
		long creatorId = Long.parseLong(strs[0]);
		long activityId = Long.parseLong(strs[1]);
		activityUidDTO.setCreatorId(creatorId);
		activityUidDTO.setActivityId(activityId);
		return activityUidDTO;
	}

	public static List<MopWealthAccountDTO> genMopWealthAccountDTOList(List<WealthAccountDTO> wealthAccountDTOs) {
		if (wealthAccountDTOs == null || wealthAccountDTOs.isEmpty())
			return Collections.EMPTY_LIST;

		List<MopWealthAccountDTO> mopWealthAccountDTOs = new ArrayList<MopWealthAccountDTO>();
		MopWealthAccountDTO mopWealthAccountDTO;
		for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs) {
			mopWealthAccountDTO = genMopWealthAccountDTO(wealthAccountDTO);
			if (mopWealthAccountDTO != null)
				mopWealthAccountDTOs.add(mopWealthAccountDTO);
		}
		return mopWealthAccountDTOs;
	}

	public static MopWealthAccountDTO genMopWealthAccountDTO(WealthAccountDTO wealthAccountDTO) {
		MopWealthAccountDTO mopWealthAccountDTO = new MopWealthAccountDTO();
		mopWealthAccountDTO.setWealthType(wealthAccountDTO.getWealthType());
		mopWealthAccountDTO.setAmount(wealthAccountDTO.getAmount());
		mopWealthAccountDTO.setMobile(wealthAccountDTO.getMobile());
		if (wealthAccountDTO.getExchangeRate() != null)
			mopWealthAccountDTO.setExchangeRate("" + wealthAccountDTO.getExchangeRate());
		mopWealthAccountDTO.setUpperLimit(wealthAccountDTO.getUpperLimit());
		mopWealthAccountDTO.setWealthAccountUid(wealthAccountDTO.getUserId() + "_" + wealthAccountDTO.getId());
		return mopWealthAccountDTO;
	}

	public static List<MarketItemDTO> genMarketingItemDTOList(List<ParamMarketItemDTO> paramMarketItemDTOs) {
		List<MarketItemDTO> marketItemDTOs = new ArrayList<MarketItemDTO>();
		if (paramMarketItemDTOs == null || paramMarketItemDTOs.size() == 0) {
			return marketItemDTOs;
		}
		MarketItemDTO marketItemDTO;
		for (ParamMarketItemDTO paramMarketItemDTO : paramMarketItemDTOs) {
			marketItemDTO = getMarketingItemDTO(paramMarketItemDTO);
			if (marketItemDTO != null)
				marketItemDTOs.add(marketItemDTO);
		}
		return marketItemDTOs;
	}

	public static MarketItemDTO getMarketingItemDTO(ParamMarketItemDTO paramMarketItemDTO) {
		MarketItemDTO marketItemDTO = new MarketItemDTO();
		String skuUid = paramMarketItemDTO.getItemSkuUid();
		if (StringUtils.isBlank(skuUid)) return null;
		SkuUidDTO skuUidDTO = parseSkuUid(skuUid);
		marketItemDTO.setServices(getMarketValueAddedServiceDTOList(paramMarketItemDTO.getServiceList()));
		marketItemDTO.setItemType(paramMarketItemDTO.getItemType());
		marketItemDTO.setItemSkuId(skuUidDTO.getSkuId());
		marketItemDTO.setSellerId(skuUidDTO.getSellerId());
		marketItemDTO.setDistributorId(paramMarketItemDTO.getDistributorId());
		marketItemDTO.setNumber(paramMarketItemDTO.getNumber());
		marketItemDTO.setShareUserId(paramMarketItemDTO.getShareUserId());
		System.out.println("营销获取信息内容："+marketItemDTO.getShareUserId());
//        marketItemDTO.setActivityInfo(getActivityInfo(paramMarketItemDTO.getActivityInfo()));
		return marketItemDTO;
	}

//    public static ActivityInfo getActivityInfo(MopActivityInfo mopActivityInfo) {
//        if (mopActivityInfo == null) return null;
//        ActivityInfo activityInfo = new ActivityInfo();
//        ActivityUidDTO activityUidDTO = parseActivityUid(mopActivityInfo.getActivityUid());
//        if (activityUidDTO == null) return null;
//        activityInfo.setActivityId(activityUidDTO.getActivityId());
//        activityInfo.setItemDTOs(genMarketingItemDTOList(mopActivityInfo.getItemList()));
//        return activityInfo;
//    }

	public static SkuUidDTO parseSkuUid(String skuUid) {
		SkuUidDTO skuUidDTO = new SkuUidDTO();
		String[] strs = skuUid.split("_");
		if (strs.length != 2) ;
		long sellerId = Long.parseLong(strs[0]);
		long skuId = Long.parseLong(strs[1]);
		skuUidDTO.setSellerId(Long.valueOf(sellerId));
		skuUidDTO.setSkuId(Long.valueOf(skuId));

		return skuUidDTO;
	}

	public static List<MarketValueAddedServiceDTO> getMarketValueAddedServiceDTOList(List<String> serviceList) {
		if (serviceList == null || serviceList.isEmpty()) {
			return Collections.emptyList();
		}
		List<MarketValueAddedServiceDTO> valueAddedServiceDTOs = new ArrayList<MarketValueAddedServiceDTO>();
		MarketValueAddedServiceDTO valueAddedServiceDTO;
		ServiceUidDTO serviceUidDTO;
		for (String serviceUid : serviceList) {
			valueAddedServiceDTO = new MarketValueAddedServiceDTO();
			serviceUidDTO = parseServiceUid(serviceUid);
			if (serviceUidDTO == null)
				continue;
			valueAddedServiceDTO.setSellerId(serviceUidDTO.getSellerId());
			valueAddedServiceDTO.setId(serviceUidDTO.getServiceId());
			valueAddedServiceDTOs.add(valueAddedServiceDTO);
		}
		return valueAddedServiceDTOs;
	}

	public static ServiceUidDTO parseServiceUid(String serviceUid) {
		if (serviceUid == null) return null;
		ServiceUidDTO serviceUidDTO = new ServiceUidDTO();
		String[] strs = serviceUid.split("_");
		if (strs.length != 2) return null;
		serviceUidDTO.setSellerId(Long.valueOf(strs[0]));
		serviceUidDTO.setServiceId(Long.valueOf(strs[1]));
		return serviceUidDTO;
	}

	public static String genItemSkuUid(Long sellerId, Long skuId) {
		return "" + sellerId + "_" + skuId;
	}

	public static String genItemUid(Long sellerId, Long itemId) {
		return "" + sellerId + "_" + itemId;
	}

	public static String genActivityUid(Long creatorId, Long activityId) {
		return "" + creatorId + "_" + activityId;
	}

	public static String genCouponUid(Long userId, Long couponId) {
		return "" + userId + "_" + couponId;
	}

	public static MopSettlementInfo genMopSettlementInfo(SettlementInfo settlementInfo) {
		MopSettlementInfo mopSettlementInfo = new MopSettlementInfo();
		mopSettlementInfo.setTotalPrice(settlementInfo.getTotalPrice());
		mopSettlementInfo.setDeliveryFee(settlementInfo.getDeliveryFee());
		mopSettlementInfo.setDiscountAmount(settlementInfo.getDiscountAmount());
		mopSettlementInfo.setMemberDiscountAmount(settlementInfo.getMemberDiscountAmount());
		mopSettlementInfo.setExchangeAmount(settlementInfo.getExchangeAmount());
		mopSettlementInfo.setDiscountInfoList(genMopDiscountInfoList(settlementInfo.getDiscountInfoList()));
		mopSettlementInfo.setDirectDiscountList(genMopDiscountInfoList(settlementInfo.getDirectDiscountList()));
		mopSettlementInfo.setSavedPostage(settlementInfo.getSavedPostage());
		mopSettlementInfo.setFreePostage(settlementInfo.isFreePostage() ? 1 : 0);
		mopSettlementInfo.setWealthAccountList(genMopWealthAccountDTOList(settlementInfo.getWealthAccountList()));
		mopSettlementInfo.setItemList(genMopMarketItemDTOList(settlementInfo.getItemList()));
		mopSettlementInfo.setGiftList(genMopMarketItemDTOList(settlementInfo.getGiftList()));
		mopSettlementInfo.setHigoExtraInfo(genMopHigoExtraInfo(settlementInfo.getHigoExtraInfo()));
		return mopSettlementInfo;
	}

	public static List<MopActivityCouponDTO> genMopActivityCouponDTOList(List<ActivityCouponDTO> activityCouponDTOs) {
		if (activityCouponDTOs == null) {
			return Collections.EMPTY_LIST;
		}
		List<MopActivityCouponDTO> mopActivityCouponDTOs = new ArrayList<MopActivityCouponDTO>();
		MopActivityCouponDTO mopActivityCouponDTO;
		for (ActivityCouponDTO activityCouponDTO : activityCouponDTOs) {
			mopActivityCouponDTO = genMopActivityCouponDTO(activityCouponDTO);
			if (mopActivityCouponDTO != null) {
				mopActivityCouponDTOs.add(mopActivityCouponDTO);
			}
		}
		return mopActivityCouponDTOs;
	}

	public static MopActivityCouponDTO genMopActivityCouponDTO(ActivityCouponDTO activityCouponDTO) {
		MopActivityCouponDTO mopActivityCouponDTO = new MopActivityCouponDTO();
		if (activityCouponDTO.getActivityCreatorId() != null) {
			mopActivityCouponDTO.setActivityCouponUid(genActivityCouponUid(activityCouponDTO));
		}
		if (activityCouponDTO.getActivityId() != null) {
			mopActivityCouponDTO.setActivityUid(genActivityUid(activityCouponDTO.getActivityCreatorId(), activityCouponDTO.getActivityId()));
		}
		mopActivityCouponDTO.setContent(activityCouponDTO.getContent());
		mopActivityCouponDTO.setDesc(activityCouponDTO.getDesc());
		mopActivityCouponDTO.setName(activityCouponDTO.getName());
		mopActivityCouponDTO.setTotalCount(activityCouponDTO.getTotalCount());
		mopActivityCouponDTO.setStatus(activityCouponDTO.getStatus());
		mopActivityCouponDTO.setLifecycle(activityCouponDTO.getLifecycle());
		mopActivityCouponDTO.setConsume(activityCouponDTO.getConsume());
		mopActivityCouponDTO.setDiscountAmount(activityCouponDTO.getDiscountAmount());
		if (activityCouponDTO.getMarketActivity() != null) {
			mopActivityCouponDTO.setStartTime(dateFormatSimple.format(activityCouponDTO.getStartTime()));
			mopActivityCouponDTO.setEndTime(dateFormatSimple.format(activityCouponDTO.getEndTime()));
		}

		return mopActivityCouponDTO;
	}

	public static List<MopGrantedCouponGatherDTO> genMopGrantedCouponGatherDTOList(List<GrantedCouponGatherDTO> grantedCouponGatherDTOs) {
		List<MopGrantedCouponGatherDTO> mopGrantedCouponGatherDTOs = new ArrayList<MopGrantedCouponGatherDTO>();
		MopGrantedCouponGatherDTO mopGrantedCouponGatherDTO;
		for (GrantedCouponGatherDTO grantedCouponGatherDTO : grantedCouponGatherDTOs) {
			mopGrantedCouponGatherDTO = genMopGrantedCouponGatherDTO(grantedCouponGatherDTO);
			if (mopGrantedCouponGatherDTO != null)
				mopGrantedCouponGatherDTOs.add(mopGrantedCouponGatherDTO);
		}
		return mopGrantedCouponGatherDTOs;
	}

	public static MopGrantedCouponGatherDTO genMopGrantedCouponGatherDTO(GrantedCouponGatherDTO grantedCouponGatherDTO) {
		MopGrantedCouponGatherDTO mopGrantedCouponGatherDTO = new MopGrantedCouponGatherDTO();
		mopGrantedCouponGatherDTO.setToolCode(grantedCouponGatherDTO.getToolCode());
		mopGrantedCouponGatherDTO.setDiscountAmount(grantedCouponGatherDTO.getDiscountAmount());
		mopGrantedCouponGatherDTO.setStartTime(dateFormat.format(grantedCouponGatherDTO.getStartTime()));
		mopGrantedCouponGatherDTO.setEndTime(dateFormat.format(grantedCouponGatherDTO.getEndTime()));
		mopGrantedCouponGatherDTO.setPropertyList(genMopPropertyDTOList(grantedCouponGatherDTO.getPropertyList()));
		mopGrantedCouponGatherDTO.setCouponUid(genCouponUid(grantedCouponGatherDTO.getReceiverId(), grantedCouponGatherDTO.getId()));
		mopGrantedCouponGatherDTO.setNumber(grantedCouponGatherDTO.getNumber());
		mopGrantedCouponGatherDTO.setName(grantedCouponGatherDTO.getName());
		mopGrantedCouponGatherDTO.setContent(grantedCouponGatherDTO.getContent());
		return mopGrantedCouponGatherDTO;
	}

	public static List<MopGrantedCouponDTO> genMopGrantedCouponDTOList(List<GrantedCouponDTO> grantedCouponDTOs) {
		List<MopGrantedCouponDTO> mopGrantedCouponDTOs = new ArrayList<MopGrantedCouponDTO>();
		if (grantedCouponDTOs == null || grantedCouponDTOs.isEmpty()) return mopGrantedCouponDTOs;
		MopGrantedCouponDTO mopGrantedCouponDTO;
		for (GrantedCouponDTO grantedCouponDTO : grantedCouponDTOs) {
			mopGrantedCouponDTO = genMopGrantedCouponDTO(grantedCouponDTO);
			if (mopGrantedCouponDTO != null)
				mopGrantedCouponDTOs.add(mopGrantedCouponDTO);
		}
		return mopGrantedCouponDTOs;
	}

	public static MopGrantedCouponDTO genMopGrantedCouponDTO(GrantedCouponDTO grantedCouponDTO) {
		MopGrantedCouponDTO mopGrantedCouponDTO = new MopGrantedCouponDTO();
		mopGrantedCouponDTO.setToolCode(grantedCouponDTO.getToolCode());
		mopGrantedCouponDTO.setDiscountAmount(grantedCouponDTO.getDiscountAmount());
		mopGrantedCouponDTO.setStartTime(dateFormatSimple.format(grantedCouponDTO.getStartTime()));
		mopGrantedCouponDTO.setEndTime(dateFormatSimple.format(grantedCouponDTO.getEndTime()));
		mopGrantedCouponDTO.setPropertyList(genMopPropertyDTOList(grantedCouponDTO.getPropertyList()));
		mopGrantedCouponDTO.setCouponUid(genCouponUid(grantedCouponDTO.getReceiverId(), grantedCouponDTO.getId()));
		mopGrantedCouponDTO.setActivityUid(genActivityUid(grantedCouponDTO.getActivityCreatorId(), grantedCouponDTO.getActivityId()));
		mopGrantedCouponDTO.setName(grantedCouponDTO.getName());
		mopGrantedCouponDTO.setContent(grantedCouponDTO.getContent());
		mopGrantedCouponDTO.setScope(grantedCouponDTO.getScope());
		mopGrantedCouponDTO.setDesc(grantedCouponDTO.getDesc());
		mopGrantedCouponDTO.setStatus(grantedCouponDTO.getStatus());
		mopGrantedCouponDTO.setNearExpireDate(grantedCouponDTO.getNearExpireDate());
		return mopGrantedCouponDTO;
	}

	public static MopMarketActivityDTO genMopMarketActivityDTO(MarketActivityDTO marketActivityDTO) {
		MopMarketActivityDTO mopMarketActivityDTO = new MopMarketActivityDTO();
		mopMarketActivityDTO.setDiscountAmount(marketActivityDTO.getDiscountAmount());
		mopMarketActivityDTO.setActivityName(marketActivityDTO.getActivityName());
		mopMarketActivityDTO.setActivityContent(marketActivityDTO.getActivityContent());
		mopMarketActivityDTO.setActivityUid(genActivityUid(marketActivityDTO.getCreatorId(), marketActivityDTO.getId()));
		mopMarketActivityDTO.setCouponMark(marketActivityDTO.getCouponMark());
		mopMarketActivityDTO.setCouponType(marketActivityDTO.getCouponType());
		mopMarketActivityDTO.setScope(marketActivityDTO.getScope());
		mopMarketActivityDTO.setIcone(marketActivityDTO.getIcon());
		mopMarketActivityDTO.setToolCode(marketActivityDTO.getToolCode());
		mopMarketActivityDTO.setPropertyList(genMopPropertyDTOList(marketActivityDTO.getPropertyList()));
		mopMarketActivityDTO.setItemList(genMopMarketItemDTOListFromActivityItem(marketActivityDTO.getActivityItemList()));
		mopMarketActivityDTO.setTargetItemList(genMopMarketItemDTOListFromActivityItem(marketActivityDTO.getTargetItemList()));
		mopMarketActivityDTO.setActivityTag(marketActivityDTO.getActivityTag());
		mopMarketActivityDTO.setLimitTagStatus(marketActivityDTO.getLimitTagStatus());
		mopMarketActivityDTO.setLimitTagDate(marketActivityDTO.getLimitTagDate());
		// 活动详情,suitTool 木有时间
		if (marketActivityDTO.getStartTime() != null) {
			if (ToolType.SIMPLE_TOOL.getCode().equals(marketActivityDTO.getToolCode())) {
				mopMarketActivityDTO.setStartTime(dateFormatSimple.format(marketActivityDTO.getStartTime()));
			} else {
				mopMarketActivityDTO.setStartTime(dateFormat.format(marketActivityDTO.getStartTime()));
			}
		}
		if (marketActivityDTO.getEndTime() != null) {
			if (ToolType.SIMPLE_TOOL.getCode().equals(marketActivityDTO.getToolCode())) {
				mopMarketActivityDTO.setEndTime(dateFormatSimple.format(marketActivityDTO.getEndTime()));
			} else {
				mopMarketActivityDTO.setEndTime(dateFormat.format(marketActivityDTO.getEndTime()));
			}
		}
		return mopMarketActivityDTO;
	}

	public static MarketActivityDTO genMarketActivityDTO(MopMarketActivityDTO mopMarketActivityDTO) throws Exception {
		if (mopMarketActivityDTO == null) {
			return null;
		}
		MarketActivityDTO marketActivityDTO = new MarketActivityDTO();
		marketActivityDTO.setActivityName(mopMarketActivityDTO.getActivityName());
		marketActivityDTO.setActivityContent(mopMarketActivityDTO.getActivityContent());
		marketActivityDTO.setToolId(mopMarketActivityDTO.getToolId());
		marketActivityDTO.setToolCode(mopMarketActivityDTO.getToolCode());
		marketActivityDTO.setScope(mopMarketActivityDTO.getScope());
		marketActivityDTO.setCouponMark(mopMarketActivityDTO.getCouponMark());
		marketActivityDTO.setPropertyList(genPropertyDTOList(mopMarketActivityDTO.getPropertyList()));
		if (mopMarketActivityDTO.getStartTime() != null) {
			marketActivityDTO.setStartTime(dateFormat.parse(mopMarketActivityDTO.getStartTime()));
		}

		if (mopMarketActivityDTO.getEndTime() != null) {
			marketActivityDTO.setEndTime(dateFormat.parse(mopMarketActivityDTO.getEndTime()));
		}
		return marketActivityDTO;
	}

	public static List<MopDiscountInfo> genMopDiscountInfoList(List<DiscountInfo> discountInfos) {
		if (discountInfos == null || discountInfos.isEmpty()) {
			return Collections.emptyList();
		}
		List<MopDiscountInfo> mopDiscountInfos = new ArrayList<MopDiscountInfo>();
		MopDiscountInfo mopDiscountInfo;
		for (DiscountInfo discountInfo : discountInfos) {
			mopDiscountInfo = genMopDiscountInfo(discountInfo);
			if (mopDiscountInfo != null)
				mopDiscountInfos.add(mopDiscountInfo);
		}
		return mopDiscountInfos;
	}

	public static MopDiscountInfo genMopDiscountInfo(DiscountInfo discountInfo) {
		MopDiscountInfo mopDiscountInfo = new MopDiscountInfo();
		mopDiscountInfo.setDiscountAmount(discountInfo.getDiscountAmount());
		mopDiscountInfo.setItemList(genMopMarketItemDTOList(discountInfo.getItemList()));
		mopDiscountInfo.setAvailableCouponList(genMopGrantedCouponDTOList(discountInfo.getAvailableCoupons()));
		mopDiscountInfo.setCouponList(genMopActivityCouponDTOList(discountInfo.getCouponList()));
		mopDiscountInfo.setMarketActivity(genMopMarketActivityDTO(discountInfo.getActivity()));
		mopDiscountInfo.setGiftList(genMopMarketItemDTOList(discountInfo.getGiftList()));
		mopDiscountInfo.setSavedPostage(discountInfo.getSavedPostage());
		mopDiscountInfo.setConsume(discountInfo.getConsume());
		mopDiscountInfo.setIsAvailable(discountInfo.isAvailable() ? 1 : 0);
		mopDiscountInfo.setFreePostage(discountInfo.isFreePostage() ? 1 : 0);
		mopDiscountInfo.setDesc(discountInfo.getDesc());
		return mopDiscountInfo;
	}

	public static List<MopMarketItemDTO> genMopMarketItemDTOListFromActivityItem(List<ActivityItemDTO> activityItemDTOs) {
		if (activityItemDTOs == null) {
			return Collections.emptyList();
		}
		List<MopMarketItemDTO> mopMarketItemDTOs = new ArrayList<MopMarketItemDTO>();
		MopMarketItemDTO mopMarketItemDTO;
		for (ActivityItemDTO activityItemDTO : activityItemDTOs) {
			mopMarketItemDTO = genMopMarketItemDTO(activityItemDTO);
			if (mopMarketItemDTO != null)
				mopMarketItemDTOs.add(mopMarketItemDTO);
		}
		return mopMarketItemDTOs;
	}

	public static List<MopMarketItemDTO> genMopMarketItemDTOList(List<MarketItemDTO> marketItemDTOs) {
		if (marketItemDTOs == null) {
			return Collections.emptyList();
		}
		List<MopMarketItemDTO> mopMarketItemDTOs = new ArrayList<MopMarketItemDTO>();
		MopMarketItemDTO mopMarketItemDTO;
		for (MarketItemDTO marketItemDTO : marketItemDTOs) {
			mopMarketItemDTO = genMopMarketItemDTO(marketItemDTO);
			if (mopMarketItemDTO != null)
				mopMarketItemDTOs.add(mopMarketItemDTO);
		}

		return mopMarketItemDTOs;
	}

	public static MopMarketItemDTO genMopMarketItemDTO(MarketItemDTO marketItemDTO) {
		MopMarketItemDTO mopMarketItemDTO = new MopMarketItemDTO();
		mopMarketItemDTO.setItemName(marketItemDTO.getItemName());
		mopMarketItemDTO.setSkuSnapshot(marketItemDTO.getSkuSnapshot());
		mopMarketItemDTO.setItemSkuUid(genItemSkuUid(marketItemDTO.getSellerId(), marketItemDTO.getItemSkuId()));
		mopMarketItemDTO.setItemUid(genItemUid(marketItemDTO.getSellerId(), marketItemDTO.getItemId()));
		mopMarketItemDTO.setSellerId(marketItemDTO.getSellerId());
		mopMarketItemDTO.setUnitPrice(marketItemDTO.getUnitPrice());
		mopMarketItemDTO.setNumber(marketItemDTO.getNumber());
		mopMarketItemDTO.setIconUrl(marketItemDTO.getIconUrl());
		mopMarketItemDTO.setItemType(marketItemDTO.getItemType());
		mopMarketItemDTO.setServiceList(genMopAddedServiceDTOList(marketItemDTO.getServices()));
		mopMarketItemDTO.setHigoExtraInfo(genMopHigoExtraInfo(marketItemDTO.getHigoExtraInfo()));
		mopMarketItemDTO.setVirtualMark(marketItemDTO.getVirtualMark());
		mopMarketItemDTO.setHigoMark(marketItemDTO.getHigoMark());
		mopMarketItemDTO.setShareUserId(marketItemDTO.getShareUserId());
		mopMarketItemDTO.setDistributorInfo(genMopDistributorInfoDTO(marketItemDTO.getDistributorInfoDTO()));
		mopMarketItemDTO.setLimitNumber(marketItemDTO.getLimitNumber());
		return mopMarketItemDTO;
	}

	public static MopDistributorInfoDTO genMopDistributorInfoDTO(DistributorInfoDTO distributorInfoDTO) {
		if (distributorInfoDTO == null) return null;
		MopDistributorInfoDTO mopDistributorInfoDTO = new MopDistributorInfoDTO();
		mopDistributorInfoDTO.setDistributorSign(distributorInfoDTO.getDistributorSign());
		mopDistributorInfoDTO.setShopName(distributorInfoDTO.getShopName());
		mopDistributorInfoDTO.setDistributorId(distributorInfoDTO.getDistributorId());
		return mopDistributorInfoDTO;
	}

	public static List<MopAddedServiceDTO> genMopAddedServiceDTOList(List<MarketValueAddedServiceDTO> marketValueAddedServiceDTOs) {
		if (marketValueAddedServiceDTOs == null || marketValueAddedServiceDTOs.isEmpty())
			return null;
		List<MopAddedServiceDTO> mopAddedServiceDTOs = new ArrayList<MopAddedServiceDTO>();
		for (MarketValueAddedServiceDTO marketValueAddedServiceDTO : marketValueAddedServiceDTOs)
			mopAddedServiceDTOs.add(genMopAddedServiceDTO(marketValueAddedServiceDTO));
		return mopAddedServiceDTOs;
	}

	public static MopAddedServiceDTO genMopAddedServiceDTO(MarketValueAddedServiceDTO marketValueAddedServiceDTO) {
		MopAddedServiceDTO mopAddedServiceDTO = new MopAddedServiceDTO();
		mopAddedServiceDTO.setServiceName(marketValueAddedServiceDTO.getName());
		mopAddedServiceDTO.setServicePrice(marketValueAddedServiceDTO.getPrice());
		mopAddedServiceDTO.setServiceUid(marketValueAddedServiceDTO.getSellerId() + "_" + marketValueAddedServiceDTO.getId());
		return mopAddedServiceDTO;
	}

	public static MopMarketItemDTO genMopMarketItemDTO(ActivityItemDTO activityItemDTO) {
		MopMarketItemDTO mopMarketItemDTO = new MopMarketItemDTO();
		mopMarketItemDTO.setItemName(activityItemDTO.getItemName());
		mopMarketItemDTO.setSkuSnapshot(activityItemDTO.getSkuSnapshot());
		mopMarketItemDTO.setItemSkuUid(genItemSkuUid(activityItemDTO.getSellerId(), activityItemDTO.getItemSkuId()));
		mopMarketItemDTO.setItemUid(genItemUid(activityItemDTO.getSellerId(), activityItemDTO.getItemId()));
		mopMarketItemDTO.setSellerId(activityItemDTO.getSellerId());
		mopMarketItemDTO.setUnitPrice(activityItemDTO.getUnitPrice());
		mopMarketItemDTO.setIconUrl(activityItemDTO.getIconUrl());
		mopMarketItemDTO.setNumber(activityItemDTO.getNumber());
		mopMarketItemDTO.setItemType(activityItemDTO.getItemType());
		mopMarketItemDTO.setVirtualMark(activityItemDTO.getVirtualMark());
		return mopMarketItemDTO;
	}

	public static List<MopPropertyDTO> genMopPropertyDTOList(List<PropertyDTO> propertyDTOs) {
		if (propertyDTOs == null) {
			return null;
		}

		List<MopPropertyDTO> mopPropertyDTOs = new ArrayList<MopPropertyDTO>();
		MopPropertyDTO mopPropertyDTO;
		for (PropertyDTO propertyDTO : propertyDTOs) {
			mopPropertyDTO = genMopPropertyDTO(propertyDTO);
			if (mopPropertyDTO != null)
				mopPropertyDTOs.add(mopPropertyDTO);
		}

		return mopPropertyDTOs;
	}

	public static MopPropertyDTO genMopPropertyDTO(PropertyDTO propertyDTO) {
		if (propertyDTO == null) {
			return null;
		}
		MopPropertyDTO mopPropertyDTO = new MopPropertyDTO();
		//FIXME 这里对外的property name对应内部的property pkey
		mopPropertyDTO.setName(propertyDTO.getPkey());
		mopPropertyDTO.setValue(propertyDTO.getValue());
		mopPropertyDTO.setValueType(propertyDTO.getValueType());
		return mopPropertyDTO;
	}

	public static List<PropertyDTO> genPropertyDTOList(List<MopPropertyDTO> mopPropertyDTOs) {
		if (mopPropertyDTOs == null) {
			return null;
		}
		List<PropertyDTO> propertyDTOs = new ArrayList<PropertyDTO>();
		PropertyDTO propertyDTO;
		for (MopPropertyDTO mopPropertyDTO : mopPropertyDTOs) {
			propertyDTO = genPropertyDTO(mopPropertyDTO);
			if (propertyDTO != null)
				propertyDTOs.add(propertyDTO);
		}

		return propertyDTOs;
	}

	public static PropertyDTO genPropertyDTO(MopPropertyDTO mopPropertyDTO) {
		if (mopPropertyDTO == null) {
			return null;
		}
		PropertyDTO propertyDTO = new PropertyDTO();
		//FIXME 这里对外的property name对应内部的property pkey
		propertyDTO.setPkey(mopPropertyDTO.getName());
		propertyDTO.setValue(mopPropertyDTO.getValue());
		propertyDTO.setValueType(mopPropertyDTO.getValueType());
		return propertyDTO;
	}

	public static MopShopDTO genMopShopDTO(ShopDTO shopDTO) {
		if (shopDTO == null) {
			return null;
		}
		MopShopDTO mopShopDTO = new MopShopDTO();
		mopShopDTO.setShopId(String.valueOf(shopDTO.getId()));
		mopShopDTO.setIconUrl(shopDTO.getShopIconUrl());
		mopShopDTO.setSellerId(String.valueOf(shopDTO.getSellerId()));
		mopShopDTO.setShopName(shopDTO.getShopName());
		return mopShopDTO;
	}

	public static MopStoreDTO genMopStoreDTO(StoreDTO storeDTO) {
		if (storeDTO == null) {
			return null;
		}
		MopStoreDTO mopStoreDTO = new MopStoreDTO();
		mopStoreDTO.setStoreName(storeDTO.getStoreName());
		mopStoreDTO.setPhone(storeDTO.getPhone());
		mopStoreDTO.setAddress(storeDTO.getAddress());
		mopStoreDTO.setStoreUid(storeDTO.getSellerId() + "_" + storeDTO.getId());
		return mopStoreDTO;
	}

	public static MopHigoExtraInfoDTO genMopHigoExtraInfo(HigoExtraInfoDTO higoExtraInfoDTO) {
		if (higoExtraInfoDTO == null) {
			return null;
		}

		MopHigoExtraInfoDTO mopHigoExtraInfoDTO = new MopHigoExtraInfoDTO();
		mopHigoExtraInfoDTO.setOriginalTaxFee(higoExtraInfoDTO.getOriginalTaxFee());
		mopHigoExtraInfoDTO.setFinalTaxFee(higoExtraInfoDTO.getFinalTaxFee());
		mopHigoExtraInfoDTO.setSupplyBase(higoExtraInfoDTO.getSupplyBase());
		mopHigoExtraInfoDTO.setDeliveryType(higoExtraInfoDTO.getDeliveryType());
		mopHigoExtraInfoDTO.setTaxRate(higoExtraInfoDTO.getTaxRate());

		return mopHigoExtraInfoDTO;
	}

	public static String genItemUid(ActivityItemDTO activityItemDTO) {
		if (activityItemDTO == null)
			return null;
		return activityItemDTO.getActivityCreatorId() + "_" + activityItemDTO.getItemId();
	}

	public static List<String> genItemUidList(List<ActivityItemDTO> activityItemDTOs) {
		if (activityItemDTOs == null || activityItemDTOs.isEmpty())
			return null;

		List<String> itemUidList = new ArrayList<String>();

		for (ActivityItemDTO activityItemDTO : activityItemDTOs)
			itemUidList.add(genItemUid(activityItemDTO));

		return itemUidList;
	}
}