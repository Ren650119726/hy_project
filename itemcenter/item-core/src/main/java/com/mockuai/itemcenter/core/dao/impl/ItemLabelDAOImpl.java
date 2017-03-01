package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemLabelQTO;
import com.mockuai.itemcenter.core.dao.ItemLabelDAO;
import com.mockuai.itemcenter.core.domain.ItemLabelDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ItemLabelDAOImpl extends SqlMapClientDaoSupport implements ItemLabelDAO {


    @Override
    public Long addItemLabel(ItemLabelDO itemLabelDO) {
        return (Long) getSqlMapClientTemplate().insert("ItemLabelDAO.addItemLabel",itemLabelDO);
    }

    @Override
    public Long updateItemLabel(ItemLabelDO itemLabelDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemLabelDAO.updateItemLabel",itemLabelDO));
    }

    @Override
    public ItemLabelDO getItemLabel(ItemLabelDO query) {
        return (ItemLabelDO) getSqlMapClientTemplate().queryForObject("ItemLabelDAO.getItemLabel",query);
    }

    @Override
    public Long deleteItemLabel(ItemLabelDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemLabelDAO.deleteItemLabel",query));
    }

    @Override
    public List<ItemLabelDO> queryItemLabel(ItemLabelQTO itemLabelQTO) {
        if (null != itemLabelQTO.getNeedPaging()&&itemLabelQTO.getNeedPaging()==true) {
            Integer totalCount = (Integer) getSqlMapClientTemplate()
                    .queryForObject("ItemLabelDAO.countItemLabel", itemLabelQTO);// 总记录数
            itemLabelQTO.setTotalCount(totalCount);
            if (totalCount == 0) {
                return Collections.emptyList();
            } else {
                itemLabelQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
            totalCount = null;
        }

        return getSqlMapClientTemplate().queryForList("ItemLabelDAO.queryItemLabel",itemLabelQTO);
    }
}