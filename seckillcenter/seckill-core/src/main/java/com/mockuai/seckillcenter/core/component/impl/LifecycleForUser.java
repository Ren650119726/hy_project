package com.mockuai.seckillcenter.core.component.impl;

import com.mockuai.seckillcenter.common.constant.ComponentType;
import com.mockuai.seckillcenter.common.constant.OrderOfUserStatus;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.component.Component;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.util.Context;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.USER_KEY;
import static com.mockuai.seckillcenter.common.constant.ComponentType.LIFECYCLE_FOR_USER;

/**
 * 查询用户是否有预单存在
 * Created by edgar.zr on 6/17/2016.
 */
@org.springframework.stereotype.Component
public class LifecycleForUser implements Component {

	public static final Logger LOGGER = LoggerFactory.getLogger(LifecycleForUser.class);

	@Autowired
	private RedissonManager redissonManager;

	public static Context wrapParams(SeckillDTO seckillDTO, Long userId, String bizCode) {

		Context context = new Context();
		context.setParam("seckillDTO", seckillDTO);
		context.setParam("userId", userId);
		context.setParam("bizCode", bizCode);
		context.setParam("component", LIFECYCLE_FOR_USER);
		return context;
	}

	@Override
	public void init() {

	}

	@Override
	public <T> T execute(Context context) throws SeckillException {
		String bizCode = (String) context.getParam("bizCode");
		Long userId = (Long) context.getParam("userId");
		SeckillDTO seckillDTO = (SeckillDTO) context.getParam("seckillDTO");

		RedissonClient client = redissonManager.getRedissonClient();
		RMapCache seckillUserMap = client.getMapCache(USER_KEY.getValue());
		Integer status = (Integer) seckillUserMap.get(
				redissonManager.cacheKey(USER_KEY.getKey(bizCode, seckillDTO.getSellerId(), seckillDTO.getId(), userId)));
//		LOGGER.info("preOrderInfo : {}, status : {}"
//				, USER_KEY.getKey(bizCode, seckillDTO.getSellerId(), seckillDTO.getId(), userId), status);
		if (status != null && status.intValue() == OrderOfUserStatus.PRE_ORDER.getValue()) { // 用户存在预单,则用户可以继续交易流程,去结算
			seckillDTO.setLifecycle(11);
		}
		return null;
	}

	@Override
	public String getComponentCode() {
		return ComponentType.LIFECYCLE_FOR_USER.getCode();
	}
}