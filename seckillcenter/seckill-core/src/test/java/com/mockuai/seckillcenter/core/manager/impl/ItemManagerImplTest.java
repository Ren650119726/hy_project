package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.mop.MopItemDTO;
import com.mockuai.seckillcenter.core.BaseTest;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 6/17/2016.
 */
public class ItemManagerImplTest extends BaseTest {

	@Autowired
	private ItemManager itemManager;

	@Test
	public void testCopySkuStock() throws Exception {
		Long originSkuId = 43454L;
		Long skuId = 43457L;
		itemManager.copySkuStock(originSkuId, skuId, 10L, "27c7bc87733c6d253458fa8908001eef");

	}

	@Test
	public void testCopySkuStockReturn() throws Exception {
		Long originSkuId = 43454L;
		Long skuId = 43457L;
		itemManager.copySkuStockReturn(originSkuId, skuId, "27c7bc87733c6d253458fa8908001eef");

	}

	@Test
	public void testGetSku() {
		Long skuId = 18899L;
		Long sellerId = 1841254L;
		try {
			ItemSkuDTO itemSkuDTO = itemManager.getItemSku(skuId, sellerId, "27c7bc87733c6d253458fa8908001eef");
			System.err.println(JsonUtil.toJson(itemSkuDTO));
		} catch (SeckillException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetItem() {
		Long itemId = 29195L;
		Long sellerId = 1841254L;
		try {
			MopItemDTO itemDTO = itemManager.getMopItem(itemId, sellerId, true, "27c7bc87733c6d253458fa8908001eef");
			System.err.println(JsonUtil.toJson(itemDTO));
		} catch (SeckillException e) {
			e.printStackTrace();
		}
	}
}