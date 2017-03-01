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
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 查询商品列表Action
 *
 * @author chen.huang
 */
@Service
public class QueryItemAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(QueryItemAction.class);

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSuitManager itemSuitManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private HigoManager higoManager;

    @Resource
    private SpecialItemExtraInfoManager specialItemExtraInfoManager;
    @Resource
    private StoreStockManager storeStockManager;


    @Resource
    private CompositeItemManager compositeItemManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        String querySuit = request.getString("querySuit");
        String appKey = request.getString("appKey");

        if (request.getParam("itemQTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemQTO is null");
        }
        ItemQTO itemQTO = (ItemQTO) request.getParam("itemQTO");

        //如果需要查询特殊状态(回收站)的商品,商品相关表查询条件需要加上delete_mark过滤
        Integer delete_mark = itemQTO.getDeleteMark();

        try {
            itemQTO.setBizCode(bizCode);//填充bizCode
            List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);


          /*  if (itemQTO.getNeedQuerySuit()) {
                for (ItemDTO itemDTO : itemDTOList) {

                    ItemQTO itemQTO1 = new ItemQTO();
                    BeanUtils.copyProperties(itemDTO, itemQTO1);

                    //FIXME 潜规则！为兼容以前代码继续使用
                    if (itemQTO.getSellerId() == 0) {
                        itemQTO.setSellerId(null);
                    }

                    List<ItemDTO> subItems = itemSuitManager.querySubItems(itemQTO1);
                    itemDTO.setSubItemList(subItems);
                }
            }*/

            // 批量查询商品库存
//            List<Long> itemIdList = new ArrayList<Long>();
//            for (ItemDTO itemDTO : itemDTOList) {
//                itemIdList.add(itemDTO.getId());
//            }
//
//            List<ItemSkuDTO> stocks = itemSkuManager.queryStock(itemIdList, bizCode);
//
//            Map<Long, Long> stockMap = new HashMap<Long, Long>();
//
//            for (ItemSkuDTO stock : stocks) {
//                stockMap.put(stock.getItemId(), stock.getStockNum());
//            }
//
//            for (ItemDTO itemDTO : itemDTOList) {
//                Long stockNum = stockMap.get(itemDTO.getId());
//                if (stockNum == null) {
//                    itemDTO.setStockNum(0L);
//                } else {
//                    itemDTO.setStockNum(stockNum);
//                }
//            }

            if (request.getParam("needExtraInfo") != null && request.getParam("needExtraInfo").equals("1")) {//如果需要额外信息

                for (ItemDTO itemDTO : itemDTOList) {

                    ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
                    itemSkuQTO.setItemId(itemDTO.getId());
                    itemSkuQTO.setSellerId(itemDTO.getSellerId());
                    itemSkuQTO.setBizCode(itemDTO.getBizCode());
                    itemSkuQTO.setDeleteMark(delete_mark);

                    List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);


                    Long skuId = itemSkuDTOList.get(0).getId();

                    itemDTO.setItemExtraInfo(
                            specialItemExtraInfoManager.getSpecialItemExtraInfo(skuId, itemDTO.getSellerId(),
                                    null, itemQTO.getItemType(), appKey)
                    );
                }
            }

            //TODO 这里有BUG，manager应该增加一个查询总量的接口
            //response = ResponseUtil.getSuccessResponse(itemDTOList, itemDTOList.size());
            // bug 修复
            //TODO 如果是跨境商品，则需要填充跨境扩展信息
//			Map<Long, ItemDTO> itemMap = new HashMap<Long, ItemDTO>();
//			for(ItemDTO itemDTO: itemDTOList){
//				itemMap.put(itemDTO.getId(), itemDTO);
//			}
//
//			if(itemMap.isEmpty() == false){
//				//TODO 这里暂时先用第一个商品的sellerId，如果后续需要支持多店铺商城，那么这里需要重构
//				List<ItemHigoInfoDTO> itemHigoInfoList = higoManager.getItemHigoInfoList(
//						new ArrayList<Long>(itemMap.keySet()),itemDTOList.get(0).getSellerId(), appKey);
//
//				if(itemHigoInfoList != null){
//					for(ItemHigoInfoDTO itemHigoInfoDTO: itemHigoInfoList){
//						ItemDTO itemDTO = itemMap.get(itemHigoInfoDTO.getItemId());
//						//填充跨境扩展信息
//						itemDTO.setHigoExtraInfo(ModelUtil.genHigoExtraInfoDTO(itemHigoInfoDTO));
//					}
//				}
//			}
            //查询库存 组装参数
            List<Long> itemIdList = Lists.newArrayList();
            for(ItemDTO itemDTO :itemDTOList){
                itemIdList.add(itemDTO.getId());
            }
            //设置组合商品
            if(!itemIdList.isEmpty() && itemQTO.getNeedComposite() != null &&  itemQTO.getNeedComposite()){
                List<CompositeItemDTO> compositeItemList =      compositeItemManager.queryCompositeItemByItemIdList(itemIdList);

                Multimap<Long, CompositeItemDTO> myMultimap = ArrayListMultimap.create();
                for(CompositeItemDTO compositeItemDTO : compositeItemList){
                    myMultimap.put(compositeItemDTO.getItemId(),compositeItemDTO);
                }
                //设置每个sku的组合商品
                for(ItemDTO itemDTO : itemDTOList){
                    Collection<CompositeItemDTO> data = myMultimap.get(itemDTO.getId());
                    if(data != null){
                        itemDTO.setComposite(true);
                        itemDTO.setCompositeItemList(new ArrayList<CompositeItemDTO>(data));
                    }
                }
            }
            StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
            storeItemSkuQTO.setItemIdList(itemIdList);
            //默认需要读取库存信息
            if(itemQTO.getNeedStockNum()){
                //读取商品库存
                List<StoreItemSkuDTO> storeItemSkuList =   storeStockManager.queryItemStock(storeItemSkuQTO,appKey);
                Map<Long,Long> storeItemSkuMap = Maps.newHashMap();
                Map<Long,Long> frozenStockSkuMap = Maps.newHashMap();
                for(StoreItemSkuDTO storeItemSku : storeItemSkuList ){
                    Long itemId = storeItemSku.getItemId();
                    if(!storeItemSkuMap.containsKey(itemId)){
                        storeItemSkuMap.put(itemId,0L);
                    }else{
                        Long stockNum =  storeItemSkuMap.get(itemId);
                        stockNum += storeItemSku.getSalesNum();
                        storeItemSkuMap.put(itemId,stockNum);
                    }
                    if(!frozenStockSkuMap.containsKey(itemId)){
                        frozenStockSkuMap.put(itemId,0L);
                    }else{
                        Long stockNum =  frozenStockSkuMap.get(itemId);
                        stockNum += storeItemSku.getFrozenStockNum();
                        frozenStockSkuMap.put(itemId,stockNum);
                    }
                }
                //设置商品库存  冻结库存
                for(ItemDTO itemDTO : itemDTOList){
                    Long itemId =  itemDTO.getId();
                    if(storeItemSkuMap.containsKey(itemId)){
                        itemDTO.setStockNum(storeItemSkuMap.get(itemDTO.getId()));
                    }
                    if(frozenStockSkuMap.containsKey(itemId)){
                        itemDTO.setFrozenStockNum(frozenStockSkuMap.get(itemDTO.getId()));
                    }

                }
            }

            if (itemQTO.getNeedPaging() != null && itemQTO.getNeedPaging() ) {
                return ResponseUtil.getSuccessResponse(itemDTOList, itemQTO.getTotalCount());
            } else {
                return ResponseUtil.getSuccessResponse(itemDTOList, itemDTOList.size());
            }

        } catch (ItemException e) {
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM.getActionName();
    }
}
