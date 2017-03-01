package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuPropertyRecommendationDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuPropertyRecommendationQTO;

import java.util.List;

/**
 * Created by yindingyu on 16/1/11.
 */
public interface ItemSkuPropertyRecommendationManager {
    List<ItemSkuPropertyRecommendationDTO> queryItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationQTO itemSkuRecommendationQTO);

    Long addItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationDTO itemSkuPropertyRecommendationDTO);

    Long deleteItemSkuPropertyRecommendation(Long id, Long sellerId, String bizCode);
}
