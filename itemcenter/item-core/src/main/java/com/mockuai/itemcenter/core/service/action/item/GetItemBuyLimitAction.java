package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemBuyLimitManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 限购;
 * Created by luliang on 15/7/17.
 */
@Service
public class GetItemBuyLimitAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetItemBuyLimitAction.class);

    @Resource
    private ItemBuyLimitManager itemBuyLimitManager;

    @Override
    public ItemResponse execute(RequestContext context) {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        Long sellerId = request.getLong("sellerId");
        Long itemId = request.getLong("itemId");
        if(sellerId == null || itemId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId and itemId could not be null.");
        }
        Integer itemBuyLimitCount = null;
        try {
            itemBuyLimitCount = itemBuyLimitManager.queryItemBuyLimit(sellerId, itemId);
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
        response = ResponseUtil.getSuccessResponse(itemBuyLimitCount);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ITEM_BUY_LIMIT.getActionName();
    }
}
