package com.mockuai.shopcenter;

import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.domain.dto.LastDaysCountDTO;
import com.mockuai.shopcenter.domain.dto.ShopCollectionDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;

import java.util.List;

/**
 * Created by luliang on 15/7/27.
 */
public interface ShopClient {

    public Response<ShopDTO> addShop(ShopDTO shopDTO, String appKey);

    public Response<List<ShopDTO>> queryShop(ShopQTO shopQTO, String appKey);

    public Response<Integer> countShop(ShopQTO shopQTO, String appKey);

    public Response<ShopDTO> getShop(Long sellerId, String appKey);

    public Response<Boolean> updateShop(ShopDTO shopDTO, String appKey);

    public Response<ShopItemGroupDTO> addShopItemGroup(ShopItemGroupDTO  shopItemGroupDTO, String appKey);

    public Response<Boolean> updateShopItemGroup(ShopItemGroupDTO  shopItemGroupDTO,String updateItems, String appKey);

    public Response<Boolean> deleteShopItemGroup(Long id, Long sellerId, String appKey);

    public Response<List<ShopItemGroupDTO>> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO, String appKey);

    public Response<ShopItemGroupDTO> getShopItemGroup(Long sellerId, Long groupId,String needItems, String appKey);

    /**
     * 查询店铺的状态;
     * @param sellerId
     * @return
     */
    public Response<Integer> getShopStatus(Long sellerId, String appKey);

    /**
     * 冻结店铺;
     * @param sellerId
     * @return
     */
    public Response<Boolean> freezeShop(Long sellerId, String appKey);

    /**
     * 解冻店铺;
     * @param sellerId
     * @return
     */
    public Response<Boolean> thawShop(Long sellerId, String appKey);

    // 店铺收藏;
    public Response<ShopCollectionDTO> addShopCollection(ShopCollectionDTO shopCollectionDTO, String appKey);

    public Response<Boolean> cancelShopCollection(Long sellerId, Long userId, String appKey);

    public Response<Boolean> checkShopCollection(Long sellerId, Long userId, String appKey);

    public Response<List<ShopCollectionDTO>> queryShopCollectionUser(ShopCollectionQTO shopCollectionQTO, String appKey);

    public Response<List<ShopDTO>> queryUserCollectionShop(ShopCollectionQTO shopCollectionQTO, String appKey);

    public Response<List<LastDaysCountDTO>> countLastDaysShop(ShopQTO shopQTO, String appKey);

    public Response<ShopDTO> getShopDetails(Long shopId,String appKey);

    public Response<Boolean> setShopDetails(ShopDTO shopDTO,String appKey);
}
