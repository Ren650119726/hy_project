package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.RItemLabelQTO;
import com.mockuai.itemcenter.core.dao.RItemLabelDAO;
import com.mockuai.itemcenter.core.domain.ItemLabelDO;
import com.mockuai.itemcenter.core.domain.RItemLabelDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RItemLabelDAOImpl extends SqlMapClientDaoSupport implements RItemLabelDAO {


    @Override
    public List<RItemLabelDO> queryRItemLabel(RItemLabelQTO rItemLabelQTO) {
        return getSqlMapClientTemplate().queryForList("RItemLabelDAO.queryRItemLabel",rItemLabelQTO);
    }

    @Override
    public Long countRItemLabel(RItemLabelQTO rItemLabelQTO) {
        return (Long)getSqlMapClientTemplate().queryForObject("RItemLabelDAO.countRItemLabel",rItemLabelQTO);
    }

    @Override
    public Long addRItemLabel(RItemLabelDO rItemLabelDO) {
        return (Long) getSqlMapClientTemplate().insert("RItemLabelDAO.addRItemLabel",rItemLabelDO);
    }

    @Override
    public Long addRList(List<RItemLabelDO> list) {
        return (Long) getSqlMapClientTemplate().insert("RItemLabelDAO.addRList",list);

    }

    @Override
    public Long deleteByItem(Long itemId) {
        return Long.valueOf(getSqlMapClientTemplate().update("RItemLabelDAO.deleteByItem",itemId));

    }

    @Override
    public Long deleteByLabel(ItemLabelDO itemLabelDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("RItemLabelDAO.deleteByLabel",itemLabelDO));
    }
}