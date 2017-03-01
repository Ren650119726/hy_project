package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.domain.dto.GoodsItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;

import java.util.List;

/**
 * Created by lotmac on 16/3/9.
 */
public interface ShopManager {

    /**
     * 添加店铺
     */
    Long add(DistShopDTO distShopDTO) throws DistributionException;

    /**
     * 查询店铺
     */
    List<DistShopDTO> query(DistShopQTO distShopQTO) throws DistributionException;

    /**
     * 获得店铺
     */
    DistShopDTO get(Long id) throws DistributionException;

    /**
     * 更新店铺
     */
    Integer update(DistShopDTO distShopDTO) throws DistributionException;

    /**
     * 通过卖家ID获得店铺
     */
    DistShopDTO getBySellerId(Long sellerId) throws DistributionException;

    void updateQrcodeUrl(DistShopQTO shop) throws DistributionException;


    List<ShopItemGroupDTO>  queryShopItemGroup(String appKey) throws  DistributionException;


    DistributionResponse queryGoodsList(boolean isSeller,  String appKey, Long sellerId, Long groupId, int offset, int count, String itemName, String orderBy , int desc ) throws DistributionException ;

    DistributionResponse getAllItem(boolean isSeller,  int offset,int count,String appKey) throws DistributionException ;


    }
