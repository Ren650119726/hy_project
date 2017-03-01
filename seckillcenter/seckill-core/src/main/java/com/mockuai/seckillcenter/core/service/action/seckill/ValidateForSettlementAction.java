package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.seckillcenter.common.api.SeckillResponse;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.constant.OrderOfUserStatus;
import com.mockuai.seckillcenter.common.constant.ResponseCode;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.manager.TradeManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.TransAction;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import com.mockuai.seckillcenter.core.util.SeckillPreconditions;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.USER_KEY;

/**
 * 
 * 结算验证秒杀
 * Created by edgar.zr on 12/15/15.
 */
@Service
public class ValidateForSettlementAction extends TransAction {

	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private TradeManager tradeManager;
	@Autowired
	private RedissonManager redissonManager;

	@Override
	protected SeckillResponse doTransaction(RequestContext context) throws SeckillException {

		Long skuId = (Long) context.getRequest().getParam("skuId");
		Long sellerId = (Long) context.getRequest().getParam("sellerId");
		Long userId = (Long) context.getRequest().getParam("userId");
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");

		SeckillPreconditions.checkNotNull(skuId, "skuId");
		SeckillPreconditions.checkNotNull(sellerId, "sellerId");
		SeckillPreconditions.checkNotNull(userId, "userId");

		SeckillDO seckillDO = new SeckillDO();
		seckillDO.setBizCode(bizCode);
		seckillDO.setSkuId(skuId);

		SeckillDTO seckillDTO = seckillManager.getSeckill(seckillDO);

		// 缓存验证
		RedissonClient client = redissonManager.getRedissonClient();
		RMapCache seckillUserMap = client.getMapCache(USER_KEY.getValue());
		Integer status = (Integer) seckillUserMap.get(
				redissonManager.cacheKey(USER_KEY.getKey(bizCode, seckillDTO.getSellerId(), seckillDTO.getId(), userId)));
		if (status == null || status.intValue() != OrderOfUserStatus.PRE_ORDER.getValue()) { // 用户存在预单,则用户可以继续交易流程,去结算
			LOGGER.error("cannot get the preOrder in cache, cacheKey : {}"
					, redissonManager.cacheKey(USER_KEY.getKey(bizCode, seckillDTO.getSellerId(), seckillDTO.getId(), userId)));
			return SeckillUtils.getFailResponse(ResponseCode.BIZ_E_SECKILL_WITHOUT_PRE_ORDER);
		}

		// 交易平台验证
		OrderQTO orderQTO = new OrderQTO();
		orderQTO.setUserId(userId);
		orderQTO.setItemId(seckillDTO.getItemId());
		orderQTO.setItemSkuId(seckillDTO.getSkuId());

		if (!tradeManager.queryPreOrder(orderQTO, appKey)) {
			LOGGER.error("can not get preOrder in trade, orderQTO : {}", JsonUtil.toJson(orderQTO));
			return SeckillUtils.getFailResponse(ResponseCode.BIZ_E_SECKILL_WITHOUT_PRE_ORDER);
		}

		return new SeckillResponse(seckillDTO);
	}

	@Override
	public String getName() {
		return ActionEnum.VALIDATE_FOR_SETTLEMENT.getActionName();
	}
}