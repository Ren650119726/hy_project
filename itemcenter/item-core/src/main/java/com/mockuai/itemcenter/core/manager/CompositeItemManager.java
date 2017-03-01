package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CompositeItemManager {
	Map getIntervalGain(Long itemId, BigDecimal oneGains, List<ItemSkuDTO> itemSkuDTOList, String appKey) throws ItemException;


    Double getCompositeGain(Long itemId, String appKey) throws ItemException;

    String getCompositeIntervalGain(Long itemId,BigDecimal oneGains,List<ItemSkuDTO> itemSkuDTOList, String appKey) throws ItemException;

    StoreItemSkuDTO getCompositeStore(Long itemId, String appKey) throws ItemException;

    /**
	 * 新增组合商品
	 * @param compositeItemDTO
	 * @return
	 * @throws ItemException
	 */
	 Long addCompositeItem(CompositeItemDTO compositeItemDTO)throws ItemException;

    void batchAddCompositeItem(List<CompositeItemDTO> compositeItemData)
            throws ItemException;

    /**
	 * 根据item_id删除关联的组合商品
	 * @param itemId
	 * @return
	 */
	 int deleteCompositeItemByItemId(Long itemId)throws ItemException;
	
	/**
	 * 根据itemId获取组合商品列表
	 * @param itemId
	 * @return
	 */
	 List<CompositeItemDTO> getCompositeItemByItemId(Long itemId);


    List<CompositeItemDTO> queryCompositeItemByItemIdList(List<Long> itemIdList);

    List<CompositeItemDTO> queryCompositeItemByItemSkuQTO(ItemSkuQTO itemSkuQTO);
}
