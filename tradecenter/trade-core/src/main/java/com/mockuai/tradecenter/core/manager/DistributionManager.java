package com.mockuai.tradecenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

/**
 * 分销平台接口封装
 * Created by zengzhangqiang on 6/6/16.
 */
public interface DistributionManager {
    /**
     * 根据分销商id列表查询分销商信息列表
     * @param distributorIdList
     * @param appKey
     * @return
     * @throws TradeException
     */
    public List<DistShopDTO> queryDistShop(List<Long> distributorIdList, String appKey) throws TradeException;

    List<SellerDTO> queryDistSeller(List<Long> distributorIdList, String appKey) throws TradeException;

    /**
     * 根据分销商id获取分销店铺信息
     * @param distributorId
     * @param appKey
     * @return
     * @throws TradeException
     */
    DistShopDTO getDistShop(Long distributorId, String appKey) throws TradeException;
}
