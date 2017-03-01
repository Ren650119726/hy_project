package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthItemQTO;
import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthItemDO;

import java.util.List;

/**
 * Created by duke on 16/4/18.
 */
public interface VirtualWealthItemDAO {
    /**
     * 添加
     */
    Long add(VirtualWealthItemDO virtualWealthItemDO);

    /**
     * 查询
     */
    List<VirtualWealthItemDO> query(VirtualWealthItemQTO virtualWealthItemQTO);

    /**
     * 获得
     */
    VirtualWealthItemDO get(Long id);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 总量
     */
    Long totalCount(VirtualWealthItemQTO virtualWealthItemQTO);

    /**
     * 更新
     */
    int update(VirtualWealthItemDO virtualWealthItemDO);
}