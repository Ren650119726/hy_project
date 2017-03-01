package com.mockuai.shopcenter.core.service.action.shop;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.BannerEnum;
import com.mockuai.shopcenter.constant.ElementEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.domain.ShopItemDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.*;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.*;
import com.mockuai.shopcenter.domain.qto.BannerQTO;
import com.mockuai.shopcenter.domain.qto.ShopCouponQTO;
import com.mockuai.shopcenter.domain.qto.ShopItemQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ziqi.
 */
@Service
public class GetShopDetailsAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(GetShopDetailsAction.class);

    @Resource
    private ShopManager shopManager;

    @Resource
    private ShopImageManager shopImageManager;

    @Resource
    private ShopCollectionManager shopCollectionManager;

    @Resource
    private ShopCouponManager shopCouponManager;

    @Resource
    private ShopItemManager shopItemManager;

    @Resource
    private BannerManager bannerManager;

    @Resource
    private ItemManager itemManager;

    @Resource
    private CouponManager couponManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        ShopRequest shopRequest = context.getRequest();
        Long shopId = (Long) shopRequest.getParam("shopId");
        Long userId = (Long) shopRequest.getParam("userId");
        String appKey = (String) shopRequest.getParam("appKey");

        String bizCode = (String) context.get("bizCode");

        if (shopId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopId is null");
        }


        ShopDTO shopDTO = shopManager.getShopById(shopId, bizCode);
        ShopImageDTO shopImageDTO;

        // img
        if (shopDTO.getShopIconId() != null) {
            shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopIconId(), shopDTO.getSellerId());
            shopDTO.setShopIconUrl(shopImageDTO.getImageUrl());
        }

        if (shopDTO.getShopBannerImageId() != null) {
            shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopBannerImageId(), shopDTO.getSellerId());
            shopDTO.setShopBannerImageUrl(shopImageDTO.getImageUrl());
        }

        // 判断收藏;
        if (userId != null) {
            ShopCollectionDTO shopCollectionDTO = shopCollectionManager.getShopCollection(shopDTO.getSellerId(), userId);
            if (shopCollectionDTO != null) {
                shopDTO.setIsCollected(true);
            } else {
                shopDTO.setIsCollected(false);
            }
        }


        //店铺首页轮播图片
        BannerQTO bannerQTO = new BannerQTO();
        bannerQTO.setParentId(shopId);
        bannerQTO.setParentType(ElementEnum.MAIN_PAGE.getType());
        bannerQTO.setBannerType(BannerEnum.CAROUSEL_BANNER.getType());
        bannerQTO.setBizCode(bizCode);

        List<BannerDTO> bannerDTOList = bannerManager.queryBanner(bannerQTO);

        Collections.sort(bannerDTOList, new Comparator<BannerDTO>() {
            @Override
            public int compare(BannerDTO o1, BannerDTO o2) {
                return o1.getBannerLocation()-o2.getBannerLocation();
            }
        });

        shopDTO.setBannerDTOList(bannerDTOList);


        ShopCouponQTO shopCouponQTO = new ShopCouponQTO();
        shopCouponQTO.setSellerId(shopDTO.getSellerId());
        shopCouponQTO.setBizCode(bizCode);

        List<ShopCouponDTO> shopCouponDTOList = shopCouponManager.queryShopCoupon(shopCouponQTO);

        List<ShopCouponDTO> shopCouponList = Lists.newArrayListWithCapacity(shopCouponDTOList.size());

        if (shopCouponDTOList != null && shopCouponDTOList.size() > 0) {

            Map<Long, ShopCouponDTO> couponIdMap = Maps.newHashMapWithExpectedSize(shopCouponDTOList.size());

            for (ShopCouponDTO shopCouponDTO : shopCouponDTOList) {
                couponIdMap.put(shopCouponDTO.getCouponId(), shopCouponDTO);
            }

            //调用营销平台填充具体信息

            ActivityCouponQTO activityCouponQTO = new ActivityCouponQTO();
            activityCouponQTO.setActivityCreatorId(shopDTO.getSellerId());
            activityCouponQTO.setIdList(new ArrayList<Long>(couponIdMap.keySet()));
            activityCouponQTO.setBizCode(bizCode);
            List<ActivityCouponDTO> couponDTOList = couponManager.queryActivityCoupon(activityCouponQTO, appKey);

            if (couponDTOList != null && couponDTOList.size() > 0) {

                for (ActivityCouponDTO activityCouponDTO : couponDTOList) {

                    ShopCouponDTO shopCouponDTO = couponIdMap.get(activityCouponDTO.getId());

                    if (shopCouponDTO != null) {

                        ShopCouponDTO shopCoupon = new ShopCouponDTO();


                        BeanUtils.copyProperties(shopCouponDTO, shopCoupon);
                        BeanUtils.copyProperties(activityCouponDTO, shopCoupon);
                        shopCoupon.setCouponId(activityCouponDTO.getId());
                        shopCouponList.add(shopCoupon);
                    }
                }
            }

            shopDTO.setShopCouponDTOList(shopCouponList);
        }

        ItemSearchQTO itemSearchQTO  = new ItemSearchQTO();
        itemSearchQTO.setBizCode(bizCode);
        itemSearchQTO.setOffset(0);
        itemSearchQTO.setCount(12);
        itemSearchQTO.setShopId(shopId);

        List<ItemSearchDTO> itemSearchDTOList = itemManager.queryItem(itemSearchQTO,appKey);



        List<ShopItemDTO> shopItemDTOList = Lists.newArrayListWithCapacity(itemSearchDTOList.size());


        for(ItemSearchDTO itemSearchDTO : itemSearchDTOList){
            ShopItemDTO shopItemDTO = new ShopItemDTO();
            BeanUtils.copyProperties(itemSearchDTO,shopItemDTO);
            shopItemDTO.setBizCode(bizCode);
            shopItemDTO.setSellerId(shopDTO.getSellerId());

            shopItemDTOList.add(shopItemDTO);
        }

        shopDTO.setShopItemDTOList(shopItemDTOList);

        /**
        ShopItemQTO shopItemQTO = new ShopItemQTO();
        shopItemQTO.setSellerId(shopDTO.getSellerId());
        shopItemQTO.setShopId(shopId);
        shopItemQTO.setBizCode(bizCode);


        List<ShopItemDTO> shopItemDTOList = shopItemManager.queryShopItem(shopItemQTO);

        if (shopCouponDTOList != null && shopCouponDTOList.size() > 0) {

            Map<Long, ShopItemDTO> shopItemDTOMap = Maps.newHashMapWithExpectedSize(shopCouponDTOList.size());

            for (ShopItemDTO shopItemDTO : shopItemDTOList) {
                shopItemDTOMap.put(shopItemDTO.getItemId(), shopItemDTO);
            }

            //调用商品平台填充具体信息
            ItemQTO itemQTO = new ItemQTO();
            itemQTO.setSellerId(shopDTO.getSellerId());
            itemQTO.setIdList(Lists.newArrayList(shopItemDTOMap.keySet()));


            List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);


            if (itemDTOList != null && itemDTOList.size() > 0){
                for(ItemDTO itemDTO : itemDTOList){

                    ShopItemDTO shopItemDTO = shopItemDTOMap.get(itemDTO.getId());

                    if(shopItemDTO!=null){
                        //TODO 拷贝属性
                    }
                }
            }

            shopDTO.setShopItemDTOList(shopItemDTOList);
        }
**/

        return ResponseUtil.getSuccessResponse(shopDTO);

    }


    @Override
    public String getName() {
        return ActionEnum.GET_SHOP_DETAILS.getActionName();
    }
}
