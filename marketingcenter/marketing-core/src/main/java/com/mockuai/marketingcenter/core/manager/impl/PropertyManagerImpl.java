package com.mockuai.marketingcenter.core.manager.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.dao.PropertyDAO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyManagerImpl implements PropertyManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyManagerImpl.class);

	@Resource
	private PropertyDAO propertyDAO;

	@Autowired
	private ItemManager itemManager;
	@Autowired
	private MarketActivityManager marketActivityManager;

	@Override
	public Map<String, PropertyDO> queryAndWrapProperty(PropertyQTO propertyQTO) throws MarketingException {
		List<PropertyDO> propertyDOs = queryProperty(propertyQTO);
		return wrapProperty(propertyDOs);
	}

	@Override
	public Map<String, PropertyDO> wrapProperty(List<PropertyDO> propertyDOs) throws MarketingException {
		if (propertyDOs == null || propertyDOs.isEmpty()) return new HashMap<String, PropertyDO>();
		Map<String, PropertyDO> propertyPKeyMap = new HashMap<String, PropertyDO>();
		for (PropertyDO propertyDO : propertyDOs) {
			propertyPKeyMap.put(propertyDO.getPkey(), propertyDO);
		}
		return propertyPKeyMap;
	}

	@Override
	public Map<String, PropertyDTO> wrapPropertyDTO(List<PropertyDTO> propertyDTOs) throws MarketingException {
		if (propertyDTOs == null || propertyDTOs.isEmpty()) return new HashMap<>();
		Map<String, PropertyDTO> propertyPKeyMap = new HashMap<String, PropertyDTO>();
		for (PropertyDTO propertyDTO : propertyDTOs) {
			propertyPKeyMap.put(propertyDTO.getPkey(), propertyDTO);
		}
		return propertyPKeyMap;
	}

	@Override
	public void fillUpMarketWithProperty(List<MarketActivityDTO> marketActivityDTOs, String bizCode) throws MarketingException {

		if (marketActivityDTOs == null || marketActivityDTOs.isEmpty()) {
			return;
		}
		try {

			Map<Long, MarketActivityDTO> marketIdKey = new HashMap<>();

			for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
				if (marketActivityDTO.getSubMarketActivityList() != null && !marketActivityDTO.getSubMarketActivityList().isEmpty()) {
					for (MarketActivityDTO innerActivityDTO : marketActivityDTO.getSubMarketActivityList()) {
						marketIdKey.put(innerActivityDTO.getId(), innerActivityDTO);
						innerActivityDTO.setPropertyList(new ArrayList<PropertyDTO>());
						innerActivityDTO.setPropertyMap(new HashMap<String, PropertyDTO>());
					}
				} else {
					marketIdKey.put(marketActivityDTO.getId(), marketActivityDTO);
					marketActivityDTO.setPropertyList(new ArrayList<PropertyDTO>());
					marketActivityDTO.setPropertyMap(new HashMap<String, PropertyDTO>());
				}
			}

			PropertyQTO propertyQTO = new PropertyQTO(PropertyOwnerType.ACTIVITY.getValue(), new ArrayList<>(marketIdKey.keySet()), bizCode);

			List<PropertyDTO> propertyDTOs = ModelUtil.genPropertyDTOList(queryProperty(propertyQTO));
			for (PropertyDTO propertyDTO : propertyDTOs) {
				marketIdKey.get(propertyDTO.getOwnerId()).getPropertyList().add(propertyDTO);
				marketIdKey.get(propertyDTO.getOwnerId()).getPropertyMap().put(propertyDTO.getPkey(), propertyDTO);
			}
		} catch (Exception e) {
			LOGGER.error("error to fillUpMarketWithProperty, {}", JsonUtil.toJson(marketActivityDTOs), e);
		}
	}

	@Override
	public void addPropertyList(List<PropertyDTO> propertyDTOs, MarketActivityDTO marketActivityDTO) throws MarketingException {
		PropertyDO propertyDO;
		//添加营销活动属性列表
		for (PropertyDTO propertyDTO : propertyDTOs) {
			propertyDO = ModelUtil.genPropertyDO(propertyDTO);
			// 目前的属性的属主均是活动
			propertyDO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());
			propertyDO.setOwnerId(Long.valueOf(marketActivityDTO.getId()));
			propertyDO.setCreatorId(marketActivityDTO.getCreatorId());
			propertyDO.setCreatorType(marketActivityDTO.getCreatorType());
			propertyDO.setBizCode(marketActivityDTO.getBizCode());
			addProperty(propertyDO);
		}
	}

	public long addProperty(PropertyDO propertyDO) throws MarketingException {
		try {
			return this.propertyDAO.addProperty(propertyDO);
		} catch (Exception e) {
			LOGGER.error("failed when adding property, property : {}", JsonUtil.toJson(propertyDO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public List<PropertyDO> queryProperty(PropertyQTO propertyQTO) throws MarketingException {
		try {
			return this.propertyDAO.queryProperty(propertyQTO);
		} catch (Exception e) {
			LOGGER.error("failed when querying property, propertyQTO : {}", JsonUtil.toJson(propertyQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public Long extractPropertyConsume(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException {
		if (propertyDTOMap == null) return 0L;
		if (propertyDTOMap.containsKey(PropertyDTO.CONSUME)) {
			return Long.parseLong(propertyDTOMap.get(PropertyDTO.CONSUME).getValue());
		} else {
			return 0L;
		}
	}

	@Override
	public Boolean extractPropertyFreePostage(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException {
		if (propertyDTOMap == null) return false;
		if (propertyDTOMap.containsKey(PropertyDTO.FREE_POSTAGE)) {
			return "1".equals(propertyDTOMap.get(PropertyDTO.FREE_POSTAGE).getValue());
		} else {
			return false;
		}
	}

	@Override
	public Long extractPropertyQuota(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException {
		if (propertyDTOMap == null) return 0L;
		if (propertyDTOMap.containsKey(PropertyDTO.QUOTA)) {
			return Long.parseLong(propertyDTOMap.get(PropertyDTO.QUOTA).getValue());
		} else {
			return 0L;
		}
	}

	@Override
	public Long extractPropertyExtra(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException {
		if (propertyDTOMap == null) return 0L;
		if (propertyDTOMap.containsKey(PropertyDTO.EXTRA)) {
			return Long.parseLong(propertyDTOMap.get(PropertyDTO.EXTRA).getValue());
		} else {
			return 0L;
		}
	}

	@Override
	public Long extractPropertyLimit(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException {
		if (propertyDTOMap == null) return 0L;
		if (propertyDTOMap.containsKey(PropertyDTO.LIMIT)) {
			return Long.parseLong(propertyDTOMap.get(PropertyDTO.LIMIT).getValue());
		} else {
			return 0L;
		}
	}

	@Override
	public List<MarketItemDTO> extractPropertyGiftItemList(Map<String, PropertyDTO> propertyDTOMap, String appKey) throws MarketingException {
		List<MarketItemDTO> itemDTOs = Collections.emptyList();
		if (propertyDTOMap.containsKey(PropertyDTO.GIFT_ITEM_LIST)) {
			try {
				itemDTOs = (JsonUtil.<List<MarketItemDTO>>parseJson(propertyDTOMap.get(PropertyDTO.GIFT_ITEM_LIST).getValue(),
						new TypeToken<List<MarketItemDTO>>() {
						}.getType()));
			} catch (JsonSyntaxException e) {
				LOGGER.error("error to extractPropertyGiftItemList, giftItemList : {}", propertyDTOMap.get(PropertyDTO.GIFT_ITEM_LIST), e);
				return Collections.emptyList();
			}
			try {
				itemManager.fillUpItemInfoDTO(itemDTOs, appKey);
				for (MarketItemDTO marketItemDTO : itemDTOs) {
					marketItemDTO.setNumber(1);
				}
			} catch (MarketingException e) {
				return Collections.emptyList();
			}
		}
		return itemDTOs;
	}

	@Override
	public List<ActivityCouponDTO> extractPropertyCouponList(Map<String, PropertyDTO> propertyDTOMap, String bizCode) throws MarketingException {
		List<ActivityCouponDTO> activityCouponDTOs = new ArrayList<>();
		if (propertyDTOMap.get(PropertyDTO.COUPON_LIST) != null) {
			List<Long> activityIds;
			try {
				activityIds = (JsonUtil.parseJson(propertyDTOMap.get(PropertyDTO.COUPON_LIST).getValue(),
						new TypeToken<List<Long>>() {
						}.getType()));
				MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
				marketActivityQTO.setIdList(new ArrayList<Long>());
				marketActivityQTO.setBizCode(bizCode);
				for (Long activityIdOfCoupon : activityIds) {
					marketActivityQTO.getIdList().add(activityIdOfCoupon);
				}
				if (marketActivityQTO.getIdList().isEmpty()) {
					return activityCouponDTOs;
				}
				List<MarketActivityDO> marketActivityDOs = marketActivityManager.queryActivity(marketActivityQTO);
				List<MarketActivityDTO> marketActivityDTOs = ModelUtil.genMarketActivityDTOList(marketActivityDOs);
				this.fillUpMarketWithProperty(marketActivityDTOs, bizCode);
				ActivityCouponDTO activityCouponDTO;
				Map<Long, MarketActivityDTO> activityIdKey = new HashMap<>();

				for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
					activityIdKey.put(marketActivityDTO.getId(), marketActivityDTO);
				}
				MarketActivityDTO marketActivityDTO;
				for (Long activityId : marketActivityQTO.getIdList()) {
					if (activityIdKey.containsKey(activityId)) {
						marketActivityDTO = activityIdKey.get(activityId);
						activityCouponDTO = new ActivityCouponDTO();
						activityCouponDTO.setDiscountAmount(this.extractPropertyQuota(marketActivityDTO.getPropertyMap()));
						activityCouponDTO.setConsume(this.extractPropertyConsume(marketActivityDTO.getPropertyMap()));
						activityCouponDTO.setName(marketActivityDTO.getActivityName());
						activityCouponDTO.setActivityId(marketActivityDTO.getId());
						activityCouponDTO.setContent(marketActivityDTO.getActivityContent());
						activityCouponDTO.setActivityCreatorId(marketActivityDTO.getCreatorId());
						activityCouponDTOs.add(activityCouponDTO);
					}
				}
			} catch (JsonSyntaxException e) {
				LOGGER.error("error to extractPropertyCouponList, couponList : {}", propertyDTOMap.get(PropertyDTO.COUPON_LIST), e);
			}
		}
		return activityCouponDTOs;
	}

	@Override
	public ActivityItemDTO extractPropertySkuId(Map<String, PropertyDTO> propertyDTOMap, Long sellerId, String appKey) throws MarketingException {
		Long skuId;
		ActivityItemDTO activityItemDTO = null;
		if (propertyDTOMap.containsKey(PropertyDTO.SKUID)) {
			skuId = Long.parseLong(propertyDTOMap.get(PropertyDTO.SKUID).getValue());
			if (StringUtils.isBlank(appKey)) {
				activityItemDTO = new ActivityItemDTO();
				activityItemDTO.setItemSkuId(skuId);
				return activityItemDTO;
			}
			ItemSkuDTO itemSkuDTO = itemManager.getItemSku(skuId, sellerId, appKey);
			if (itemSkuDTO == null) {
				LOGGER.error("error to extractPropertySkuId, cannot getSkuItemDTO, skuId : {}, sellerId : {}", skuId, sellerId);
				return activityItemDTO;
			}
			ItemDTO itemDTO = itemManager.getItem(itemSkuDTO.getItemId(), sellerId, appKey);
			if (itemDTO == null) {
				LOGGER.error("error to extractPropertySkuId, cannot getItemDTO, skuId : {}, sellerId : {}", itemSkuDTO.getItemId(), sellerId);
				return activityItemDTO;
			}
			activityItemDTO = new ActivityItemDTO();
			activityItemDTO.setItemName(itemDTO.getItemName());
			activityItemDTO.setIconUrl(itemSkuDTO.getImageUrl());
			activityItemDTO.setSellerId(sellerId);
			activityItemDTO.setItemId(itemDTO.getId());
			activityItemDTO.setItemSkuId(skuId);
			activityItemDTO.setUnitPrice(itemSkuDTO.getPromotionPrice());
			activityItemDTO.setSkuSnapshot(itemSkuDTO.getSkuCode());
			activityItemDTO.setStockNum(itemSkuDTO.getStockNum());
		}
		return activityItemDTO;
	}

	@Override
	public Long extractPropertyItemId(Map<String, PropertyDTO> propertyDTOMap, String appKey) throws MarketingException {
		if (propertyDTOMap == null) return null;
		try {
			if (propertyDTOMap.containsKey(PropertyDTO.ITEMID)) {
				return Long.valueOf(propertyDTOMap.get(PropertyDTO.ITEMID).getValue());
			}
		} catch (Exception e) {
			LOGGER.error("error to extractPropertyItemId, propertyDTOMap : {}, appKey : {}",
					JsonUtil.toJson(propertyDTOMap), appKey, e);
		}
		return null;
	}
}