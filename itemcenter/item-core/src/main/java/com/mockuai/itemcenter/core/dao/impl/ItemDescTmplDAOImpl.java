package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemDescTmplQTO;
import com.mockuai.itemcenter.core.dao.ItemDescTmplDAO;
import com.mockuai.itemcenter.core.domain.ItemDescTmplDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ItemDescTmplDAOImpl extends SqlMapClientDaoSupport implements ItemDescTmplDAO {


    @Override
    public Long addItemDescTmpl(ItemDescTmplDO itemDescTmplDO) {
        return (Long) getSqlMapClientTemplate().insert("item_desc_tmpl.addItemDescTmpl", itemDescTmplDO);
    }

    @Override
    public Long updateItemDescTmpl(ItemDescTmplDO itemDescTmplDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("item_desc_tmpl.updateItemDescTmpl", itemDescTmplDO));
    }

    @Override
    public ItemDescTmplDO getItemDescTmpl(ItemDescTmplDO query) {
        return (ItemDescTmplDO) getSqlMapClientTemplate().queryForObject("item_desc_tmpl.getItemDescTmpl", query);
    }

    @Override
    public Long deleteItemDescTmpl(ItemDescTmplDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("item_desc_tmpl.deleteItemDescTmpl", query));
    }

    @Override
    public List<ItemDescTmplDO> queryItemDescTmpl(ItemDescTmplQTO itemDescTmplQTO) {

        if (itemDescTmplQTO.getNeedPaging() != null && itemDescTmplQTO.getNeedPaging()) {
            Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("item_desc_tmpl.countItemDescTmpl", itemDescTmplQTO);// 总记录数
            itemDescTmplQTO.setTotalCount((int) totalCount.longValue());
            if (totalCount == 0) {
                return Collections.EMPTY_LIST;
            } else {
                itemDescTmplQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
        }
        return getSqlMapClientTemplate().queryForList("item_desc_tmpl.queryItemDescTmpl", itemDescTmplQTO);
    }
}