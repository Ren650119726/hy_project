package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.CollectionShopDTO;

import java.util.List;

/**
 * Created by duke on 16/5/21.
 */
public interface CollectionShopManager {
    /**
     * 添加
     * */
    Long add(CollectionShopDTO collectionShopDTO);

    /**
     * 删除
     * */
    int delete(Long id);

    /**
     * 通过用户ID查询
     * */
    List<CollectionShopDTO> queryByUserId(Long userId);
}
