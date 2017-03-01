package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.core.domain.RBrandCategoryDO;

import java.util.List;

public interface RBrandCategoryDAO {

    Long addRBrandCategory(RBrandCategoryDO rBrandCategoryDO);

    List<RBrandCategoryDO> queryRBrandCategory(RBrandCategoryDO rBrandCategoryDO);

    Long deleteRBrandCategoryByBrandId(RBrandCategoryDO rBrandCategoryDO);
}