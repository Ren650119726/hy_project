package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemSettlementConfigQTO;
import com.mockuai.itemcenter.core.dao.ItemSettlementConfigDAO;
import com.mockuai.itemcenter.core.domain.ItemSettlementConfigDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ItemSettlementConfigDAOImpl extends SqlMapClientDaoSupport implements ItemSettlementConfigDAO {


    @Override
    public ItemSettlementConfigDO getItemSettlementConfig(ItemSettlementConfigDO query) {
        return (ItemSettlementConfigDO) getSqlMapClientTemplate().queryForObject("ItemSettlementConfig.getItemSettlementConfig",query);
    }

    @Override
    public Long enableItemSettlementConfig(ItemSettlementConfigDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSettlementConfig.enableItemSettlementConfig",query));
    }

    @Override
    public Long disableItemSettlementConfig(ItemSettlementConfigDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSettlementConfig.disableItemSettlementConfig",query));
    }

    @Override
    public Long deleteItemSettlementConfig(ItemSettlementConfigDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSettlementConfig.deleteItemSettlementConfig",query));
    }

    @Override
    public List<ItemSettlementConfigDO> queryItemSettlementConfig(ItemSettlementConfigQTO query) {

        if(query.getNeedPaging()!=null&&query.getNeedPaging()) {

            Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ItemSettlementConfig.countItemSettlementConfig", query);

            if(count > 0){
                query.setOffsetAndTotalPage();
                query.setTotalCount(count);
            }else {
                return Collections.EMPTY_LIST;
            }
        }

        return getSqlMapClientTemplate().queryForList("ItemSettlementConfig.queryItemSettlementConfig", query);
    }

    @Override
    public Long addItemSettlementConfig(ItemSettlementConfigDO itemSettlementConfigDO) {
        return (Long) getSqlMapClientTemplate().insert("ItemSettlementConfig.addItemSettlementConfig",itemSettlementConfigDO);
    }

    @Override
    public Long updateItemSettlementConfig(ItemSettlementConfigDO itemSettlementConfigDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSettlementConfig.updateItemSettlementConfig",itemSettlementConfigDO));
    }
}