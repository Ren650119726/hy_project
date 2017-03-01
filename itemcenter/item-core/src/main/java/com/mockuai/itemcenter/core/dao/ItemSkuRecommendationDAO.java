package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemSkuRecommendationQTO;
import com.mockuai.itemcenter.core.domain.ItemSkuRecommendationDO;

import java.util.List;

public interface ItemSkuRecommendationDAO {

    List<ItemSkuRecommendationDO> queryItemSkuRecommendation(ItemSkuRecommendationQTO itemSkuRecommendationQTO);

    Long addItemSkuRecommendation(ItemSkuRecommendationDO itemSkuRecommendationDO);

    Long deleteItemSkuRecommendation(ItemSkuRecommendationDO query);
}