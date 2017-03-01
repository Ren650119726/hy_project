package com.mockuai.giftscenter.core.service.action.gifts;

import java.util.List;

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
public class AppQueryGiftsAction extends TransAction {


    @Autowired
    private GiftsPacketManager giftsPacketManager;
    
    @Override
    protected GiftsResponse doTransaction(RequestContext context) throws GiftsException {
    	 String appKey = (String) context.getRequest().getParam("appKey");
    	 List<GiftsPacketDTO>  list =   giftsPacketManager.appQueryGiftsPacket(appKey);

        return GiftsUtils.getSuccessResponse(list);
    }

    @Override
    public String getName() {
        return ActionEnum.APP_QUERY_GIFTS.getActionName();
    }
}