package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.core.dao.RBrandCategoryDAO;
import com.mockuai.itemcenter.core.domain.RBrandCategoryDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RBrandCategoryDAOImpl extends SqlMapClientDaoSupport implements RBrandCategoryDAO {


    @Override
    public Long addRBrandCategory(RBrandCategoryDO rBrandCategoryDO) {
        return (Long) getSqlMapClientTemplate().insert("r_brand_category.addRBrandCategory",rBrandCategoryDO);
    }

    @Override
    public List<RBrandCategoryDO> queryRBrandCategory(RBrandCategoryDO rBrandCategoryDO) {
        return getSqlMapClientTemplate().queryForList("r_brand_category.queryRBrandCategory",rBrandCategoryDO);
    }

    @Override
    public Long deleteRBrandCategoryByBrandId(RBrandCategoryDO rBrandCategoryDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("r_brand_category.deleteRBrandCategoryByBrandId",rBrandCategoryDO));
    }
}