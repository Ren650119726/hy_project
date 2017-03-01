package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.SingleServerConfig;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by edgar.zr on 6/08/2016.
 */
public class RedissonMangerImpl implements RedissonManager, InitializingBean {

	private String host;
	private String port;
	private String user;
	private String password;

	private String env;

	private RedissonClient redissonClient;

	public void setEnv(String env) {
		this.env = env;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Config config = new Config();
		config.setUseLinuxNativeEpoll(false);

		SingleServerConfig serversConfig = config.useSingleServer();
//		if (StringUtils.isNotBlank(getUser())) {
//			serversConfig.setAddress(getHost() + ":" + getPort());
//			serversConfig.setPassword(getUser() + ":" + getPassword());
//			serversConfig.setConnectionMinimumIdleSize(80);
		// max 1000
//			serversConfig.setConnectionPoolSize(600);
//		} else {
		serversConfig.setAddress(getHost() + ":" + getPort());
		if (StringUtils.isNotBlank(getPassword())) {
			serversConfig.setPassword(getPassword());
		}
		redissonClient = Redisson.create(config);
	}

	@Override
	public RedissonClient getRedissonClient() throws SeckillException {
		return redissonClient;
	}

	@Override
	public String cacheKey(String key) throws SeckillException {
		return key + "_" + env;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}