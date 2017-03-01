package com.mockuai.mainweb.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface ItemManager {

//     List<ItemDTO> queryItem(Long itemId) throws MainWebException;
    List<ItemDTO> queryItem(ItemQTO itemQTO, String apKey) throws MainWebException;


    ItemDTO getItemById(Long itemId, Long sellerId, Boolean needDetail, String appKey) throws MainWebException;
}
