package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.area.AreaDTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;
import java.util.Set;

/**
 * Created by yindingyu on 15/10/23.
 */
public interface RegionManager {


    List<AreaDTO> queryAreas(String appkey) throws ItemException;
}
