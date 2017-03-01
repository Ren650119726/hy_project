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

/**
 * 
 */
@Service
public class GetGiftsAction extends TransAction {


    @Autowired
    private GiftsPacketManager giftsPacketManager;
    
    @Override
    protected GiftsResponse<GiftsPacketDTO> doTransaction(RequestContext context) throws GiftsException {
    	
    	Long id = (Long) context.getRequest().getParam("giftsID");
    	String appKey = (String) context.getRequest().getParam("appKey");
    	
    	GiftsPacketDTO giftsPacketDTO = giftsPacketManager.getGifts(id);

        return GiftsUtils.getSuccessResponse(giftsPacketDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_GIFTS.getActionName();
    }
}