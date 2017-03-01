package com.mockuai.itemcenter.core.service.action.item;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.constant.MessageTagEnum;
import com.mockuai.itemcenter.common.constant.MessageTopicEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.constant.ItemStatus;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.message.producer.Producer;
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

import java.util.Date;

/**
 * 下架商品Action
 * 
 * @author chen.huang
 *
 */
@Service
public class WithdrawItemAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemAction.class);
	@Resource
	private ItemManager itemManager;

	@Resource
	TransactionTemplate transactionTemplate;

	@Resource
	private ItemSearchManager itemSearchManager;

	@Resource
	private Producer producer;

	@SuppressWarnings("unchecked")
	@Override
	public ItemResponse execute(final RequestContext context) throws ItemException {
		ItemResponse response = (ItemResponse)transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					ItemResponse response = null;
					ItemRequest request = context.getRequest();
                    String bizCode = (String) context.get("bizCode");


                    // 验证ID
					if (request.getLong("itemId") == null) {
						return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemId is missing");
					}
					if (request.getLong("supplierId") == null) {
						return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "supplierId is missing");
					}
					Long itemId = request.getLong("itemId");// 商品品牌ID
					Long sellerId = request.getLong("supplierId");// 供应商ID

                    ItemDTO itemDTO = itemManager.getItem(itemId, sellerId, bizCode);
                    Date saleBegin = itemDTO.getSaleBegin();
					if(saleBegin != null) {
						long now = System.currentTimeMillis();
						if(now >= saleBegin.getTime()) {
							// 去掉上架时间,否则自动下架程序会自动上架;(FIXME 当上架时间和下架时间缺一个时，定时上下架程序失效)
                            itemManager.removeItemSaleBegin(itemId, sellerId, bizCode);
                        }
					}

                    int result = itemManager.withdrawItem(itemId, sellerId, bizCode);
                    if(result > 0){
						response = ResponseUtil.getSuccessResponse(true);
					}else{
						response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
					}

					//TODO 删除商品索引跟下架商品逻辑的事务性保证；删除商品索引异步化
					//删除商品在搜索引擎中的索引
					itemSearchManager.deleteItemIndex(itemId, sellerId);

					itemDTO.setItemStatus(ItemStatus.WITHDRAW.getStatus());
					producer.send(
							MessageTopicEnum.ITEM_STATUS_CHANGE.getTopic(),
							MessageTagEnum.SINGLE.getTag(),
							itemDTO);

					return response;
					
				} catch (ItemException e) {
					status.setRollbackOnly();
					log.error(e.toString());
					return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
				}
			}
		});
		return response;

	}

	@Override
	public String getName() {
		return ActionEnum.WITHDRAW_ITEM.getActionName();
	}
}
