package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemCategoryTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryTmplQTO;
import com.mockuai.itemcenter.core.dao.ItemCategoryTmplDAO;
import com.mockuai.itemcenter.core.domain.ItemCategoryTmplDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yindingyu on 16/3/1.
 */
@Repository
public class ItemCategoryTmplDAOImpl extends SqlMapClientDaoSupport implements ItemCategoryTmplDAO {
    @Override
    public List<ItemCategoryTmplDO> queryItemCategoryTmpl(ItemCategoryTmplQTO tmplQTO) {

        return getSqlMapClientTemplate().queryForList("ItemCategoryTmplDAO.queryItemCategoryTmpl", tmplQTO);
    }

    @Override
    public ItemCategoryTmplDO getItemCategoryTmpl(Long topId) {

        ItemCategoryTmplDO query = new ItemCategoryTmplDO();
        query.setId(topId);
        return (ItemCategoryTmplDO) getSqlMapClientTemplate().queryForObject("ItemCategoryTmplDAO.getItemCategoryTmpl", query);
    }

    @Override
    public Long addItemCategoryTmpl(ItemCategoryTmplDO itemCategoryTmplDO) {
        return (Long) getSqlMapClientTemplate().insert("ItemCategoryTmplDAO.addItemCategoryTmpl", itemCategoryTmplDO);
    }
}
