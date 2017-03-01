package com.mockuai.itemcenter.core.service.action.item; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemLabelDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemLabelManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiguansheng on 2016/12/2.
 */
@Service
public class CopyItemAction extends TransAction {

    @Autowired
    private ItemManager itemManager;
    @Autowired
    private ItemLabelManager itemLabelManager;
    @Autowired
    private ItemPropertyManager itemPropertyManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemRequest request = context.getRequest();
        String appKey = (String) request.getParam("appKey");

        Long itemId =    request.getLong("id");
        Long sellerId = (Long) request.getParam("sellerId");
        String bizCode = (String) context.get("bizCode");

        if(itemId == null){
            throw  new ItemException(ResponseCode.PARAM_E_INVALID);
        }

        ItemDTO itemDTO = itemManager.getItem(itemId,sellerId,bizCode);
        List<ItemLabelDTO> labelDTOList =  itemLabelManager.queryItemLabelsByItem(itemDTO);
        // 根据itemId查找该商品下的所有的基本属性
        ItemPropertyQTO itemPropertyQTO = new ItemPropertyQTO();
        itemPropertyQTO.setItemId(itemId);
        itemPropertyQTO.setSellerId(sellerId);
        itemPropertyQTO.setNeedPaging(null); //不需要分页
        List<ItemPropertyDTO> itemPropertyList = this.itemPropertyManager.queryItemProperty(itemPropertyQTO);




        itemDTO.setId(null);
        itemDTO.setSaleBegin(null);
        itemDTO.setSaleEnd(null);
        itemDTO.setMarketPrice(null);
        itemDTO.setPromotionPrice(null);
        itemDTO.setWirelessPrice(null);
        itemDTO.setDeleteMark(0);
        itemDTO.setCreateTime(null);
        itemDTO.setGmtModified(null);
        itemDTO = itemManager.addItem(itemDTO,appKey);
        if(CollectionUtils.isNotEmpty(itemPropertyList)){
            conversionProperty(itemPropertyList,itemDTO.getId());
            itemPropertyManager.addItemProperty(itemPropertyList);
        }
        itemDTO.setItemLabelDTOList(labelDTOList);
        //保存这个
        itemLabelManager.addRItemLabelList(itemDTO);
        return ResponseUtil.getSuccessResponse();
    }



    private void conversionProperty(List<ItemPropertyDTO> itemPropertyList,Long itemId){
        for(ItemPropertyDTO itemPropertyDTO :itemPropertyList){
            itemPropertyDTO.setItemId(itemId);
        }
    }

    @Override
    public String getName() {
        return ActionEnum.COPY_ITEM.getActionName();
    }
}
