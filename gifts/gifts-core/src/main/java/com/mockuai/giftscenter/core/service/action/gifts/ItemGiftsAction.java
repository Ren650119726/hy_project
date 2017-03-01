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
public class ItemGiftsAction extends TransAction {


    @Autowired
    private GiftsPacketManager giftsPacketManager;
    
    @Override
    protected GiftsResponse doTransaction(RequestContext context) throws GiftsException {
    	
    	Long itemId = (Long) context.getRequest().getParam("itemId");
    	
    	Long itemSkuId = (Long) context.getRequest().getParam("itemSkuId");
    	
    	SeckillPreconditions.checkNotNull(itemId, "itemId");
    	GiftsPacketDTO giftsPacketDTOs = new GiftsPacketDTO();
    	giftsPacketDTOs.setGoodsId(itemId);
    	giftsPacketDTOs.setGoodsSkuId(itemSkuId);
    	GiftsPacketDTO giftsPacketDTO = giftsPacketManager.itemGifts(giftsPacketDTOs);

        return GiftsUtils.getSuccessResponse(giftsPacketDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.ITEM_GIFTS.getActionName();
    }
}