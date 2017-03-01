package com.mockuai.itemcenter.core.manager.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mockuai.common.uils.staticpage.SerialPageEnum;
import com.mockuai.common.uils.staticpage.StaticPage;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.higocenter.common.domain.SkuHigoInfoDTO;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.mop.MopBaseItemDTO;
import com.mockuai.itemcenter.common.domain.qto.*;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.action.item.GetItemAction;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ModelUtil;
import com.mockuai.itemcenter.core.util.MopApiUtil;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hy on 2016/12/16.
 */
@Component
public class PublishItemManagerImpl implements PublishItemManager {

    private static final Logger log = LoggerFactory.getLogger(GetItemAction.class);

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private ItemImageManager itemImageManager;

    @Resource
    private SkuPropertyManager skuPropertyManager;

    @Resource
    private ItemPropertyManager itemPropertyManager;

    @Resource
    private SellerBrandManager sellerBrandManager;

    @Resource
    private ItemBuyLimitManager itemBuyLimitManager;

    @Resource
    private ItemLabelManager itemLabelManager;

    @Resource
    private ValueAddedServiceManager valueAddedServiceManager;

    @Resource
    private ItemSuitManager itemSuitManager;

    @Resource
    private HigoManager higoManager;

    @Resource
    private FreightManager freightManager;

    @Resource
    private ItemSalesVolumeManager itemSalesVolumeManager;

    @Resource
    private ItemDescTmplManager itemDescTmplManager;

    @Resource
    private DistributorManager distributorManager;


    @Resource
    private ItemSearchManager itemSearchManager;
    @Resource
    private StaticPage staticPage;



    @Override
    public MopBaseItemDTO publish(Long itemId,String appKey,String bizCode,Boolean needWritePage) throws ItemException {
        ItemDTO itemDTO = null;
        ItemResponse response = null;
        //  Integer type = request.getInteger("type");
        // 验证ID
        /*if (request.getLong("type") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "type is missing");
        }*/
        if (itemId == null) {
            throw  new ItemException(ResponseCode.PARAM_E_MISSING,"itemID is missing");
        }

        Long sellerId =Long.parseLong(ParamEnum.SELLER_ID.getValue());// 供应商ID
        List<ItemSkuDistPlanDTO> skuDistPlanList = distributorManager.getItemSkuDistPlanList(itemId, appKey);
        Map<Long, ItemSkuDistPlanDTO> skuDistMap = new HashMap<Long, ItemSkuDistPlanDTO>(skuDistPlanList.size());
        for (ItemSkuDistPlanDTO skuDistPlan : skuDistPlanList) {
            skuDistMap.put(skuDistPlan.getItemSkuId(), skuDistPlan);
        }

        // 如果是需要详细信息时候  需要找到普通属性和销售属性值 －－ updated by cwr
            try {
                itemDTO = itemManager.getItem(itemId, sellerId, bizCode);
                ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
                itemSkuQTO.setItemId(itemId);
                itemSkuQTO.setSellerId(sellerId);
                itemSkuQTO.setBizCode(bizCode);
                // 获取ItemSku列表
                List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);

                // 根据itemId查找该商品下的所有的基本属性
                ItemPropertyQTO itemPropertyQTO = new ItemPropertyQTO();
                itemPropertyQTO.setItemId(itemId);
                itemPropertyQTO.setSellerId(sellerId);
                itemPropertyQTO.setNeedPaging(null); //不需要分页
                List<ItemPropertyDTO> itemPropertyList = this.itemPropertyManager.queryItemProperty(itemPropertyQTO);

                //TODO 根据appkey判断应用类型，并根据property的bizMark来控制是否返回数据（有些字段在面向买家的应用是不返回的）

                itemDTO.setItemPropertyList(itemPropertyList);

                // 根据itemId查找该商品下的所有的销售属性值
                SkuPropertyQTO skuPropertyQTO = new SkuPropertyQTO();
                skuPropertyQTO.setSellerId(sellerId);
                skuPropertyQTO.setItemId(itemId);
                skuPropertyQTO.setNeedPaging(null);//不需要分页
                List<SkuPropertyDTO> skuPropertyList = this.skuPropertyManager.querySkuProperty(skuPropertyQTO);

                if (itemDTO.getHigoMark() != null && itemDTO.getHigoMark().equals(1)) {

                    List<Long> skuIdList = Lists.newArrayList();
                    for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
                        skuIdList.add(itemSkuDTO.getId());
                    }

                    if (skuIdList.size() > 0) {

                        List<SkuHigoInfoDTO> skuHigoInfoDTOList = higoManager.getSkuHigoInfoList(skuIdList, sellerId, appKey);

                        Map<Long, SkuHigoInfoDTO> skuHigoInfoDTOMap = Maps.newHashMap();

                        for (SkuHigoInfoDTO skuHigoInfoDTO : skuHigoInfoDTOList) {
                            skuHigoInfoDTOMap.put(skuHigoInfoDTO.getSkuId(), skuHigoInfoDTO);
                        }

                        for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
                            SkuHigoInfoDTO skuHigoInfoDTO = skuHigoInfoDTOMap.get(itemSkuDTO.getId());

                            SkuHigoExtraInfoDTO skuHigoExtraInfoDTO = new SkuHigoExtraInfoDTO();

                            if (skuHigoInfoDTO != null) {
                                skuHigoExtraInfoDTO.setCustomsCode(skuHigoInfoDTO.getCustomsCode());
                                itemSkuDTO.setSkuHigoExtraInfoDTO(skuHigoExtraInfoDTO);
                            }
                        }
                    }
                }

                itemDTO.setSkuPropertyList(skuPropertyList);

                Multimap<Long, SkuPropertyDTO> propertyMap = HashMultimap.create();

                for (SkuPropertyDTO skuPropertyDTO : skuPropertyList) {
                    propertyMap.put(skuPropertyDTO.getSkuId(), skuPropertyDTO);
                }
                StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
                storeItemSkuQTO.setItemId(itemId);

                for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {

                    Collection<SkuPropertyDTO> collection = propertyMap.get(itemSkuDTO.getId());

                    if (!CollectionUtils.isEmpty(collection)) {
                        itemSkuDTO.setSkuPropertyDTOList(Lists.newArrayList(propertyMap.get(itemSkuDTO.getId())));
                    }
                }

                // 获取副图列表
                ItemImageQTO itemImageQTO = new ItemImageQTO();
                itemImageQTO.setItemId(itemId);
                itemImageQTO.setSellerId(sellerId);
                List<ItemImageDTO> itemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);

                //往商品SKU中填充商品sku图片
                fillItemSkuImage(itemImageDTOList, itemSkuDTOList);

                itemDTO.setItemImageDTOList(itemImageDTOList);
                itemDTO.setItemSkuDTOList(itemSkuDTOList);

            } catch (ItemException e) {
                log.error("", e);
                throw  e;
            }

        //获取商品品牌信息
        Long itemBrandId = itemDTO.getItemBrandId();
        if (itemBrandId != null && itemBrandId != 0) {

            SellerBrandDTO sellerBrand = sellerBrandManager.getSellerBrand(itemBrandId);

            if (sellerBrand != null) {
                itemDTO.setItemBrandDTO(sellerBrand);
                //TODO 这里暂时返回兼容itemProperty中的品牌属性，后续需要考虑在商家中心来插入，平台不感知该属性 add by zengzhangqiang on 2015-06-11
                if (itemDTO.getItemPropertyList() == null) {
                    itemDTO.setItemPropertyList(new ArrayList<ItemPropertyDTO>());
                }
            }
        }
        // 判断商品限购;
        List<ItemBuyLimitDTO> itemBuyLimitDTOs = itemBuyLimitManager.queryItemBuyLimitRecord(sellerId, itemId);
        if (itemBuyLimitDTOs != null) {
            List<LimitEntity> limitEntities = new ArrayList<LimitEntity>();
            for (ItemBuyLimitDTO itemBuyLimitDTO : itemBuyLimitDTOs) {
                LimitEntity limitEntity = new LimitEntity();
                limitEntity.setLimitCount(itemBuyLimitDTO.getBuyCount());
                try {
                    //如果限购时间为空，则代表永久限购，时间上可以填充一个默认最小值和最大值
                    if (itemBuyLimitDTO.getBeginTime() == null) {
                        limitEntity.setBeginTime(dateFormat.parse("2000-01-01 00:00:01"));
                    } else {
                        limitEntity.setBeginTime(itemBuyLimitDTO.getBeginTime());
                    }

                    if (itemBuyLimitDTO.getEndTime() == null) {
                        limitEntity.setEndTime(dateFormat.parse("2102-01-01 00:00:01"));
                    } else {
                        limitEntity.setEndTime(itemBuyLimitDTO.getEndTime());
                    }

                } catch (Exception e) {
                    log.error("", e);
                }
                limitEntities.add(limitEntity);
            }
            itemDTO.setBuyLimit(limitEntities);
        }


        try {
            //添加服务标签列表
            List<ItemLabelDTO> itemLabelDTOList = itemLabelManager.queryItemLabelsByItem(itemDTO);
            itemDTO.setItemLabelDTOList(itemLabelDTOList);
        } catch (ItemException e) {
            log.error("查询服务标签列表失败 item_id {} biz_code {}", itemId, bizCode);
            log.error("查询出错", e);
        } catch (Exception e) {
            log.error("查询服务标签列表失败 item_id {} biz_code {}", itemId, bizCode);
            log.error("查询出错", e);

        }

        try {
            //添加增值服务列表,出现错误时降级处理
            List<ValueAddedServiceTypeDTO> valueAddedServiceTypeDTOs = valueAddedServiceManager.queryValueAddedServiceByItem(itemDTO);
            itemDTO.setValueAddedServiceTypeDTOList(valueAddedServiceTypeDTOs);
        } catch (ItemException e) {
            log.error("查询增值服务列表失败 item_id {} biz_code {}", itemId, bizCode);
        } catch (Exception e) {
            log.error("查询增值服务列表失败 item_id {} biz_code {}", itemId, bizCode);
        }


        //添加套装子商品信息
        if (itemDTO.getItemType() != null && itemDTO.getItemType() == DBConst.SUIT_ITEM.getCode()) {

            ItemQTO itemQTO = new ItemQTO();
            BeanUtils.copyProperties(itemDTO, itemQTO);

            List<ItemDTO> itemDTOList = itemSuitManager.querySubItems(itemQTO);

            Long stockNum = itemDTO.getItemSkuDTOList().get(0).getStockNum();

            for (ItemDTO itemDTO1 : itemDTOList) {

                Long subStock = itemDTO1.getItemSkuDTOList().get(0).getStockNum();

                stockNum = subStock < stockNum ? subStock : stockNum;
            }
            itemDTO.getItemSkuDTOList().get(0).setStockNum(stockNum);
            itemDTO.setSubItemList(itemDTOList);
        }

        //TODO 如果是跨境商品，则需要添加跨境扩展信息
        if (itemDTO.getHigoMark() != null && itemDTO.getHigoMark().intValue() == 1) {
            //查询跨境平台，获取该商品的跨境扩展信息
            ItemHigoInfoDTO itemHigoInfoDTO =
                    higoManager.getItemHigoInfo(itemDTO.getId(), itemDTO.getSellerId(), appKey);
            itemDTO.setHigoExtraInfo(ModelUtil.genHigoExtraInfoDTO(itemHigoInfoDTO));
        }


        //运费
        long freight = freightManager.calculateItemDefaultFreight(itemDTO);


        //销量数据
        long salesVolume = itemSalesVolumeManager.getItemSalesVolume(itemDTO.getId(), itemDTO.getBizCode());

        itemDTO.setSalesVolume(salesVolume);

        itemDTO.setFreight(freight);


        //商品详情模板
        if (itemDTO.getDescTmplId() != null) {
            ItemDescTmplDTO itemDescTmplDTO = itemDescTmplManager.getItemDescTmpl(itemDTO.getDescTmplId(), sellerId, bizCode);

            if (itemDescTmplDTO != null) {
                itemDTO.setBeforDesc(itemDescTmplDTO.getBeforeDesc());
                itemDTO.setAfterDesc(itemDescTmplDTO.getAfterDesc());
            }
        }

        //更新发布状态(发布)
        Long result = itemManager.updateIssueStatus(itemId, ItemIssueStatusEnum.ISSUE.getCode());

        //更新搜索引擎
        //仅当商品上架才推送到搜索引擎
        if ( itemDTO.getItemStatus() == ItemStatus.ON_SALE.getStatus()) {
            itemSearchManager.setItemIndex(itemDTO);
        }

        MopBaseItemDTO mopBaseItemDTO = MopApiUtil.genMopBaseItem(itemDTO);
        if(needWritePage != null && needWritePage  ){
            staticPage.serialPage(SerialPageEnum.ITEM,itemDTO.getId(),
                    JsonUtil.toJson(mopBaseItemDTO));
        }
        return mopBaseItemDTO;




    }

    private void fillItemSkuImage(List<ItemImageDTO> itemImageDTOs,
                                  List<ItemSkuDTO> itemSkuDTOList) {
        if (itemImageDTOs == null || itemSkuDTOList == null) {
            return;
        }
        Map<Long, ItemImageDTO> skuImageMap = new HashMap<Long, ItemImageDTO>();
        for (ItemImageDTO itemImageDTO : itemImageDTOs) {
            if (itemImageDTO.getPropertyValueId() != null && itemImageDTO.getPropertyValueId().longValue() > 0) {
                //商品SKU图片
                skuImageMap.put(itemImageDTO.getPropertyValueId(), itemImageDTO);
            }
        }

        //FIXME 商品sku图片与商品sku是根据商品sku属性上的propertyValueId进行关联的
        for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
            if (itemSkuDTO.getSkuPropertyDTOList() != null) {
                for (SkuPropertyDTO skuPropertyDTO : itemSkuDTO.getSkuPropertyDTOList()) {
                    if (skuPropertyDTO.getPropertyValueId() != null
                            && skuImageMap.containsKey(skuPropertyDTO.getPropertyValueId())) {
                        itemSkuDTO.setImageUrl(skuImageMap.get(skuPropertyDTO.getPropertyValueId()).getImageUrl());
                    }
                }
            }
        }


        return;
    }


}
