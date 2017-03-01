package com.mockuai.itemcenter.common.util;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.mop.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zengzhangqiang on 4/26/15.
 */
public class CommonMopApiUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static String genItemUid(long itemId, long sellerId) {
        return "" + sellerId + "_" + itemId;
    }

    public static String genSkuUid(long itemSkuId, long sellerId) {
        return "" + sellerId + "_" + itemSkuId;
    }

    public static String genItemImageUid(long itemImageId, long sellerId) {
        return "" + sellerId + "_" + itemImageId;
    }

    public static String genOrderUid(long orderId, long sellerId, long buyerId) {
        return "" + sellerId + "_" + buyerId + "_" + orderId;
    }

    public static String genCommentUid(long commentId, long sellerId, long userId) {
        return "" + sellerId + "_" + userId + "_" + commentId;
    }

    public static String genSkuPropertyUid(Long sellerId, Long skuPropertyId) {
        if (skuPropertyId == null) {
            return null;
        }
        return "" + sellerId + "_" + skuPropertyId;
    }

    public static List<MopItemDTO> genMopItemListForSearchDTO(List<ItemSearchDTO> itemSearchDTOs) {
        List<MopItemDTO> mopItemDTOs = new ArrayList<MopItemDTO>();
        for (ItemSearchDTO itemSearchDTO : itemSearchDTOs) {
            mopItemDTOs.add(genMopItem(itemSearchDTO));
        }
        return mopItemDTOs;
    }

    public static List<MopItemDTO> genMopItemListForGroupDTO(List<GroupItemDTO> groupItemDTOs) {
        List<MopItemDTO> mopItemDTOs = new ArrayList<MopItemDTO>();
        for (GroupItemDTO groupItemDTO : groupItemDTOs) {
            mopItemDTOs.add(genMopItem(groupItemDTO));
        }
        return mopItemDTOs;
    }

    public static List<MopItemDTO> genMopItemList(List<ItemDTO> itemDTOs) {
        if (itemDTOs == null) {
            return null;
        }
        List<MopItemDTO> mopItemDTOs = new ArrayList<MopItemDTO>();
        for (ItemDTO itemDTO : itemDTOs) {
            mopItemDTOs.add(genMopItem(itemDTO));
        }
        return mopItemDTOs;
    }

    public static MopItemDTO genMopItem(ItemSearchDTO itemSearchDTO) {
        MopItemDTO mopItemDTO = new MopItemDTO();
        mopItemDTO.setItemBrand(genMopItemBrand(itemSearchDTO));
        //mopItemDTO.setItemGroupUid(genGroupItemUid(itemSearchDTO.getSellerId(), itemSearchDTO.getGroupId()));

        //兼容以前不存在shopId的情况
        mopItemDTO.setShopId(itemSearchDTO.getShopId() == null ? 0 : itemSearchDTO.getShopId());
        //兼容以前不存在groupId的情况
        mopItemDTO.setItemGroupUid(itemSearchDTO.getGroupId() == null ? "" : "" + itemSearchDTO.getSellerId() + "_" + itemSearchDTO.getGroupId());
        mopItemDTO.setItemName(itemSearchDTO.getItemName());
        mopItemDTO.setItemUid(itemSearchDTO.getItemUid());
        mopItemDTO.setIconUrl(itemSearchDTO.getIconUrl());
        mopItemDTO.setCategoryId(itemSearchDTO.getCategoryId());
        mopItemDTO.setSellerId(itemSearchDTO.getSellerId());
        mopItemDTO.setMarketPrice(itemSearchDTO.getMarketPrice());
        mopItemDTO.setPromotionPrice(itemSearchDTO.getPromotionPrice());
        mopItemDTO.setWirelessPrice(itemSearchDTO.getWirelessPrice());
        mopItemDTO.setCategoryName(itemSearchDTO.getCategoryName());
        mopItemDTO.setCornerIconUrl(itemSearchDTO.getCornerIconUrl());
        mopItemDTO.setSupplyBase(itemSearchDTO.getSupplyBase());
        mopItemDTO.setSalesVolume(itemSearchDTO.getSalesVolume());
        mopItemDTO.setStockStatus(itemSearchDTO.getStockStatus());
        return mopItemDTO;
    }

    public static MopItemDTO genMopItem(GroupItemDTO groupItemDTO) {
        MopItemDTO mopItemDTO = new MopItemDTO();

        MopItemBrandDTO mopItemBrandDTO = new MopItemBrandDTO();
        mopItemBrandDTO.setId(groupItemDTO.getBrandId());
        mopItemDTO.setItemBrand(mopItemBrandDTO);

        mopItemDTO.setItemName(groupItemDTO.getItemName());
        mopItemDTO.setItemUid(groupItemDTO.getItemUid());
        mopItemDTO.setIconUrl(groupItemDTO.getIconUrl());
        mopItemDTO.setCategoryId(groupItemDTO.getCategoryId());
        mopItemDTO.setSellerId(groupItemDTO.getSellerId());
        mopItemDTO.setMarketPrice(groupItemDTO.getMarketPrice());
        mopItemDTO.setPromotionPrice(groupItemDTO.getPromotionPrice());
        mopItemDTO.setWirelessPrice(groupItemDTO.getWirelessPrice());
        mopItemDTO.setCategoryName(groupItemDTO.getCategoryName());
        return mopItemDTO;
    }

    public static MopItemBrandDTO genMopItemBrand(SellerBrandDTO sellerBrandDTO) {
        if (sellerBrandDTO == null) {
            return null;
        }
        MopItemBrandDTO mopItemBrandDTO = new MopItemBrandDTO();
        mopItemBrandDTO.setId(sellerBrandDTO.getId());
        mopItemBrandDTO.setLogoUrl(sellerBrandDTO.getLogo());
        mopItemBrandDTO.setName(sellerBrandDTO.getBrandName());
        mopItemBrandDTO.setEnName(sellerBrandDTO.getBrandEnName());
        return mopItemBrandDTO;
    }

    public static MopItemBrandDTO genMopItemBrand(ItemSearchDTO itemSearchDTO) {
        MopItemBrandDTO mopItemBrandDTO = new MopItemBrandDTO();

        if (null == itemSearchDTO.getBrandId()) {
            return null;
        } else {
            mopItemBrandDTO.setId(itemSearchDTO.getBrandId());
        }
        return mopItemBrandDTO;
    }

    public static MopItemDTO genMopItem(ItemDTO itemDTO) {
        MopItemDTO mopItemDTO = new MopItemDTO();
        mopItemDTO.setItemUid(genItemUid(itemDTO.getId(), itemDTO.getSellerId()));
        mopItemDTO.setItemName(itemDTO.getItemName());
        mopItemDTO.setSellerId(itemDTO.getSellerId());
        mopItemDTO.setCategoryId(itemDTO.getCategoryId());
        mopItemDTO.setItemType(itemDTO.getItemType());
        mopItemDTO.setIconUrl(itemDTO.getIconUrl());
        //TODO descUrl值设置

        String itemDesc = itemDTO.getItemDesc();

        if (itemDTO.getBeforDesc() != null) {
            itemDesc = itemDTO.getBeforDesc() + itemDesc;
        }

        if (itemDTO.getAfterDesc() != null) {
            itemDesc = itemDesc + itemDTO.getAfterDesc();
        }

        mopItemDTO.setItemDesc(itemDesc);
        mopItemDTO.setMarketPrice(itemDTO.getMarketPrice());
        mopItemDTO.setPromotionPrice(itemDTO.getPromotionPrice());
        mopItemDTO.setWirelessPrice(itemDTO.getWirelessPrice());
        mopItemDTO.setSalesVolume(itemDTO.getSalesVolume());
        mopItemDTO.setFreight(itemDTO.getFreight());
        mopItemDTO.setVirtualMark(itemDTO.getVirtualMark());
        if (itemDTO.getSaleBegin() != null) {
            mopItemDTO.setSaleBegin(dateFormat.format(itemDTO.getSaleBegin()));
            //TODO 这里待重构成取数据库时间
            // 当前之间转化成秒;
            long nowTime = System.currentTimeMillis() / 1000;
            // 起售时间转化成秒;
            long saleBeginTime = itemDTO.getSaleBegin().getTime() / 1000;
            if (saleBeginTime < nowTime) {
                mopItemDTO.setSalesRemainTime(0L);
            } else {
                mopItemDTO.setSalesRemainTime(saleBeginTime - nowTime);
            }
        }

        if (itemDTO.getSaleEnd() != null) {
            mopItemDTO.setSaleEnd(dateFormat.format(itemDTO.getSaleEnd()));
        }
        mopItemDTO.setDeliveryType(itemDTO.getDeliveryType());
        mopItemDTO.setItemBrand(genMopItemBrand(itemDTO.getItemBrandDTO()));
        mopItemDTO.setItemSkuList(genMopItemSkuList(itemDTO.getItemSkuDTOList()));
        mopItemDTO.setItemImageList(genMopItemImageList(itemDTO.getItemImageDTOList()));

        // 限购;
        if (itemDTO.getBuyLimit() == null || itemDTO.getBuyLimit().size() == 0) {
            mopItemDTO.setLimitBuyCount(0);
        }
        long currentMillis = System.currentTimeMillis();

        if (itemDTO.getBuyLimit() != null) {
            for (LimitEntity limitEntity : itemDTO.getBuyLimit()) {

                long beginTime = limitEntity.getBeginTime().getTime();
                long endTime = limitEntity.getEndTime().getTime();
                if (currentMillis >= beginTime && currentMillis <= endTime) {
                    mopItemDTO.setLimitBuyCount(limitEntity.getLimitCount());
                }
            }
        }

        // 根据item_id查询所有的sku属性时候 有重复  比如：sku1 和 sku2 都有红色这个属性 结果红色会出现2次 导致app端会出现重复显示
        List<SkuPropertyDTO> skuPropertyList = itemDTO.getSkuPropertyList();
        List<SkuPropertyDTO> returnPropertyList = new ArrayList<SkuPropertyDTO>();
        Set<Long> valueIdList = new HashSet<Long>();
        if (skuPropertyList != null) {
            for (SkuPropertyDTO item : skuPropertyList) {
                if (!valueIdList.contains(item.getPropertyValueId())) {
                    valueIdList.add(item.getPropertyValueId());
                    returnPropertyList.add(item);
                }
            }
        }

        mopItemDTO.setSkuPropertyList(genMopSkuPropertyList(returnPropertyList));
        mopItemDTO.setItemPropertyList(genMopItemPropertyList(itemDTO.getItemPropertyList()));

        //FIXME 兼容saleMinNum和saleMaxNum不存在的情况
        if (itemDTO.getSaleMinNum() != null) {
            mopItemDTO.setSaleMinNum(itemDTO.getSaleMinNum().longValue());
        } else {
            mopItemDTO.setSaleMinNum(0L);
        }

        if (itemDTO.getSaleMaxNum() != null) {
            mopItemDTO.setSaleMaxNum(itemDTO.getSaleMaxNum().longValue());
        } else {
            mopItemDTO.setSaleMaxNum(0L);
        }
        mopItemDTO.setStatus(itemDTO.getItemStatus());

        mopItemDTO.setServiceTypeList(genMopServiceTypeList(itemDTO.getValueAddedServiceTypeDTOList()));

        mopItemDTO.setItemLabelList(genMopItemLabelList(itemDTO.getItemLabelDTOList()));


        mopItemDTO.setDiscountInfoList(itemDTO.getDiscountInfoList());

        mopItemDTO.setItemExtraInfo(itemDTO.getItemExtraInfo());

        //设置商品跨境扩展信息
        mopItemDTO.setHigoMark(itemDTO.getHigoMark());
        if (itemDTO.getHigoMark() != null && itemDTO.getHigoMark().intValue() == 1) {
            mopItemDTO.setHigoExtraInfo(genMopHigoExtraInfo(itemDTO.getHigoExtraInfo()));
        }

        if (null != itemDTO.getShopId()) {
            mopItemDTO.setShopId(itemDTO.getShopId());
        }

        mopItemDTO.setShopInfo(genMopShopInfo(itemDTO.getShopInfoDTO()));


        mopItemDTO.setDistributorInfo(itemDTO.getDistributorInfoDTO());

        mopItemDTO.setQrCode(itemDTO.getQrCode());

        //TODO bizProperty值设置
        return mopItemDTO;
    }

    private static MopShopInfoDTO genMopShopInfo(ShopInfoDTO shopInfoDTO) {

        if (shopInfoDTO == null) {
            return null;
        }

        MopShopInfoDTO mopShopInfoDTO = new MopShopInfoDTO();
        mopShopInfoDTO.setIconUrl(shopInfoDTO.getIconUrl());
        mopShopInfoDTO.setCollectionNum(shopInfoDTO.getCollectionNum() == null ? 0 : shopInfoDTO.getCollectionNum());
        mopShopInfoDTO.setItemNum(shopInfoDTO.getItemNum() == null ? 0 : shopInfoDTO.getItemNum());
        mopShopInfoDTO.setSellerId(shopInfoDTO.getSellerId());
        mopShopInfoDTO.setShopId(shopInfoDTO.getShopId());
        mopShopInfoDTO.setShopName(shopInfoDTO.getShopName());

        return mopShopInfoDTO;
    }

    private static List<MopItemLabelDTO> genMopItemLabelList(List<ItemLabelDTO> itemLabelDTOList) {

        if (itemLabelDTOList == null) {
            return null;
        }

        List<MopItemLabelDTO> itemLabelList = Lists.newArrayListWithCapacity(itemLabelDTOList.size());

        for (ItemLabelDTO itemLabelDTO : itemLabelDTOList) {
            itemLabelList.add(genMopItemLabel(itemLabelDTO));
        }

        return itemLabelList;
    }

    private static MopItemLabelDTO genMopItemLabel(ItemLabelDTO itemLabelDTO) {

        MopItemLabelDTO itemLabel = new MopItemLabelDTO();
        itemLabel.setLabelUid("" + itemLabelDTO.getSellerId() + "_" + itemLabelDTO.getId());
        itemLabel.setLabelName(itemLabelDTO.getLabelName());
        itemLabel.setLabelDesc(itemLabelDTO.getLabelDesc());
        itemLabel.setIconUrl(itemLabelDTO.getIconUrl());

        return itemLabel;

    }

    private static List<MopValueAddedServiceTypeDTO> genMopServiceTypeList(List<ValueAddedServiceTypeDTO> valueAddedServiceTypeDTOList) {
        if (valueAddedServiceTypeDTOList == null) {
            return null;
        }

        List<MopValueAddedServiceTypeDTO> serviceTypeList = Lists.newArrayListWithCapacity(valueAddedServiceTypeDTOList.size());

        for (ValueAddedServiceTypeDTO serviceTypeDTO : valueAddedServiceTypeDTOList) {
            serviceTypeList.add(genMopServiceType(serviceTypeDTO));
        }

        return serviceTypeList;
    }

    private static MopValueAddedServiceTypeDTO genMopServiceType(ValueAddedServiceTypeDTO serviceTypeDTO) {

        if (serviceTypeDTO == null) {
            return null;
        }

        MopValueAddedServiceTypeDTO serviceType = new MopValueAddedServiceTypeDTO();
        serviceType.setTypeName(serviceTypeDTO.getTypeName());
        serviceType.setServiceTypeUid("" + serviceTypeDTO.getSellerId() + "_" + serviceTypeDTO.getId());
        serviceType.setServiceList(genMopServiceList(serviceTypeDTO.getValueAddedServiceDTOList()));

        return serviceType;
    }

    private static List<MopValueAddedServiceDTO> genMopServiceList(List<ValueAddedServiceDTO> valueAddedServiceDTOList) {
        if (valueAddedServiceDTOList == null) {
            return null;
        }

        List<MopValueAddedServiceDTO> serviceList = Lists.newArrayListWithCapacity(valueAddedServiceDTOList.size());

        for (ValueAddedServiceDTO serviceDTO : valueAddedServiceDTOList) {
            serviceList.add(genMopService(serviceDTO));
        }

        return serviceList;
    }

    private static MopValueAddedServiceDTO genMopService(ValueAddedServiceDTO serviceDTO) {
        MopValueAddedServiceDTO service = new MopValueAddedServiceDTO();

        service.setIconUrl(serviceDTO.getIconUrl());
        service.setServiceDesc(serviceDTO.getServiceDesc());
        service.setServiceName(serviceDTO.getServiceName());
        service.setServicePrice(serviceDTO.getServicePrice());
        service.setServiceUid("" + serviceDTO.getSellerId() + "_" + serviceDTO.getId());

        return service;
    }


    public static List<MopItemSkuDTO> genMopItemSkuList(List<ItemSkuDTO> itemSkuList) {
        if (itemSkuList == null) {
            return null;
        }

        List<MopItemSkuDTO> mopItemSkuDTOs = new ArrayList<MopItemSkuDTO>();
        for (ItemSkuDTO itemSkuDTO : itemSkuList) {
            MopItemSkuDTO mopItemSkuDTO = genMopItemSku(itemSkuDTO);
            mopItemSkuDTOs.add(mopItemSkuDTO);
        }
        return mopItemSkuDTOs;
    }

    public static MopItemSkuDTO genMopItemSku(ItemSkuDTO itemSku) {
        MopItemSkuDTO mopItemSkuDTO = new MopItemSkuDTO();
        mopItemSkuDTO.setSkuUid(genSkuUid(itemSku.getId(), itemSku.getSellerId()));
        mopItemSkuDTO.setSkuCode(itemSku.getSkuCode());
        mopItemSkuDTO.setItemUid(genItemUid(itemSku.getItemId(), itemSku.getSellerId()));
        mopItemSkuDTO.setBarCode(itemSku.getBarCode());
        mopItemSkuDTO.setCostPrice(itemSku.getCostPrice());
        //mopItemSkuDTO.setMaterialCode(itemSku.getMaterialCode());
        mopItemSkuDTO.setImageUrl(itemSku.getImageUrl());
        mopItemSkuDTO.setMarketPrice(itemSku.getMarketPrice());
        mopItemSkuDTO.setPromotionPrice(itemSku.getPromotionPrice());
        mopItemSkuDTO.setWirelessPrice(itemSku.getWirelessPrice());
        mopItemSkuDTO.setStockNum(itemSku.getStockNum()-itemSku.getFrozenStockNum());
        mopItemSkuDTO.setSoldNum(itemSku.getSoldNum());
        mopItemSkuDTO.setSkuPropertyList(genMopSkuPropertyList(itemSku.getSkuPropertyDTOList()));
        return mopItemSkuDTO;
    }

    public static List<MopSkuPropertyDTO> genMopSkuPropertyList(List<SkuPropertyDTO> skuPropertyDTOList) {
        if (skuPropertyDTOList == null) {
            return null;
        }

        List<MopSkuPropertyDTO> mopSkuPropertyDTOs = new ArrayList<MopSkuPropertyDTO>();
        for (SkuPropertyDTO skuPropertyDTO : skuPropertyDTOList) {
            MopSkuPropertyDTO mopSkuPropertyDTO = genMopSkuProperty(skuPropertyDTO);
            mopSkuPropertyDTOs.add(mopSkuPropertyDTO);
        }

        return mopSkuPropertyDTOs;
    }

    public static MopSkuPropertyDTO genMopSkuProperty(SkuPropertyDTO skuProperty) {
        MopSkuPropertyDTO mopSkuPropertyDTO = new MopSkuPropertyDTO();
        //TODO code值的设置
        mopSkuPropertyDTO.setName(skuProperty.getName());
        mopSkuPropertyDTO.setValue(skuProperty.getValue());
        mopSkuPropertyDTO.setValueType(skuProperty.getValueType());
        mopSkuPropertyDTO.setSort(skuProperty.getSort());
        mopSkuPropertyDTO.setSkuPropertyUid(genSkuPropertyUid(skuProperty.getSellerId(), skuProperty.getId()));

        return mopSkuPropertyDTO;
    }

    public static List<MopItemPropertyDTO> genMopItemPropertyList(List<ItemPropertyDTO> itemPropertyList) {
        if (itemPropertyList == null) {
            return null;
        }

        List<MopItemPropertyDTO> mopItemPropertyDTOs = new ArrayList<MopItemPropertyDTO>();
        for (ItemPropertyDTO itemPropertyDTO : itemPropertyList) {
            // 根据bizMark字段来判断是否需要需要显示给用户看   updated by cwr
            if (isItemPropertyShow2User(itemPropertyDTO.getBizMark())) {
                MopItemPropertyDTO mopItemPropertyDTO = genMopItemProperty(itemPropertyDTO);
                mopItemPropertyDTOs.add(mopItemPropertyDTO);
            }
        }

        return mopItemPropertyDTOs;
    }

    public static MopItemPropertyDTO genMopItemProperty(ItemPropertyDTO itemProperty) {
        MopItemPropertyDTO mopItemPropertyDTO = new MopItemPropertyDTO();
        //TODO code值的设置
        System.out.println("itemPropetyCode: " + itemProperty.getCode());
        mopItemPropertyDTO.setCode(itemProperty.getCode());
        mopItemPropertyDTO.setName(itemProperty.getName());
        mopItemPropertyDTO.setValue(itemProperty.getValue());
        mopItemPropertyDTO.setValueType(itemProperty.getValueType());
        mopItemPropertyDTO.setSort(itemProperty.getSort());
        return mopItemPropertyDTO;
    }

    public static List<MopItemImageDTO> genMopItemImageList(List<ItemImageDTO> itemImageList) {
        if (itemImageList == null) {
            return null;
        }

        List<MopItemImageDTO> mopItemImageList = new ArrayList<MopItemImageDTO>();
        for (ItemImageDTO itemImageDTO : itemImageList) {
            MopItemImageDTO mopItemImageDTO = genMopItemImage(itemImageDTO);
            mopItemImageList.add(mopItemImageDTO);
        }

        return mopItemImageList;
    }

    public static MopItemImageDTO genMopItemImage(ItemImageDTO itemImage) {
        MopItemImageDTO mopItemImageDTO = new MopItemImageDTO();
        mopItemImageDTO.setImageName(itemImage.getImageName());
        mopItemImageDTO.setImageType(itemImage.getImageType());
        mopItemImageDTO.setImageUrl(itemImage.getImageUrl());
        mopItemImageDTO.setItemUid(genItemUid(itemImage.getItemId(), itemImage.getSellerId()));
        mopItemImageDTO.setItemImageUid(genItemImageUid(itemImage.getId(), itemImage.getSellerId()));
        if (itemImage.getPropertyValueId() != null || itemImage.getPropertyValueId().longValue() > 0) {
            mopItemImageDTO.setSkuPropertyUid(genSkuPropertyUid(itemImage.getSellerId(), itemImage.getPropertyValueId()));
        }

        return mopItemImageDTO;
    }

    /**
     * 根据标记为来判断该字段是否是显示给用户
     *
     * @param bizMark
     * @return
     */
    public static boolean isItemPropertyShow2User(Long bizMark) {
        if (bizMark == null) {// 如果是该字段没有配置 保险起见不给买家查看
            return false;
        }
        //1 表示只显示给卖家  2 表示该字段可以显示给买家
        if (bizMark.longValue() == 2L) {
            return true;
        }
        return false;
    }

    public static MopHigoExtraInfoDTO genMopHigoExtraInfo(HigoExtraInfoDTO higoExtraInfoDTO) {
        if (higoExtraInfoDTO == null) {
            return null;
        }

        MopHigoExtraInfoDTO mopHigoExtraInfoDTO = new MopHigoExtraInfoDTO();
        mopHigoExtraInfoDTO.setOriginalTaxFee(higoExtraInfoDTO.getOriginalTaxFee());
        mopHigoExtraInfoDTO.setFinalTaxFee(higoExtraInfoDTO.getFinalTaxFee());
        mopHigoExtraInfoDTO.setSupplyBase(higoExtraInfoDTO.getSupplyBase());
        mopHigoExtraInfoDTO.setDeliveryType(higoExtraInfoDTO.getDeliveryType());
        mopHigoExtraInfoDTO.setTaxRate(higoExtraInfoDTO.getTaxRate());

        return mopHigoExtraInfoDTO;
    }

    public static String genGroupItemUid(long itemGroupId, long sellerId) {
        return "" + sellerId + "_" + itemGroupId;
    }


}
