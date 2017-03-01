package com.mockuai.messagecenter.core.manager.impl;


import net.spy.memcached.AddrUtil;
import net.spy.memcached.CASValue;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.ops.OperationStatus;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.messagecenter.core.manager.CacheManager;
import com.mockuai.messagecenter.core.util.cache.CacheGate;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by zengzhangqiang on 6/11/15.
 */
public class CacheManagerImpl implements CacheManager {
    private static final Logger log = LoggerFactory.getLogger(CacheManagerImpl.class);

    private MemcachedClient memcachedClient;
    private final String host = "558c1e51d6e640c5.m.cnhzaliqshpub001.ocs.aliyuncs.com";//控制台上的“内网地址”
    private final String port ="11211"; //默认端口 11211，不用改
    private final String username = "558c1e51d6e640c5";//控制台上的“访问账号”
    private final String password = "hzkansQWSD123";//邮件中提供的“密码”
    private String isLocal = "true";
    
    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }
    
    public void init(){
        try{
            //TODO 断开重连机制确认
            AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(username, password));
            memcachedClient = new MemcachedClient(
                    new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                            .setAuthDescriptor(ad)
                            .build(),
                    AddrUtil.getAddresses(host + ":" + port));
        }catch(Exception e){
            log.error("ocs client init error", e);
        }
    }

    @Override
    public void set(String key, int expired, Object obj){
        if(StringUtils.isBlank(key) || obj==null){
            return;
        }
        if ("true".equals(isLocal)) {
            CacheGate.put(key, obj);
        } else {
        	//TODO expired时间单位确认，超时时间设定，服务异常容错，同步/异步设置处理
            OperationFuture<Boolean> opResult = memcachedClient.set(key, expired, obj);
            OperationStatus operationStatus = opResult.getStatus();
            if(operationStatus.isSuccess() == false){
                log.error("[CACHE_TRACE] error to set, key:{}, errorMsg:{}", key, operationStatus.getMessage());
            }
        }
    }

    @Override
    public Object get(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        //TODO 确认这里的GET是否会TOUCH缓存
        //TODO 超时时间设定，服务异常容错
        if ("true".equals(isLocal)) {
            return CacheGate.get(key);
        } else{
        	return memcachedClient.get(key);
        }
        
    }

    @Override
    public void remove(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if("true".equals(isLocal)){
        	CacheGate.remove(key);
        }else{
        	memcachedClient.delete(key);
        }
        
    }

    @Override
    public Object getAndTouch(String key, int expired) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        //TODO 超时时间设定，服务异常容错
        CASValue<Object> result = memcachedClient.getAndTouch(key, expired);
        if(result != null){
            return result.getValue();
        }else{
            return null;
        }
    }
    
    public static void main(String[] args) {

        final String host = "0c300bb107854a84.m.cnhzalicm10pub001.ocs.aliyuncs.com";//控制台上的“内网地址”
        final String port ="11211"; //默认端口 11211，不用改
        final String username = "0c300bb107854a84";//控制台上的“访问账号”
        final String password = "HZmkocs001";//邮件中提供的“密码”


        MemcachedClient cache = null;
        try {
            AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(username, password));
            cache = new MemcachedClient(
                    new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                            .setAuthDescriptor(ad)
                            .build(),
                    AddrUtil.getAddresses(host + ":" + port));

            System.out.println("OCS Sample Code");

            //向OCS中存一个key为"ocs"的数据，便于后面验证读取数据
            OperationFuture future = cache.set("ocs", 1000," Open Cache Service,  from www.Aliyun.com");

            //向OCS中存若干个数据，随后可以在OCS控制台监控上看到统计信息
            for(int i=0;i<100;i++){
                String key="key-"+i;
                String value="value-"+i;

                //执行set操作，向缓存中存数据
                cache.set(key, 1000, value);
                future.get();  //  确保之前(mc.set())操作已经结束
            }


            System.out.println("Set操作完成!");



            //执行get操作，从缓存中读数据,读取key为"ocs"的数据
            System.out.println("Get操作:"+cache.get("ocs"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (cache != null) {
            cache.shutdown();
        }

    }//eof
}