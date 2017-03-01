package com.mockuai.itemcenter.core.service.action.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCollectionManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 查询商品收藏列表Action
 * 移除店铺概念， 做些修改
 */
@Service
public class QueryItemCollectionAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryItemCollectionAction.class);
    @Resource
    private ItemCollectionManager itemCollectionManager;
    @Resource
    private ItemManager itemManager;

    //@Resource
   // private DistributorManager distributorManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        ItemCollectionQTO itemCollectionQTO = (ItemCollectionQTO) request.getParam("itemCollectionQTO");

        String bizCode = (String) context
                .get("bizCode");
        String appKey = (String) context.get("appKey");

        if (itemCollectionQTO == null) {
            return new ItemResponse(ResponseCode.PARAM_E_MISSING, "itemCollectionQTO is null");
        }

        boolean pageFlag = false;

        if(itemCollectionQTO.getNeedPaging()!=null&&itemCollectionQTO.getNeedPaging()){
            pageFlag = true;
            itemCollectionQTO.setNeedPaging(null);
        }


        try {
            List<ItemCollectionDTO> itemCollectionDTOList = itemCollectionManager.queryItemCollection(itemCollectionQTO);

            if (itemCollectionDTOList.size() > 0) {

                //Map<Long, Long> distributorIdMap = Maps.newHashMap();
                Map<Long,ItemCollectionDTO> itemCollectionMap = Maps.newHashMap();
                List<Long> itemIdList = Lists.newArrayList();
                for (ItemCollectionDTO itemCollectionDTO : itemCollectionDTOList) {
                    //distributorIdMap.put(itemCollectionDTO.getItemId(), itemCollectionDTO.getDistributorId());
                    itemIdList.add(itemCollectionDTO.getItemId());
                    itemCollectionMap.put(itemCollectionDTO.getItemId(),itemCollectionDTO);
                }

                ItemQTO itemQTO = new ItemQTO();
                itemQTO.setBizCode(bizCode);
                itemQTO.setIdList(itemIdList);

                if(pageFlag){
                    itemQTO.setNeedPaging(true);
                    itemQTO.setOffset(itemCollectionQTO.getOffset());
                    itemQTO.setPageSize(itemCollectionQTO.getPageSize());
                }

                itemQTO.setSellerId(0L);
                List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);
                //设置分享人id
                for(ItemDTO itemDTO : itemDTOList){
                    itemDTO.setShareUserId(itemCollectionMap.get(itemDTO.getId()).getShareUserId());
                }
                //去除店铺概念， 所以移除掉
               /* List<Long> distributorIdList = Lists.newArrayList(distributorIdMap.values());

                DistShopQTO qto = new DistShopQTO();
                qto.setSellerIds(distributorIdList);

                List<DistShopDTO> sellerDTOList = distributorManager.queryShop(qto, appKey);

                Map<Long,DistShopDTO> sellerDTOMap = Maps.newHashMap();

                for(DistShopDTO sellerDTO : sellerDTOList){
                    sellerDTOMap.put(sellerDTO.getSellerId(),sellerDTO);
                }


                List<ItemDTO> itemDTOs = Lists.newArrayList();


                for(ItemDTO itemDTO : itemDTOList){

                    DistShopDTO sellerDTO = sellerDTOMap.get(distributorIdMap.get(itemDTO.getId()));

                    if(sellerDTO !=null){

                        DistShopForMopDTO distShopForMopDTO = new DistShopForMopDTO();
                        distShopForMopDTO.setDistributorId(sellerDTO.getSellerId());
                        distShopForMopDTO.setShopName(sellerDTO.getShopName());
                        itemDTO.setDistributorInfoDTO(distShopForMopDTO);
                        itemDTOs.add(itemDTO);
                    }

                }*/

              /*  if(itemCollectionQTO.getNeedPaging()!=null&&itemCollectionQTO.getNeedPaging()){
                    response = ResponseUtil.getSuccessResponse(itemDTOs, itemCollectionQTO.getTotalCount());
                }else {
                    response = ResponseUtil.getSuccessResponse(itemDTOs,itemDTOs.size());
                }*/

                if(itemCollectionQTO.getNeedPaging()!=null&&itemCollectionQTO.getNeedPaging()){
                    response = ResponseUtil.getSuccessResponse(itemDTOList, itemCollectionQTO.getTotalCount());
                }else {
                    response = ResponseUtil.getSuccessResponse(itemDTOList,itemDTOList.size());
                }
            }else {
                response = ResponseUtil.getSuccessResponse(Collections.EMPTY_LIST,0L);
            }

            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_COLLECTION.getActionName();
    }
}
