package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.message.producer.Producer;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 更新商品销售属性(ItemSku) Action
 * 
 * @author chen.huang
 *
 */

@Service
public class CrushItemSkuStockAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(CrushItemSkuStockAction.class);
	@Resource
	private ItemSkuManager itemSkuManager;

    @Resource
    private Producer producer;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getLong("skuId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemSkuID is missing");
		}
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		if (request.getInteger("number") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "crushNumber is missing");
		}
		long skuId = request.getLong("skuId");
		long sellerId = request.getLong("sellerId");
		int increasedNumber = request.getInteger("number");
        String bizCode = (String) context.get("bizCode");
        try {
            itemSkuManager.crushItemSkuStock(skuId, sellerId, increasedNumber, bizCode);
            response = ResponseUtil.getSuccessResponse(true);

            context.put(HookEnum.STOCK_CHANGE_HOOK.getHookName(), "");
            context.put("skuId", skuId);
            context.put("sellerId", sellerId);

			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.CRUSH_ITEM_SKU_STOCK.getActionName();
	}
}
