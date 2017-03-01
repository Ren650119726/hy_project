package com.mockuai.itemcenter.core.service.action.item;

import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CompositeItemManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.StoreStockManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lizg on 2016/11/1.
 * 获取商品库存
 */

@Service
public class GetItemStockAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetItemStockAction.class);

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private StoreStockManager storeStockManager;
    @Resource
    private CompositeItemManager compositeItemManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {

        ItemDTO itemDTO = null;
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String appKey = (String) context.get("appKey");
        String bizCode = (String) context.get("bizCode");
        // 验证ID
        if (request.getLong("id") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemID is missing");
        }
        if (request.getLong("supplierId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
        }
        Long itemId = request.getLong("id");// 商品ID
        Long sellerId = request.getLong("supplierId");// 供应商ID


        try {
            try{
                itemDTO = itemManager.getItem(itemId, sellerId, bizCode);
            }catch (ItemException e){
                //记录不存在
                if(e.getResponseCode() == ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST){
                    itemDTO = new ItemDTO();
                    itemDTO.setItemSkuDTOList(new ArrayList<ItemSkuDTO>());
                    return ResponseUtil.getSuccessResponse(itemDTO);
                }else{
                    throw  e;
                }

            }


            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
            itemSkuQTO.setItemId(itemId);
            itemSkuQTO.setSellerId(sellerId);
            itemSkuQTO.setBizCode(bizCode);
            // 获取ItemSku列表
            List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);
            log.info("[{}] itemSkuDTOList size:{}",itemSkuDTOList.size());

            Long totalSalesNum = 0L;
            Long totalFrozenStockNum = 0L;
            /**
             * 按组合商品查询算出
             */

            Map<Long, StoreItemSkuDTO> storeSkuMap = Maps.newHashMap();
            Integer compositeSaleNum = 0;
            if (itemDTO.getItemType() == ItemType.COMPOSITE_ITEM.getType()) {
                StoreItemSkuDTO storeItemSkuDTO = compositeItemManager.getCompositeStore(itemId, appKey);
                compositeSaleNum = storeItemSkuDTO.getSalesNum().intValue();
            } else {
                StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
                storeItemSkuQTO.setItemId(itemId);
                /**
                 * 读取sku库存
                 */
                List<StoreItemSkuDTO> storeItemSkuList = storeStockManager.queryItemStock(storeItemSkuQTO, appKey);

                //相同的sku 不同的store 有不同的数据
                for (StoreItemSkuDTO storeItemSku : storeItemSkuList) {
                    Long skuId = storeItemSku.getItemSkuId();
                    StoreItemSkuDTO item = storeSkuMap.get(skuId);
                    if (item == null) {
                        storeSkuMap.put(skuId, storeItemSku);
                        totalSalesNum +=storeItemSku.getSalesNum();
                        totalFrozenStockNum +=storeItemSku.getFrozenStockNum();
                    } else {
                        item.setStockNum(storeItemSku.getSalesNum() + item.getSalesNum());
                        item.setFrozenStockNum(storeItemSku.getFrozenStockNum() + item.getFrozenStockNum());
                        storeSkuMap.put(skuId, item);
                    }

                }
                log.info("[{}] totalSalesNum:{}",totalSalesNum);
                itemDTO.setStockNum(totalSalesNum);
                itemDTO.setFrozenStockNum(totalFrozenStockNum);
            }

            for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {

                //组合商品设置他的库存
                if (itemDTO.getItemType().intValue() == ItemType.COMPOSITE_ITEM.getType()) {
                    itemSkuDTO.setStockNum(compositeSaleNum.longValue());
                    itemSkuDTO.setFrozenStockNum(0L);
                } else {
                    //如果包含这个sku 则设置对应的库存
                    if (storeSkuMap.containsKey(itemSkuDTO.getId())) {
                        itemSkuDTO.setStockNum(storeSkuMap.get(itemSkuDTO.getId()).getSalesNum());
                        itemSkuDTO.setFrozenStockNum(storeSkuMap.get(itemSkuDTO.getId()).getFrozenStockNum());
                    }
                }

            }

            itemDTO.setItemSkuDTOList(itemSkuDTOList);

        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }


        response = ResponseUtil.getSuccessResponse(itemDTO);


        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ITEM_STOCK.getActionName();
    }
}
