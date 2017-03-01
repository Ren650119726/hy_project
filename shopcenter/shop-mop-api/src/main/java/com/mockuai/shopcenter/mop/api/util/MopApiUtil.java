package com.mockuai.shopcenter.mop.api.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.shopcenter.domain.dto.*;
import com.mockuai.shopcenter.mop.api.domain.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ziqi.
 */
public class MopApiUtil {

    public static MopShopDTO genMopShopDTO(ShopDTO shopDTO) {

        MopShopDTO mopShopDTO = new MopShopDTO();
        if (shopDTO != null) {
            mopShopDTO.setShopId(shopDTO.getId());
            mopShopDTO.setSellerId(shopDTO.getSellerId());
            mopShopDTO.setShopUid("" + shopDTO.getSellerId() + "_" + shopDTO.getId());
            mopShopDTO.setShopName(shopDTO.getShopName());
            mopShopDTO.setCreateTime(shopDTO.getCreateTime());
            mopShopDTO.setCustomerServicePhone(shopDTO.getCustomerServicePhone());
            mopShopDTO.setSellerName(shopDTO.getSellerName());
            mopShopDTO.setShopAddress(shopDTO.getShopAddress());
            mopShopDTO.setShopDesc(shopDTO.getShopDesc());
            mopShopDTO.setIconUrl(shopDTO.getShopIconUrl());
            mopShopDTO.setBgImageUrl(shopDTO.getShopBannerImageUrl());

            if (shopDTO.getIsCollected() != null && shopDTO.getIsCollected() == true) {
                mopShopDTO.setIsCollected(1);
            } else {
                mopShopDTO.setIsCollected(0);
            }

            mopShopDTO.setShopBannerList(genMopBannerDTOList(shopDTO.getBannerDTOList()));
            mopShopDTO.setCouponList(genMopShopCouponDTOList(shopDTO.getShopCouponDTOList()));
            mopShopDTO.setItemList(genMopItemDTOList(shopDTO.getShopItemDTOList()));

        }
        return mopShopDTO;
    }

    private static List<MopShopItemDTO> genMopItemDTOList(List<ShopItemDTO> shopItemDTOList) {

        if(shopItemDTOList==null){
            return Collections.EMPTY_LIST;
        }

        List<MopShopItemDTO> mopShopItemDTOList = Lists.newArrayListWithCapacity(shopItemDTOList.size());

        for(ShopItemDTO shopItemDTO : shopItemDTOList){
            MopShopItemDTO mopShopItemDTO = genMopShopItemDTO(shopItemDTO);
            mopShopItemDTOList.add(mopShopItemDTO);

        }

        return mopShopItemDTOList;
    }

    private static MopShopItemDTO genMopShopItemDTO(ShopItemDTO shopItemDTO) {

        if(shopItemDTO==null){
            return null;
        }

        MopShopItemDTO mopShopItemDTO = new MopShopItemDTO();

        if(Strings.isNullOrEmpty(shopItemDTO.getItemUid())) {
            mopShopItemDTO.setItemUid(""+shopItemDTO.getSellerId()+"_"+shopItemDTO.getItemId());
        }else {
            mopShopItemDTO.setItemUid(shopItemDTO.getItemUid());
        }

        mopShopItemDTO.setCategoryId(shopItemDTO.getCategoryId());
        mopShopItemDTO.setItemName(shopItemDTO.getItemName());
        mopShopItemDTO.setIconUrl(shopItemDTO.getIconUrl());
        mopShopItemDTO.setItemType(shopItemDTO.getItemType()==null?1:shopItemDTO.getItemType());
        mopShopItemDTO.setMarketPrice(shopItemDTO.getMarketPrice());
        mopShopItemDTO.setPromotionPrice(shopItemDTO.getPromotionPrice());
        mopShopItemDTO.setWirelessPrice(shopItemDTO.getWirelessPrice());
        mopShopItemDTO.setGroupId(shopItemDTO.getGroupId()==null?0:shopItemDTO.getGroupId());

        return mopShopItemDTO;

    }

    private static List<MopShopCouponDTO> genMopShopCouponDTOList(List<ShopCouponDTO> shopCouponDTOList) {

        if(shopCouponDTOList==null){
            return Collections.EMPTY_LIST;
        }

        List<MopShopCouponDTO> mopShopCouponDTOList = Lists.newArrayListWithCapacity(shopCouponDTOList.size());

        for(ShopCouponDTO shopCouponDTO : shopCouponDTOList){
            MopShopCouponDTO mopShopCouponDTO = genMopShopCouponDTO(shopCouponDTO);
            mopShopCouponDTOList.add(mopShopCouponDTO);
        }

        return mopShopCouponDTOList;

    }

    private static MopShopCouponDTO genMopShopCouponDTO(ShopCouponDTO shopCouponDTO) {

        if(shopCouponDTO==null){
            return null;
        }

        MopShopCouponDTO mopShopCouponDTO = new MopShopCouponDTO();

        mopShopCouponDTO.setActivityCouponUid("" + shopCouponDTO.getSellerId() + "_" + shopCouponDTO.getCouponId());
        mopShopCouponDTO.setConsume(shopCouponDTO.getConsume());
        mopShopCouponDTO.setContent(shopCouponDTO.getContent());
        mopShopCouponDTO.setName(shopCouponDTO.getName());
        mopShopCouponDTO.setDiscountAmount(shopCouponDTO.getDiscountAmount());
        mopShopCouponDTO.setEndTime(shopCouponDTO.getEndTime());
        mopShopCouponDTO.setStartTime(shopCouponDTO.getStartTime());
        mopShopCouponDTO.setLifecycle(shopCouponDTO.getLifecycle() == null ? -1 : shopCouponDTO.getLifecycle());
        mopShopCouponDTO.setStatus(shopCouponDTO.getStatus());
        mopShopCouponDTO.setTotalCount(shopCouponDTO.getTotalCount());

        return mopShopCouponDTO;
    }

    private static List<MopBannerDTO> genMopBannerDTOList(List<BannerDTO> bannerDTOList) {

        if (bannerDTOList == null) {
            return Collections.EMPTY_LIST;
        }

        List<MopBannerDTO> mopBannerDTOList = Lists.newArrayListWithCapacity(bannerDTOList.size());

        for (BannerDTO bannerDTO : bannerDTOList) {
            MopBannerDTO mopBannerDTO = genMopBannerDTO(bannerDTO);
            mopBannerDTOList.add(mopBannerDTO);
        }

        return mopBannerDTOList;
    }

    private static MopBannerDTO genMopBannerDTO(BannerDTO bannerDTO) {

        if (bannerDTO == null) {
            return null;
        }

        MopBannerDTO mopBannerDTO = new MopBannerDTO();
        mopBannerDTO.setBannerUid("" + bannerDTO.getSellerId() + "_" + bannerDTO.getId());
        mopBannerDTO.setBannerLocation(bannerDTO.getBannerLocation() == null ? 0 : bannerDTO.getBannerLocation());
        mopBannerDTO.setImageUrl(bannerDTO.getImageUrl());
        mopBannerDTO.setImageDesc(bannerDTO.getImageDesc());
        mopBannerDTO.setUrl(bannerDTO.getUrl());

        return mopBannerDTO;

    }

    public static List<MopShopDTO> genMopShopDTOList(List<ShopDTO> shopDTOs) {

        if (CollectionUtils.isEmpty(shopDTOs)) {
            return Collections.EMPTY_LIST;
        }
        List<MopShopDTO> mopShopDTOs = new ArrayList<MopShopDTO>();
        for (ShopDTO shopDTO : shopDTOs) {
            mopShopDTOs.add(genMopShopDTO(shopDTO));
        }
        return mopShopDTOs;
    }

    public static MopShopItemGroupDTO genMopShopItemGroupDTO(ShopItemGroupDTO shopItemGroupDTO) {

        MopShopItemGroupDTO mopShopItemGroupDTO = new MopShopItemGroupDTO();
        if (shopItemGroupDTO != null) {
            mopShopItemGroupDTO.setShopId(shopItemGroupDTO.getShopId());
            mopShopItemGroupDTO.setSellerId(shopItemGroupDTO.getSellerId());
            mopShopItemGroupDTO.setCreateTime(shopItemGroupDTO.getCreateTime());
            mopShopItemGroupDTO.setItemGroupUid(genMopItemGroupUid(shopItemGroupDTO.getSellerId(),shopItemGroupDTO.getId()));
            mopShopItemGroupDTO.setGroupName(shopItemGroupDTO.getGroupName());
        }
        return mopShopItemGroupDTO;
    }

    private static String genMopItemGroupUid(Long sellerId, Long id) {
        return ""+sellerId+"_"+id;
    }

    public static List<MopShopItemGroupDTO> genMopShopItemGroupDTOList(List<ShopItemGroupDTO> shopItemGroupDTOs) {

        if (shopItemGroupDTOs == null || shopItemGroupDTOs.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<MopShopItemGroupDTO> mopShopItemGroupDTOs = new ArrayList<MopShopItemGroupDTO>();
        for (ShopItemGroupDTO shopItemGroupDTO : shopItemGroupDTOs) {
            mopShopItemGroupDTOs.add(genMopShopItemGroupDTO(shopItemGroupDTO));
        }
        return mopShopItemGroupDTOs;
    }

    public static ShopUidDTO parseShopUid(String itemUid) {
        if (StringUtils.isBlank(itemUid)) {
            return null;
        }
        try {
            ShopUidDTO shopUidDTO = new ShopUidDTO();
            String[] strs = itemUid.split("_");
            if (strs.length != 2) {
                return null;
            }

            long sellerId = Long.parseLong(strs[0]);
            long shopId = Long.parseLong(strs[1]);
            shopUidDTO.setSellerId(sellerId);
            shopUidDTO.setSellerId(shopId);

            return shopUidDTO;
        } catch (Exception e) {
            //TODO error handle
        }
        return null;
    }

    public static MopStoreDTO genMopStoreDTO(StoreDTO storeDTO) {

        MopStoreDTO mopStoreDTO = new MopStoreDTO();

        mopStoreDTO.setStoreUid(StoreUtil.genUid(storeDTO.getId(), storeDTO.getSellerId()));
        mopStoreDTO.setStoreNumber(storeDTO.getStoreNumber());
        mopStoreDTO.setStoreName(storeDTO.getStoreName());
        mopStoreDTO.setStoreImage(storeDTO.getStoreImage());

        mopStoreDTO.setServiceTime(storeDTO.getServiceTime());
        mopStoreDTO.setServiceDesc(storeDTO.getServiceDesc());

        mopStoreDTO.setLongitude(storeDTO.getLongitude());
        mopStoreDTO.setLatitude(storeDTO.getLatitude());
        mopStoreDTO.setPhone(storeDTO.getPhone());

        mopStoreDTO.setCountryCode(storeDTO.getCountryCode());
        mopStoreDTO.setProvinceCode(storeDTO.getProvinceCode());
        mopStoreDTO.setCityCode(storeDTO.getCityCode());
        mopStoreDTO.setAreaCode(storeDTO.getAreaCode());
        mopStoreDTO.setTownCode(storeDTO.getTownCode());

        mopStoreDTO.setSupportPickUp(storeDTO.getSupportPickUp());
        mopStoreDTO.setSupportDelivery(storeDTO.getSupportDelivery());

        mopStoreDTO.setDeliveryRange(storeDTO.getDeliveryRange());

        mopStoreDTO.setAddress(storeDTO.getAddress());

        return mopStoreDTO;
    }

    public static List<MopStoreDTO> genMopStoreDTOList(List<StoreDTO> storeDTOs) {

        List<MopStoreDTO> mopStoreDTOs = new ArrayList<MopStoreDTO>();

        for (StoreDTO storeDTO : storeDTOs) {
            mopStoreDTOs.add(genMopStoreDTO(storeDTO));
        }

        return mopStoreDTOs;
    }
}
