package com.mockuai.itemcenter.core.service.action.item;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchResultDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
@Service
public class SearchItemWithDefaultSkuAction implements Action {
    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemSearchQTO itemSearchQTO = (ItemSearchQTO) context.getRequest().getParam("itemSearchQTO");
        String bizCode = (String) context.get("bizCode");
        try {

            itemSearchQTO.setBizCode(bizCode);

            ItemSearchResultDTO itemSearchResultDTO = itemSearchManager.searchItemIndex(itemSearchQTO);

            List<ItemSearchDTO> itemSearchDTOList = itemSearchResultDTO.getItemSearchDTOList();

            List<ItemDTO> itemDTOList = Lists.newArrayListWithCapacity(itemSearchDTOList.size());

            for (ItemSearchDTO itemSearchDTO : itemSearchDTOList) {

                ItemDTO itemDTO = new ItemDTO();

                BeanUtils.copyProperties(itemSearchDTO, itemDTO);

                String itemUid = itemSearchDTO.getItemUid();

                String[] strs = itemUid.split("_");

                Long sellerId = Long.parseLong(strs[0]);

                Long itemId = Long.parseLong(strs[1]);

                itemDTO.setSellerId(sellerId);
                itemDTO.setId(itemId);

                ItemSkuQTO qto = new ItemSkuQTO();

                qto.setItemId(itemId);
                qto.setSellerId(sellerId);
                qto.setBizCode(bizCode);

                List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(qto);

                itemDTO.setItemSkuDTOList(itemSkuDTOList.subList(0, 1));

                itemDTOList.add(itemDTO);
            }

            ItemResponse response = ResponseUtil.getSuccessResponse(itemDTOList);
            response.setTotalCount(itemSearchResultDTO.getCount());


            return response;
        } catch (ItemException e) {
            return ResponseUtil.getErrorResponse(e.getResponseCode());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_WITH_DEFAULT_SKU.getActionName();
    }
}
