package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/21/16.
 */
public class HandleGiftItemStep extends TradeBaseStep {
    @Override
    public StepName getName() {
        return StepName.HANDLE_GIFT_ITEM_STEP;
    }

    @Override
    public TradeResponse execute() {
        SettlementInfo settlement = (SettlementInfo) this.getAttr("settlement");
        String appKey = (String) this.getAttr("appKey");
        ItemManager itemManager = (ItemManager) this.getBean("itemManager");
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
        

        //如果结算信息为空，则直接返回错误信息，让错误直接暴露，方便排查
        if(null == settlement){
            logger.error("settlement is null");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "settlement is null");
        }

        //如果本次结算没有任何赠品需要处理，则直接返回成功
        if(null==settlement.getGiftList() || settlement.getGiftList().isEmpty()) {
            return ResponseUtils.getSuccessResponse();
        }

        logger.info(" 【settlement.getGiftList】 "+JSONObject.toJSONString(settlement.getGiftList()));

        //基于营销结算信息中的赠品列表，构建订单赠品列表
        List<OrderItemDTO> giftList = new ArrayList<OrderItemDTO>();
        try {
        	
        	List<DiscountInfo> discountInfoList = settlement.getDirectDiscountList();
        	
        	// 订单总金额 TODO 税费暂时为0
            Long orderAmount = orderDTO.getTotalPrice()+settlement.getDeliveryFee()+0;            
            
            if (discountInfoList.isEmpty() == false) {
                for (DiscountInfo discountInfo : discountInfoList) {
                    MarketActivityDTO marketActivityDTO = discountInfo.getActivity();

                    if (marketActivityDTO.getToolCode().equals("ReachMultipleReduceTool")) { //满减送才有赠品
                    	// 满减送金额是否达标判断 TODO
                    	if(orderAmount>=discountInfo.getConsume() ){
                    		
                    		for (MarketItemDTO marketitemDTO : settlement.getGiftList()) {
                                List<Long> skuIds = new ArrayList<Long>();
                                skuIds.add(marketitemDTO.getItemSkuId());

                                List<ItemSkuDTO> itemSkuList = itemManager.queryItemSku(skuIds, marketitemDTO.getSellerId(), appKey);
                                if (itemSkuList == null | itemSkuList.size() == 0) {
                                    logger.error("itemSkuList is null : " + skuIds + "," + marketitemDTO.getSellerId());
                                    return  ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "itemSku doesn't exist");
                                }

                                ItemSkuDTO sku = itemSkuList.get(0);
                                List<Long> itemIdList = new ArrayList<Long>();
                                itemIdList.add(sku.getItemId());
                                List<ItemDTO> items = itemManager.queryItem(itemIdList, marketitemDTO.getSellerId(), appKey);
                                if (items == null | items.size() == 0) {
                                    logger.error("Item is null" + itemIdList + " " + marketitemDTO.getSellerId());
                                    return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "item doesn't exist");
                                }
                                ItemDTO itemDTO = items.get(0);

                                OrderItemDTO orderItemDTO = new OrderItemDTO();
                                orderItemDTO.setItemId(marketitemDTO.getItemId());
                                orderItemDTO.setSellerId(marketitemDTO.getSellerId());
                                orderItemDTO.setItemSkuId(marketitemDTO.getItemSkuId());
                                orderItemDTO.setItemName(marketitemDTO.getItemName());
                                orderItemDTO.setItemImageUrl(marketitemDTO.getIconUrl());
                                orderItemDTO.setItemSkuDesc(sku.getSkuCode());
                                orderItemDTO.setUnitPrice(0L);
                                orderItemDTO.setCategoryId(itemDTO.getCategoryId());
                                orderItemDTO.setItemBrandId(itemDTO.getItemBrandId());

                                if (null != marketitemDTO.getNumber()) {
                                    orderItemDTO.setNumber(marketitemDTO.getNumber());
                                } else {
                                    orderItemDTO.setNumber(1);
                                }

                                orderItemDTO.setItemType(1);
                                giftList.add(orderItemDTO);
                            }
                    		
                    	}
                    }
                }
            }   		
            
            logger.info(" 【giftList】 "+JSONObject.toJSONString(giftList));
                
        } catch (TradeException e) {
            logger.error("error to generate orderItem list", e);
            return ResponseUtils.getFailResponse(e.getResponseCode(), e.getMessage());
        }

        //将赠品列表放入管道上下文中，方便后面的节点处理
        this.setAttr("giftList", giftList);

        return ResponseUtils.getSuccessResponse();

    }
}
