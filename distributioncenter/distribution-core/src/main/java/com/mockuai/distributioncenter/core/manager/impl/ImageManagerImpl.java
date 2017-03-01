package com.mockuai.distributioncenter.core.manager.impl;

import com.hanshu.imagecenter.client.ImageClient;
import com.mockuai.distributioncenter.core.manager.ImageManager;
import com.mockuai.imagecenter.common.api.action.Response;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 16/6/1.
 */
@Service
public class ImageManagerImpl implements ImageManager {
    private static final Logger log = LoggerFactory.getLogger(ImageManagerImpl.class);

    @Autowired
    private ImageClient imageClient;

    @Override
    public ImageDTO addShopImage(Long userId, String url, String appKey) {
    	try{
	        Response<ImageDTO> response = imageClient.addShopImage(userId, url, appKey);
	        if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
	            return response.getModule();
	        } else {
	            log.error("add shop image error, errMsg: {}", response.getMessage());
	        }
    	}catch(Exception e){
    		log.error("add shop image error!!!!!!");
    	}
		return null;
    }

    @Override
    public ImageDTO addRecommendImage(Long userId, String url, String appKey) {
    	try{
	        Response<ImageDTO> response = imageClient.addRecommendImage(userId, url, appKey);
	        if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
	            return response.getModule();
	        } else {
	            log.error("add recommend image error, errMsg: {}", response.getMessage());
	        }
    	}catch (Exception e){
    		log.error("add recommend image error!!!!!!");
    	}
    	   return null;
    }

    @Override
    public String getImage(String type, Long id, String appKey) {
    	try{
	        Response<String> response = imageClient.getImage(type, id, appKey);
	        if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
	            return response.getModule();
	        } else {
	            log.error("get image error, errMsg: {}", response.getMessage());
	        }
    	}catch (Exception e){
    		log.error("get image error error!!!!!!");
    	}
    	   return null;
    }
}
