package com.mockuai.distributioncenter.core.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.core.manager.HeadsingleManager;
import com.mockuai.headsinglecenter.client.HeadSingleSubClient;
import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;

/**
 * Created by duke on 16/6/1.
 */
@Service
public class HeadsingleManagerImpl implements HeadsingleManager {
    private static final Logger log = LoggerFactory.getLogger(HeadsingleManagerImpl.class);

    @Autowired
    private HeadSingleSubClient headSingleSubClient;

    @Override
    public HeadSingleSubDTO queryHeadSingleSubById(Long id, String appKey) {
    	try{
	        Response<HeadSingleSubDTO> response =  headSingleSubClient.queryHeadSingleSubById(id, appKey);
	        if (response.isSuccess()) {
	            return response.getModule();
	        } else {
	            log.error("queryHeadSingleSubById: {}", response.getMessage());
	        }
    	}catch(Exception e){
    		log.error("query queryHeadSingleSubById:{}",e);
    	}
		return null;
    }

}
