package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.GiftsManager;
import com.mockuai.giftscenter.client.GiftsClient;
import com.mockuai.giftscenter.common.api.Response;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketProfitDTO;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by duke on 16/5/23.
 */
@Component
public class GiftsManagerImpl implements GiftsManager {
    private static final Logger log = LoggerFactory.getLogger(GiftsManagerImpl.class);

    @Autowired
    private GiftsClient giftsClient;

    @Override
    public GiftsPacketProfitDTO getGiftsPoint(Long itemId, Long levelId, String appKey) throws DistributionException {
        Response<GiftsPacketProfitDTO> response = giftsClient.giftsPoints(itemId, levelId, appKey);
        if (!response.isSuccess()) {
            log.error("get gift by itemId: {}, levelId: {} error: {}", itemId, levelId, response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION, response.getMessage());
        } else {
            return response.getModule();
        }
    }
    
    
    @Override
    public void grantActionGift(Long receiverId,String mobile ,int appType,int actionType, String appKey) throws DistributionException {
    	try{
    		 giftsClient.grantActionGift(receiverId, mobile, appType, appType, appKey);
    	}catch(Exception e){
    		log.error(" giftsClient grantActionGift Call error,receiverId {} ",receiverId);
    	}
    }
}
