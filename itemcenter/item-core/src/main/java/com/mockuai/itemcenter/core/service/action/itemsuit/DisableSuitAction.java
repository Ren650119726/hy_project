package com.mockuai.itemcenter.core.service.action.itemsuit;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSuitManager;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 使套装失效，置为失效状态，同时下架套装商品
 */
@Service
public class DisableSuitAction extends TransAction{

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSuitManager itemSuitManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("bizCode");

        Long itemId = context.getRequest().getLong("itemId");

        Long sellerId = context.getRequest().getLong("sellerId");

        if(itemId==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"商品ID不能为空");
        }

        if(sellerId==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"店铺ID不能为空");
        }


        itemSuitManager.disableItemSuit(itemId,sellerId,bizCode);


        //将套装商品置为失效状态，不可被上下架触发
        Integer result = itemManager.disableItem(itemId, sellerId, bizCode);

        return ResponseUtil.getSuccessResponse(result.longValue());
    }

    @Override
    public String getName() {
        return ActionEnum.DISABLE_ITEM_SUIT.getActionName();
    }
}
