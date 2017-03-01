package com.mockuai.tradecenter.core.service.action.order.add.step;

import java.util.Map;

import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;

/**
 * 竞拍和秒杀商品需要有存在预单，才能完成下单操作
 * Created by zengzhangqiang on 5/20/16.
 */
public class CheckPreOrderStep extends TradeBaseStep {
    @Override
    public StepName getName() {
        return StepName.CHECK_PRE_ORDER_STEP;
    }

    @Override
    public TradeResponse execute() {
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
//        ItemManager itemManager = (ItemManager) this.getBean("itemManager");
        OrderManager orderManager = (OrderManager) this.getBean("orderManager");
        Map<Long, ItemDTO> itemMap = (Map<Long, ItemDTO>) this.getAttr("itemMap");

        try {
        	if(itemMap!= null && itemMap.size() >0){
        		for (Map.Entry<Long, ItemDTO> entry : itemMap.entrySet()) {
                    ItemDTO itemDTO = entry.getValue();
                    int itemType = itemDTO.getItemType();
                    if (itemType == ItemType.SECKILL.getType() || itemType == ItemType.AUCTION.getType()) {
//                        OrderDO preOrderDO = orderManager.getPreOrder(orderDTO.getUserId(), entry.getKey());
                    	OrderItemQTO query = new OrderItemQTO();
                		query.setUserId(orderDTO.getUserId());
                		query.setItemId(entry.getKey());    
                		query.setDeleteMark(0);                	
                    	
                    	OrderDO preOrderDO = orderManager.getPreOrderByItemQTO(query);
                        if (null == preOrderDO) {
                            return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_ITEM_SKU_INVALID,
                                    "机会已被抢走");
                        }
                    }
                }
        	}
            
        } catch (TradeException e) {
            logger.error("error to check preOrder", e);
            return ResponseUtils.getFailResponse(e.getResponseCode(), e.getMessage());
        }

        return ResponseUtils.getSuccessResponse();
    }
}
