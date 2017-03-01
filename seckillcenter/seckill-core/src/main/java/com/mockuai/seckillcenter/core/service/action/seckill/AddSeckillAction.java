package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemLabelDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.LimitEntity;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.seckillcenter.common.api.SeckillResponse;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.constant.ResponseCode;
import com.mockuai.seckillcenter.common.constant.SeckillStatus;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.TransAction;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import com.mockuai.seckillcenter.core.util.ModelUtil;
import com.mockuai.seckillcenter.core.util.SeckillPreconditions;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import org.redisson.RedissonClient;
import org.redisson.core.RSemaphore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.SECKILL_COUNT;

/**
 * 添加秒杀
 * 创建秒杀商品，移动对应的库存到秒杀商品上
 * 初始化参与人数缓存
 * Created by edgar.zr on 12/4/15.
 */
@Service
public class AddSeckillAction extends TransAction {

	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private ItemManager itemManager;
	@Autowired
	private RedissonManager redissonManager;

	@Override
	protected SeckillResponse doTransaction(RequestContext context) throws SeckillException {
		SeckillDTO seckillDTO = (SeckillDTO) context.getRequest().getParam("seckillDTO");
		String appKey = (String) context.get("appKey");
		String bizCode = (String) context.get("bizCode");
		SeckillPreconditions.checkNotNull(seckillDTO, "seckillDTO");

		seckillDTO.setBizCode(bizCode);
		SeckillPreconditions.checkNotNull(seckillDTO.getSellerId(), "sellerId");
		SeckillPreconditions.checkNotNull(seckillDTO.getItemId(), "itemId");
		SeckillPreconditions.checkNotNull(seckillDTO.getSkuId(), "skuId");
		SeckillPreconditions.checkNotNull(seckillDTO.getStartTime(), "startTime");
		SeckillPreconditions.checkNotNull(seckillDTO.getEndTime(), "endTime");
		if (seckillDTO.getEndTime().before(seckillDTO.getStartTime())) {
			return new SeckillResponse(ResponseCode.PARAMETER_ERROR, "活动时间不合法");//活动结束页面是什么效果
		}

		seckillDTO.setItemSellerId(seckillDTO.getItemSellerId() == null
				                           ? seckillDTO.getSellerId() : seckillDTO.getItemSellerId());

		createItem(seckillDTO, appKey);

		Long seckillId = seckillManager.addSeckill(ModelUtil.genSeckillDO(seckillDTO));

		Date now = new Date();
		long expireTime = SeckillUtils.expireTime(seckillDTO.getEndTime(), now);

		RedissonClient client = redissonManager.getRedissonClient();
		RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, seckillDTO.getSellerId(), seckillId)));
		seckillCount.expire(expireTime, TimeUnit.MILLISECONDS);
		seckillCount.setPermits(seckillDTO.getStockNum().intValue());

		return SeckillUtils.getSuccessResponse(seckillId);
	}

	/**
	 * 创建秒杀商品
	 *
	 * @param seckillDTO
	 * @return 秒杀商品 id
	 * @throws SeckillException
	 */
	public Long createItem(SeckillDTO seckillDTO, String appKey) throws SeckillException {

		ItemDTO itemDTO;
		ItemSkuDTO itemSkuDTO = null;
		try {
			itemDTO = itemManager.getItem(seckillDTO.getItemId(), seckillDTO.getItemSellerId(), appKey);
			if (itemDTO == null) {
				LOGGER.error("invalidate item, itemId : {}, sellerId : {}, appKey : {}",
						seckillDTO.getItemId(), seckillDTO.getItemSellerId(), appKey);
				throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
			}
			for (ItemSkuDTO skuDTO : itemDTO.getItemSkuDTOList()) {
				if (skuDTO.getId().longValue() == seckillDTO.getSkuId()) {
					itemSkuDTO = skuDTO;
					break;
				}
			}

			if (itemSkuDTO == null) {
				LOGGER.error("invalidate sku, skuId : {}, sellerId : {}, appKey : {}",
						seckillDTO.getSkuId(), seckillDTO.getItemSellerId(), appKey);
				throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
			}
			seckillDTO.setOriginItemId(seckillDTO.getItemId());
			seckillDTO.setOriginSkuId(seckillDTO.getSkuId());
			seckillDTO.setItemName(itemDTO.getItemName());
			seckillDTO.setMarketPrice(itemSkuDTO.getMarketPrice()); // 原价
			seckillDTO.setOriginStockNum(itemSkuDTO.getStockNum()); // 原商品库存
			seckillDTO.setContent(itemDTO.getItemName());
			seckillDTO.setStatus(SeckillStatus.NORMAL.getValue());
		} catch (SeckillException e) {
			LOGGER.error("error to validate targetItem in addSeckill, seckillDTO : {}", JsonUtil.toJson(seckillDTO));
			throw new SeckillException(ResponseCode.BIZ_E_INVALIDATE_TARGET_ITEM);
		}
		if (seckillDTO.getStockNum() > seckillDTO.getOriginStockNum()) {
			throw new SeckillException(ResponseCode.BIZ_E_STOCK_NUM_OUT_OF_ITEM);
		}

		try {
			itemDTO.setItemType(DBConst.SECKILL_ITEM.getCode());
			itemDTO.setSaleBegin(null);
			itemDTO.setSaleEnd(null);
			itemDTO.setCreateTime(null);
			if (itemDTO.getItemImageDTOList() != null) {
				for (ItemImageDTO imageDTO : itemDTO.getItemImageDTOList()) {
					imageDTO.setId(null);
				}
			}
//			if (itemDTO.getItemLabelDTOList() != null) {
//				for (ItemLabelDTO itemLabelDTO : itemDTO.getItemLabelDTOList()) {
//					itemLabelDTO.setId(null);
//				}
//			}
			itemDTO.setItemLabelDTOList(null);
			itemDTO.setItemPropertyList(null);
			//itemDTO.setCompositeItem(null);
			// startTime/endTime 不传
			itemDTO.setItemStatus(4);
			itemDTO.setValueAddedServiceTypeDTOList(null);
			List<LimitEntity> limitEntities = new ArrayList<LimitEntity>();
			LimitEntity limitEntity = new LimitEntity();
			limitEntity.setLimitCount(seckillDTO.getLimit());
			limitEntities.add(limitEntity);
			itemDTO.setBuyLimit(limitEntities);
			itemDTO.setSaleMaxNum(seckillDTO.getLimit().intValue() == 0 ? null : seckillDTO.getLimit());
			List<ItemSkuDTO> itemSkuDTOs = new ArrayList<ItemSkuDTO>();
			// 原价是 market_price
			itemSkuDTO.setPromotionPrice(seckillDTO.getPrice()); // 秒杀价
			itemSkuDTO.setWirelessPrice(seckillDTO.getPrice());
			itemSkuDTO.setFrozenStockNum(0L);
			itemSkuDTO.setSoldNum(0L);

			itemDTO.setPromotionPrice(seckillDTO.getPrice());
			itemDTO.setWirelessPrice(seckillDTO.getPrice());
			itemSkuDTO.setStockNum(0L);
			itemSkuDTOs.add(itemSkuDTO);
			itemDTO.setItemSkuDTOList(itemSkuDTOs);
			itemDTO.setStockNum(0L);
			itemDTO.setFrozenStockNum(0L);
			if (itemDTO.getFreightTemplate() == null && itemDTO.getFreight() == null) {
				itemDTO.setFreight(0L);
			}
			itemDTO.setId(null);
			itemDTO.getItemSkuDTOList().get(0).setId(null);
			if (itemSkuDTO.getSkuPropertyDTOList() != null) {
				for (SkuPropertyDTO skuPropertyDTO : itemSkuDTO.getSkuPropertyDTOList()) {
					skuPropertyDTO.setId(null);
				}
			}
			LOGGER.info("[{}]itemDTO--------------------------:"+JsonUtil.toJson(itemDTO));
			itemDTO = itemManager.addItem(itemDTO, appKey);
			if (itemDTO == null || itemDTO.getItemSkuDTOList() == null || itemDTO.getItemSkuDTOList().isEmpty())
				throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
			seckillDTO.setItemId(itemDTO.getId()); // 秒杀活动商品信息
			seckillDTO.setSkuId(itemDTO.getItemSkuDTOList().get(0).getId());

			// 移动库存
			Boolean result = itemManager.copySkuStock(seckillDTO.getOriginSkuId(), seckillDTO.getSkuId(), seckillDTO.getStockNum(), appKey);
			if (!result) {
				LOGGER.error("error to move stock num, originItemId : {}, itemId : {}, stockNum : {}"
						, seckillDTO.getOriginSkuId(), seckillDTO.getSkuId(), seckillDTO.getStockNum());
				throw new SeckillException(ResponseCode.BIZ_E_MOVE_STOCK_NUM);
			}
		} catch (SeckillException e) {
			LOGGER.error("error to add item in addSeckill, seckillDTO : {}", JsonUtil.toJson(seckillDTO), e);
			throw new SeckillException(ResponseCode.BIZ_E_ADD_TARGET_ITEM);
		}
		return itemDTO.getId();
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_SECKILL.getActionName();
	}
}