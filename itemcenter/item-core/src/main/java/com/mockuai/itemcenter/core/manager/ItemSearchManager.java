package com.mockuai.itemcenter.core.manager;

//import com.mockuai.distributioncenter.common.domain.dto.RatioDTO;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchResultDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public interface ItemSearchManager {

    /**
     * 往搜索引擎中添加或更新item索引
     * @param itemDTO
     * @throws ItemException
     */
    public void setItemIndex(ItemDTO itemDTO) throws ItemException;


    /**
     * 搜索item文档
     * @param itemSearchQTO
     * @return
     * @throws ItemException
     */
    public ItemSearchResultDTO searchItemIndex(ItemSearchQTO itemSearchQTO) throws ItemException;

    /**
     * 删除item索引
     * @param itemId
     * @param sellerId
     * @return
     * @throws ItemException
     */
    public boolean deleteItemIndex(Long itemId, Long sellerId) throws ItemException;

    public boolean setItemIndex (ItemSearchDTO itemSearchDTO) throws ItemException;

    boolean setItemIndex(List<ItemSearchDTO> itemSearchDTOs);

    public boolean updateItemSalesVolume(List<OrderItemDTO> orderItemDTOs) throws ItemException;

    public boolean updateItemSalesVolume(Long sellerId,Long itemId,Integer number,String bizCode) throws ItemException;


    boolean updateItemComments(ItemCommentDTO commentDTO) throws ItemException;

//    boolean updateItemDirectCommission(List<RatioDTO> ratioDTOList) throws ItemException;
}
