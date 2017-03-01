package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.domain.dto.RBrandCategoryDTO;
import com.mockuai.itemcenter.core.dao.RBrandCategoryDAO;
import com.mockuai.itemcenter.core.domain.RBrandCategoryDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.RBrandCategoryManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/5/13.
 */
@Service
public class RBrandCategoryManagerImpl implements RBrandCategoryManager {

    @Resource
    private RBrandCategoryDAO rBrandCategoryDAO;

    @Override
    public Long addRBrandCategory(RBrandCategoryDTO rBrandCategoryDTO) throws ItemException {
        RBrandCategoryDO rBrandCategoryDO = new RBrandCategoryDO();
        BeanUtils.copyProperties(rBrandCategoryDTO,rBrandCategoryDO);
        return rBrandCategoryDAO.addRBrandCategory(rBrandCategoryDO);
    }

    @Override
    public List<Long> queryRBrandCategory(Long brandId, String bizCode) throws ItemException {

        RBrandCategoryDO query = new RBrandCategoryDO();
        query.setBrandId(brandId);
        query.setBizCode(bizCode);

        List<RBrandCategoryDO> rBrandCategoryDOList = rBrandCategoryDAO.queryRBrandCategory(query);

        List<Long> categotyIdList = Lists.newArrayList();

        for(RBrandCategoryDO rBrandCategoryDO : rBrandCategoryDOList){

            categotyIdList.add(rBrandCategoryDO.getCategoryId());
        }
        return categotyIdList;
    }

    @Override
    public List<Long> queryRBrandCategoryByCateId(Long cateId, String bizCode) throws ItemException {
        RBrandCategoryDO query = new RBrandCategoryDO();
        query.setCategoryId(cateId);
        query.setBizCode(bizCode);

        List<RBrandCategoryDO> rBrandCategoryDOList = rBrandCategoryDAO.queryRBrandCategory(query);

        List<Long> brandIdList = Lists.newArrayList();

        for(RBrandCategoryDO rBrandCategoryDO : rBrandCategoryDOList){

            brandIdList.add(rBrandCategoryDO.getBrandId());
        }
        return brandIdList;
    }

    @Override
    public Long deleteRBrandCategoryByBrandId(Long brandId, String bizCode) throws ItemException {
        RBrandCategoryDO query = new RBrandCategoryDO();
        query.setBrandId(brandId);
        query.setBizCode(bizCode);

        return rBrandCategoryDAO.deleteRBrandCategoryByBrandId(query);
    }
}
