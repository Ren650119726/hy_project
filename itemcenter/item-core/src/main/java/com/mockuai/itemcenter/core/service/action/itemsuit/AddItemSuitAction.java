package com.mockuai.itemcenter.core.service.action.itemsuit;

import com.google.common.collect.Sets;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.core.domain.ItemPropertyTmplDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemBuyLimitManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.ItemSuitManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by yindingyu on 15/12/7.
 */
@Service
public class AddItemSuitAction extends TransAction{

    @Resource
    private ItemSuitManager itemSuitManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemBuyLimitManager itemBuyLimitManager;


    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemRequest request = context.getRequest();

        String bizCode = (String)context.get("bizCode");

        if(request.getObject("rItemSuitDTOList")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"rItemSuitDTOList不能为空");
        }

        if(request.getObject("itemDTO")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"itemDTO");
        }

        String appKey = request.getString("appKey");

        List<RItemSuitDTO> rItemSuitDTOList = (List<RItemSuitDTO>) request.getObject("rItemSuitDTOList");

        ItemDTO itemDTO = (ItemDTO) request.getObject("itemDTO");

        //添加套装商品
        itemDTO.setBizCode(bizCode);
        ItemDTO retItemDTO = addSuitItem(itemDTO,appKey);

        Set<Long> subItemSkuIdSet = Sets.newHashSetWithExpectedSize(rItemSuitDTOList.size());

        for(RItemSuitDTO rItemSuitDTO : rItemSuitDTOList){

            if(subItemSkuIdSet.contains(rItemSuitDTO.getSubItemSkuId())){
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,"一个套装中不可以添加相同的的sku");
            }else {
                subItemSkuIdSet.add(rItemSuitDTO.getSubItemSkuId());
            }

            rItemSuitDTO.setBizCode(bizCode);
            rItemSuitDTO.setSuitId(retItemDTO.getId());
            rItemSuitDTO.setSuitSkuId(retItemDTO.getItemSkuDTOList().get(0).getId());
        }

        ItemSuitDTO itemSuitDTO = new ItemSuitDTO();
        itemSuitDTO.setBizCode(bizCode);
        itemSuitDTO.setSellerId(retItemDTO.getSellerId());
        itemSuitDTO.setSuitId(retItemDTO.getId());
        itemSuitDTO.setSuitSkuId(retItemDTO.getItemSkuDTOList().get(0).getId());
        itemSuitDTO.setSaleBegin(itemDTO.getSaleBegin());
        itemSuitDTO.setSaleEnd(itemDTO.getSaleEnd());
        itemSuitDTO.setStatus(1);
        itemSuitDTO.setSaleVolume(0L);


        Long result = itemSuitManager.addItemSuit(itemSuitDTO,rItemSuitDTOList);

        return ResponseUtil.getSuccessResponse(result);

    }

    private ItemDTO addSuitItem(ItemDTO itemDTO,String appKey) throws ItemException{

        ItemDTO retItemDTO = itemManager.addItem(itemDTO,appKey);// 新增加的itemDO


        Long itemId = retItemDTO.getId();
        Long sellerId = retItemDTO.getSellerId();
        String bizCode = retItemDTO.getBizCode();



        // 返回的ItemSkuDTO列表
        List<ItemSkuDTO> retItemSkuDTOList = new ArrayList<ItemSkuDTO>();
        List<ItemSkuDTO> itemSkuDTOList = itemDTO.getItemSkuDTOList();
        for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
            itemSkuDTO.setItemId(itemId);
            itemSkuDTO.setSellerId(sellerId);
            itemSkuDTO.setBizCode(bizCode);//填充bizCode
            // 2.添加ItemSkuList
            ItemSkuDTO retitemSkuDTO = itemSkuManager.addItemSku(itemSkuDTO,appKey);
            Long skuId = retitemSkuDTO.getId();

            List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();

            retItemSkuDTOList.add(retitemSkuDTO);

        }

        System.out.println("add itemSku success");


        retItemDTO.setItemSkuDTOList(retItemSkuDTOList);

        // 设置限购;
        if(itemDTO.getBuyLimit() != null) {
            for(LimitEntity entity: itemDTO.getBuyLimit()) {
                ItemBuyLimitDTO itemBuyLimitDTO = new ItemBuyLimitDTO();
                itemBuyLimitDTO.setSellerId(sellerId);
                itemBuyLimitDTO.setItemId(itemId);
                itemBuyLimitDTO.setDeleteMark(0);
                itemBuyLimitDTO.setBeginTime(entity.getBeginTime());
                itemBuyLimitDTO.setEndTime(entity.getEndTime());
                itemBuyLimitDTO.setBizCode(itemDTO.getBizCode());
                itemBuyLimitDTO.setBuyCount(entity.getLimitCount());
                itemBuyLimitDTO.setBizCode(bizCode);//填充bizCode
                itemBuyLimitManager.addItemBuyLimit(itemBuyLimitDTO);
            }
        }

        return retItemDTO;

    }

    @Override
    public String getName() {
        return ActionEnum.ADD_ITEM_SUIT.getActionName();
    }
}
