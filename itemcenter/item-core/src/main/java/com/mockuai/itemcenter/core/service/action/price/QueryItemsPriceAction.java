package com.mockuai.itemcenter.core.service.action.price;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.*;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/12/4.
 */
@Service
public class QueryItemsPriceAction extends TransAction {

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private ValueAddedServiceManager valueAddedServiceManager;

    @Resource
    private ItemImageManager itemImageManager;

    @Resource
    private SkuPropertyManager skuPropertyManager;

    private Logger logger = LoggerFactory.getLogger(QueryItemsPriceAction.class);

    @Override
    protected ItemResponse doTransaction(RequestContext context)  {

        String bizCode = (String) context.get("bizCode");
        ItemRequest request = context.getRequest();
        Long userId = request.getLong("userId");
        try {
            List<ItemPriceQTO> itemPriceQTOList = request.getObject("itemPriceQTOList", List.class);
            List<ItemPriceDTO> itemPriceDTOList = Lists.newArrayList();

            if (itemPriceQTOList != null && itemPriceQTOList.size() > 0) {

                int size = itemPriceQTOList.size();

                List<Long> itemIdList = Lists.newArrayListWithExpectedSize(size);
                List<Long> skuIdList = Lists.newArrayListWithExpectedSize(size);
                List<Long> serviceIdList = Lists.newArrayListWithExpectedSize(size);

                Map<Long, ItemSkuDTO> skuDTOMap = Maps.newHashMapWithExpectedSize(size);
                Map<Long,ItemDTO> itemDTOMap = Maps.newHashMapWithExpectedSize(size);
                Map<Long, ValueAddedServiceDTO> serviceDTOMap = Maps.newHashMap();

                for (ItemPriceQTO itemPriceQTO : itemPriceQTOList) {
                    skuIdList.add(itemPriceQTO.getItemSkuId());

                    if(itemPriceQTO.getServiceIdList()!=null) {
                        serviceIdList.addAll(itemPriceQTO.getServiceIdList());
                    }
                }

                ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
                itemSkuQTO.setIdList(skuIdList);

                List<ItemSkuDTO> skuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);

                for (ItemSkuDTO itemSkuDTO : skuDTOList) {
                    skuDTOMap.put(itemSkuDTO.getId(), itemSkuDTO);
                    itemIdList.add(itemSkuDTO.getItemId());
                }



                //查询所有商品
                ItemQTO itemQTO = new ItemQTO();
                itemQTO.setIdList(itemIdList);
                itemQTO.setSellerId(0L);
                List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);

                for(ItemDTO itemDTO : itemDTOList){
                    itemDTOMap.put(itemDTO.getId(),itemDTO);
                }

                //查询所有商品图片
                ItemImageQTO itemImageQTO = new ItemImageQTO();
                itemImageQTO.setItemIdList(itemIdList);
//                itemImageQTO.setSellerId(itemSkuQTO.getSellerId());
//                itemImageQTO.setBizCode(itemSkuQTO.getBizCode());

                List<ItemImageDTO> itemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);

                //匹配sku的一张
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

//                Multimap<Long, SkuPropertyDTO> propertyMap = LinkedHashMultimap.create();
                Map<Long, List<SkuPropertyDTO>> propertyMap = Maps.newHashMap();

//                for (SkuPropertyDTO skuPropertyDTO : skuPropertyDTOList) {
//                    propertyMap.put(skuPropertyDTO.getSkuId(), skuPropertyDTO);
//                }
                for (SkuPropertyDTO skuPropertyDTO : skuPropertyDTOList) {
                    Long skuId = skuPropertyDTO.getSkuId();

                    List<SkuPropertyDTO> propertyList = propertyMap.get(skuId);

                    if (propertyList == null) {
                        propertyMap.put(skuId, Lists.newArrayList(skuPropertyDTO));
                    } else {
                        propertyList.add(skuPropertyDTO);
                    }
                }

                for (ItemSkuDTO itemSkuDTO : skuDTOList) {

//                    itemSkuDTO.setSkuPropertyDTOList(Lists.newArrayList(propertyMap.get(itemSkuDTO.getId())));
                	itemSkuDTO.setSkuPropertyDTOList(propertyMap.get(itemSkuDTO.getId()));
                	
                    if (itemSkuDTO.getSkuPropertyDTOList() != null) {

                        for (SkuPropertyDTO skuPropertyDTO : itemSkuDTO.getSkuPropertyDTOList()) {

//                            if (skuPropertyDTO.getPropertyValueId() != null
//                                    && skuImageMap.containsKey(skuPropertyDTO.getPropertyValueId())) {
//                                ItemImageDTO itemImageDTO = skuImageMap.get(skuPropertyDTO.getPropertyValueId());
                                ItemImageDTO itemImageDTO =  getItemImage(itemImageDTOList,itemSkuDTO.getItemId(),skuPropertyDTO.getPropertyValueId());
                                if (itemImageDTO != null) {
                                    itemSkuDTO.setImageUrl(itemImageDTO.getImageUrl());
                                }
//                            }
                        }
                    }
                }

                ValueAddedServiceQTO valueAddedServiceQTO = new ValueAddedServiceQTO();
                valueAddedServiceQTO.setIdList(serviceIdList);
                List<ValueAddedServiceDTO> serviceDTOList = valueAddedServiceManager.queryValueAddedService(valueAddedServiceQTO);

                for (ValueAddedServiceDTO valueAddedServiceDTO : serviceDTOList) {
                    serviceDTOMap.put(valueAddedServiceDTO.getId(), valueAddedServiceDTO);
                }

                for (ItemPriceQTO itemPriceQTO : itemPriceQTOList) {

                    ItemPriceDTO itemPriceDTO = new ItemPriceDTO();

                    ItemSkuDTO itemSkuDTO = skuDTOMap.get(itemPriceQTO.getItemSkuId());

                    ItemDTO itemDTO = itemDTOMap.get(itemSkuDTO.getItemId());

                    itemPriceDTO.setItemSkuDTO(itemSkuDTO);
                    itemPriceDTO.setItemDTO(itemDTO);

                    List<Long> serviceDTOIdList = itemPriceQTO.getServiceIdList();

                    if (serviceIdList != null && serviceIdList.size() > 0) {  //如果有增值服务，需要查询增值服务

                        List<ValueAddedServiceDTO> valueAddedServiceDTOList = Lists.newArrayListWithCapacity(serviceIdList.size());

                        for (Long serviceId : serviceDTOIdList) {
                            valueAddedServiceDTOList.add(serviceDTOMap.get(serviceId));
                        }

                        //判断是否有同类型的增值服务
                        if (valueAddedServiceDTOList.size() > 1) {
                            for (int i = 0; i < valueAddedServiceDTOList.size() - 1; i++) {
                                for (int j = i + 1; j < valueAddedServiceDTOList.size(); j++) {

                                    if (valueAddedServiceDTOList.get(i).getTypeId().longValue()
                                            == valueAddedServiceDTOList.get(j).getTypeId().longValue()) {
                                        throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "不可以选择同一类型的增值服务");
                                    }
                                }
                            }
                        }

                        itemPriceDTO.setValueAddedServiceDTOList(valueAddedServiceDTOList);
                    }

                    itemPriceDTOList.add(itemPriceDTO);
                }

                return ResponseUtil.getSuccessResponse(itemPriceDTOList);
            }

        }catch ( Exception e){
            logger.error("商品价格查询异常",e);
            return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,e.getMessage());
        }

        return ResponseUtil.getSuccessResponse(Collections.EMPTY_LIST);
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
        return ActionEnum.QUERY_ITEMS_PRICE.getActionName();
    }
}
