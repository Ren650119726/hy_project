package com.mockuai.giftscenter.core.service.action.gifts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketProfitDTO;
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
public class GiftsPointsAction extends TransAction {


    @Autowired
    private GiftsPacketManager giftsPacketManager;
    
    @Override
    protected GiftsResponse doTransaction(RequestContext context) throws GiftsException {
    	
    	Long itemId = (Long) context.getRequest().getParam("itemId");
    	
    	Long levelId = (Long) context.getRequest().getParam("levelId");
    	
    	SeckillPreconditions.checkNotNull(itemId, "itemId");
    	
    	GiftsPacketProfitDTO giftsPacketProfitDTO = giftsPacketManager.giftsPoints(itemId, levelId);
        return GiftsUtils.getSuccessResponse(giftsPacketProfitDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GIFTS_POINTS.getActionName();
    }
}