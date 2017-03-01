package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;
import java.util.Map;

public interface PropertyManager {

	Map<String, PropertyDO> queryAndWrapProperty(PropertyQTO propertyQTO) throws MarketingException;

	/**
	 * 以属性名称做键值创建 Map
	 *
	 * @param propertyDOs
	 * @return
	 * @throws MarketingException
	 */
	Map<String, PropertyDO> wrapProperty(List<PropertyDO> propertyDOs) throws MarketingException;

	Map<String, PropertyDTO> wrapPropertyDTO(List<PropertyDTO> propertyDTOs) throws MarketingException;

	/**
	 * 填充活动属性列表
	 *
	 * @param marketActivityDTOs
	 * @param bizCode
	 * @throws MarketingException
	 */
	void fillUpMarketWithProperty(List<MarketActivityDTO> marketActivityDTOs, String bizCode) throws MarketingException;

	void addPropertyList(List<PropertyDTO> propertyDTOs, MarketActivityDTO marketActivityDTO) throws MarketingException;

	long addProperty(PropertyDO propertyDO) throws MarketingException;

	List<PropertyDO> queryProperty(PropertyQTO propertyQTO) throws MarketingException;

	Long extractPropertyConsume(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException;

	Long extractPropertyQuota(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException;

	Long extractPropertyExtra(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException;

	Long extractPropertyLimit(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException;

	Boolean extractPropertyFreePostage(Map<String, PropertyDTO> propertyDTOMap) throws MarketingException;

	List<MarketItemDTO> extractPropertyGiftItemList(Map<String, PropertyDTO> propertyDTOMap, String appKey) throws MarketingException;

	List<ActivityCouponDTO> extractPropertyCouponList(Map<String, PropertyDTO> propertyDTOMap, String bizCode) throws MarketingException;

	/**
	 * 换购到的商品，作为普通商品，不填充优惠信息
	 *
	 * @param propertyDTOMap
	 * @param sellerId
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	ActivityItemDTO extractPropertySkuId(Map<String, PropertyDTO> propertyDTOMap, Long sellerId, String appKey) throws MarketingException;

	/**
	 * 获取 itemId
	 *
	 * @param propertyDTOMap
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	Long extractPropertyItemId(Map<String, PropertyDTO> propertyDTOMap, String appKey) throws MarketingException;
}