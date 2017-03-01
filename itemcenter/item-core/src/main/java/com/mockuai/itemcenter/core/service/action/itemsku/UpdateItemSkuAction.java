package com.mockuai.itemcenter.core.service.action.itemsku;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.core.message.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 更新商品销售属性(ItemSku) Action
 * 
 * @author chen.huang
 *
 */

@Service
public class UpdateItemSkuAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateItemSkuAction.class);
	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	private Producer producer;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("itemSkuDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemSkuDTO is null");
		}
        String bizCode = (String) context.get("bizCode");
        ItemSkuDTO itemSkuDTO = (ItemSkuDTO) request.getParam("itemSkuDTO");
        itemSkuDTO.setBizCode(bizCode);
        try {
			Boolean isSuccessfullyUpdated = itemSkuManager.updateItemSku(itemSkuDTO);
			response = ResponseUtil.getSuccessResponse(isSuccessfullyUpdated);


			context.put(HookEnum.STOCK_CHANGE_HOOK.getHookName(), "");
			context.put("skuId", itemSkuDTO.getId());
			context.put("sellerId", itemSkuDTO.getSellerId());

			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.UPDATE_ITEM_SKU.getActionName();
	}
}
