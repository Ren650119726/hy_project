package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemSkuRecommendationQTO;
import com.mockuai.itemcenter.core.dao.ItemSkuRecommendationDAO;
import com.mockuai.itemcenter.core.domain.ItemSkuRecommendationDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ItemSkuRecommendationDAOImpl extends SqlMapClientDaoSupport implements ItemSkuRecommendationDAO {

    @Override
    public List<ItemSkuRecommendationDO> queryItemSkuRecommendation(ItemSkuRecommendationQTO itemSkuRecommendationQTO) {

        if (itemSkuRecommendationQTO.getNeedPaging() != null && itemSkuRecommendationQTO.getNeedPaging() == true) {
            Long totalCount = (Long) getSqlMapClientTemplate()
                    .queryForObject("ItemSkuRecommendationDAO.countItemSkuRecommendation", itemSkuRecommendationQTO);// 总记录数
            itemSkuRecommendationQTO.setTotalCount(totalCount.intValue());
            if (totalCount == 0) {
                return Collections.emptyList();
            } else {
                itemSkuRecommendationQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
        }

        return getSqlMapClientTemplate().queryForList("ItemSkuRecommendationDAO.queryItemSkuRecommendation", itemSkuRecommendationQTO);
    }

    @Override
    public Long addItemSkuRecommendation(ItemSkuRecommendationDO itemSkuRecommendationDO) {
        return (Long) getSqlMapClientTemplate().insert("ItemSkuRecommendationDAO.addItemSkuRecommendation", itemSkuRecommendationDO);
    }

    @Override
    public Long deleteItemSkuRecommendation(ItemSkuRecommendationDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSkuRecommendationDAO.deleteItemSkuRecommendation", query));
    }
}