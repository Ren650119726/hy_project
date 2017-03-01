package com.mockuai.marketingcenter.core.manager;

import java.util.List;

import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by edgar.zr on 5/23/2016.
 */
public interface DistributorManager {

    List<DistShopDTO> queryShop(DistShopQTO distShopQTO, String appKey) throws MarketingException;
    
    GainsSetDTO getGainsSet(String appKey) throws MarketingException;
    
    List<ItemSkuDistPlanDTO> getItemSkuDistPlanList(Long itemId , String appKey) throws MarketingException;
    
}