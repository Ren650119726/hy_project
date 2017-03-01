package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.datacenter.client.DataClient;
import com.mockuai.datacenter.common.api.Response;
import com.mockuai.itemcenter.core.manager.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yindingyu on 16/1/27.
 */
@Service
public class DataManagerImpl implements DataManager {

    private  static  final Logger LOG = LoggerFactory.getLogger(DataManagerImpl.class);

    @Resource
    private DataClient dataClient;

    public void buriedPoint(Long sellerId, String key, Map<String, String> params, Long buriedTime, String appKey){

        Response<Boolean> response;

        try {
            response = dataClient.buriedPoint(sellerId, key, params, buriedTime, appKey);

            if(!response.isSuccess()){
                LOG.error("埋点时出现异常 sellerId {} key {} code {} message {}" ,sellerId,key,response.getCode(),response.getMessage());
            }

        }catch (Exception e){
            LOG.error("埋点时出现异常 sellerId {} key {} message {}" ,sellerId,key,e.getMessage());
        }

    }
}
