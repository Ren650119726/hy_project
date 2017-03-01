package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemSkuPropertyRecommendationQTO;
import com.mockuai.itemcenter.core.domain.ItemSkuPropertyRecommendationDO;

import java.util.List;

public interface ItemSkuPropertyRecommendationDAO {

    List<ItemSkuPropertyRecommendationDO> queryItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationQTO itemSkuRecommendationQTO);

    Long addItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationDO itemSkuPropertyRecommendationDO);

    Long deleteItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationDO query);
}