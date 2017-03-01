package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.core.manager.CacheManager;
import com.mockuai.usercenter.core.util.cache.CacheGate;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.CASValue;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 15/9/28.
 */
@Service
public class CacheManagerImpl implements CacheManager, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(CacheManagerImpl.class);
    private final String host = "558c1e51d6e640c5.m.cnhzaliqshpub001.ocs.aliyuncs.com";//控制台上的“内网地址”
    private final String port = "11211"; //默认端口 11211，不用改
    private final String username = "558c1e51d6e640c5";//控制台上的“访问账号”
    private final String password = "hzkansQWSD123";//邮件中提供的“密码”
    private MemcachedClient memcachedClient;
    private String isLocal = "true";

    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }

    public void afterPropertiesSet() throws Exception {
        try {
            AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(username, password));
            memcachedClient = new MemcachedClient(
                    new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                            .setAuthDescriptor(ad)
                            .build(),
                    AddrUtil.getAddresses(host + ":" + port)
            );
        } catch (Exception e) {
            log.error("ocs client init error", e);
        }
    }

    public void set(String key, int expired, Object obj) {
        if (StringUtils.isBlank(key) || obj == null) {
            return;
        }
        if ("true".equals(isLocal)) {
            CacheGate.put(key, obj);
        } else {
            memcachedClient.set(key, expired, obj);
        }
    }

    public Object get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if ("true".equals(isLocal)) {
            return CacheGate.get(key);
        } else {
            return memcachedClient.get(key);
        }
    }

    public Object getAndTouch(String key, int expired) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        //TODO 超时时间设定，服务异常容错
        CASValue<Object> result = memcachedClient.getAndTouch(key, expired);
        if (result != null) {
            return result.getValue();
        } else {
            return null;
        }
    }

    public void remove(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        memcachedClient.delete(key);
    }
}