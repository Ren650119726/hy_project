package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.itemcenter.common.domain.mop.MopItemDTO;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.core.BaseActionTest;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.ITEM_KEY;

/**
 * Created by edgar.zr on 12/15/15.
 */
public class DetailOfSeckillByItemActionTest extends BaseActionTest {

	@Autowired
	private RedissonManager redissonManager;

	public DetailOfSeckillByItemActionTest() {
		super(DetailOfSeckillByItemActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.DETAIL_OF_SECKILL_BY_ITEM.getActionName();
	}

	@Test
	public void test() {
		Long itemId = 29196L;
		Long distributorId = 1842012L;
		Long sellerId = 1841254L;
		Long userId = 111L;

		request.setParam("itemId", itemId);
		request.setParam("distributorId", distributorId);
		request.setParam("sellerId", sellerId);
		request.setParam("userId", userId);

		doExecute();
	}

	@Test
	public void getDetailFromRedis() {
		RedissonClient client = null;
		try {
			client = redissonManager.getRedissonClient();
		} catch (SeckillException e) {
			e.printStackTrace();
		}

		RMapCache itemUidMap = client.getMapCache(ITEM_KEY.getValue());
		try {
			MopItemDTO mopItemDTO = (MopItemDTO) itemUidMap.get(redissonManager.cacheKey(ITEM_KEY.getKey("hanshu", 1841254L, 2289L)));
			System.err.println(JsonUtil.toJson(mopItemDTO));
		} catch (SeckillException e) {
			e.printStackTrace();
		}

	}
}