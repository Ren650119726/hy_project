package com.mockuai.giftscenter.core.service.action.gifts;

import com.alibaba.fastjson.JSON;
import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.api.Request;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.dto.ActionGiftDTO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.ActionGiftManager;
import com.mockuai.giftscenter.core.service.RequestContext;
import com.mockuai.giftscenter.core.service.action.TransAction;
import com.mockuai.giftscenter.core.util.GiftsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查询有礼活动
 */
@Service
public class GetActionGiftAction extends TransAction {

    @Autowired
    private ActionGiftManager actionGiftManager;
    
    @Override
    protected GiftsResponse doTransaction(RequestContext context) throws GiftsException {
        Request request = context.getRequest();
        String appKey = (String) request.getParam("appKey");
        int actionType = (int) request.getParam("actionType");
        ActionGiftDTO actionGiftDTO =         actionGiftManager.queryByActionType(actionType,appKey);
        LOGGER.info("actionGiftDTO:{} 查询",JSON.toJSON(actionGiftDTO));
        return GiftsUtils.getSuccessResponse(actionGiftDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ACTION_GIFT.getActionName();
    }
}