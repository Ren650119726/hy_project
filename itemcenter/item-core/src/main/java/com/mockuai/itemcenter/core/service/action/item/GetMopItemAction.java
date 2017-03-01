package com.mockuai.itemcenter.core.service.action.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mockuai.distributioncenter.common.domain.dto.DistShopForMopDTO;
import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.*;
import com.mockuai.itemcenter.common.util.CommonMopApiUtil;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ModelUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.marketingcenter.common.constant.ItemType;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 查看商品Action
 *
 * @author chen.huang
 */

@Service
public class GetMopItemAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetMopItemAction.class);

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
    private MarketingManager marketingManager;

    @Resource
    private ItemLabelManager itemLabelManager;

    @Resource
    private ValueAddedServiceManager valueAddedServiceManager;

    @Resource
    private ItemSuitManager itemSuitManager;

    @Resource
    private SpecialItemExtraInfoManager specialItemExtraInfoManager;

    @Resource
    private ShopManager shopManager;

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
    private AppManager appManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemDTO itemDTO = null;
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String appKey = (String) context.get("appKey");
        String bizCode = (String) context.get("bizCode");
        // 验证ID
        if (request.getLong("id") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemID is missing");
        }
        if (request.getLong("supplierId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
        }
        Long itemId = request.getLong("id");// 商品品牌ID
        Long sellerId = request.getLong("supplierId");// 供应商ID
        Long userId = request.getLong("userId");

        Long distributorId = request.getLong("distributorId");

        // 如果是需要详细信息时候  需要找到普通属性和销售属性值 －－ updated by cwr
        if (request.getParam("needDetail") != null && ((Boolean) request.getParam("needDetail"))) {
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
                //q
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

//                    if (skuIdList.size() > 0) {
//
//                        List<SkuHigoInfoDTO> skuHigoInfoDTOList = higoManager.getSkuHigoInfoList(skuIdList, sellerId, appKey);
//
//                        Map<Long, SkuHigoInfoDTO> skuHigoInfoDTOMap = Maps.newHashMap();
//
//                        for (SkuHigoInfoDTO skuHigoInfoDTO : skuHigoInfoDTOList) {
//                            skuHigoInfoDTOMap.put(skuHigoInfoDTO.getSkuId(), skuHigoInfoDTO);
//                        }
//
//                        for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
//                            SkuHigoInfoDTO skuHigoInfoDTO = skuHigoInfoDTOMap.get(itemSkuDTO.getId());
//
//                            SkuHigoExtraInfoDTO skuHigoExtraInfoDTO = new SkuHigoExtraInfoDTO();
//
//                            if (skuHigoInfoDTO != null) {
//                                skuHigoExtraInfoDTO.setCustomsCode(skuHigoInfoDTO.getCustomsCode());
//                                itemSkuDTO.setSkuHigoExtraInfoDTO(skuHigoExtraInfoDTO);
//                            }
//                        }
//                    }
                }

                // 根据item_id查询所有的sku属性时候 有重复  比如：sku1 和 sku2 都有尺码L这个属性 结果会有重复 需要去除重复
                //去重操作 本段代码修改移到item-mop层修改 不然会导致商家中心sku部分出问题

//				List<SkuPropertyDTO> returnPropertyList = new ArrayList<SkuPropertyDTO>();
//				Set<Long> valueIdList = new HashSet<Long>();

//				if(skuPropertyList !=null){
//					for(SkuPropertyDTO item : skuPropertyList){
//						if(!valueIdList.contains(item.getVid())){
//							valueIdList.add(item.getVid());
//							returnPropertyList.add(item);
//						}
//					}
//				}
                itemDTO.setSkuPropertyList(skuPropertyList);

                Multimap<Long,SkuPropertyDTO> propertyMap= HashMultimap.create();

                for(SkuPropertyDTO skuPropertyDTO :skuPropertyList){
                    propertyMap.put(skuPropertyDTO.getSkuId(),skuPropertyDTO);
                }

                for(ItemSkuDTO itemSkuDTO : itemSkuDTOList){

                    Collection<SkuPropertyDTO> collection = propertyMap.get(itemSkuDTO.getId());

                    if(!CollectionUtils.isEmpty(collection)) {
                        itemSkuDTO.setSkuPropertyDTOList(Lists.newArrayList(propertyMap.get(itemSkuDTO.getId())));
                    }
                }

                // 获取副图列表
                ItemImageQTO itemImageQTO = new ItemImageQTO();
                itemImageQTO.setItemId(itemId);
                itemImageQTO.setSellerId(sellerId);
                List<ItemImageDTO> itemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);

                //过滤商品图片，将商品副图和SKU图片分开，这里只提取商品副图，过滤掉商品SKU图片
//				List<ItemImageDTO> itemExtraImageList = getItemExtraImageList(itemImageDTOList);

                //往商品SKU中填充商品sku图片
                fillItemSkuImage(itemImageDTOList, itemSkuDTOList);

                itemDTO.setItemImageDTOList(itemImageDTOList);
                itemDTO.setItemSkuDTOList(itemSkuDTOList);
                //TODO 商品价格填充逻辑重构
                if (itemSkuDTOList != null && itemSkuDTOList.isEmpty() == false) {
                    ItemSkuDTO itemSkuDTO = itemSkuDTOList.get(0);
                    itemDTO.setMarketPrice(itemSkuDTO.getMarketPrice());
                    itemDTO.setPromotionPrice(itemSkuDTO.getPromotionPrice());
                    itemDTO.setWirelessPrice(itemSkuDTO.getWirelessPrice());
                }

            } catch (ItemException e) {
                response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
                return response;
            }
        } else { //不需要查找普通属性和销售属性值的时候
            try {
                itemDTO = itemManager.getItem(itemId, sellerId, bizCode);
                ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
                itemSkuQTO.setItemId(itemId);
                itemSkuQTO.setSellerId(sellerId);
                itemSkuQTO.setBizCode(bizCode);
                // 获取ItemSku列表
                List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);

                long stockNum = 0L;

                for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
                    stockNum += itemSkuDTO.getStockNum();
                }

                itemDTO.setStockNum(stockNum);

                // 获取副图列表
                ItemImageQTO itemImageQTO = new ItemImageQTO();
                itemImageQTO.setItemId(itemId);
                itemImageQTO.setSellerId(sellerId);
                List<ItemImageDTO> itemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);

                //过滤商品图片，将商品副图和SKU图片分开，这里只提取商品副图，过滤掉商品SKU图片
//				List<ItemImageDTO> itemExtraImageList = getItemExtraImageList(itemImageDTOList);

                //往商品SKU中填充商品sku图片
                fillItemSkuImage(itemImageDTOList, itemSkuDTOList);

                itemDTO.setItemImageDTOList(itemImageDTOList);
                itemDTO.setItemSkuDTOList(itemSkuDTOList);
            } catch (ItemException e) {
                response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
                return response;
            }
        }

        //获取商品品牌信息
        Long itemBrandId = itemDTO.getItemBrandId();
        if (itemBrandId != null && itemBrandId != 0) {

            SellerBrandDTO sellerBrand = sellerBrandManager.getSellerBrand(itemBrandId);
//            // fixbug.
//            if (sellerBrand == null) {
//                ItemException e = ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "brand not exists for the item.");
//                response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
//                log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
//                return response;
//            }

            if (sellerBrand != null) {
                itemDTO.setItemBrandDTO(sellerBrand);
                //TODO 这里暂时返回兼容itemProperty中的品牌属性，后续需要考虑在商家中心来插入，平台不感知该属性 add by zengzhangqiang on 2015-06-11
                if (itemDTO.getItemPropertyList() == null) {
                    itemDTO.setItemPropertyList(new ArrayList<ItemPropertyDTO>());
                }
                ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
                itemPropertyDTO.setCode("IC_APP_P_ITEM_000001");
                itemPropertyDTO.setName("品牌");
                itemPropertyDTO.setValue(sellerBrand.getBrandName());
                itemPropertyDTO.setValueType(1);
                itemDTO.getItemPropertyList().add(itemPropertyDTO);
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

                    Date date = new Date();

                    //只有当前时间在限购时间段内，才返回限购数量
                    //if(date.after(limitEntity.getBeginTime())&&date.before(limitEntity.getEndTime())){
                    //   limitEntity.setLimitCount(itemBuyLimitDTO.getBuyCount());
                    //}


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
        } catch (Exception e) {
            log.error("查询服务标签列表失败 item_id {} biz_code {}", itemId, bizCode);
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
        } else if (itemDTO.getItemType() != null && itemDTO.getItemType() != DBConst.NORMAL_ITEM.getCode()) {
//
//            itemDTO.setItemExtraInfo(specialItemExtraInfoManager.getSpecialItemExtraInfo(itemDTO.getItemSkuDTOList().get(0).getId(), sellerId, userId, itemDTO.getItemType(), appKey));
        } else {
            //填充商品优惠信息
//            List<MopDiscountInfo> mopDiscountInfoList = marketingManager.queryItemDiscountInfo(itemDTO,userId, appKey);
//            itemDTO.setDiscountInfoList(mopDiscountInfoList);
//            log.error("xxxx item_uid {}_{}",sellerId,itemId);
        }


        //填充店铺信息
        ShopDTO shopDTO = shopManager.getShop(sellerId, appKey);

        if (shopDTO != null) {

            ShopInfoDTO shopInfoDTO = new ShopInfoDTO();

            shopInfoDTO.setSellerId(shopDTO.getSellerId());
            shopInfoDTO.setShopId(shopDTO.getId());
            shopInfoDTO.setShopName(shopDTO.getShopName());
            shopInfoDTO.setCollectionNum(shopDTO.getCollectionNum());
            shopInfoDTO.setIconUrl(shopDTO.getShopIconUrl());

            ItemQTO itemQTO = new ItemQTO();
            itemQTO.setSellerId(sellerId);
            itemQTO.setBizCode(bizCode);
            itemQTO.setItemType(ItemType.COMMON.getValue());

            Long count = itemManager.countItem(itemQTO);

            shopInfoDTO.setItemNum(count.intValue());

            itemDTO.setShopInfoDTO(shopInfoDTO);
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


        if (distributorId != null) {

            //查询分销商信息
            DistShopForMopDTO distShopForMopDTO = distributorManager.getShopForMopBySellerId(distributorId, appKey);
            itemDTO.setDistributorInfoDTO(distShopForMopDTO);
//
//            String domain = appManager.getAppInfoByType(bizCode, AppTypeEnum.APP_WAP).getDomainName();
//
//            //例子  http://m.haiyun.com/detail.html?item_uid=1841254_29065&distributor_id=19
//
//            String url = "http://" + domain + "/detail.html?item_uid=" + sellerId + "_" + itemId + "&distributor_id=" + distributorId;
//
//            String qr = imageManager.genItemImage(itemId, sellerId, distributorId, url, appKey);
//
//            //查询二维码,如果没有,则生成
//            itemDTO.setQrCode(qr);
        }


        //TODO 判断商品收藏状态
        response = ResponseUtil.getSuccessResponse(CommonMopApiUtil.genMopItem(itemDTO));


        return response;


    }


    private List<ItemImageDTO> getItemExtraImageList(List<ItemImageDTO> itemImageDTOs) {
        if (itemImageDTOs == null || itemImageDTOs.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<ItemImageDTO> itemExtraImageList = new ArrayList<ItemImageDTO>();
        Map<Long, ItemImageDTO> skuImageMap = new HashMap<Long, ItemImageDTO>();
        for (ItemImageDTO itemImageDTO : itemImageDTOs) {
            if (itemImageDTO.getPropertyValueId() == null || itemImageDTO.getPropertyValueId().longValue() <= 0) {
                //商品副图
                itemExtraImageList.add(itemImageDTO);
            }
        }

        return itemExtraImageList;
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

    @Override
    public String getName() {
        return ActionEnum.GET_MOP_ITEM.getActionName();
    }
}
