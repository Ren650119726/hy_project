package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthItemQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

/**
 * Created by duke on 16/4/18.
 */
public interface VirtualWealthItemManager {
    /**
     * 添加
     */
    Long addItem(VirtualWealthItemDTO virtualWealthItemDTO) throws VirtualWealthException;

    /**
     * 查询
     */
    List<VirtualWealthItemDTO> queryItems(VirtualWealthItemQTO virtualWealthItemQTO) throws VirtualWealthException;

    /**
     * 删除
     */
    int deleteItem(Long id) throws VirtualWealthException;

    /**
     * 总量
     */
    Long totalCount(VirtualWealthItemQTO virtualWealthItemQTO) throws VirtualWealthException;

    /**
     * 获得
     */
    VirtualWealthItemDTO get(Long id) throws VirtualWealthException;

    /**
     * 更新
     */
    int update(VirtualWealthItemDTO virtualWealthItemDTO) throws VirtualWealthException;
}