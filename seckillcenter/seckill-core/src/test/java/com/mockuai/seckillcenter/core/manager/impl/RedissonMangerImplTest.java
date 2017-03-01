package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.SingleServerConfig;
import org.redisson.core.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by edgar.zr on 6/08/2016.
 */
public class RedissonMangerImplTest
//		extends BaseTest
{

	@Autowired
	private RedissonManager redissonManager;

	@Test
	public void test() {
		Config config = new Config();
		config.setUseLinuxNativeEpoll(false);

		SingleServerConfig serversConfig = config.useSingleServer();
		serversConfig.setAddress("120.27.133.46:6379");
		serversConfig.setPassword("haiynredis");
		serversConfig.setConnectionMinimumIdleSize(10);
		RedissonClient client = Redisson.create(config);

//        RLock lock = client.getLock("name");
//        if (lock.isLocked())
//        lock.lock();
//        if (lock.tryLock()) {
		RMap<String, String> map = client.getMap("aaa");
		map.put("b", "b");
//        }
//        lock.lock();
//        System.err.println(lock.isLocked());
//        lock.unlock();
//        System.err.println(lock.isLocked());
		RMap rMap = client.getMap("aaa");
		rMap.expire(0000L, TimeUnit.DAYS);

		System.err.println(JsonUtil.toJson(rMap));
	}
}