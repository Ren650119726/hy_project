package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.higocenter.common.domain.SkuHigoInfoDTO;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.constant.StockStatus;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.util.StockStatusUtil;
import com.mockuai.itemcenter.core.domain.ItemPropertyTmplDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ModelUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 增加商品Action
 *
 * @author chen.huang
 */
@Service
public class AddItemAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddItemAction.class);
    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private ItemPropertyManager itemPropertyManager; // updated by cwr

    @Resource
    private SkuPropertyManager skuPropertyManager;

    @Resource
    private ItemImageManager itemImageManager;

    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    private CompositeItemManager compositeItemManager;

    @Resource
    private ItemBuyLimitManager itemBuyLimitManager;

    @Resource
    private ItemPropertyTmplManager itemPropertyTmplManager;

    @Resource
    private HigoManager higoManager;

    @Resource
    private ShopManager shopManager;

    @Resource
    private ItemCategoryManager itemCategoryManager;

    @Resource
    private ItemCategoryTmplManager itemCategoryTmplManager;

    @Resource
    TransactionTemplate transactionTemplate;


    @Resource
    private ItemLabelManager itemLabelManager;

    public ItemResponse doTransaction(final RequestContext context) throws ItemException {
        //@SuppressWarnings("rawtypes")

        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        String appKey = (String) request.getParam("appKey");


        // 验证DTO是否为空
        if (request.getParam("itemDTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
        }
        ItemDTO itemDTO = (ItemDTO) request.getParam("itemDTO");


        //商品类目处理
        if (itemDTO.getCategoryId() == null) {

            if (itemDTO.getCategoryTmplId() == null) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "类目id和类目模板id必须有一个不为空");
            }

            Long categoryId = itemCategoryTmplManager.addOrGetCategoryBytmplId(itemDTO.getCategoryTmplId(), bizCode);

            itemDTO.setCategoryId(categoryId);


        }

        //上传商品描述内容

        // 1.首先添加item返回itemId
        itemDTO.setBizCode(bizCode);//填充bizCode

        itemDTO.setWirelessPrice(itemDTO.getPromotionPrice());
        //防止传参带有itemID
        if (itemDTO.getId() != null) {
            itemDTO.setId(null);
        }

        /**
         * 如果是跨境商品，则在商品表中冗余发货方式字段
         * （FIXME 这么做是为了兼容旧的跨境商城逻辑，comment by caishen on 2016-01-09）
         */
        if (itemDTO.getHigoMark() != null
                && itemDTO.getHigoMark().intValue() == 1 && itemDTO.getHigoExtraInfo() != null) {
            itemDTO.setDeliveryType(itemDTO.getHigoExtraInfo().getDeliveryType());
        }

        if (itemDTO.getShopId() == null || itemDTO.getShopId().longValue() == 0L) {

            ShopDTO shopDTO = shopManager.getShop(itemDTO.getSellerId(), appKey);

            //兼容旧有单店铺逻辑，单店铺商品不存在shopId
            if (shopDTO != null) {
                itemDTO.setShopId(shopDTO.getId());
            } else {
                itemDTO.setShopId(0L);
            }

        }

        itemDTO.setStockStatus(StockStatus.SELL_OUT.getStatus());
        //设置为组合商品
        if(CollectionUtils.isNotEmpty(itemDTO.getCompositeItemList())){
            itemDTO.setItemType(ItemType.COMPOSITE_ITEM.getType());
        }
        ItemDTO retItemDTO = itemManager.addItem(itemDTO, appKey);// 新增加的itemDO

        Long itemId = retItemDTO.getId();
        Long sellerId = retItemDTO.getSellerId();

        //写入属性具体值的时候需要同时写入bizMark 和 propretyCode  需要先查询出来
        ItemPropertyTmplQTO itemPropertyTmplQTO = new ItemPropertyTmplQTO();
        //根据该商品的所属的类目来查找属性列表 该属性有一些基本的基本配置比如： bizMark 和 propertyCode
        itemPropertyTmplQTO.setCategoryId(itemDTO.getCategoryId());
        Map<Long, ItemPropertyTmplDO> propertyTmplMap = new HashMap<Long, ItemPropertyTmplDO>();
        List<ItemPropertyTmplDO> itemPropertyTmplList = this.itemPropertyTmplManager.queryItemPropertyTmpl(itemPropertyTmplQTO);
        if (itemPropertyTmplList != null) {
            for (ItemPropertyTmplDO item : itemPropertyTmplList) {
                propertyTmplMap.put(item.getId(), item);
            }
        }

        //普通属性的处理  非sku属性的处理  --  updated by cwr
        //更新商品参数 -- updated by jiguansheng
        List<ItemPropertyDTO> itemPropertyList = itemDTO.getItemPropertyList();
        if (!CollectionUtils.isEmpty(itemPropertyList)) {
           for (ItemPropertyDTO itemProperty : itemPropertyList) {
                itemProperty.setItemId(itemId);
                //itemProperty.setSellerId(sellerId);

                //ItemPropertyTmplDO itemPropertyTmplDO = propertyTmplMap.get(itemProperty.getItemPropertyTmplId());
                //itemProperty.setBizMark(itemPropertyTmplDO == null ? null : itemPropertyTmplDO.getBizMark());
                //itemProperty.setCode(itemPropertyTmplDO == null ? null : itemPropertyTmplDO.getCode());
                //itemProperty.setValueType(itemPropertyTmplDO == null ? null : itemPropertyTmplDO.getValueType());
                //TODO 根据字段定义的valueType来判断必须是数字  有些字段必须是数字 比如重量字段必须是数字

                // TODO 后续考虑使用批量写入
                //itemProperty.setBizCode(bizCode);//填充bizCode
                //ItemPropertyDTO itemProperty2 = this.itemPropertyManager.addItemProperty(itemProperty);
            }
             itemPropertyManager.addItemProperty(itemPropertyList);
        }

        // 返回的ItemSkuDTO列表
        List<ItemSkuDTO> retItemSkuDTOList = new ArrayList<ItemSkuDTO>();
        List<ItemSkuDTO> itemSkuDTOList = itemDTO.getItemSkuDTOList();

        Long stockNum = 0L;

        for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {

            //防止传参带有itemSkuId
            if (itemDTO.getId() == null) {
                itemDTO.setId(null);
            }

            itemSkuDTO.setItemId(itemId);
            itemSkuDTO.setSellerId(sellerId);
            itemSkuDTO.setBizCode(bizCode);//填充bizCode
            // 2.添加ItemSkuList
            ItemSkuDTO retitemSkuDTO = itemSkuManager.addItemSku(itemSkuDTO, appKey);

            //跨境商城添加sku跨境相关信息;
            if(itemDTO.getHigoMark()!=null&&itemDTO.getHigoMark().intValue()==1){

                if(itemSkuDTO.getSkuHigoExtraInfoDTO()!=null){

                    SkuHigoInfoDTO skuHigoInfoDTO = new SkuHigoInfoDTO();
                    skuHigoInfoDTO.setBizCode(bizCode);
                    skuHigoInfoDTO.setSellerId(sellerId);
                    skuHigoInfoDTO.setItemId(retItemDTO.getId());
                    skuHigoInfoDTO.setSkuId(retitemSkuDTO.getId());
                    skuHigoInfoDTO.setCustomsCode(itemSkuDTO.getSkuHigoExtraInfoDTO().getCustomsCode());
                    higoManager.addSkuHigoInfo(skuHigoInfoDTO,appKey);
                }
            }

            Long skuId = retitemSkuDTO.getId();

            List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();

            if (skuPropertyDTOList != null) {
                for (SkuPropertyDTO skuPropertyDTO : skuPropertyDTOList) {
                    skuPropertyDTO.setBizCode(bizCode);//填充bizCode
                }


                //2.5将code_value值更新到item_sku表中
                itemSkuManager.updateItemSkuCodeValue(skuId, sellerId, skuPropertyDTOList, bizCode);
                // 3.添加SkuPropertyList
                List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
                        .addSkuProperty(itemId, skuId, sellerId, skuPropertyDTOList, bizCode);


                retitemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);

            }

            retItemSkuDTOList.add(retitemSkuDTO);

            stockNum += itemSkuDTO.getStockNum();
        }

        itemDTO.setStockNum(stockNum);
        itemDTO.setFrozenStockNum(0L);

        StockStatus stockStatus = StockStatusUtil.genStockStatus(itemSkuDTOList);

        itemDTO.setStockStatus(stockStatus.getStatus());

        itemManager.updateItemStockNum(itemDTO);


        System.out.println("add itemSku success");

        // 4.添加副图列表
        List<ItemImageDTO> itemImageDTOList = itemDTO.getItemImageDTOList();

        if (itemImageDTOList != null) {
            for (ItemImageDTO itemImageDTO : itemImageDTOList) {
                itemImageDTO.setBizCode(bizCode);//填充bizCode
            }

            List<ItemImageDTO> retItemImageDTOList = itemImageManager
                    .addItemImage(itemId, sellerId, itemImageDTOList);

            retItemDTO.setItemImageDTOList(retItemImageDTOList);
        }

        // 保存组合商品
        if(CollectionUtils.isNotEmpty(itemDTO.getCompositeItemList())){
            compositeItemManager.deleteCompositeItemByItemId(itemId);
            List<CompositeItemDTO> compositeItems = itemDTO.getCompositeItemList();
            if(compositeItems != null){
                for(CompositeItemDTO compositeItem : compositeItems){
                    //compositeItem.setSkuId(skuId);//关联新写入的sku
                    compositeItem.setItemId(itemId);//关联item
                    //TODO
                    //compositeItem.setBizCode(item.getBizCode());

                }
            }
            this.compositeItemManager.batchAddCompositeItem(compositeItems);
        }



        retItemDTO.setItemSkuDTOList(retItemSkuDTOList);
        response = ResponseUtil.getSuccessResponse(retItemDTO);

        // 设置限购;
        if (itemDTO.getBuyLimit() != null) {
            for (LimitEntity entity : itemDTO.getBuyLimit()) {
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

        //如果是跨境商品，则需要添加跨境扩展信息
        if (itemDTO.getHigoMark() != null && itemDTO.getHigoMark().intValue() == 1) {
            HigoExtraInfoDTO higoExtraInfoDTO = itemDTO.getHigoExtraInfo();
            ItemHigoInfoDTO itemHigoInfoDTO = ModelUtil.genItemHigoInfoDTO(higoExtraInfoDTO);
            itemHigoInfoDTO.setItemId(itemId);
            itemHigoInfoDTO.setSellerId(sellerId);
            itemHigoInfoDTO.setBizCode(bizCode);
            itemHigoInfoDTO = higoManager.addItemHigoInfo(itemHigoInfoDTO, appKey);
        }

        //添加入搜索引擎的逻辑
//        if (itemManager.needIndex(retItemDTO)) {
//            itemSearchManager.setItemIndex(retItemDTO);
//        }

        //添加商品时,更新所属类目的最新使用时间
        if (retItemDTO.getCategoryId() != null && retItemDTO.getCategoryId().longValue() != 0) {

            //错误降级处理
            try {
                itemCategoryManager.updateGmtUsed(retItemDTO.getCategoryId(), bizCode);
            } catch (ItemException e) {
                log.error("更新类目最新使用时间时出现异常 item_uid:{}_{} category_id:{} biz_code:{} code:{} msg:{}"
                        , sellerId, itemId, retItemDTO.getCategoryId(), bizCode, e.getCode(), e.getMessage());
            } catch (Exception e) {
                log.error("更新类目最新使用时间时出现未知异常 item_uid:{}_{} category_id:{} biz_code:{} msg:{}"
                        , sellerId, itemId, retItemDTO.getCategoryId(), bizCode, e.getMessage());
            }
        }

        //设置商品服务标签
        if(itemDTO.getItemLabelDTOList()!= null && !itemDTO.getItemLabelDTOList().isEmpty()){
            itemLabelManager.addRItemLabelList(itemDTO);
        }
        return response;


    }

    private ItemSkuDTO getCheapestItemSku(List<ItemSkuDTO> itemSkuDTOs) {
        if (itemSkuDTOs == null) {
            return null;
        }

        Map<Long, ItemSkuDTO> itemPriceMap = new HashMap<Long, ItemSkuDTO>();
        for (ItemSkuDTO itemSkuDTO : itemSkuDTOs) {
            itemPriceMap.put(itemSkuDTO.getPromotionPrice(), itemSkuDTO);
        }
        //TODO
        return null;
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_ITEM.getActionName();
    }
}
