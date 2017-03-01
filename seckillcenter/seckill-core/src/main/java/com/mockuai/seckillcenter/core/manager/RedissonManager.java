package com.mockuai.seckillcenter.core.manager;

import com.mockuai.seckillcenter.core.exception.SeckillException;
import org.redisson.RedissonClient;

/**
 * Created by edgar.zr on 6/08/2016.
 */
public interface RedissonManager {

	RedissonClient getRedissonClient() throws SeckillException;

	String cacheKey(String key) throws SeckillException;
}