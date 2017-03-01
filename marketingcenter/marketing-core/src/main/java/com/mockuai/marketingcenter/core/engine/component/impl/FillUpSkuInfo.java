package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPriceQTO;
import com.mockuai.itemcenter.common.util.PriceUtil;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.DistributorInfoDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketValueAddedServiceDTO;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.DistributorManager;
import com.mockuai.marketingcenter.core.manager.ItemPriceManager;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ComponentType.FILL_UP_SKU_INFO;

/**
 * 根据用户信息填充商品信息，会员价，服务加等
 * <p/>
 * Created by edgar.zr on 1/19/16.
 */
@Service
public class FillUpSkuInfo implements Component {

	private static final Logger LOGGER = LoggerFactory.getLogger(FillUpSkuInfo.class);

	@Autowired
	private ItemPriceManager itemPriceManager;
	@Autowired
	private DistributorManager distributorManager;

	public static Context wrapParams(List<MarketItemDTO> marketItemDTOs, Long userId, String appKey) {
		Context context = new Context();
		context.setParam("itemList", marketItemDTOs);
		context.setParam("userId", userId);
		context.setParam("appKey", appKey);
		context.setParam("component", FILL_UP_SKU_INFO);
		return context;
	}

	@Override
	public void init() {

	}

	@Override
	public Void execute(Context context) throws MarketingException {
		List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getParam("itemList");
		String appKey = (String) context.getParam("appKey");
		Long userId = (Long) context.getParam("userId");

		context.setParam("memberDiscountAmount", 0L);

		if (marketItemDTOs == null || marketItemDTOs.isEmpty()) {
			LOGGER.error("the itemList is empty, userId : {}, appKey : {}", userId, appKey);
			throw new MarketingException(ResponseCode.PARAMETER_MISSING, "itemList 非法");
		}

		Map<Long, List<MarketItemDTO>> skuIdKeyItemValue = new HashMap<>();		

		// 对itemDTO按skuId进行分组
		for (MarketItemDTO marketItemDTO : marketItemDTOs) {
			marketItemDTO.setDeliveryType(0);
			if (!skuIdKeyItemValue.containsKey(marketItemDTO.getItemSkuId()))
				skuIdKeyItemValue.put(marketItemDTO.getItemSkuId(), new ArrayList<MarketItemDTO>());
			skuIdKeyItemValue.get(marketItemDTO.getItemSkuId()).add(marketItemDTO);
		}

		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<ItemPriceQTO> itemPriceQTOs = new ArrayList<>();
		ItemPriceQTO itemPriceQTO = null;
		for (MarketItemDTO marketItemDTO : marketItemDTOs) {
			itemPriceQTO = new ItemPriceQTO();
			itemPriceQTO.setItemSkuId(marketItemDTO.getItemSkuId());
			itemPriceQTO.setSellerId(marketItemDTO.getSellerId());
			itemPriceQTO.setServiceIdList(ModelUtil.genServiceList(marketItemDTO.getServices()));
			itemPriceQTOs.add(itemPriceQTO);
		}

		List<ItemPriceDTO> itemPriceDTOs;

		stopWatch.reset();
		stopWatch.start();
		itemPriceDTOs = itemPriceManager.queryItemPriceDTO(itemPriceQTOs, userId, appKey);
		if (itemPriceDTOs == null || itemPriceDTOs.isEmpty() || marketItemDTOs.size() != itemPriceDTOs.size()) {
			LOGGER.error("error to queryItemPrice, itemPriceQTOs : {}, userId : {}, appKey : {} "
					, JsonUtil.toJson(itemPriceQTO), userId, appKey);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}

		ItemDTO itemDTO;
		ItemSkuDTO itemSkuDTO;
		List<MarketItemDTO> tempMarketItemDTOs;
		for (ItemPriceDTO itemPriceDTO : itemPriceDTOs) {
			itemSkuDTO = itemPriceDTO.getItemSkuDTO();
			itemDTO = itemPriceDTO.getItemDTO();

			tempMarketItemDTOs = skuIdKeyItemValue.get(itemSkuDTO.getId());
			// 同一个 sku 出现了多个 marketItemDTO
			// 粗略过滤掉覆盖的情况
			for (MarketItemDTO marketItemDTO : tempMarketItemDTOs) {
				marketItemDTO.setUnitPrice(itemSkuDTO.getPromotionPrice());
				if ((marketItemDTO.getServices() == null || marketItemDTO.getServices().isEmpty())
						    && (itemPriceDTO.getValueAddedServiceDTOList() == null
								        || itemPriceDTO.getValueAddedServiceDTOList().isEmpty())) {
					marketItemDTO.setTotalPrice(itemSkuDTO.getPromotionPrice());
				}
				if (marketItemDTO.getServices() != null
						    && !marketItemDTO.getServices().isEmpty()
						    && itemPriceDTO.getValueAddedServiceDTOList() != null
						    && !itemPriceDTO.getValueAddedServiceDTOList().isEmpty()) {
					marketItemDTO.setTotalPrice(PriceUtil.calculatePrice(itemPriceDTO));
				}

				marketItemDTO.setSkuSnapshot(itemSkuDTO.getSkuCode());
				marketItemDTO.setItemId(itemSkuDTO.getItemId());
				// TODO
				marketItemDTO.setIconUrl(itemSkuDTO.getImageUrl());
				
				fillUpServices(marketItemDTO, itemPriceDTO.getValueAddedServiceDTOList());

				// 复制总价,用于税费计算
				marketItemDTO.setOriginTotalPrice(marketItemDTO.getTotalPrice());

				// 补全商品类别
				marketItemDTO.setItemType(itemDTO.getItemType());
				marketItemDTO.setItemName(itemDTO.getItemName());
				marketItemDTO.setBrandId(itemDTO.getItemBrandId());
				marketItemDTO.setCategoryId(itemDTO.getCategoryId());
				marketItemDTO.setVirtualMark(itemDTO.getVirtualMark());
				marketItemDTO.setSupplierId(itemDTO.getSupplierId());
				marketItemDTO.setHigoMark(itemDTO.getHigoMark());
				if (marketItemDTO.getIconUrl() == null) {
					marketItemDTO.setIconUrl(itemDTO.getIconUrl());
				}
			}
		}
		context.setParam("memberDiscountAmount", 0L);
		return null;
	}

	private void fillUpServices(MarketItemDTO marketItemDTO, List<ValueAddedServiceDTO> valueAddedServiceDTOs) {
		if (marketItemDTO.getServices() == null || marketItemDTO.getServices().isEmpty()
				    || valueAddedServiceDTOs == null || valueAddedServiceDTOs.isEmpty())
			return;

		marketItemDTO.setServices(new ArrayList<MarketValueAddedServiceDTO>());

		MarketValueAddedServiceDTO marketValueAddedServiceDTO;
		for (ValueAddedServiceDTO valueAddedServiceDTO : valueAddedServiceDTOs) {
			marketValueAddedServiceDTO = new MarketValueAddedServiceDTO();
			marketValueAddedServiceDTO.setId(valueAddedServiceDTO.getId());
			marketValueAddedServiceDTO.setSellerId(valueAddedServiceDTO.getSellerId());
			marketValueAddedServiceDTO.setName(valueAddedServiceDTO.getServiceName());
			marketValueAddedServiceDTO.setPrice(valueAddedServiceDTO.getServicePrice());
			marketItemDTO.getServices().add(marketValueAddedServiceDTO);
		}
	}

	@Override
	public String getComponentCode() {
		return FILL_UP_SKU_INFO.getCode();
	}
}