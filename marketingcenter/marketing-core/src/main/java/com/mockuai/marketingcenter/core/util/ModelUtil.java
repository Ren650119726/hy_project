package com.mockuai.marketingcenter.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.marketingcenter.common.constant.PropertyEnum;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.*;
import com.mockuai.marketingcenter.core.domain.*;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModelUtil {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static List<CouponCodeDTO> genCouponCodeList(List<CouponCodeDO> couponCodeDOs) {
		List<CouponCodeDTO> couponCodeDTOs = new ArrayList<CouponCodeDTO>();
		if (couponCodeDOs == null || couponCodeDOs.size() == 0) {
			return Collections.emptyList();
		}
		for (CouponCodeDO couponCodeDO : couponCodeDOs) {
			couponCodeDTOs.add(genCouponCodeDTO(couponCodeDO));
		}
		return couponCodeDTOs;
	}

	public static CouponCodeDTO genCouponCodeDTO(CouponCodeDO couponCodeDO) {
		if (couponCodeDO == null) {
			return null;
		}
		CouponCodeDTO couponCodeDTO = new CouponCodeDTO();
		BeanUtils.copyProperties(couponCodeDO, couponCodeDTO);
		return couponCodeDTO;
	}

	public static ActivityHistoryDO genActivityHistoryDO(ActivityHistoryDTO activityHistoryDTO) {
		if (activityHistoryDTO == null) {
			return null;
		}
		ActivityHistoryDO activityHistoryDO = new ActivityHistoryDO();
		BeanUtils.copyProperties(activityHistoryDTO, activityHistoryDO);
		return activityHistoryDO;
	}

	public static ActivityHistoryDTO genActivityHistoryDTO(ActivityHistoryDO activityHistoryDO) {
		if (activityHistoryDO == null) {
			return null;
		}
		ActivityHistoryDTO activityHistoryDTO = new ActivityHistoryDTO();
		BeanUtils.copyProperties(activityHistoryDO, activityHistoryDTO);
		return activityHistoryDTO;
	}

	public static List<ActivityHistoryDTO> genActivityHistoryDTOList(List<ActivityHistoryDO> activityHistoryDOs) {
		if (activityHistoryDOs == null || activityHistoryDOs.isEmpty()) {
			return null;
		}
		List<ActivityHistoryDTO> activityHistoryDTOs = new ArrayList<ActivityHistoryDTO>();
		ActivityHistoryDTO activityHistoryDTO;
		for (ActivityHistoryDO activityHistoryDO : activityHistoryDOs) {
			activityHistoryDTO = new ActivityHistoryDTO();
			BeanUtils.copyProperties(activityHistoryDO, activityHistoryDTO);
			activityHistoryDTOs.add(activityHistoryDTO);
		}
		return activityHistoryDTOs;
	}

	public static List<ActivityCouponDTO> genActivityCouponDTOList(List<ActivityCouponDO> activityCouponDOList) {
		if (activityCouponDOList == null) {
			return null;
		}

		List activityCouponDTOs = new ArrayList();
		for (ActivityCouponDO activityCouponDO : activityCouponDOList) {
			activityCouponDTOs.add(genActivityCouponDTO(activityCouponDO));
		}
		return activityCouponDTOs;
	}

	public static ActivityCouponDTO genActivityCouponDTO(ActivityCouponDO activityCouponDO, MarketActivityDTO marketActivityDTO) {
		ActivityCouponDTO activityCouponDTO = genActivityCouponDTO(activityCouponDO);
		activityCouponDTO.setName(marketActivityDTO.getActivityName());
		activityCouponDTO.setDesc(marketActivityDTO.getActivityContent());
		activityCouponDTO.setMarketActivity(marketActivityDTO);
		return activityCouponDTO;
	}

	public static ActivityCouponDTO genActivityCouponDTO(ActivityCouponDO activityCouponDO) {
		if (activityCouponDO == null) {
			return null;
		}

		ActivityCouponDTO activityCouponDTO = new ActivityCouponDTO();
		BeanUtils.copyProperties(activityCouponDO, activityCouponDTO);
		return activityCouponDTO;
	}

	public static ActivityCouponDO genActivityCouponDO(ActivityCouponDTO activityCouponDTO) {
		if (activityCouponDTO == null) {
			return null;
		}

		ActivityCouponDO activityCouponDO = new ActivityCouponDO();
		BeanUtils.copyProperties(activityCouponDTO, activityCouponDO);
		return activityCouponDO;
	}

	public static List<MarketActivityDTO> genMarketActivityDTOList(List<MarketActivityDO> marketActivityDOList) {
		if (marketActivityDOList == null) {
			return null;
		}

		List marketActivityDTOs = new ArrayList();

		for (MarketActivityDO marketActivityDO : marketActivityDOList) {
			marketActivityDTOs.add(genMarketActivityDTO(marketActivityDO));
		}

		return marketActivityDTOs;
	}

	public static List<OrderRecordDTO> genOrderRecordDTOList(List<OrderRecordDO> orderRecordDOs) {
		if (orderRecordDOs == null) {
			return Collections.EMPTY_LIST;
		}

		List<OrderRecordDTO> orderRecordDTOs = new ArrayList();

		for (OrderRecordDO orderRecordDO : orderRecordDOs) {
			orderRecordDTOs.add(genOrderRecordDTO(orderRecordDO));
		}

		return orderRecordDTOs;
	}

	public static OrderRecordDTO genOrderRecordDTO(OrderRecordDO orderRecordDO) {
		if (orderRecordDO == null) {
			return null;
		}

		OrderRecordDTO orderRecordDTO = new OrderRecordDTO();
		BeanUtils.copyProperties(orderRecordDO, orderRecordDTO);
		return orderRecordDTO;
	}

	public static MarketActivityDTO genMarketActivityDTO(MarketActivityDO marketActivityDO) {
		if (marketActivityDO == null) {
			return null;
		}

		MarketActivityDTO marketActivityDTO = new MarketActivityDTO();
		BeanUtils.copyProperties(marketActivityDO, marketActivityDTO);
		return marketActivityDTO;
	}

	public static MarketActivityDO genMarketActivityDO(MarketActivityDTO marketActivityDTO) {
		if (marketActivityDTO == null) {
			return null;
		}

		MarketActivityDO marketActivityDO = new MarketActivityDO();
		BeanUtils.copyProperties(marketActivityDTO, marketActivityDO);
		return marketActivityDO;
	}

	public static MarketToolDO genMarketToolDO(MarketToolDTO marketToolDTO) {
		if (marketToolDTO == null) {
			return null;
		}

		MarketToolDO marketToolDO = new MarketToolDO();
		BeanUtils.copyProperties(marketToolDTO, marketToolDO);
		return marketToolDO;
	}

	public static List<GrantedCouponDTO> genGrantedCouponDTOList(List<GrantedCouponDO> grantedCouponDOList,
	                                                             MarketActivityDTO marketActivityDTO) {
		if (grantedCouponDOList == null) {
			return null;
		}

		List grantedCouponDTOs = new ArrayList();
		for (GrantedCouponDO grantedCouponDO : grantedCouponDOList) {
			grantedCouponDTOs.add(genGrantedCouponDTO(grantedCouponDO, marketActivityDTO));
		}

		return grantedCouponDTOs;
	}

	public static GrantedCouponDTO genGrantedCouponDTO(GrantedCouponDO grantedCouponDO,
	                                                   MarketActivityDTO marketActivityDTO) {
		if (grantedCouponDO == null) {
			return null;
		}

		GrantedCouponDTO grantedCouponDTO = new GrantedCouponDTO();
		BeanUtils.copyProperties(grantedCouponDO, grantedCouponDTO);
		if (grantedCouponDO.getInvalidTime() != null) {
			grantedCouponDTO.setStartTime(grantedCouponDO.getActivateTime());
			grantedCouponDTO.setEndTime(grantedCouponDO.getInvalidTime());
		}
		grantedCouponDTO.setDiscountAmount(
				Long.parseLong(marketActivityDTO.getPropertyMap().get(PropertyDTO.QUOTA).getValue()));
		grantedCouponDTO.setConsume(
				Long.valueOf(marketActivityDTO.getPropertyMap().get(PropertyDTO.CONSUME).getValue()));
		grantedCouponDTO.setPropertyList(marketActivityDTO.getPropertyList());
		grantedCouponDTO.setName(marketActivityDTO.getActivityName());
		grantedCouponDTO.setContent(marketActivityDTO.getActivityContent());
		grantedCouponDTO.setScope(marketActivityDTO.getScope());
		Long current = new Date().getTime();
		Long hours = (grantedCouponDTO.getEndTime().getTime() - current) / 1000 / 3600;
		if (grantedCouponDTO.getStatus().intValue() == UserCouponStatus.UN_USE.getValue()) {
			grantedCouponDTO.setNearExpireDate(hours >= 0 && hours <= 72 ? 1 : 0);
		} else {
			grantedCouponDTO.setNearExpireDate(0);
		}
		return grantedCouponDTO;
	}

	public static void main(String[] args) {
		Long current = new Date().getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date date = format.parse("2016-06-07 00:00:00");

			Long hours = (current - date.getTime()) / 1000 / 60 / 60;
			System.err.println(hours);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static List<PropertyDTO> genPropertyDTOList(List<PropertyDO> propertyDOs) {
		if (propertyDOs == null) {
			return null;
		}

		List propertyDTOs = new ArrayList();
		for (PropertyDO propertyDO : propertyDOs) {
			propertyDTOs.add(genPropertyDTO(propertyDO));
		}
		return propertyDTOs;
	}

	public static PropertyDTO genPropertyDTO(PropertyDO propertyDO) {
		if (propertyDO == null) {
			return null;
		}

		PropertyDTO propertyDTO = new PropertyDTO();
		BeanUtils.copyProperties(propertyDO, propertyDTO);
		return propertyDTO;
	}

	public static PropertyDO genPropertyDO(PropertyDTO propertyDTO) {
		if (propertyDTO == null) {
			return null;
		}

		PropertyDO propertyDO = new PropertyDO();
		BeanUtils.copyProperties(propertyDTO, propertyDO);
		return propertyDO;
	}

	public static List<PropertyDO> genPropertyDOList(List<PropertyDTO> propertyDTOs) {
		if (propertyDTOs == null) {
			return null;
		}

		List<PropertyDO> propertyDOs = new CopyOnWriteArrayList<PropertyDO>();
		for (PropertyDTO propertyDTO : propertyDTOs) {
			propertyDOs.add(genPropertyDO(propertyDTO));
		}

		return propertyDOs;
	}

	public static PropertyTmplDO genPropertyTmplDO(PropertyTmplDTO propertyTmplDTO) {
		if (propertyTmplDTO == null) {
			return null;
		}

		PropertyTmplDO propertyTmplDO = new PropertyTmplDO();
		BeanUtils.copyProperties(propertyTmplDTO, propertyTmplDO);
		return propertyTmplDO;

	}

	public static PropertyTmplDTO genCommonPropertyTmplDTO(PropertyEnum propertyEnum, Integer required) {

		PropertyTmplDTO propertyTmplDTO = new PropertyTmplDTO();

		propertyTmplDTO.setBizCode("mockuai");
		propertyTmplDTO.setOwnerType(1);
		propertyTmplDTO.setName(propertyEnum.getName());
		propertyTmplDTO.setPkey(propertyEnum.getValue());
		propertyTmplDTO.setValueType(1);
		propertyTmplDTO.setRequiredMark(required);
		propertyTmplDTO.setCreatorType(1);
		propertyTmplDTO.setCreatorId(1L);
		propertyTmplDTO.setDeleteMark(0);

		return propertyTmplDTO;
	}

	public static List<ActivityItemDO> genActivityItemDOList(List<ActivityItemDTO> activityItemDTOs) {
		if (activityItemDTOs == null) {
			return null;
		}

		List<ActivityItemDO> activityItemDOs = new CopyOnWriteArrayList<ActivityItemDO>();
		for (ActivityItemDTO activityItemDTO : activityItemDTOs) {
			activityItemDOs.add(genActivityItemDO(activityItemDTO));
		}

		return activityItemDOs;
	}

	public static ActivityItemDO genActivityItemDO(ActivityItemDTO activityItemDTO) {
		if (activityItemDTO == null) {
			return null;
		}

		ActivityItemDO activityItemDO = new ActivityItemDO();
		BeanUtils.copyProperties(activityItemDTO, activityItemDO);
		return activityItemDO;
	}

	public static List<ActivityItemDTO> genActivityItemDTOList(List<ActivityItemDO> activityItemDOs) {
		if (activityItemDOs == null) {
			return null;
		}

		List<ActivityItemDTO> activityItemDTOs = new CopyOnWriteArrayList<ActivityItemDTO>();
		for (ActivityItemDO activityItemDO : activityItemDOs) {
			activityItemDTOs.add(genActivityItemDTO(activityItemDO));
		}

		return activityItemDTOs;
	}

	public static ActivityItemDTO genActivityItemDTO(ActivityItemDO activityItemDO) {
		if (activityItemDO == null) {
			return null;
		}

		ActivityItemDTO activityItemDTO = new ActivityItemDTO();
		BeanUtils.copyProperties(activityItemDO, activityItemDTO);
		return activityItemDTO;
	}

	public static List<Long> genServiceList(List<MarketValueAddedServiceDTO> serviceDTOs) {
		List<Long> serviceIds = new ArrayList<Long>();
		if (serviceDTOs == null || serviceDTOs.isEmpty()) return serviceIds;
		for (MarketValueAddedServiceDTO marketValueAddedServiceDTO : serviceDTOs) {
			serviceIds.add(marketValueAddedServiceDTO.getId());
		}
		return serviceIds;
	}

	public static Map<Long, ActivityHistoryDO> filterTradeMessage(JSONObject msg) {
		if (msg == null) return null;
		JSONArray array = msg.getJSONArray("orderItems");
		if (array == null || array.isEmpty())
			return null;

		Map<Long, ActivityHistoryDO> activityIdKeyHistoryValue = new HashMap<Long, ActivityHistoryDO>();
		ActivityHistoryDO activityHistoryDO;
		JSONObject object;
		for (int i = 0; i < array.size(); i++) {
			object = array.getJSONObject(i);
			if (object.getLong("activityId") == null) continue;
			if (!activityIdKeyHistoryValue.containsKey(object.getLong("activityId"))) {
				activityHistoryDO = new ActivityHistoryDO();
				activityIdKeyHistoryValue.put(object.getLong("activityId"), activityHistoryDO);
				activityHistoryDO.setSellerId(object.getLong("sellerId"));
				activityHistoryDO.setBizCode(msg.getString("bizCode"));
				activityHistoryDO.setActivityId(object.getLong("activityId"));
				activityHistoryDO.setItemId(object.getLong("itemId"));
				activityHistoryDO.setNum(0L);
				activityHistoryDO.setOrderId(msg.getLong("id"));
				activityHistoryDO.setSkuId(object.getLong("itemSkuId"));
				activityHistoryDO.setUserId(msg.getLong("userId"));
			}
			activityHistoryDO = activityIdKeyHistoryValue.get(object.getLong("activityId"));
			activityHistoryDO.setNum(activityHistoryDO.getNum() + object.getInteger("number").longValue());
		}

		return activityIdKeyHistoryValue;
	}
}