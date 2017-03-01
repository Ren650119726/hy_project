package com.mockuai.itemcenter.core.service.action.item;

import com.google.common.collect.Lists;
import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.higocenter.common.domain.SkuHigoInfoDTO;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.domain.ItemPropertyTmplDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.message.producer.Producer;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ModelUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/9/8.
 */
@Service
public class UpdateItemAction extends TransAction {

    @Resource
    private ItemManager itemManager;

    public ItemManager getItemManager() {
        return itemManager;
    }

    @Resource
    private SkuPropertyManager skuPropertyManager;

    @Resource
    private StoreStockManager storeStockManager;

    public ItemBuyLimitManager getItemBuyLimitManager() {
        return itemBuyLimitManager;
    }

    public void setItemBuyLimitManager(ItemBuyLimitManager itemBuyLimitManager) {
        this.itemBuyLimitManager = itemBuyLimitManager;
    }

    @Resource
    private ItemPropertyManager itemPropertyManager;

    @Resource
    private ItemImageManager itemImageManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private ItemPropertyTmplManager itemPropertyTmplManager;

    @Resource
    private CompositeItemManager compositeItemManager;

    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    private Producer producer;

    @Resource
    private ItemBuyLimitManager itemBuyLimitManager;

    @Resource
    private HigoManager higoManager;

    @Resource
    private ShopManager shopManager;

    @Resource
    private ItemCategoryTmplManager itemCategoryTmplManager;

    @Resource
    private ItemLabelManager itemLabelManager;

    @Resource
    private PublishItemManager publishItemManager;

    private static final Logger log = LoggerFactory.getLogger(UpdateItemAction.class);

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemResponse response = null;
        String bizCode = (String) context.get("bizCode");
        ItemRequest request = context.getRequest();
        final String appKey = (String) request.getParam("appKey");

        // 验证DTO是否为空
        if (request.getParam("itemDTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
        }

        ItemDTO itemDTO = (ItemDTO) request.getParam("itemDTO");

        log.info("[{}] itemDTO:{}", JsonUtil.toJson(itemDTO.getBizTag()));

        if(itemDTO.getBizTag() == 11) {
            itemDTO.setIssueStatus(ItemIssueStatusEnum.ISSUE.getCode());
            itemDTO.setBizCode(bizCode);
            itemManager.updateItemWithBlankDate(itemDTO);
        }else if (itemDTO.getBizTag() == 12) {
            itemDTO.setIssueStatus(ItemIssueStatusEnum.NO_ISSUE.getCode());
            itemDTO.setBizCode(bizCode);
            itemManager.updateItemWithBlankDate(itemDTO);
        }

        // 如果需要修改所有的商品下面的属性 销售属性 及其 图片等 需要先删除 在写入  -- updated by cwr
        Long itemId = itemDTO.getId();
        Long supplierId = itemDTO.getSellerId();


        //判断指定商品是否存在
        ItemDTO itemResult = itemManager.getItem(itemId, supplierId, itemDTO.getBizCode());
        if (itemResult == null) {
            //TODO error handle
        }


        // 修正未存储shop_id的商品数据
        if (itemDTO.getShopId() == null || itemDTO.getShopId().longValue() == 0L) {

            try {

                ShopDTO shopDTO = shopManager.getShop(supplierId, appKey);

                if (shopDTO != null) {
                    itemResult.setShopId(shopDTO.getId());
                }
            } catch (Exception e) {
                //不影响业务，异常直接降级
            }
        }
        if(itemDTO.getCompositeItemList() != null
           && itemDTO.getCompositeItemList().isEmpty()     ){
            compositeItemManager.deleteCompositeItemByItemId(itemId);
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

        //商品类目处理
        if (itemDTO.getCategoryId() == null) {

            if (itemDTO.getCategoryTmplId() == null) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "类目id和类目模板id必须有一个不为空");
            }

            Long categoryId = itemCategoryTmplManager.addOrGetCategoryBytmplId(itemDTO.getCategoryTmplId(), bizCode);

            itemDTO.setCategoryId(categoryId);


        }
        //更新限购信息
        if (itemDTO.getBuyLimit() != null) {
            // 限购;
            itemBuyLimitManager.deleteItemBuyLimit(supplierId, itemId); // 删除原有的限购

            for (LimitEntity entity : itemDTO.getBuyLimit()) {
                if (entity.getLimitCount() == null || entity.getLimitCount().intValue() == 0) {
                    continue;
                }
                ItemBuyLimitDTO itemBuyLimitDTO = new ItemBuyLimitDTO();
                itemBuyLimitDTO.setSellerId(supplierId);
                itemBuyLimitDTO.setItemId(itemId);
                itemBuyLimitDTO.setDeleteMark(0);
                itemBuyLimitDTO.setBeginTime(entity.getBeginTime());
                itemBuyLimitDTO.setEndTime(entity.getEndTime());
                itemBuyLimitDTO.setBizCode(itemResult.getBizCode());
                itemBuyLimitDTO.setBuyCount(entity.getLimitCount());


                itemBuyLimitManager.addItemBuyLimit(itemBuyLimitDTO);

            }
        }

        //TODO 商品索引更新逻辑异步化;索引更新与商品更新的事务性保证
        //更新商品索引
        itemResult = itemManager.getItem(itemId, supplierId, itemDTO.getBizCode());//查询更新完毕之后的商品信息R

        //添加入搜索引擎的逻辑
        if (itemManager.needIndex(itemResult) && itemDTO.getItemStatus() == ItemStatus.ON_SALE.getStatus()) {
            itemSearchManager.setItemIndex(itemResult);
            //更新商品库存
            log.info("发布商品页面：{}",itemDTO.getId());
            //publishItemManager.publish(itemId,appKey,bizCode,true);
        } else {
            itemSearchManager.deleteItemIndex(itemId, supplierId);
            //更新发布状态(未发布)
            log.error("change item issueStatus:{}",itemId);

        }


        //TODO 商品业务状态检查

        if (request.getParam("updateDetail") != null && (Boolean) (request.getParam("updateDetail"))) {

            //更新跨境扩展信息
            if (itemDTO.getHigoMark() != null && itemDTO.getHigoMark().intValue() == 1) {
                ItemHigoInfoDTO itemHigoInfoDTO = ModelUtil.genItemHigoInfoDTO(itemDTO.getHigoExtraInfo());
                itemHigoInfoDTO.setItemId(itemResult.getId());
                itemHigoInfoDTO.setSellerId(itemResult.getSellerId());
                itemHigoInfoDTO.setBizCode(itemResult.getBizCode());
                higoManager.updateItemHigoInfo(itemHigoInfoDTO, appKey);
            }

            // 先更改item表
            Boolean isSuccessfullyUpdated = itemManager.updateItemWithBlankDate(itemDTO);
            //删除item下面的图片 基本属性 销售属性 item_sku 在写入
            int imageRows = this.itemImageManager.deleteItemImageListByItemId(itemId, supplierId);
            int skuPropertyRows = this.skuPropertyManager.deleteByItemId(itemId, supplierId);
//			int itemSkuRows = this.itemSkuManager.deleteByItemId(itemId, supplierId);

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

            // 在写入
            List<ItemPropertyDTO> itemPropertyList = itemDTO.getItemPropertyList();
            //更新商品参数 updated by jiguansheng
            if (itemPropertyList != null  ) {
                 this.itemPropertyManager.deleteByItemId(itemId, supplierId);
              /*  for (ItemPropertyDTO itemProperty : itemPropertyList) {
                    itemProperty.setItemId(itemId);
                    itemProperty.setSellerId(supplierId);

                    ItemPropertyTmplDO itemPropertyTmplDO = propertyTmplMap.get(itemProperty.getItemPropertyTmplId());
                    itemProperty.setBizMark(itemPropertyTmplDO == null ? null : itemPropertyTmplDO.getBizMark());
                    itemProperty.setCode(itemPropertyTmplDO == null ? null : itemPropertyTmplDO.getCode());
                    itemProperty.setValueType(itemPropertyTmplDO == null ? null : itemPropertyTmplDO.getValueType());

                    //TODO 根据字段定义的valueType来判断必须是数字  有些字段必须是数字 比如重量字段必须是数字

                    // TODO 后续考虑使用批量写入
                    ItemPropertyDTO itemProperty2 = this.itemPropertyManager.addItemProperty(itemProperty);
                }*/
                if(!itemPropertyList.isEmpty())
                    itemPropertyManager.addItemProperty(itemPropertyList);
            }

            // 返回的ItemSkuDTO列表
            List<ItemSkuDTO> retItemSkuDTOList = new ArrayList<ItemSkuDTO>();
            List<ItemSkuDTO> itemSkuDTOList = itemDTO.getItemSkuDTOList();

            //查询该商品的sku列表
            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
            itemSkuQTO.setItemId(itemId);
            itemSkuQTO.setSellerId(supplierId);
            itemSkuQTO.setBizCode(bizCode);
            List<ItemSkuDTO> itemOldSkuList = itemSkuManager.queryItemSku(itemSkuQTO);//该商品原有的sku列表
            if (itemOldSkuList == null) {
                //TODO error handle
            }

            /**
             * 处理sku的增删改查
             * （1）如果待更新sku不带id，则认为是新增sku;
             * （2）如果待更新sku带id，则认为是需要更新的sku;
             * （3）如果原有sku中，有sku不存在于待更新sku列表中，则删除该原有sku
             */
            Long stockNum = 0L;
            Long frozenStockNum = 0L;
            for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {

                Long num = itemSkuDTO.getStockNum();
                Long frozenNum = itemSkuDTO.getFrozenStockNum();

                if(num!=null){
                    stockNum += num;
                }

                if(frozenNum!=null){
                    frozenStockNum += frozenNum;
                }

                if (itemSkuDTO.getId() == null) {//新增的sku
                    itemSkuDTO.setItemId(itemId);
                    itemSkuDTO.setBizCode(bizCode);//塞入bizCode
                    itemSkuDTO.setSellerId(supplierId);
                    // 2.添加ItemSkuList
                    ItemSkuDTO retitemSkuDTO = this.itemSkuManager.addItemSku(itemSkuDTO, appKey);

                    //跨境商城添加sku跨境相关信息;
                    if(itemDTO.getHigoMark()!=null&&itemDTO.getHigoMark().intValue()==1){

                        if(itemSkuDTO.getSkuHigoExtraInfoDTO()!=null){

                            SkuHigoInfoDTO skuHigoInfoDTO = new SkuHigoInfoDTO();
                            skuHigoInfoDTO.setBizCode(bizCode);
                            skuHigoInfoDTO.setSellerId(itemDTO.getSellerId());
                            skuHigoInfoDTO.setItemId(itemDTO.getId());
                            skuHigoInfoDTO.setSkuId(retitemSkuDTO.getId());
                            skuHigoInfoDTO.setCustomsCode(itemSkuDTO.getSkuHigoExtraInfoDTO().getCustomsCode());
                            higoManager.addSkuHigoInfo(skuHigoInfoDTO,appKey);
                        }
                    }

                    Long skuId = retitemSkuDTO.getId();


                /*else{
					List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
					//2.5将code_value值更新到item_sku表中
					itemSkuManager.updateItemSkuCodeValue(skuId, supplierId, skuPropertyDTOList);
					// 3.添加SkuPropertyList
					List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
							.addSkuProperty(itemId,skuId, supplierId, skuPropertyDTOList);
					retitemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
					retItemSkuDTOList.add(retitemSkuDTO);
				}*/

                    List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
                    //2.5将code_value值更新到item_sku表中
                    itemSkuManager.updateItemSkuCodeValue(skuId, supplierId, skuPropertyDTOList, bizCode);
                    // 3.添加SkuPropertyList
                    List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
                            .addSkuProperty(itemId, skuId, supplierId, skuPropertyDTOList, bizCode);
                    retitemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
                    retItemSkuDTOList.add(retitemSkuDTO);
                } else {//需要更新的sku
                    itemSkuDTO.setItemId(itemId);
                    itemSkuDTO.setSellerId(supplierId);
                    itemSkuDTO.setBizCode(bizCode);
                    itemSkuManager.updateItemSku(itemSkuDTO);

                    //跨境商城添加sku跨境相关信息;
                    if(itemDTO.getHigoMark()!=null&&itemDTO.getHigoMark().intValue()==1){

                        if(itemSkuDTO.getSkuHigoExtraInfoDTO()!=null){

                            SkuHigoInfoDTO skuHigoInfoDTO = new SkuHigoInfoDTO();
                            skuHigoInfoDTO.setBizCode(bizCode);
                            skuHigoInfoDTO.setSellerId(itemDTO.getSellerId());
                            skuHigoInfoDTO.setItemId(itemDTO.getId());
                            skuHigoInfoDTO.setSkuId(itemSkuDTO.getId());
                            skuHigoInfoDTO.setCustomsCode(itemSkuDTO.getSkuHigoExtraInfoDTO().getCustomsCode());
                            higoManager.updateSkuHigoInfo(skuHigoInfoDTO,appKey);
                        }
                    }

                    Long skuId = itemSkuDTO.getId();

				/* 组合商品部分暂时不需要
				if(itemSkuDTO.getIsComposite() != null && itemSkuDTO.getIsComposite()){
					if(isFirst){
						isFirst = false;//只需要在第一次时候删除
						int compositeItemRow = this.compositeItemManager.deleteCompositeItemByItemId(itemId, supplierId);
					}
					List<CompositeItemDTO> compositeItems = itemSkuDTO.getCompositeItemList();
					if(compositeItems != null){
						for(CompositeItemDTO compositeItem : compositeItems){
							compositeItem.setSkuId(skuId);//关联新写入的sku
							compositeItem.setItemId(itemId);//关联item
							compositeItem.setSupplierId(supplierId);
							//TODO
							//compositeItem.setBizCode(item.getBizCode());
							Long dbId = this.compositeItemManager.addCompositeItem(compositeItem);
						}
					}
				}else{
					List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
					//2.5将code_value值更新到item_sku表中
					itemSkuManager.updateItemSkuCodeValue(skuId, supplierId, skuPropertyDTOList);
					// 3.添加SkuPropertyList
					List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
							.addSkuProperty(itemId,skuId, supplierId, skuPropertyDTOList);
					retitemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
					retItemSkuDTOList.add(retitemSkuDTO);
				}*/

                    //删除原来的
                    skuPropertyManager.deleteSkuPropertyListBySkuId(skuId,supplierId,bizCode);

                    List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
                    //2.5将code_value值更新到item_sku表中

                    if(!CollectionUtils.isEmpty(skuPropertyDTOList)) {
                        itemSkuManager.updateItemSkuCodeValue(skuId, supplierId, skuPropertyDTOList, bizCode);

                        // 3.添加SkuPropertyList
                        List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
                                .addSkuProperty(itemId, skuId, supplierId, skuPropertyDTOList, bizCode);
                        itemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
                    }
                    retItemSkuDTOList.add(itemSkuDTO);
                }
            }


            /*if(itemDTO.getItemSkuDTOList()!=null) {
                StockStatus stockStatus = StockStatusUtil.genStockStatus(itemSkuDTOList);
                //不更新商品这边的库存数量， 统一由仓库suppliercenter控制数量
                itemDTO.setStockNum(stockNum);
                itemDTO.setStockStatus(stockStatus.getStatus());
                itemDTO.setFrozenStockNum(frozenStockNum);

                itemManager.updateItemStockNum(itemDTO);
            }*/

            //处理sku删除逻辑
            List<Long> deleteItemSkuIdList = Lists.newArrayList();
            for (ItemSkuDTO itemSkuDTO : itemOldSkuList) {
                boolean needDelete = true;
                for (ItemSkuDTO updateItemSkuDTO : itemSkuDTOList) {
                    if (updateItemSkuDTO.getId() != null
                            && updateItemSkuDTO.getId().longValue() == itemSkuDTO.getId().longValue()) {
                        needDelete = false;
                        break;
                    }
                }

                //TODO 待重构成批量删除
                if (needDelete) {
                    deleteItemSkuIdList.add(itemSkuDTO.getId());
                    boolean deleteResult = itemSkuManager.deleteItemSku(itemSkuDTO.getId(), itemSkuDTO.getSellerId(), bizCode, appKey);
                    if (deleteResult == false) {
                        //TODO error handle;
                    }
                }
            }

            //删除库存数据

            if(deleteItemSkuIdList.size()>0) {
                storeStockManager.deleteStoreSkuStock(deleteItemSkuIdList, appKey);
            }

            //更新商品服务标签
            //删除标签  加入新的标签
            if(itemDTO.getItemLabelDTOList() != null){
                itemLabelManager.addRItemLabelList(itemDTO);
            }


            // 4.添加副图列表
            List<ItemImageDTO> itemImageDTOList = itemDTO.getItemImageDTOList();
            List<ItemImageDTO> retItemImageDTOList = itemImageManager
                    .addItemImage(itemId, supplierId, itemImageDTOList);
            itemDTO.setBizCode(bizCode);

            producer.send(
                    MessageTopicEnum.ITEM.getTopic(),
                    MessageTagEnum.ITEM_UPDATE.getTag(),
                    itemDTO.getSellerId()+"_"+itemDTO.getId(),
                    itemDTO);

            response = ResponseUtil.getSuccessResponse(true);
            return response;

        } else {
            try {
                Boolean isSuccessfullyUpdated = itemManager.updateItemWithBlankDate(itemDTO);
                response = ResponseUtil.getSuccessResponse(isSuccessfullyUpdated);
                return response;
            } catch (ItemException e) {
                response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
                return response;
            }
        }
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_ITEM.getActionName();
    }

}
