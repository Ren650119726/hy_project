package com.mockuai.itemcenter.core.web;


import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuPropertyRecommendationDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuRecommendationDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSkuPropertyRecommendationManager;
import com.mockuai.itemcenter.core.manager.ItemSkuRecommendationManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/9/25.
 */

@Controller
@RequestMapping("/hanshu/data_migrate")
public class DataMigationAction {

    @Resource
    private ItemSkuPropertyRecommendationManager itemSkuPropertyRecommendationManager;

    @Resource
    protected SkuPropertyManager skuPropertyManager;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private ItemManager itemManager;

    private static String appKey = "27c7bc87733c6d253458fa8908001eef";

    private static String bizCode = "hanshu";


    @RequestMapping(value = "/sku_property.do")
    public Response<String> queryItems(HttpServletRequest request, @RequestParam(required = false, value = "item_id_list") List<Long> itemIdList) {

        /**
         * 查询所有skuProperty
         */

        try {

            ItemQTO itemQTO = new ItemQTO();
            itemQTO.setIdList(itemIdList);
            //itemQTO.setIdList(Lists.newArrayList(2163L, 2215L));
            itemQTO.setSellerId(0L);

            List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);


            //以商品为单位修正

            for (ItemDTO itemDTO : itemDTOList) {

                SkuPropertyQTO skuPropertyQTO = new SkuPropertyQTO();
                skuPropertyQTO.setBizCode(bizCode);
                skuPropertyQTO.setItemId(itemDTO.getId());

                List<SkuPropertyDTO> skuPropertyDTOList = skuPropertyManager.querySkuProperty(skuPropertyQTO);

                Multimap<String, ItemSkuPropertyRecommendationDTO> valueMap = LinkedListMultimap.create();

                for (SkuPropertyDTO skuPropertyDTO : skuPropertyDTOList) {

                    Collection<ItemSkuPropertyRecommendationDTO> recommendations = valueMap.get(skuPropertyDTO.getValue());

                    //默认为新加的属性

                    boolean newFlag = true;
                    Long valueId = 0L;

                    if (!CollectionUtils.isEmpty(recommendations)) {
                        //比如有两个同名的黑属性  ,主色 黑  配色 黑,就要确认是不是同样是主色里的黑
                        for (ItemSkuPropertyRecommendationDTO itemSkuRecommendationDTO : recommendations) {
                            if (skuPropertyDTO.getSkuPropertyTmplId().longValue() == itemSkuRecommendationDTO.getParentId()) {
                                newFlag = false;
                                valueId = itemSkuRecommendationDTO.getId();
                                //break;
                            }
                        }
                    }

                    if (newFlag) {
                        final ItemSkuPropertyRecommendationDTO itemSkuPropertyRecommendationDTO = new ItemSkuPropertyRecommendationDTO();

                        itemSkuPropertyRecommendationDTO.setParentId(skuPropertyDTO.getSkuPropertyTmplId());
                        itemSkuPropertyRecommendationDTO.setPropertyName(skuPropertyDTO.getValue());

                        itemSkuPropertyRecommendationDTO.setBizCode(skuPropertyDTO.getBizCode());
                        itemSkuPropertyRecommendationDTO.setSellerId(skuPropertyDTO.getSellerId());

                        valueId = (Long)transactionTemplate.execute(new TransactionCallback() {

                            public Object doInTransaction(TransactionStatus status) {

                                return itemSkuPropertyRecommendationManager.addItemSkuPropertyRecommendation(itemSkuPropertyRecommendationDTO);
                            }

                        });



                            itemSkuPropertyRecommendationDTO.setId(valueId);

                            valueMap.put(skuPropertyDTO.getValue(), itemSkuPropertyRecommendationDTO);
                        }

                        skuPropertyDTO.setPropertyValueId(valueId);
                        skuPropertyManager.updateSkuProperty(skuPropertyDTO);
                    }

                    //System.out.println(JsonUtil.toJson(valueMap));
                }

                return ResponseUtil.getSuccessResponse("success", itemDTOList.size());
            }catch(ItemException e){

                e.printStackTrace();
                return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR, "error");
            }


        }


    }
