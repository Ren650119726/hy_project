package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.Map;

/**
 * Created by yindingyu on 15/10/20.
 */
public interface FreightManager {
    public Long calculate(Map<Long, Integer> itemNums, AddressDTO addressDTO) throws ItemException;

    Long calculateItemDefaultFreight(ItemDTO itemDTO);
}