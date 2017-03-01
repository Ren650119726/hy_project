package com.mockuai.itemcenter.core.service.action.item;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.constant.MessageTagEnum;
import com.mockuai.itemcenter.common.constant.MessageTopicEnum;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.message.producer.Producer;
import com.mockuai.itemcenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除商品Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteItemAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemAction.class);
	@Resource
	private ItemManager itemManager;

	@Resource
	TransactionTemplate transactionTemplate;

	@Resource
	private ItemSkuManager itemSkuManager;
	
	@Resource
	private ItemImageManager itemImageManager;
	
	@Resource
	private ItemPropertyManager itemPropertyManager;
	
	@Resource
	private SkuPropertyManager skuPropertyManager;

	@Resource
	private ItemSearchManager itemSearchManager;

	@Resource
	private ItemBuyLimitManager itemBuyLimitManager;

	@Resource
	private Producer producer;


	public ItemResponse doTransaction(final RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String appKey  = (String) request.getParam("appKey");
		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemID is missing");
		}
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		Long itemId = request.getLong("ID");// 商品品牌ID
		Long sellerId = request.getLong("sellerId");// 供应商ID

        String bizCode = (String) context.get("bizCode");
        // 获取ItemSku列表
					/*
					ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
					itemSkuQTO.setItemId(itemId);
					itemSkuQTO.setSellerId(sellerId);
					List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);
					for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
						Long skuId = itemSkuDTO.getId();
						// 删除itemSku ,在manager层中先删除skuproperty列表
						itemSkuManager.deleteItemSku(skuId, sellerId);
					}
					*/
		// updated by cwr
        int result = itemSkuManager.deleteByItemId(itemId, sellerId, bizCode, appKey);//删除对应的item_sku
        int result2 = itemPropertyManager.deleteByItemId(itemId, sellerId);//删除对应的基本属性
		int result3 =  skuPropertyManager.deleteByItemId(itemId, sellerId);//删除对应的销售属性
		// 删除限购;
		itemBuyLimitManager.deleteItemBuyLimit(sellerId, itemId);
		// 删除副图列表
		itemImageManager.deleteItemImageListByItemId(itemId, sellerId);
        itemManager.deleteItem(itemId, sellerId, bizCode, appKey);

		//TODO 删除商品和删除索引事务性保证逻辑;删除索引异步化
		//删除商品在搜索引擎中的索引
		itemSearchManager.deleteItemIndex(itemId, sellerId);
		response = ResponseUtil.getSuccessResponse(true);

		//发送商品删除的消息
		producer.send(
				MessageTopicEnum.ITEM_DELETE.getTopic(),
				MessageTagEnum.SINGLE.getTag(),
				itemId);



		return response;
//		ItemResponse response = (ItemResponse)transactionTemplate.execute(new TransactionCallback() {
//			@Override
//			public Object doInTransaction(TransactionStatus status) {
//				try {
//
//				} catch (ItemException e) {
//					status.setRollbackOnly();
//					log.error(e.toString());
//					return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
//				}
//			}
//		});
//		return response;

	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_ITEM.getActionName();
	}
}
