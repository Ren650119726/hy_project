package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemSkuPropertyRecommendationQTO;
import com.mockuai.itemcenter.core.dao.ItemSkuPropertyRecommendationDAO;
import com.mockuai.itemcenter.core.domain.ItemSkuPropertyRecommendationDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ItemSkuPropertyRecommendationDAOImpl extends SqlMapClientDaoSupport implements ItemSkuPropertyRecommendationDAO {


    @Override
    public List<ItemSkuPropertyRecommendationDO> queryItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationQTO itemSkuPropertyRecommendationQTO) {

        if(itemSkuPropertyRecommendationQTO.getNeedPaging()!=null&&itemSkuPropertyRecommendationQTO.getNeedPaging()==true){
            Long totalCount = (Long) getSqlMapClientTemplate()
                    .queryForObject("ItemSkuPropertyRecommendationDAO.countItemSkuPropertyRecommendation",itemSkuPropertyRecommendationQTO);// 总记录数
            itemSkuPropertyRecommendationQTO.setTotalCount(totalCount.intValue());
            if (totalCount == 0) {
                return Collections.emptyList();
            } else {
                itemSkuPropertyRecommendationQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
        }

        return getSqlMapClientTemplate().queryForList("ItemSkuPropertyRecommendationDAO.queryItemSkuPropertyRecommendation",itemSkuPropertyRecommendationQTO);
    }

    @Override
    public Long addItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationDO itemSkuPropertyRecommendationDO) {
        return (Long) getSqlMapClientTemplate().insert("ItemSkuPropertyRecommendationDAO.addItemSkuPropertyRecommendation",itemSkuPropertyRecommendationDO);
    }

    @Override
    public Long deleteItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSkuPropertyRecommendationDAO.deleteItemSkuPropertyRecommendation",query));
    }
}