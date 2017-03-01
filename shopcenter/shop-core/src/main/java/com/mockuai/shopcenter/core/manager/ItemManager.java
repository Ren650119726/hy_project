package com.mockuai.shopcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.shopcenter.core.exception.ShopException;

import java.util.List;

/**
 * Created by yindingyu on 16/1/14.
 */
public interface ItemManager {

    List<ItemDTO> queryItem(ItemQTO itemQTO,String appKey) throws ShopException;

    List<ItemSearchDTO> queryItem(ItemSearchQTO itemSearcQTO,String appKey) throws ShopException;
}
