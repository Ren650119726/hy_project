package com.mockuai.itemcenter.core.service.action.itemsku;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemImageQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询增加商品销售属性(ItemSku)列表 Action
 *
 * @author chen.huang
 */
@Service
public class QueryItemSkuAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryItemSkuAction.class);

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private ItemImageManager itemImageManager;

    @Resource
    private SkuPropertyManager skuPropertyManager;
    @Resource
    private StoreStockManager storeStockManager;
    @Resource
    private CompositeItemManager compositeItemManager;


    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String appKey =request.getString("appKey");
        if (request.getParam("itemSkuQTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemSkuQTO is null");
        }

        String bizCode = (String) context.get("bizCode");
        ItemSkuQTO itemSkuQTO = (ItemSkuQTO) request.getParam("itemSkuQTO");
        itemSkuQTO.setBizCode(bizCode);

        try {
            List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);
            List<Long> itemIdList = Lists.newArrayListWithExpectedSize(itemSkuDTOList.size());
            List<Long> skuIdList = Lists.newArrayListWithExpectedSize(itemSkuDTOList.size());

            for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
                itemIdList.add(itemSkuDTO.getItemId());
                skuIdList.add(itemSkuDTO.getId());
            }

            if (itemSkuDTOList.size() > 0 && itemSkuQTO.getNeedImage() != null && itemSkuQTO.getNeedImage() == 1) {



                ItemImageQTO itemImageQTO = new ItemImageQTO();

                itemImageQTO.setItemIdList(itemIdList);

                //读取商品sku库存信息
                StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
                storeItemSkuQTO.setItemSkuIdList(skuIdList);
                Map<Long ,StoreItemSkuDTO> storeSkuDTOMap = new HashMap<Long, StoreItemSkuDTO>();
                //是否需要查询库存

                if(itemSkuQTO.getNeedQueryStock() == null ||   itemSkuQTO.getNeedQueryStock()){
                    List<StoreItemSkuDTO> storeItemSkuList =  storeStockManager.queryItemStock(storeItemSkuQTO,appKey);

                    for(StoreItemSkuDTO storeItemSkuDTO : storeItemSkuList){
                        StoreItemSkuDTO item  =   storeSkuDTOMap.get(  storeItemSkuDTO.getItemSkuId());
                        if(item == null){
                            storeSkuDTOMap.put(storeItemSkuDTO.getItemSkuId(),storeItemSkuDTO);
                        }else{
                            item.setStockNum(item.getSalesNum().longValue() + storeItemSkuDTO.getSalesNum().longValue());
                            item.setFrozenStockNum(item.getFrozenStockNum().longValue()+storeItemSkuDTO.getFrozenStockNum().longValue());
                            storeSkuDTOMap.put(storeItemSkuDTO.getItemSkuId(),item);
                        }
                    }
                }

                List<ItemImageDTO> itemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);

                Map<Long, ItemImageDTO> skuImageMap = new HashMap<Long, ItemImageDTO>();
                for (ItemImageDTO itemImageDTO : itemImageDTOList) {
                    if (itemImageDTO.getPropertyValueId() != null && itemImageDTO.getPropertyValueId().longValue() > 0) {
                        //商品SKU图片
                        skuImageMap.put(itemImageDTO.getPropertyValueId(), itemImageDTO);
                    }
                }

                //添加sku属性列表
                SkuPropertyQTO skuPropertyQTO = new SkuPropertyQTO();
                skuPropertyQTO.setSkuIdList(skuIdList);
                skuPropertyQTO.setSellerId(itemSkuQTO.getSellerId());

                List<SkuPropertyDTO> skuPropertyDTOList = skuPropertyManager.querySkuProperty(skuPropertyQTO);

                Map<Long, List<SkuPropertyDTO>> propertyMap = Maps.newHashMap();

                for (SkuPropertyDTO skuPropertyDTO : skuPropertyDTOList) {
                    Long skuId = skuPropertyDTO.getSkuId();

                    List<SkuPropertyDTO> propertyList = propertyMap.get(skuId);

                    if (propertyList == null) {
                        propertyMap.put(skuId, Lists.newArrayList(skuPropertyDTO));
                    } else {
                        propertyList.add(skuPropertyDTO);
                    }
                }



                for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
                    StoreItemSkuDTO storeItemSkuDTO =   storeSkuDTOMap.get(itemSkuDTO.getId());
                    //设置库存和冻结数量
                    if(storeItemSkuDTO != null){

                        itemSkuDTO.setStockNum(storeItemSkuDTO.getSalesNum());
                        itemSkuDTO.setFrozenStockNum(storeItemSkuDTO.getFrozenStockNum());
                    }

                    itemSkuDTO.setSkuPropertyDTOList(propertyMap.get(itemSkuDTO.getId()));
                      if(itemSkuDTO.getSkuPropertyDTOList() == null)
                          continue;
                      for(SkuPropertyDTO skuProperty : itemSkuDTO.getSkuPropertyDTOList()){
                          ItemImageDTO itemImageDTO =  getItemImage(itemImageDTOList,itemSkuDTO.getItemId(),skuProperty.getPropertyValueId());
                          if(itemImageDTO == null)
                              continue;
                          itemSkuDTO.setImageUrl(itemImageDTO.getImageUrl());
                          break;
                      }

                }
            }
            response = ResponseUtil.getSuccessResponse(itemSkuDTOList, itemSkuQTO.getTotalCount());
            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }

    }


    private ItemImageDTO getItemImage(List<ItemImageDTO> itemImageList ,Long itemId,Long propertyValueId ){
           for(ItemImageDTO itemImage : itemImageList){
               if(itemImage.getItemId().longValue() == itemId.longValue()
                   && itemImage.getPropertyValueId().longValue() == propertyValueId.longValue()
                       )
               return itemImage;
           }

             return null;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_SKU.getActionName();
    }
}
