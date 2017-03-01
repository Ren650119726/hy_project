package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 下架商品Action
 * 
 * @author chen.huang
 *
 */
@Service
public class BatchWithdrawItemAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemAction.class);
	@Resource
	private ItemManager itemManager;

	@Resource
	TransactionTemplate transactionTemplate;

	@Resource
	private ItemSearchManager itemSearchManager;

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
					if (request.getObject("itemIds", List.class) == null) {
						return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemIds is missing");
					}
					if (request.getLong("supplierId") == null) {
						return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "supplierId is missing");
					}

					List<Long> itemIds = request.getObject("itemIds",List.class);// 商品品牌ID
					Long sellerId = request.getLong("supplierId");// 供应商ID

                    ItemQTO itemQTO = new ItemQTO();
                    itemQTO.setIdList(itemIds);
                    itemQTO.setSellerId(sellerId);
					itemQTO.setBizCode(bizCode);

					List<ItemDTO> itemDTOs = itemManager.queryItem(itemQTO);

                    for(ItemDTO itemDTO : itemDTOs) {

                        Long itemId = itemDTO.getId();

                        Date saleBegin = itemDTO.getSaleBegin();
                        if (saleBegin != null) {
                            long now = System.currentTimeMillis();
                            if (now >= saleBegin.getTime()) {
                                // 去掉上架时间,否则自动下架程序会自动上架;(FIXME 当上架时间和下架时间缺一个时，定时上下架程序失效)
								//itemManager.removeItemSaleEnd(itemId, sellerId, bizCode);
                                itemManager.removeItemSaleBegin(itemId, sellerId, bizCode);
							}
                        }

						int result = itemManager.withdrawItem(itemId, sellerId, bizCode);
						if (result > 0) {
                            response = ResponseUtil.getSuccessResponse(true);
                        } else {
                            response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
                        }

                        //TODO 删除商品索引跟下架商品逻辑的事务性保证；删除商品索引异步化
                        //删除商品在搜索引擎中的索引
                        itemSearchManager.deleteItemIndex(itemId, sellerId);
                    }
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
		return ActionEnum.BATCH_WITHDRAW_ITEM.getActionName();
	}
}
