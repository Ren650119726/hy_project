package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSuitDTO;
import com.mockuai.itemcenter.common.domain.dto.RItemSuitDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSuitQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
public interface ItemSuitManager {

    /**
     * 添加商品套装
     * @param rItemSuitDTO
     * @return
     */
    public Long addItemSuit(RItemSuitDTO rItemSuitDTO);


    public Long addItemSuit(List<RItemSuitDTO> rItemSuitDTOs);

    public Long addItemSuit(ItemSuitDTO itemSuitDTO,List<RItemSuitDTO> rItemSuitDTOs) throws ItemException;

    /**
     * 查询套装下的所有商品
     * @param itemQTO
     * @return
     */
    public List<ItemDTO> querySuit(ItemQTO itemQTO) throws ItemException;

    /**
     * 查询一个商品所属的所有套装
     * @param itemQTO
     * @return
     */
    public List<ItemDTO> querySuitOfItem(ItemQTO itemQTO) throws ItemException;

    List<ItemDTO> querySubItems(ItemQTO itemQTO) throws ItemException;

    void disableItemSuit(Long itemId, Long sellerId, String bizCode) throws ItemException;

    void increaseSuitSalesVolume(Long itemId, Long sellerId, Long num) throws ItemException;

    ItemSuitDTO getSuitExtraInfo(Long itemId, Long sellerId, String bizCode);

    List<ItemSuitDTO> queryItemSuitDiscount(ItemSuitQTO itemSuitQTO) throws ItemException;
}
