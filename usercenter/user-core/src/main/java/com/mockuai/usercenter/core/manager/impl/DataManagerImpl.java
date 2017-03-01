/*package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.datacenter.client.DataClient;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

*//**
 * Created by duke on 15/10/20.
 *//*
@Service
public class DataManagerImpl implements DataManager, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(DataManagerImpl.class);

    @Resource
    private DataClient dataClient;

    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        threadPoolExecutor = new ThreadPoolExecutor(16, 32, 30L*60*1000, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(1024));
    }

    @Override
    public void buriedPoint(final Long sellerId, final String key, final Map<String, String> params, final Long buriedTime, final String appKey)
            throws UserException {
        try {
            threadPoolExecutor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    dataClient.buriedPoint(sellerId, key, params, buriedTime, appKey);
                    return true;
                }
            });
        } catch (Exception e) {
            log.error("buried point failed, errMsg: {}", e.getMessage());
        }
    }
}
*/