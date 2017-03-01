package com.mockuai.giftscenter.core.service.action.gifts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.GiftsPacketManager;
import com.mockuai.giftscenter.core.service.RequestContext;
import com.mockuai.giftscenter.core.service.action.TransAction;
import com.mockuai.giftscenter.core.util.GiftsUtils;
import com.mockuai.giftscenter.core.util.SeckillPreconditions;

/**
 * 
 */
@Service
public class UpdateGiftsAction extends TransAction {


    @Autowired
    private GiftsPacketManager giftsPacketManager;
    
    @Override
    protected GiftsResponse doTransaction(RequestContext context) throws GiftsException {
    	
    	GiftsPacketDTO giftsPacketDTO = (GiftsPacketDTO) context.getRequest().getParam("giftsPacketDTO");
    	String appKey = (String) context.getRequest().getParam("appKey");
    	Long sellerId = (Long) context.getRequest().getParam("sellerId");
    	SeckillPreconditions.checkNotNull(giftsPacketDTO, "giftsPacketDTO");
    	
    	Integer asd = giftsPacketManager.updateGiftsPacket(giftsPacketDTO,appKey,sellerId);

        return GiftsUtils.getSuccessResponse(asd);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_GIFTS.getActionName();
    }
}