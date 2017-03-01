package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.MessageTagEnum;
import com.mockuai.itemcenter.common.constant.MessageTopicEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.constant.ItemStatus;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.message.producer.Producer;
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

/**
 * 商品预售Action
 * 
 * @author ziqi
 *
 */
@Service
public class PreSaleItemAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(PreSaleItemAction.class);
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

					//判断商品上下架时间是否设置,是否在当前时间之后，否则无法预售
					 if((itemDTO.getSaleBegin() != null)&&itemDTO.getSaleBegin().before(new Date())){
						 return ResponseUtil.getErrorResponse(ResponseCode.BASE_STATE_E_SALE_BEGIN_TIME_IS_INVALID);
					 }

					int result = itemManager.preSaleItem(itemId, sellerId, bizCode);
					if(result > 0){
						response = ResponseUtil.getSuccessResponse(true);
					}else{
						response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
					}

					//TODO 商品索引更新逻辑异步化
					//更新商品索引
					itemSearchManager.setItemIndex(itemDTO);

					itemDTO.setItemStatus(ItemStatus.PRE_SALE.getStatus());

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
		return ActionEnum.PRE_SALE_ITEM.getActionName();
	}
}
