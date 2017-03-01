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
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 下架商品Action
 * 
 * @author chen.huang
 *
 */
@Service
public class BatchDeleteItemAction implements Action {
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
                    String appKey = request.getString("appKey");

                    ItemQTO itemQTO = new ItemQTO();
                    itemQTO.setIdList(itemIds);
                    itemQTO.setSellerId(sellerId);
					List<ItemDTO> itemDTOs = itemManager.queryItem(itemQTO);


                    if (CollectionUtils.isEmpty(itemDTOs)) {

                        ItemQTO itemQTO2 = new ItemQTO();
                        itemQTO2.setIdList(itemIds);
                        itemQTO2.setSellerId(sellerId);
                        itemQTO2.setDeleteMark(2);
                        itemDTOs = itemManager.queryItem(itemQTO2);
                    }


                    for(ItemDTO itemDTO : itemDTOs) {

                        Long itemId = itemDTO.getId();


                        boolean result = itemManager.deleteItem(itemId, sellerId, itemDTO.getBizCode(), appKey);

                        //TODO 删除商品索引跟下架商品逻辑的事务性保证；删除商品索引异步化
                        //删除商品在搜索引擎中的索引
                        itemSearchManager.deleteItemIndex(itemId, sellerId);
                    }

                    response = ResponseUtil.getSuccessResponse(true);

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
        return ActionEnum.BATCH_DELETE_ITEM.getActionName();
    }
}
