package com.mockuai.giftscenter.core.service.action.gifts;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.api.Request;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.qto.ActionGiftQTO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.ActionGiftManager;
import com.mockuai.giftscenter.core.service.RequestContext;
import com.mockuai.giftscenter.core.service.action.TransAction;
import com.mockuai.giftscenter.core.util.GiftsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  编辑有礼活动
 */
@Service
public class EditActionGiftAction extends TransAction {

    @Autowired
    private ActionGiftManager actionGiftManager;
    
    @Override
    protected GiftsResponse doTransaction(RequestContext context) throws GiftsException {
        Request request = context.getRequest();
        ActionGiftQTO actionGiftQTO = (ActionGiftQTO) request.getParam("actionGiftQTO");
        long actionId ;
        try {
            if(actionGiftQTO.getId() == null){
                actionId = actionGiftManager.save(actionGiftQTO);
            }else{
                actionGiftManager.update(actionGiftQTO);
                actionId = actionGiftQTO.getId();
            }

        }catch (GiftsException e){
            LOGGER.error(e.getMessage());
            return GiftsUtils.getFailResponse(e.getCode(),e.getMessage());
        }

        return GiftsUtils.getSuccessResponse(actionId);
    }

    @Override
    public String getName() {
        return ActionEnum.EDIT_ACTION_GIFT.getActionName();
    }
}