package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuRecommendationDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuRecommendationQTO;

import java.util.List;

/**
 * Created by yindingyu on 16/1/11.
 */
public interface ItemSkuRecommendationManager {
    Long addItemSkuRecommendation(ItemSkuRecommendationDTO itemSkuRecommendationDTO);

    Long deleteItemSkuRecommendation(Long id, Long sellerId, String bizCode);

    List<ItemSkuRecommendationDTO> queryItemSkuRecommendation(ItemSkuRecommendationQTO itemSkuRecommendationQTO);
}
