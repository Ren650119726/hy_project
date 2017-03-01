package com.mockuai.giftscenter.core.service.action.gifts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.giftscenter.common.domain.qto.GiftsPacketQTO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.GiftsPacketManager;
import com.mockuai.giftscenter.core.service.RequestContext;
import com.mockuai.giftscenter.core.service.action.TransAction;
import com.mockuai.giftscenter.core.util.GiftsUtils;

/**
 * 
 */
@Service
public class QueryGiftsAction extends TransAction {


    @Autowired
    private GiftsPacketManager giftsPacketManager;
    
    @Override
    protected GiftsResponse doTransaction(RequestContext context) throws GiftsException {
    	
    	GiftsPacketQTO giftsPacketQTO = (GiftsPacketQTO) context.getRequest().getParam("giftsPacketQTO");
    	
    	
         List<GiftsPacketDTO>  list =   giftsPacketManager.queryGiftsPacket(giftsPacketQTO);

        return GiftsUtils.getSuccessResponse(list,giftsPacketQTO.getTotalCount());
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_GIFTS.getActionName();
    }
}