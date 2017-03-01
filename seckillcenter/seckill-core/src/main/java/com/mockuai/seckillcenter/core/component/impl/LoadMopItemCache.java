package com.mockuai.seckillcenter.core.component.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.mop.MopItemDTO;
import com.mockuai.itemcenter.common.domain.mop.PropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.seckillcenter.common.constant.ComponentType;
import com.mockuai.seckillcenter.common.constant.ResponseCode;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.component.Component;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.util.Context;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.ITEM_KEY;
import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.SECKILL_KEY;
import static com.mockuai.seckillcenter.common.constant.ComponentType.LOAD_MOP_ITEM_CACHE;

/**
 * 加载秒杀商品数据，同时判断用户是否有对当前商品收藏
 * Created by edgar.zr on 8/05/2016.
 */
@org.springframework.stereotype.Component
public class LoadMopItemCache implements Component {

	public static final Logger LOGGER = LoggerFactory.getLogger(LoadMopItemCache.class);
	@Autowired
	private RedissonManager redissonManager;
	@Autowired
	private ItemManager itemManager;
	@Autowired
	private SeckillManager seckillManager;

	public static Context wrapParams(Long itemId, Long sellerId, Long userId, Long distributorId, String bizCode, String appKey) {

		Context context = new Context();
		context.setParam("itemId", itemId);
		context.setParam("sellerId", sellerId);
		context.setParam("userId", userId);
		context.setParam("distributorId", distributorId);
		context.setParam("bizCode", bizCode);
		context.setParam("appKey", appKey);
		context.setParam("component", LOAD_MOP_ITEM_CACHE);
		return context;
	}

	@Override
	public void init() {

	}

	@Override
	public MopItemDTO execute(Context context) throws SeckillException {

		Long itemId = (Long) context.getParam("itemId");
		Long sellerId = (Long) context.getParam("sellerId");
		String appKey = (String) context.getParam("appKey");
		Long userId = (Long) context.getParam("userId");
		Long distributorId = (Long) context.getParam("distributorId");
		String bizCode = (String) context.getParam("bizCode");

		RedissonClient client = redissonManager.getRedissonClient();

		RMapCache itemUidMap = client.getMapCache(ITEM_KEY.getValue());
		MopItemDTO mopItemDTO = itemManager.getMopItem(itemId, sellerId, true, appKey);

		if (mopItemDTO == null) {
			LOGGER.error("the item doesn't exist, itemId : {}, sellerId : {}, appKey : {}",
					itemId, sellerId, appKey);
			throw new SeckillException(ResponseCode.BIZ_E_ITEM_OF_SECKILL_NOT_EXIST);
		}

		if (userId != null) {

			ItemCollectionQTO itemCollectionQTO = new ItemCollectionQTO();
			itemCollectionQTO.setUserId(userId);
			itemCollectionQTO.setDistributorId(distributorId);
			itemCollectionQTO.setItemId(itemId);

			List<ItemDTO> itemDTOs = itemManager.queryItemCollection(itemCollectionQTO, appKey);
			LOGGER.debug("collections : {}", JsonUtil.toJson(itemDTOs));
			String collectV = "0";

			if (itemDTOs != null && !itemDTOs.isEmpty()) {
				collectV = "1";
			}
			PropertyDTO bizProperty;
			List<PropertyDTO> bizPropertyDTOList = mopItemDTO.getBizPropertyList();
			if (bizPropertyDTOList == null) {
				bizPropertyDTOList = new ArrayList<>(1);
				mopItemDTO.setBizPropertyList(bizPropertyDTOList);
			} else {
				Iterator<PropertyDTO> iterator = bizPropertyDTOList.iterator();
				while (iterator.hasNext()) {
					bizProperty = iterator.next();
					if ("IC_SYS_P_BIZ_000001".equals(bizProperty.getCode()) && "collect_status".equals(bizProperty.getName())) {
						iterator.remove();
						break;
					}
				}
			}
			bizProperty = new PropertyDTO();
			bizPropertyDTOList.add(bizProperty);
			bizProperty.setName("collect_status");
			bizProperty.setCode("IC_SYS_P_BIZ_000001");
			bizProperty.setValueType(1);
			bizProperty.setValue(collectV);
		}

		SeckillDO getSeckill = new SeckillDO();
		getSeckill.setItemId(itemId);
		SeckillDTO seckillDTO = seckillManager.getSeckill(getSeckill);

		mopItemDTO.setItemExtraInfo(redissonManager.cacheKey(SECKILL_KEY.getKey(bizCode, sellerId, seckillDTO.getId())));

		itemUidMap.fastPut(redissonManager.cacheKey(ITEM_KEY.getKey(bizCode, sellerId, itemId))
				, mopItemDTO, SeckillUtils.expireTime(), TimeUnit.MILLISECONDS);

		return mopItemDTO;
	}

	@Override
	public String getComponentCode() {
		return ComponentType.LOAD_MOP_ITEM_CACHE.getCode();
	}
}