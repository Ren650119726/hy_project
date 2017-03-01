package com.mockuai.itemcenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.*;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by yindingyu on 16/5/19.
 * updated by jiguansheng
 */
public interface DistributorManager {
    GainsSetDTO getGainsSet(String appKey) throws ItemException;

    List<SellerDTO> querySeller(SellerQTO sellerQTO, String appKey) throws ItemException;

    List<DistShopDTO> queryShop(DistShopQTO qto, String appKey) throws ItemException;

    //getDistByItemSkuId
    List<ItemSkuDistPlanDTO> getDistByItemSkuId(List<Long> itemSkuIdList, String appKey) throws ItemException;

    DistShopForMopDTO getShopForMopBySellerId(Long distributorId, String appKey) throws ItemException;

    //查询商品分佣信息
    List<ItemSkuDistPlanDTO> getItemSkuDistPlanList(Long itemId , String appKey) throws ItemException ;
}
