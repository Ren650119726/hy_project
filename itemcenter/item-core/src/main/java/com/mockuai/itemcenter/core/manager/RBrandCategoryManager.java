package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.RBrandCategoryDTO;
import com.mockuai.itemcenter.core.domain.RBrandCategoryDO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

public interface RBrandCategoryManager {

    Long addRBrandCategory(RBrandCategoryDTO rBrandCategoryDTO) throws ItemException;

    List<Long> queryRBrandCategory(Long brandId,String bizCode) throws ItemException;

    List<Long> queryRBrandCategoryByCateId(Long cateId,String bizCode) throws ItemException;

    Long deleteRBrandCategoryByBrandId(Long brandId,String bizCode) throws ItemException;
}