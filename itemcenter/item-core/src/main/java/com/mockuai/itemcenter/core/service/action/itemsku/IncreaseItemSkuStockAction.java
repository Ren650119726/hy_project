package com.mockuai.itemcenter.core.service.action.itemsku;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.StoreStockManager;
import com.mockuai.itemcenter.core.message.producer.Producer;
import com.mockuai.itemcenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 更新商品销售属性(ItemSku) Action
 *
 * @author chen.huang
 */

@Service
public class IncreaseItemSkuStockAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(IncreaseItemSkuStockAction.class);
    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private StoreStockManager storeStockManager;

    @Resource
    private Producer producer;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        if (request.getLong("skuId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemSkuID is missing");
        }
        if (request.getLong("storeId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "storeId is missing");
        }
        if (request.getLong("sellerId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
        }
        if (request.getInteger("number") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "increasedNumber is missing");
        }
        long skuId = request.getLong("skuId");
        long sellerId = request.getLong("sellerId");
        long storeId = request.getLong("storeId");
        int increasedNumber = request.getInteger("number");
        String bizCode = (String) context.get("bizCode");
        String appKey = request.getString("appKey");


        storeStockManager.reduceStoreNumAction(storeId,skuId,Long.valueOf(increasedNumber),appKey);

        itemSkuManager.increaseItemSkuStock(skuId, sellerId, increasedNumber, bizCode);
        response = ResponseUtil.getSuccessResponse(true);

        context.put(HookEnum.STOCK_CHANGE_HOOK.getHookName(), "");
        context.put("skuId", skuId);
        context.put("sellerId", sellerId);

        return response;

    }


    @Override
    public String getName() {
        return ActionEnum.INCREASE_ITEM_SKU_STOCK.getActionName();
    }
}
