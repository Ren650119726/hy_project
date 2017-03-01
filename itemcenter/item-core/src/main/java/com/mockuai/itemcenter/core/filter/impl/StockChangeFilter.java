package com.mockuai.itemcenter.core.filter.impl;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.common.util.StockStatusUtil;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.filter.Filter;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.message.producer.Producer;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class StockChangeFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(StockChangeFilter.class);
    @Override
    public boolean isAccept(RequestContext ctx) {
        return true;
    }

    @Override
    public ItemResponse before(RequestContext ctx) throws ItemException {
        return new ItemResponse(ResponseCode.SUCCESS);
    }

    @Override
    public ItemResponse after(RequestContext ctx) throws ItemException {

        String bizCode = (String) ctx.get("bizCode");

        if (ctx.get(HookEnum.STOCK_CHANGE_HOOK.getHookName()) != null) {

            Long sellerId = (Long) ctx.get("sellerId");
            Long skuId = (Long) ctx.get("skuId");

            try {


                ItemSkuManager itemSkuManager = (ItemSkuManager) ctx.getAppContext().getBean("itemSkuManagerImpl");


                ItemSkuDTO itemSkuDTO = itemSkuManager.getItemSku(skuId, sellerId, bizCode);


                Producer producer = (Producer) ctx.getAppContext().getBean("transmitMsgProducer");

                producer.send(
                        MessageTopicEnum.ITEM_SKU_UPDATE.getTopic(),
                        MessageTagEnum.STOCK.getTag(),
                        itemSkuDTO);

                ItemManager itemManager = (ItemManager) ctx.getAppContext().getBean("itemManagerImpl");

                ItemDTO itemDTO = itemManager.getItem(itemSkuDTO.getItemId(), sellerId, bizCode);

                ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
                itemSkuQTO.setItemId(itemDTO.getId());

                List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);

                long stockNum = 0;
                long frozenStockNum = 0;
                if(itemSkuDTOList.size()>0){

                    StockStatus stockStatus = StockStatusUtil.genStockStatus(itemSkuDTOList);

                    for(ItemSkuDTO skuDTO : itemSkuDTOList){
                        stockNum += skuDTO.getStockNum();
                        frozenStockNum += skuDTO.getFrozenStockNum();
                    }

                    itemDTO.setStockStatus(stockStatus.getStatus());
                    itemDTO.setStockNum(stockNum);
                    itemDTO.setFrozenStockNum(frozenStockNum);
                    itemManager.updateItemStockNum(itemDTO);
                }



            } catch (ItemException e) {
                log.error(e.getMessage(), e);
                return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
            }

        }else if (ctx.get(HookEnum.BATCH_STOCK_CHANGE_HOOK.getHookName()) != null) {

            Long sellerId = (Long) ctx.get("sellerId");
            List<Long> skuIdList = (List<Long>) ctx.get("skuIdList");

            try {

                for(Long skuId : skuIdList) {


                    ItemSkuManager itemSkuManager = (ItemSkuManager) ctx.getAppContext().getBean("itemSkuManagerImpl");


                    ItemSkuDTO itemSkuDTO = itemSkuManager.getItemSku(skuId, sellerId, bizCode);


                    Producer producer = (Producer) ctx.getAppContext().getBean("transmitMsgProducer");

                    producer.send(
                            MessageTopicEnum.ITEM_SKU_UPDATE.getTopic(),
                            MessageTagEnum.STOCK.getTag(),
                            itemSkuDTO);

                    ItemManager itemManager = (ItemManager) ctx.getAppContext().getBean("itemManagerImpl");

                    ItemDTO itemDTO = itemManager.getItem(itemSkuDTO.getItemId(), sellerId, bizCode);

                    ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
                    itemSkuQTO.setItemId(itemDTO.getId());
                    itemSkuQTO.setSellerId(itemDTO.getSellerId());
                    itemSkuQTO.setBizCode(bizCode);

                    List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);

                    long stockNum = 0;
                    long frozenStockNum = 0;
                    if(itemSkuDTOList.size()>0){

                        StockStatus stockStatus = StockStatusUtil.genStockStatus(itemSkuDTOList);

                        for(ItemSkuDTO skuDTO : itemSkuDTOList){
                            stockNum += skuDTO.getStockNum();
                            frozenStockNum += skuDTO.getFrozenStockNum();
                        }

                        itemDTO.setStockStatus(stockStatus.getStatus());
                        itemDTO.setStockNum(stockNum);
                        itemDTO.setFrozenStockNum(frozenStockNum);
                        itemManager.updateItemStockNum(itemDTO);
                    }
                }



            } catch (ItemException e) {
                log.error(e.getMessage(), e);
                return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
            }

        }

        return new ItemResponse(ResponseCode.SUCCESS);

    }
}
