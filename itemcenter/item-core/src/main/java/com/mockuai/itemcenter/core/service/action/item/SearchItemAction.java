package com.mockuai.itemcenter.core.service.action.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchResultDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ItemUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
@Service
public class SearchItemAction implements Action {
    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    private ItemManager itemManager;

    @Resource
    private MarketingManager marketingManager;
    @Resource
    private StoreStockManager storeStockManager;
    @Resource
    private CompositeItemManager compositeItemManager;


    private static final Logger log = LoggerFactory.getLogger(SearchItemAction.class);

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemSearchQTO itemSearchQTO = (ItemSearchQTO) context.getRequest().getParam("itemSearchQTO");
        String bizCode = (String) context.get("bizCode");
        String appKey = (String) context.getRequest().getParam("appKey");
        try {

            itemSearchQTO.setBizCode(bizCode);
            List<CompositeItemDTO> compositeItemList = null;
            ItemSearchResultDTO itemSearchResultDTO = itemSearchManager.searchItemIndex(itemSearchQTO);

            List<ItemSearchDTO> itemSearchDTOList = itemSearchResultDTO.getItemSearchDTOList();
            //收集所有的组合商品关系表
            Map<Long,List<CompositeItemDTO>> compositeItemDTOListMap = new HashMap<Long, List<CompositeItemDTO>>();
            if (CollectionUtils.isNotEmpty(itemSearchDTOList)) {

                List<Long> idList = Lists.newArrayList();
                List<Long> skuIdList = Lists.newArrayList();
                for (ItemSearchDTO itemSearchDTO : itemSearchDTOList) {
                    Long itemId = ItemUtil.parseUid(itemSearchDTO.getItemUid()).getItemId();
                    idList.add(itemId);
                }
                // 查询出 组合商品的商品id集合
                List<Long> hasCompositeItemIdList =   itemManager.queryCompositeItem(idList);

                //查出所有商品入参  itemId 和skuId
                for (ItemSearchDTO itemSearchDTO : itemSearchDTOList) {
                    Long itemId = ItemUtil.parseUid(itemSearchDTO.getItemUid()).getItemId();

                    //如果是组合商品  查询 哪些组合sku
                    if(hasCompositeItemIdList.contains(itemId)){
                        compositeItemList =   compositeItemManager.getCompositeItemByItemId(itemId);
                        compositeItemDTOListMap.put(itemId,compositeItemList);
                        for(CompositeItemDTO compositeItem : compositeItemList){
                            skuIdList.add(compositeItem.getSubSkuId());
                        }
                    }


                }
                //查询商品可销售库存
                StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
                storeItemSkuQTO.setItemIdList(idList);
                if(!skuIdList.isEmpty()){
                    storeItemSkuQTO.setOrItemSkuIdList(skuIdList);
                }

                List<StoreItemSkuDTO> storeItemSkuList =  storeStockManager.queryItemStock(storeItemSkuQTO,appKey);

                Map<Long,StoreItemSkuDTO>  storeItemMap = new HashMap<Long, StoreItemSkuDTO>();
                Map<Long,StoreItemSkuDTO>  storeSkuMap = new HashMap<Long, StoreItemSkuDTO>();
                for(StoreItemSkuDTO item : storeItemSkuList){
                    StoreItemSkuDTO storeItemDTO =  storeItemMap.get(item.getItemId());
                    if(storeItemDTO == null){
                        storeItemMap.put(item.getItemId(),item);
                    }else{
                        storeItemDTO.setSalesNum(storeItemDTO.getSalesNum()+ item.getSalesNum());
                        storeItemMap.put(item.getItemId(),storeItemDTO);
                    }
                    StoreItemSkuDTO storeSkuDTO =   storeSkuMap.get(item.getItemSkuId());
                    if(storeSkuDTO == null){
                        storeSkuMap.put(item.getItemSkuId(),item);
                    }else{
                        storeSkuDTO.setSalesNum(storeSkuDTO.getSalesNum()+item.getSalesNum());
                        storeSkuMap.put(item.getItemSkuId(),storeSkuDTO);
                    }

                }
                //Map<Long, ItemDTO> map = itemManager.queryItemMap(idList, bizCode);

                for (ItemSearchDTO itemSearchDTO : itemSearchDTOList) {

                    Long itemId = ItemUtil.parseUid(itemSearchDTO.getItemUid()).getItemId();
                   // ItemDTO itemDTO = map.get(itemId);

                    //设置商品库存状态
                    StoreItemSkuDTO storeItemSkuDTO =   storeItemMap.get(itemId);
                    if(storeItemSkuDTO == null || storeItemSkuDTO.getSalesNum() == null || storeItemSkuDTO.getSalesNum() == 0){
                        itemSearchDTO.setStockStatus(0);
                    }else if( storeItemSkuDTO.getSalesNum().longValue()>0){
                        itemSearchDTO.setStockStatus(1);
                    }
                    //设置组合商品库存
                    if( hasCompositeItemIdList.contains(itemId)){
                        //默认设置有库存
                        itemSearchDTO.setStockStatus(1);
                         List<CompositeItemDTO> thatCompositeItem =  compositeItemDTOListMap.get(itemId);
                        if(CollectionUtils.isEmpty(thatCompositeItem)){
                            itemSearchDTO.setStockStatus(0);
                            continue;
                        }
                        for(CompositeItemDTO compositeItem : thatCompositeItem){
                            //商品没有对应的库存信息，则售罄

                            StoreItemSkuDTO storeSkuDTO =   storeSkuMap.get(compositeItem.getSubSkuId());
                            if(storeSkuDTO == null || storeSkuDTO.getSalesNum() == null || storeSkuDTO.getSalesNum() == 0){
                                itemSearchDTO.setStockStatus(0);
                            }else{
                                Long salesNum =   storeSkuDTO.getSalesNum();
                                if(compositeItem.getNum().intValue() > salesNum.intValue()){
                                    itemSearchDTO.setStockStatus(0);
                                }
                            }

                        }
                    }


                }

                ItemQTO itemQTO = new ItemQTO();
                itemQTO.setSellerId(0L);
                itemQTO.setIdList(idList);

                List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);

                List<DiscountInfo> discountInfoList = marketingManager.discountInfoOfItemListAction(itemDTOList, null, appKey);

               // Map<String, DiscountInfo> discountInfoMap = Maps.newHashMapWithExpectedSize(discountInfoList.size());

                //全场活动标志
                boolean allFlag = false;

                //全场活动的icon_url
                String allIconUrl = "";
                //限时购集合
                Map<Long, DiscountInfo> timeRangeInfoMap = Maps.newHashMap();
                Multimap<String, MarketActivityDTO> activityMap =   ArrayListMultimap.create();
                for (DiscountInfo discountInfo : discountInfoList) {


                    if(ToolType.TIME_RANGE_DISCOUNT.getCode().equals(discountInfo.getActivity().getToolCode())){
                        timeRangeInfoMap.put(discountInfo.getActivity().getId(),discountInfo);
                    }

                   /* if(CollectionUtils.isEmpty(discountInfo.getItemList())){
                        allFlag = true;
                        allIconUrl = discountInfo.getActivity().getIcon();
                        activity = discountInfo.getActivity();
                        break;
                    }*/
                    //itemList是空集合 表示所有的商品都参加活动
                    if(CollectionUtils.isEmpty(discountInfo.getItemList())){
                        for (ItemSearchDTO itemSearchDTO : itemSearchDTOList) {
                            activityMap.put(itemSearchDTO.getItemUid(),discountInfo.getActivity());
                        }
                    }else{
                        for (MarketItemDTO marketItemDTO : discountInfo.getItemList()) {
                            activityMap.put(marketItemDTO.getSellerId() + "_" + marketItemDTO.getItemId(),discountInfo.getActivity());
                            //discountInfoMap.put(marketItemDTO.getSellerId() + "_" + marketItemDTO.getItemId(), discountInfo);
                        }
                    }


                }

                for (ItemSearchDTO itemSearchDTO : itemSearchDTOList) {
                        Collection<MarketActivityDTO> marketActivityList = activityMap.get(itemSearchDTO.getItemUid());

                        if (CollectionUtils.isNotEmpty(marketActivityList)) {
                            List<MarketActivityDTO> data = new ArrayList<MarketActivityDTO>(marketActivityList);
                            itemSearchDTO.setActivityIconUrl(data.get(0).getIcon());
                            itemSearchDTO.setOnSale(1);
                            itemSearchDTO.setMarketActivityDTO(data);
                            //找到限时购优惠
                            DiscountInfo discountInfo =  findTimeRangeDiscountInfo(data,timeRangeInfoMap);
                            if(discountInfo != null){
                                setTimeRangePrice(itemSearchDTO,discountInfo.getItemList());
                            }
                        } else {
                            itemSearchDTO.setOnSale(0);
                        }
                    }
            }


            ItemResponse response = ResponseUtil.getSuccessResponse(itemSearchResultDTO.getItemSearchDTOList());
            response.setTotalCount(itemSearchResultDTO.getCount());


            return response;
        } catch (ItemException e) {
            return ResponseUtil.getErrorResponse(e.getResponseCode());
        } catch (Exception e) {
            log.error("search item error", e);
            return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }



    private DiscountInfo findTimeRangeDiscountInfo(List<MarketActivityDTO> data ,  Map<Long, DiscountInfo> timeRangeInfoMap ){

        for(MarketActivityDTO activityDTO :data){
            if(timeRangeInfoMap.containsKey(activityDTO.getId())){
                return timeRangeInfoMap.get(activityDTO.getId());
            }
        }
        return null;
    }

    private void setTimeRangePrice(ItemSearchDTO itemSearchDTO ,List<MarketItemDTO> itemList){
        Long itemId = ItemUtil.parseUid(itemSearchDTO.getItemUid()).getItemId();

        for(MarketItemDTO itemDTO : itemList){
            if(itemDTO.getItemId().intValue() == itemId.intValue() ){
                itemSearchDTO.setWirelessPrice(itemDTO.getUnitPrice());
                itemSearchDTO.setPromotionPrice(itemDTO.getUnitPrice());
                break;
            }
        }
    }



    @Override
    public String getName() {
        return ActionEnum.SEARCH_ITEM.getActionName();
    }
}
