package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemPropertyManager;
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
import java.util.List;

/**
 * 上架商品Action
 * 
 * @author chen.huang
 *
 */
@Service
public class UpItemAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpItemAction.class);
	@Resource
	private ItemManager itemManager;

	@Resource
	TransactionTemplate transactionTemplate;

	@Resource
	private ItemSearchManager itemSearchManager;

	@Resource
	private ItemPropertyManager itemPropertyManager;

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

					//TODO  调试完以后就该删除
                    //LoggerFactory.getLogger(getClass()).info("上架商品接口itemid={}",request.getParam("itemId"));
                    //LoggerFactory.getLogger(getClass()).info("上架商品接口sellerid={}",request.getParam("supplierId"));


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
                    Date endDate = itemDTO.getSaleEnd();
					if(endDate != null) {
						Long saleEnd = itemDTO.getSaleEnd().getTime();
						long now = System.currentTimeMillis();
						if(now >= saleEnd) {
							// 去掉下架时间,否则自动下架程序会自动下架;
                            itemManager.removeItemSaleEnd(itemId, sellerId, bizCode);
                        }
					}

                    int result = itemManager.upItem(itemId, sellerId, bizCode);
                    if(result > 0){
						response = ResponseUtil.getSuccessResponse(true);
					}else{
						response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
					}


					//获取商品属性信息
					// 根据itemId查找该商品下的所有的基本属性
					ItemPropertyQTO itemPropertyQTO = new ItemPropertyQTO();
					itemPropertyQTO.setItemId(itemId);
					itemPropertyQTO.setSellerId(sellerId);
					itemPropertyQTO.setNeedPaging(null); //不需要分页
					List<ItemPropertyDTO> itemPropertyList = itemPropertyManager.queryItemProperty(itemPropertyQTO);
					itemDTO.setItemPropertyList(itemPropertyList);
					//TODO 商品索引更新逻辑异步化
					//更新商品索引

					//手动上架时，索引中设置上架时间为当前时间
					itemDTO.setSaleBegin(new Date());
					itemSearchManager.setItemIndex(itemDTO);

					itemDTO.setItemStatus(ItemStatus.ON_SALE.getStatus());
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
		return ActionEnum.UP_ITEM.getActionName();
	}
}
