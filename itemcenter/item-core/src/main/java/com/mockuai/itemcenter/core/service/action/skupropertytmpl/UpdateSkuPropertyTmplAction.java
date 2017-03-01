package com.mockuai.itemcenter.core.service.action.skupropertytmpl;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.itemcenter.core.dao.SkuPropertyValueDAO;
import com.mockuai.itemcenter.core.domain.SkuPropertyDO;
import com.mockuai.itemcenter.core.domain.SkuPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyTmplManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyValueManager;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 更新商品属性模板Action
 *
 * @author chen.huang
 */

@Service
public class UpdateSkuPropertyTmplAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(UpdateSkuPropertyTmplAction.class);
    @Resource
    private SkuPropertyTmplManager skuPropertyTmplManager;

    @Resource
    private SkuPropertyValueManager skuPropertyValueManager;

    @Resource
    private SkuPropertyManager skuPropertyManager;

    @Resource
    private ItemManager itemManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();

        String bizCode = (String) context.get("bizCode");

        // 验证DTO是否为空
        if (request.getParam("skuPropertyTmplDTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyTmplDTO is null");
        }
        SkuPropertyTmplDTO skuPropertyTmplDTO = (SkuPropertyTmplDTO) request.getParam("skuPropertyTmplDTO");
        skuPropertyTmplDTO.setBizCode(bizCode);
        try {

            Boolean isSuccessfullyUpdated = skuPropertyTmplManager.updateSkuPropertyTmpl(skuPropertyTmplDTO);

            Long tmplId = skuPropertyTmplDTO.getId();

            SkuPropertyDO skuPropertyDO = new SkuPropertyDO();
            skuPropertyDO.setBizCode(bizCode);
            skuPropertyDO.setSkuPropertyTmplId(tmplId);

            skuPropertyDO.setName(skuPropertyTmplDTO.getName());
            skuPropertyDO.setCode(skuPropertyTmplDTO.getCode());

            //通过此模板生成的sku属性也要跟着修改
            skuPropertyManager.batchUpdateSkuProperty(skuPropertyDO);


            List<SkuPropertyValueDTO> oldValues = skuPropertyValueManager.querySkuPropertyValue(tmplId, bizCode);


            List<SkuPropertyValueDTO> newValues = skuPropertyTmplDTO.getPropertyValues();

            if (newValues == null) {
                newValues = Collections.EMPTY_LIST;
            }


            Set<Long> oldValueIds = Sets.newHashSet();

            for (SkuPropertyValueDTO skuPropertyValueDTO : oldValues) {
                oldValueIds.add(skuPropertyValueDTO.getId());
            }

            Set<Long> newValueIds = Sets.newHashSet();

            //新添加的propertyValue
            List<SkuPropertyValueDTO> addedValues = Lists.newArrayList();


            for (SkuPropertyValueDTO skuPropertyValueDTO : newValues) {

                if (skuPropertyValueDTO.getId() == null) {
                    addedValues.add(skuPropertyValueDTO);
                } else {
                    newValueIds.add(skuPropertyValueDTO.getId());
                }
            }


            List<SkuPropertyValueDO> skuPropertyValueDOs =
                    ModelUtil.genSkuPropertyValueDOList(addedValues);
            //给属性值列表填充skuPropertyTmplId和bizCode信息
            for (SkuPropertyValueDO skuPropertyValueDO : skuPropertyValueDOs) {
                skuPropertyValueDO.setSkuPropertyTmplId(tmplId);
                skuPropertyValueDO.setBizCode(bizCode);//填充bizCode
            }

            if (skuPropertyValueDOs != null && skuPropertyValueDOs.isEmpty() == false) {
                skuPropertyValueManager.addSkuPropertyValues(skuPropertyValueDOs);
            }


            //需要修改的valueId;
            Set<Long> modifiedIds = Sets.intersection(oldValueIds, newValueIds);


            for (SkuPropertyValueDTO skuPropertyValueDTO : newValues) {

                if (skuPropertyValueDTO.getId() != null && modifiedIds.contains(skuPropertyValueDTO.getId())) {

                    skuPropertyValueManager.updateSkuPropertyValue(skuPropertyValueDTO);
                }
            }


            //需要删除的valueId;
            Set<Long> deletedIds = Sets.difference(oldValueIds, newValueIds);


            if (!CollectionUtils.isEmpty(deletedIds)) {


                for(Long id : deletedIds){

                    skuPropertyValueManager.deleteSkuPropertyValue(id, bizCode);
                }

                // 相关商品下架
                ItemQTO itemQTO = new ItemQTO();
                itemQTO.setBizCode(bizCode);
                itemQTO.setSellerId(0L);
                itemQTO.setCategoryId(skuPropertyTmplDTO.getCategoryId());

                List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);

                if(!CollectionUtils.isEmpty(itemDTOList)){

                    for(ItemDTO itemDTO : itemDTOList){
                        itemManager.skuInvalidItem(itemDTO.getId(), itemDTO.getSellerId(), bizCode);
                    }
                }



            }


            response = ResponseUtil.getSuccessResponse(isSuccessfullyUpdated);
            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        } catch (Exception e) {
            return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SKU_PROPERTY_TMPL.getActionName();
    }
}
