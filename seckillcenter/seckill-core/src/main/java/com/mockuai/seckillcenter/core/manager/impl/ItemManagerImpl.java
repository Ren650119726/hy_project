package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.client.ItemCollectionClient;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.mop.MopItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.seckillcenter.common.constant.ResponseCode;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class ItemManagerImpl implements ItemManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemManagerImpl.class);

	@Autowired
	private ItemSkuClient itemSkuClient;

	@Autowired
	private ItemClient itemClient;
	@Autowired
	private StoreItemSkuClient storeItemSkuClient;
	@Autowired
	private ItemCollectionClient itemCollectionClient;

	@Override
	public ItemDTO addItem(ItemDTO itemDTO, String appKey) throws SeckillException {
		try {
			Response<ItemDTO> response = itemClient.addItem(itemDTO, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to addItem, itemDTO : {}, appKey : {}, errCode : {}, errMsg : {}",
					JsonUtil.toJson(itemDTO), appKey, response.getCode(), response.getMessage());
			throw new SeckillException(response.getCode(), response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to addItem, itemDTO : {}, appKey : {}", JsonUtil.toJson(itemDTO), appKey, e);
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public ItemSkuDTO getItemSku(Long skuId, Long sellerId, String appKey) throws SeckillException {
		try {
			Response<ItemSkuDTO> response = itemSkuClient.getItemSku(skuId, sellerId, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to getItemSku, skuId : {}, sellerId : {}, appKey : {}, errCode : {}, errMsg : {}",
					skuId, sellerId, appKey, response.getCode(), response.getMessage());
			throw new SeckillException(response.getCode(), response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getItemSku, skuId : {}, sellerId : {}, appKey : {}", skuId, sellerId, appKey, e);
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<ItemDTO> queryItem(ItemQTO itemQTO, String appKey) throws SeckillException {
		try {
			Response<List<ItemDTO>> response = itemClient.queryItem(itemQTO, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to queryItem, itemQTO : {}, appKey : {}, errCode : {}, errMsg : {}",
					JsonUtil.toJson(itemQTO), appKey, response.getCode(), response.getMessage());
			throw new SeckillException(response.getCode(), response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to queryItem, itemQTO : {}, appKey : {}", JsonUtil.toJson(itemQTO), appKey, e);
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO, String appKey) throws SeckillException {
		try {
			Response<List<ItemSkuDTO>> response = itemSkuClient.queryItemSku(itemSkuQTO, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to queryItemSku, itemSkuQTO : {}, appKey : {}, errCode : {}, errMsg : {}",
					JsonUtil.toJson(itemSkuQTO), appKey, response.getCode(), response.getMessage());
			throw new SeckillException(response.getCode(), response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to queryItemSku, itemSkuQTO : {}, appKey : {}", JsonUtil.toJson(itemSkuQTO), appKey, e);
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public ItemDTO getItem(Long itemId, Long sellerId, String appKey) throws SeckillException {
		try {
			Response<ItemDTO> response = itemClient.getItem(itemId, sellerId, true, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to getItem, itemId : {}, sellerId : {}, appKey : {}, errCode : {}, errMsg : {}",
					itemId, sellerId, appKey, response.getCode(), response.getMessage());
			throw new SeckillException(response.getCode(), response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getItem, itemId : {}, sellerId : {}, appKey : {}", itemId, sellerId, appKey, e);
			throw new SeckillException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
		}
	}

	@Override
	public MopItemDTO getMopItem(Long itemId, Long sellerId, Boolean needDetail, String appKey) throws SeckillException {
		try {
			Response<MopItemDTO> response = itemClient.getMopItem(itemId, sellerId, needDetail, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to getMopItem, itemId : {}, sellerId : {}, needDetail : {}, appKey : {}, errCode : {}, errMsg : {}",
					itemId, sellerId, needDetail, appKey, response.getCode(), response.getMessage());
			throw new SeckillException(response.getCode(), response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getMopItem, itemId : {}, sellerId : {}, needDetail : {}, appKey : {}",
					itemId, sellerId, needDetail, appKey, e);
			throw new SeckillException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
		}
	}

	@Override
	public void fillUpItem(List<SeckillDTO> seckillDTOs, String appKey) throws SeckillException {
		if (seckillDTOs == null || seckillDTOs.isEmpty()) return;
		LOGGER.info("进来了1");
		Map<Long, SeckillDTO> skuIdKey = new HashMap<>();

		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setIdList(new ArrayList<Long>());
		itemSkuQTO.setNeedImage(1);

		for (SeckillDTO seckillDTO : seckillDTOs) {

			skuIdKey.put(seckillDTO.getSkuId(), seckillDTO);

			itemSkuQTO.getIdList().add(seckillDTO.getSkuId());
		}
		LOGGER.info("进来了2");
		try {
			List<ItemSkuDTO> itemSkuDTOs = queryItemSku(itemSkuQTO, appKey);
			if (itemSkuDTOs == null || itemSkuDTOs.isEmpty()) {
				LOGGER.error("error to queryItemSku, it's empty, itemSkuQTO : {}", JsonUtil.toJson(itemSkuQTO));
				throw new SeckillException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
			}
			LOGGER.info("进来了3");
			SeckillDTO seckillDTO;
			for (ItemSkuDTO itemSkuDTO : itemSkuDTOs) {
				LOGGER.info("进来了4");
				seckillDTO = skuIdKey.get(itemSkuDTO.getId());
				seckillDTO.setIconUrl(itemSkuDTO.getImageUrl());
				seckillDTO.setFrozenNum(itemSkuDTO.getFrozenStockNum());
				LOGGER.info("itemSkuDTO.getFrozenStockNum()冻结库存:"+itemSkuDTO.getFrozenStockNum());
				if (seckillDTO.getCurrentStockNum() == null) {
					LOGGER.info("itemSkuDTO.getStockNum()剩余库存:"+itemSkuDTO.getStockNum());
					seckillDTO.setCurrentStockNum(itemSkuDTO.getStockNum() != null ? itemSkuDTO.getStockNum() : 0L);
					seckillDTO.setSales(seckillDTO.getStockNum() - seckillDTO.getCurrentStockNum());
				} else {
					seckillDTO.setSales(seckillDTO.getStockNum() - seckillDTO.getCurrentStockNum());
				}
			}
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to fill up item", e);
		}
	}

	@Override
	public Boolean copySkuStock(Long itemSkuId, Long itemSkuIdNew, Long stock, String appKey) throws SeckillException {
		try {
			com.mockuai.suppliercenter.common.api.Response<Boolean> response = storeItemSkuClient.copySkuStock(itemSkuId, itemSkuIdNew, stock, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to copySkuStock, itemSkuId : {}, itemSkuIdNew : {}, stockNum : {}, appKey : {}, errCode : {}, errMsg : {}",
					itemSkuId, itemSkuIdNew, stock, appKey, response.getCode(), response.getMessage());
			throw new SeckillException(response.getCode(), response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to copySkuStock, itemSkuId : {}, itemSkuIdNew : {}, stockNum : {}, appKey : {}",
					itemSkuId, itemSkuIdNew, stock, appKey, e);
			throw new SeckillException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
		}
	}

	@Override
	public Boolean copySkuStockReturn(Long itemSkuId, Long itemSkuIdNew, String appKey) throws SeckillException {

		try {
			com.mockuai.suppliercenter.common.api.Response<Boolean> response = storeItemSkuClient.copySkuStockReturn(itemSkuId, itemSkuIdNew, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to copySkuStockReturn, itemSkuId : {}, itemSkuIdNew : {}, appKey : {}, errCode : {}, errMsg : {}",
					itemSkuId, itemSkuIdNew, appKey, response.getCode(), response.getMessage());
			throw new SeckillException(response.getCode(), response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to copySkuStockReturn, itemSkuId : {}, itemSkuIdNew : {}, appKey : {}",
					itemSkuId, itemSkuIdNew, appKey, e);
			throw new SeckillException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
		}
	}

	@Override
	public List<ItemDTO> queryItemCollection(ItemCollectionQTO itemCollectionQTO, String appKey) throws SeckillException {

		try {
			Response<List<ItemDTO>> response = itemCollectionClient.queryItemCollection(itemCollectionQTO, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to queryItemCollection, itemCollectionQTO : {}, appKey : {}, errCode : {}, errMsg : {}",
					JsonUtil.toJson(itemCollectionQTO), appKey, response.getCode(), response.getMessage());
			throw new SeckillException(response.getCode(), response.getMessage());
		} catch (SeckillException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to queryItemCollection, itemCollectionQTO : {}, appKey : {}",
					JsonUtil.toJson(itemCollectionQTO), appKey, e);
			throw new SeckillException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
		}
	}
}