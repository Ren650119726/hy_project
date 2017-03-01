package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketProfitDTO;

/**
 * Created by duke on 16/5/23.
 */
public interface GiftsManager {
    GiftsPacketProfitDTO getGiftsPoint(Long itemId, Long levelId, String appKey) throws DistributionException;
    
    void grantActionGift(Long receiverId,String mobile ,int appType,int actionType, String appKey)throws DistributionException;
}
